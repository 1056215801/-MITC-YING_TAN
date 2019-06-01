package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.entity.InfoSearch;
import com.mit.community.mapper.mapper.InfoSearchMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoSearchService {
    @Autowired
    private InfoSearchMapper infoSearchMapper;

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
                System.out.println("=======================");
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

}
