package cn.sisyphe.coffee.stock.infrastructure.shared.product;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：产品的SpringCloud调用接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Component
public class LocalProductCloudRepository implements ProductCloudRepository {

    @Override
    public ResponseResult findByProductCode(String productCode) {
        return new ResponseResult();
    }
}
