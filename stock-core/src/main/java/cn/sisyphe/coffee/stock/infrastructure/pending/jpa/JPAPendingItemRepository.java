package cn.sisyphe.coffee.stock.infrastructure.pending.jpa;

import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
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
public interface JPAPendingItemRepository extends JpaRepository<PendingBillItem, String>, JpaSpecificationExecutor<PendingBillItem> {

    @Query(value = "select bill_code from pending_bill_item where item_code = ?1", nativeQuery = true)
    String findByItemCode(String itemCode);

    /**
     * 查询未冲减的数据
     *
     * @param isOffset 0:未冲减 1：已冲减
     * @return
     */
    @Query(value = "select * from pending_bill_item where is_offset = ?1", nativeQuery = true)
    List<PendingBillItem> findNotOffset(int isOffset);

}
