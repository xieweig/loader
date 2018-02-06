package cn.sisyphe.coffee.stock.controller;

import cn.sisyphe.coffee.stock.application.StorageQueryManager;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.coffee.stock.viewmodel.StorageDTO;
import cn.sisyphe.framework.web.ResponseResult;
import cn.sisyphe.framework.web.exception.DataException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XiongJing on 2018/2/1.
 * remark：
 * version:
 */
@RestController
@RequestMapping("/stock/storage")
@Api(description = "库存查询相关接口")
@CrossOrigin(origins = "*")
public class StorageQueryController {
    @Autowired
    private StorageQueryManager storageQueryManager;

    /**
     * 查询最新库存信息
     *
     * @param conditionQuery
     * @return
     */
    @ApiOperation(value = "查询最新库存信息")
    @RequestMapping(path = "/findNowByCondition", method = RequestMethod.POST)
    public ResponseResult findNowByCondition(@RequestBody ConditionQueryStorage conditionQuery) {
        ResponseResult responseResult = new ResponseResult();
        try {
            StorageDTO nowByCondition = storageQueryManager.findNowByCondition(conditionQuery);
            responseResult.put("result", nowByCondition);
        } catch (DataException e) {
            responseResult.putException(e);
        }
        return responseResult;
    }

    /**
     * 查询历史库存信息
     *
     * @param conditionQuery
     * @return
     */
    @ApiOperation(value = "查询历史库存信息")
    @RequestMapping(path = "/findOldByCondition", method = RequestMethod.POST)
    public ResponseResult findOldByCondition(@RequestBody ConditionQueryStorage conditionQuery) {
        ResponseResult responseResult = new ResponseResult();
        try {
            responseResult.put("result", storageQueryManager.findOldByCondition(conditionQuery));
        } catch (DataException e) {
            responseResult.putException(e);
        }
        return responseResult;
    }

}
