package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.AccessRecord;
import com.mit.community.entity.OwnerShipInfo;
import com.mit.community.mapper.AccessRecordMapper;
import com.mit.community.population.service.PersonLabelsService;
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
    @Autowired
    private PersonLabelsService personLabelsService;

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
            wrapper.eq("access_type", accessType);
        }
        if (StringUtils.isNotBlank(carphone)) {
            wrapper.eq("owner_phone", carphone);
        }
        if (begintime != null) {
            wrapper.ge("gmt_create", begintime);
        }
        if (endtime != null) {
            wrapper.le("gmt_create", endtime);
        }
        wrapper.orderBy("gmt_create", false);
        List<AccessRecord> list = accessRecordMapper.selectPage(page, wrapper);
        if (!list.isEmpty()) {
            for (int i=0; i<list.size(); i++) {
                List<OwnerShipInfo> ownerInfo = personLabelsService.getOwnerInfo(list.get(i).getCarnum());
                if (!ownerInfo.isEmpty()) {
                    list.get(i).setCarOwner(ownerInfo.get(0).getCarOwner());
                    list.get(i).setOwnerHouse(ownerInfo.get(0).getZoneName()+ownerInfo.get(0).getBuildingName()+ownerInfo.get(0).getUnitName()+ownerInfo.get(0).getRoomNum());
                }
            }
        }
        page.setRecords(list);
        return page;
    }
}
