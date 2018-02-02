package cn.sisyphe.coffee.stock.infrastructure.shared.cargo;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：货物的SpringCloud调用接口
 * version: 1.0
 *
 * @author XiongJing
 */
@FeignClient(value = "COFFEE-BASEINFO", fallback = LocalCargoCloudRepository.class)
public interface CargoCloudRepository {

    /**
     * 根据货物编码查询货物信息
     *
     * @param cargoCode 货物编码
     * @return
     */
    @RequestMapping(path = "/api/v1/baseInfo/cargo/findDTOByCargoCode", method = RequestMethod.GET)
    ResponseResult findByCargoCode(@RequestParam("cargoCode") String cargoCode);
}
