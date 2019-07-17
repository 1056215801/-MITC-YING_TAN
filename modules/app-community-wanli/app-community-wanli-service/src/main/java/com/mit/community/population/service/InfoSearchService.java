package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.LdzInfo;
import com.mit.community.entity.OldInfo;
import com.mit.community.entity.WgyInfo;
import com.mit.community.entity.ZyzInfo;
import com.mit.community.entity.entity.*;
import com.mit.community.mapper.LdzMapper;
import com.mit.community.mapper.OldMapper;
import com.mit.community.mapper.WgyMapper;
import com.mit.community.mapper.ZyzMapper;
import com.mit.community.mapper.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoSearchService {
    @Autowired
    private InfoSearchMapper infoSearchMapper;
    @Autowired
    private AZBMapper aZBMapper;
    @Autowired
    private BearInfoMapper bearInfoMapper;
    @Autowired
    private CXMapper cXMapper;
    @Autowired
    private EngPeopleMapper engPeopleMapper;
    @Autowired
    private MilitaryServiceMapper militaryServiceMapper;
    @Autowired
    private PartyInfoMapper partyInfoMapper;
    @Autowired
    private SFPeopleMapper sFPeopleMapper;
    @Autowired
    private SQJZPeopleMapper sQJZPeopleMapper;
    @Autowired
    private StayPeopleMapper stayPeopleMapper;
    @Autowired
    private XDMapper xDMapper;
    @Autowired
    private XmsfPeopleMapper xmsfPeopleMapper;
    @Autowired
    private ZDQSNCMapper zDQSNCMapper;
    @Autowired
    private ZSZHMapper zSZHMapper;
    @Autowired
    private OldMapper oldMapper;
    @Autowired
    private LdzMapper ldzMapper;
    @Autowired
    private WgyMapper wgyMapper;
    @Autowired
    private ZyzMapper zyzMapper;

    @Transactional
    public Map<String, Object> getLabelInfo(Integer person_baseinfo_id) {
        Map<String, Object> map = new HashMap<>();
        EntityWrapper<AzbInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", person_baseinfo_id);
        List<AzbInfo> list = aZBMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            AzbInfo azbInfo = list.get(0);
            map.put("艾滋病危险人员", azbInfo);
        }
        EntityWrapper<BearInfo> wrapper1 = new EntityWrapper<>();
        wrapper1.eq("person_baseinfo_id", person_baseinfo_id);
        List<BearInfo> list1 = bearInfoMapper.selectList(wrapper1);
        if (!list1.isEmpty()) {
            BearInfo bearInfo = list1.get(0);
            map.put("计生人员", bearInfo);
        }
        EntityWrapper<CXInfo> wrapper2 = new EntityWrapper<>();
        wrapper2.eq("person_baseinfo_id", person_baseinfo_id);
        List<CXInfo> list2 = cXMapper.selectList(wrapper2);
        if (!list2.isEmpty()) {
            CXInfo cXInfo = list2.get(0);
            map.put("疑似传销人员", cXInfo);
        }
        EntityWrapper<EngPeopleInfo> wrapper3 = new EntityWrapper<>();
        wrapper3.eq("person_baseinfo_id", person_baseinfo_id);
        List<EngPeopleInfo> list3 = engPeopleMapper.selectList(wrapper3);
        if (!list3.isEmpty()) {
            EngPeopleInfo engPeopleInfo = list3.get(0);
            map.put("境外人员", engPeopleInfo);
        }
        EntityWrapper<MilitaryServiceInfo> wrapper4 = new EntityWrapper<>();
        wrapper4.eq("person_baseinfo_id", person_baseinfo_id);
        List<MilitaryServiceInfo> list4 = militaryServiceMapper.selectList(wrapper4);
        if (!list4.isEmpty()) {
            MilitaryServiceInfo militaryServiceInfo = list4.get(0);
            map.put("兵役人员", militaryServiceInfo);
        }
        EntityWrapper<PartyInfo> wrapper5 = new EntityWrapper<>();
        wrapper5.eq("person_baseinfo_id", person_baseinfo_id);
        List<PartyInfo> list5 = partyInfoMapper.selectList(wrapper5);
        if (!list5.isEmpty()) {
            PartyInfo partyInfo = list5.get(0);
            map.put("党员", partyInfo);
        }

        EntityWrapper<SFPeopleInfo> wrapper6 = new EntityWrapper<>();
        wrapper6.eq("person_baseinfo_id", person_baseinfo_id);
        List<SFPeopleInfo> list6 = sFPeopleMapper.selectList(wrapper6);
        if (!list6.isEmpty()) {
            SFPeopleInfo sFPeopleInfo = list6.get(0);
            map.put("上访人员", sFPeopleInfo);
        }
        EntityWrapper<SQJZPeopleinfo> wrapper7 = new EntityWrapper<>();
        wrapper7.eq("person_baseinfo_id", person_baseinfo_id);
        List<SQJZPeopleinfo> list7 = sQJZPeopleMapper.selectList(wrapper7);
        if (!list7.isEmpty()) {
            SQJZPeopleinfo sQJZPeopleinfo = list7.get(0);
            map.put("社区矫正人员", sQJZPeopleinfo);
        }
        EntityWrapper<StayPeopleInfo> wrapper8 = new EntityWrapper<>();
        wrapper8.eq("person_baseinfo_id", person_baseinfo_id);
        List<StayPeopleInfo> list8 = stayPeopleMapper.selectList(wrapper8);
        if (!list8.isEmpty()) {
            StayPeopleInfo stayPeopleInfo = list8.get(0);
            map.put("留守人员", stayPeopleInfo);
        }
        EntityWrapper<XDInfo> wrapper9 = new EntityWrapper<>();
        wrapper9.eq("person_baseinfo_id", person_baseinfo_id);
        List<XDInfo> list9 = xDMapper.selectList(wrapper9);
        if (!list9.isEmpty()) {
            XDInfo xDInfo = list9.get(0);
            map.put("吸毒人员", xDInfo);
        }
        EntityWrapper<XmsfPeopleInfo> wrapper10 = new EntityWrapper<>();
        wrapper10.eq("person_baseinfo_id", person_baseinfo_id);
        List<XmsfPeopleInfo> list10 = xmsfPeopleMapper.selectList(wrapper10);
        if (!list10.isEmpty()) {
            XmsfPeopleInfo xmsfPeopleInfo = list10.get(0);
            map.put("刑满释放人员", xmsfPeopleInfo);
        }
        EntityWrapper<ZDQSNCInfo> wrapper11 = new EntityWrapper<>();
        wrapper11.eq("person_baseinfo_id", person_baseinfo_id);
        List<ZDQSNCInfo> list11 = zDQSNCMapper.selectList(wrapper11);
        if (!list11.isEmpty()) {
            ZDQSNCInfo zDQSNCInfo = list11.get(0);
            map.put("重点青少年", zDQSNCInfo);
        }
        EntityWrapper<ZSZHInfo> wrapper12 = new EntityWrapper<>();
        wrapper12.eq("person_baseinfo_id", person_baseinfo_id);
        List<ZSZHInfo> list12 = zSZHMapper.selectList(wrapper12);
        if (!list12.isEmpty()) {
            ZSZHInfo zSZHInfo = list12.get(0);
            map.put("肇事肇祸等严重精神障碍患者", zSZHInfo);
        }
        EntityWrapper<OldInfo> wrapper13 = new EntityWrapper<>();
        wrapper13.eq("person_baseinfo_id", person_baseinfo_id);
        List<OldInfo> list13 = oldMapper.selectList(wrapper13);
        if (!list13.isEmpty()) {
            OldInfo oldInfo = list13.get(0);
            map.put("六十岁以上老人", oldInfo);
        }
        EntityWrapper<LdzInfo> wrapper14 = new EntityWrapper<>();
        wrapper14.eq("person_baseinfo_id", person_baseinfo_id);
        List<LdzInfo> list14 = ldzMapper.selectList(wrapper14);
        if (!list14.isEmpty()) {
            LdzInfo ldzInfo = list14.get(0);
            map.put("楼栋长", ldzInfo);
        }
        EntityWrapper<WgyInfo> wrapper15 = new EntityWrapper<>();
        wrapper15.eq("person_baseinfo_id", person_baseinfo_id);
        List<WgyInfo> list15 = wgyMapper.selectList(wrapper15);
        if (!list15.isEmpty()) {
            WgyInfo wgyInfo = list15.get(0);
            map.put("网格员", wgyInfo);
        }
        EntityWrapper<ZyzInfo> wrapper16 = new EntityWrapper<>();
        wrapper16.eq("person_baseinfo_id", person_baseinfo_id);
        List<ZyzInfo> list16 = zyzMapper.selectList(wrapper16);
        if (!list16.isEmpty()) {
            ZyzInfo zyzInfo = list16.get(0);
            map.put("志愿者", zyzInfo);
        }
        return map;
    }

    public Page<InfoSearch> listPage(String communtiyCode, Integer ageStart, Integer ageEnd, String name, String idNum, String sex,
                                     String education, String job, String matrimony, String zzmm, String label,
                                     Integer pageNum, Integer pageSize, String rycf, String accountType, String streetName, String areaName) {
        Page<InfoSearch> page = new Page<>(pageNum, pageSize);
        EntityWrapper<InfoSearch> wrapper = new EntityWrapper<>();
        if (label == null || "".equals(label)) {
            if (StringUtils.isNotBlank(accountType)) {
                if ("区级账号".equals(accountType)) {
                    wrapper.eq("b.area_name", areaName);
                }
                if ("镇/街道账号".equals(accountType)) {
                    wrapper.eq("b.street_name", streetName);
                }
            }
            wrapper.eq("a.community_code",communtiyCode);
            if (ageStart != 0) {
                wrapper.ge("(YEAR(NOW())-YEAR(a.birthday)-1) + ( DATE_FORMAT(a.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("(YEAR(NOW())-YEAR(a.birthday)-1) + ( DATE_FORMAT(a.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageEnd);
            }
            if (StringUtils.isNotBlank(name)) {
                wrapper.eq("a.name", name);
            }
            if (StringUtils.isNotBlank(idNum)) {
                wrapper.eq("a.id_card_num", idNum);
            }
            if (StringUtils.isNotBlank(sex)) {
                wrapper.eq("a.gender", sex);
            }
            if (StringUtils.isNotBlank(education)) {
                wrapper.like("a.education", education);
            }
            if (StringUtils.isNotBlank(job)) {
                wrapper.like("a.profession", job);
            }
            if (StringUtils.isNotBlank(matrimony)) {
                wrapper.eq("a.matrimony", matrimony);
            }
            if (StringUtils.isNotBlank(zzmm)) {
                wrapper.eq("a.politic_countenance", zzmm);
            }
            if (StringUtils.isNotBlank(rycf)) {
                if ("flow".equals(rycf)) {
                    wrapper.eq("a.rksx", 2);
                } else if ("census".equals(rycf)) {
                    wrapper.eq("a.rksx", 1);
                }
            }
            //wrapper.eq("a.isdelete", 0);
            wrapper.orderBy("a.gmt_create", false);
            List<InfoSearch> list = infoSearchMapper.selectPersonBaseInfoPage(page, wrapper);
            page.setRecords(list);
        } else {
            if (StringUtils.isNotBlank(accountType)) {
                if ("区级账号".equals(accountType)) {
                    wrapper.eq("c.area_name", areaName);
                }
                if ("镇/街道账号".equals(accountType)) {
                    wrapper.eq("c.street_name", streetName);
                }
            }

            if (ageStart != 0) {
                wrapper.ge("(YEAR(NOW())-YEAR(b.birthday)-1) + ( DATE_FORMAT(b.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("(YEAR(NOW())-YEAR(b.birthday)-1) + ( DATE_FORMAT(b.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageEnd);
            }
            if (StringUtils.isNotBlank(name)) {
                wrapper.eq("b.name", name);
            }
            if (StringUtils.isNotBlank(idNum)) {
                wrapper.eq("b.id_card_num", idNum);
            }
            if (StringUtils.isNotBlank(sex)) {
                wrapper.eq("b.gender", sex);
            }
            if (StringUtils.isNotBlank(education)) {
                wrapper.eq("b.education", education);
            }
            if (StringUtils.isNotBlank(job)) {
                wrapper.eq("b.profession", job);
            }
            if (StringUtils.isNotBlank(matrimony)) {
                wrapper.eq("b.matrimony", matrimony);
            }
            if (StringUtils.isNotBlank(zzmm)) {
                wrapper.eq("b.politic_countenance", zzmm);
            }
            if (StringUtils.isNotBlank(rycf)) {
                if ("flow".equals(rycf)) {
                    wrapper.eq("b.rksx", 2);
                } else if ("census".equals(rycf)) {
                    wrapper.eq("b.rksx", 1);
                }
            }
            wrapper.eq("b.isdelete", 0);
            wrapper.orderBy("b.gmt_create", false);
            if ("艾滋病危险人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectAzbInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("计生人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectBearInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("疑似传销人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectCxInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("境外人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectEngPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("多余的".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectFlowPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("兵役人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectMilitaryServiceInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("党员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectPartyInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("上访人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectSfInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("社区矫正人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectSqjzpeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("留守人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectStayPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("吸毒人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectXdPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("刑满释放人员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectXmsfPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("重点青少年".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectZdqsnPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("肇事肇祸等严重精神障碍患者".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectZszhPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("楼栋长".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectLdzPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("网格员".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectWgyPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("志愿者".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectZyzPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("六十岁以上老人".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectOldPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else {
                wrapper.eq("a.label", label);
                List<InfoSearch> list = infoSearchMapper.selectZdyInfoPage(page, wrapper);
                page.setRecords(list);
            }
        }
        return page;
    }

    public List<InfoSearch> list(Integer ageStart, Integer ageEnd, String name, String idNum, String sex, String education, String job, String matrimony, String zzmm, String label, String rycf, String accountType, String streetName, String areaName){
        EntityWrapper<InfoSearch> wrapper = new EntityWrapper<>();
        List<InfoSearch> list = new ArrayList<>();
        if (label == null || "".equals(label)) {
            if (StringUtils.isNotBlank(accountType)) {
                if ("区级账号".equals(accountType)) {
                    wrapper.eq("b.area_name", areaName);
                }
                if ("镇/街道账号".equals(accountType)) {
                    wrapper.eq("b.street_name", streetName);
                }
            }

            if (ageStart != 0) {
                wrapper.ge("(YEAR(NOW())-YEAR(a.birthday)-1) + ( DATE_FORMAT(a.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("(YEAR(NOW())-YEAR(a.birthday)-1) + ( DATE_FORMAT(a.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageEnd);
            }

            if (StringUtils.isNotBlank(name)) {
                wrapper.eq("a.name", name);
            }
            if (StringUtils.isNotBlank(idNum)) {
                wrapper.eq("a.id_card_num", idNum);
            }
            if (StringUtils.isNotBlank(sex)) {
                wrapper.eq("a.gender", sex);
            }
            if (StringUtils.isNotBlank(education)) {
                wrapper.like("a.education", education);
            }
            if (StringUtils.isNotBlank(job)) {
                wrapper.like("a.profession", job);
            }
            if (StringUtils.isNotBlank(matrimony)) {
                wrapper.eq("a.matrimony", matrimony);
            }
            if (StringUtils.isNotBlank(zzmm)) {
                wrapper.eq("a.politic_countenance", zzmm);
            }
            if (StringUtils.isNotBlank(rycf)) {
                if ("flow".equals(rycf)) {
                    wrapper.eq("a.rksx", 2);
                } else if ("census".equals(rycf)) {
                    wrapper.eq("a.rksx", 1);
                }
            }
            wrapper.orderBy("a.gmt_create", false);
            list = infoSearchMapper.selectPersonBaseInfo( wrapper);
        } else {
            if (StringUtils.isNotBlank(accountType)) {
                if ("区级账号".equals(accountType)) {
                    wrapper.eq("c.area_name", areaName);
                }
                if ("镇/街道账号".equals(accountType)) {
                    wrapper.eq("c.street_name", streetName);
                }
            }
            if (ageStart != 0) {
                wrapper.ge("(YEAR(NOW())-YEAR(b.birthday)-1) + ( DATE_FORMAT(b.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("(YEAR(NOW())-YEAR(b.birthday)-1) + ( DATE_FORMAT(b.birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )", ageEnd);
            }
            if (StringUtils.isNotBlank(name)) {
                wrapper.eq("b.name", name);
            }
            if (StringUtils.isNotBlank(idNum)) {
                wrapper.eq("b.id_card_num", idNum);
            }
            if (StringUtils.isNotBlank(sex)) {
                wrapper.eq("b.gender", sex);
            }
            if (StringUtils.isNotBlank(education)) {
                wrapper.eq("b.education", education);
            }
            if (StringUtils.isNotBlank(job)) {
                wrapper.eq("b.profession", job);
            }
            if (StringUtils.isNotBlank(matrimony)) {
                wrapper.eq("b.matrimony", matrimony);
            }
            if (StringUtils.isNotBlank(zzmm)) {
                wrapper.eq("b.politic_countenance", zzmm);
            }
            if (StringUtils.isNotBlank(rycf)) {
                if ("flow".equals(rycf)) {
                    wrapper.eq("b.rksx", 2);
                } else if ("census".equals(rycf)) {
                    wrapper.eq("b.rksx", 1);
                }
            }
            wrapper.orderBy("a.gmt_create", false);
            if ("艾滋病危险人员".equals(label)) {
                list = infoSearchMapper.selectAzbInfo(wrapper);

            } else if ("计生人员".equals(label)) {
                list = infoSearchMapper.selectBearInfo(wrapper);

            } else if ("疑似传销人员".equals(label)) {
                list = infoSearchMapper.selectCxInfo(wrapper);

            } else if ("境外人员".equals(label)) {
                list = infoSearchMapper.selectEngPeopleInfo(wrapper);

            } else if ("flowPeople".equals(label)) {
                list = infoSearchMapper.selectFlowPeopleInfo(wrapper);

            } else if ("兵役人员".equals(label)) {
                list = infoSearchMapper.selectMilitaryServiceInfo(wrapper);

            } else if ("党员".equals(label)) {
                list = infoSearchMapper.selectPartyInfo(wrapper);

            } else if ("上访人员".equals(label)) {
                list = infoSearchMapper.selectSfInfo(wrapper);

            } else if ("社区矫正人员".equals(label)) {
                list = infoSearchMapper.selectSqjzpeopleInfo(wrapper);

            } else if ("留守人员".equals(label)) {
                list = infoSearchMapper.selectStayPeopleInfo(wrapper);

            } else if ("吸毒人员".equals(label)) {
                list = infoSearchMapper.selectXdPeopleInfo(wrapper);

            } else if ("刑满释放人员".equals(label)) {
                list = infoSearchMapper.selectXmsfPeopleInfo(wrapper);

            } else if ("重点青少年".equals(label)) {
                list = infoSearchMapper.selectZdqsnPeopleInfo(wrapper);

            } else if ("肇事肇祸等严重精神障碍患者".equals(label)) {
                list = infoSearchMapper.selectZszhPeopleInfo(wrapper);
            } else if ("楼栋长".equals(label)) {
                list = infoSearchMapper.selectLdzPeopleInfo(wrapper);
            } else if ("网格员".equals(label)) {
                list = infoSearchMapper.selectWgyPeopleInfo(wrapper);
            } else if ("志愿者".equals(label)) {
                list = infoSearchMapper.selectZyzPeopleInfo(wrapper);
            } else if ("六十岁以上老人".equals(label)) {
                list = infoSearchMapper.selectOldPeopleInfo(wrapper);
            } else {
                wrapper.eq("a.label", label);
                list = infoSearchMapper.selectZdyInfo(wrapper);
            }

        }
        return list;
    }

    public String getByPhone(String phone){
        return infoSearchMapper.getByPhone(phone);
    }
}
