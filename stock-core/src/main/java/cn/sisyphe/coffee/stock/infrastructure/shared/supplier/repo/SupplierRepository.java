package cn.sisyphe.coffee.stock.infrastructure.shared.supplier.repo;

/**
 * Created by XiongJing on 2018/1/31.
 * remark：站点SpringCloud调用接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface SupplierRepository {

    String findSupplierNameBySupplierCode(String supplierCode);

}
