package cn.sisyphe.coffee.stock.domain.shared.supplier;

import cn.sisyphe.coffee.stock.infrastructure.shared.supplier.repo.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2018/2/5 12:01
 * @description
 */

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public String findSupplierNameBySupplierCode(String supplierCode) {
        return supplierRepository.findSupplierNameBySupplierCode(supplierCode);
    }
}
