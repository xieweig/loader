package cn.sisyphe.coffee.stock.infrastructure.offset.jpa;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：冲减数据JPA接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface JPAOffsetRepository extends JpaRepository<Offset, Long>, JpaSpecificationExecutor<Offset> {

    /**
     * 查询原出库单冲减
     *
     * @param sourceCode
     * @return
     */
    Set<Offset> findAllBySourceCodeOrderByCreateTime(String sourceCode);


    /**
     * 库存量查询
     *
     * @param stationCode
     * @param storageCode
     * @param cargoCode
     * @return
     */

    @Query(value = "SELECT IFNULL(sum(a.surplus_amount),0) FROM offset a " +
            "WHERE a.station_code =?1 AND a.storage_code = ?2 AND a.cargo_code =?3", nativeQuery = true)
    Integer getStock(String stationCode, String storageCode, String cargoCode);


    /**
     * 带货物的查询
     * @param station
     * @param rawMaterial
     * @param cargo
     * @param surplusAmount
     * @return
     */
    Offset findFirstByStationAndRawMaterialAndCargoAndSurplusAmountNotOrderByCreateTime(Station station, RawMaterial rawMaterial, Cargo cargo, Integer surplusAmount);

    /**
     * 按原料的查询
     * @param station
     * @param rawMaterial
     * @param surplusAmount
     * @return
     */
    Offset findFirstByStationAndRawMaterialAndSurplusAmountNotOrderByCreateTime(Station station, RawMaterial rawMaterial, Integer surplusAmount);
}
