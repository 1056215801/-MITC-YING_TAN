package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.*;
import com.mit.community.population.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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


    /*@PostMapping("/save")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "                       LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result save(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime rdsq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime zzrq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime cjgzsj, String rdszzb, String zzszzb, String zzszdw, String szdzb,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime jrdzbsj, String xrdnzw, String rdjsr, String yyjndf, Integer person_baseinfo_id){
        partyInfoService.save(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
        return Result.success("保存党员信息成功");

    }*/
    @RequestMapping("/save")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "                       LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result save(HttpServletRequest request) {
        InputStream in = null;
        try {
            // 获取请求的流信息
            in = request.getInputStream();
            String params = inputStream2String(in, "");
            System.out.println(params);
            JSONObject jsonObject = JSONObject.fromObject(params);
            String jwry = jsonObject.getString("jwry");
            if (!"0".equals(jwry)) {
                JSONObject obj = jsonObject.getJSONObject("jwry");
                EngPeopleInfo engPeopleInfo = (EngPeopleInfo)JSONObject.toBean(obj,EngPeopleInfo.class);
                if (engPeopleInfo.getPerson_baseinfo_id() != null) {
                    engPeopleService.save(engPeopleInfo);
                }
            }

            String lsry = jsonObject.getString("lsry");
            //JSONObject lsry = jsonObject.getJSONObject("lsry");
            if (!"0".equals(lsry)) {
                JSONObject obj = jsonObject.getJSONObject("lsry");
                StayPeopleInfo stayPeopleInfo = (StayPeopleInfo)JSONObject.toBean(obj,StayPeopleInfo.class);
                if (stayPeopleInfo.getPerson_baseinfo_id() != null) {
                    stayPeopleService.save(stayPeopleInfo);
                }
            }
            String dy = jsonObject.getString("dy");
            //JSONObject dy = jsonObject.getJSONObject("dy");
            if (!"0".equals(dy)) {
                JSONObject obj = jsonObject.getJSONObject("dy");
                PartyInfo partyInfo = (PartyInfo)JSONObject.toBean(obj,PartyInfo.class);
                if (partyInfo.getPerson_baseinfo_id() != null) {
                    partyInfoService.save(partyInfo);
                }
            }
            String js = jsonObject.getString("js");
            //JSONObject js = jsonObject.getJSONObject("js");
            if (!"0".equals(js)) {
                JSONObject obj = jsonObject.getJSONObject("js");
                BearInfo bearInfo = (BearInfo)JSONObject.toBean(obj,BearInfo.class);
                if (bearInfo.getPerson_baseinfo_id() != null) {
                    bearInfoService.save(bearInfo);
                }
            }
            String by = jsonObject.getString("by");
            //JSONObject by = jsonObject.getJSONObject("by");
            if (!"0".equals(by)) {
                JSONObject obj = jsonObject.getJSONObject("by");
                MilitaryServiceInfo militaryServiceInfo = (MilitaryServiceInfo)JSONObject.toBean(obj,MilitaryServiceInfo.class);
                if (militaryServiceInfo.getPerson_baseinfo_id() != null) {
                    militaryServiceService.save(militaryServiceInfo);
                }
            }
            String xmsf = jsonObject.getString("xmsf");
            //JSONObject xmsf = jsonObject.getJSONObject("xmsf");
            if (!"0".equals(xmsf)) {
                JSONObject obj = jsonObject.getJSONObject("xmsf");
                XmsfPeopleInfo xmsfPeopleInfo = (XmsfPeopleInfo)JSONObject.toBean(obj,XmsfPeopleInfo.class);
                if (xmsfPeopleInfo.getPerson_baseinfo_id() != null) {
                    xmsfPeopleService.save(xmsfPeopleInfo);
                }
            }
            String yscx = jsonObject.getString("yscx");
            //JSONObject yscx = jsonObject.getJSONObject("yscx");
            if (!"0".equals(yscx)){
                JSONObject obj = jsonObject.getJSONObject("yscx");
                CXInfo cXInfo = (CXInfo)JSONObject.toBean(obj,CXInfo.class);
                if (cXInfo.getPerson_baseinfo_id() != null) {
                    cXService.save(cXInfo);
                }
            }
            String sfyy = jsonObject.getString("sfyy");
            //JSONObject sfyy = jsonObject.getJSONObject("sfyy");
            if (!"0".equals(sfyy)){
                JSONObject obj = jsonObject.getJSONObject("sfyy");
                SFPeopleInfo sFPeopleInfo = (SFPeopleInfo)JSONObject.toBean(obj,SFPeopleInfo.class);
                if (sFPeopleInfo.getPerson_baseinfo_id() != null) {
                    sFPeopleService.save(sFPeopleInfo);
                }
            }
            String sqjz = jsonObject.getString("sqjz");
            //JSONObject sqjz = jsonObject.getJSONObject("sqjz");
            if (!"0".equals(sqjz)){
                JSONObject obj = jsonObject.getJSONObject("sqjz");
                SQJZPeopleinfo sQJZPeopleinfo = (SQJZPeopleinfo)JSONObject.toBean(obj,SQJZPeopleinfo.class);
                if (sQJZPeopleinfo.getPerson_baseinfo_id() != null) {
                    sQJZPeopleService.save(sQJZPeopleinfo);
                }
            }
            String zszh = jsonObject.getString("zszh");
            //JSONObject zszh = jsonObject.getJSONObject("zszh");
            if (!"0".equals(zszh)){
                JSONObject obj = jsonObject.getJSONObject("zszh");
                ZSZHInfo zSZHInfo = (ZSZHInfo)JSONObject.toBean(obj,ZSZHInfo.class);
                if (zSZHInfo.getPerson_baseinfo_id() != null) {
                    zSZHService.save(zSZHInfo);
                }
            }
            String xdry = jsonObject.getString("xdry");
            //JSONObject xdry = jsonObject.getJSONObject("xdry");
            if (!"0".equals(xdry)){
                JSONObject obj = jsonObject.getJSONObject("xdry");
                XDInfo xDInfo = (XDInfo)JSONObject.toBean(obj,XDInfo.class);
                if (xDInfo.getPerson_baseinfo_id() != null) {
                    xDService.save(xDInfo);
                }
            }
            String azb = jsonObject.getString("azb");
            //JSONObject azb = jsonObject.getJSONObject("azb");
            if (!"0".equals(azb)){
                JSONObject obj = jsonObject.getJSONObject("azb");
                AzbInfo azbInfo = (AzbInfo)JSONObject.toBean(obj,AzbInfo.class);
                if (azbInfo.getPerson_baseinfo_id() != null) {
                    aZBService.save(azbInfo);
                }
            }
            String zdqsn = jsonObject.getString("zdqsn");
            //JSONObject zdqsn = jsonObject.getJSONObject("zdqsn");
            if (!"0".equals(zdqsn)){
                JSONObject obj = jsonObject.getJSONObject("zdqsn");
                ZDQSNCInfo zDQSNCInfo = (ZDQSNCInfo)JSONObject.toBean(obj,ZDQSNCInfo.class);
                if (zDQSNCInfo.getPerson_baseinfo_id() != null) {
                    zDQSNCService.save(zDQSNCInfo);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Result.success("保存党员信息成功");
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
