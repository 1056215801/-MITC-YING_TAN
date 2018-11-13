package com.mit.cloud.rest;

import com.mit.cloud.dao.TestBookMapper;
import com.mit.cloud.model.TestBook;
import com.mit.cloud.service.TestBookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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