import cn.sisyphe.coffee.stock.StockClientApplication;
import cn.sisyphe.coffee.stock.domain.offset.OffsetDataPersistence;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.parser.StationParser;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.infrastructure.pending.PendingRepository;
import cn.sisyphe.coffee.stock.infrastructure.pending.jpa.JPAPendingRepository;
import cn.sisyphe.framework.web.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockClientApplication.class)
public class OffsetItemTest2 {

    @Autowired
    private PendingRepository pendingRepository;


    @Autowired
    private OffsetDataPersistence offsetDataPersistence;

    @Test
    public void offsetTest() throws InterruptedException {
        offsetTest1();
        Thread.sleep(2000L);
        offsetTest2();
        Thread.sleep(2000L);
        offsetTest3();
        Thread.sleep(2000L);
        offsetTest4();
        Thread.sleep(2000L);
        offsetTest5();
        offsetTest6();
        Thread.sleep(2000L);
        offsetTest7();
        Thread.sleep(2000L);
        offsetTest8();
        offsetTest9();
        offsetTest10();
        offsetTest11();
    }


    /**
     * 进货入库
     */
    @Test
    public void offsetTest1() {

        String pendCode = "pendBillCode021";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());

    }

    /**
     * 配送出库
     */
    @Test
    public void offsetTest2() {

        String pendCode = "deliveryBillCode021";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());

    }

    /**
     * 配送入库
     */
    @Test
    public void offsetTest3() {

        String pendCode = "deliveryBillCode022";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());

    }

    /**
     * 全调拨
     */
    @Test
    public void offsetTest4() {
        String pendCode = "moveBillCode022";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());

    }



    /**
     * 有误差的调拨
     */
    @Test
    public void offsetTest5() {
        String pendCode = "moveHalfBillCode023";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());


    }

    /**
     * 自主全调拨
     */
    @Test
    public void offsetTest6() {
        String pendCode = "moveBillCode024";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }


    /**
     * 自主全调拨
     */
    @Test
    public void offsetTest7() {
        String pendCode = "moveHalfBillCode024";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }


    /**
     * 报损出库
     */
    @Test
    public void offsetTest8() {
        String pendCode = "outBillCode001";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }

    /**
     * 报溢入库
     */
    @Test
    public void offsetTest9() {
        String pendCode = "inBillCode001";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }

    /**
     * 日常误差
     */
    @Test
    public void offsetTest10() {
        String pendCode = "misBillCode021";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }

    /**
     * 报溢入库
     */
    @Test
    public void offsetTest11() {
        String pendCode = "inBillCode002";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }

    /**
     * 报溢入库
     */
    @Test
    public void offsetTest12() {
        String pendCode = "inBillCode003";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }

    /**
     * 报损出库
     */
    @Test
    public void offsetTest13() {
        String pendCode = "outBillCode002";
        PendingBill pendingBill = pendingRepository.findByCode(pendCode);
        OffsetService offsetService = new OffsetService(new StationParser(), offsetDataPersistence);
        offsetService.setPendingBill(pendingBill);

        offsetService.offset(new ResponseResult());
    }

    @Autowired
    private JPAPendingRepository jpaPendingRepository;

    @Test
    public void offsetTest0(){

        //jpaPendingRepository.findAllByPendingBillItemList_OutStation_StationCode("HDQA02");
        //jpaPendingRepository.findAllByPendingBillItemList_PendingBillDetailList_Cargo_CargoCode("cargoCode002");
        jpaPendingRepository.findFirstByPendingBillItemList_IsOffset(false);
    }
}
