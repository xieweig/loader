package cn.sisyphe.coffee.stock.infrastructure.shared.cargo.repo;

import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.infrastructure.shared.cargo.CargoCloudRepository;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：货物Cloud操作接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public class CargoRepositoryImpl implements CargoRepository {

    @Autowired
    private CargoCloudRepository cargoCloudRepository;


    /**
     * 根据货物编码查询货物信息
     *
     * @param cargoCode 货物编码
     * @return
     */
    @Override
    public Cargo findByCargoCode(String cargoCode) {
        ResponseResult responseResult = cargoCloudRepository.findByCargoCode(cargoCode);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("cargo")) {
            return new Cargo();
        }
        LinkedHashMap<String, String> cargoResultMap = (LinkedHashMap) resultMap.get("cargo");
        Cargo cargo = new Cargo();
        cargo.setCargoCode(cargoResultMap.get("cargoCode"));
        cargo.setCargoName(cargoResultMap.get("cargoName"));
        cargo.setBarCode(cargoResultMap.get("barCode"));
        cargo.setMeasurement(Integer.valueOf(cargoResultMap.get("measurement")));
        cargo.setStandardUnit(cargoResultMap.get("standardUnit"));
        cargo.setEffectiveTime(Integer.valueOf(cargoResultMap.get("effectiveTime")));
        return cargo;
    }
}
