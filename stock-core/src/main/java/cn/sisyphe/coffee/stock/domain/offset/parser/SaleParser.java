package cn.sisyphe.coffee.stock.domain.offset.parser;

import cn.sisyphe.coffee.stock.application.ShareManager;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.strategy.CargoFirstOffsetStrategy;
import cn.sisyphe.coffee.stock.domain.offset.strategy.OffsetStrategy;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.Constants;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.product.Formula;
import cn.sisyphe.coffee.stock.domain.shared.goods.product.Product;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.framework.message.core.MessagingHelper;
import cn.sisyphe.framework.web.ResponseResult;
import cn.sisyphe.framework.web.exception.DataException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.sisyphe.coffee.stock.domain.shared.Constants.SALE_EXCHANGE;

/**
 * Created by heyong on 2018/1/8 12:26
 * Description: 零售单解析器
 *
 * @author heyong
 */
public class SaleParser implements BillParser {

    /**
     * 自研产品
     */
    private static final String SELF_PRODUCT = "SELF_PRODUCT";

    /**
     * 成品
     */
    private static final String PRODUCT = "PRODUCT";

    /**
     * 套餐
     */
    private static final String MEAL = "MEAL";
    /**
     * 冲减服务
     */
    private OffsetService offsetService;

    private ShareManager shareManager;


    private ResponseResult responseResult;

    /**
     * 冲减操作上下文
     *
     * @param offsetService
     */
    @Override
    public void setOffsetService(OffsetService offsetService) {
        this.offsetService = offsetService;
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
        return true;
    }


    /**
     * 解析零售单据具体信息
     *
     * @param responseResult
     * @return
     */
    @Override
    public PendingBill parseBill(ResponseResult responseResult) {
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("order")) {
            // TODO: 2018/1/23 MQ中单据解析
            throw new DataException("200006", "没有包含订单信息");
        }
        Map<String, Object> order = (Map) resultMap.get("order");
        PendingBill pendingBill = new PendingBill();
        pendingBill.setBillCode((String) order.get("saleOrderCode"));
        List<PendingBillItem> pendingBillItems = convertToBillItems(order);
        pendingBill.addPendingBillItems(pendingBillItems);

        return pendingBill;
    }

    private List<PendingBillItem> convertToBillItems(Map<String, Object> order) {
        List<PendingBillItem> pendingBillItems = new ArrayList<>();
        List<JSONObject> orderDetails = (List) order.get("saleOrderDetailSet");
        for (JSONObject orderDetail : orderDetails) {
            PendingBillItem pendingBillItem = new PendingBillItem();
            pendingBillItem.setItemCode((String) order.get("saleOrderCode"));
            pendingBillItem.setInOutStorage(InOutStorage.OUT_STORAGE);
            pendingBillItem.setOutStation(mapStation((String) order.get("stationCode")));
            List<PendingBillDetail> pendingBillDetails = mapPendBillDetails(orderDetail);
            pendingBillItem.addPendingBillDetails(pendingBillDetails);
            pendingBillItems.add(pendingBillItem);

        }

        return pendingBillItems;
    }

    private Station mapStation(String stationCode) {
        if (stationCode == null) {
            return null;
        }
        Station station = new Station();
        station.setStationCode(stationCode);
        station.setStorageCode("NORMAL");
        station.setStationName(getShareManager().findStationNameByStationCode(stationCode));
        return station;
    }

    private List<PendingBillDetail> mapPendBillDetails(JSONObject orderDetail) {
        List<PendingBillDetail> pendingBillDetails = new ArrayList<>();
        Product product = shareManager.findByProductCode((String) orderDetail.get("productCode"));
        if (SELF_PRODUCT.equals(product.getProductType())) {
            pendingBillDetails.addAll(mapPendingBillDetails(orderDetail, product, 1));
        }
        if (PRODUCT.equals(product.getProductType())) {
            pendingBillDetails.add(mapProductPendingDetail(orderDetail, product, 1));
        }
        if (MEAL.equals(product.getProductType())) {
            for (Formula formula : product.getFormulas()) {
                Product formulaProduct = shareManager.findByProductCode(formula.getRawMaterialCode());
                if (SELF_PRODUCT.equals(formulaProduct.getProductType())) {
                    pendingBillDetails.addAll(mapPendingBillDetails(orderDetail, formulaProduct, formula.getAmount()));
                }
                if (PRODUCT.equals(formulaProduct.getProductType())) {
                    pendingBillDetails.add(mapProductPendingDetail(orderDetail, formulaProduct, formula.getAmount()));
                }
            }
        }
        return pendingBillDetails;
    }

    private PendingBillDetail mapProductPendingDetail(JSONObject orderDetail, Product product, Integer factor) {
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        pendingBillDetail.setActualAmount(orderDetail.getInteger("saleAmount") * factor);
        pendingBillDetail.setActualTotalAmount(orderDetail.getInteger("saleAmount") * factor);
        pendingBillDetail.setCargo(new Cargo(product.getProductCode()));
        pendingBillDetail.setRawMaterial(new RawMaterial(product.getProductCode()));
        return pendingBillDetail;
    }

    private List<PendingBillDetail> mapPendingBillDetails(JSONObject orderDetail, Product product, Integer factor) {
        List<PendingBillDetail> pendingBillDetails = new ArrayList<>();

        for (Formula formula : product.getFormulas()) {
            PendingBillDetail pendingBillDetail = new PendingBillDetail();
            if ("MATERIAL".equals(formula.getFormulaType())) {
                pendingBillDetail.setActualTotalAmount(mapTotalAmount(formula, orderDetail) * factor);
                pendingBillDetail.setActualAmount(orderDetail.getInteger("saleAmount") * factor);
                pendingBillDetail.setRawMaterial(new RawMaterial(formula.getRawMaterialCode()));
                pendingBillDetails.add(pendingBillDetail);
            }
        }
        return pendingBillDetails;
    }


    private Integer mapTotalAmount(Formula formula, JSONObject orderDetail) {
        Integer productAmount = orderDetail.getInteger("saleAmount");
        return formula.getAmount() * productAmount;
    }

    /**
     * 成功
     *
     * @param pendingBillItem
     */
    @Override
    public void success(PendingBillItem pendingBillItem) {
        MessagingHelper.messaging().convertAndSend(SALE_EXCHANGE, Constants.COFFEE_SALE_SUCCESS_KEY, this.responseResult);

    }

    /**
     * 失败
     *
     * @param pendingBillItem
     * @param errorMessage
     */
    @Override
    public void fail(PendingBillItem pendingBillItem, String errorMessage) {
        MessagingHelper.messaging().convertAndSend(SALE_EXCHANGE, Constants.COFFEE_SALE_FAIL_KEY, this.responseResult);

    }

    public ShareManager getShareManager() {
        return shareManager;
    }

    public void setShareManager(ShareManager shareManager) {
        this.shareManager = shareManager;
    }

    public ResponseResult getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }
}
