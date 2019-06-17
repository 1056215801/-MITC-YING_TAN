package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Investigation;
import com.mit.community.mapper.InvestigationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/31 19:40
 * @Company mitesofor
 * @Description:
 */
@Service
public class InvestigationService extends ServiceImpl<InvestigationMapper, Investigation> {

    @Autowired
    private InvestigationMapper investigationMapper;

    public Page<Investigation> listPages(String communityCode, String title, Integer status,
                                         LocalDateTime starttime, LocalDateTime endtime,
                                         Integer pageNum, Integer pageSize) {
        EntityWrapper<Investigation> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.eq("title", title);
        }
        if (status != null) {
            wrapper.eq("status", status);
        } else {
            wrapper.lt("status", 2);
        }
        if (starttime != null) {
            wrapper.le("starttime", starttime);
        }
        if (endtime != null) {
            wrapper.ge("endtime", endtime);
        }
        wrapper.orderBy("gmt_create", false);
        Page<Investigation> page = new Page<>(pageNum, pageSize);
        List<Investigation> records = investigationMapper.selectPage(page, wrapper);
        page.setRecords(records);
        return page;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:49
     * @Company mitesofor
     * @Description:~保存
     */
    public String save(String communityCode, Integer id, String title, Integer creater,
                       LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
        String msg = "";
        try {
            if (id == null) {
                Investigation investigation = new Investigation(title, communityCode, 1, status, creater,
                        beginTime, endTime, 0);
                investigation.setGmtCreate(LocalDateTime.now());
                investigation.setGmtModified(LocalDateTime.now());
                investigationMapper.insert(investigation);
            } else {
                Investigation investigation = this.getObjectById(id);
                investigation.setTitle(title);
                investigation.setBegintime(beginTime);
                investigation.setEndtime(endTime);
                investigation.setStatus(status);
                investigation.setGmtModified(LocalDateTime.now());
                investigationMapper.updateById(investigation);
            }
            msg = "success";
        } catch (Exception e) {
            //msg = "fail";
            throw new RuntimeException(e.getMessage());
        }
        return msg;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 10:06
     * @Company mitesofor
     * @Description:~获取单个对象
     */
    public Investigation getObjectById(Integer id) {
        EntityWrapper<Investigation> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<Investigation> list = investigationMapper.selectList(wrapper);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:53
     * @Company mitesofor
     * @Description:~移除
     */
    public String remove(Integer id) {
        String msg = "";
        try {
            Investigation investigation = this.getObjectById(id);
            investigation.setStatus(2);//删除
            investigation.setGmtModified(LocalDateTime.now());
            investigationMapper.updateById(investigation);
            msg = "success";
        } catch (Exception e) {
            msg = "fail";
        }
        return msg;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:55
     * @Company mitesofor
     * @Description:~启用
     */
    public String enable(Integer id) {
        String msg = "";
        try {
            Investigation investigation = this.getObjectById(id);
            investigation.setStatus(1);//启用
            investigation.setGmtModified(LocalDateTime.now());
            investigationMapper.updateById(investigation);
            msg = "success";
        } catch (Exception e) {
            msg = "fail";
        }
        return msg;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:55
     * @Company mitesofor
     * @Description:~停用
     */
    public String stop(Integer id) {
        String msg = "";
        try {
            Investigation investigation = this.getObjectById(id);
            investigation.setStatus(0);//停用
            investigation.setGmtModified(LocalDateTime.now());
            investigationMapper.updateById(investigation);
            msg = "success";
        } catch (Exception e) {
            msg = "fail";
        }
        return msg;
    }
}
