package cn.sisyphe.coffee.stock.infrastructure.shared.user;

import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by XiongJing on 2018/1/2.
 * remark：springCloud方式查询用户信息
 * version: 1.0
 *
 * @author XiongJing
 */
@FeignClient(value = "OAUTH-CLIENT", fallback = LocalUserCloudRepository.class)
public interface UserCloudRepository {
    @RequestMapping(path = "/api/oauth/user/findByLikeUserName", method = RequestMethod.GET)
    ResponseResult findByLikeUserName(@RequestParam("userName") String supplierCode);

    @RequestMapping(path = "/api/oauth/user/findOneByUserCode", method = RequestMethod.GET)
    ResponseResult findOneByUserCode(@RequestParam("userCode") String userCode);
}
