package cn.sisyphe.coffee.stock.infrastructure.shared.rawmaterial.repo;

import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.infrastructure.shared.rawmaterial.RawMaterialCloudRepository;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：原料Cloud操作接口实现
 * version:1.0
 *
 * @author XiongJing
 */
@Repository
public class RawMaterialRepositoryImpl implements RawMaterialRepository {

    @Autowired
    private RawMaterialCloudRepository rawMaterialCloudRepository;

    /**
     * 根据原料编码查询原料信息
     *
     * @param materialCode 原料编码
     * @return
     */
    @Override
    public RawMaterial findByMaterialCode(String materialCode) {
        ResponseResult responseResult = rawMaterialCloudRepository.findByMaterialCode(materialCode);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("rawMaterial")) {
            return new RawMaterial();
        }
        LinkedHashMap<String, String> materialResultMap = (LinkedHashMap) resultMap.get("rawMaterial");
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawMaterialCode(materialResultMap.get("code"));
        rawMaterial.setRawMaterialName(materialResultMap.get("name"));

        return rawMaterial;
    }

    /**
     * 根据多个原料分类编码查询原料编码信息
     *
     * @param materialTypeArray
     * @return
     */
    @Override
    public List<String> findByMaterialTypeCodes(List<Long> materialTypeArray) {
        ResponseResult responseResult = rawMaterialCloudRepository.findByMaterialTypeCodes(materialTypeArray);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("materialCodes")) {
            return new ArrayList<>();
        }
        List<String> strings = (List<String>) resultMap.get("materialCodes");
        return strings;
    }
}
