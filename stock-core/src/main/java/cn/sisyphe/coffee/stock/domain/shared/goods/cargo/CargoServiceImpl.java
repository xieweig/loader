package cn.sisyphe.coffee.stock.domain.shared.goods.cargo;

import cn.sisyphe.coffee.stock.infrastructure.shared.cargo.repo.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：
 * version:
 */
@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository cargoRepository;


    @Override
    public Cargo findByCargoCode(String cargoCode) {
        return cargoRepository.findByCargoCode(cargoCode);
    }
}
