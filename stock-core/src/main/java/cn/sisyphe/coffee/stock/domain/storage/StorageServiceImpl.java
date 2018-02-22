package cn.sisyphe.coffee.stock.domain.storage;

import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.infrastructure.storage.StorageInventoryRepository;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.framework.common.utils.StringUtil;
import cn.sisyphe.framework.web.exception.DataException;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by heyong on 2018/1/30 11:07
 * Description:
 *
 * @author heyong
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageInventoryRepository storageInventoryRepository;
    @Autowired
    private EntityManager entityManager;

    /**
     * 更新库存
     *
     * @param station
     * @param cargo
     * @param rawMaterial
     * @param amount
     */
    @Override
    public void updateInventory(Station station, Cargo cargo, RawMaterial rawMaterial, int amount) {
        if (station == null || StringUtils.isEmpty(station.getStationCode())
                || StringUtils.isEmpty(station.getStorageCode())
                || cargo == null || StringUtils.isEmpty(cargo.getCargoCode())) {
            throw new DataException("200016", "库存更新失败");
        }

        StorageInventory storageInventory = storageInventoryRepository.findByStationAndCargo(station, cargo);

        if (storageInventory != null) {
            storageInventory.setTotalAmount(amount);
        } else {
            storageInventory = new StorageInventory();
            storageInventory.setStation(station);
            storageInventory.setCargo(cargo);
            storageInventory.setRawMaterial(rawMaterial);
            storageInventory.setTotalAmount(amount);
        }

        storageInventoryRepository.save(storageInventory);
    }

    /**
     * 多条件查询
     *
     * @param conditionQuery
     * @return
     */
    @Override
    public List<StorageInventory> findPageByCondition(ConditionQueryStorage conditionQuery) {
        StringBuffer stringBuffer = addParameters(conditionQuery);
        // 加上分页
        String pageSql = withPage(stringBuffer, conditionQuery);
        System.err.println("拼接后的sql:" + pageSql);
        Query query = entityManager.createNativeQuery(pageSql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List queryResultList = query.getResultList();

        return inventoryMapper(queryResultList);
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
        return new Long(inventoryMapper(queryResultList).size());
    }

    /**
     * 拼接参数
     *
     * @param conditionQuery
     * @return
     */
    private StringBuffer addParameters(ConditionQueryStorage conditionQuery) {
        StringBuffer sql = new StringBuffer("select inventory_id,create_time,update_time,raw_material_code,cargo_code," +
                "station_code,storage_code,SUM(total_amount) as total_number from storage_inventory where 1=1 ");
        // 拼接货物编码
        if (!StringUtil.isEmpty(conditionQuery.getCargoCode())) {
            sql.append(" and cargo_code like '");
            sql.append(conditionQuery.getCargoCode() + "'");
        }

        // 拼接多个货物编码
        if (conditionQuery.getCargoCodeArray() != null && conditionQuery.getCargoCodeArray().size() > 0) {
            sql.append(" and cargo_code in ( ");
            StringBuffer sb = new StringBuffer();
            for (String code : conditionQuery.getCargoCodeArray()) {
                sb.append("'");
                sb.append(code);
                sb.append("',");
            }
            String substring = sb.substring(0, sb.length() - 1);
            sql.append(substring);
            sql.append(" ) ");
        }


        // 拼接原料编码
        if (!StringUtil.isEmpty(conditionQuery.getMaterialCode())) {
            sql.append(" and raw_material_code like '");
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
            sql.append(" ) ");
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
            sql.append(" ) ");
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
            sql.append(" ) ");
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
            sql.append(" ) ");
        }

        String cargoOrMaterial = conditionQuery.getCargoOrMaterial();
        if (cargoOrMaterial != null && conditionQuery.getStorageCodeArray() != null && conditionQuery.getStorageCodeArray().size() > 0) {
            sql.append(" GROUP BY station_code,storage_code,");
        } else {
            sql.append(" GROUP BY station_code,");
        }
        if ("cargo".equals(cargoOrMaterial)) {
            sql.append(" cargo_code");
        } else {
            sql.append(" raw_material_code");
        }

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
     * 映射对象属性
     *
     * @param resultList
     * @return
     */
    private List<StorageInventory> inventoryMapper(List resultList) {

        List<StorageInventory> inventoryArrayList = new ArrayList<>();
        for (Object object : resultList) {
            Map map = (Map) object;
            StorageInventory inventory = new StorageInventory();
            // ID
            inventory.setInventoryId(Long.valueOf(map.get("inventory_id").toString()));
            // 创建时间
            inventory.setCreateTime((java.util.Date) map.get("create_time"));
            // 修改时间
            inventory.setUpdateTime((Date) map.get("update_time"));
            // 原料编码
            inventory.setRawMaterial(new RawMaterial(map.get("raw_material_code").toString()));
            // 货物编码
            inventory.setCargo(new Cargo(map.get("cargo_code").toString()));
            // 站点编码和库位编码
            inventory.setStation(new Station(map.get("station_code").toString(), map.get("storage_code").toString()));
            // 总数量
            inventory.setTotalAmount(Integer.valueOf(map.get("total_number").toString()));

            inventoryArrayList.add(inventory);
        }

        return inventoryArrayList;
    }
}
