package cn.sisyphe.stockexcel.domain.impl;

import cn.sisyphe.coffee.stock.application.StorageQueryManager;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.coffee.stock.viewmodel.StorageDTO;
import cn.sisyphe.coffee.stock.viewmodel.StorageQueryDTO;
import cn.sisyphe.common.excel.base.ExcelDataQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：库存流水查询-按货物-数据来源组装
 * version: 1.0
 *
 * @author XiongJing
 */
@Component
public class OffsetCargoData extends ExcelDataQuery<StorageQueryDTO> {

    @Autowired
    private StorageQueryManager storageQueryManager;

    private ConditionQueryStorage condition;

    @Override
    public void initExcelDataQuery() {
        StorageDTO storageDTO = storageQueryManager.findOldByCondition(condition);
        setTotalNum(storageDTO.getTotalNumber().intValue());
    }

    @Override
    public List<StorageQueryDTO> getSourcesPageData(int i, Object o) {
        return storageQueryManager.findOldByCondition(condition).getContent();
    }

    @Override
    public ConditionQueryStorage getCondition() {
        return condition;
    }

    public void setCondition(ConditionQueryStorage condition) {
        this.condition = condition;
    }
}
