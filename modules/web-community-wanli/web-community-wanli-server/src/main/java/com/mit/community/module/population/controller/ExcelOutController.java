package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ExcelData;
import com.mit.community.entity.entity.*;
import com.mit.community.population.service.ExcelOutService;
import com.mit.community.population.service.InfoSearchService;
import com.mit.community.service.LabelsService;
import com.mit.community.util.ExcelUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

@RequestMapping(value = "/excelOut")
@RestController
@Slf4j
@Api(tags = "excel导出")
public class ExcelOutController {
    @Autowired
    private ExcelOutService excelOutService;
    @Autowired
    private InfoSearchService infoSearchService;
    @Autowired
    private LabelsService labelsService;

    @PostMapping("/getInfoExcel")
    @ApiOperation(value = "人员信息导出excel", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result getInfoExcel(HttpServletResponse response, HttpServletRequest request, @RequestParam( required = false, defaultValue = "0")Integer ageStart, @RequestParam( required = false, defaultValue = "0")Integer ageEnd, String name, String idNum, String sex, String education, String job, String matrimony, String zzmm, String label, String rycf) throws Exception{
        List<InfoSearch> list = infoSearchService.list(ageStart, ageEnd, name, idNum, sex, education, job, matrimony, zzmm, label, rycf);
        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                System.out.println("==========id="+list.get(i).getPersonBaseInfo().getId());
                List<String> labels = labelsService.getLabelsByUserId(list.get(i).getPersonBaseInfo().getId());
                list.get(i).getPersonBaseInfo().setLabels(labels);
            }
        }
        ExcelData data = new ExcelData();
        data.setName("用户基本信息");
        List<String> titles = new ArrayList<>();
        titles.add("姓名");
        titles.add("性别");
        titles.add("年龄");
        titles.add("身份证号码");
        titles.add("婚姻状况");
        titles.add("政治面貌");
        titles.add("联系方式");
        titles.add("家庭地址");
        titles.add("人口属性");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        List<Object> row = null;
        for(int i=0;i<list.size();i++) {
            row = new ArrayList<>();
            row.add(list.get(i).getPersonBaseInfo().getName());
            row.add(list.get(i).getPersonBaseInfo().getGender());
            row.add(list.get(i).getPersonBaseInfo().getAge());
            row.add(list.get(i).getPersonBaseInfo().getIdCardNum());
            row.add(list.get(i).getPersonBaseInfo().getMatrimony());
            row.add(list.get(i).getPersonBaseInfo().getPoliticCountenance());
            row.add(list.get(i).getPersonBaseInfo().getCellphone());
            row.add(list.get(i).getPersonBaseInfo().getPlaceOfResideDetail());
            row.add(list.get(i).getPersonBaseInfo().getRksx() == 1 ? "户籍人口":"流动人口");
            rows.add(row);
        }
        data.setRows(rows);
        SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName=fdate.format(new Date())+".xlsx";
        String basePath = request.getServletContext().getRealPath("excel/");
        System.out.println("==========="+basePath);
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdir();
        }
        ExcelUtils.generateExcel(data,basePath+fileName);
        return Result.success("http://127.0.0.1:9766/excel/"+fileName);
    }

    @PostMapping("/getAzbExcel")
    @ApiOperation(value = "艾滋病人excel", notes = "")
    public Result getAzbExcel(HttpServletRequest request) throws Exception{
        String fileName = null;
        List<AzbExcelInfo> list = excelOutService.getAzbExcel();
        if(!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("艾滋病人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");
            titles.add("感染途径");
            titles.add("是否有违法犯罪史");
            titles.add("违法犯罪情况");
            titles.add("案件类别");
            titles.add("关注类型");
            titles.add("帮扶情况");
            titles.add("帮扶人联系方式");
            titles.add("帮扶人姓名");
            titles.add("收治情况");
            titles.add("收治机构名称");
            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for(int i=0;i<list.size();i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());
                row.add(list.get(i).getGrtj());
                row.add(list.get(i).getSfwf());
                row.add(list.get(i).getWffzqk());
                row.add(list.get(i).getAjlb());
                row.add(list.get(i).getGzlx());
                row.add(list.get(i).getBfqk());
                row.add(list.get(i).getBfrdh());
                row.add(list.get(i).getBfrxm());
                row.add(list.get(i).getSzqk());
                row.add(list.get(i).getSzjgmc());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            fileName="艾滋病人员信息" + fdate.format(new Date())+".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("==========="+basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data,basePath+fileName);
            return Result.success("http://127.0.0.1:9766/excel/"+fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getBearExcel")
    @ApiOperation(value = "计生excel", notes = "")
    public Result getBearExcel(HttpServletRequest request) throws Exception{
        List<BearExcelInfo> list = excelOutService.getBearExcel();
        if(!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("艾滋病人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("配偶姓名");
            titles.add("配偶性别");
            titles.add("现工作单位或家庭住址");
            titles.add("登记结婚年月");
            titles.add("户口性质");
            titles.add("婚姻状况");
            titles.add("节育手术时间");
            titles.add("手术类型");
            titles.add("手术医院");
            titles.add("审查原因");
            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for(int i=0;i<list.size();i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());
                row.add(list.get(i).getPoxm());
                row.add(list.get(i).getPoxb());
                row.add(list.get(i).getXgzdw());
                row.add(list.get(i).getDjjhny());
                row.add(list.get(i).getHkxz());
                row.add(list.get(i).getHyzk());
                row.add(list.get(i).getJysssj());
                row.add(list.get(i).getSslx());
                row.add(list.get(i).getSsyy());
                row.add(list.get(i).getCcyy());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName="计生人员信息" + fdate.format(new Date())+".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("==========="+basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data,basePath+fileName);
            return Result.success("http://127.0.0.1:9766/excel/"+fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getEngPeopleExcel")
    @ApiOperation(value = "境外excel", notes = "")
    public Result getEngPeopleExcel(HttpServletRequest request) throws Exception{
        List<EngPeopleExcelInfo> list = excelOutService.getEngPeopleExcel();
        if(!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("艾滋病人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("外文姓");
            titles.add("外文名");
            titles.add("中文姓名");
            titles.add("国籍（地区）");
            titles.add("宗教信仰");
            titles.add("证件代码");
            titles.add("证件号码");
            titles.add("证件有效期");
            titles.add("来华目的");
            titles.add("抵达日期");
            titles.add("预计离开日期");
            titles.add("是否重点关注人员");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for(int i=0;i<list.size();i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName="传销人员信息" + fdate.format(new Date())+".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("==========="+basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data,basePath+fileName);
            return Result.success("http://127.0.0.1:9766/excel/"+fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getCXExcel")
    @ApiOperation(value = "传销excel", notes = "")
    public Result getCXExcel(HttpServletRequest request) throws Exception{
        List<CXExcelInfo> list = excelOutService.getCXExcel();
        if(!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("艾滋病人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("第一涉嫌传销及相关情况");
            titles.add("第二涉嫌传销及相关情况");
            titles.add("第三涉嫌传销及相关情况");
            titles.add("备注");
            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for(int i=0;i<list.size();i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());
                row.add(list.get(i).getDysxcx());
                row.add(list.get(i).getDrsxcx());
                row.add(list.get(i).getDssxcx());
                row.add(list.get(i).getBz());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName="传销人员信息" + fdate.format(new Date())+".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("==========="+basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data,basePath+fileName);
            return Result.success("http://127.0.0.1:9766/excel/"+fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

}
