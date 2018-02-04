package cn.sisyphe.coffee.stock.domain.storage;

import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;

import java.util.List;

/**
 * Created by heyong on 2017/12/29 16:00
 * Description:
 *
 * @author heyong
 */
public interface StorageService {

    /**
     * 更新库存
     *
     * @param station
     * @param cargo
     * @param rawMaterial
     * @param amount
     */

    void updateInventory(Station station, Cargo cargo, RawMaterial rawMaterial, int amount);


    /**
     * 高级查询
     *
     * @param conditionQuery
     * @return
     */
    List<StorageInventory> findPageByCondition(ConditionQueryStorage conditionQuery);

    /**
     * 查询总数
     *
     * @param conditionQuery
     * @return
     */
    Long findTotalByCondition(ConditionQueryStorage conditionQuery);

}
