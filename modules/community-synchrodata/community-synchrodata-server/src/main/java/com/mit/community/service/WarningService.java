package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.common.HttpLogin;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.Warning;
import com.mit.community.entity.Zone;
import com.mit.community.mapper.WarningMapper;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private SysUserService sysUserService;

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

    /**
     * 从dnake平台查询所有预警
     * @return java.util.List<com.mit.community.entity.Warning>
     * @author shuyy
     * @date 2018/11/30 16:26
     * @company mitesofor
    */
    public List<Warning> listFromDnake(){
        List<SysUser> sysUsers = sysUserService.list();
        final ArrayList<Warning> result = Lists.newArrayListWithCapacity(1000);
        sysUsers.forEach(sysUser -> {
            this.listFromDnakePage(sysUser, 1, result);
        });
        return result;
    }
    /**
     * 从dnake平台分页查询预警
     * @param sysUser 用户
     * @param pageNum 当前页
     * @param result 结果集合
     * @author shuyy
     * @date 2018/11/30 16:25
     * @company mitesofor
    */
    private void listFromDnakePage(SysUser sysUser, Integer pageNum, List<Warning> result){
        HttpLogin httpLogin = new HttpLogin(sysUser.getUsername(), sysUser.getPassword());
        httpLogin.loginUser();
        String url = "http://cmp.ishanghome.com/cmp/deviceAlarm/load";
        NameValuePair[] data = {new NameValuePair("page", pageNum + ""),
                new NameValuePair("list", "100")};
        String post = httpLogin.post(url, data, httpLogin.getCookie());
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        List<Warning> warningList = JSON.parseArray(jsonArray.toString(), Warning.class);
        warningList.forEach(warning -> {
            warning.setGmtCreate(LocalDateTime.now());
            warning.setGmtModified(LocalDateTime.now());
            warning.setCommunityCode(sysUser.getCommunityCode());
        });
        result.addAll(warningList);
        int s = jsonObject.getInteger("lastPage");
        if(s > pageNum){
            this.listFromDnakePage(sysUser, ++pageNum, result);
        }
    }

}
