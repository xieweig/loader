package cn.sisyphe.coffee.stock.infrastructure.pending.jpa;

import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：待冲减单据JPA接口
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public interface JPAPendingRepository extends JpaRepository<PendingBill, String>, JpaSpecificationExecutor<PendingBill> {

    /**
     * 查询第一个没有冲减的PendingBill
     *
     * @param isOffSet
     * @return
     */
    PendingBill findFirstByPendingBillItemList_IsOffset(boolean isOffSet);

}
