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
     * 计划
     */
    PLAN,
    /**
     * 进货
     */
    PURCHASE,
    /**
     * 配送
     */
    DELIVERY,
    /**
     * 调剂
     */
    ADJUST,
    /**
     * 退库
     */
    RESTOCK,
    /**
     * 退货
     */
    RETURNED,

    /**
     * 调拨
     */
    ALLOT,
    /**
     * 流转误差
     */
    MISTAKE,

    /**
     * 其它出入库
     */
    IN_OUT_SELF;


    /**
     * 是否是自主拣货
     */
    public static Boolean selfPickGoods(BillTypeEnum billType) {
        return NO_PLAN.equals(billType);
    }
}
