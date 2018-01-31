package cn.sisyphe.coffee.stock.infrastructure.shared.rawmaterial;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/25.
 * remark：原料的SpringCloud调用接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Component
public class LocalRawMaterialCloudRepository implements RawMaterialCloudRepository {
    @Override
    public ResponseResult findByMaterialCode(String materialCode) {
        return new ResponseResult();
    }

    @Override
    public ResponseResult findByMaterialTypeCodes(@RequestBody List<Long> materialTypeArray) {
        return new ResponseResult();
    }
}
