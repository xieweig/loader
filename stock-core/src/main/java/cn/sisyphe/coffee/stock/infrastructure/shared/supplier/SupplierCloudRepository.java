package cn.sisyphe.coffee.stock.infrastructure.shared.supplier;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date 2018/2/5 11:49
 * @description
 */

@FeignClient(value = "COFFEE-BASEINFO", fallback = LocalSupplierCloudRepository.class)
public interface SupplierCloudRepository {

    /**
     * @param supplierCode
     * @return
     */
    @RequestMapping(path = "/api/v1/baseInfo/supplier/findBySupplierCode", method = RequestMethod.GET)
    ResponseResult findSupplierNameBySupplierCode(@RequestParam("supplierCode") String supplierCode);
}
