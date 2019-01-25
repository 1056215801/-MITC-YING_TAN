package com.mit.community.config;

import com.mit.community.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author shuyy
 * @date 2018/11/8 8:40
 * @company mitesofor
 */
@Slf4j
@ControllerAdvice("com.mit")
@ResponseBody
public class CustomerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result otherExceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error("系统繁忙");
    }

}
