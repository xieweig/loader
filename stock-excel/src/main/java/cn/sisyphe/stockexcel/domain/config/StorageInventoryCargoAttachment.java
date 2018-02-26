package cn.sisyphe.stockexcel.domain.config;

import cn.sisyphe.common.excel.config.IExcelAttachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：库存查询-按货物
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageInventoryCargoAttachment implements IExcelAttachment {
    private List<String> fieldList;

    public StorageInventoryCargoAttachment() {
        fieldList = new ArrayList<>();
        fieldList.add("单据时间");
        fieldList.add("站点");
        fieldList.add("库位");
        fieldList.add("货物编码");
        fieldList.add("货物名称");
        fieldList.add("规格");
        fieldList.add("数量");
        fieldList.add("原料名称");
    }

    @Override
    public String getFileName() {
        return "StorageInventoryCargoTemplateOut";
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
        return "库存查询-按货物";
    }
}
