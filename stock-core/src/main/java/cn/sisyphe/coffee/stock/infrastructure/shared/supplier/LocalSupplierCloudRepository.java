package cn.sisyphe.coffee.stock.infrastructure.shared.supplier;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * @Date 2018/2/5 11:49
 * @description
 */
@Component
public class LocalSupplierCloudRepository implements SupplierCloudRepository {

    @Override
    public ResponseResult findSupplierNameBySupplierCode(String supplierCode) {
        return new ResponseResult();
    }
}
