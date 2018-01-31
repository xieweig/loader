package cn.sisyphe.coffee.stock.domain.offset;

import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：冲减业务接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface OffsetExtraService {
    /**
     * 高级查询
     *
     * @param conditionQuery
     * @return
     */
    List<Offset> findPageByCondition(ConditionQueryStorage conditionQuery);

    /**
     * 查询总数
     *
     * @param conditionQuery
     * @return
     */
    Long findTotalByCondition(ConditionQueryStorage conditionQuery);
}
