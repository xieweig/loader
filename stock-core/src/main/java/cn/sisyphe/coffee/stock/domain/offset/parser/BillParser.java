package cn.sisyphe.coffee.stock.domain.offset.parser;

import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.strategy.OffsetStrategy;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.framework.web.ResponseResult;

/**
 * Created by heyong on 2018/1/8 14:39
 * Description: 单据解析器
 *
 * @author heyong
 */
public interface BillParser {

    /**
     * 冲减操作上下文
     *
     * @param offsetService
     */
    void setOffsetService(OffsetService offsetService);

    /**
     * 冲减策略
     *
     * @return
     */
    OffsetStrategy getOffsetStrategy();


    /**
     * 是否保存未冲减的单据
     *
     * @return
     */
    Boolean isSaveUnOffsetBill();

    /**
     * 解析单据
     *
     * @param responseResult
     * @return
     */
    PendingBill parseBill(ResponseResult responseResult);


    /**
     * 成功
     * @param pendingBillItem
     */
    void success(PendingBillItem pendingBillItem);

    /**
     * 失败
     * @param pendingBillItem
     */
    void fail(PendingBillItem pendingBillItem);

}
