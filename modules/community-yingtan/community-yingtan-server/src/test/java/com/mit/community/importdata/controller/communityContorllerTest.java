package com.mit.community.importdata.controller;

import com.mit.community.service.IClusterCommunityService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Deng
 * @date 2018/11/14 13:54
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@Slf4j
@Api(value = "数据测试")
public class communityContorllerTest {

    @Autowired
    private IClusterCommunityService clusterCommunity;

    @RequestMapping("/clusterCommunity")
    public String clusterCommunity() {
        return "OK";
    }

}
