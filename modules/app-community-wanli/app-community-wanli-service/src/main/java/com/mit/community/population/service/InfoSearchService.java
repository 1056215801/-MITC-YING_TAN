package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.entity.*;
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

    @Transactional
    public Map<String, Object> getLabelInfo(Integer person_baseinfo_id){
        Map<String, Object> map = new HashMap<>();

        EntityWrapper<AzbInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", person_baseinfo_id);
        List<AzbInfo> list = aZBMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            AzbInfo azbInfo = list.get(0);
            map.put("azb", azbInfo);
        }

        EntityWrapper<BearInfo> wrapper1 = new EntityWrapper<>();
        wrapper1.eq("person_baseinfo_id", person_baseinfo_id);
        List<BearInfo> list1 = bearInfoMapper.selectList(wrapper1);
        if (!list1.isEmpty()) {
            BearInfo bearInfo = list1.get(0);
            map.put("js", bearInfo);
        }

        EntityWrapper<CXInfo> wrapper2 = new EntityWrapper<>();
        wrapper2.eq("person_baseinfo_id", person_baseinfo_id);
        List<CXInfo> list2 = cXMapper.selectList(wrapper2);
        if (!list2.isEmpty()) {
            CXInfo cXInfo = list2.get(0);
            map.put("cx", cXInfo);
        }

        EntityWrapper<EngPeopleInfo> wrapper3 = new EntityWrapper<>();
        wrapper3.eq("person_baseinfo_id", person_baseinfo_id);
        List<EngPeopleInfo> list3 = engPeopleMapper.selectList(wrapper3);
        if (!list3.isEmpty()) {
            EngPeopleInfo engPeopleInfo = list3.get(0);
            map.put("jw", engPeopleInfo);
        }

        EntityWrapper<MilitaryServiceInfo> wrapper4 = new EntityWrapper<>();
        wrapper4.eq("person_baseinfo_id", person_baseinfo_id);
        List<MilitaryServiceInfo> list4 = militaryServiceMapper.selectList(wrapper4);
        if (!list4.isEmpty()) {
            MilitaryServiceInfo militaryServiceInfo = list4.get(0);
            map.put("by", militaryServiceInfo);
        }

        EntityWrapper<PartyInfo> wrapper5 = new EntityWrapper<>();
        wrapper5.eq("person_baseinfo_id", person_baseinfo_id);
        List<PartyInfo> list5 = partyInfoMapper.selectList(wrapper5);
        if (!list5.isEmpty()) {
            PartyInfo partyInfo = list5.get(0);
            map.put("dy", partyInfo);
        }

        EntityWrapper<SFPeopleInfo> wrapper6 = new EntityWrapper<>();
        wrapper6.eq("person_baseinfo_id", person_baseinfo_id);
        List<SFPeopleInfo> list6 = sFPeopleMapper.selectList(wrapper6);
        if (!list6.isEmpty()) {
            SFPeopleInfo sFPeopleInfo = list6.get(0);
            map.put("sf", sFPeopleInfo);
        }

        EntityWrapper<SQJZPeopleinfo> wrapper7 = new EntityWrapper<>();
        wrapper7.eq("person_baseinfo_id", person_baseinfo_id);
        List<SQJZPeopleinfo> list7 = sQJZPeopleMapper.selectList(wrapper7);
        if (!list7.isEmpty()) {
            SQJZPeopleinfo sQJZPeopleinfo = list7.get(0);
            map.put("sqjz", sQJZPeopleinfo);
        }

        EntityWrapper<StayPeopleInfo> wrapper8 = new EntityWrapper<>();
        wrapper8.eq("person_baseinfo_id", person_baseinfo_id);
        List<StayPeopleInfo> list8 = stayPeopleMapper.selectList(wrapper8);
        if (!list8.isEmpty()) {
            StayPeopleInfo stayPeopleInfo = list8.get(0);
            map.put("ls", stayPeopleInfo);
        }

        EntityWrapper<XDInfo> wrapper9 = new EntityWrapper<>();
        wrapper9.eq("person_baseinfo_id", person_baseinfo_id);
        List<XDInfo> list9 = xDMapper.selectList(wrapper9);
        if (!list9.isEmpty()) {
            XDInfo xDInfo = list9.get(0);
            map.put("xd", xDInfo);
        }

        EntityWrapper<XmsfPeopleInfo> wrapper10 = new EntityWrapper<>();
        wrapper10.eq("person_baseinfo_id", person_baseinfo_id);
        List<XmsfPeopleInfo> list10 = xmsfPeopleMapper.selectList(wrapper10);
        if (!list10.isEmpty()) {
            XmsfPeopleInfo xmsfPeopleInfo = list10.get(0);
            map.put("xmsf", xmsfPeopleInfo);
        }

        EntityWrapper<ZDQSNCInfo> wrapper11 = new EntityWrapper<>();
        wrapper11.eq("person_baseinfo_id", person_baseinfo_id);
        List<ZDQSNCInfo> list11 = zDQSNCMapper.selectList(wrapper11);
        if (!list11.isEmpty()) {
            ZDQSNCInfo zDQSNCInfo = list11.get(0);
            map.put("zdqsn", zDQSNCInfo);
        }

        EntityWrapper<ZSZHInfo> wrapper12 = new EntityWrapper<>();
        wrapper12.eq("person_baseinfo_id", person_baseinfo_id);
        List<ZSZHInfo> list12 = zSZHMapper.selectList(wrapper12);
        if (!list12.isEmpty()) {
            ZSZHInfo zSZHInfo = list12.get(0);
            map.put("zszh", zSZHInfo);
        }

        return map;
    }

    public Page<InfoSearch> listPage(Integer ageStart, Integer ageEnd, String name, String idNum, String sex, String education, String job, String matrimony, String zzmm, String label, Integer pageNum, Integer pageSize, String rycf){
        Page<InfoSearch> page = new Page<>(pageNum, pageSize);
        EntityWrapper<InfoSearch> wrapper = new EntityWrapper<>();
        if (label == null || "".equals(label)) {
            if (ageStart != 0) {
                wrapper.ge("age", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("age", ageEnd);
            }

            if (StringUtils.isNotBlank(name)) {
                wrapper.eq("name", name);
            }
            if (StringUtils.isNotBlank(idNum)) {
                wrapper.eq("id_card_num", idNum);
            }
            if (StringUtils.isNotBlank(sex)) {
                wrapper.eq("gender", sex);
            }
            if (StringUtils.isNotBlank(education)) {
                wrapper.like("education", education);
            }
            if (StringUtils.isNotBlank(job)) {
                wrapper.like("profession", job);
            }
            if (StringUtils.isNotBlank(matrimony)) {
                wrapper.eq("matrimony", matrimony);
            }
            if (StringUtils.isNotBlank(zzmm)) {
                wrapper.eq("politic_countenance", zzmm);
            }
            if (StringUtils.isNotBlank(rycf)) {
                if ("flow".equals(rycf)) {
                    wrapper.eq("rksx", 2);
                } else if ("census".equals(rycf)) {
                    wrapper.eq("rksx", 1);
                }
            }
            wrapper.orderBy("gmt_create", false);
            List<InfoSearch> list = infoSearchMapper.selectPersonBaseInfoPage(page, wrapper);
            page.setRecords(list);
        } else {
            if (ageStart != 0) {
                wrapper.ge("b.age", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("b.age", ageEnd);
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
            if ("azb".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectAzbInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("bear".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectBearInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("cx".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectCxInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("engPeople".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectEngPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("flowPeople".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectFlowPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("militaryService".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectMilitaryServiceInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("party".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectPartyInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("sf".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectSfInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("sqjzpeople".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectSqjzpeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("stayPeople".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectStayPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("xd".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectXdPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("xmsf".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectXmsfPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("zdqsn".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectZdqsnPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            } else if ("zszh".equals(label)) {
                List<InfoSearch> list = infoSearchMapper.selectZszhPeopleInfoPage(page, wrapper);
                page.setRecords(list);
            }

        }
        return page;
    }

    public List<InfoSearch> list(Integer ageStart, Integer ageEnd, String name, String idNum, String sex, String education, String job, String matrimony, String zzmm, String label, String rycf){
        EntityWrapper<InfoSearch> wrapper = new EntityWrapper<>();
        List<InfoSearch> list = new ArrayList<>();
        if (label == null || "".equals(label)) {
            if (ageStart != 0) {
                wrapper.ge("age", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("age", ageEnd);
            }

            if (StringUtils.isNotBlank(name)) {
                wrapper.eq("name", name);
            }
            if (StringUtils.isNotBlank(idNum)) {
                wrapper.eq("id_card_num", idNum);
            }
            if (StringUtils.isNotBlank(sex)) {
                wrapper.eq("gender", sex);
            }
            if (StringUtils.isNotBlank(education)) {
                wrapper.like("education", education);
            }
            if (StringUtils.isNotBlank(job)) {
                wrapper.like("profession", job);
            }
            if (StringUtils.isNotBlank(matrimony)) {
                wrapper.eq("matrimony", matrimony);
            }
            if (StringUtils.isNotBlank(zzmm)) {
                wrapper.eq("politic_countenance", zzmm);
            }
            if (StringUtils.isNotBlank(rycf)) {
                if ("flow".equals(rycf)) {
                    wrapper.eq("rksx", 2);
                } else if ("census".equals(rycf)) {
                    wrapper.eq("rksx", 1);
                }
            }
            wrapper.orderBy("gmt_create", false);
            list = infoSearchMapper.selectPersonBaseInfo( wrapper);
        } else {
            if (ageStart != 0) {
                wrapper.ge("b.age", ageStart);
            }
            if (ageEnd != 0) {
                wrapper.le("b.age", ageEnd);
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
            if ("azb".equals(label)) {
                list = infoSearchMapper.selectAzbInfo(wrapper);

            } else if ("bear".equals(label)) {
                list = infoSearchMapper.selectBearInfo(wrapper);

            } else if ("cx".equals(label)) {
                list = infoSearchMapper.selectCxInfo(wrapper);

            } else if ("engPeople".equals(label)) {
                list = infoSearchMapper.selectEngPeopleInfo(wrapper);

            } else if ("flowPeople".equals(label)) {
                list = infoSearchMapper.selectFlowPeopleInfo(wrapper);

            } else if ("militaryService".equals(label)) {
                list = infoSearchMapper.selectMilitaryServiceInfo(wrapper);

            } else if ("party".equals(label)) {
                list = infoSearchMapper.selectPartyInfo(wrapper);

            } else if ("sf".equals(label)) {
                list = infoSearchMapper.selectSfInfo(wrapper);

            } else if ("sqjzpeople".equals(label)) {
                list = infoSearchMapper.selectSqjzpeopleInfo(wrapper);

            } else if ("stayPeople".equals(label)) {
                list = infoSearchMapper.selectStayPeopleInfo(wrapper);

            } else if ("xd".equals(label)) {
                list = infoSearchMapper.selectXdPeopleInfo(wrapper);

            } else if ("xmsf".equals(label)) {
                list = infoSearchMapper.selectXmsfPeopleInfo(wrapper);

            } else if ("zdqsn".equals(label)) {
                list = infoSearchMapper.selectZdqsnPeopleInfo(wrapper);

            } else if ("zszh".equals(label)) {
                list = infoSearchMapper.selectZszhPeopleInfo(wrapper);

            }

        }
        return list;
    }



}
