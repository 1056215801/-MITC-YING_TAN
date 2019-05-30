package com.mit.community.module.population.controller;

import com.mit.community.population.service.PartyInfoService;
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
            System.out.println(jwry + "===================");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //partyInfoService.save(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
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
