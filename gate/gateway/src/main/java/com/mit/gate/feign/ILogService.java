package com.mit.gate.feign;

import com.mit.api.vo.log.LogInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 日志
 *
 * @author shuyy
 * @date 2018/11/7 11:33
 * @company mitesofor
 */
@FeignClient("ace-admin")
public interface ILogService {
    /**
     * @param info
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:03 2018/11/9
     */
    @RequestMapping(value = "/api/log/save", method = RequestMethod.POST)
    void saveLog(LogInfo info);
}
