package cn.sisyphe.coffee.stock.domain.offset;

import cn.sisyphe.coffee.stock.infrastructure.offset.OffsetRepository;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：冲减业务接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Service
public class OffsetExtraServiceImpl implements OffsetExtraService {


    @Autowired
    private OffsetRepository offsetRepository;

    @Override
    public List<Offset> findPageByCondition(ConditionQueryStorage conditionQuery) {
        List<Offset> offsetList = offsetRepository.findByCondition(conditionQuery);
        return offsetList;
    }

    @Override
    public Long findTotalByCondition(ConditionQueryStorage conditionQuery) {
        return offsetRepository.findTotalByCondition(conditionQuery);
    }

}
