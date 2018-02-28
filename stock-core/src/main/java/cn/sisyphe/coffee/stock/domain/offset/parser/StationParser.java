package cn.sisyphe.coffee.stock.domain.offset.parser;

import ch.lambdaj.function.closure.Switcher;
import cn.sisyphe.coffee.stock.application.ShareManager;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.strategy.CargoFirstOffsetStrategy;
import cn.sisyphe.coffee.stock.domain.offset.strategy.OffsetStrategy;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.message.strategy.AbstractMessagePurposeStrategy;
import cn.sisyphe.coffee.stock.domain.shared.message.strategy.InStockPurposeStrategyImpl;
import cn.sisyphe.coffee.stock.domain.shared.message.strategy.MoveStockPurposeStrategyImpl;
import cn.sisyphe.coffee.stock.domain.shared.message.strategy.OutStockPurposeStrategyImpl;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.storage.model.Storage;
import cn.sisyphe.framework.web.ResponseResult;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum.DAILY_MISTAKE;
import static cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum.LOST_MISTAKE;
import static cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum.OVERFLOW_MISTAKE;
import static cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum.valueOf;
import static cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage.IN_STORAGE;
import static cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage.MOVE_STORAGE;
import static cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage.OUT_STORAGE;
import static java.util.Arrays.asList;

/**
 * Created by heyong on 2018/1/8 14:54
 * Description: 站内站间单据适配器
 *
 * @author heyong
 */

public class StationParser implements BillParser {

    private static final List<String> BILL_PURPOSE = asList("IN_STORAGE", "OUT_STORAGE", "MOVE_STORAGE");

    private static final List<BillTypeEnum> MISTAKE_BILL_TYPE = asList(OVERFLOW_MISTAKE, LOST_MISTAKE, DAILY_MISTAKE);

    /**
     * 冲减服务
     */
    private OffsetService offsetService;

    private ResponseResult responseResult;

    private ShareManager shareManager;

    /**
     * 冲减操作上下文
     *
     * @param offsetService
     */
    @Override
    public void setOffsetService(OffsetService offsetService) {
        this.offsetService = offsetService;
    }

    public ShareManager getShareManager() {
        return shareManager;
    }

    public void setShareManager(ShareManager shareManager) {
        this.shareManager = shareManager;
    }

    /**
     * 冲减策略
     *
     * @return
     */
    @Override
    public OffsetStrategy getOffsetStrategy() {
        return new CargoFirstOffsetStrategy(offsetService);
    }


    /**
     * 是否保存未冲减的单据
     *
     * @return
     */
    @Override
    public Boolean isSaveUnOffsetBill() {
        return false;
    }

    @Override
    public PendingBill parseBill(ResponseResult responseResult) {
        this.responseResult = responseResult;
        PendingBill pendingBill = new PendingBill();
        Map<String, Object> properties = (Map) responseResult.getResult().get("bill");
        PendingBillItem pendingBillItem = convertToBillItem(properties);
        pendingBill.setBillCode(pendingBillItem.getItemCode());
        pendingBill.setSourceCode(mapSourceCode(properties));
        pendingBill.addPendingBillItem(pendingBillItem);
        return pendingBill;
    }

    /**
     * 成功
     *
     * @param pendingBillItem
     */
    @Override
    public void success(PendingBillItem pendingBillItem) {
        getMessageStrategy(pendingBillItem.getInOutStorage()).send(pendingBillItem, this.responseResult, true);
    }

    /**
     * 失败
     *
     * @param pendingBillItem
     */
    @Override
    public void fail(PendingBillItem pendingBillItem) {
        getMessageStrategy(pendingBillItem.getInOutStorage()).send(pendingBillItem, this.responseResult, false);

    }

    private PendingBillItem convertToBillItem(Map<String, Object> properties) {
        List<JSONObject> billDetails = (List) properties.get("billDetails");
        PendingBillItem pendingBillItem = new PendingBillItem();
        pendingBillItem.setInOutStorage(getInOrOutStorage((String) properties.get("billPurpose")));
        pendingBillItem.setInStation(convertLocation((JSONObject) properties.get("inLocation")));
        pendingBillItem.setOutStation(convertLocation((JSONObject) properties.get("outLocation")));
        pendingBillItem.setItemCode((String) properties.get("billCode"));
        pendingBillItem.setSourceBillType(mapBillType((String) properties.get("billType"), pendingBillItem.getInOutStorage()));
        List<PendingBillDetail> pendingBillDetails = new ArrayList<>();
        for (JSONObject billDetail : billDetails) {
            PendingBillDetail pendingBillDetail = new PendingBillDetail();
            pendingBillDetail.setActualAmount(billDetail.getInteger("actualAmount"));
            pendingBillDetail.setShipAmount(billDetail.getInteger("shippedAmount"));
            pendingBillDetail.setUnitCost(billDetail.getBigDecimal("unitPrice"));
            pendingBillDetail.setExpirationTime(billDetail.getDate("dateInProduced"));
            mapMaterialAndCargo(pendingBillDetail, (JSONObject) billDetail.get("goods"));
            mapTotalAmount(billDetail, pendingBillDetail, pendingBillItem);
            pendingBillDetails.add(pendingBillDetail);
        }
        pendingBillItem.setPendingBillDetailList(pendingBillDetails);
        return pendingBillItem;
    }

    private BillTypeEnum mapBillType(String billType, InOutStorage inOutStorage) {
        if ("MISTAKE".equals(billType)) {
            if (IN_STORAGE.equals(inOutStorage)) {
                return OVERFLOW_MISTAKE;
            }
            if (OUT_STORAGE.equals(inOutStorage)) {
                return LOST_MISTAKE;
            }
            if (MOVE_STORAGE.equals(inOutStorage)) {
                return DAILY_MISTAKE;
            }
        }
        return valueOf(billType);
    }

    private String mapSourceCode(Map<String, Object> properties) {
        if (properties.containsKey("sourceCode")) {
            return (String) properties.get("sourceCode");
        }
        return null;
    }

    private Station convertLocation(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        Station station = new Station();
        if (jsonObject.containsKey("stationCode")) {
            station.setStationCode(jsonObject.getString("stationCode"));
            station.setStationName(getShareManager().findStationNameByStationCode(jsonObject.getString("stationCode")));
        }
        if (jsonObject.containsKey("supplierCode")) {
            station.setStationCode(jsonObject.getString("supplierCode"));
            station.setStationName(getShareManager().findSupplierNameBySupplierCode(jsonObject.getString("supplierCode")));
        }
        if (jsonObject.containsKey("storage")) {
            Storage storage = new Storage();
            storage.setStorageCode(((JSONObject) jsonObject.get("storage")).getString("storageCode"));
            station.setStorage(storage);
        }
        return station;
    }

    private InOutStorage getInOrOutStorage(String billPurpose) {
        if (!BILL_PURPOSE.contains(billPurpose)) {
            throw new RuntimeException("单据目的错误");
        }
        return InOutStorage.valueOf(billPurpose);
    }

    private void mapMaterialAndCargo(PendingBillDetail pendingBillDetail, JSONObject jsonObject) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode(jsonObject.getString("rawMaterialCode"));
        pendingBillDetail.setRawMaterial(rawMaterial);
        if (jsonObject.containsKey("cargo")) {
            Cargo cargo = new Cargo();
            cargo.setCargoCode(((JSONObject) jsonObject.get("cargo")).getString("cargoCode"));
            pendingBillDetail.setCargo(cargo);
        }
    }

    private void mapTotalAmount(JSONObject billDetail, PendingBillDetail pendingBillDetail, PendingBillItem pendingBillItem) {
        if (pendingBillDetail.getCargo() != null && !MISTAKE_BILL_TYPE.contains(pendingBillItem.getSourceBillType())) {
            Cargo cargo = shareManager.findByCargoCode(pendingBillDetail.getCargo().getCargoCode());
            if (cargo != null && cargo.getMeasurement() != null) {
                Integer measurement = cargo.getMeasurement();
                pendingBillDetail.setActualTotalAmount(pendingBillDetail.getActualAmount() * measurement);
                pendingBillDetail.setShipTotalAmount(pendingBillDetail.getShipAmount() * measurement);
                return;
            }
            pendingBillDetail.setActualTotalAmount(pendingBillDetail.getActualAmount());
            pendingBillDetail.setShipTotalAmount(pendingBillDetail.getShipAmount());
            return;
        }
        pendingBillDetail.setActualTotalAmount(billDetail.getInteger("actualTotalAmount"));

        if (((Integer) billDetail.get("shippedAmount")) == 0) {
            // 误差单不复写此值
            if (!MISTAKE_BILL_TYPE.contains(pendingBillItem.getSourceBillType())) {
                pendingBillDetail.setShipTotalAmount(billDetail.getInteger("actualTotalAmount"));
            } else {
                pendingBillDetail.setShipTotalAmount(0);
            }
        }


    }

    private AbstractMessagePurposeStrategy getMessageStrategy(InOutStorage inOutStorage) {
        return new Switcher<AbstractMessagePurposeStrategy>()
                .addCase(IN_STORAGE, new InStockPurposeStrategyImpl())
                .addCase(MOVE_STORAGE, new MoveStockPurposeStrategyImpl())
                .addCase(OUT_STORAGE, new OutStockPurposeStrategyImpl())
                .exec(inOutStorage);
    }
}
