package cn.sisyphe.coffee.stock.infrastructure.pending.jpa;

import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author XiongJing
 * @date 2018/1/27
 * remark：
 * version:
 */
public interface JPAPendingDetailRepository extends JpaRepository<PendingBillDetail, Long>, JpaSpecificationExecutor<PendingBillDetail> {

    /**
     * 查询未冲减的明细信息
     *
     * @param isOffset
     * @return
     */
    @Query(value = "select item_code from pending_bill_detail where is_offset = ?1", nativeQuery = true)
    List<String> findByIsOffset(int isOffset);
}
