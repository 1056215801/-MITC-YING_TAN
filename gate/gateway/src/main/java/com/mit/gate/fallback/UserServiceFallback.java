package com.mit.gate.fallback;

import com.mit.gate.feign.IUserService;
import com.mit.api.vo.authority.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * fallback
 * @author shuyy
 * @date 2018/11/7 11:33
 * @company mitesofor
*/
@Service
@Slf4j
public class UserServiceFallback implements IUserService {
    @Override
    public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username) {
        log.error("调用{}异常{}","getPermissionByUsername",username);
        return null;
    }

    @Override
    public List<PermissionInfo> getAllPermissionInfo() {
        log.error("调用{}异常","getPermissionByUsername");
        return null;
    }
}
