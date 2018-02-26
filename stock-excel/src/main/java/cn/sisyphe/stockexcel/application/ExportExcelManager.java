package cn.sisyphe.stockexcel.application;

import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.common.asyncTask.enums.AsyncStatusValue;
import cn.sisyphe.common.asyncTask.model.AsyncTaskResult;
import cn.sisyphe.common.exception.DataException;
import cn.sisyphe.common.util.StringUtil;
import cn.sisyphe.framework.common.utils.FileUtil;
import cn.sisyphe.framework.common.utils.date.DateUtil;
import cn.sisyphe.stockexcel.domain.impl.OffsetCargoData;
import cn.sisyphe.stockexcel.domain.impl.OffsetMaterialData;
import cn.sisyphe.stockexcel.domain.impl.StorageInventoryCargoData;
import cn.sisyphe.stockexcel.domain.impl.StorageInventoryMaterialData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * Created by XiongJing on 2018/2/23.
 * remark：导出服务管理层
 * version: 1.0
 *
 * @author XiongJing
 */
@Service
public class ExportExcelManager {
    @Autowired
    private OffsetCargoData offsetCargoData;
    @Autowired
    private OffsetMaterialData offsetMaterialData;
    @Autowired
    private StorageInventoryCargoData storageInventoryCargoData;
    @Autowired
    private StorageInventoryMaterialData storageInventoryMaterialData;

    @Autowired
    private AsyncTask asyncTask;

    private AsyncTaskResult asyncTaskResult;
    /**
     * 文件库
     */
    @Value(value = "${uplod.file.url}")
    private String uploadFileUrl;
    /**
     * 本地文件地址
     */
    @Value(value = "${down.load.path}")
    private String localUrl;

    /**
     * 导出库存流水按货物
     *
     * @param conditionQueryStorage
     * @param userCode
     * @param currentStationCode
     * @return
     * @throws Exception
     */
    public AsyncTaskResult exportOffsetCargo(ConditionQueryStorage conditionQueryStorage, String userCode, String currentStationCode) throws Exception {
        String excelUniqueIdentification = conditionQueryStorage.getExcelUniqueIdentification();
        if (asyncTaskResult == null) {
            //表示当前没有人在导出
            //则新建任务
            offsetCargoData.setCondition(conditionQueryStorage);
            excelUniqueIdentification = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss") + userCode + currentStationCode;
            Future<Object> future = asyncTask.doExportOffsetCargoTask(offsetCargoData, excelUniqueIdentification);
            if (future != null) {
                asyncTaskResult = new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Working, future);
            }
            return asyncTaskResult;
        } else {
            //任务存在时
            return obtainTaskInfo(excelUniqueIdentification);
        }
    }

    /**
     * 导出库存流水按原料
     *
     * @param conditionQueryStorage
     * @param userCode
     * @param currentStationCode
     * @return
     * @throws Exception
     */
    public AsyncTaskResult exportOffsetMaterial(ConditionQueryStorage conditionQueryStorage, String userCode, String currentStationCode) throws Exception {
        String excelUniqueIdentification = conditionQueryStorage.getExcelUniqueIdentification();
        if (asyncTaskResult == null) {
            //表示当前没有人在导出
            //则新建任务
            offsetMaterialData.setCondition(conditionQueryStorage);
            excelUniqueIdentification = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss") + userCode + currentStationCode;
            Future<Object> future = asyncTask.doExportOffsetMaterialTask(offsetMaterialData, excelUniqueIdentification);
            if (future != null) {
                asyncTaskResult = new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Working, future);
            }
            return asyncTaskResult;
        } else {
            //任务存在时
            return obtainTaskInfo(excelUniqueIdentification);
        }
    }

    /**
     * 导出实时库存按货物
     *
     * @param conditionQueryStorage
     * @param userCode
     * @param currentStationCode
     * @return
     * @throws Exception
     */
    public AsyncTaskResult exportStorageInventoryCargo(ConditionQueryStorage conditionQueryStorage, String userCode, String currentStationCode) throws Exception {
        String excelUniqueIdentification = conditionQueryStorage.getExcelUniqueIdentification();
        if (asyncTaskResult == null) {
            //表示当前没有人在导出
            //则新建任务
            storageInventoryCargoData.setCondition(conditionQueryStorage);
            excelUniqueIdentification = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss") + userCode + currentStationCode;
            Future<Object> future = asyncTask.doExportStorageInventoryCargoTask(storageInventoryCargoData, excelUniqueIdentification);
            if (future != null) {
                asyncTaskResult = new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Working, future);
            }
            return asyncTaskResult;
        } else {
            //任务存在时
            return obtainTaskInfo(excelUniqueIdentification);
        }
    }

    /**
     * 导出实时库存按原料
     *
     * @param conditionQueryStorage
     * @param userCode
     * @param currentStationCode
     * @return
     * @throws Exception
     */
    public AsyncTaskResult exportStorageInventoryMaterial(ConditionQueryStorage conditionQueryStorage, String userCode, String currentStationCode) throws Exception {
        String excelUniqueIdentification = conditionQueryStorage.getExcelUniqueIdentification();
        if (asyncTaskResult == null) {
            //表示当前没有人在导出
            //则新建任务
            storageInventoryMaterialData.setCondition(conditionQueryStorage);
            excelUniqueIdentification = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss") + userCode + currentStationCode;
            Future<Object> future = asyncTask.doExportStorageInventoryMaterialTask(storageInventoryMaterialData, excelUniqueIdentification);
            if (future != null) {
                asyncTaskResult = new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Working, future);
            }
            return asyncTaskResult;
        } else {
            //任务存在时
            return obtainTaskInfo(excelUniqueIdentification);
        }
    }


    /**
     * 判断当前是否存在正在进行中的导出任务
     *
     * @param excelUniqueIdentification 唯一编码
     * @return
     * @throws Exception
     */
    private AsyncTaskResult obtainTaskInfo(String excelUniqueIdentification) throws Exception {
        //判断当前导出任务是否为当前操作人的
        if (asyncTaskResult.getUniqueIdentification().equals(excelUniqueIdentification)) {
            //判断是否完成
            if (asyncTaskResult.getFuture().isDone()) {
                String filePath = (String) asyncTaskResult.getFuture().get();
                File tempFile = new File(filePath.trim());
                String fileName = tempFile.getName();
                AsyncTaskResult asyncResult = new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Finish, asyncTaskResult.getFuture(), fileName);
                String resultStr = FileUtil.uploadFile(uploadFileUrl, localUrl + filePath, fileName);
                asyncTaskResult = null;
                if (!"success".equals(resultStr)) {
                    throw new DataException("将导出的文件放至文件库失败！");
                }
                return asyncResult;
            } else {
                return new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Working);
            }
        } else {
            //把已完成的或者错误的任务清空
            if (asyncTaskResult.getFuture().isDone() || StringUtil.isEmpty(asyncTaskResult.getUniqueIdentification())) {
                asyncTaskResult = null;
            }
            //当前有其他人正在在导出
            return new AsyncTaskResult(excelUniqueIdentification, AsyncStatusValue.Locked);
        }
    }
}
