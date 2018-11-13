package com.mit.community.rest;

import com.mit.community.service.TestBookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/testBook")
@Api(value = "测试书籍controller", tags = {"测试书籍接口"})
public class TestBookController {

    @Autowired
    private TestBookService testBookService;

    /**
     * 测试
     *
     * @throws
     * @param: @return
     * @return: String
     * @author shuyy
     * @company mitesofor
     */
    @GetMapping("ok")
    public Object test() {
        testBookService.insert();
        return "ok";
    }


}