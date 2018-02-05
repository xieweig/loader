import cn.sisyphe.coffee.stock.StockClientApplication;
import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.infrastructure.offset.OffsetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by heyong on 2018/1/19 17:26
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockClientApplication.class)
public class OffsetTest {

    @Autowired
    private OffsetRepository offsetRepository;


    /**
     * 查询总量
     */
    @Test
    public void queryAmountTest() {

        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode001");
        //
        Station station = new Station();
        //
        station.setStationCode("HDQA02");

        station.setStorageCode("ST0002");
        // 库位
        Integer amount = offsetRepository.getStockByStationAndCargo(station, cargo);
        System.out.println("amount " + amount);
    }

    /**
     * 保存单个
     */
    @Test
    public void save() {
        Offset offset = new Offset();
        offset.setBatchCode("batch001");
        Station station = new Station();
        station.setStationCode("HDQA00");
        offset.setStation(station);
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("materialCode001");
        offset.setRawMaterial(rawMaterial);
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode001");
        offset.setCargo(cargo);
        offset.setInventoryTotalAmount(1000);
        offset.setTotalOffsetAmount(100);
        offset.setOffsetAmount(100);
        offset.setSurplusAmount(0);
        offset.setUnitCost(new BigDecimal(2.25));
        offset.setExpirationTime(new Date());
        offset.setSourceCode("sourceCode001");
        offset.setInOutStorage(InOutStorage.OUT_STORAGE);
        offsetRepository.save(offset);
    }

    /**
     * 批量保存
     */
    @Test
    public void batchSave() {
        List<Offset> offsetList = new ArrayList<>();
        Offset offset = new Offset();
        offset.setBatchCode("batch002");
        Station station = new Station();
        station.setStationCode("HDQA02");
        offset.setStation(station);
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("materialCode002");
        offset.setRawMaterial(rawMaterial);
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode002");
        offset.setCargo(cargo);
        offset.setInventoryTotalAmount(1000);
        offset.setTotalOffsetAmount(100);
        offset.setOffsetAmount(100);
        offset.setSurplusAmount(0);
        offset.setUnitCost(new BigDecimal(3.25));
        offset.setExpirationTime(new Date());
        offset.setSourceCode("sourceCode002");
        offset.setInOutStorage(InOutStorage.OUT_STORAGE);
        offsetList.add(offset);

        Offset offset2 = new Offset();
        offset2.setBatchCode("batch003");
        Station station2 = new Station();
        station2.setStationCode("HDQA03");
        offset2.setStation(station2);
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("materialCode003");
        offset2.setRawMaterial(rawMaterial);
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode003");
        offset2.setCargo(cargo2);
        offset2.setInventoryTotalAmount(1000);
        offset2.setTotalOffsetAmount(100);
        offset2.setOffsetAmount(100);
        offset2.setSurplusAmount(0);
        offset2.setUnitCost(new BigDecimal(4.25));
        offset2.setExpirationTime(new Date());
        offset2.setSourceCode("sourceCode003");
        offset2.setInOutStorage(InOutStorage.OUT_STORAGE);
        offsetList.add(offset2);

        offsetRepository.save(offsetList);
    }


//
//    @Autowired
//    private StrategyService strategyService;
//
//    @Test
//    public void testGetOffSet() {
//
//        Station station = new Station();
//        station.setStationCode("cq12");
//
//        RawMaterial rawMaterial = new RawMaterial();
//        rawMaterial.setRawMaterialCode("NL");
//
//        Cargo cargo = new Cargo();
//        cargo.setCargoCode("NL");
//
//        Offset offset = strategyService.findOneWriteDown(station, null, rawMaterial, cargo);
//        if (offset == null) {
//            return;
//        }
//        System.out.println("" + offset.toString());
//    }

    @Test
    public void test(){
        Station station = new Station();
        station.setStationCode("HDQA00");
        station.setStorageCode("Noraml001");
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode001");

        int i = offsetRepository.getStockByStationAndCargo(station, cargo);
        System.err.println(i);
    }
}
