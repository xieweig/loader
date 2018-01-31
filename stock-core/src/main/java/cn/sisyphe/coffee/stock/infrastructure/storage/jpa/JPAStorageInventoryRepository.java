package cn.sisyphe.coffee.stock.infrastructure.storage.jpa;

import cn.sisyphe.coffee.stock.domain.shared.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author XiongJing
 * @date 2018/1/26
 * remark：
 * version:
 */
public interface JPAStorageInventoryRepository extends JpaRepository<StorageInventory, Long>, JpaSpecificationExecutor<StorageInventory> {

    /**
     * 根据站点编码查询
     *
     * @param stationCode
     * @return
     */
    List<StorageInventory> findByStation_StationCode(String stationCode);

    /**
     * 根据库房编码查询
     *
     * @param station
     * @return
     */
    List<StorageInventory> findByStation(Station station);


    /**
     * 查询站点-库位下的货物
     * @param station
     * @param cargo
     * @return
     */
    StorageInventory findFirstByStationAndCargo(Station station, Cargo cargo);

    /**
     * 根据货物编码查询
     *
     * @param cargo
     * @return
     */
    List<StorageInventory> findByCargo(Cargo cargo);

    /**
     * 根据货物编码查询
     *
     * @param rawMaterial
     * @return
     */
    List<StorageInventory> findByRawMaterial(RawMaterial rawMaterial);
}
