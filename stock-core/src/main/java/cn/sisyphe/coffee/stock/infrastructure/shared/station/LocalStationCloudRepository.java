package cn.sisyphe.coffee.stock.infrastructure.shared.station;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * Created by XiongJing on 2018/1/31.
 * remark：
 * version:
 */
@Component
public class LocalStationCloudRepository implements StationCloudRepository {
    @Override
    public ResponseResult findStationName(String stationCode) {
        return new ResponseResult();
    }
}
