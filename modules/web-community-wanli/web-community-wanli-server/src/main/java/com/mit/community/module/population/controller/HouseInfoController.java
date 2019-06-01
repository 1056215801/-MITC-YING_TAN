package com.mit.community.module.population.controller;

import com.alibaba.fastjson.JSON;
import com.mit.community.entity.entity.HouseInfo;
import com.mit.community.population.service.HouseInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static com.mit.community.util.Utils.inputStream2String;

@RequestMapping(value = "/houseInfo")
@RestController
@Slf4j
@Api(tags = "人员房屋信息")
public class HouseInfoController {
    @Autowired
    private HouseInfoService houseInfoService;

    @PostMapping("/save")
    @ApiOperation(value = "保存房屋信息", notes = "传参：Integer person_baseinfo_id 基本信息id，json list包含的字段：String community_code 小区code, community_name 小区名称, zone_id 分区id, zone_name 分区名称, building_id 楼栋id, building_name 楼栋名称, unit_id 单元id, unit_name 单元名称, room_id房间id,\"\n" +
            "    + \"room_num 房号, household_type 房屋关系")
    public Result save(HttpServletRequest request){
        InputStream in = null;
        try{
            in = request.getInputStream();
            String params = inputStream2String(in, "");
            System.out.println(params);
            JSONObject jsonObject = JSONObject.fromObject(params);
            Integer person_baseinfo_id = jsonObject.getInt("person_baseinfo_id");
            JSONArray array = jsonObject.getJSONArray("data");
            List<HouseInfo> list = (List)JSONArray.toCollection(array, HouseInfo.class);
            for(int i=0;i < list.size();i++) {
                list.get(i).setPerson_baseinfo_id(person_baseinfo_id);
            }
            houseInfoService.save(list);
            return Result.success("房产信息保存成功");
        }catch(Exception e){
            e.printStackTrace();
            return Result.error("房产信息保存失败");
        }
    }
}
