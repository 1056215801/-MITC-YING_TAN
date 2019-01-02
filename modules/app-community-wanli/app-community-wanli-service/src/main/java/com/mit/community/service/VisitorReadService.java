package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.VisitorRead;
import com.mit.community.mapper.VisitorReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访客表已读业务处理层
 * @author Mr.Deng
 * @date 2019/1/2 10:23
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class VisitorReadService extends ServiceImpl<VisitorReadMapper, VisitorRead> {
    @Autowired
    private VisitorReadMapper visitorReadMapper;

    /**
     * 添加访客已读信息
     * @param visitorRead 访客已读信息
     * @author Mr.Deng
     * @date 10:24 2019/1/2
     */
    public void save(VisitorRead visitorRead) {
        visitorReadMapper.insert(visitorRead);
    }

    /**
     * 查询访客已读信息，通告用户id和访客表id列表
     * @param userId     用户id
     * @param visitorIds 访客表id列表
     * @return 访客已读信息
     * @author Mr.Deng
     * @date 10:47 2019/1/2
     */
    public List<VisitorRead> listByUserIdAndVisitorId(Integer userId, List<Integer> visitorIds) {
        EntityWrapper<VisitorRead> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.in("visitor_id", visitorIds);
        return visitorReadMapper.selectList(wrapper);
    }

    /**
     * 查询访客已读信息，通告用户id和访客表id
     * @param userId    用户id
     * @param visitorId 访客表id
     * @return 访客已读信息
     * @author Mr.Deng
     * @date 10:58 2019/1/2
     */
    public VisitorRead getVistorReadByUserIdAndVisitorId(Integer userId, Integer visitorId) {
        EntityWrapper<VisitorRead> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("visitor_id", visitorId);
        List<VisitorRead> visitorReads = visitorReadMapper.selectList(wrapper);
        if (visitorReads.isEmpty()) {
            return null;
        }
        return visitorReads.get(0);
    }

}
