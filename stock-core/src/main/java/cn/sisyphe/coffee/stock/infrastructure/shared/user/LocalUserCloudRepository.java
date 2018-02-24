package cn.sisyphe.coffee.stock.infrastructure.shared.user;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by XiongJing on 2018/1/2.
 * remark：springCloud方式查询用户信息返回
 * version: 1.0
 *
 * @author XiongJing
 */
@Component
public class LocalUserCloudRepository implements UserCloudRepository {
    @Override
    public ResponseResult findByLikeUserName(@RequestParam("userName") String supplierCode) {
        return new ResponseResult();
    }

    @Override
    public ResponseResult findOneByUserCode(@RequestParam("userCode") String userCode) {
        return new ResponseResult();
    }


}
