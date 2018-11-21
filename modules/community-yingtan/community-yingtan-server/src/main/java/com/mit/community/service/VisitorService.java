package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Visitor;
import com.mit.community.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访客业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 20:19
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class VisitorService extends ServiceImpl<VisitorMapper, Visitor> {

    private final VisitorMapper visitorMapper;

    @Autowired
    public VisitorService(VisitorMapper visitorMapper) {
        this.visitorMapper = visitorMapper;
    }

    /**
     * 添加访客信息
     *
     * @param visitor 访客信息
     * @return
     * @author Mr.Deng
     * @date 20:20 2018/11/14
     */
    public void save(Visitor visitor) {
        visitorMapper.insert(visitor);
    }

    /**
     * 获取所有的访客信息
     *
     * @return 访客信息列表
     * @author Mr.Deng
     * @date 20:21 2018/11/14
     */
    public List<Visitor> list() {
        return visitorMapper.selectList(null);
    }

    /**
     * 通过小区code获取访客信息表
     *
     * @param communityCode 小区code
     * @return 访客记录列表
     * @author Mr.Deng
     * @date 15:17 2018/11/21
     */
    public List<Visitor> listByCommunityCode(String communityCode) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return visitorMapper.selectList(wrapper);
    }

    /**
     * 通过一组小区code获取访客记录信息
     *
     * @param communityCodes 小区code列表
     * @return 访客记录列表
     * @author Mr.Deng
     * @date 15:19 2018/11/21
     */
    public List<Visitor> listByCommunityCodes(List<String> communityCodes) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return visitorMapper.selectList(wrapper);
    }

}
