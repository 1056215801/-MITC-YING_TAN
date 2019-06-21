package com.mit.community.module.population.controller;

import com.mit.community.entity.ExcelData;
import com.mit.community.entity.LdzExcelInfo;
import com.mit.community.entity.OldExcelInfo;
import com.mit.community.entity.ZyzExcelInfo;
import com.mit.community.entity.entity.*;
import com.mit.community.population.service.ExcelOutService;
import com.mit.community.population.service.InfoSearchService;
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

    @PostMapping("/getMilitaryExcel")
    @ApiOperation(value = "兵役excel", notes = "")
    public Result getMilitaryExcel(HttpServletRequest request) throws Exception {
        List<MilitaryServiceExcelInfo> list = excelOutService.getMilitaryExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("兵役信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("学业情况");
            titles.add("专业编码");
            titles.add("专业名称");
            titles.add("专业特长");
            titles.add("从业类别");
            titles.add("就读学校或工作单位");
            titles.add("职业资格证书等级");
            titles.add("何时参加过多长时间的军师训练");
            titles.add("身高");
            titles.add("体重");
            titles.add("左眼裸眼视力");
            titles.add("右眼裸眼视力");
            titles.add("健康状况");
            titles.add("身体目测");
            titles.add("病史调查");
            titles.add("外出情况");
            titles.add("政治初审");
            titles.add("兵役登记结论");
            titles.add("原因");
            titles.add("登记形式");
            titles.add("是否推荐为预征对象");
            titles.add("备注");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getXyqk());
                row.add(list.get(i).getZybm());
                row.add(list.get(i).getZymc());
                row.add(list.get(i).getZytc());
                row.add(list.get(i).getCylb());
                row.add(list.get(i).getJdxx());
                row.add(list.get(i).getZyzgzs());
                row.add(list.get(i).getHscjdcsjjsxl());
                row.add(list.get(i).getSg());
                row.add(list.get(i).getTz());
                row.add(list.get(i).getZylysl());
                row.add(list.get(i).getYylysl());
                row.add(list.get(i).getJkzk());
                row.add(list.get(i).getStmc());
                row.add(list.get(i).getBsdc());
                row.add(list.get(i).getWcqk());
                row.add(list.get(i).getZzcs());
                row.add(list.get(i).getBydjjl());
                row.add(list.get(i).getYy());
                row.add(list.get(i).getDjxs());
                row.add(list.get(i).getSftj() == 1 ? "是" : "否");
                row.add(list.get(i).getBz());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "兵役信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getZyzExcel")
    @ApiOperation(value = "志愿者excel", notes = "")
    public Result getZyzExcel(HttpServletRequest request) throws Exception {
        List<ZyzExcelInfo> list = excelOutService.getZyzExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("志愿者信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("家庭经济状况");
            titles.add("是否纳入低保");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("与监护人关系");
            titles.add("初次发病日期");
            titles.add("目前诊断类型");
            titles.add("有无肇事肇祸史");
            titles.add("肇事肇祸次数");
            titles.add("上次肇事肇祸日期");
            titles.add("目前危险性评估登记");
            titles.add("治疗情况");
            titles.add("治疗医院名称");
            titles.add("实施住院治疗原因");
            titles.add("接收康复训练机构名称");
            titles.add("参与管理人员");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                /*row.add(list.get(i).getJtjjzk());
                row.add(list.get(i).getSfnrdb());
                row.add(list.get(i).getJhrsfzh());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getCcfbrq());
                row.add(list.get(i).getMqzdlx());
                row.add(list.get(i).getYwzszhs());
                row.add(list.get(i).getZszhcs());
                row.add(list.get(i).getSczszhrq());
                row.add(list.get(i).getMqwxxpgdj());
                row.add(list.get(i).getZlqk());
                row.add(list.get(i).getZlyy());
                row.add(list.get(i).getSszyzlyy());
                row.add(list.get(i).getJskfxljgmc());
                row.add(list.get(i).getCyglry());
                row.add(list.get(i).getBfqk());*/
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "志愿者信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getWgyExcel")
    @ApiOperation(value = "网格员excel", notes = "")
    public Result getWgyExcel(HttpServletRequest request) throws Exception {
        List<LdzExcelInfo> list = excelOutService.getWgyExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("网格员信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("家庭经济状况");
            titles.add("是否纳入低保");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("与监护人关系");
            titles.add("初次发病日期");
            titles.add("目前诊断类型");
            titles.add("有无肇事肇祸史");
            titles.add("肇事肇祸次数");
            titles.add("上次肇事肇祸日期");
            titles.add("目前危险性评估登记");
            titles.add("治疗情况");
            titles.add("治疗医院名称");
            titles.add("实施住院治疗原因");
            titles.add("接收康复训练机构名称");
            titles.add("参与管理人员");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                /*row.add(list.get(i).getJtjjzk());
                row.add(list.get(i).getSfnrdb());
                row.add(list.get(i).getJhrsfzh());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getCcfbrq());
                row.add(list.get(i).getMqzdlx());
                row.add(list.get(i).getYwzszhs());
                row.add(list.get(i).getZszhcs());
                row.add(list.get(i).getSczszhrq());
                row.add(list.get(i).getMqwxxpgdj());
                row.add(list.get(i).getZlqk());
                row.add(list.get(i).getZlyy());
                row.add(list.get(i).getSszyzlyy());
                row.add(list.get(i).getJskfxljgmc());
                row.add(list.get(i).getCyglry());
                row.add(list.get(i).getBfqk());*/
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "网格员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getOldExcel")
    @ApiOperation(value = "60岁老人excel", notes = "")
    public Result getOldExcel(HttpServletRequest request) throws Exception {
        List<OldExcelInfo> list = excelOutService.getOldExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("60岁以上老人基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("家庭经济状况");
            titles.add("是否纳入低保");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("与监护人关系");
            titles.add("初次发病日期");
            titles.add("目前诊断类型");
            titles.add("有无肇事肇祸史");
            titles.add("肇事肇祸次数");
            titles.add("上次肇事肇祸日期");
            titles.add("目前危险性评估登记");
            titles.add("治疗情况");
            titles.add("治疗医院名称");
            titles.add("实施住院治疗原因");
            titles.add("接收康复训练机构名称");
            titles.add("参与管理人员");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                /*row.add(list.get(i).getJtjjzk());
                row.add(list.get(i).getSfnrdb());
                row.add(list.get(i).getJhrsfzh());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getCcfbrq());
                row.add(list.get(i).getMqzdlx());
                row.add(list.get(i).getYwzszhs());
                row.add(list.get(i).getZszhcs());
                row.add(list.get(i).getSczszhrq());
                row.add(list.get(i).getMqwxxpgdj());
                row.add(list.get(i).getZlqk());
                row.add(list.get(i).getZlyy());
                row.add(list.get(i).getSszyzlyy());
                row.add(list.get(i).getJskfxljgmc());
                row.add(list.get(i).getCyglry());
                row.add(list.get(i).getBfqk());*/
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "60岁以上老人信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getLdzExcel")
    @ApiOperation(value = "楼栋长excel", notes = "")
    public Result getLdzExcel(HttpServletRequest request) throws Exception {
        List<LdzExcelInfo> list = excelOutService.getLdzExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("楼栋长基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("家庭经济状况");
            titles.add("是否纳入低保");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("与监护人关系");
            titles.add("初次发病日期");
            titles.add("目前诊断类型");
            titles.add("有无肇事肇祸史");
            titles.add("肇事肇祸次数");
            titles.add("上次肇事肇祸日期");
            titles.add("目前危险性评估登记");
            titles.add("治疗情况");
            titles.add("治疗医院名称");
            titles.add("实施住院治疗原因");
            titles.add("接收康复训练机构名称");
            titles.add("参与管理人员");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                /*row.add(list.get(i).getJtjjzk());
                row.add(list.get(i).getSfnrdb());
                row.add(list.get(i).getJhrsfzh());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getCcfbrq());
                row.add(list.get(i).getMqzdlx());
                row.add(list.get(i).getYwzszhs());
                row.add(list.get(i).getZszhcs());
                row.add(list.get(i).getSczszhrq());
                row.add(list.get(i).getMqwxxpgdj());
                row.add(list.get(i).getZlqk());
                row.add(list.get(i).getZlyy());
                row.add(list.get(i).getSszyzlyy());
                row.add(list.get(i).getJskfxljgmc());
                row.add(list.get(i).getCyglry());
                row.add(list.get(i).getBfqk());*/
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "楼栋长信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getInfoExcel")
    @ApiOperation(value = "人员信息导出excel", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result getInfoExcel(HttpServletResponse response, HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer ageStart,
                               @RequestParam(required = false, defaultValue = "0") Integer ageEnd,
                               String name, String idNum, String sex, String education, String job,
                               String matrimony, String zzmm, String rycf, String label, Integer pageNum, Integer pageSize) throws Exception {
        List<InfoSearch> list = infoSearchService.list(ageStart, ageEnd, name, idNum, sex, education, job, matrimony, zzmm, label, rycf);
        ExcelData data = new ExcelData();
        data.setName("用户基本信息");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("公民身份号码");
        titles.add("姓名");
        titles.add("曾用名");
        titles.add("性别");
        titles.add("出生日期");
        titles.add("民族");
        titles.add("籍贯");
        titles.add("婚姻状况");
        titles.add("政治面貌");
        titles.add("学历");
        titles.add("宗教信仰");
        titles.add("职业类别");
        titles.add("职业");
        titles.add("联系方式");
        titles.add("户籍地");
        titles.add("户籍地详址");
        titles.add("现住地");
        titles.add("现住地详址");
        titles.add("服务处所");
        titles.add("人员成分");

        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        List<Object> row = null;
        for (int i = 0; i < list.size(); i++) {
            row = new ArrayList<>();
            row.add(i);
            row.add(list.get(i).getPersonBaseInfo().getIdCardNum());
            row.add(list.get(i).getPersonBaseInfo().getName());
            row.add(list.get(i).getPersonBaseInfo().getFormerName());
            row.add(list.get(i).getPersonBaseInfo().getGender());
            row.add(list.get(i).getPersonBaseInfo().getBirthday());
            row.add(list.get(i).getPersonBaseInfo().getNation());
            row.add(list.get(i).getPersonBaseInfo().getNativePlace());
            row.add(list.get(i).getPersonBaseInfo().getMatrimony());
            row.add(list.get(i).getPersonBaseInfo().getPoliticCountenance());
            row.add(list.get(i).getPersonBaseInfo().getEducation());
            row.add(list.get(i).getPersonBaseInfo().getReligion());
            row.add(list.get(i).getPersonBaseInfo().getJobType());
            row.add(list.get(i).getPersonBaseInfo().getProfession());
            row.add(list.get(i).getPersonBaseInfo().getCellphone());
            row.add(list.get(i).getPersonBaseInfo().getPlaceOfDomicile());
            row.add(list.get(i).getPersonBaseInfo().getPlaceOfDomicileDetail());
            row.add(list.get(i).getPersonBaseInfo().getPlaceOfReside());
            row.add(list.get(i).getPersonBaseInfo().getPlaceOfResideDetail());
            row.add(list.get(i).getPersonBaseInfo().getPlaceOfServer());
            row.add(list.get(i).getPersonBaseInfo().getRksx() == 1 ? "户籍人口" : "流动人口");
            rows.add(row);
        }
        data.setRows(rows);
        SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = "人员基本信息" + fdate.format(new Date()) + ".xlsx";
        String basePath = request.getServletContext().getRealPath("excel/");
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdir();
        }
        ExcelUtils.generateExcel(data, basePath + fileName);
        return Result.success("http://127.0.0.1:9766/api/web/communitywanli/excel/" + fileName);
    }

    @PostMapping("/getAzbExcel")
    @ApiOperation(value = "艾滋病人excel", notes = "")
    public Result getAzbExcel(HttpServletRequest request) throws Exception {
        String fileName = null;
        List<AzbExcelInfo> list = excelOutService.getAzbExcel();
        if (!list.isEmpty()) {
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
            for (int i = 0; i < list.size(); i++) {
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
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            fileName = "艾滋病人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getBearExcel")
    @ApiOperation(value = "计生excel", notes = "")
    public Result getBearExcel(HttpServletRequest request) throws Exception {
        List<BearExcelInfo> list = excelOutService.getBearExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("计生人员基本信息");
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
            for (int i = 0; i < list.size(); i++) {
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
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "计生人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getCXExcel")
    @ApiOperation(value = "传销excel", notes = "")
    public Result getCXExcel(HttpServletRequest request) throws Exception {
        List<CXExcelInfo> list = excelOutService.getCXExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("传销人员基本信息");
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
            for (int i = 0; i < list.size(); i++) {
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
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "传销人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getEngPeopleExcel")
    @ApiOperation(value = "境外excel", notes = "")
    public Result getEngPeopleExcel(HttpServletRequest request) throws Exception {
        List<EngPeopleExcelInfo> list = excelOutService.getEngPeopleExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("境外人员基本信息");
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
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getWwx());
                row.add(list.get(i).getWwm());
                row.add(list.get(i).getZwm());
                row.add(list.get(i).getGj());
                row.add(list.get(i).getZjxy());
                row.add(list.get(i).getZjdm());
                row.add(list.get(i).getZjhm());
                row.add(list.get(i).getZjyxq());
                row.add(list.get(i).getLhmd());
                row.add(list.get(i).getDdrq());
                row.add(list.get(i).getYjlkrq());
                row.add(list.get(i).getSfzdry());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "境外人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getPartyExcel")
    @ApiOperation(value = "党员excel", notes = "")
    public Result getPartyExcel(HttpServletRequest request) throws Exception {
        List<PartyExcelInfo> list = excelOutService.getPartyExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("党员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("入党日期");
            titles.add("转正日期");
            titles.add("参加工作时间");
            titles.add("入党时所在支部");
            titles.add("转正所在支部");
            titles.add("组织所在单位");
            titles.add("所在党支部");
            titles.add("进入党支部时间");
            titles.add("现任党内职务");
            titles.add("入党介绍人");
            titles.add("月应交纳党费");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getRdsq());
                row.add(list.get(i).getZzrq());
                row.add(list.get(i).getCjgzsj());
                row.add(list.get(i).getRdszzb());
                row.add(list.get(i).getZzszzb());
                row.add(list.get(i).getZzszdw());
                row.add(list.get(i).getSzdzb());
                row.add(list.get(i).getJrdzbsj());
                row.add(list.get(i).getXrdnzw());
                row.add(list.get(i).getRdjsr());
                row.add(list.get(i).getYyjndf());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "党员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getSfExcel")
    @ApiOperation(value = "上访人员excel", notes = "")
    public Result getSfExcel(HttpServletRequest request) throws Exception {
        List<SFPeopleExcelInfo> list = excelOutService.getSfExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("上访人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("是否群众信访事件");
            titles.add("来信次数");
            titles.add("来电话次数");
            titles.add("上访时间");
            titles.add("上访人数");
            titles.add("上访发生地点");
            titles.add("上访人员诉求");
            titles.add("处理情况办法");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getSfqzxf());
                row.add(list.get(i).getLxcs());
                row.add(list.get(i).getLdcs());
                row.add(list.get(i).getSfsj());
                row.add(list.get(i).getSfrs());
                row.add(list.get(i).getSffsdd());
                row.add(list.get(i).getSfrysq());
                row.add(list.get(i).getClqkbf());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "上访人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getStayExcel")
    @ApiOperation(value = "留守人员excel", notes = "")
    public Result getStayExcel(HttpServletRequest request) throws Exception {
        List<StayPeopleExcelInfo> list = excelOutService.getStayExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("留守人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("健康状况");
            titles.add("个人年收入");
            titles.add("人户一致标识");
            titles.add("留守人员类型");
            titles.add("家庭主要成员身份证号码");
            titles.add("家庭主要成员姓名");
            titles.add("家庭主要成员健康状况");
            titles.add("与留守人员关系");
            titles.add("家庭主要成员联系方式");
            titles.add("家庭主要成员工作详细地址");
            titles.add("家庭年收入");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getJkzk());
                row.add(list.get(i).getGrnsr());
                row.add(list.get(i).getRhizbz());
                row.add(list.get(i).getLsrylx());
                row.add(list.get(i).getJtzyrysfzh());
                row.add(list.get(i).getJtzycyxm());
                row.add(list.get(i).getJtzycyjkzk());
                row.add(list.get(i).getYlsrygx());
                row.add(list.get(i).getJtzycylxfs());
                row.add(list.get(i).getJtzycygzxxdz());
                row.add(list.get(i).getJtnsr());
                row.add(list.get(i).getKnjsq());
                row.add(list.get(i).getBfqk());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "留守人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getXdExcel")
    @ApiOperation(value = "吸毒人员excel", notes = "")
    public Result getXdExcel(HttpServletRequest request) throws Exception {
        List<XdExcelInfo> list = excelOutService.getXdExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("吸毒人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("初次发现日期");
            titles.add("管控人情况");
            titles.add("管控人姓名");
            titles.add("管控人联系方式");
            titles.add("帮扶情况");
            titles.add("帮扶人姓名");
            titles.add("帮扶人联系方式");
            titles.add("有无犯罪史");
            titles.add("吸毒情况");
            titles.add("吸毒原因");
            titles.add("吸毒后果");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getCcfxsj());
                row.add(list.get(i).getGkqk());
                row.add(list.get(i).getGkrxm());
                row.add(list.get(i).getGkrlxfs());
                row.add(list.get(i).getBfqk());
                row.add(list.get(i).getBfrxm());
                row.add(list.get(i).getBfrlxfs());
                row.add(list.get(i).getYwfzs());
                row.add(list.get(i).getXdqk());
                row.add(list.get(i).getXdyy());
                row.add(list.get(i).getXdhg());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "吸毒人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getXmsfExcel")
    @ApiOperation(value = "刑满释放人员excel", notes = "")
    public Result getXmsfExcel(HttpServletRequest request) throws Exception {
        List<XmsfExcelInfo> list = excelOutService.getXmsfExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("刑满释放人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("是否累犯");
            titles.add("原罪名");
            titles.add("原判刑期");
            titles.add("服刑场所");
            titles.add("释放日期");
            titles.add("危险性评估类型");
            titles.add("衔接日期");
            titles.add("衔接情况");
            titles.add("安置日期");
            titles.add("未安置原因");
            titles.add("帮教情况");
            titles.add("是否重新犯罪");
            titles.add("重新犯罪罪名");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getSflf());
                row.add(list.get(i).getYzm());
                row.add(list.get(i).getYpxq());
                row.add(list.get(i).getFxcs());
                row.add(list.get(i).getSfrq());
                row.add(list.get(i).getWxxpglx());
                row.add(list.get(i).getXjrq());
                row.add(list.get(i).getXjqk());
                row.add(list.get(i).getAzrq());
                row.add(list.get(i).getAzqk());
                row.add(list.get(i).getWazyy());
                row.add(list.get(i).getBjqk());
                row.add(list.get(i).getSfcxfz());
                row.add(list.get(i).getCxfzzm());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "刑满释放人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getZdqsnExcel")
    @ApiOperation(value = "重点青少年excel", notes = "")
    public Result getZdqsnExcel(HttpServletRequest request) throws Exception {
        List<ZDQSNCExcelInfo> list = excelOutService.getZdqsnExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("重点青少年基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("人员类型");
            titles.add("家庭情况");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("监护人联系方式");
            titles.add("监护人居住详址");
            titles.add("是否违法犯罪");
            titles.add("违法犯罪情况");
            titles.add("帮扶人联系方式");
            titles.add("帮扶手段");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getRylx());
                row.add(list.get(i).getJtqk());
                row.add(list.get(i).getJhrsfz());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getYjhrgx());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getJhrjzxxdz());
                row.add(list.get(i).getSfwffz());
                row.add(list.get(i).getWffzqk());
                row.add(list.get(i).getBfrlxfs());
                row.add(list.get(i).getBfsd());
                row.add(list.get(i).getBfqk());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "重点青少年信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getZszhExcel")
    @ApiOperation(value = "肇事肇祸excel", notes = "")
    public Result getZszhExcel(HttpServletRequest request) throws Exception {
        List<ZSZHExcelInfo> list = excelOutService.getZszhExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("肇事肇祸人员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("家庭经济状况");
            titles.add("是否纳入低保");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("与监护人关系");
            titles.add("初次发病日期");
            titles.add("目前诊断类型");
            titles.add("有无肇事肇祸史");
            titles.add("肇事肇祸次数");
            titles.add("上次肇事肇祸日期");
            titles.add("目前危险性评估登记");
            titles.add("治疗情况");
            titles.add("治疗医院名称");
            titles.add("实施住院治疗原因");
            titles.add("接收康复训练机构名称");
            titles.add("参与管理人员");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getJtjjzk());
                row.add(list.get(i).getSfnrdb());
                row.add(list.get(i).getJhrsfzh());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getCcfbrq());
                row.add(list.get(i).getMqzdlx());
                row.add(list.get(i).getYwzszhs());
                row.add(list.get(i).getZszhcs());
                row.add(list.get(i).getSczszhrq());
                row.add(list.get(i).getMqwxxpgdj());
                row.add(list.get(i).getZlqk());
                row.add(list.get(i).getZlyy());
                row.add(list.get(i).getSszyzlyy());
                row.add(list.get(i).getJskfxljgmc());
                row.add(list.get(i).getCyglry());
                row.add(list.get(i).getBfqk());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "肇事肇祸人员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getDyExcel")
    @ApiOperation(value = "党员excel", notes = "")
    public Result getDyExcel(HttpServletRequest request) throws Exception {
        List<ZSZHExcelInfo> list = excelOutService.getZszhExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("党员基本信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("家庭经济状况");
            titles.add("是否纳入低保");
            titles.add("监护人公民身份号码");
            titles.add("监护人姓名");
            titles.add("与监护人关系");
            titles.add("初次发病日期");
            titles.add("目前诊断类型");
            titles.add("有无肇事肇祸史");
            titles.add("肇事肇祸次数");
            titles.add("上次肇事肇祸日期");
            titles.add("目前危险性评估登记");
            titles.add("治疗情况");
            titles.add("治疗医院名称");
            titles.add("实施住院治疗原因");
            titles.add("接收康复训练机构名称");
            titles.add("参与管理人员");
            titles.add("帮扶情况");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getJtjjzk());
                row.add(list.get(i).getSfnrdb());
                row.add(list.get(i).getJhrsfzh());
                row.add(list.get(i).getJhrxm());
                row.add(list.get(i).getJhrlxfs());
                row.add(list.get(i).getCcfbrq());
                row.add(list.get(i).getMqzdlx());
                row.add(list.get(i).getYwzszhs());
                row.add(list.get(i).getZszhcs());
                row.add(list.get(i).getSczszhrq());
                row.add(list.get(i).getMqwxxpgdj());
                row.add(list.get(i).getZlqk());
                row.add(list.get(i).getZlyy());
                row.add(list.get(i).getSszyzlyy());
                row.add(list.get(i).getJskfxljgmc());
                row.add(list.get(i).getCyglry());
                row.add(list.get(i).getBfqk());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "党员信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getHjrkExcel")
    @ApiOperation(value = "户籍人口信息excel", notes = "")
    public Result getHjrkExcel(HttpServletRequest request) throws Exception {
        List<CensusExcelInfo> list = excelOutService.getHjrkExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("户籍人口信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("人户一致标志");
            titles.add("户号");
            titles.add("与户主关系");
            titles.add("户主公民身份号码");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getRhyzbz());
                row.add(list.get(i).getHh());
                row.add(list.get(i).getYhzgx());
                row.add(list.get(i).getHzsfz());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "户籍人口信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getLdExcel")
    @ApiOperation(value = "流动人口信息excel", notes = "")
    public Result getLdExcel(HttpServletRequest request) throws Exception {
        List<FlowPeopleExcelInfo> list = excelOutService.getLdExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("流动人口信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("流入原因");
            titles.add("办证类型");
            titles.add("证件号码");
            titles.add("登记日期");
            titles.add("证件到期日期");
            titles.add("住所类型");
            titles.add("是否重点关注人员");
            titles.add("朋友或同事联系方式");
            titles.add("家庭重要人员联系方式");
            titles.add("与户主关系");
            titles.add("户主公民身份号码");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getLryy());
                row.add(list.get(i).getBzlx());
                row.add(list.get(i).getZjhm());
                row.add(list.get(i).getDjrq());
                row.add(list.get(i).getZjdqrq());
                row.add(list.get(i).getZslx());
                row.add(list.get(i).getSfzdgzry());
                row.add(list.get(i).getTslxfs());
                row.add(list.get(i).getJtzycylxfs());
                row.add(list.get(i).getYhzgx());
                row.add(list.get(i).getHzsfz());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "流动人口信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getHouseExcel")
    @ApiOperation(value = "房屋信息excel", notes = "")
    public Result getHouseExcel(HttpServletRequest request) throws Exception {
        List<HouseExcelInfo> list = excelOutService.getHouseExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("流动人口信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("小区");
            titles.add("楼栋");
            titles.add("单元");
            titles.add("房号");
            titles.add("房屋关系");
            titles.add("购买时间");
            titles.add("占地面积");
            titles.add("是否按揭");
            titles.add("房屋用途");
            titles.add("房屋性质");
            titles.add("房屋地址");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getZone_name());
                row.add(list.get(i).getBuilding_name());
                row.add(list.get(i).getUnit_name());
                row.add(list.get(i).getRoom_name());
                row.add(list.get(i).getHouseRelation());
                row.add(list.get(i).getBuyTime());
                row.add(list.get(i).getArea());
                row.add(list.get(i).getMortgage());
                row.add(list.get(i).getHouseUsage());
                row.add(list.get(i).getHouseAttr());
                row.add(list.get(i).getFwdz());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "房屋信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

    @PostMapping("/getCarExcel")
    @ApiOperation(value = "车辆信息excel", notes = "")
    public Result getCarExcel(HttpServletRequest request) throws Exception {
        List<CarExcelInfo> list = excelOutService.getCarExcel();
        if (!list.isEmpty()) {
            ExcelData data = new ExcelData();
            data.setName("车辆信息");
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("性别");
            titles.add("年龄");
            titles.add("身份证号码");
            titles.add("联系方式");
            titles.add("家庭地址");

            titles.add("车牌号");
            titles.add("车型");
            titles.add("颜色");
            titles.add("品牌");
            titles.add("型号");
            titles.add("排量");
            titles.add("发动机型号");
            titles.add("驾驶证");
            titles.add("行驶证");
            titles.add("生产日期");
            titles.add("购买日期");

            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList<>();
            List<Object> row = null;
            for (int i = 0; i < list.size(); i++) {
                row = new ArrayList<>();
                row.add(list.get(i).getName());
                row.add(list.get(i).getGender());
                row.add(list.get(i).getAge());
                row.add(list.get(i).getIdCardNum());
                row.add(list.get(i).getCellphone());
                row.add(list.get(i).getPlaceOfResideDetail());

                row.add(list.get(i).getCph());
                row.add(list.get(i).getCx());
                row.add(list.get(i).getYs());
                row.add(list.get(i).getPp());
                row.add(list.get(i).getXh());
                row.add(list.get(i).getPl());
                row.add(list.get(i).getFdjh());
                row.add(list.get(i).getJsz());
                row.add(list.get(i).getXsz());
                row.add(list.get(i).getSzrq());
                row.add(list.get(i).getGmrq());
                rows.add(row);
            }
            data.setRows(rows);
            SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "车辆信息" + fdate.format(new Date()) + ".xlsx";
            String basePath = request.getServletContext().getRealPath("excel/");
            System.out.println("===========" + basePath);
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            ExcelUtils.generateExcel(data, basePath + fileName);
            return Result.success("http://127.0.0.1:9766/excel/" + fileName);
        } else {
            return Result.success("无相关信息");
        }
    }

}
