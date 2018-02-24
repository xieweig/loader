package cn.sisyphe.coffee.stock.infrastructure.shared.user.repo;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/2.
 * remark：SpringCloud方式查询用户信息接口
 * version: 1.0
 *
 * @author XiongJing
 */
@Repository
public interface UserRepository {

    /**
     * 根据用户名称查询用户编码集合
     *
     * @param userName 用户名称
     * @return
     */
    List<String> findByLikeUserName(String userName);

    /**
     * 根据用户编码称查询用户姓名
     *
     * @param userCode 用户名称
     * @return
     */
    String findOneByUserCode(String userCode);

}
