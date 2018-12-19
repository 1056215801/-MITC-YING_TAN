package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.OldMedicalReadUser;
import com.mit.community.mapper.OldMedicalReadUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 老人体检已读业务处理层
 * @author Mr.Deng
 * @date 2018/12/18 19:41
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class OldMedicalReadUserService {
    @Autowired
    private OldMedicalReadUserMapper oldMedicalReadUserMapper;

    /**
     * 添加老人条件已读信息
     * @param oldMedicalReadUser 老人体检信息
     * @author Mr.Deng
     * @date 19:42 2018/12/18
     */
    public void save(OldMedicalReadUser oldMedicalReadUser) {
        oldMedicalReadUserMapper.insert(oldMedicalReadUser);
    }

    /**
     * 查询老人体检已读信息
     * @param userId       用户id
     * @param oldMedicalId 老人体检id
     * @return 老人体检
     * @author Mr.Deng
     * @date 19:50 2018/12/18
     */
    public List<OldMedicalReadUser> getByUserIdOldMedicalId(Integer userId, Integer oldMedicalId) {
        EntityWrapper<OldMedicalReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("old_medical_id", oldMedicalId);
        return oldMedicalReadUserMapper.selectList(wrapper);
    }

    /**
     * 老人体检浏览量
     * @param oldMedicalId 老人体检id
     * @return 浏览量
     * @author Mr.Deng
     * @date 19:50 2018/12/18
     */
    public Integer countByUserIdOldMedicalId(Integer oldMedicalId) {
        EntityWrapper<OldMedicalReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("old_medical_id", oldMedicalId);
        return oldMedicalReadUserMapper.selectCount(wrapper);
    }
}