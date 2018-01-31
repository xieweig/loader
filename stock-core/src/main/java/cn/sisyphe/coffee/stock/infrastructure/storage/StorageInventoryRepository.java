package cn.sisyphe.coffee.stock.infrastructure.storage;

import cn.sisyphe.coffee.stock.domain.shared.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author XiongJing
 * @date 2018/1/26
 * remark：
 * version:
 */
public interface StorageInventoryRepository {
    /**
     * 保存
     *
     * @param storage
     */
    void save(StorageInventory storage);

    /**
     * 批量保存
     *
     * @param storageInventoryList
     */
    void save(List<StorageInventory> storageInventoryList);


    /**
     * 根据站点编码查询
     *
     * @param stationCode
     * @return
     */
    List<StorageInventory> findByStationCode(String stationCode);

    /**
     * 根据库房编码查询
     *
     * @param station
     * @return
     */
    List<StorageInventory> findByStorage(Station station);

    /**
     * 查询站点-库位下的货物
     * @param station
     * @param cargo
     * @return
     */
    StorageInventory findByStationAndCargo(Station station, Cargo cargo);

    /**
     * 根据货物查询
     *
     * @param cargo
     * @return
     */
    List<StorageInventory> findByCargo(Cargo cargo);

    /**
     * 根据原料查询
     *
     * @param rawMaterial
     * @return
     */
    List<StorageInventory> findByRawMaterial(RawMaterial rawMaterial);

    /**
     * 高级条件查询
     *
     * @param ta
     * @param pageable
     * @return
     */
    Page<StorageInventory> findAll(Specification<StorageInventory> ta, Pageable pageable);
}
