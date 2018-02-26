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
     * 按货物的查询未冲减的记录
     * @param station
     * @param rawMaterial
     * @param cargo
     * @param surplusAmount
     * @return
     */
    Offset findFirstByStationAndRawMaterialAndCargoAndSurplusAmountNotOrderByCreateTime(Station station, RawMaterial rawMaterial, Cargo cargo, Integer surplusAmount);

    /**
     * 按原料的查询未冲减的记录
     * @param station
     * @param rawMaterial
     * @param surplusAmount
     * @return
     */
    Offset findFirstByStationAndRawMaterialAndSurplusAmountNotOrderByCreateTime(Station station, RawMaterial rawMaterial, Integer surplusAmount);


    /**
     * 按货物查询当前站点的最近的进货史
     * @param stationCode
     * @param rawMaterial
     * @param cargo
     * @return
     */
    Offset findFirstByStation_StationCodeAndRawMaterialAndCargoAndInOutStorageOrderByCreateTimeDesc(String stationCode, RawMaterial rawMaterial, Cargo cargo, Integer inOutStorage);

    /**
     * 按原料查询当前站点的最近的进货史
     * @param stationCode
     * @param rawMaterial
     * @return
     */
    Offset findFirstByStation_StationCodeAndRawMaterialAndInOutStorageOrderByCreateTimeDesc(String stationCode, RawMaterial rawMaterial, Integer inOutStorage);

    /**
     * 查询原料在某个站点某个库位下的最新库存信息
     *
     * @param stationCode     站点编码
     * @param rawMaterialCode 原料编码
     * @param storageCode     库位编码
     * @return
     */
    @Query(value = "select * from offset where offset_id = (select max(offset_id) from offset where station_code = ?1 and raw_material_code = ?2 and storage_code = ?3)",nativeQuery = true)
    Offset findRawMaterialStock(String stationCode, String rawMaterialCode, String storageCode);
}
