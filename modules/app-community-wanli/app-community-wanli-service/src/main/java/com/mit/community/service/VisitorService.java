package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.mit.community.entity.Visitor;
import com.mit.community.entity.VisitorImg;
import com.mit.community.entity.VisitorRead;
import com.mit.community.mapper.VisitorMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 访客业务处理层
 * @author Mr.Deng
 * @date 2018/12/3 16:46
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private VisitorImgService visitorImgService;
    @Autowired
    private VisitorReadService visitorReadService;

    /**
     * 查询所有访客信息
     * @return 访客信息列表
     * @author Mr.Deng
     * @date 17:16 2018/12/3
     */
    public Page<Visitor> listPage(Integer userId, String cellphone, Integer pageNum, Integer pageSize) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.eq("invite_mobile", cellphone);
        Page<Visitor> page = new Page<>(pageNum, pageSize);
        List<Visitor> visitors = visitorMapper.selectPage(page, wrapper);
        if (!visitors.isEmpty()) {
            Map<Integer, Visitor> map = Maps.newHashMapWithExpectedSize(visitors.size());
            List<Integer> visitorIds = visitors.stream().map(Visitor::getId).collect(Collectors.toList());
            visitors.forEach(item -> {
                item.setReadStatus(false);
                map.put(item.getId(), item);
            });
            List<VisitorRead> visitorReads = visitorReadService.listByUserIdAndVisitorId(userId, visitorIds);
            visitorReads.forEach(item -> {
                Integer visitorId = item.getVisitorId();
                Visitor visitor = map.get(visitorId);
                visitor.setReadStatus(true);
            });
        }
        page.setRecords(visitors);
        return page;
    }

    public Page<Visitor> listWebPage( String communityCode, String inviteName, String inviteMobile, String codeType, String codeStatus,
                                      LocalDateTime gmtCreateTimeStart, LocalDateTime gmtCreateTimeEnd,Integer pageNum, Integer pageSize){
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(inviteName)) {
            wrapper.eq("invite_name", inviteName);
        }
        if (StringUtils.isNotBlank(inviteMobile)) {
            wrapper.eq("invite_mobile", inviteMobile);
        }
        if (StringUtils.isNotBlank(codeType)) {
            wrapper.eq("code_type", codeType);
        }
        if (StringUtils.isNotBlank(codeStatus)) {
            wrapper.eq("code_status", codeStatus);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("gmt_create", gmtCreateTimeEnd);
        }
        wrapper.orderBy("gmt_create", false);
        Page<Visitor> page = new Page<>(pageNum, pageSize);
        List<Visitor> visitors = visitorMapper.selectPage(page, wrapper);
        page.setRecords(visitors);
        return page;
    }
    /**
     * 获取访客详情
     * @param id id
     * @return com.mit.community.entity.Visitor
     * @author shuyy
     * @date 2018/12/29 10:44
     * @company mitesofor
     */
    public Visitor getById(Integer id) {
        Visitor visitor = visitorMapper.selectById(id);
        if (visitor != null) {
            List<VisitorImg> visitorImgs = visitorImgService.listByVisitorId(id);
            visitor.setVisitorImgList(visitorImgs);
            return visitor;
        }
        return null;
    }

}
