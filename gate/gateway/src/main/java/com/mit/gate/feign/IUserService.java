package com.mit.gate.feign;

import com.mit.gate.fallback.UserServiceFallback;
import com.mit.api.vo.authority.PermissionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 权限
 *
 * @author shuyy
 * @date 2018/11/7 11:33
 * @company mitesofor
 */
@FeignClient(value = "admin", fallback = UserServiceFallback.class)
public interface IUserService {
    /**
     * @param username
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:03 2018/11/9
     */
    @RequestMapping(value = "/api/user/un/{username}/permissions", method = RequestMethod.GET)
    List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);

    /**
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:03 2018/11/9
     */
    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET)
    List<PermissionInfo> getAllPermissionInfo();
}
