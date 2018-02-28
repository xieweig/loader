package cn.sisyphe.coffee.stock.domain.offset.parser;

import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.strategy.CargoFirstOffsetStrategy;
import cn.sisyphe.coffee.stock.domain.offset.strategy.OffsetStrategy;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.framework.web.ResponseResult;
import cn.sisyphe.framework.web.exception.DataException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyong on 2018/1/8 12:26
 * Description: 零售单解析器
 *
 * @author heyong
 */
public class SaleParser implements BillParser {

    /**
     * 冲减服务
     */
    private OffsetService offsetService;

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
        if (!resultMap.containsKey("bill")) {
            // TODO: 2018/1/23 MQ中单据解析
            throw new DataException("200006", "没有包含单据信息");
        }
        // 解析MQ中的数据源
        LinkedHashMap resultLinked = (LinkedHashMap) resultMap;
        // 单据编码
        String billCode = resultLinked.get("billCode").toString();

        PendingBill pendingBill = new PendingBill();
        // 设置单据编码
        pendingBill.setBillCode(billCode);

        // 设置待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<PendingBillItem>();
        // 构造待冲减项
        PendingBillItem pendingBillItem = new PendingBillItem();

        // 设置待冲减的物品
        List<PendingBillDetail> pendingBillDetails = new ArrayList<PendingBillDetail>();
        // 构造待冲减的物品
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        LinkedHashMap billDetailsLinked = (LinkedHashMap) resultLinked.get("billDetails");
        billDetailsLinked.get("actualAmount");

        pendingBillDetails.add(pendingBillDetail);

        pendingBillItem.setPendingBillDetailList(pendingBillDetails);


        billItemList.add(pendingBillItem);

        pendingBill.setPendingBillItemList(billItemList);

        return pendingBill;
    }

    /**
     * 成功
     *
     * @param pendingBillItem
     */
    @Override
    public void success(PendingBillItem pendingBillItem) {

    }

    /**
     * 失败
     * @param pendingBillItem
     * @param errorMessage
     */
    @Override
    public void fail(PendingBillItem pendingBillItem, String errorMessage){

    }
}
