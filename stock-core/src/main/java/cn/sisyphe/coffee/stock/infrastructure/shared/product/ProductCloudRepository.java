package cn.sisyphe.coffee.stock.infrastructure.shared.product;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：产品的SpringCloud调用接口
 * version: 1.0
 *
 * @author XiongJing
 */
@FeignClient(value = "COFFEE-BASEINFO", fallback = LocalProductCloudRepository.class)
public interface ProductCloudRepository {
    /**
     * 根据产品编码查询产品信息
     *
     * @param productCode 产品编码
     * @return
     */
    @RequestMapping(path = "/api/v1/baseInfo/products/findByProductCode", method = RequestMethod.GET)
    ResponseResult findByProductCode(@RequestParam(value = "productCode") String productCode);
}
