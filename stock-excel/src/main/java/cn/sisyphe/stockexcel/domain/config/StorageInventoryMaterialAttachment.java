package cn.sisyphe.stockexcel.domain.config;

import cn.sisyphe.common.excel.config.IExcelAttachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：库存查询-按原料
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageInventoryMaterialAttachment implements IExcelAttachment {
    private List<String> fieldList;

    public StorageInventoryMaterialAttachment() {
        fieldList = new ArrayList<>();
        fieldList.add("查询时间");
        fieldList.add("站点");
        fieldList.add("库位");
        fieldList.add("原料编码");
        fieldList.add("原料名称");
        fieldList.add("数量");
    }

    @Override
    public String getFileName() {
        return "StorageInventoryMaterialTemplateOut";
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
        return "库存查询-按原料";
    }
}
