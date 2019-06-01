package com.mit.community.module.population.controller;

import com.alibaba.fastjson.JSON;
import com.mit.community.entity.entity.CarInfo;
import com.mit.community.entity.entity.HouseInfo;
import com.mit.community.population.service.CarInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import static com.mit.community.util.Utils.inputStream2String;

/**
 * 车辆信息
 */

@RequestMapping(value = "/carInfo")
@RestController
@Slf4j
@Api(tags = "车辆信息")
public class CarInfoController {
    @Autowired
    private CarInfoService carInfoService;

    @PostMapping("/save")
    @ApiOperation(value = "保存车辆信息", notes = "传参：Integer person_baseinfo_id 基本信息id，json list包含的字段：String cph 车牌号, String cx 车型, String ys 颜色, String pp 品牌, String xh 型号, String pl 排量, String fdjh 发动机号, String jsz 驾驶证, String xsz 行驶证,"
    + "LocalDateTime szrq 生产日期, LocalDateTime gmrq 购买日期，String ajqk 按揭情况")
    public Result save(HttpServletRequest request){
        InputStream in = null;
        try{
            in = request.getInputStream();
            String params = inputStream2String(in, "");
            System.out.println(params);
            JSONObject jsonObject = JSONObject.fromObject(params);
            Integer person_baseinfo_id = jsonObject.getInt("person_baseinfo_id");
            JSONArray array = jsonObject.getJSONArray("data");
            List<CarInfo> list = (List)JSONArray.toCollection(array, CarInfo.class);
            for(int i=0;i < list.size();i++) {
                list.get(i).setPerson_baseinfo_id(person_baseinfo_id);
            }
            carInfoService.save(list);
            return Result.success("房产信息保存成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("房产信息保存成功");
        }
    }

    public String inputStream2String(InputStream in, String coding) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
