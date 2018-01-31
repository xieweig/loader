package cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial;

import cn.sisyphe.coffee.stock.infrastructure.shared.rawmaterial.repo.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：
 * version:
 */
@Service
public class RawMaterialServiceImpl implements RawMaterialService {
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    /**
     * 根据原料分类编码查询原料编码
     *
     * @param materialTypeArray
     * @return
     */
    @Override
    public List<String> findByMaterialTypeCodes(List<Long> materialTypeArray) {
        return rawMaterialRepository.findByMaterialTypeCodes(materialTypeArray);
    }
}
