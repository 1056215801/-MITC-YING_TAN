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
            JSONObject jwry = jsonObject.getJSONObject("jwry");
            if (jwry != null) {
                EngPeopleInfo engPeopleInfo = (EngPeopleInfo)JSONObject.toBean(jwry,EngPeopleInfo.class);
                if (engPeopleInfo.getPerson_baseinfo_id() != null) {
                    engPeopleService.save(engPeopleInfo);
                }
            }

            JSONObject lsry = jsonObject.getJSONObject("lsry");
            if (lsry != null) {
                StayPeopleInfo stayPeopleInfo = (StayPeopleInfo)JSONObject.toBean(lsry,StayPeopleInfo.class);
                if (stayPeopleInfo.getPerson_baseinfo_id() != null) {
                    stayPeopleService.save(stayPeopleInfo);
                }
            }

            JSONObject dy = jsonObject.getJSONObject("dy");
            if (dy != null) {
                PartyInfo partyInfo = (PartyInfo)JSONObject.toBean(dy,PartyInfo.class);
                if (partyInfo.getPerson_baseinfo_id() != null) {
                    partyInfoService.save(partyInfo);
                }
            }

            JSONObject js = jsonObject.getJSONObject("js");
            if (js != null) {
                BearInfo bearInfo = (BearInfo)JSONObject.toBean(js,BearInfo.class);
                if (bearInfo.getPerson_baseinfo_id() != null) {
                    bearInfoService.save(bearInfo);
                }
            }

            JSONObject by = jsonObject.getJSONObject("by");
            if (by != null) {
                MilitaryServiceInfo militaryServiceInfo = (MilitaryServiceInfo)JSONObject.toBean(by,MilitaryServiceInfo.class);
                if (militaryServiceInfo.getPerson_baseinfo_id() != null) {
                    militaryServiceService.save(militaryServiceInfo);
                }
            }

            JSONObject xmsf = jsonObject.getJSONObject("xmsf");
            if (xmsf != null) {
                XmsfPeopleInfo xmsfPeopleInfo = (XmsfPeopleInfo)JSONObject.toBean(xmsf,XmsfPeopleInfo.class);
                if (xmsfPeopleInfo.getPerson_baseinfo_id() != null) {
                    xmsfPeopleService.save(xmsfPeopleInfo);
                }
            }

            JSONObject yscx = jsonObject.getJSONObject("yscx");
            if (yscx != null){
                CXInfo cXInfo = (CXInfo)JSONObject.toBean(yscx,CXInfo.class);
                if (cXInfo.getPerson_baseinfo_id() != null) {
                    cXService.save(cXInfo);
                }
            }
            JSONObject sfyy = jsonObject.getJSONObject("sfyy");
            if (sfyy != null){
                SFPeopleInfo sFPeopleInfo = (SFPeopleInfo)JSONObject.toBean(sfyy,SFPeopleInfo.class);
                if (sFPeopleInfo.getPerson_baseinfo_id() != null) {
                    sFPeopleService.save(sFPeopleInfo);
                }
            }
            JSONObject sqjz = jsonObject.getJSONObject("sqjz");
            if (sqjz != null){
                SQJZPeopleinfo sQJZPeopleinfo = (SQJZPeopleinfo)JSONObject.toBean(sqjz,SQJZPeopleinfo.class);
                if (sQJZPeopleinfo.getPerson_baseinfo_id() != null) {
                    sQJZPeopleService.save(sQJZPeopleinfo);
                }
            }
            JSONObject zszh = jsonObject.getJSONObject("zszh");
            if (zszh != null){
                ZSZHInfo zSZHInfo = (ZSZHInfo)JSONObject.toBean(zszh,ZSZHInfo.class);
                if (zSZHInfo.getPerson_baseinfo_id() != null) {
                    zSZHService.save(zSZHInfo);
                }
            }
            JSONObject xdry = jsonObject.getJSONObject("xdry");
            if (xdry != null){
                XDInfo xDInfo = (XDInfo)JSONObject.toBean(xdry,XDInfo.class);
                if (xDInfo.getPerson_baseinfo_id() != null) {
                    xDService.save(xDInfo);
                }
            }
            JSONObject azb = jsonObject.getJSONObject("azb");
            if (azb != null){
                AzbInfo azbInfo = (AzbInfo)JSONObject.toBean(azb,AzbInfo.class);
                if (azbInfo.getPerson_baseinfo_id() != null) {
                    aZBService.save(azbInfo);
                }
            }
            JSONObject zdqsn = jsonObject.getJSONObject("zdqsn");
            if (zdqsn != null){
                ZDQSNCInfo zDQSNCInfo = (ZDQSNCInfo)JSONObject.toBean(zdqsn,ZDQSNCInfo.class);
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
