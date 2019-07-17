package com.mit.community.module.population.controller;

import com.mit.community.entity.*;
import com.mit.community.entity.entity.*;
import com.mit.community.population.service.*;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/partyInfo")
@RestController
@Slf4j
@Api(tags = "党员信息")
public class PartyInfoController {
    @Autowired
    private PartyInfoService partyInfoService;
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
    @Autowired
    private ZdyPersonLabelService zdyPersonLabelService;


    @PostMapping("/saveParty")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "                       LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result saveParty(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rdsq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime zzrq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime cjgzsj, String rdszzb, String zzszzb, String zzszdw, String szdzb,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime jrdzbsj, String xrdnzw, String rdjsr, String yyjndf, Integer person_baseinfo_id) {
        partyInfoService.save(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
        return Result.success("保存党员信息成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "                       LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result update(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rdsq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime zzrq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime cjgzsj, String rdszzb, String zzszzb, String zzszdw, String szdzb,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime jrdzbsj, String xrdnzw, String rdjsr, String yyjndf, Integer person_baseinfo_id, int isDelete) {
        /*PartyInfo partyInfo = new PartyInfo(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id, isDelete);
        partyInfoService.save(partyInfo);*/
        return Result.success("保存党员信息成功");
    }

    @RequestMapping("/save")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result save(HttpServletRequest request) {
        InputStream in = null;
        try {
            // 获取请求的流信息
            in = request.getInputStream();
            String params = inputStream2String(in, "");
            System.out.println("=========================11111"+params);
            JSONObject jsonObject = JSONObject.fromObject(params);
            //境外人员
            if (jsonObject.has("境外人员")) {
                String jwry = jsonObject.getString("境外人员");
                if (!"0".equals(jwry)) {
                    JSONObject obj = jsonObject.getJSONObject("境外人员");
                    EngPeopleInfo engPeopleInfo = (EngPeopleInfo) JSONObject.toBean(obj, EngPeopleInfo.class);
                    if (engPeopleInfo.getPerson_baseinfo_id() != null) {
                        engPeopleService.save(engPeopleInfo);
                    }
                }
            }

            //留守人员
            if (jsonObject.has("留守人员")) {
                String lsry = jsonObject.getString("留守人员");
                if (!"0".equals(lsry)) {
                    JSONObject obj = jsonObject.getJSONObject("留守人员");
                    StayPeopleInfo stayPeopleInfo = (StayPeopleInfo) JSONObject.toBean(obj, StayPeopleInfo.class);
                    if (stayPeopleInfo.getPerson_baseinfo_id() != null) {
                        stayPeopleService.save(stayPeopleInfo);
                    }
                }
            }

            //党员
            if (jsonObject.has("党员")) {
                String dy = jsonObject.getString("党员");
                if (!"0".equals(dy)) {
                    JSONObject obj = jsonObject.getJSONObject("党员");
                    PartyInfo partyInfo = (PartyInfo) JSONObject.toBean(obj, PartyInfo.class);
                    if (partyInfo.getPerson_baseinfo_id() != null) {
                        partyInfoService.save(partyInfo);
                    }
                }
            }

            //计生人员
            if (jsonObject.has("计生人员")) {
                String js = jsonObject.getString("计生人员");
                if (!"0".equals(js)) {
                    JSONObject obj = jsonObject.getJSONObject("计生人员");
                    BearInfo bearInfo = (BearInfo) JSONObject.toBean(obj, BearInfo.class);
                    if (bearInfo.getPerson_baseinfo_id() != null) {
                        bearInfoService.save(bearInfo);
                    }
                }
            }

            //兵役
            if (jsonObject.has("兵役人员")) {
                String by = jsonObject.getString("兵役人员");
                if (!"0".equals(by)) {
                    JSONObject obj = jsonObject.getJSONObject("兵役人员");
                    MilitaryServiceInfo militaryServiceInfo = (MilitaryServiceInfo) JSONObject.toBean(obj, MilitaryServiceInfo.class);
                    if (militaryServiceInfo.getPerson_baseinfo_id() != null) {
                        militaryServiceService.save(militaryServiceInfo);
                    }
                }
            }

            //刑满释放
            if (jsonObject.has("刑满释放人员")) {
                String xmsf = jsonObject.getString("刑满释放人员");
                if (!"0".equals(xmsf)) {
                    JSONObject obj = jsonObject.getJSONObject("刑满释放人员");
                    XmsfPeopleInfo xmsfPeopleInfo = (XmsfPeopleInfo) JSONObject.toBean(obj, XmsfPeopleInfo.class);
                    if (xmsfPeopleInfo.getPerson_baseinfo_id() != null) {
                        xmsfPeopleService.save(xmsfPeopleInfo);
                    }
                }
            }

            //疑似传销
            if (jsonObject.has("疑似传销人员")) {
                String yscx = jsonObject.getString("疑似传销人员");
                if (!"0".equals(yscx)) {
                    JSONObject obj = jsonObject.getJSONObject("疑似传销人员");
                    CXInfo cXInfo = (CXInfo) JSONObject.toBean(obj, CXInfo.class);
                    if (cXInfo.getPerson_baseinfo_id() != null) {
                        cXService.save(cXInfo);
                    }
                }
            }

            //上访人员
            if (jsonObject.has("上访人员")) {
                String sfyy = jsonObject.getString("上访人员");
                if (!"0".equals(sfyy)) {
                    JSONObject obj = jsonObject.getJSONObject("上访人员");
                    SFPeopleInfo sFPeopleInfo = (SFPeopleInfo) JSONObject.toBean(obj, SFPeopleInfo.class);
                    if (sFPeopleInfo.getPerson_baseinfo_id() != null) {
                        sFPeopleService.save(sFPeopleInfo);
                    }
                }
            }

            //社区矫正
            if (jsonObject.has("社区矫正人员")) {
                String sqjz = jsonObject.getString("社区矫正人员");
                if (!"0".equals(sqjz)) {
                    JSONObject obj = jsonObject.getJSONObject("社区矫正人员");
                    SQJZPeopleinfo sQJZPeopleinfo = (SQJZPeopleinfo) JSONObject.toBean(obj, SQJZPeopleinfo.class);
                    if (sQJZPeopleinfo.getPerson_baseinfo_id() != null) {
                        sQJZPeopleService.save(sQJZPeopleinfo);
                    }
                }
            }

            //肇事肇祸
            if (jsonObject.has("肇事肇祸等严重精神障碍患者")) {
                String zszh = jsonObject.getString("肇事肇祸等严重精神障碍患者");
                if (!"0".equals(zszh)) {
                    JSONObject obj = jsonObject.getJSONObject("肇事肇祸等严重精神障碍患者");
                    ZSZHInfo zSZHInfo = (ZSZHInfo) JSONObject.toBean(obj, ZSZHInfo.class);
                    if (zSZHInfo.getPerson_baseinfo_id() != null) {
                        zSZHService.save(zSZHInfo);
                    }
                }
            }

            //吸毒人员
            if (jsonObject.has("吸毒人员")) {
                String xdry = jsonObject.getString("吸毒人员");
                if (!"0".equals(xdry)) {
                    JSONObject obj = jsonObject.getJSONObject("吸毒人员");
                    XDInfo xDInfo = (XDInfo) JSONObject.toBean(obj, XDInfo.class);
                    if (xDInfo.getPerson_baseinfo_id() != null) {
                        xDService.save(xDInfo);
                    }
                }
            }

            //艾滋病人员
            if (jsonObject.has("艾滋病危险人员")) {
                String azb = jsonObject.getString("艾滋病危险人员");
                if (!"0".equals(azb)) {
                    JSONObject obj = jsonObject.getJSONObject("艾滋病危险人员");
                    AzbInfo azbInfo = (AzbInfo) JSONObject.toBean(obj, AzbInfo.class);
                    if (azbInfo.getPerson_baseinfo_id() != null) {
                        aZBService.save(azbInfo);
                    }
                }
            }

            //重点青少年
            if (jsonObject.has("重点青少年")) {
                String zdqsn = jsonObject.getString("重点青少年");
                if (!"0".equals(zdqsn)) {
                    JSONObject obj = jsonObject.getJSONObject("重点青少年");
                    ZDQSNCInfo zDQSNCInfo = (ZDQSNCInfo) JSONObject.toBean(obj, ZDQSNCInfo.class);
                    if (zDQSNCInfo.getPerson_baseinfo_id() != null) {
                        zDQSNCService.save(zDQSNCInfo);
                    }
                }
            }

            //志愿者
            if (jsonObject.has("志愿者")) {
                String zyz = jsonObject.getString("志愿者");
                if (!"0".equals(zyz)) {
                    JSONObject obj = jsonObject.getJSONObject("志愿者");
                    ZyzInfo zyzInfo = (ZyzInfo) JSONObject.toBean(obj, ZyzInfo.class);
                    if (zyzInfo.getPerson_baseinfo_id() != null) {
                        zyzService.save(zyzInfo);
                    }
                }
            }

            //60岁以上老人
            if (jsonObject.has("六十岁以上老人")) {
                String old = jsonObject.getString("六十岁以上老人");
                if (!"0".equals(old)) {
                    JSONObject obj = jsonObject.getJSONObject("六十岁以上老人");
                    OldInfo oldInfo = (OldInfo) JSONObject.toBean(obj, OldInfo.class);
                    if (oldInfo.getPerson_baseinfo_id() != null) {
                        oldService.save(oldInfo);
                    }
                }
            }

            //网格员
            if (jsonObject.has("网格员")) {
                String wgy = jsonObject.getString("网格员");
                if (!"0".equals(wgy)) {
                    JSONObject obj = jsonObject.getJSONObject("网格员");
                    WgyInfo wgyInfo = (WgyInfo) JSONObject.toBean(obj, WgyInfo.class);
                    if (wgyInfo.getPerson_baseinfo_id() != null) {
                        wgyService.save(wgyInfo);
                    }
                }
            }

            //楼栋长
            if (jsonObject.has("楼栋长")) {
                String ldz = jsonObject.getString("楼栋长");
                if (!"0".equals(ldz)) {
                    JSONObject obj = jsonObject.getJSONObject("楼栋长");
                    LdzInfo ldzInfo = (LdzInfo) JSONObject.toBean(obj, LdzInfo.class);
                    if (ldzInfo.getPerson_baseinfo_id() != null) {
                        ldzService.save(ldzInfo);
                    }
                }
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            return Result.error("信息保存失败");
        }
        return Result.success("信息保存成功");
    }

    @PostMapping("/saveZdy")
    @ApiOperation(value = "保存自定义信息", notes = "")
    public Result saveZdy(HttpServletRequest request) {
        InputStream in = null;
        try{
            in = request.getInputStream();
            String params = inputStream2String(in, "");
            JSONObject jsonObject = JSONObject.fromObject(params);
            if (jsonObject.has("personBaseinfoId")) {
                Integer personBaseinfoId = Integer.parseInt(jsonObject.getString("personBaseinfoId"));
                if (jsonObject.has("labels")) {
                    List<ZdyPersonLabel> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("labels");
                    ZdyPersonLabel zdyPersonLabel = null;
                    for (int i =0; i<array.size();i++) {
                        net.sf.json.JSONObject data = (net.sf.json.JSONObject) array.get(i);
                        zdyPersonLabel = new ZdyPersonLabel();
                        zdyPersonLabel.setPersonBaseinfoId(personBaseinfoId);
                        zdyPersonLabel.setLabel(data.getString("label"));
                        zdyPersonLabel.setRemarks(data.getString("remarks"));
                        list.add(zdyPersonLabel);
                    }
                    zdyPersonLabelService.saveList(personBaseinfoId, list);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return Result.error("保存失败");
        }

        return Result.success("保存成功");
    }


    @PostMapping("/getZdy")
    @ApiOperation(value = "获取自定义信息", notes = "")
    public Result saveZdy(HttpServletRequest request, Integer personBaseinfoId) {
        List<ZdyPersonLabel> list = zdyPersonLabelService.getListByPersonBaseInfoId(personBaseinfoId);
        return Result.success(list);
    }




    @PostMapping("/delete")
    @ApiOperation(value = "删除党员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id) {
        partyInfoService.delete(id);
        return Result.success("删除成功");
    }

    /**
     * 接口解析方法
     *
     * @param in
     * @param coding
     * @return
     */
    public String inputStream2String(InputStream in, String coding) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
