package com.mit.community.population.service;

import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 人员基本信息
 *
 * @author xiong
 * @date 2019/5/25
 * @company mitesofor
 */
@Service
public class PersonBaseInfoService {
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public Integer save(String idCardNum, String name, String formerName, String gender, LocalDateTime birthday, String nation, String nativePlace, String matrimony, String politicCountenance,
                        String education, String religion, String jobType, String profession, String cellphone, String placeOfDomicile, String placeOfDomicileDetail, String placeOfReside,
                        String placeOfResideDetail, String placeOfServer, String photoBase64) {
        PersonBaseInfo personBaseInfo = new PersonBaseInfo(idCardNum, name, formerName, gender, birthday, nation, nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, photoBase64, 0, 0);
        personBaseInfo.setGmtCreate(LocalDateTime.now());
        personBaseInfo.setGmtModified(LocalDateTime.now());
        personBaseInfoMapper.insert(personBaseInfo);
        return personBaseInfo.getId();
    }
}
