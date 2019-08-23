package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.CarPerception;
import com.mit.community.entity.ExcelData;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.entity.InfoSearch;
import com.mit.community.population.service.InfoSearchService;
import com.mit.community.service.LabelsService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.ExcelUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员信息搜索
 * @author xq
 * @date 2019/6/10
 * @company mitesofor
 */
@RequestMapping(value = "/infoSearch")
@RestController
@Slf4j
@Api(tags = "人员信息搜索")
public class InfoSearchController {
    @Autowired
    private InfoSearchService infoSearchService;
    @Autowired
    private LabelsService labelsService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/listPage")
    @ApiOperation(value = "人员信息分页查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result listPage(HttpServletRequest request,@RequestParam(required = false, defaultValue = "0") Integer ageStart,
                           @RequestParam(required = false, defaultValue = "0") Integer ageEnd,
                           String name, String idNum, String sex, String education, String job,
                           String matrimony, String zzmm, String rycf, String label, Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String accountType = sysUser.getAccountType();
        String streetName = sysUser.getStreetName();
        String areaName = sysUser.getAreaName();

        Page<InfoSearch> page = infoSearchService.listPage(sysUser.getCommunityCode(),ageStart, ageEnd, name, idNum,
                sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf, accountType, streetName, areaName);
        List<InfoSearch> list = page.getRecords();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                List<String> labels = labelsService.getLabelsByUserId(list.get(i).getPersonBaseInfo().getId());
                list.get(i).getPersonBaseInfo().setLabels(labels);
            }
        }
        page.setRecords(list);
        return Result.success(page);
    }

    @PostMapping("/getLabelInfo")
    @ApiOperation(value = "人员标签信息", notes = "传参：Integer person_baseinfo_id")
    public Result getLabelInfo(Integer person_baseinfo_id) {
        Map<String, Object> map = infoSearchService.getLabelInfo(person_baseinfo_id);
        return Result.success(map);
    }


    @PostMapping("/flowListPage")
    @ApiOperation(value = "流动人口信息查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result flowListPage(HttpServletRequest request,@RequestParam(required = false, defaultValue = "0") Integer ageStart,
                           @RequestParam(required = false, defaultValue = "0") Integer ageEnd,
                           String name, String idNum, String sex, String education, String job,
                           String matrimony, String zzmm, String rycf, String label, Integer pageNum, Integer pageSize) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String accountType = sysUser.getAccountType();
        String streetName = sysUser.getStreetName();
        String areaName = sysUser.getAreaName();

        Page<InfoSearch> page = infoSearchService.listPage(sysUser.getCommunityCode(),ageStart, ageEnd, name, idNum,
                sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf, accountType, streetName, areaName);
        List<InfoSearch> list = page.getRecords();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                List<String> labels = labelsService.getLabelsByUserId(list.get(i).getPersonBaseInfo().getId());
                list.get(i).getPersonBaseInfo().setLabels(labels);
                String validityDay = infoSearchService.getByPhone(list.get(i).getPersonBaseInfo().getCellphone());//通过电话关联上
                if (StringUtils.isNotBlank(validityDay)) {
                    //先把字符串转成Date类型
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //此处会抛异常
                    Date date = sdf.parse(validityDay);
                    //获取截止日期毫秒数
                    long longDate = date.getTime();
                    long now = System.currentTimeMillis();
                    int days = (int) ((longDate - now) / (1000*3600*24));
                    list.get(i).setValidityDays(days);
                }
            }
        }
        page.setRecords(list);
        return Result.success(page);
    }


    /*@PostMapping("/carPerception")
    @ApiOperation(value = "车辆感知", notes = "")
    public Result carPerception(HttpServletRequest request, String communityCode) {
        CarPerception carPerception = new CarPerception();
        Map<String,String> sr = new HashedMap();
        sr.put("0","3");
        sr.put("1","4");
        sr.put("2","4");
        sr.put("3","4");
        sr.put("4","4");
        sr.put("5","4");
        sr.put("6","4");
        sr.put("7","4");
        sr.put("8","4");
        sr.put("9","4");
        sr.put("10","4");
        sr.put("11","4");
        sr.put("12","4");
        sr.put("13","4");
        sr.put("14","4");
        sr.put("15","4");
        sr.put("16","4");
        sr.put("17","4");
        sr.put("18","4");
        sr.put("19","4");
        sr.put("20","4");
        sr.put("21","4");
        sr.put("22","4");
        sr.put("23","4");

        Map<String,String> sc = new HashedMap();
        sc.put("0","3");
        sc.put("1","4");
        sc.put("2","4");
        sc.put("3","4");
        sc.put("4","4");
        sc.put("5","4");
        sc.put("6","4");
        sc.put("7","4");
        sc.put("8","4");
        sc.put("9","4");
        sc.put("10","4");
        sc.put("11","4");
        sc.put("12","4");
        sc.put("13","4");
        sc.put("14","4");
        sc.put("15","4");
        sc.put("16","4");
        sc.put("17","4");
        sc.put("18","4");
        sc.put("19","4");
        sc.put("20","4");
        sc.put("21","4");
        sc.put("22","4");
        sc.put("23","4");

        carPerception.setSr(sr);
        carPerception.setSc(sc);
        return Result.success(carPerception);
    }*/



}
