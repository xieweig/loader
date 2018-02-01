package cn.sisyphe.coffee.stock.infrastructure.shared.station.repo;

/**
 * Created by XiongJing on 2018/1/31.
 * remark：站点SpringCloud调用接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface StationRepository {

    /**
     * 根据站点编码查询站点名称
     *
     * @param stationCode
     * @return
     */
    String findStationName(String stationCode);
}
