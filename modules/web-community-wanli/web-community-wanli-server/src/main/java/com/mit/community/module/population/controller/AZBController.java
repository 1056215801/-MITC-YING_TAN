package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.AzbInfo;
import com.mit.community.population.service.AZBService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 艾滋病
 * @author xq
 * @date 2019/6/8
 * @company mitesofor
 */
@RequestMapping(value = "/azb")
@RestController
@Slf4j
@Api(tags = "艾滋病人员信息")
public class AZBController {
    @Autowired
    private AZBService aZBService;

    @PostMapping("/save")
    @ApiOperation(value = "保存艾滋病人员信息", notes = "传参：String grtj 感染途径, String sfwf 是否有违法犯罪史, String wffzqk 违法犯罪情况, String ajlb 案件类别, String gzlx 关注类型, String bfqk 帮扶情况, String bfrdh 帮扶人联系方式\n" +
            "    ,String bfrxm 帮扶人姓名, String szqk 收治情况, String szjgmc 收治机构名称, Integer person_baseinfo_id 基本信息id")
    public Result save(String grtj, String sfwf, String wffzqk, String ajlb, String gzlx, String bfqk, String bfrdh
            , String bfrxm, String szqk, String szjgmc, Integer person_baseinfo_id) {
        aZBService.save(grtj, sfwf, wffzqk, ajlb, gzlx, bfqk, bfrdh, bfrxm, szqk, szjgmc, person_baseinfo_id);
        return Result.success("保存成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新艾滋病人员信息", notes = "传参：String grtj 感染途径, String sfwf 是否有违法犯罪史, String wffzqk 违法犯罪情况, String ajlb 案件类别, String gzlx 关注类型, String bfqk 帮扶情况, String bfrdh 帮扶人联系方式\n" +
            "    ,String bfrxm 帮扶人姓名, String szqk 收治情况, String szjgmc 收治机构名称, Integer person_baseinfo_id 基本信息id")
    public Result update(String grtj, String sfwf, String wffzqk, String ajlb, String gzlx, String bfqk, String bfrdh
            , String bfrxm, String szqk, String szjgmc, Integer person_baseinfo_id, int isDelete) {
        AzbInfo azbInfo = new AzbInfo(grtj, sfwf, wffzqk, ajlb, gzlx, bfqk, bfrdh, bfrxm, szqk, szjgmc, person_baseinfo_id, isDelete);
        aZBService.save(azbInfo);
        return Result.success("更新成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除艾滋病人员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id) {
        aZBService.delete(id);
        return Result.success("删除成功");
    }
}
