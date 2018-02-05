package cn.sisyphe.coffee.stock.infrastructure.shared.supplier.repo;

import cn.sisyphe.coffee.stock.infrastructure.shared.supplier.SupplierCloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Date 2018/2/5 11:50
 * @description
 */

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    @Autowired
    private SupplierCloudRepository supplierCloudRepository;

    @Override
    public String findSupplierNameBySupplierCode(String supplierCode) {
        Map resultMap = supplierCloudRepository.findSupplierNameBySupplierCode(supplierCode).getResult();
        if (!resultMap.containsKey("result")) {
            return null;
        }
        Map supplierProperties = (Map) ((Map) resultMap.get("result")).get("supplier");
        return (String) supplierProperties.get("supplierName");
    }
}
