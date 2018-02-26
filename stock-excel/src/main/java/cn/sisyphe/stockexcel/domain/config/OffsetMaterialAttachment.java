package cn.sisyphe.stockexcel.domain.config;

import cn.sisyphe.common.excel.config.IExcelAttachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：库存流水查询-按原料
 * version: 1.0
 *
 * @author XiongJing
 */
public class OffsetMaterialAttachment implements IExcelAttachment {
    private List<String> fieldList;

    public OffsetMaterialAttachment() {
        fieldList = new ArrayList<>();
        fieldList.add("单据时间");
        fieldList.add("站点");
        fieldList.add("库位");
        fieldList.add("原料编码");
        fieldList.add("原料名称");
        fieldList.add("变化量");
        fieldList.add("单据类型");
        fieldList.add("单号");
        fieldList.add("出库站点");
        fieldList.add("出库库位");
        fieldList.add("入库站点");
        fieldList.add("入库库位");
    }

    @Override
    public String getFileName() {
        return "OffsetMaterialTemplateOut";
    }

    @Override
    public List<String> getFieldList() {
        return fieldList;
    }

    @Override
    public void setSheetStyle() {

    }

    @Override
    public String getSheetName() {
        return "库存流水查询-按原料";
    }
}
