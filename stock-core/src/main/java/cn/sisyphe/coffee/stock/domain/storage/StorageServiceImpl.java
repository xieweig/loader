package cn.sisyphe.coffee.stock.domain.storage;

import cn.sisyphe.coffee.stock.domain.shared.Station;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.infrastructure.storage.StorageInventoryRepository;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

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
            throw new DataException("001", "库存更新失败");
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
    public Page<StorageInventory> findPageByCondition(ConditionQueryStorage conditionQuery) {
        // 组装页面
        Pageable pageable = new PageRequest(conditionQuery.getPage() - 1, conditionQuery.getPageSize());

        Page<StorageInventory> storagePage;
        storagePage = queryByParams(conditionQuery, pageable);

        // 改变页码导致的页面为空时，获取最后一页
        if (storagePage.getContent().size() < 1 && storagePage.getTotalElements() > 0) {
            pageable = new PageRequest(storagePage.getTotalPages() - 1, conditionQuery.getPageSize());
            storagePage = queryByParams(conditionQuery, pageable);
        }

        return storagePage;
    }

    /**
     * 拼接查询条件
     *
     * @param conditionQuery
     * @param pageable
     * @return
     */
    private Page<StorageInventory> queryByParams(final ConditionQueryStorage conditionQuery, Pageable pageable) {
        return storageInventoryRepository.findAll((root, query, cb) -> {
            // 交集
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            // 拼接货物编码
            if (!StringUtils.isEmpty(conditionQuery.getCargoCode())) {
                expressions.add(cb.like(root.get("cargo").get("cargoCode").as(String.class), "%" + conditionQuery.getCargoCode() + "%"));
            }
            // 拼接多个货物编码
            if (conditionQuery.getCargoCodeArray() != null && conditionQuery.getCargoCodeArray().size() > 0) {
                expressions.add(root.get("cargo").get("cargoCode").as(String.class).in(conditionQuery.getCargoCodeArray()));
            }
            // 拼接原料分类
            if (!StringUtils.isEmpty(conditionQuery.getMaterialTypeArray())) {
                expressions.add(root.get("rawMaterial").get("rawMaterialCode").as(String.class).in(conditionQuery.getMaterialCodes()));
            }
            // 原料编码
            if (!StringUtils.isEmpty(conditionQuery.getMaterialCode())) {
                expressions.add(cb.like(root.get("rawMaterial").get("rawMaterialCode").as(String.class), "%" + conditionQuery.getMaterialCode() + "%"));
            }
            // 拼接多个原料编码
            if (conditionQuery.getMaterialCodeArray() != null && conditionQuery.getMaterialCodeArray().size() > 0) {
                expressions.add(root.get("rawMaterial").get("rawMaterialCode").as(String.class).in(conditionQuery.getMaterialCodeArray()));
            }
            // 拼接查询站点
            if (conditionQuery.getStationCodeArray() != null && conditionQuery.getStationCodeArray().size() > 0) {
                expressions.add(root.get("station").get("stationCode").as(String.class).in(conditionQuery.getStationCodeArray()));
            }
            // 拼接查询库位
            if (conditionQuery.getStorageCodeArray() != null && conditionQuery.getStorageCodeArray().size() > 0) {
                expressions.add(root.get("station").get("storageCode").as(String.class).in(conditionQuery.getStorageCodeArray()));
            }
            return predicate;
        }, pageable);
    }
}
