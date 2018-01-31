package cn.sisyphe.coffee.stock.infrastructure.shared.product.repo;

import cn.sisyphe.coffee.stock.domain.shared.goods.product.Product;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：产品Cloud操作接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface ProductRepository {

    /**
     * 根据产品编码查询产品信息
     *
     * @param productCode 产品编码
     * @return
     */
    Product findByProductCode(String productCode);
}
