package cn.sisyphe.coffee.stock.domain.offset;

import cn.sisyphe.coffee.stock.domain.offset.parser.BillParser;
import cn.sisyphe.coffee.stock.domain.offset.service.InStockService;
import cn.sisyphe.coffee.stock.domain.offset.service.MoveStockService;
import cn.sisyphe.coffee.stock.domain.offset.service.OutStockService;
import cn.sisyphe.coffee.stock.domain.offset.service.StockService;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.framework.web.ResponseResult;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

/**
 * Created by heyong on 2018/1/5 9:53
 * Description: 冲减服务
 *
 * @author heyong
 */
public class OffsetService {

    /**
     * 待处理的单据
     */
    private PendingBill pendingBill;

    /**
     * 单据解析器
     */
    private BillParser billParser;

    /**
     * 冲减相关数据持久化
     */
    private OffsetDataPersistence offsetDataPersistence;


    public OffsetService(BillParser billParser, OffsetDataPersistence offsetDataPersistence) {
        this.billParser = billParser;
        this.billParser.setOffsetService(this);
        this.offsetDataPersistence = offsetDataPersistence;
    }


    /**
     * 冲减 -- 主方法
     *
     * @param responseResult
     */
    public void offset(ResponseResult responseResult) {
        // 解析单据
        pendingBill = billParser.parseBill(responseResult);

        // 预设完成
        pendingBill.setOffset(true);

        for (PendingBillItem pendingBillItem : pendingBill.getPendingBillItemList()) {
            if (pendingBillItem.getOffset()) {
                continue;
            }

            // 冲减单据项
            offsetItem(pendingBillItem);
        }


        if (pendingBill.getOffset()) {
            // 处理完成，清除明细
            pendingBill.getPendingBillItemList().clear();
            offsetDataPersistence.getPendingRepository().save(pendingBill);
        }
    }


    /**
     * 检查库存是否满足
     *
     * @param pendingBillItem
     */
    private void checkStock(PendingBillItem pendingBillItem) {

    }

    /**
     * 获取库存量
     *
     * @param cargo
     * @param station
     * @return
     */
    private int getInventoryAmount(Cargo cargo, Station station) {

        if(station == null || StringUtils.isEmpty(station.getStationCode()) || StringUtils.isEmpty(station.getStorageCode())
                || cargo == null || StringUtils.isEmpty(cargo.getCargoCode())){
            return 0;
        }

        return offsetDataPersistence.getOffsetRepository().getStockByStationAndCargo(station, cargo);
    }

    /**
     * 保存冲减数据
     *
     * @param offsets
     */
    public void save(Iterable<Offset> offsets) {
        offsetDataPersistence.getOffsetRepository().save(offsets);
    }

    /**
     * 根据待冲减明细创建冲减
     * @param offsetting
     * @param station
     * @param amount
     * @param inOutStorage
     * @param inStation
     * @param outStation
     * @param billTypeEnum
     * @return
     */
    public Offset createByOffsetting(Offset offsetting, Station station, int amount, InOutStorage inOutStorage, Station inStation, Station outStation, BillTypeEnum billTypeEnum) {
        Offset offset = new Offset();

        offset.setBatchCode(offsetting.getBatchCode());
        offset.setStation(station);
        offset.setRawMaterial(offsetting.getRawMaterial());
        offset.setCargo(offsetting.getCargo());
        offset.setUnitCost(offsetting.getUnitCost());
        offset.setExpirationTime(offsetting.getExpirationTime());
        offset.setInOutStorage(inOutStorage);


        if (InOutStorage.OUT_STORAGE.equals(inOutStorage)) {
            offset.setSurplusAmount(0);
            // 已冲减数量
            offset.setOffsetAmount(amount);

            // 获取库存量 <在事务内前一操作会被统计下来>
            offset.setInventoryTotalAmount(getInventoryAmount(offsetting.getCargo(), station));
        } else {
            // 获取库存量 <在事务内前一操作会被统计下来>
            offset.setInventoryTotalAmount(getInventoryAmount(offsetting.getCargo(), station) + amount);
            // 可冲减数量
            offset.setSurplusAmount(amount);
            // 已冲减数量
            offset.setOffsetAmount(0);
        }

        // 本次冲减总量
        offset.setTotalOffsetAmount(amount);
        offset.setSourceCode(pendingBill.getBillCode());

        // 显示数据
        offset.setSourceBillType(billTypeEnum);
        offset.setInStation(inStation);
        offset.setOutStation(outStation);


        // 更新库存量
        offsetDataPersistence.getStorageService().updateInventory(station, offset.getCargo(), offset.getRawMaterial(), offset.getInventoryTotalAmount());
        return offset;
    }

    /**
     * 根据待处理明细创建冲减
     * @param detail
     * @param station
     * @param amount
     * @param inOutStorage
     * @param inStation
     * @param outStation
     * @param billTypeEnum
     * @return
     */
    public Offset createByPendingDetail(PendingBillDetail detail, Station station, int amount, InOutStorage inOutStorage, Station inStation, Station outStation, BillTypeEnum billTypeEnum) {
        Offset offset = new Offset();

        // 无来源入库 查询最近未冲减的资料
        if (detail.getCargo() == null || detail.getUnitCost() == null || detail.getExpirationTime() == null) {
            setRecentlyCargoInfo(detail, station);

            if (detail.getCargo() == null){
                throw new DataException("005", "此站点没有此原料的进货史，不能冲减");
            }
        }

        offset.setBatchCode(pendingBill.getBillCode());
        offset.setStation(station);

        offset.setRawMaterial(detail.getRawMaterial());
        offset.setCargo(detail.getCargo());
        offset.setUnitCost(detail.getUnitCost());
        offset.setExpirationTime(detail.getExpirationTime());
        offset.setInOutStorage(inOutStorage);


        // 通过冲减查询库存量
        offset.setInventoryTotalAmount(getInventoryAmount(detail.getCargo(), station) + inOutStorage.getValue() * amount);

        offset.setTotalOffsetAmount(amount);
        if (InOutStorage.OUT_STORAGE.equals(inOutStorage)) {
            offset.setSurplusAmount(0);
            // 已冲减数量
            offset.setOffsetAmount(amount);
        } else {
            // 可冲减数量
            offset.setSurplusAmount(amount);
            // 已冲减数量
            offset.setOffsetAmount(0);
        }
        offset.setSourceCode(pendingBill.getBillCode());

        // 显示数据
        offset.setSourceBillType(billTypeEnum);
        offset.setInStation(inStation);
        offset.setOutStation(outStation);

        // 更新库存量
        offsetDataPersistence.getStorageService().updateInventory(station, offset.getCargo(), offset.getRawMaterial(), offset.getInventoryTotalAmount());
        return offset;
    }

    /**
     * 获取最近的进货史
     *
     * @param detail
     * @param station
     */
    private void setRecentlyCargoInfo(PendingBillDetail detail, Station station) {
        Offset offsetting = offsetDataPersistence.getOffsetRepository().findLastCargoOrRawMaterialHistory(station, detail.getRawMaterial(), detail.getCargo());

        if (offsetting == null) {
            return;
        }

        if (detail.getCargo() == null || StringUtils.isEmpty(detail.getCargo().getCargoCode())) {
            detail.setCargo(offsetting.getCargo());
        }

        if (detail.getUnitCost() == null) {
            detail.setUnitCost(offsetting.getUnitCost());
        }

        if (detail.getExpirationTime() == null) {
            detail.setExpirationTime(offsetting.getExpirationTime());
        }
    }

    /**
     * 冲减 整体粒度
     *
     * @param pendingBillItem
     */
    private void offsetItem(PendingBillItem pendingBillItem) {

        StockService stockService;
        switch (pendingBillItem.getInOutStorage()) {
            case IN_STORAGE:
                stockService = new InStockService(this, pendingBillItem);
                break;
            case OUT_STORAGE:
                stockService = new OutStockService(this, pendingBillItem);
                break;
            case MOVE_STORAGE:
                stockService = new MoveStockService(this, pendingBillItem);
                break;
            default:
                throw new DataException("200005", "冲减初始化失败");
        }


        //事务开始
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(60);
        TransactionStatus status = offsetDataPersistence.getTransactionManager().getTransaction(def);

        try {
            // 检查库存
            checkStock(pendingBillItem);
            // 冲减
            stockService.offset();
            // 已冲减
            pendingBillItem.setOffset(true);

            // 处理成功
            billParser.success(pendingBillItem);

            // 事务提交
            offsetDataPersistence.getTransactionManager().commit(status);

        } catch (Exception e) {
            if (!status.isCompleted()) {
                // 事务回滚
                offsetDataPersistence.getTransactionManager().rollback(status);

                pendingBill.setOffset(false);
                pendingBillItem.setOffset(false);
                billParser.fail(pendingBillItem);

                e.printStackTrace();

            }
        }
    }


    public PendingBill getPendingBill() {
        return pendingBill;
    }

    public void setPendingBill(PendingBill pendingBill) {
        this.pendingBill = pendingBill;
    }

    public BillParser getBillParser() {
        return billParser;
    }

    public void setBillParser(BillParser billParser) {
        this.billParser = billParser;
    }

    public OffsetDataPersistence getOffsetDataPersistence() {
        return offsetDataPersistence;
    }

    public void setOffsetDataPersistence(OffsetDataPersistence offsetDataPersistence) {
        this.offsetDataPersistence = offsetDataPersistence;
    }
}
