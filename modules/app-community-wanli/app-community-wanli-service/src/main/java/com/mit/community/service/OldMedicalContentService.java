package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.OldMedicalContent;
import com.mit.community.mapper.OldMedicalContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 老人体检详情信息业务处理层
 * @author Mr.Deng
 * @date 2018/12/18 19:40
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class OldMedicalContentService {
    @Autowired
    private OldMedicalContentMapper oldMedicalContentMapper;

    /**
     * 查找老人体检详情信息
     * @param oldMedicalId 老人体检id
     * @return 老人体检详情信息
     * @author Mr.Deng
     * @date 19:55 2018/12/18
     */
    public OldMedicalContent getByOldMedicalId(Integer oldMedicalId) {
        EntityWrapper<OldMedicalContent> wrapper = new EntityWrapper<>();
        wrapper.eq("old_medical_id", oldMedicalId);
        List<OldMedicalContent> oldMedicalContents = oldMedicalContentMapper.selectList(wrapper);
        if (oldMedicalContents.isEmpty()) {
            return null;
        }
        return oldMedicalContents.get(0);
    }

    /**
     * 添加老人体检详情
     * @param oldMedicalContent 老人体检详情
     * @author Mr.Deng
     * @date 17:01 2019/1/2
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(OldMedicalContent oldMedicalContent) {
        oldMedicalContent.setGmtCreate(LocalDateTime.now());
        oldMedicalContent.setGmtModified(LocalDateTime.now());
        oldMedicalContentMapper.insert(oldMedicalContent);
    }

    /**
     * 更新老人体检详情
     * @param oldMedicalContent 老人体检详情
     * @author Mr.Deng
     * @date 17:20 2019/1/2
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByOldMedicalId(OldMedicalContent oldMedicalContent) {
        oldMedicalContent.setGmtModified(LocalDateTime.now());
        EntityWrapper<OldMedicalContent> wrapper = new EntityWrapper<>();
        wrapper.eq("old_medical_id", oldMedicalContent.getOldMedicalId());
        oldMedicalContentMapper.update(oldMedicalContent, wrapper);
    }

    /**
     * 删除老人详情信息，通过老人体检id
     * @param oldMedicalId 老人体检id
     * @author Mr.Deng
     * @date 17:33 2019/1/2
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer oldMedicalId) {
        EntityWrapper<OldMedicalContent> wrapper = new EntityWrapper<>();
        wrapper.eq("old_medical_id", oldMedicalId);
        oldMedicalContentMapper.delete(wrapper);
    }
}
