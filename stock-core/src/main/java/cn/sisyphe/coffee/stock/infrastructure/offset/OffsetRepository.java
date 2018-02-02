package cn.sisyphe.coffee.stock.infrastructure.offset;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;

import java.util.List;
import java.util.Set;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：已冲减数据接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface OffsetRepository {

    /**
     * 保存单个
     *
     * @param offset
     */
    void save(Offset offset);

    /**
     * 批量保存
     *
     * @param offsetList
     */
    void save(Iterable<Offset> offsetList);


    /**
     * 库存量查询
     *
     * @param station
     * @param cargo
     * @return
     */
    int getStockByStationAndCargo(Station station, Cargo cargo);

    /**
     * 查询首个原料
     *
     * @param station
     * @param rawMaterial
     * @param cargo
     * @return
     */
    Offset findFirstRawMaterial(Station station, RawMaterial rawMaterial, Cargo cargo);

    /**
     * 查询原出库单冲减
     *
     * @param sourceCode
     * @return
     */
    Set<Offset> findAllBySourceCodeOrderByCreateTime(String sourceCode);

    /**
     * 高级条件查询
     *
     * @param conditionQuery
     * @return
     */
    List<Offset> findByCondition(ConditionQueryStorage conditionQuery);

    /**
     * 查询总数
     *
     * @param conditionQuery
     * @return
     */
    Long findTotalByCondition(ConditionQueryStorage conditionQuery);
}
