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
     * 查询首个货物或原料
     *
     * @param station
     * @param rawMaterial
     * @param cargo
     * @return
     */
    Offset findFirstCargoOrRawMaterial(Station station, RawMaterial rawMaterial, Cargo cargo);

    /**
     * 查询最近的货物或原料的进货史
     * @param station
     * @param rawMaterial
     * @param cargo
     * @return
     */
    Offset findLastCargoOrRawMaterialHistory(Station station, RawMaterial rawMaterial, Cargo cargo);

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

    /**
     * 高级条件查询
     *
     * @param conditionQuery
     * @return
     */
    List<Offset> findByConditionToStorage(ConditionQueryStorage conditionQuery);

    /**
     * 查询总数
     *
     * @param conditionQuery
     * @return
     */
    Long findTotalByConditionToStorage(ConditionQueryStorage conditionQuery);

    /**
     * 查询原料在某个站点某个库位下的最新库存信息
     *
     * @param stationCode     站点编码
     * @param rawMaterialCode 原料编码
     * @param storageCode     库位编码
     * @return
     */
    Offset findRawMaterialStock(String stationCode, String rawMaterialCode, String storageCode);
}
