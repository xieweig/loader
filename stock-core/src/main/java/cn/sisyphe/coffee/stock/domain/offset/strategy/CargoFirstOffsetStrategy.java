package cn.sisyphe.coffee.stock.domain.offset.strategy;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.util.StringUtils;

/**
 * Created by heyong on 2018/1/19 14:30
 * Description: 货物优先冲减策略
 *
 * @author heyong
 */
public class CargoFirstOffsetStrategy implements OffsetStrategy {

    /**
     * 冲减服务
     */
    private OffsetService offsetService;

    public CargoFirstOffsetStrategy(OffsetService offsetService) {
        this.offsetService = offsetService;
    }


    /**
     * 获取待冲减数据
     *
     * @param station
     * @param rawMaterial
     * @param cargo
     * @return
     */
    @Override
    public Offset getOffsetting(Station station, RawMaterial rawMaterial, Cargo cargo) {

        if (station == null || StringUtils.isEmpty(station.getStationCode()) || StringUtils.isEmpty(station.getStorageCode())) {
            throw new DataException("200010", "站点/库位信息为空");
        }

        if (rawMaterial == null || StringUtils.isEmpty(rawMaterial.getRawMaterialCode())) {
            throw new DataException("200011", "原料信息为空");
        }

        //查找货物
        return offsetService.getOffsetDataPersistence().getOffsetRepository().findFirstRawMaterial(station, rawMaterial, cargo);
    }

}
