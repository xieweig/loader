package cn.sisyphe.coffee.stock.infrastructure.shared.rawmaterial.repo;

import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：原料Cloud操作接口
 * version: 1.0
 *
 * @author XiongJing
 */
public interface RawMaterialRepository {

    /**
     * 根据原料编码查询原料信息
     *
     * @param materialCode 原料编码
     * @return
     */
    RawMaterial findByMaterialCode(String materialCode);

    /**
     * 根据多个原料分类编码查询原料编码信息
     *
     * @param materialTypeArray
     * @return
     */
    List<String> findByMaterialTypeCodes(List<Long> materialTypeArray);
}
