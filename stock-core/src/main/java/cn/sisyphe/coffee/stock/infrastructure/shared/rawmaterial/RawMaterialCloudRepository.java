package cn.sisyphe.coffee.stock.infrastructure.shared.rawmaterial;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：原料的SpringCloud调用接口
 * version: 1.0
 *
 * @author XiongJing
 */
@FeignClient(value = "COFFEE-BASEINFO", fallback = LocalRawMaterialCloudRepository.class)
public interface RawMaterialCloudRepository {

    /**
     * 根据原料编码查询原料信息
     *
     * @param materialCode 原料编码
     * @return
     */
    @RequestMapping(path = "/api/v1/baseInfo/rawMaterial/findByMaterialCode", method = RequestMethod.GET)
    ResponseResult findByMaterialCode(@RequestParam("materialCode") String materialCode);

    /**
     * 根据多个原料分类编码查询原料编码信息
     *
     * @param materialTypeArray
     * @return
     */
    @RequestMapping(path = "/api/v1/baseInfo/rawMaterial/findByMaterialTypeCodes", method = RequestMethod.POST)
    ResponseResult findByMaterialTypeCodes(@RequestBody List<Long> materialTypeArray);
}
