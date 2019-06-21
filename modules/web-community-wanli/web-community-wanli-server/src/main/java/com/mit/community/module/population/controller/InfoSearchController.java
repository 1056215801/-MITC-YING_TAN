package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ExcelData;
import com.mit.community.entity.entity.InfoSearch;
import com.mit.community.population.service.InfoSearchService;
import com.mit.community.service.LabelsService;
import com.mit.community.util.ExcelUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/infoSearch")
@RestController
@Slf4j
@Api(tags = "人员信息搜索")
public class InfoSearchController {
    @Autowired
    private InfoSearchService infoSearchService;
    @Autowired
    private LabelsService labelsService;

    @PostMapping("/listPage")
    @ApiOperation(value = "人员信息分页查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result listPage(@RequestParam(required = false, defaultValue = "0") Integer ageStart,
                           @RequestParam(required = false, defaultValue = "0") Integer ageEnd,
                           String name, String idNum, String sex, String education, String job,
                           String matrimony, String zzmm, String rycf, String label, Integer pageNum, Integer pageSize) {
        Page<InfoSearch> page = infoSearchService.listPage(ageStart, ageEnd, name, idNum,
                sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf);
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

    @PostMapping("/getInfoExcel")
    @ApiOperation(value = "人员信息导出excel", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result getInfoExcel(HttpServletResponse response, HttpServletRequest request,
                               @RequestParam(required = false, defaultValue = "0") Integer ageStart,
                               @RequestParam(required = false, defaultValue = "0") Integer ageEnd,
                               String name, String idNum, String sex, String education, String job,
                               String matrimony, String zzmm, String rycf, String label, Integer pageNum, Integer pageSize) throws Exception {
        Page<InfoSearch> page = infoSearchService.listPage(ageStart, ageEnd, name, idNum,
                sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf);
        List<InfoSearch> list = page.getRecords();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                List<String> labels = labelsService.getLabelsByUserId(list.get(i).getPersonBaseInfo().getId());
                list.get(i).getPersonBaseInfo().setLabels(labels);
            }
        }
        ExcelData data = new ExcelData();
        data.setName("用户信息数据");
        List<String> titles = new ArrayList<>();
        titles.add("姓名");
        titles.add("性别");
        titles.add("年龄");
        titles.add("身份证号码");
        titles.add("人口属性");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        List<Object> row = null;
        for (int i = 0; i < page.getRecords().size(); i++) {
            row = new ArrayList<>();
            row.add(page.getRecords().get(i).getPersonBaseInfo().getName());
            row.add(page.getRecords().get(i).getPersonBaseInfo().getGender());
            row.add(page.getRecords().get(i).getPersonBaseInfo().getAge());
            row.add(page.getRecords().get(i).getPersonBaseInfo().getIdCardNum());
            row.add(page.getRecords().get(i).getPersonBaseInfo().getRksx() == 1 ? "户籍人口" : "流动人口");
            rows.add(row);
        }
        data.setRows(rows);
        SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = fdate.format(new Date()) + ".xlsx";
        String basePath = request.getServletContext().getRealPath("excel/");
        //String basePath = request.getServletContext().getRealPath("imgs/");
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdir();
        }
        ExcelUtils.generateExcel(data, basePath + fileName);
        //ExcelUtils.exportExcel(response, fileName, data);
        page.setRecords(list);
        return Result.success("http://127.0.0.1:9766/api/web/communitywanli/excel/" + fileName);
    }



    @PostMapping("/flowListPage")
    @ApiOperation(value = "流动人口信息查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result flowListPage(@RequestParam(required = false, defaultValue = "0") Integer ageStart,
                           @RequestParam(required = false, defaultValue = "0") Integer ageEnd,
                           String name, String idNum, String sex, String education, String job,
                           String matrimony, String zzmm, String rycf, String label, Integer pageNum, Integer pageSize) throws Exception {
        Page<InfoSearch> page = infoSearchService.listPage(ageStart, ageEnd, name, idNum,
                sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf);
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

    /*@PostMapping("/test")
    @ApiOperation(value = "人员信息分页查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result test() {
        String day = infoSearchService.getById(1372220);
        return Result.success(day);
    }*/



}
