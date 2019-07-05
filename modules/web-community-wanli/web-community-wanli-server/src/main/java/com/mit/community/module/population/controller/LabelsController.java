package com.mit.community.module.population.controller;

import com.mit.community.entity.*;
import com.mit.community.entity.entity.*;
import com.mit.community.population.service.*;
import com.mit.community.service.LdzService;
import com.mit.community.service.OldService;
import com.mit.community.service.WgyService;
import com.mit.community.service.ZyzService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/labels")
@Slf4j
@Api(tags = "添加人员标签")
public class LabelsController {
    @Autowired
    private PartyInfoService partyInfoService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private PersonBaseInfoService personBaseInfoService;
    @Autowired
    private EngPeopleService engPeopleService;
    @Autowired
    private StayPeopleService stayPeopleService;
    @Autowired
    private BearInfoService bearInfoService;
    @Autowired
    private MilitaryServiceService militaryServiceService;
    @Autowired
    private XmsfPeopleService xmsfPeopleService;
    @Autowired
    private CXService cXService;
    @Autowired
    private SFPeopleService sFPeopleService;
    @Autowired
    private SQJZPeopleService sQJZPeopleService;
    @Autowired
    private ZSZHService zSZHService;
    @Autowired
    private XDService xDService;
    @Autowired
    private AZBService aZBService;
    @Autowired
    private ZDQSNCService zDQSNCService;
    @Autowired
    private ZyzService zyzService;
    @Autowired
    private OldService oldService;
    @Autowired
    private WgyService wgyService;
    @Autowired
    private LdzService ldzService;

    @PostMapping("/save")
    @ApiOperation(value = "保存人员标签", notes = "输入参数：")
    public Result save(String labels,Integer userId, String credentialNum, String householdName, String mobile){
        Integer personBaseInfoId = personBaseInfoService.getIdByCardNum(credentialNum);
        if (personBaseInfoId == null) {
            personBaseInfoId = personBaseInfoService.getIdByNameAndPhone(householdName, mobile);
        }
        if (personBaseInfoId != null) {
            String[] label = labels.split(",");
            for (int i=0; i<label.length;i++) {
                if ("境外人员".equals(label[i])) {
                    EngPeopleInfo engPeopleInfo = new EngPeopleInfo();
                    engPeopleInfo.setPerson_baseinfo_id(personBaseInfoId);
                    engPeopleService.save(engPeopleInfo);
                } else if ("留守人员".equals(label[i])) {
                    StayPeopleInfo stayPeopleInfo = new StayPeopleInfo();
                    stayPeopleInfo.setPerson_baseinfo_id(personBaseInfoId);
                    stayPeopleService.save(stayPeopleInfo);
                } else if ("党员".equals(label[i])) {
                    PartyInfo partyInfo = new PartyInfo();
                    partyInfo.setPerson_baseinfo_id(personBaseInfoId);
                    partyInfoService.save(partyInfo);
                } else if ("计生".equals(label[i])) {
                    BearInfo bearInfo = new BearInfo();
                    bearInfo.setPerson_baseinfo_id(personBaseInfoId);
                    bearInfoService.save(bearInfo);
                } else if ("兵役".equals(label[i])) {
                    MilitaryServiceInfo militaryServiceInfo = new MilitaryServiceInfo();
                    militaryServiceInfo.setPerson_baseinfo_id(personBaseInfoId);
                    militaryServiceService.save(militaryServiceInfo);
                } else if ("刑满释放".equals(label[i])) {
                    XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo();
                    xmsfPeopleInfo.setPerson_baseinfo_id(personBaseInfoId);
                    xmsfPeopleService.save(xmsfPeopleInfo);
                } else if ("疑似传销".equals(label[i])) {
                    CXInfo cXInfo = new CXInfo();
                    cXInfo.setPerson_baseinfo_id(personBaseInfoId);
                    cXService.save(cXInfo);
                } else if ("上访人员".equals(label[i])) {
                    SFPeopleInfo sFPeopleInfo = new SFPeopleInfo();
                    sFPeopleInfo.setPerson_baseinfo_id(personBaseInfoId);
                    sFPeopleService.save(sFPeopleInfo);
                } else if ("社区矫正".equals(label[i])) {
                    SQJZPeopleinfo sQJZPeopleinfo = new SQJZPeopleinfo();
                    sQJZPeopleinfo.setPerson_baseinfo_id(personBaseInfoId);
                    sQJZPeopleService.save(sQJZPeopleinfo);
                } else if ("肇事肇祸".equals(label[i])) {
                    ZSZHInfo zSZHInfo = new ZSZHInfo();
                    zSZHInfo.setPerson_baseinfo_id(personBaseInfoId);
                    zSZHService.save(zSZHInfo);
                } else if ("吸毒人员".equals(label[i])) {
                    XDInfo xDInfo = new XDInfo();
                    xDInfo.setPerson_baseinfo_id(personBaseInfoId);
                    xDService.save(xDInfo);
                } else if ("艾滋病危险人员".equals(label[i])) {
                    AzbInfo azbInfo = new AzbInfo();
                    azbInfo.setPerson_baseinfo_id(personBaseInfoId);
                    aZBService.save(azbInfo);
                } else if ("重点青少年".equals(label[i])) {
                    ZDQSNCInfo zDQSNCInfo = new ZDQSNCInfo();
                    zDQSNCInfo.setPerson_baseinfo_id(personBaseInfoId);
                    zDQSNCService.save(zDQSNCInfo);
                } else if ("志愿者".equals(label[i])) {
                    ZyzInfo zyzInfo = new ZyzInfo();
                    zyzInfo.setPerson_baseinfo_id(personBaseInfoId);
                    zyzService.save(zyzInfo);
                } else if ("老人".equals(label[i])) {
                    OldInfo oldInfo = new OldInfo();
                    oldInfo.setPerson_baseinfo_id(personBaseInfoId);
                    oldService.save(oldInfo);
                } else if ("网格员".equals(label[i])) {
                    WgyInfo wgyInfo = new WgyInfo();
                    wgyInfo.setPerson_baseinfo_id(personBaseInfoId);
                    wgyService.save(wgyInfo);
                } else if ("楼栋长".equals(label[i])) {
                    LdzInfo ldzInfo = new LdzInfo();
                    ldzInfo.setPerson_baseinfo_id(personBaseInfoId);
                    ldzService.save(ldzInfo);
                }
            }
        } else {
            return Result.error("保存失败，未查找到该人员信息");
        }
        //personLabelsService.saveLabels(labels, userId);
        return Result.success("保存成功");
    }

    @PostMapping("/getLabels")
    @ApiOperation(value = "获取人员标签", notes = "输入参数：")
    public Result save(Integer household){
        String labels = personLabelsService.getLabelsByHousehold(household);
        return Result.success(labels);
    }

    @PostMapping("/getPeopleCount")
    @ApiOperation(value = "获取驻留人数", notes = "输入参数：")
    public Result getPeopleCount(){//正式的需要传入楼栋信息，摄像头的地址要和小区关联
        int count = personLabelsService.getPeopleCount();
        int outCount = personLabelsService.getOutCount();
        int peopleCount = count - outCount;
        return Result.success(peopleCount);
    }

    @GetMapping("/getPeopleOue")
    @ApiOperation(value = "获取出去的人", notes = "输入参数：")
    public Result getPeopleOue(){//正式的需要传入楼栋信息，摄像头的地址要和小区关联
        PeopleOut peopleOut = personLabelsService.getPeopleOue();
        return Result.success(peopleOut);
    }

}
