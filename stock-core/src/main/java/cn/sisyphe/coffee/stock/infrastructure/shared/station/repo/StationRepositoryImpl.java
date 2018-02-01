package cn.sisyphe.coffee.stock.infrastructure.shared.station.repo;

import cn.sisyphe.coffee.stock.infrastructure.shared.station.StationCloudRepository;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by XiongJing on 2018/1/31.
 * remark：站点SpringCloud调用接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public class StationRepositoryImpl implements StationRepository {
    @Autowired
    private StationCloudRepository stationCloudRepository;

    /**
     * 根据站点编码查询站点名称
     *
     * @param stationCode
     * @return
     */
    @Override
    public String findStationName(String stationCode) {

        ResponseResult responseResult = stationCloudRepository.findStationName(stationCode);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("result")) {
            return null;
        }
        return resultMap.get("result").toString();
    }
}
