package cn.sisyphe.coffee.stock.controller.base;

import cn.sisyphe.framework.auth.logic.AuthenticationSupplier;
import cn.sisyphe.framework.cache.core.CacheHelper;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Amy on 2017/12/27.
 * describe：管理范围
 */
@Service
public class AuthSupplier implements AuthenticationSupplier {

    /**
     * 员工管理范围
     */
    public static final String OAUTH_USER_MANAGEMENT_SCOPE = "OAUTH_USER_MANAGEMENT_SCOPE";
    @Override
    public void importScope(String s, Set<String> set) {

    }

    @Override
    public Set<String> findScope(String s) {
        //读取缓存
        JSONArray jsonArray = (JSONArray) CacheHelper.cache().hashPop(OAUTH_USER_MANAGEMENT_SCOPE, s);
        Set<String> stationCodeSet = new HashSet<>();
        if (jsonArray == null) {
            return stationCodeSet;
        }
        for (Object object : jsonArray.getJSONArray(1)) {
            stationCodeSet.add(object.toString());
        }
        return stationCodeSet;
    }
}
