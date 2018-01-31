package cn.sisyphe.coffee.stock.infrastructure.shared.cargo;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：货物的SpringCloud调用接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Component
public class LocalCargoCloudRepository implements CargoCloudRepository {
    @Override
    public ResponseResult findByCargoCode(String cargoCode) {
        return new ResponseResult();
    }
}
