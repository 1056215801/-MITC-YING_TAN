package com.mit.community.module.bigdata.controller;

import com.mit.community.service.HouseHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 大数据平台
 *
 * @author shuyy
 * @date 2018/11/16
 * @company mitesofor
 */
@RestController
@RequestMapping("bigData")
public class BigDataController {

    @Autowired
    private HouseHoldService houseHoldService;

    @GetMapping("test")
    public String test(Integer householdId) throws IOException {
        String result = houseHoldService.getCredentialNumFromDnake(householdId);
        return result;

    }


}
