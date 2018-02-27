package cn.sisyphe.coffee.stock.domain.offset.service;

import cn.sisyphe.coffee.stock.configuration.StorageConfiguration;
import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.utils.SMath;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by heyong on 2018/1/25 15:03
 * Description: 移库冲减服务
 *
 * @author heyong
 */
public class MoveStockService implements StockService {

    /**
     * 冲减服务
     */
    private OffsetService offsetService;
    /**
     * 待冲减项
     */
    private PendingBillItem pendingBillItem;

    public MoveStockService(OffsetService offsetService, PendingBillItem pendingBillItem) {
        this.offsetService = offsetService;
        this.pendingBillItem = pendingBillItem;
    }


    /**
     * 冲减
     */
    @Override
    public void offset() {
        checkParams();

        for (PendingBillDetail detail : pendingBillItem.getPendingBillDetailList()) {
            if (detail.getOffset() != null && detail.getOffset()) {
                continue;
            }

            offsetDetail(detail);
        }
    }

    /**
     * 参数检查
     */
    private void checkParams() {
        if (pendingBillItem == null ||
                pendingBillItem.getOutStation() == null ||
                StringUtils.isEmpty(pendingBillItem.getOutStation().getStationCode()) ||
                StringUtils.isEmpty(pendingBillItem.getOutStation().getStorageCode()) ||
                pendingBillItem.getInStation() == null ||
                StringUtils.isEmpty(pendingBillItem.getInStation().getStationCode()) ||
                StringUtils.isEmpty(pendingBillItem.getInStation().getStorageCode())) {

            throw new DataException("200000", "参数不完整", null, pendingBillItem);
        }
    }

    /**
     * 冲减明细
     *
     * @param detail
     */
    private void offsetDetail(PendingBillDetail detail) {

        if (detail.getActualTotalAmount() == null || detail.getShipTotalAmount() == null) {
            throw new DataException("200001", "没有应拣/实拣数量");
        } else if (detail.getRawMaterial() == null || StringUtils.isEmpty(detail.getRawMaterial().getRawMaterialCode())) {
            throw new DataException("200002", "原料信息不完整");
        }


        // 可共同冲减数量
        int amount = SMath.min(detail.getShipTotalAmount(), detail.getActualTotalAmount());

        if (amount > 0) {
            inAndOutOffset(detail, amount);
        }

        if (detail.getActualTotalAmount() > 0) {
            // 实拣多 调入多
            inOffset(detail);

        } else if (detail.getShipTotalAmount() > 0) {
            // 应拣多 调出多
            outOffset(detail);
        }
    }

    /**
     * 出库冲减
     *
     * @param detail
     */
    private void outOffset(PendingBillDetail detail) {
        // 获取可冲减的货物
        Offset offsetting = offsetService.getBillParser().getOffsetStrategy().getOffsetting(pendingBillItem.getOutStation(), detail.getRawMaterial(), detail.getCargo());

        if (offsetting == null || offsetting.getSurplusAmount() <= 0) {
            // 没有可冲减的物品
            throw new DataException("200003", "没有可冲减的物品");
        }

        // 应拣数量
        int oAmount = SMath.min(detail.getShipTotalAmount(), offsetting.getSurplusAmount());
        // 剩余未冲减数量
        offsetting.setSurplusAmount(offsetting.getSurplusAmount() - oAmount);
        // 已冲减数量
        offsetting.setOffsetAmount(offsetting.getOffsetAmount() + oAmount);
        // 剩余未冲减数量
        detail.setShipTotalAmount(detail.getShipTotalAmount() - oAmount);
        // 保存冲减数据
        offsetService.save(createOutAndMistakeOffset(offsetting, oAmount));

        if (detail.getShipTotalAmount() > 0) {
            outOffset(detail);
        }
    }

    /**
     * 入库冲减
     *
     * @param detail
     */
    private void inOffset(PendingBillDetail detail) {
        offsetService.save(createInAndMistakeOffset(detail, detail.getActualTotalAmount()));

    }


    /**
     * 同时冲减出入库
     *
     * @param detail
     * @param amount
     */
    private void inAndOutOffset(PendingBillDetail detail, int amount) {
        // 获取可冲减的货物
        Offset offsetting = offsetService.getBillParser().getOffsetStrategy().getOffsetting(pendingBillItem.getOutStation(), detail.getRawMaterial(), detail.getCargo());

        if (offsetting == null || offsetting.getSurplusAmount() <= 0) {
            // 没有可冲减的物品
            throw new DataException("200004", "没有可冲减的物品");
        }

        // 应拣数量
        int oAmount = SMath.min(detail.getShipTotalAmount(), detail.getActualTotalAmount(), offsetting.getSurplusAmount(), amount);


        // 剩余未冲减数量
        offsetting.setSurplusAmount(offsetting.getSurplusAmount() - oAmount);
        // 已冲减数量
        offsetting.setOffsetAmount(offsetting.getOffsetAmount() + oAmount);
        // 剩余未冲减数量
        detail.setActualTotalAmount(detail.getActualTotalAmount() - oAmount);
        detail.setShipTotalAmount(detail.getShipTotalAmount() - oAmount);
        amount = amount - oAmount;

        // 保存冲减数据
        offsetService.save(createInAndOutOffset(offsetting, oAmount));

        if (amount > 0) {
            inAndOutOffset(detail, amount);
        }
    }

    /**
     * 入库和误差冲减
     *
     * @param detail
     * @param amount
     * @return
     */
    private Set<Offset> createInAndMistakeOffset(PendingBillDetail detail, int amount) {
        Set<Offset> offsetList = new LinkedHashSet<>();

        // 入库
        offsetList.add(offsetService.createByPendingDetail(detail, pendingBillItem.getInStation(), detail.getActualTotalAmount(), InOutStorage.IN_STORAGE
                , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));

        // 误差库
        Station station = new Station();
        station.setStationCode(pendingBillItem.getInStation().getStationCode());
        station.setStorageCode(StorageConfiguration.MISTAKE_STORAGE);

        // 误差冲减
        offsetList.add(offsetService.createByPendingDetail(detail, station, -amount, InOutStorage.IN_STORAGE
                , station, pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));

        return offsetList;
    }


    /**
     * 出库和误差冲减
     *
     * @param offsetting
     * @param amount
     * @return
     */
    private Set<Offset> createOutAndMistakeOffset(Offset offsetting, int amount) {
        Set<Offset> offsetList = new LinkedHashSet<>();

        // 更新已冲减数据
        offsetList.add(offsetting);
        offsetList.add(offsetService.createByOffsetting(offsetting, pendingBillItem.getOutStation(), amount, InOutStorage.OUT_STORAGE
                , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));

        // 误差库
        Station station = new Station();
        station.setStationCode(pendingBillItem.getInStation().getStationCode());
        station.setStorageCode(StorageConfiguration.MISTAKE_STORAGE);

        // 误差冲减数据
        offsetList.add(offsetService.createByOffsetting(offsetting, station, amount, InOutStorage.IN_STORAGE
                , station, pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));

        return offsetList;
    }

    /**
     * 入库和出库冲减
     *
     * @param offsetting
     * @param amount
     */
    private Set<Offset> createInAndOutOffset(Offset offsetting, int amount) {
        Set<Offset> offsetList = new LinkedHashSet<>();

        // 待冲减数据
        offsetList.add(offsetting);
        // 出库
        offsetList.add(offsetService.createByOffsetting(offsetting, pendingBillItem.getOutStation(), amount, InOutStorage.OUT_STORAGE
                , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));
        // 入库
        offsetList.add(offsetService.createByOffsetting(offsetting, pendingBillItem.getInStation(), amount, InOutStorage.IN_STORAGE
                , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));

        return offsetList;
    }


}
