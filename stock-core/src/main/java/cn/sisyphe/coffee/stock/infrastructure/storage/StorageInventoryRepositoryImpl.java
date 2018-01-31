package cn.sisyphe.coffee.stock.infrastructure.storage;

import cn.sisyphe.coffee.stock.domain.shared.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.infrastructure.storage.jpa.JPAStorageInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author XiongJing
 * @date 2018/1/26
 * remark：
 * version:
 */
@Repository
public class StorageInventoryRepositoryImpl implements StorageInventoryRepository {

    @Autowired
    private JPAStorageInventoryRepository jpaStorageInventoryRepository;

    @Override
    public void save(StorageInventory storage) {
        jpaStorageInventoryRepository.save(storage);
    }

    @Override
    public void save(List<StorageInventory> storageInventoryList) {
        jpaStorageInventoryRepository.save(storageInventoryList);
    }

    /**
     * 根据站点编码查询
     *
     * @param stationCode
     * @return
     */
    @Override
    public List<StorageInventory> findByStationCode(String stationCode) {
        return jpaStorageInventoryRepository.findByStation_StationCode(stationCode);
    }

    /**
     * 根据库房编码查询
     *
     * @param station
     * @return
     */
    @Override
    public List<StorageInventory> findByStorage(Station station) {
        return jpaStorageInventoryRepository.findByStation(station);
    }

    /**
     * 查询站点-库位下的货物
     *
     * @param station
     * @param cargo
     * @return
     */
    @Override
    public StorageInventory findByStationAndCargo(Station station, Cargo cargo) {
        return jpaStorageInventoryRepository.findFirstByStationAndCargo(station, cargo);
    }

    /**
     * 根据货物查询
     *
     * @param cargo
     * @return
     */
    @Override
    public List<StorageInventory> findByCargo(Cargo cargo) {
        return jpaStorageInventoryRepository.findByCargo(cargo);
    }

    /**
     * 根据原料查询
     *
     * @param rawMaterial
     * @return
     */
    @Override
    public List<StorageInventory> findByRawMaterial(RawMaterial rawMaterial) {
        return jpaStorageInventoryRepository.findByRawMaterial(rawMaterial);
    }


    @Override
    public Page<StorageInventory> findAll(Specification<StorageInventory> ta, Pageable pageable) {
        return jpaStorageInventoryRepository.findAll(ta,pageable);
    }

}
