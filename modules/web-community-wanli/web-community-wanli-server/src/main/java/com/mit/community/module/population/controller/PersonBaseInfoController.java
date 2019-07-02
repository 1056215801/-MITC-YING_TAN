package com.mit.community.module.population.controller;


import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.entity.CensusInfo;
import com.mit.community.entity.entity.FlowPeopleInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.population.service.CensusInfoService;
import com.mit.community.population.service.FlowPeopleService;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.service.RedisService;
import com.mit.community.service.UserService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * 人员基本信息
 *
 * @author xiong
 * @date 2019/5/25
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: mitesofor </p>
 */
@RequestMapping(value = "/personBaseInfo")
@RestController
@Slf4j
@Api(tags = "人员基本信息")
public class PersonBaseInfoController {
    @Autowired
    private PersonBaseInfoService personBaseInfoService;
    @Autowired
    private FlowPeopleService flowPeopleService;
    @Autowired
    private CensusInfoService censusInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/savePersonBaseInfo")
    @ApiOperation(value = "保存人员基本信息", notes = "传参：String idCardNum 公民身份号码, String name 姓名, String formerName 曾用名, String gender 性别, LocalDate birthday 出生日期, String nation 民族, String nativePlace 籍贯, String matrimony 婚姻状况, String politicCountenance 政治面貌,\n" +
            "String education 学历, String religion 宗教信仰, String jobType 职业类别, String profession 职业, String cellphone 联系方式, String placeOfDomicile 户籍地, String placeOfDomicileDetail 户籍门详址, String placeOfReside 现住地,\n" +
            "String placeOfResideDetail 现住门详址, String placeOfServer 服务处所, String photoBase64 照片base64")
    public Result savePersonBaseInfo(HttpServletRequest request, Integer baseId,
                                     String idCardNum,
                                     String name,
                                     String formerName,
                                     String gender,
                                     @RequestParam(required = false) String birthday,
                                     String nation, String nativePlace, String matrimony, String politicCountenance,
                                     String education, String religion, String jobType, String profession, String cellphone,
                                     String placeOfDomicile, String placeOfDomicileDetail, String placeOfReside,
                                     String placeOfResideDetail, String placeOfServer, Integer rksx) throws ParseException {

            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            String communityCode = sysUser.getCommunityCode();

        //if (personBaseInfoService.isExist(idCardNum)) {//已经存在就更新
        if (baseId != null) {
            /*personBaseInfoService.updateByIdCardNum(age, idCardNum, name, formerName, gender,
                    DateUtils.dateStrToLocalDateTime(birthday), nation, nativePlace, matrimony,
                    politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                    placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null);*/
            personBaseInfoService.updateByIdCardNum(baseId, idCardNum, name, formerName, gender,
                    DateUtils.dateStrToLocalDateTime(birthday), nation, nativePlace, matrimony,
                    politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                    placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null, rksx,communityCode);
            return Result.success("信息更新成功");
        } else {
            Integer id = personBaseInfoService.save(idCardNum, name, formerName, gender, DateUtils.dateStrToLocalDateTime(birthday),
                    nation, nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                    placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null,communityCode);
            return Result.success(id);
        }
    }


    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/6 10:56
     * @Company mitesofor
     * @Description:~根据主键查询基本信息
     */
    @RequestMapping("/getPersonBaseInfo")
    @ApiOperation(value = "获取基本信息", notes = "主键：id")
    public Result getObjectById(Integer id) {
        PersonBaseInfo personBaseInfo = personBaseInfoService.getObjectById(id);
        return Result.success(personBaseInfo);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/6 12:52
     * @Company mitesofor
     * @Description:~获取人员成分信息
     */
    @RequestMapping("/getComposition")
    @ApiOperation(value = "查询人员成分信息", notes = "id：主键,rksx：成分属性")
    public Result getComposition(Integer id, Integer rksx) {
        if (rksx == 0) {//未录入
            return Result.error("null");
        }
        if (rksx == 1) {//户籍人口
            CensusInfo info = censusInfoService.getObjectById(id);
            return Result.success(info);
        }
        if (rksx == 2) {//流动人口
            FlowPeopleInfo info = flowPeopleService.getObjectById(id);
            return Result.success(info);
        }
        return null;
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除人员基本信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id) {
        personBaseInfoService.delete(id);
        return Result.success("删除成功");
    }
}
