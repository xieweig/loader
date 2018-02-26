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
            return "无";
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
        if (BillTypeEnum.NO_PLAN.name().equals(billType)) {
            typeStr = "无计划";
        } else if (BillTypeEnum.PLAN.name().equals(billType)) {
            typeStr = "计划单";
        } else if (BillTypeEnum.PURCHASE.name().equals(billType)) {
            typeStr = "进货单";
        } else if (BillTypeEnum.DELIVERY.name().equals(billType)) {
            typeStr = "配送单";
        } else if (BillTypeEnum.ADJUST.name().equals(billType)) {
            typeStr = "调剂单";
        } else if (BillTypeEnum.RESTOCK.name().equals(billType)) {
            typeStr = "退库单";
        } else if (BillTypeEnum.RETURNED.name().equals(billType)) {
            typeStr = "退货单";
        } else if (BillTypeEnum.ALLOT.name().equals(billType)) {
            typeStr = "调拨单";
        } else if (BillTypeEnum.MISTAKE.name().equals(billType)) {
            typeStr = "流转误差单";
        } else if (BillTypeEnum.OVERFLOW_MISTAKE.name().equals(billType)) {
            typeStr = "报溢单";
        } else if (BillTypeEnum.LOST_MISTAKE.name().equals(billType)) {
            typeStr = "报损单";
        } else if (BillTypeEnum.DAILY_MISTAKE.name().equals(billType)) {
            typeStr = "日常误差单";
        } else if (BillTypeEnum.IN_OUT_SELF.name().equals(billType)) {
            typeStr = "其它出入库单";
        }
        return typeStr;
    }
}
