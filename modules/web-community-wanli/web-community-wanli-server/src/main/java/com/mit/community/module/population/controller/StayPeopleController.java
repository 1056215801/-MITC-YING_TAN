package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.StayPeopleInfo;
import com.mit.community.population.service.StayPeopleService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/stayPeople")
@RestController
@Slf4j
@Api(tags = "留守人员信息")
public class StayPeopleController {
    @Autowired
    private StayPeopleService stayPeopleService;

    @PostMapping("/save")
    @ApiOperation(value = "保存留守人员信息", notes = "传参：String jkzk 健康状况, String grnsr 个人年收入, String rhizbz 人户一致标识, String lsrylx 留守人员类型, String jtzyrysfzh 家庭主要成员身份证号码, String jtzycyxm 家庭主要成员姓名, String jtzycyjkzk 家庭主要成员健康状况, String ylsrygx 与留守人员关系, String jtzycylxfs 家庭主要成员联系方式,\n" +
            "String jtzycygzxxdz 家庭主要成员工作详细地址, String jtnsr 家庭年收入, String knjsq 困难及诉求, String bfqk 帮扶情况")
    public Result save(String jkzk, String grnsr, String rhizbz, String lsrylx, String jtzyrysfzh, String jtzycyxm, String jtzycyjkzk, String ylsrygx, String jtzycylxfs,
                       String jtzycygzxxdz, String jtnsr, String knjsq, String bfqk, Integer person_baseinfo_id){
        stayPeopleService.save(jkzk, grnsr, rhizbz, lsrylx, jtzyrysfzh, jtzycyxm, jtzycyjkzk, ylsrygx, jtzycylxfs, jtzycygzxxdz, jtnsr, knjsq, bfqk, person_baseinfo_id);
        return Result.success("留守人员信息保存成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新留守人员信息", notes = "传参：String jkzk 健康状况, String grnsr 个人年收入, String rhizbz 人户一致标识, String lsrylx 留守人员类型, String jtzyrysfzh 家庭主要成员身份证号码, String jtzycyxm 家庭主要成员姓名, String jtzycyjkzk 家庭主要成员健康状况, String ylsrygx 与留守人员关系, String jtzycylxfs 家庭主要成员联系方式,\n" +
            "String jtzycygzxxdz 家庭主要成员工作详细地址, String jtnsr 家庭年收入, String knjsq 困难及诉求, String bfqk 帮扶情况")
    public Result update(String jkzk, String grnsr, String rhizbz, String lsrylx, String jtzyrysfzh, String jtzycyxm, String jtzycyjkzk, String ylsrygx, String jtzycylxfs,
                       String jtzycygzxxdz, String jtnsr, String knjsq, String bfqk, Integer person_baseinfo_id, int isDelete){
        StayPeopleInfo stayPeopleInfo = new StayPeopleInfo(jkzk, grnsr, rhizbz, lsrylx, jtzyrysfzh, jtzycyxm, jtzycyjkzk, ylsrygx, jtzycylxfs, jtzycygzxxdz, jtnsr, knjsq, bfqk, person_baseinfo_id, isDelete);
        stayPeopleService.save(stayPeopleInfo);
        return Result.success("留守人员信息更新成功");

    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除留守人员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id){
        stayPeopleService.delete(id);
        return Result.success("删除成功");
    }

}
