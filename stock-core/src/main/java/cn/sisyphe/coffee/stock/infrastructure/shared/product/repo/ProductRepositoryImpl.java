package cn.sisyphe.coffee.stock.infrastructure.shared.product.repo;

import cn.sisyphe.coffee.stock.domain.shared.goods.product.Product;
import cn.sisyphe.coffee.stock.infrastructure.shared.product.ProductCloudRepository;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：产品Cloud操作接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private ProductCloudRepository productCloudRepository;

    /**
     * 根据产品编码查询产品信息
     *
     * @param productCode 产品编码
     * @return
     */
    @Override
    public Product findByProductCode(String productCode) {
        ResponseResult responseResult = productCloudRepository.findByProductCode(productCode);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("product")) {
            return new Product();
        }
        LinkedHashMap<String, String> productResultMap = (LinkedHashMap) resultMap.get("cargo");
        Product product = new Product();
        product.setProductCode(productResultMap.get("productCode"));
        product.setProductName(productResultMap.get("productName"));
        return product;
    }
}
