package cn.sisyphe.coffee.stock.infrastructure.shared.cargo.repo;

import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：货物Cloud操作接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface CargoRepository {

    /**
     * 根据货物编码查询货物信息
     *
     * @param cargoCode 货物编码
     * @return
     */
    Cargo findByCargoCode(String cargoCode);
}
