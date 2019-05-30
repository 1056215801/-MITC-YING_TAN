package com.mit.community.module.population.controller;

import com.alibaba.fastjson.JSON;
import com.mit.community.entity.entity.HouseInfo;
import com.mit.community.population.service.HouseInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/houseInfo")
@RestController
@Slf4j
@Api(tags = "人员房屋信息")
public class HouseInfoController {
    @Autowired
    private HouseInfoService houseInfoService;

    public Result save(Integer person_baseinfo_id, String json){
        if (StringUtils.isNotBlank(json)) {
            List<HouseInfo> list = JSON.parseArray(json,HouseInfo.class);
            for(int i=0; i < list.size(); i++){
                list.get(i).setPerson_baseinfo_id(person_baseinfo_id);
            }
            houseInfoService.save(list);
            return Result.success("房产信息保存成功");
        }
        return Result.error("房产信息保存失败");

    }
}
