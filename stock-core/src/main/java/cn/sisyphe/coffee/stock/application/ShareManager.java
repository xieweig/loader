package cn.sisyphe.coffee.stock.application;

import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.CargoService;
import cn.sisyphe.coffee.stock.domain.shared.station.StationService;
import cn.sisyphe.coffee.stock.domain.shared.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2018/2/2 9:56
 * @description
 */
@Service
public class ShareManager {

    @Autowired
    private CargoService cargoService;


    @Autowired
    private StationService stationService;

    @Autowired
    private SupplierService supplierService;


    public Cargo findByCargoCode(String cargoCode) {
        return cargoService.findByCargoCode(cargoCode);
    }

    public String findStationNameByStationCode(String stationCode) {
        return stationService.findStationName(stationCode);
    }

    public String findSupplierNameBySupplierCode(String stationCode) {
        return supplierService.findSupplierNameBySupplierCode(stationCode);
    }
}
