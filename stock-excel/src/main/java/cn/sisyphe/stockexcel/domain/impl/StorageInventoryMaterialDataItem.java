package cn.sisyphe.stockexcel.domain.impl;

import cn.sisyphe.coffee.stock.viewmodel.StorageQueryDTO;
import cn.sisyphe.common.excel.base.ExcelRowProcess;

import static cn.sisyphe.stockexcel.domain.utils.AnalysisUtils.isEmpty;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：实时库存查询-按原料-数据一一对应
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageInventoryMaterialDataItem extends ExcelRowProcess {

    public StorageInventoryMaterialDataItem() {
    }

    @Override
    public void doProcessRowData() {
        StorageQueryDTO storageQueryDTO = (StorageQueryDTO) getDataSourceObj();
        // 单据时间
        addExcelRowFieldData(isEmpty(storageQueryDTO.getStopTime().toString()));
        // 站点
        addExcelRowFieldData(isEmpty(storageQueryDTO.getStationName()));
        // 库位 // TODO: 2018/2/24 需要另写方法显示库位中文名称--SpringCloud方式去查询库位名称
        addExcelRowFieldData(isEmpty(storageQueryDTO.getStorageName()));
        // 原料编码
        addExcelRowFieldData(isEmpty(storageQueryDTO.getMaterialCode()));
        // 原料名称
        addExcelRowFieldData(isEmpty(storageQueryDTO.getMaterialName()));
        // 数量
        addExcelRowFieldData(isEmpty(storageQueryDTO.getNumber()));
    }
}
