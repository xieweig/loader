package cn.sisyphe.coffee.stock.infrastructure.offset;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.infrastructure.offset.jpa.JPAOffsetRepository;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.framework.common.utils.StringUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：已冲减数据接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public class OffsetRepositoryImpl implements OffsetRepository {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JPAOffsetRepository jpaOffsetRepository;


    /**
     * 保存单个冲减数据
     *
     * @param offset
     */
    @Override
    public void save(Offset offset) {
        jpaOffsetRepository.save(offset);
    }

    /**
     * 批量保存冲减数据
     *
     * @param offsetList
     */
    @Override
    public void save(Iterable<Offset> offsetList) {
        jpaOffsetRepository.save(offsetList);
    }


    /**
     * 库存量查询
     *
     * @param station
     * @param cargo
     * @return
     */
    @Override
    public int getStockByStationAndCargo(Station station, Cargo cargo) {
        Integer count = jpaOffsetRepository.getStock(station.getStationCode(), station.getStorageCode(), cargo.getCargoCode());

        return count == null ? 0 : count;
    }

    /**
     * 查询首个原料
     *
     * @param station
     * @param rawMaterial
     * @param cargo
     * @return
     */
    @Override
    public Offset findFirstRawMaterial(Station station, RawMaterial rawMaterial, Cargo cargo) {

        Offset offset = null;
        if (cargo != null) {
            offset = jpaOffsetRepository.findFirstByStationAndRawMaterialAndCargoAndSurplusAmountNotOrderByCreateTime(station, rawMaterial, cargo, 0);
        }

        if (offset == null) {
            offset = jpaOffsetRepository.findFirstByStationAndRawMaterialAndSurplusAmountNotOrderByCreateTime(station, rawMaterial, 0);
        }

        return offset;
    }


    /**
     * 查询原出库单冲减
     *
     * @param sourceCode
     * @return
     */
    @Override
    public Set<Offset> findAllBySourceCodeOrderByCreateTime(String sourceCode) {
        return jpaOffsetRepository.findAllBySourceCodeOrderByCreateTime(sourceCode);
    }

    /**
     * 高级查询
     *
     * @param conditionQuery
     * @return
     */
    @Override
    public List<Offset> findByCondition(ConditionQueryStorage conditionQuery) {
        StringBuffer stringBuffer = addParameters(conditionQuery);
        // 加上分页
        String pageSql = withPage(stringBuffer, conditionQuery);
        System.err.println("拼接后的sql:"+pageSql);
        Query query = entityManager.createNativeQuery(pageSql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List queryResultList = query.getResultList();
        return offsetMapper(queryResultList);
    }

    /**
     * 查询总数
     *
     * @param conditionQuery
     * @return
     */
    @Override
    public Long findTotalByCondition(ConditionQueryStorage conditionQuery) {
        StringBuffer stringBuffer = addParameters(conditionQuery);
        Query query = entityManager.createNativeQuery(stringBuffer.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List queryResultList = query.getResultList();
        return new Long(offsetMapper(queryResultList).size());
    }


    /**
     * 拼接参数
     *
     * @param conditionQuery
     * @return
     */
    private StringBuffer addParameters(ConditionQueryStorage conditionQuery) {
        StringBuffer sql = new StringBuffer("SELECT batch_Code,station_code,raw_material_code,cargo_code," +
                "inventory_total_amount,total_offset_amount,offset_amount,surplus_amount,unit_cost,source_code," +
                "in_out_storage,create_time FROM offset " +
                "WHERE offset_id IN (SELECT max(offset_id) FROM offset WHERE 1 = 1 ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 拼接开始时间
        if (conditionQuery.getStopTimeStart() != null) {
            sql.append(" and create_time  >= '");
            sql.append(sdf.format(conditionQuery.getStopTimeStart()));
            sql.append("'");
        }
        // 拼接结束时间
        if (conditionQuery.getStopTimeEnd() != null) {
            sql.append(" and create_time  <= '");
            sql.append(sdf.format(conditionQuery.getStopTimeEnd()));
            sql.append("'");
        }
        // 拼接原料编码
        if (!StringUtil.isEmpty(conditionQuery.getMaterialCode())) {
            sql.append(" and raw_material_code like ' ");
            sql.append(conditionQuery.getMaterialCode() + "'");
        }
        // 拼接多个原料名称
        if (conditionQuery.getMaterialCodeArray() != null && conditionQuery.getMaterialCodeArray().size() > 0) {
            sql.append(" and raw_material_code in ( ");
            StringBuffer sb = new StringBuffer();
            for (String code : conditionQuery.getMaterialCodeArray()) {
                sb.append("'");
                sb.append(code);
                sb.append("',");
            }
            String substring = sb.substring(0, sb.length() - 1);
            sql.append(substring);
            sql.append(")");
        }
        // 拼接多个原料分类
        if (conditionQuery.getMaterialTypeArray() != null && conditionQuery.getMaterialTypeArray().size() > 0) {
            sql.append(" and raw_material_code in ( ");
            StringBuffer sb = new StringBuffer();
            for (String code : conditionQuery.getMaterialCodes()) {
                sb.append("'");
                sb.append(code);
                sb.append("',");
            }
            String substring = sb.substring(0, sb.length() - 1);
            sql.append(substring);
            sql.append(")");
        }
        // 拼接站点
        if (conditionQuery.getStationCodeArray() != null && conditionQuery.getStationCodeArray().size() > 0) {
            sql.append(" and station_code in ( ");
            StringBuffer sb = new StringBuffer();
            for (String code : conditionQuery.getStationCodeArray()) {
                sb.append("'");
                sb.append(code);
                sb.append("',");
            }
            String substring = sb.substring(0, sb.length() - 1);
            sql.append(substring);
            sql.append(")");
        }
        // 拼接库位
        if (conditionQuery.getStorageCodeArray() != null && conditionQuery.getStorageCodeArray().size() > 0) {
            sql.append(" and storage_code in ( ");
            StringBuffer sb = new StringBuffer();
            for (String code : conditionQuery.getStorageCodeArray()) {
                sb.append("'");
                sb.append(code);
                sb.append("',");
            }
            String substring = sb.substring(0, sb.length() - 1);
            sql.append(substring);
            sql.append(")");
        }
        sql.append(" GROUP BY station_code,storage_code,cargo_code) ORDER BY station_code,storage_code,cargo_code");

        return sql;
    }

    /**
     * 拼接分页信息
     *
     * @param sql
     * @param conditionQuery
     * @return
     */
    private String withPage(StringBuffer sql, ConditionQueryStorage conditionQuery) {
        sql.append(" limit ");
        sql.append(conditionQuery.getPage() * conditionQuery.getPageSize() - conditionQuery.getPageSize());
        sql.append(",");
        sql.append(conditionQuery.getPageSize());
        return sql.toString();
    }

    /**
     * @param resultList
     * @return
     */
    private List<Offset> offsetMapper(List resultList) {

        List<Offset> offsetList = new ArrayList<>();
        for (Object object : resultList) {
            Map map = (Map) object;
            Offset offset = new Offset();
            // 批号
            offset.setBatchCode(map.get("batch_Code").toString());
            // 站点
            offset.setStation(new Station(map.get("station_code").toString(), map.get("storage_code").toString()));
            // 原料
            offset.setRawMaterial(new RawMaterial(map.get("raw_material_code").toString()));
            // 货物
            offset.setCargo(new Cargo(map.get("cargo_code").toString()));
            // 当前货物库存总量
            offset.setInventoryTotalAmount((Integer) map.get("inventory_total_amount"));
            // 本次货物冲减总量
            offset.setTotalOffsetAmount((Integer) map.get("total_offset_amount"));
            // 已冲减数量
            offset.setOffsetAmount((Integer) map.get("offset_amount"));
            // 剩余未冲减数量
            offset.setSurplusAmount((Integer) map.get("surplus_amount"));
            // 成本金额
            offset.setUnitCost((BigDecimal) map.get("unit_cost"));
            // 源单号
            offset.setSourceCode(map.get("source_code").toString());
            // 进出库类型in_out_storage
            offset.setInOutStorage((InOutStorage) map.get("surplus_amount"));
            // 时间
            offset.setCreateTime((Date) map.get("create_time"));
            offsetList.add(offset);
        }

        return offsetList;
    }
}
