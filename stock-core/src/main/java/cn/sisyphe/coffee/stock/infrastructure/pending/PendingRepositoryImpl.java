package cn.sisyphe.coffee.stock.infrastructure.pending;

import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.infrastructure.pending.jpa.JPAPendingItemRepository;
import cn.sisyphe.coffee.stock.infrastructure.pending.jpa.JPAPendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：待冲减单据基础设施接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public class PendingRepositoryImpl implements PendingRepository {

    @Autowired
    private JPAPendingRepository pendingRepository;

    @Autowired
    private JPAPendingItemRepository itemRepository;


    /**
     * 查询单个
     *
     * @param pendCode
     * @return
     */
    @Override
    public PendingBill findByCode(String pendCode) {
        return pendingRepository.findOne(pendCode);
    }

    /**
     * 保存单个
     *
     * @param pendingBill
     */
    @Override
    public void save(PendingBill pendingBill) {
        pendingRepository.save(pendingBill);
    }

    /**
     * 批量保存
     *
     * @param pendingBillList
     */
    @Override
    public void save(List<PendingBill> pendingBillList) {
        pendingRepository.save(pendingBillList);
    }

    /**
     * 查询未冲减的数据
     *
     * @return 未冲减的数据
     */
    @Override
    public List<PendingBill> findNotOffset() {

        List<PendingBillItem> billItemList = itemRepository.findNotOffset(0);

        List<PendingBill> pendingBillList = new ArrayList<>();
        if (billItemList == null || billItemList.size() <= 0) {
            return new ArrayList<>();
        } else {
            // 循环数据
            for (PendingBillItem item : billItemList) {
                // 根据itemCode查询billCode
                String billCode = itemRepository.findByItemCode(item.getItemCode());
                if (StringUtils.isEmpty(billCode)) {
                    continue;
                }
                // 根据BillCode查询
                PendingBill pendingBill = pendingRepository.findOne(billCode);
                // 去重
                if (pendingBill != null && !pendingBillList.contains(pendingBill)) {
                    pendingBillList.add(pendingBill);
                }
            }
            return pendingBillList;
        }
    }

    /**
     * 查询没有冲减的PendingBill
     *
     * @param isOffSet
     * @return
     */
    @Override
    public PendingBill findFirstPendingBillIsOffset(Boolean isOffSet) {
        return pendingRepository.findFirstByPendingBillItemList_IsOffset(isOffSet);
    }
}
