import cn.sisyphe.coffee.stock.StockClientApplication;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.infrastructure.storage.jpa.JPAStorageInventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/26.
 * remark：
 * version:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockClientApplication.class)
public class StorageInventoryTest {

    @Autowired
    private JPAStorageInventoryRepository jpaStorageInventoryRepository;

    /**
     * 保存单个
     */
    @Test
    public void save() {
//        StorageInventory storageInventory = new StorageInventory();
//        storageInventory.setStorageCode("Noraml001");
//        storageInventory.setStationCode("HDQA01");
//        storageInventory.setCargoCode("cargoCode002");
//        storageInventory.setTotalAmount(33000);
//        jpaStorageInventoryRepository.save(storageInventory);

    }

    /**
     * 根据站点编码查询
     */
    @Test
    public void findByStationCode() {
        String stationCode = "HDQA05";
        List<StorageInventory> inventoryList = jpaStorageInventoryRepository.findByStation_StationCode(stationCode);
        System.err.println(inventoryList.size());
        System.err.println(inventoryList.toString());
    }

    /**
     * 根据库房编码查询
     */
    @Test
    public void findByStorageCode() {
        String storageCod = "Noraml001";
        List<StorageInventory> inventoryList = jpaStorageInventoryRepository.findByStation(new Station("HDQA05", storageCod));
        System.err.println(inventoryList.size());
        System.err.println(inventoryList.toString());
    }

    /**
     * 根据货物编码查询
     */
    @Test
    public void findByCargoCode() {
        String cargoCode = "cargoCode001";
        List<StorageInventory> inventoryList = jpaStorageInventoryRepository.findByCargo(new Cargo(cargoCode));
        System.err.println(inventoryList.size());
        System.err.println(inventoryList.toString());
    }

//    /**
//     * 查询单个
//     */
//    @Test
//    public void findOne() {
//        StorageInventoryKey storageInventoryKey = new StorageInventoryKey();
////        storageInventoryKey.setCargoCode("cargoCode003");
////        storageInventoryKey.setStorageCode("Noraml001");
////        storageInventoryKey.setStationCode("HDQA05");
//        StorageInventory storageInventory = jpaStorageInventoryRepository.findOne(storageInventoryKey);
//        System.err.println(storageInventory.toString());
//    }
}
