package cn.sisyphe.stockexcel.domain.utils;

import cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum;
import cn.sisyphe.framework.common.utils.StringUtil;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：数据映射帮助类
 * version: 1.0
 *
 * @author XiongJing
 */
public class AnalysisUtils {
    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static String isEmpty(String str) {
        if (!StringUtil.isEmpty(str)) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 映射单据类型
     *
     * @param billType
     * @return
     */
    public static String billTypeNameMap(String billType) {
        String typeStr = "";
        if (BillTypeEnum.NO_PLAN.equals(billType)) {
            typeStr = "无计划";
        } else if (BillTypeEnum.PLAN.equals(billType)) {
            typeStr = "计划单";
        } else if (BillTypeEnum.PURCHASE.equals(billType)) {
            typeStr = "进货单";
        } else if (BillTypeEnum.DELIVERY.equals(billType)) {
            typeStr = "配送单";
        } else if (BillTypeEnum.ADJUST.equals(billType)) {
            typeStr = "调剂单";
        } else if (BillTypeEnum.RESTOCK.equals(billType)) {
            typeStr = "退库单";
        } else if (BillTypeEnum.RETURNED.equals(billType)) {
            typeStr = "退货单";
        } else if (BillTypeEnum.ALLOT.equals(billType)) {
            typeStr = "调拨单";
        } else if (BillTypeEnum.MISTAKE.equals(billType)) {
            typeStr = "流转误差单";
        } else if (BillTypeEnum.OVERFLOW_MISTAKE.equals(billType)) {
            typeStr = "报溢单";
        } else if (BillTypeEnum.LOST_MISTAKE.equals(billType)) {
            typeStr = "报损单";
        } else if (BillTypeEnum.DAILY_MISTAKE.equals(billType)) {
            typeStr = "日常误差单";
        } else if (BillTypeEnum.IN_OUT_SELF.equals(billType)) {
            typeStr = "其它出入库单";
        }
        return typeStr;
    }
}
