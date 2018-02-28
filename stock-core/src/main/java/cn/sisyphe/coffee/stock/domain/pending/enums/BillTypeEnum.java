package cn.sisyphe.coffee.stock.domain.pending.enums;

/**
 * Created by heyong on 2017/12/19 17:22
 * Description: 单据种类(与单据属性合并)
 *
 * @author heyong
 */
public enum BillTypeEnum {
    /**
     * 无计划
     */
    NO_PLAN,
    /**
     * 计划单
     */
    PLAN,
    /**
     * 进货单
     */
    PURCHASE,
    /**
     * 配送单
     */
    DELIVERY,
    /**
     * 调剂单
     */
    ADJUST,
    /**
     * 退库单
     */
    RESTOCK,
    /**
     * 退货单
     */
    RETURNED,

    /**
     * 调拨单
     */
    ALLOT,
    /**
     * 流转误差单
     */
    MISTAKE,

    /**
     * 报溢单
     */
    OVERFLOW_MISTAKE,

    /**
     * 报损单
     */
    LOST_MISTAKE,

    /**
     * 日常误差单
     */
    DAILY_MISTAKE,

    /**
     * 其它出入库单
     */
    IN_OUT_SELF,

    SALE;


    /**
     * 是否是自主拣货
     */
    public static Boolean selfPickGoods(BillTypeEnum billType) {
        return NO_PLAN.equals(billType);
    }
}
