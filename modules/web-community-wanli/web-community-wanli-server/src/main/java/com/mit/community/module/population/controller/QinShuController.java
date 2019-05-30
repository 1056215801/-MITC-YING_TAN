package com.mit.community.module.population.controller;

import com.alibaba.fastjson.JSON;
import com.mit.community.entity.entity.QinShuInfo;
import com.mit.community.population.service.QinShuService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/qinshu")
@RestController
@Slf4j
@Api(tags = "人员亲属关系信息")
public class QinShuController {
    @Autowired
    private QinShuService qinShuService;

    public Result save(Integer person_baseinfo_id, String json){
        if (StringUtils.isNotBlank(json)) {
            List<QinShuInfo> list = JSON.parseArray(json,QinShuInfo.class);
            for(int i=0; i < list.size(); i++){
                list.get(i).setPerson_baseinfo_id(person_baseinfo_id);
            }
            qinShuService.save(list);
            return Result.success("亲属关系保存成功");
        }
        return Result.error("亲属关系保存失败");
    }

}
