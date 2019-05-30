package com.mit.community.module.population.controller;


import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.service.UserService;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private UserService userService;

    @PostMapping("/savePersonBaseInfo")
    @ApiOperation(value = "保存人员基本信息", notes = "传参：String idCardNum 公民身份号码, String name 姓名, String formerName 曾用名, String gender 性别, LocalDate birthday 出生日期, String nation 民族, String nativePlace 籍贯, String matrimony 婚姻状况, String politicCountenance 政治面貌,\n" +
            "String education 学历, String religion 宗教信仰, String jobType 职业类别, String profession 职业, String cellphone 联系方式, String placeOfDomicile 户籍地, String placeOfDomicileDetail 户籍门详址, String placeOfReside 现住地,\n" +
            "String placeOfResideDetail 现住门详址, String placeOfServer 服务处所, String photoBase64 照片base64")
    public Result savePersonBaseInfo(String idCardNum,
                                     String name,
                                     String formerName,
                                     String gender,
                                     @RequestParam(required = false) String birthday,
                                     String nation, String nativePlace, String matrimony, String politicCountenance,
                                     String education, String religion, String jobType, String profession, String cellphone,
                                     String placeOfDomicile, String placeOfDomicileDetail, String placeOfReside,
                                     String placeOfResideDetail, String placeOfServer) throws ParseException {
        //User user = userService.getUserByCardNum(idCardNum);
        Integer id = personBaseInfoService.save(idCardNum, name, formerName, gender, DateUtils.dateStrToLocalDateTime(birthday),
                nation, nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null);
        return Result.success(id);
    }

}
