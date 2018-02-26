package cn.sisyphe.stockexcel.domain.impl;

import cn.sisyphe.coffee.stock.viewmodel.StorageQueryDTO;
import cn.sisyphe.common.excel.base.ExcelRowProcess;

import static cn.sisyphe.stockexcel.domain.utils.AnalysisUtils.isEmpty;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：实时库存查询-按货物-数据一一对应
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageInventoryCargoDataItem extends ExcelRowProcess {

    public StorageInventoryCargoDataItem() {
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
        // 货物编码
        addExcelRowFieldData(isEmpty(storageQueryDTO.getCargoCode()));
        // 货物名称
        addExcelRowFieldData(isEmpty(storageQueryDTO.getCargoName()));
        // 规格
        addExcelRowFieldData(isEmpty(storageQueryDTO.getStandardName()));
        // 数量
        addExcelRowFieldData(isEmpty(storageQueryDTO.getNumber()));
        // 原料名称
        addExcelRowFieldData(isEmpty(storageQueryDTO.getMaterialName()));
    }
}
