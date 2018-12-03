package com.mit.community.service;

import com.mit.community.entity.ApplyKey;
import com.mit.community.entity.ApplyKeyImg;
import com.mit.community.mapper.ApplyKeyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 申请钥匙业务层
 * @author Mr.Deng
 * @date 2018/12/3 14:45
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ApplyKeyService {

    @Autowired
    private ApplyKeyMapper applyKeyMapper;
    @Autowired
    private ApplyKeyImgService applyKeyImgService;

    /**
     * 添加申请钥匙数据
     * @param applyKey 申请钥匙数据
     * @return 添加数据条数
     * @author Mr.Deng
     * @date 11:07 2018/11/30
     */
    public Integer save(ApplyKey applyKey) {
        applyKey.setGmtCreate(LocalDateTime.now());
        applyKey.setGmtModified(LocalDateTime.now());
        return applyKeyMapper.insert(applyKey);
    }

    /**
     * 申请钥匙
     * @param communityCode    小区code
     * @param communityName    小区名称
     * @param zoneId           分区id
     * @param zoneName         分区名称
     * @param buildingId       楼栋id
     * @param buildingName     楼栋名称
     * @param unitId           单元id
     * @param unitName         单元名称
     * @param roomId           房间id
     * @param roomNum          房间编号
     * @param contactPerson    申请人
     * @param contactCellphone 申请人电话
     * @param content          描述
     * @param creatorUserId    创建人id
     * @author Mr.Deng
     * @date 14:50 2018/12/3
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertApplyKey(String communityCode, String communityName, Integer zoneId, String zoneName,
                               Integer buildingId, String buildingName, Integer unitId, String unitName, Integer roomId,
                               String roomNum, String contactPerson, String contactCellphone, String content,
                               Integer creatorUserId, List<String> images) {
        //初始化审批时间
        String str = "0000-00-00 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        ApplyKey applyKey = new ApplyKey(communityCode, communityName, zoneId, zoneName, buildingId, buildingName,
                unitId, unitName, roomId, roomNum, contactPerson, contactCellphone, 2, content, creatorUserId,
                StringUtils.EMPTY, dateTime);
        Integer applyKeySave = this.save(applyKey);
        if (applyKeySave > 0) {
            if (images.size() > 0 && StringUtils.isNotBlank(images.get(0))) {
                for (String image : images) {
                    ApplyKeyImg applyKeyImg = new ApplyKeyImg(applyKey.getId(), image);
                    applyKeyImgService.save(applyKeyImg);
                }
            }
        }
    }

}
