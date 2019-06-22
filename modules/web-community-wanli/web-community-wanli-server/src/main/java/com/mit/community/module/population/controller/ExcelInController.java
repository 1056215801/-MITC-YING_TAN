package com.mit.community.module.population.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.*;
import com.mit.community.population.service.*;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RequestMapping(value = "/excelIn")
@RestController
@Slf4j
@Api(tags = "excel导入")
public class ExcelInController {
    @Autowired
    private ExcelInService excelInService;
    @Autowired
    private PersonBaseInfoService personBaseInfoService;
    @Autowired
    private AZBService aZBService;
    @Autowired
    private BearInfoService bearInfoService;
    @Autowired
    private CXService cXService;
    @Autowired
    private SFPeopleService sFPeopleService;
    @Autowired
    private SQJZPeopleService sQJZPeopleService;
    @Autowired
    private StayPeopleService stayPeopleService;
    @Autowired
    private XDService xDService;
    @Autowired
    private XmsfPeopleService xmsfPeopleService;
    @Autowired
    private ZDQSNCService zDQSNCService;
    @Autowired
    private ZSZHService zSZHService;
    @Autowired
    private LdzService ldzService;
    @Autowired
    private OldService oldService;
    @Autowired
    private WgyService wgyService;
    @Autowired
    private ZyzService zyzService;
    @Autowired
    private PartyInfoService partyInfoService;
    @Autowired
    private CensusInfoService censusInfoService;
    @Autowired
    private FlowPeopleService flowPeopleService;
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private MilitaryServiceService militaryServiceService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/Military")
    @ApiOperation(value = "兵役excel导入", notes = "传参：")
    public Result Military(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        MilitaryServiceInfo militaryServiceInfo = null;
        List<MilitaryServiceInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);
            Cell cell17 = sheet.getCell(17, i);
            Cell cell18 = sheet.getCell(18, i);
            Cell cell19 = sheet.getCell(19, i);
            Cell cell20 = sheet.getCell(20, i);
            Cell cell21 = sheet.getCell(21, i);
            Cell cell22 = sheet.getCell(22, i);
            Cell cell23 = sheet.getCell(23, i);
            Cell cell24 = sheet.getCell(24, i);
            Cell cell25 = sheet.getCell(25, i);

            String xyqk = cell5.getContents();
            String zybm = cell6.getContents();
            String zymc = cell7.getContents();
            String zytc = cell8.getContents();
            String cylb = cell9.getContents();
            String jdxx = cell10.getContents();
            String zyzgzs = cell11.getContents();
            String hscjdcsjjsxl = cell12.getContents();
            String sg = cell13.getContents();
            String tz = cell14.getContents();
            String zylysl = cell15.getContents();
            String yylysl = cell16.getContents();
            String jkzk = cell17.getContents();
            String stmc = cell18.getContents();
            String bsdc = cell19.getContents();
            String wcqk = cell20.getContents();
            String zzcs = cell21.getContents();
            String bydjjl = cell22.getContents();
            String yy = cell23.getContents();
            String djxs = cell24.getContents();
            String sftjStr = cell25.getContents();
            int sftj = 0;
            if (StringUtils.isNotBlank(sftjStr)) {
                if ("是".equals(sftjStr)) {
                    sftj = 1;
                }
            }
            String bz = cell21.getContents();

            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            militaryServiceInfo = new MilitaryServiceInfo(xyqk, zybm, zymc, zytc, cylb, jdxx, zyzgzs, hscjdcsjjsxl, sg, tz, zylysl, yylysl, jkzk, stmc, bsdc, wcqk, zzcs,
                    bydjjl, yy, djxs, sftj, bz, person_baseinfo_id, 0);
            list.add(militaryServiceInfo);
        }
        militaryServiceService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/zyz")
    @ApiOperation(value = "志愿者excel导入", notes = "传参：")
    public Result zyz(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        ZyzInfo zyzInfo = null;
        List<ZyzInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);

            String isReg = cell5.getContents();
            String organization = cell6.getContents();
            String regNumber = cell7.getContents();
            String serviceExp = cell8.getContents();
            String serviceType = cell9.getContents();
            String specialSkills = cell10.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            zyzInfo = new ZyzInfo(person_baseinfo_id, 0, isReg, organization, regNumber, serviceExp, serviceType, specialSkills);
            list.add(zyzInfo);
        }
        zyzService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/wgy")
    @ApiOperation(value = "网格员excel导入", notes = "传参：")
    public Result wgy(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        WgyInfo wgyInfo = null;
        List<WgyInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);

            String gddh = cell5.getContents();
            String jtzycygzdwjzw = cell6.getContents();
            String jtzycyjkzk = cell7.getContents();
            String jtzycylxfs = cell8.getContents();
            String jtzycyxm = cell9.getContents();
            String jtzyrysfzh = cell10.getContents();
            String officeTime = cell11.getContents();
            String workCondition = cell12.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            wgyInfo = new WgyInfo(person_baseinfo_id, 0, gddh, jtzycygzdwjzw, jtzycyjkzk, jtzycylxfs, jtzycyxm, jtzyrysfzh, officeTime, workCondition, null, null);
            list.add(wgyInfo);
        }
        wgyService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/old")
    @ApiOperation(value = "60岁老人excel导入", notes = "传参：")
    public Result old(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        OldInfo oldInfo = null;
        List<OldInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);

            String disease = cell5.getContents();
            String householdName = cell6.getContents();
            String isInsurance = cell7.getContents();
            String jkzk = cell8.getContents();
            String jtzycygzdwjzw = cell9.getContents();
            String jtzycyjkzk = cell10.getContents();
            String jtzycylxfs = cell11.getContents();
            String jtzycyxm = cell12.getContents();
            String jtzyrysfzh = cell13.getContents();
            String knjsq = cell14.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            oldInfo = new OldInfo(person_baseinfo_id, 0, disease, householdName, isInsurance, jkzk, jtzycygzdwjzw, jtzycyjkzk, jtzycylxfs, jtzycyxm, jtzyrysfzh, knjsq);
            list.add(oldInfo);
        }
        oldService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/ldz")
    @ApiOperation(value = "楼栋长excel导入", notes = "传参：")
    public Result ldz(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        LdzInfo ldzInfo = null;
        List<LdzInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);

            String gddh = cell5.getContents();
            String jtzycygzdwjzw = cell6.getContents();
            String jtzycyjkzk = cell7.getContents();
            String jtzycylxfs = cell8.getContents();
            String jtzycyxm = cell9.getContents();
            String jtzyrysfzh = cell10.getContents();
            String officeTime = cell11.getContents();
            String workCondition = cell12.getContents();

            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            ldzInfo = new LdzInfo(person_baseinfo_id, 0, gddh, jtzycygzdwjzw, jtzycyjkzk, jtzycylxfs, jtzycyxm, jtzyrysfzh, officeTime, workCondition);
            list.add(ldzInfo);
        }
        ldzService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/zszh")
    @ApiOperation(value = "肇事肇祸excel导入", notes = "传参：")
    public Result zszh(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        ZSZHInfo zSZHInfo = null;
        List<ZSZHInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);
            Cell cell17 = sheet.getCell(17, i);
            Cell cell18 = sheet.getCell(18, i);
            Cell cell19 = sheet.getCell(19, i);
            Cell cell20 = sheet.getCell(20, i);
            Cell cell21 = sheet.getCell(21, i);

            String jtjjzk = cell5.getContents();
            String sfnrdb = cell6.getContents();
            String jhrsfzh = cell7.getContents();
            String jhrxm = cell8.getContents();
            String jhrlxfs = cell9.getContents();
            String ccfbrq = cell10.getContents();
            String mqzdlx = cell11.getContents();
            String ywzszhs = cell12.getContents();
            String zszhcs = cell13.getContents();
            String sczszhrq = cell14.getContents();
            String mqwxxpgdj = cell15.getContents();
            String zlqk = cell16.getContents();
            String zlyy = cell17.getContents();
            String sszyzlyy = cell18.getContents();
            String jskfxljgmc = cell19.getContents();
            String cyglry = cell20.getContents();
            String bfqk = cell21.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            zSZHInfo = new ZSZHInfo(jtjjzk, sfnrdb, jhrsfzh, jhrxm, jhrlxfs, ccfbrq,
                    mqzdlx, ywzszhs, Integer.valueOf(zszhcs), sczszhrq, mqwxxpgdj, zlqk, zlyy, sszyzlyy, jskfxljgmc,
                    cyglry, bfqk, person_baseinfo_id, 0);
            list.add(zSZHInfo);
        }
        zSZHService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/zdqsn")
    @ApiOperation(value = "重点青少年excel导入", notes = "传参：")
    public Result zdqsn(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        ZDQSNCInfo zDQSNCInfo = null;
        List<ZDQSNCInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);

            String rylx = cell5.getContents();
            String jtqk = cell6.getContents();
            String jhrsfz = cell7.getContents();
            String jhrxm = cell8.getContents();
            String yjhrgx = cell9.getContents();
            String jhrlxfs = cell10.getContents();
            String jhrjzxxdz = cell11.getContents();
            String sfwffz = cell12.getContents();
            String wffzqk = cell13.getContents();
            String bfrlxfs = cell14.getContents();
            String bfsd = cell15.getContents();
            String bfqk = cell16.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            zDQSNCInfo = new ZDQSNCInfo(rylx, jtqk, jhrsfz, jhrxm, yjhrgx, jhrlxfs, jhrjzxxdz, sfwffz, wffzqk, bfrlxfs, bfsd, bfqk, person_baseinfo_id, 0);
            list.add(zDQSNCInfo);
        }
        zDQSNCService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/xmsf")
    @ApiOperation(value = "刑满释放人员excel导入", notes = "传参：")
    public Result xmsf(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        XmsfPeopleInfo xmsfPeopleInfo = null;
        List<XmsfPeopleInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);
            Cell cell17 = sheet.getCell(17, i);
            Cell cell18 = sheet.getCell(18, i);

            String sflf = cell5.getContents();
            String yzm = cell6.getContents();
            String ypxq = cell7.getContents();
            String fxcs = cell8.getContents();
            String sfrq = cell9.getContents();
            String wxxpglx = cell10.getContents();
            String xjrq = cell11.getContents();
            String xjqk = cell12.getContents();
            String azrq = cell13.getContents();
            String azqk = cell14.getContents();
            String wazyy = cell15.getContents();
            String bjqk = cell16.getContents();
            String sfcxfz = cell17.getContents();
            String cxfzzm = cell18.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id, 0);
            list.add(xmsfPeopleInfo);
        }
        xmsfPeopleService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/xd")
    @ApiOperation(value = "吸毒人员excel导入", notes = "传参：")
    public Result xd(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        XDInfo xDInfo = null;
        List<XDInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);

            String ccfxsj = cell5.getContents();
            String gkqk = cell6.getContents();
            String gkrxm = cell7.getContents();
            String gkrlxfs = cell8.getContents();
            String bfqk = cell9.getContents();
            String bfrxm = cell10.getContents();
            String bfrlxfs = cell11.getContents();
            String ywfzs = cell12.getContents();
            String xdqk = cell13.getContents();
            String xdyy = cell14.getContents();
            String xdhg = cell15.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            //xDInfo = new XDInfo(ccfxsj, gkqk, gkrxm, gkrlxfs, bfqk, bfrxm, bfrlxfs, ywfzs, xdqk, xdyy, xdhg, person_baseinfo_id, 0);
            list.add(xDInfo);
        }
        xDService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/azb")
    @ApiOperation(value = "艾滋病excel导入", notes = "传参：")
    public Result azb(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        AzbInfo azbInfo = null;
        List<AzbInfo> list = new ArrayList<>();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        //int columns=sheet.getColumns();
        Integer person_baseinfo_id = null;
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            String grtj = cell5.getContents();
            Cell cell6 = sheet.getCell(6, i);
            String sfwf = cell6.getContents();
            Cell cell7 = sheet.getCell(7, i);
            String wffzqk = cell7.getContents();
            Cell cell8 = sheet.getCell(8, i);
            String ajlb = cell8.getContents();
            Cell cell9 = sheet.getCell(9, i);
            String gzlx = cell9.getContents();
            Cell cell10 = sheet.getCell(10, i);
            String bfqk = cell10.getContents();
            Cell cell11 = sheet.getCell(11, i);
            String bfrdh = cell11.getContents();
            Cell cell12 = sheet.getCell(12, i);
            String bfrxm = cell12.getContents();
            Cell cell13 = sheet.getCell(13, i);
            String szqk = cell13.getContents();
            Cell cell14 = sheet.getCell(14, i);
            String szjgmc = cell14.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            azbInfo = new AzbInfo(grtj, sfwf, wffzqk, ajlb, gzlx, bfqk, bfrdh, bfrxm, szqk, szjgmc, person_baseinfo_id, 0);
            list.add(azbInfo);
        }
        aZBService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/bear")
    @ApiOperation(value = "计生excel导入", notes = "传参：")
    public Result bear(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        BearInfo bearInfo = null;
        List<BearInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            String poxm = cell5.getContents();
            String poxb = cell6.getContents();
            String xgzdw = cell7.getContents();
            String djjhny = cell8.getContents();
            String hkxz = cell9.getContents();
            String hyzk = cell10.getContents();
            String jysssj = cell11.getContents();
            String sslx = cell12.getContents();
            String ssyy = cell13.getContents();
            String ccyy = cell14.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            bearInfo = new BearInfo(poxm, poxb, xgzdw, djjhny, hkxz, hyzk, jysssj, sslx, ssyy, ccyy, person_baseinfo_id, 0);
            list.add(bearInfo);
        }
        bearInfoService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/cx")
    @ApiOperation(value = "传销excel导入", notes = "传参：")
    public Result cx(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        CXInfo cXInfo = null;
        List<CXInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);

            String dysxcx = cell5.getContents();
            String drsxcx = cell6.getContents();
            String dssxcx = cell7.getContents();
            String bz = cell8.getContents();

            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            cXInfo = new CXInfo(dysxcx, drsxcx, dssxcx, bz, person_baseinfo_id, 0);
            list.add(cXInfo);
        }
        cXService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/sf")
    @ApiOperation(value = "上访excel导入", notes = "传参：")
    public Result sf(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        SFPeopleInfo sFPeopleInfo = null;
        List<SFPeopleInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);

            String sfqzxf = cell5.getContents();
            String lxcs = cell6.getContents();
            String ldcs = cell7.getContents();
            String sfsj = cell8.getContents();
            String sfrs = cell9.getContents();
            String sffsdd = cell10.getContents();
            String sfrysq = cell11.getContents();
            String clqkbf = cell12.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            sFPeopleInfo = new SFPeopleInfo(sfqzxf, Integer.valueOf(lxcs), Integer.valueOf(ldcs), sfsj, Integer.valueOf(sfrs), sffsdd,
                    sfrysq, clqkbf, person_baseinfo_id, 0);
            list.add(sFPeopleInfo);
        }
        sFPeopleService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/sqjz")
    @ApiOperation(value = "社区矫正excel导入", notes = "传参：")
    public Result sqjz(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        SQJZPeopleinfo sQJZPeopleinfo = null;
        List<SQJZPeopleinfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);
            Cell cell17 = sheet.getCell(17, i);
            Cell cell18 = sheet.getCell(18, i);
            Cell cell19 = sheet.getCell(19, i);
            Cell cell20 = sheet.getCell(20, i);
            Cell cell21 = sheet.getCell(21, i);
            Cell cell22 = sheet.getCell(22, i);
            Cell cell23 = sheet.getCell(23, i);
            Cell cell24 = sheet.getCell(24, i);
            Cell cell25 = sheet.getCell(25, i);
            Cell cell26 = sheet.getCell(26, i);
            Cell cell27 = sheet.getCell(27, i);
            Cell cell28 = sheet.getCell(28, i);
            Cell cell29 = sheet.getCell(29, i);
            Cell cell30 = sheet.getCell(30, i);
            Cell cell31 = sheet.getCell(31, i);
            Cell cell32 = sheet.getCell(32, i);

            String sqjzrybh = cell5.getContents();
            String yjycs = cell6.getContents();
            String jzlb = cell7.getContents();
            String ajlb = cell8.getContents();
            String jtzm = cell9.getContents();
            String ypxq = cell10.getContents();
            String ypxkssj = cell11.getContents();
            String ypxjssj = cell12.getContents();
            String jzkssj = cell13.getContents();
            String jzjssj = cell14.getContents();
            String jsfs = cell15.getContents();
            String ssqk = cell16.getContents();
            String sflgf = cell17.getContents();
            String ssqku = cell18.getContents();
            String sfjljzxz = cell19.getContents();
            String jzjclx = cell20.getContents();
            String sfytg = cell21.getContents();
            String tgyy = cell22.getContents();
            String jcjdtgqk = cell23.getContents();
            String tgjzqk = cell24.getContents();
            String sfylg = cell25.getContents();
            String lgyy = cell26.getContents();
            String jcjdlgqk = cell27.getContents();
            String lgjzqk = cell28.getContents();
            String jcqk = cell29.getContents();
            String xfbgzx = cell30.getContents();
            String sfcxfz = cell31.getContents();
            String cxfzmc = cell32.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            sQJZPeopleinfo = new SQJZPeopleinfo(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                    tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id, 0);
            list.add(sQJZPeopleinfo);
        }
        sQJZPeopleService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/stay")
    @ApiOperation(value = "留守人员excel导入", notes = "传参：")
    public Result stay(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        StayPeopleInfo stayPeopleInfo = null;
        List<StayPeopleInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);
            Cell cell17 = sheet.getCell(17, i);

            String jkzk = cell5.getContents();
            String grnsr = cell6.getContents();
            String rhizbz = cell7.getContents();
            String lsrylx = cell8.getContents();
            String jtzyrysfzh = cell9.getContents();
            String jtzycyxm = cell10.getContents();
            String jtzycyjkzk = cell11.getContents();
            String ylsrygx = cell12.getContents();
            String jtzycylxfs = cell13.getContents();
            String jtzycygzxxdz = cell14.getContents();
            String jtnsr = cell15.getContents();
            String knjsq = cell16.getContents();
            String bfqk = cell17.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            stayPeopleInfo = new StayPeopleInfo(jkzk, grnsr, rhizbz, lsrylx, jtzyrysfzh, jtzycyxm, jtzycyjkzk, ylsrygx, jtzycylxfs, jtzycygzxxdz, jtnsr, knjsq, bfqk, person_baseinfo_id, 0);
            list.add(stayPeopleInfo);
        }
        stayPeopleService.saveList(list);
        return Result.success("保存成功");
    }


    @PostMapping("/dy")
    @ApiOperation(value = "党员excel导入", notes = "传参：")
    public Result dy(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        PartyInfo partyInfo = null;
        List<PartyInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);

            String rdsq = cell5.getContents();
            String zzrq = cell6.getContents();
            String cjgzsj = cell7.getContents();
            String rdszzb = cell8.getContents();
            String zzszzb = cell9.getContents();
            String zzszdw = cell10.getContents();
            String szdzb = cell11.getContents();
            String jrdzbsj = cell12.getContents();
            String xrdnzw = cell13.getContents();
            String rdjsr = cell14.getContents();
            String yyjndf = cell15.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            partyInfo = new PartyInfo(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id, 0);
            list.add(partyInfo);
        }
        partyInfoService.saveList(list);
        return Result.success("保存成功");
    }

    @RequestMapping(value = "/person", produces = "text/html;charset=utf-8", method = RequestMethod.POST)
    @ApiOperation(value = "人员基本信息excel导入", notes = "传参：")
    public Result person(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        //String filePath = saveFile(request, excel);
        //System.out.println("======filePath="+filePath);
        //把MultipartFile转化为File
        //boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //CommonsMultipartFile cf = (CommonsMultipartFile) file;
        //DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        //File fo = fi.getStoreLocation();
        Workbook book;
        book = Workbook.getWorkbook(file.getInputStream());
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        PersonBaseInfo personBaseInfo = null;
        List<PersonBaseInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idCardNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String formerName = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String gender = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);
            Cell cell16 = sheet.getCell(16, i);
            Cell cell17 = sheet.getCell(17, i);
            Cell cell18 = sheet.getCell(18, i);
            Cell cell19 = sheet.getCell(19, i);

            String birthdayString = cell5.getContents();
            LocalDateTime birthday = null;
            int age = 0;
            if (StringUtils.isNotBlank(birthdayString)) {
                birthday = DateUtils.dateStrToLocalDateTime(birthdayString);
                String[] ages = birthdayString.split("-");
                age = 2019 - Integer.parseInt(ages[0]);
            }
            String nation = cell6.getContents();
            String nativePlace = cell7.getContents();
            String matrimony = cell8.getContents();
            String politicCountenance = cell9.getContents();
            String education = cell10.getContents();
            String religion = cell11.getContents();
            String jobType = cell12.getContents();
            String profession = cell13.getContents();
            String cellphone = cell14.getContents();
            String placeOfDomicile = cell15.getContents();
            String placeOfDomicileDetail = cell16.getContents();
            String placeOfReside = cell17.getContents();
            String placeOfResideDetail = cell18.getContents();
            String placeOfServer = cell19.getContents();

            if (!StringUtils.isNotBlank(idCardNum)) {
                return Result.success("存在未填写身份证信息的记录");
            }
            personBaseInfo = new PersonBaseInfo(idCardNum, userName, formerName, gender, birthday, nation,
                    nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                    placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, null, 0, age, 0, null, null,communityCode);
            list.add(personBaseInfo);
        }
        personBaseInfoService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/census")
    @ApiOperation(value = "户籍人口excel导入", notes = "传参：")
    public Result census(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        CensusInfo censusInfo = null;
        List<CensusInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);

            String rhyzbz = cell5.getContents();
            String hh = cell6.getContents();
            String yhzgx = cell7.getContents();
            String hzsfz = cell8.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            censusInfo = new CensusInfo(rhyzbz, hh, yhzgx, hzsfz, person_baseinfo_id);
            list.add(censusInfo);
        }
        censusInfoService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/ld")
    @ApiOperation(value = "流动人口信息excel导入", notes = "传参：")
    public Result ld(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        FlowPeopleInfo flowPeopleInfo = null;
        List<FlowPeopleInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);

            String lryy = cell5.getContents();
            String bzlx = cell6.getContents();
            String zjhm = cell7.getContents();
            String djrqStr = cell8.getContents();
            LocalDateTime djrq = null;
            if (StringUtils.isNotBlank(djrqStr)) {
                djrq = DateUtils.strToLocalDateTime(djrqStr);
            }
            String zjdqrqStr = cell9.getContents();
            LocalDateTime zjdqrq = null;
            if (StringUtils.isNotBlank(zjdqrqStr)) {
                zjdqrq = DateUtils.strToLocalDateTime(zjdqrqStr);
            }
            String zslx = cell10.getContents();
            String sfzdgzryStr = cell11.getContents();
            int sfzdgzry = 0;
            if (StringUtils.isNotBlank(sfzdgzryStr)) {
                if ("是".equals(sfzdgzryStr)) {
                    sfzdgzry = 1;
                }
            }
            String tslxfs = cell12.getContents();
            String jtzycylxfs = cell13.getContents();
            String yhzgx = cell14.getContents();
            String hzsfz = cell15.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            flowPeopleInfo = new FlowPeopleInfo(lryy, bzlx, zjhm, djrq, zjdqrq, zslx,
                    sfzdgzry, tslxfs, jtzycylxfs, yhzgx, hzsfz, person_baseinfo_id);
            list.add(flowPeopleInfo);
        }
        flowPeopleService.saveList(list);
        return Result.success("保存成功");
    }

    @PostMapping("/car")
    @ApiOperation(value = "车辆excel导入", notes = "传参：")
    public Result car(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String filePath = saveFile(request, excel);
        Workbook book;
        book = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        Integer person_baseinfo_id = null;
        CarInfo carInfo = null;
        List<CarInfo> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            Cell cell = sheet.getCell(1, i);
            String idNum = cell.getContents();
            Cell cell2 = sheet.getCell(2, i);
            String userName = cell2.getContents();
            Cell cell3 = sheet.getCell(3, i);
            String gender = cell3.getContents();
            Cell cell4 = sheet.getCell(4, i);
            String cellphone = cell4.getContents();

            Cell cell5 = sheet.getCell(5, i);
            Cell cell6 = sheet.getCell(6, i);
            Cell cell7 = sheet.getCell(7, i);
            Cell cell8 = sheet.getCell(8, i);
            Cell cell9 = sheet.getCell(9, i);
            Cell cell10 = sheet.getCell(10, i);
            Cell cell11 = sheet.getCell(11, i);
            Cell cell12 = sheet.getCell(12, i);
            Cell cell13 = sheet.getCell(13, i);
            Cell cell14 = sheet.getCell(14, i);
            Cell cell15 = sheet.getCell(15, i);

            String cph = cell5.getContents();
            String cx = cell6.getContents();
            String ys = cell7.getContents();
            String pp = cell8.getContents();
            String xh = cell9.getContents();
            String pl = cell10.getContents();
            String fdjh = cell11.getContents();
            String jsz = cell12.getContents();
            String xsz = cell13.getContents();
            String szrq = cell14.getContents();
            String gmrq = cell15.getContents();
            if (StringUtils.isNotBlank(idNum)) {
                Integer id = personBaseInfoService.getIdByCardNum(idNum);
                if (id != null) {
                    person_baseinfo_id = id;
                } else {
                    person_baseinfo_id = personBaseInfoService.save(0, idNum, userName, null, gender, null, null, null, null, null,
                            null, null, null, null, cellphone, null, null, null, null, null, null,communityCode);
                }
            } else {
                return Result.success("存在未填写身份证信息的记录");
            }
            carInfo = new CarInfo(cph, cx, ys, pp, xh, pl, fdjh, jsz, xsz, szrq, gmrq, person_baseinfo_id);
            list.add(carInfo);
        }
        carInfoService.saveList(list);
        return Result.success("保存成功");
    }

    public String saveFile(HttpServletRequest request, MultipartFile excel) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        String name = excel.getOriginalFilename();
        String ext = "";//文件后缀
        if (name.contains(".")) {
            ext = name.substring(name.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + ext;
        String basePath = request.getServletContext().getRealPath("excel/");
        System.out.println("===========" + basePath);
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdir();
        }
        String filePath = basePath + fileName;
        File localFile = new File(filePath);
        excel.transferTo(localFile);
        return filePath;
    }
}
