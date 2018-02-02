package cn.sisyphe.coffee.stock.domain.shared.goods.product;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：
 * version:
 */
public interface ProductService {

    /**
     * 根据产品编码查询产品信息
     *
     * @param productCode 产品编码
     * @return
     */
    Product findByProductCode(String productCode);
}
