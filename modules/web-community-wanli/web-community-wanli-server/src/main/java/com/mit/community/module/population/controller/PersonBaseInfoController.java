package com.mit.community.module.population.controller;


import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.CensusInfo;
import com.mit.community.entity.entity.FlowPeopleInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.population.service.CensusInfoService;
import com.mit.community.population.service.FlowPeopleService;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private RoomService roomService;

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
        if (StringUtils.isNotBlank(idCardNum)) {
            if (baseId != null) {
                personBaseInfoService.updateByIdCardNum(baseId, idCardNum, name, formerName, gender,
                        DateUtils.dateStrToLocalDateTime(birthday), nation, nativePlace, matrimony,
                        politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                        placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null, rksx,communityCode);
                return Result.success("信息更新成功");
            } else {
                Integer id = personBaseInfoService.save(idCardNum, name, formerName, gender, DateUtils.dateStrToLocalDateTime(birthday),
                        nation, nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                        placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null,communityCode);
                if (id == 0) {
                    return Result.error("身份证号码重复");
                }
                return Result.success(id);
            }
        } else {
            return Result.error("身份证号码不能为空");
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

    @PostMapping("/dataUpload")
    @ApiOperation(value = "上传人员数据信息", notes = "传参：community 分区，building 楼栋，unit 单元，room 房屋，relation 与户主关系，name 名字，sex 性别，idcard 身份证号码，phone 联系电话，nation 民族， jg 籍贯，" +
            "hyzk 婚姻状况, zzmm 政治面貌, job 职业，edu 学历， image 人脸图片， carNum 车牌号码")
    public Result dataUpload(String community, String building, String unit, String room, String relation, String name, String sex, String idcard, String phone, String nation, String jg, String hyzk, String zzmm, String job,
                             String edu, MultipartFile image, String carNum) throws Exception{
        System.out.println("================电话号码="+phone+",idCard="+idcard);
        String imageUrl = "";
        if (image != null) {
            String namephoto = image.getOriginalFilename();
            String ext = namephoto.substring(namephoto.lastIndexOf("."));
            String fileHz = name + UUID.randomUUID().toString() + ext;
            String basePath = "D:\\upload";
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            byte[] b = image.getBytes();
            imageUrl = basePath + "\\" +fileHz;
            File aa = new File(imageUrl);
            FileImageOutputStream fos = new FileImageOutputStream(aa);
            fos.write(b, 0, b.length);
            fos.close();
        }

        personBaseInfoService.dataUpload( community, building, unit, room, relation, name, sex, idcard, phone, nation, jg, hyzk, zzmm, job,
                 edu, imageUrl, carNum);
        return Result.success("保存成功");
    }


    @PostMapping("/getBuildinglist")
    @ApiOperation(value = "获取楼栋信息", notes = "communityName 小区名称")
    public Result getBuildinglist(String communityName){
        /*String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }*/
        Zone zone = zoneService.getByCommunityName(communityName);
        List<Building> list = buildingService.listByZoneId(zone.getZoneId());
        return Result.success(list);
    }

    @PostMapping("/getUnitlist")
    @ApiOperation(value = "获取单元信息", notes = "buildingId 楼栋id")
    public Result getUnitlist(Integer buildingId){
        /*String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }*/
        List<Unit> list = unitService.listByBuildingId(buildingId);
        return Result.success(list);
    }

    @PostMapping("/getRoomlist")
    @ApiOperation(value = "获取房间信息", notes = "unitId 单元id")
    public Result getRoomlist(Integer unitId){
        /*String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }*/
        List<Room> list = roomService.listByUnitId(unitId);
        return Result.success(list);
    }



}
