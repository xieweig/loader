package cn.sisyphe.coffee.stock.domain.shared.goods.product;

import cn.sisyphe.coffee.stock.infrastructure.shared.product.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：
 * version:
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 根据产品编码查询产品信息
     *
     * @param productCode 产品编码
     * @return
     */
    @Override
    public Product findByProductCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }
}
