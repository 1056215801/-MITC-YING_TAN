package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Warning;
import com.mit.community.mapper.WarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报警业务层
 * @author Mr.Deng
 * @date 2018/11/27 14:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class WarningService extends ServiceImpl<WarningMapper, Warning> {

    private final WarningMapper warningMapper;

    @Autowired
    public WarningService(WarningMapper warningMapper) {
        this.warningMapper = warningMapper;
    }

    /**
     * 添加报警数据
     * @param warning 报警数据
     * @author Mr.Deng
     * @date 14:34 2018/11/27
     */
    public void save(Warning warning) {
        warningMapper.insert(warning);
    }

    /**
     * 删除所有警报数据
     * @author Mr.Deng
     * @date 15:47 2018/11/27
     */
    public void remove() {
        warningMapper.delete(null);
    }

    /**
     * 统计预警总数,通过小区code
     * @param communityCode 小区code
     * @return 预警总数
     * @author Mr.Deng
     * @date 16:49 2018/11/27
     */
    public Integer countByCommunityCode(String communityCode) {
        EntityWrapper<Warning> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return warningMapper.selectCount(wrapper);
    }

    /**
     * 统计预警总数,通过一组小区code
     * @param communityCodes 一组小区code
     * @return 预警总数
     * @author Mr.Deng
     * @date 16:49 2018/11/27
     */
    public Integer countByCommunityCodeList(List<String> communityCodes) {
        EntityWrapper<Warning> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return warningMapper.selectCount(wrapper);
    }

}
