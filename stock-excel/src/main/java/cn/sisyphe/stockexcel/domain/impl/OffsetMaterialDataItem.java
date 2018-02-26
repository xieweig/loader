package cn.sisyphe.stockexcel.domain.impl;

import cn.sisyphe.coffee.stock.viewmodel.StorageQueryDTO;
import cn.sisyphe.common.excel.base.ExcelRowProcess;

import static cn.sisyphe.stockexcel.domain.utils.AnalysisUtils.billTypeNameMap;
import static cn.sisyphe.stockexcel.domain.utils.AnalysisUtils.isEmpty;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：库存流水查询-按原料-数据一一对应
 * version: 1.0
 *
 * @author XiongJing
 */
public class OffsetMaterialDataItem extends ExcelRowProcess {

    public OffsetMaterialDataItem() {
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
        // 变化量
        addExcelRowFieldData(isEmpty(storageQueryDTO.getChangeNumber()));
        // 单据类型
        addExcelRowFieldData(isEmpty(billTypeNameMap(storageQueryDTO.getBillType())));
        // 单号
        addExcelRowFieldData(isEmpty(storageQueryDTO.getBillCode()));
        // 出库站点
        addExcelRowFieldData(isEmpty(storageQueryDTO.getOutStationName()));
        // 出库库位 // TODO: 2018/2/24 需要另写方法显示库位中文名称--SpringCloud方式去查询库位名称
        addExcelRowFieldData(isEmpty(storageQueryDTO.getOutStorageCode()));
        // 入库站点
        addExcelRowFieldData(isEmpty(storageQueryDTO.getInStationName()));
        // 入库库位 // TODO: 2018/2/24 需要另写方法显示库位中文名称--SpringCloud方式去查询库位名称
        addExcelRowFieldData(isEmpty(storageQueryDTO.getInStorageCode()));
    }
}
