package cn.sisyphe.coffee.stock.infrastructure.shared.station;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by XiongJing on 2018/1/31.
 * remark：站点SpringCloud接口调用
 * version: 1.0
 *
 * @author XiongJing
 */
@FeignClient(value = "COFFEE-BASEINFO-TEST", fallback = LocalStationCloudRepository.class)
public interface StationCloudRepository {

    /**
     *
     * @param stationCode
     * @return
     */
    @RequestMapping(path = "/api/v1/baseInfo/station/findStationNameByStationCodeForApi", method = RequestMethod.GET)
    ResponseResult findStationName(@RequestParam("stationCode") String stationCode);
}
