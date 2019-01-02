package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.OldMedical;
import com.mit.community.entity.OldMedicalContent;
import com.mit.community.entity.OldMedicalReadUser;
import com.mit.community.mapper.OldMedicalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 老人体检业务处理层
 *
 * @author Mr.Deng
 * @date 2018/12/18 19:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class OldMedicalService {
    @Autowired
    private OldMedicalMapper oldMedicalMapper;
    @Autowired
    private OldMedicalContentService oldMedicalContentService;
    @Autowired
    private OldMedicalReadUserService oldMedicalReadUserService;

    /**
     * 查询老人体检信息，通过小区code
     *
     * @param communityCode 小区code
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 19:58 2018/12/18
     */
    public List<OldMedical> listByCommunityCode(String communityCode) {
        EntityWrapper<OldMedical> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.orderBy("gmt_create", false);
        return oldMedicalMapper.selectList(wrapper);
    }

    /**
     * 查询老人体检信息，通过老人体检id
     *
     * @param id 老人体检id
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 20:00 2018/12/18
     */
    public OldMedical getById(Integer id) {
        return oldMedicalMapper.selectById(id);
    }

    /**
     * 查询所有的老人体检信息
     *
     * @param userId        用户id
     * @param communityCode 小区code
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 20:08 2018/12/18
     */
    public List<OldMedical> listAll(Integer userId, String communityCode) {
        List<OldMedical> oldMedicals = this.listByCommunityCode(communityCode);
        if (!oldMedicals.isEmpty()) {
            for (OldMedical oldMedical : oldMedicals) {
                String status = getStatus(oldMedical.getStartTime(), oldMedical.getEndTime());
                oldMedical.setOldMedicalStatus(status);
                List<OldMedicalReadUser> oldMedicalReadUsers = oldMedicalReadUserService.getByUserIdOldMedicalId(userId, oldMedical.getId());
                if (oldMedicalReadUsers.isEmpty()) {
                    oldMedical.setReadStatus(false);
                } else {
                    oldMedical.setReadStatus(true);
                }
            }
        }
        return oldMedicals;
    }

    /**
     * 查询老人体检信息，通过老人体检id
     *
     * @param oldMedicalId 老人体检id
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 20:12 2018/12/18
     */
    public OldMedical getByOldMedicalId(Integer oldMedicalId) {
        OldMedical oldMedical = this.getById(oldMedicalId);
        if (oldMedical != null) {
            Integer readNum = oldMedicalReadUserService.countByUserIdOldMedicalId(oldMedicalId);
            oldMedical.setReadNum(readNum);
            String status = getStatus(oldMedical.getStartTime(), oldMedical.getEndTime());
            oldMedical.setOldMedicalStatus(status);
            OldMedicalContent oldMedicalContent = oldMedicalContentService.getByOldMedicalId(oldMedicalId);
            if (oldMedicalContent != null) {
                oldMedical.setContent(oldMedicalContent.getContent());
            }
        }
        return oldMedical;
    }

    /**
     * 判断当前活动
     *
     * @param startTime 活动开始时间
     * @param endTime   活动结束时间
     * @return 活动状态
     * @author Mr.Deng
     * @date 17:09 2018/12/18
     */
    private static String getStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        if (nowTime.isBefore(startTime)) {
            return "未开始";
        }
        if (nowTime.isAfter(endTime)) {
            return "已结束";
        }
        return "进行中";
    }

    /**
     * 统计未读
     * @param communityCode 小区code
     * @param userId userId
     * @return java.lang.Integer
     * @author shuyy
     * @date 2019-01-02 16:11
     * @company mitesofor
    */
    public Integer countNotRead(String communityCode, Integer userId) {
        EntityWrapper<OldMedical> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        Integer num = oldMedicalMapper.selectCount(wrapper);
        Integer notRead = oldMedicalReadUserService.countReadNum(userId);
        return num - notRead;

    }

}
