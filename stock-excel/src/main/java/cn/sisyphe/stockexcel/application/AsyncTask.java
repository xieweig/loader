package cn.sisyphe.stockexcel.application;

import cn.sisyphe.common.excel.ExcelManager;
import cn.sisyphe.stockexcel.domain.config.OffsetCargoAttachment;
import cn.sisyphe.stockexcel.domain.config.OffsetMaterialAttachment;
import cn.sisyphe.stockexcel.domain.config.StorageInventoryCargoAttachment;
import cn.sisyphe.stockexcel.domain.config.StorageInventoryMaterialAttachment;
import cn.sisyphe.stockexcel.domain.impl.OffsetCargoData;
import cn.sisyphe.stockexcel.domain.impl.OffsetCargoDataItem;
import cn.sisyphe.stockexcel.domain.impl.OffsetMaterialData;
import cn.sisyphe.stockexcel.domain.impl.OffsetMaterialDataItem;
import cn.sisyphe.stockexcel.domain.impl.StorageInventoryCargoData;
import cn.sisyphe.stockexcel.domain.impl.StorageInventoryCargoDataItem;
import cn.sisyphe.stockexcel.domain.impl.StorageInventoryMaterialData;
import cn.sisyphe.stockexcel.domain.impl.StorageInventoryMaterialDataItem;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Created by XiongJing on 2018/2/23.
 * remark：异步线程处理方法区
 * version: 1.0
 *
 * @author XiongJing
 */
@Component
public class AsyncTask {

    /**
     * 库存流水-按货物查询
     *
     * @param offsetCargoData
     * @param excelUniqueIdentification
     * @return
     * @throws Exception
     */
    @Async
    public Future<Object> doExportOffsetCargoTask(OffsetCargoData offsetCargoData, String excelUniqueIdentification) throws Exception {
        ExcelManager.instance().appendExcelSheetData(offsetCargoData, new OffsetCargoAttachment(), new OffsetCargoDataItem(), excelUniqueIdentification);
        String downPathStr = ExcelManager.instance().doExcelBuild();
        System.gc();
        return new AsyncResult<>(downPathStr);
    }

    /**
     * 库存流水-按原料查询
     *
     * @param offsetMaterialData
     * @param excelUniqueIdentification
     * @return
     * @throws Exception
     */
    @Async
    public Future<Object> doExportOffsetMaterialTask(OffsetMaterialData offsetMaterialData, String excelUniqueIdentification) throws Exception {
        ExcelManager.instance().appendExcelSheetData(offsetMaterialData, new OffsetMaterialAttachment(), new OffsetMaterialDataItem(), excelUniqueIdentification);
        String downPathStr = ExcelManager.instance().doExcelBuild();
        System.gc();
        return new AsyncResult<>(downPathStr);
    }

    /**
     * 实时库存-按货物查询
     *
     * @param inventoryCargoData
     * @param excelUniqueIdentification
     * @return
     * @throws Exception
     */
    @Async
    public Future<Object> doExportStorageInventoryCargoTask(StorageInventoryCargoData inventoryCargoData, String excelUniqueIdentification) throws Exception {
        ExcelManager.instance().appendExcelSheetData(inventoryCargoData, new StorageInventoryCargoAttachment(), new StorageInventoryCargoDataItem(), excelUniqueIdentification);
        String downPathStr = ExcelManager.instance().doExcelBuild();
        System.gc();
        return new AsyncResult<>(downPathStr);
    }

    /**
     * 实时库存-按原料查询
     *
     * @param inventoryMaterialData
     * @param excelUniqueIdentification
     * @return
     * @throws Exception
     */
    @Async
    public Future<Object> doExportStorageInventoryMaterialTask(StorageInventoryMaterialData inventoryMaterialData, String excelUniqueIdentification) throws Exception {
        ExcelManager.instance().appendExcelSheetData(inventoryMaterialData, new StorageInventoryMaterialAttachment(), new StorageInventoryMaterialDataItem(), excelUniqueIdentification);
        String downPathStr = ExcelManager.instance().doExcelBuild();
        System.gc();
        return new AsyncResult<>(downPathStr);
    }
}
