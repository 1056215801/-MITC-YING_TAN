package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.AccessCard;
import com.mit.community.entity.AccessCardPageInfo;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.mapper.AccessCardMapper;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;
    @Autowired
    private PersonLabelsMapper personLabelsMapper;
    @Autowired
    private HouseholdRoomService householdRoomService;


    public void save(AccessCard accessCard) {
        accessCardMapper.insert(accessCard);
    }

    public void save(String cardNum, Integer houseHoldId, String deviceNum, Integer deviceGroupId, int isUpload) {
        AccessCard accessCard = new AccessCard();
        accessCard.setCardNum(cardNum);
        accessCard.setHouseHoldId(houseHoldId);
        accessCard.setIsUpload(isUpload);
        if (StringUtils.isNotBlank(deviceNum)) {
            accessCard.setDeviceNum(deviceNum);
        }
        if (deviceGroupId != null) {
            accessCard.setDeviceGroupId(deviceGroupId);
        }
        accessCard.setCardType(1);
        accessCard.setCardMedia(1);
        accessCard.setGmtCreate(LocalDateTime.now());
        accessCard.setGmtModified(LocalDateTime.now());
        accessCardMapper.insert(accessCard);
    }

    public AccessCard getByCardNumAndMac (String cardNum, String mac) {
        return personLabelsMapper.getByCardNumAndMac(cardNum, mac);
    }

    public AccessCard getByHouseHoidIdAndDeviceNum (Integer houseHoldId, String deviceNum) {
        EntityWrapper<AccessCard> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", houseHoldId);
        wrapper.eq("device_num", deviceNum);
        List<AccessCard> list = accessCardMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public AccessCard getByCardNumAndDeviceNum (String cardNum, String deviceNum) {
        EntityWrapper<AccessCard> wrapper = new EntityWrapper<>();
        wrapper.eq("card_num", cardNum);
        wrapper.eq("device_num", deviceNum);
        List<AccessCard> list = accessCardMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public AccessCard getByHouseHoldIdAndDeviceNumAndCardNum(Integer householdId, String deviceNum, String cardNum) {
        EntityWrapper<AccessCard> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        if (StringUtils.isNotBlank(deviceNum)) {
            wrapper.eq("device_num", deviceNum);
        }
        wrapper.eq("card_num", cardNum);
        List<AccessCard> list = accessCardMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public AccessCard getById (Integer id) {
        return accessCardMapper.selectById(id);
    }

    public void updateUploadById (int uploadStatu, Integer id) {
        AccessCard accessCard = new AccessCard();
        accessCard.setIsUpload(uploadStatu);
        accessCard.setGmtModified(LocalDateTime.now());
        accessCard.setId(id);
        accessCardMapper.updateById(accessCard);
    }

    public Page<AccessCardPageInfo> getMenJinCardPage(String communityCode, String cardNum, String mobile, Integer zoneId, Integer buildingId, Integer unitId, Integer roomeId, Integer cardType,
                                                      Integer cardMedia, Integer authType, Integer pageNum, Integer pageSize) {
        Page<AccessCardPageInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<AccessCardPageInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("b.community_code", communityCode);
        if (StringUtils.isNotBlank(cardNum)) {
            wrapper.eq("a.card_num", cardNum);
        }
        if (StringUtils.isNotBlank(mobile)) {
            wrapper.eq("b.mobile", mobile);
        }
        if (zoneId != null) {
            wrapper.eq("c.zone_id", zoneId);
        }
        if (buildingId != null) {
            wrapper.eq("c.building_id", buildingId);
        }
        if (unitId != null) {
            wrapper.eq("c.unit_id", unitId);
        }
        if (roomeId != null) {
            wrapper.eq("c.room_id", roomeId);
        }
        if (cardType != null) {
            wrapper.eq("a.card_type", cardType);
        }
        if (cardMedia != null) {
            wrapper.eq("a.card_media", cardMedia);
        }
        if (authType != null) {
            wrapper.eq("c.room_id", roomeId);
        }
        if (authType != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (authType == 1) {//期限时间小于30天，即将过期
                //查询residenceTime(LocalDate)字段
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                Date startDate = cal.getTime();
                cal.add(Calendar.DATE, 30);
                Date endDate = cal.getTime();
                wrapper.ge("b.validity_time", LocalDate.parse(sdf.format(startDate), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                wrapper.le("b.validity_time", LocalDate.parse(sdf.format(endDate), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (authType == 2) {//已过期
                wrapper.lt("b.validity_time", LocalDate.parse(sdf.format(new Date()), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
        List<AccessCardPageInfo> list = personLabelsMapper.selectMenJinCardPage(page, wrapper);
        if (!list.isEmpty()) {
            List<HouseholdRoom> rooms = null;
            for (int i=0; i < list.size(); i++) {
                rooms = new ArrayList<>();
                rooms = householdRoomService.listByHouseholdId(list.get(i).getHouseHoldId());
                list.get(i).setHouses(rooms);
            }
        }
        page.setRecords(list);
        return page;
    }

    public List<AccessCard> selectByHouseHoldId(Integer houseHoldId){
        EntityWrapper<AccessCard> wrapperCard = new EntityWrapper<>();
        wrapperCard.eq("household_id", houseHoldId);
        wrapperCard.setSqlSelect("distinct card_num,card_type,card_media");
        return accessCardMapper.selectList(wrapperCard);
    }
}
