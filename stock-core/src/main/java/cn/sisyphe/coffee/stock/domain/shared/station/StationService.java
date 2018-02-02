package cn.sisyphe.coffee.stock.domain.shared.station;

/**
 * Created by XiongJing on 2018/2/2.
 * remark：
 * version:
 */
public interface StationService {
    /**
     * 根据站点编码查询站点名称
     *
     * @param stationCode
     * @return
     */
    String findStationName(String stationCode);
}
