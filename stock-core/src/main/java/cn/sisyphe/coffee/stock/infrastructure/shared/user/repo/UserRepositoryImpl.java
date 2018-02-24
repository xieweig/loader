package cn.sisyphe.coffee.stock.infrastructure.shared.user.repo;

import cn.sisyphe.coffee.stock.infrastructure.shared.user.UserCloudRepository;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XiongJing on 2018/1/2.
 * remark：SpringCloud方式查询用户信息接口实现
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserCloudRepository userCloudRepository;

    /**
     * 根据用户名称查询用户编码集合
     *
     * @param userName 用户名称
     * @return
     */
    @Override
    public List<String> findByLikeUserName(String userName) {

        ResponseResult responseResult = userCloudRepository.findByLikeUserName(userName);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("result")) {
            List<String> list = new ArrayList<>();
            list.add("NOT_EXISTS");
            return list;
        }
        List<String> userCodeList = (ArrayList) resultMap.get("result");
        return userCodeList;
    }

    @Override
    public String findOneByUserCode(String userCode){
        ResponseResult responseResult = userCloudRepository.findOneByUserCode(userCode);
        Map<String, Object> resultMap = responseResult.getResult();
        if (!resultMap.containsKey("result")) {
            return null;
        }
        return (String) resultMap.get("result");
    }

}
