package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.AccessRecord;
import com.mit.community.mapper.AccessRecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author HuShanLin
 * @Date Created in 18:03 2019/6/27
 * @Company: mitesofor </p>
 * @Description:~
 */
@Service
public class AccessRecordService {

    @Autowired
    private AccessRecordMapper accessRecordMapper;

    public Page<AccessRecord> listPage(String communityCode, String carnum, String accessType, String carphone, LocalDateTime begintime, LocalDateTime endtime,
                                       Integer pageNum, Integer pageSize) {
        Page<AccessRecord> page = new Page<>(pageNum, pageSize);
        EntityWrapper<AccessRecord> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(carnum)) {
            wrapper.eq("car_num", carnum);
        }
        if (StringUtils.isNotBlank(accessType)) {
            wrapper.eq("accessType", accessType);
        }
        if (StringUtils.isNotBlank(carphone)) {
            wrapper.eq("owner_phone", carphone);
        }
        if (begintime != null) {
            wrapper.ge("passtime", begintime);
        }
        if (endtime != null) {
            wrapper.le("passtime", endtime);
        }
        wrapper.orderBy("gmt_create", false);
        List<AccessRecord> list = accessRecordMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public void save(AccessRecord accessRecord) {
        accessRecordMapper.insert(accessRecord);
    }

    public AccessRecord getNoPhotoRecord(String communityCode){
        EntityWrapper<AccessRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code",communityCode);
        wrapper.like("car_num_patch","%||E-N-D");
        wrapper.orderBy("gmt_create",false);
        wrapper.last("limit 1");
        List<AccessRecord> list = accessRecordMapper.selectList(wrapper);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public void updateObjectById(AccessRecord accessRecord){
        accessRecordMapper.updateById(accessRecord);
    }
}
