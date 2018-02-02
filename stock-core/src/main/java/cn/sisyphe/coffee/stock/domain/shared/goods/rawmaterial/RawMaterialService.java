package cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：
 * version:
 */
public interface RawMaterialService {
    /**
     * 根据多个原料分类编码查询原料编码信息
     *
     * @param materialTypeArray
     * @return
     */
    List<String> findByMaterialTypeCodes(List<Long> materialTypeArray);

    /**
     * 根据原料编码查询原料信息
     *
     * @param materialCode 原料编码
     * @return
     */
    RawMaterial findByMaterialCode(String materialCode);

}
