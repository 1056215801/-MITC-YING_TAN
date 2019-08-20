package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.User;
import com.mit.community.entity.entity.PeopleDataUpload;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.PeopleDataUploadMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.service.LabelsService;
import com.mit.community.service.UserService;
import com.mit.community.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    private UserService userService;
    @Autowired
    private LabelsService labelsService;
    @Autowired
    private PeopleDataUploadMapper peopleDataUploadMapper;

    @Transactional
    public Integer save( String idCardNum, String name, String formerName, String gender, LocalDateTime birthday,
                        String nation, String nativePlace, String matrimony, String politicCountenance,
                        String education, String religion, String jobType, String profession, String cellphone,
                        String placeOfDomicile, String placeOfDomicileDetail, String placeOfReside,
                        String placeOfResideDetail, String placeOfServer, String photoBase64, String communityCode) {
        User user = userService.getByIDNumber(idCardNum);
        if (user != null) {
            if(StringUtils.isNotBlank(user.getName())){
                name = user.getName();
            }
            if(StringUtils.isNotBlank(user.getCellphone())){
                cellphone = user.getCellphone();
            }
        }
        PersonBaseInfo personBaseInfo = new PersonBaseInfo(idCardNum, name, formerName, gender, birthday, nation, nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, photoBase64, 0, 0, 0, null, null,communityCode);
        personBaseInfo.setGmtCreate(LocalDateTime.now());
        personBaseInfo.setGmtModified(LocalDateTime.now());
        if(this.getIdByCardNum(idCardNum) == null){
            personBaseInfoMapper.insert(personBaseInfo);
            return personBaseInfo.getId();
        } else {
            return 0;
        }
    }

    @Transactional
    public void save(PersonBaseInfo personBaseInfo) {
        User user = userService.getByIDNumber(personBaseInfo.getIdCardNum());
        if (user != null) {
            if(StringUtils.isNotBlank(user.getName())){
                personBaseInfo.setName(user.getName());
            }
            if(StringUtils.isNotBlank(user.getCellphone())){
                personBaseInfo.setCellphone(user.getCellphone());
            }
        }
        personBaseInfo.setGmtCreate(LocalDateTime.now());
        personBaseInfo.setGmtModified(LocalDateTime.now());
        Integer id = this.getIdByCardNum(personBaseInfo.getIdCardNum());
        if( id == null){
            personBaseInfoMapper.insert(personBaseInfo);
        } else {
            personBaseInfo.setId(id);
            personBaseInfoMapper.updateById(personBaseInfo);
        }
    }

    @Transactional
    public Integer saveReturnId(PersonBaseInfo personBaseInfo) {
        User user = userService.getByIDNumber(personBaseInfo.getIdCardNum());
        if (user != null) {
            if(StringUtils.isNotBlank(user.getName())){
                personBaseInfo.setName(user.getName());
            }
            if(StringUtils.isNotBlank(user.getCellphone())){
                personBaseInfo.setCellphone(user.getCellphone());
            }
        }
        personBaseInfo.setGmtCreate(LocalDateTime.now());
        personBaseInfo.setGmtModified(LocalDateTime.now());
        Integer id = this.getIdByCardNum(personBaseInfo.getIdCardNum());
        if( id == null){
            personBaseInfoMapper.insert(personBaseInfo);
            id = personBaseInfo.getId();
        } else {
            personBaseInfo.setId(id);
            personBaseInfoMapper.updateById(personBaseInfo);
        }
        return id;
    }

    public boolean isExist(String idCardNum) {
        boolean flag = true;
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id_card_num", idCardNum);
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (list.isEmpty()) {
            flag = false;
        }
        return flag;
    }

    public void updateByIdCardNum(Integer baseId, String idCardNum, String name, String formerName, String gender, LocalDateTime birthday,
                                  String nation, String nativePlace, String matrimony, String politicCountenance, String education,
                                  String religion, String jobType, String profession, String cellphone, String placeOfDomicile,
                                  String placeOfDomicileDetail, String placeOfReside, String placeOfResideDetail,
                                  String placeOfServer, String base64, Integer rksx,String communityCode) {
        PersonBaseInfo personBaseInfo = new PersonBaseInfo(idCardNum, name, formerName, gender, birthday, nation,
                nativePlace, matrimony, politicCountenance, education, religion, jobType, profession, cellphone, placeOfDomicile,
                placeOfDomicileDetail, placeOfReside, placeOfResideDetail, placeOfServer, base64, rksx, 0, 0, null, null,communityCode);
        personBaseInfo.setId(baseId);
        personBaseInfo.setGmtModified(LocalDateTime.now());
        personBaseInfoMapper.updateById(personBaseInfo);
        //EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        //wrapper.eq("id", baseId);
        //personBaseInfoMapper.update(personBaseInfo, wrapper);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/6 10:59
     * @Company mitesofor
     * @Description:~根据主键查询基本信息
     */
    public PersonBaseInfo getObjectById(Integer id) {
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<PersonBaseInfo> infos = personBaseInfoMapper.selectList(wrapper);
        if (!infos.isEmpty()) {
            return infos.get(0);
        }
        return null;
    }

    public PersonBaseInfo getPersonByMobile(String cellphone, String communityCode) {
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        if(StringUtils.isNotBlank(communityCode)){
            wrapper.eq("community_code", communityCode);
        }
        List<PersonBaseInfo> infos = personBaseInfoMapper.selectList(wrapper);
        if (!infos.isEmpty()) {
            return infos.get(0);
        }
        return null;
    }

    @Transactional
    public void delete(Integer id) {
        /*PersonBaseInfo personBaseInfo = new PersonBaseInfo();
        personBaseInfo.setIsDelete(1);
        EntityWrapper<PersonBaseInfo> dalete = new EntityWrapper<>();
        dalete.eq("id", id);
        personBaseInfoMapper.update(personBaseInfo, dalete);*/
        personBaseInfoMapper.deleteById(id);
    }

    public Integer getIdByCardNum(String idCardNum) {
        Integer id = null;
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id_card_num", idCardNum);
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();
        }
        return id;
    }

    public Integer getIdByNameAndPhone(String name, String phone) {
        Integer id = null;
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        //wrapper.eq("name", name);
        wrapper.eq("cellphone", phone);
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();

        }
        return id;
    }

    public String getLabelsByCredentialNum(String idCardNum) {
        String label = "";
        Integer id = null;
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id_card_num", idCardNum);
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();
            List<String> strings = labelsService.getLabelsByUserId(id);
            if(!strings.isEmpty()){
                for(int i=0; i<strings.size(); i++){
                    label = label + strings.get(i) + ",";
                }
                label = label.substring(0,label.length()-1);
            }
        }

        return label;
    }

    public String getLabelsByMobile(String cellphone, String communityCode) {
        String label = "";
        Integer id = null;
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();
            List<String> strings = labelsService.getLabelsByUserId(id);
            if(!strings.isEmpty()){
                for(int i=0; i<strings.size(); i++){
                    label = label + strings.get(i) + ",";
                }
                label = label.substring(0,label.length()-1);
            }
        }

        return label;
    }

    @Transactional
    public void saveList(List<PersonBaseInfo> list) {
        for(PersonBaseInfo azbInfo:list){
            this.save(azbInfo);
        }
    }

    public void updateById(Integer id, String communityCode){
        PersonBaseInfo personBaseInfo = new PersonBaseInfo();
        personBaseInfo.setId(id);
        personBaseInfo.setCommunity_code(communityCode);
        personBaseInfoMapper.updateById(personBaseInfo);
    }

    public void dataUpload(String community, String building, String unit, String room, String relation, String name, String sex, String idCard, String phone, String nation, String jg, String hyzk, String zzmm, String job,
                           String edu, String image, String carNum){
        EntityWrapper<PeopleDataUpload> wrapper = new EntityWrapper<>();
        wrapper.eq("phone", phone);
        //wrapper.eq("idCard", idCard);
        List<PeopleDataUpload> list = peopleDataUploadMapper.selectList(wrapper);
        if(list.isEmpty()){
            PeopleDataUpload peopleDataUpload = new PeopleDataUpload(community, building, unit, room, relation, name, sex, idCard, phone, nation, jg, hyzk, zzmm, job,
                    edu, image, carNum);
            peopleDataUpload.setGmtCreate(LocalDateTime.now());
            peopleDataUpload.setGmtModified(LocalDateTime.now());
            peopleDataUploadMapper.insert(peopleDataUpload);
        } else {
            PeopleDataUpload peopleDataUpload = new PeopleDataUpload(community, building, unit, room, relation, name, sex, idCard, phone, nation, jg, hyzk, zzmm, job,
                    edu, image, carNum);
            peopleDataUpload.setId(list.get(0).getId());
            peopleDataUpload.setGmtModified(LocalDateTime.now());
            peopleDataUploadMapper.updateById(peopleDataUpload);
        }
    }

}
