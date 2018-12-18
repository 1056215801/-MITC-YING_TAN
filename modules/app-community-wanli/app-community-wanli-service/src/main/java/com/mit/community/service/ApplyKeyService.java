package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.Constants;
import com.mit.community.entity.ApplyKey;
import com.mit.community.entity.ApplyKeyImg;
import com.mit.community.mapper.ApplyKeyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Autowired
    private UserTrackService userTrackService;

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
     * 修改数据
     * @param applyKey 申请钥匙数据
     * @return 修改数据
     * @author Mr.Deng
     * @date 15:28 2018/12/3
     */
    public Integer update(ApplyKey applyKey) {
        applyKey.setGmtModified(LocalDateTime.now());
        return applyKeyMapper.updateById(applyKey);
    }

    /**
     * 查询申请钥匙数据，通过申请钥匙id
     * @param applyKeyId 申请钥匙id
     * @return 申请钥匙信息
     * @author Mr.Deng
     * @date 15:30 2018/12/3
     */
    public ApplyKey selectById(Integer applyKeyId) {
        return applyKeyMapper.selectById(applyKeyId);
    }

    /**
     * 查询申请钥匙列表. 通过申请状态
     * @param zoneId           分区id
     * @param buildingId       楼栋id
     * @param unitId           单元id
     * @param roomId           房间id
     * @param contactPerson    联系人
     * @param contactCellphone 联系电话
     * @param status           状态
     * @return java.util.List<com.mit.community.entity.ApplyKey>
     * @author shuyy
     * @date 2018/12/14 16:03
     * @company mitesofor
     */
    public List<ApplyKey> listByPage(Integer createUserId, String communityCode, Integer zoneId, Integer buildingId, Integer unitId,
                                     Integer roomId, String contactPerson, String contactCellphone, Integer status, Integer pageNum, Integer pageSize) {
        EntityWrapper<ApplyKey> wrapper = new EntityWrapper<>();
        if (createUserId != null) {
            wrapper.eq("creator_user_id", createUserId);
        }
        if (zoneId != null) {
            wrapper.eq("zone_id", zoneId);
        }
        if (buildingId != null) {
            wrapper.eq("building_id", buildingId);
        }
        if (unitId != null) {
            wrapper.eq("unit_id", unitId);
        }
        if (roomId != null) {
            wrapper.eq("roomId", roomId);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(contactPerson)) {
            wrapper.eq("contact_person", contactPerson);
        }
        if (StringUtils.isNotBlank(contactCellphone)) {
            wrapper.eq("contact_cellphone", contactCellphone);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderBy("gmt_create", false);
        if (pageNum != null && pageSize != null) {
            Page<ApplyKey> page = new Page<>(pageNum, pageSize);
            return applyKeyMapper.selectPage(page, wrapper);
        }
        return applyKeyMapper.selectList(wrapper);
    }

    /*
    public List<ApplyKey> listByCellphone(String contactPerson) {
        EntityWrapper<ApplyKey> wrapper = new EntityWrapper<>();
        wrapper.eq("contact_person", contactPerson);
        return applyKeyMapper.selectList(wrapper);
    }
*/

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
                               Integer creatorUserId, String idCard, List<String> images) {
        ApplyKey applyKey = new ApplyKey(communityCode, communityName, zoneId, zoneName, buildingId, buildingName,
                unitId, unitName, roomId, roomNum, contactPerson, contactCellphone, 1, content, creatorUserId,
                StringUtils.EMPTY, Constants.NULL_LOCAL_DATE_TIME, idCard);
        this.save(applyKey);
        for (String image : images) {
            ApplyKeyImg applyKeyImg = new ApplyKeyImg(applyKey.getId(), image);
            applyKeyImgService.save(applyKeyImg);
        }
    }

    /**
     * 审批申请钥匙
     * @param applyKeyId  申请钥匙id
     * @param checkPerson 审批人
     * @author Mr.Deng
     * @date 15:26 2018/12/3
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByCheckPerson(Integer applyKeyId, String checkPerson) {
        ApplyKey applyKey = this.selectById(applyKeyId);
        applyKey.setCheckTime(LocalDateTime.now());
        applyKey.setStatus(2);
        applyKey.setCheckPerson(checkPerson);
        this.update(applyKey);
    }

}
