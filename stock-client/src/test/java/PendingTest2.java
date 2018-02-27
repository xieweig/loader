import cn.sisyphe.coffee.stock.StockClientApplication;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.infrastructure.pending.PendingRepository;
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
 * Created by XiongJing on 2018/1/26.
 * remark：
 * version:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockClientApplication.class)
public class PendingTest2 {

    @Autowired
    private PendingRepository pendingRepository;

    /**
     * 进货入库
     */
    @Test
    public void purchaseTest() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("pendBillCode021");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA01");
        inStation.setStorageCode("ST0001");
        pendingBillItem.setInStation(inStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.IN_STORAGE);


        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);
        // 成本金额
        pendingBillDetail.setUnitCost(new BigDecimal(0.66));

        // 应拣总量
        pendingBillDetail.setShipTotalAmount(2000);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(2000);
        // 过期时间
        pendingBillDetail.setExpirationTime(new Date());

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);
        // 成本金额
        pendingBillDetail2.setUnitCost(new BigDecimal(0.55));

        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(3000);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(3000);
        // 过期时间
        pendingBillDetail2.setExpirationTime(new Date());

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================

        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }

    /**
     * 配送出库
     */
    @Test
    public void deliveryOutTest() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("deliveryBillCode021");
        // 原单号
        pendingBill.setSourceCode("pendBillCode021");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0002");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA01");
        outStation.setStorageCode("ST0001");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.OUT_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);


        // 实拣总量
        pendingBillDetail.setActualTotalAmount(125);


        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);


        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(1500);


        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }

    /**
     * 配送入库
     */
    @Test
    public void deliveryInTest() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("deliveryBillCode022");
        // 原单号
        pendingBill.setSourceCode("deliveryBillCode021");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0002");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA01");
        outStation.setStorageCode("ST0001");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.IN_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);


        // 实拣总量
        pendingBillDetail.setActualTotalAmount(125);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);

        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(1500);


        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }

    /**
     * 全调拨
     */
    @Test
    public void moveAllTest() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("moveBillCode022");
        // 原单号
        pendingBill.setSourceCode("deliveryBillCode022");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0003");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA02");
        outStation.setStorageCode("ST0002");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.MOVE_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);


        // 应拣总量
        pendingBillDetail.setShipTotalAmount(125);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(125);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);

        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(1500);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(1500);

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }


    /**
     * 有误差的调拨
     */
    @Test
    public void moveHalfTest() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("moveHalfBillCode023");
        // 原单号
        //pendingBill.setSourceCode("deliveryBillCode002");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0004");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA02");
        outStation.setStorageCode("ST0003");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.MOVE_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);

        // 应拣总量
        pendingBillDetail.setShipTotalAmount(125);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(170);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);
        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(1200);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(1000);

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }


    /**
     * 自主全调拨
     */
    @Test
    public void moveAllTest1() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("moveBillCode024");
        // 原单号
        //pendingBill.setSourceCode("deliveryBillCode002");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0003");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA02");
        outStation.setStorageCode("ST0004");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.MOVE_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);


        // 应拣总量
        pendingBillDetail.setShipTotalAmount(80);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(80);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);

        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(610);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(610);

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }


    /**
     * 自主有误差的调拨
     */
    @Test
    public void moveHalfTest1() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("moveHalfBillCode024");
        // 原单号
        //pendingBill.setSourceCode("deliveryBillCode002");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0002");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA02");
        outStation.setStorageCode("ST0004");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.MOVE_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);

        // 应拣总量
        pendingBillDetail.setShipTotalAmount(55);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(60);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);
        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(120);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(100);

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }



    /**
     * 日常误差
     */
    @Test
    public void mistakeAllTest() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("misBillCode021");


        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("mistake");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA02");
        outStation.setStorageCode("ST0002");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.MOVE_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode003");
        pendingBillDetail.setCargo(cargo);


        // 应拣总量
        pendingBillDetail.setShipTotalAmount(10);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(10);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode004");
        pendingBillDetail2.setCargo(cargo2);

        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(20);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(20);

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }

    /**
     * 日常误差
     */
    @Test
    public void missAllTest1() {
        PendingBill pendingBill = new PendingBill();
        // 单号
        pendingBill.setBillCode("missBillCode001");
        // 原单号
        //pendingBill.setSourceCode("deliveryBillCode002");

        // 待冲减的项
        List<PendingBillItem> billItemList = new ArrayList<>();
        PendingBillItem pendingBillItem = new PendingBillItem();
        // 项目号
        pendingBillItem.setItemCode(pendingBill.getBillCode());

        // 调入站点
        Station inStation = new Station();
        inStation.setStationCode("HDQA02");
        inStation.setStorageCode("ST0005");
        pendingBillItem.setInStation(inStation);

        // 调出站点
        Station outStation = new Station();
        outStation.setStationCode("HDQA02");
        outStation.setStorageCode("mistake");
        pendingBillItem.setOutStation(outStation);

        // 进出库类型
        pendingBillItem.setInOutStorage(InOutStorage.MOVE_STORAGE);



        // 待处理单明细
        List<PendingBillDetail> billDetailList = new ArrayList<>();
        // ====================待处理单明细1==========================
        PendingBillDetail pendingBillDetail = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode("rawMaterial001");
        pendingBillDetail.setRawMaterial(rawMaterial);
        // 货物
        Cargo cargo = new Cargo();
        cargo.setCargoCode("cargoCode001");
        pendingBillDetail.setCargo(cargo);


        // 应拣总量
        pendingBillDetail.setShipTotalAmount(2);
        // 实拣总量
        pendingBillDetail.setActualTotalAmount(2);

        billDetailList.add(pendingBillDetail);

        // ====================待处理单明细2==========================
        PendingBillDetail pendingBillDetail2 = new PendingBillDetail();
        // 原料
        RawMaterial rawMaterial2 = new RawMaterial();
        rawMaterial2.setRawMaterialCode("rawMaterial002");
        pendingBillDetail2.setRawMaterial(rawMaterial2);
        // 货物
        Cargo cargo2 = new Cargo();
        cargo2.setCargoCode("cargoCode002");
        pendingBillDetail2.setCargo(cargo2);

        // 应拣总量
        pendingBillDetail2.setShipTotalAmount(-750);
        // 实拣总量
        pendingBillDetail2.setActualTotalAmount(-750);

        billDetailList.add(pendingBillDetail2);

        pendingBillItem.setPendingBillDetailList(billDetailList);
        billItemList.add(pendingBillItem);
        // ========================================================


        pendingBill.setPendingBillItemList(billItemList);
        pendingRepository.save(pendingBill);
    }


    /**
     * 查询单个
     */
    @Test
    public void queryOne() {
        String pendCode = "pendBillCode001";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        System.err.println(pendingBill.toString());
    }

    @Test
    public void queryNotOffset(){
        List<PendingBill> pendingBillList = pendingRepository.findNotOffset();
        System.err.println(pendingBillList.size());
        System.err.println(pendingBillList.toString());
    }

}
