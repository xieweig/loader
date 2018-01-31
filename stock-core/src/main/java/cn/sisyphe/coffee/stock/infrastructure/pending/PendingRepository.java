package cn.sisyphe.coffee.stock.infrastructure.pending;

import cn.sisyphe.coffee.stock.domain.pending.PendingBill;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：待冲减单据基础设施接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface PendingRepository {

    /**
     * 查询单个
     *
     * @param pendCode
     * @return
     */
    PendingBill findByCode(String pendCode);

    /**
     * 保存单个
     *
     * @param pendingBill
     */
    void save(PendingBill pendingBill);

    /**
     * 批量保存
     *
     * @param pendingBillList
     */
    void save(List<PendingBill> pendingBillList);

    /**
     * 查询没有冲减的数据
     *
     * @return 没有冲减的数据
     */
    List<PendingBill> findNotOffset();


    /**
     * 查询没有冲减的PendingBill数据
     *
     * @param isOffSet
     * @return
     */
    PendingBill findFirstPendingBillIsOffset(Boolean isOffSet);


}
