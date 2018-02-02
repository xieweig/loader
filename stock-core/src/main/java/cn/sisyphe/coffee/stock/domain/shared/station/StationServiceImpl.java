package cn.sisyphe.coffee.stock.domain.shared.station;

import cn.sisyphe.coffee.stock.infrastructure.shared.station.repo.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XiongJing on 2018/2/2.
 * remark：
 * version:
 */
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    /**
     * 根据站点编码查询站点名称
     *
     * @param stationCode
     * @return
     */
    @Override
    public String findStationName(String stationCode) {
        return stationRepository.findStationName(stationCode);
    }
}
