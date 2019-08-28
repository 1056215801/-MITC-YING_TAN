package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.AccessCard;
import com.mit.community.mapper.AccessCardMapper;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;
    @Autowired
    private PersonLabelsMapper personLabelsMapper;

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

}
