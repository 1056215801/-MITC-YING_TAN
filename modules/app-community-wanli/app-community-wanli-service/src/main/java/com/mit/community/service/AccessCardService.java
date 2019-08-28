package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.AccessCard;
import com.mit.community.mapper.AccessCardMapper;
<<<<<<< HEAD
<<<<<<< HEAD
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
=======
>>>>>>> remotes/origin/newdev
=======
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
import org.apache.commons.lang3.StringUtils;
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;
<<<<<<< HEAD
<<<<<<< HEAD
    @Autowired
    private PersonLabelsMapper personLabelsMapper;
=======
>>>>>>> remotes/origin/newdev
=======
    @Autowired
    private PersonLabelsMapper personLabelsMapper;
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10

    public void save(AccessCard accessCard) {
        accessCardMapper.insert(accessCard);
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======

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
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10

    public AccessCard getByCardNumAndMac (String cardNum, String mac) {
        return personLabelsMapper.getByCardNumAndMac(cardNum, mac);
    }

<<<<<<< HEAD

=======
>>>>>>> remotes/origin/newdev
=======
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

>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10
}
