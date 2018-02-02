package cn.sisyphe.coffee.stock.domain.offset.strategy;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;

/**
 * Created by heyong on 2018/1/19 14:02
 * Description: 冲减策略
 *
 * @author heyong
 */
public interface OffsetStrategy {


    /**
     * 获取待冲减数据
     *
     * @param station
     * @param rawMaterial
     * @param cargo
     * @return
     */
    Offset getOffsetting(Station station, RawMaterial rawMaterial, Cargo cargo);
}
