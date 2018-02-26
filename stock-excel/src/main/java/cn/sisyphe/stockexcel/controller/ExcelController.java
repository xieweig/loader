package cn.sisyphe.stockexcel.controller;

import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.common.ResponseResult;
import cn.sisyphe.stockexcel.application.ExportExcelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：库存导出服务对外接口
 * version: 1.0
 *
 * @author XiongJing
 */
@RestController
@RequestMapping("/stock/excel/export")
@CrossOrigin(origins = "*")
public class ExcelController {
    @Autowired
    private ExportExcelManager exportExcelManager;

    /**
     * 导出库存流水-按货物查询
     *
     * @param conditionQueryStorage
     * @param request
     * @return
     */
//    @ScopeAuth(scopes = {"#conditionQueryStorage.stationCodeArray"},token = "userCode")
    @RequestMapping(path = "/exportOffsetCargo", method = RequestMethod.POST)
    public ResponseResult exportOffsetCargo(@RequestBody ConditionQueryStorage conditionQueryStorage, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();
        try {
//            LoginInfo loginInfo = LoginInfo.getLoginInfo(request);
            responseResult.put("asyncTaskResult", exportExcelManager.exportOffsetCargo(conditionQueryStorage, "OperatorCode", "StationCode"));
        } catch (Exception e) {
            responseResult.putException(e);
        }
        return responseResult;
    }

    /**
     * 导出库存流水-按货物查询
     *
     * @param conditionQueryStorage
     * @param request
     * @return
     */
//    @ScopeAuth(scopes = {"#conditionQueryStationMinimum.stationCodeArray"},token = "userCode")
    @RequestMapping(path = "/exportOffsetMaterial", method = RequestMethod.POST)
    public ResponseResult exportOffsetMaterial(@RequestBody ConditionQueryStorage conditionQueryStorage, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();
        try {
//            LoginInfo loginInfo = LoginInfo.getLoginInfo(request);
            responseResult.put("asyncTaskResult", exportExcelManager.exportOffsetMaterial(conditionQueryStorage, "OperatorCode", "StationCode"));
        } catch (Exception e) {
            responseResult.putException(e);
        }
        return responseResult;
    }


    /**
     * 导出实时库存-按货物查询
     *
     * @param conditionQueryStorage
     * @param request
     * @return
     */
//    @ScopeAuth(scopes = {"#conditionQueryStorage.stationCodeArray"},token = "userCode")
    @RequestMapping(path = "/exportStorageInventoryCargo", method = RequestMethod.POST)
    public ResponseResult exportStorageInventoryCargo(@RequestBody ConditionQueryStorage conditionQueryStorage, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();
        try {
//            LoginInfo loginInfo = LoginInfo.getLoginInfo(request);
            responseResult.put("asyncTaskResult", exportExcelManager.exportStorageInventoryCargo(conditionQueryStorage, "OperatorCode", "StationCode"));
        } catch (Exception e) {
            responseResult.putException(e);
        }
        return responseResult;
    }

    /**
     * 导出实时库存-按货物查询
     *
     * @param conditionQueryStorage
     * @param request
     * @return
     */
//    @ScopeAuth(scopes = {"#conditionQueryStorage.stationCodeArray"},token = "userCode")
    @RequestMapping(path = "/exportStorageInventoryMaterial", method = RequestMethod.POST)
    public ResponseResult exportStorageInventoryMaterial(@RequestBody ConditionQueryStorage conditionQueryStorage, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();
        try {
//            LoginInfo loginInfo = LoginInfo.getLoginInfo(request);
            responseResult.put("asyncTaskResult", exportExcelManager.exportStorageInventoryMaterial(conditionQueryStorage, "OperatorCode", "StationCode"));
        } catch (Exception e) {
            responseResult.putException(e);
        }
        return responseResult;
    }
}
