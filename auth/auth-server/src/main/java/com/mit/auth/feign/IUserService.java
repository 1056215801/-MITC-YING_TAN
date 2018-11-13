package com.mit.auth.feign;

import com.mit.api.vo.user.UserInfo;
import com.mit.auth.configuration.FeignConfiguration;
import com.mit.auth.util.user.JwtAuthenticationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 远程调用用户service
* @author: shuyy
* @date: 2018/11/6 17:18
* @company mitesofor
*/
@FeignClient(value = "ace-admin",configuration = FeignConfiguration.class)
public interface IUserService {

  /**
  * 远程调用用户校验
  * @param authenticationRequest 用户实体类
  * @return:
  * @author: shuyy
  * @date: 2018/11/6 17:17
  * @company mitesofor
  */
  @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
  UserInfo validate(@RequestBody JwtAuthenticationRequest authenticationRequest);
}
