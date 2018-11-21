package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeWebConstants;
import com.google.common.collect.Maps;
import com.mit.common.util.DateUtils;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.mapper.AccessControlMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门禁记录业务层
 *
 * @author Mr.Deng
 * @date 2018/11/15 11:55
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class AccessControlService extends ServiceImpl<AccessControlMapper, AccessControl> {
    private final AccessControlMapper accessControlMapper;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    @Autowired
    public AccessControlService(AccessControlMapper accessControlMapper,
                                ClusterCommunityService clusterCommunityService, ZoneService zoneService) {
        this.accessControlMapper = accessControlMapper;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
    }

    /**
     * 添加门禁记录
     *
     * @param accessControl 门禁记录信息
     * @author Mr.Deng
     * @date 11:57 2018/11/15
     */
    public void save(AccessControl accessControl) {
        accessControlMapper.insert(accessControl);
    }

    /***
     * 从dnake接口，分页查询门禁列表。通过小区编码
     * @param communityCode 小区编码
     * @param pageSize 分页大小
     * @param pageNum 当前页
     * @param param 其他参数
     * @author shuyy
     * @date 2018/11/16 16:43
     * @company mitesofor
     */
    public List<AccessControl> listFromDnakeByCommunityCodePage(String communityCode, Integer pageNum,
                                                                Integer pageSize, Map<String, Object> param) {
        String url = "/v1/device/getAccessControlList";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("accountId", DnakeWebConstants.ACCOUNT_ID);
        if (param != null || !param.isEmpty()) {
            map.putAll(param);
        }
        String result = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray accessControlList = (JSONArray) jsonResult.get("accessControlList");
        return JSONObject.parseArray(accessControlList.toJSONString(),
                AccessControl.class);
    }

    /***
     * 获取最新的门禁记录
     * @return com.mit.community.entity.AccessControl
     * @author shuyy
     * @date 2018/11/16 17:03
     * @company mitesofor
     */
    public AccessControl getNewestAccessControlByCommunityCode(String communityCode) {
        RowBounds rowBounds = new RowBounds(0, 1);
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.orderBy("access_time", false);
        wrapper.eq("community_code", communityCode);
        List<AccessControl> list = this.accessControlMapper.selectPage(rowBounds, wrapper);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /***
     * 查询增量门禁数据。（依据是门禁时间）
     * @param clusterCommunityList 社区列表。里面必须包含communityCode
     * @return List<AccessControl>
     * @author shuyy
     * @date 2018/11/16 17:37
     * @company mitesofor
     */
    public List<AccessControl> listIncrementByCommunityCode(List<ClusterCommunity> clusterCommunityList) {
        List<AccessControl> allAccessControlsList = new ArrayList<>();
        clusterCommunityList.forEach(item -> {
            AccessControl newestAccessControl = this.getNewestAccessControlByCommunityCode(item.getCommunityCode());
            HashMap<String, Object> param = Maps.newHashMapWithExpectedSize(5);
            if (newestAccessControl != null) {
                LocalDateTime accessTime = newestAccessControl.getAccessTime();
                // startDate过滤条件查出的结果是包含startDate的记录。所以这里加1秒
                accessTime = accessTime.plusSeconds(1);
                String accessTimeStr = DateUtils.format(accessTime, null);
                param.put("startDate", accessTimeStr);
            }
            int index = 1;
            while (true) {
                List<AccessControl> accessControls = this
                        .listFromDnakeByCommunityCodePage(item.getCommunityCode(),
                                index, 100, param);
                boolean isEnd = false;
                if (accessControls.size() < 100) {
                    isEnd = true;
                }
                String communityName = StringUtils.EMPTY;
                if (!accessControls.isEmpty()) {
                    communityName = clusterCommunityService.getByCommunityCode(item.getCommunityCode()).getCommunityName();
                }
                for (int i = 0; i < accessControls.size(); i++) {
                    AccessControl accessControl = accessControls.get(i);
                    if (StringUtils.isBlank(accessControl.getZoneName())) {
                        accessControls.remove(i);
                        i--;
                    } else {
                        accessControl.setAccessControlId(accessControl.getId());
                        accessControl.setId(null);
                        accessControl.setCommunityCode(item.getCommunityCode());
                        accessControl.setCommunityName(communityName);
                        if (accessControl.getCardNum() == null) {
                            accessControl.setCardNum(StringUtils.EMPTY);
                        }
                        Integer zoneId = zoneService.getByNameAndCommunityCode(accessControl.getZoneName(), item.getCommunityCode()).getZoneId();
                        accessControl.setZoneId(zoneId);
                        accessControl.setGmtCreate(LocalDateTime.now());
                        accessControl.setGmtModified(LocalDateTime.now());
                    }
                    accessControl.setCommunityCode(item.getCommunityCode());
                }
                allAccessControlsList.addAll(accessControls);
                if (isEnd) {
                    break;
                } else {
                    index++;
                }
            }
        });
        return allAccessControlsList;
    }

    /**
     * 通过小区code获取门禁记录信息
     *
     * @param communityCode 小区code
     * @return 门禁记录列表
     * @author Mr.Deng
     * @date 15:29 2018/11/21
     */
    public List<AccessControl> listByCommunityCode(String communityCode) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return accessControlMapper.selectList(wrapper);
    }

    /**
     * 通过一组小区code获取门禁记录信息
     *
     * @param communityCodes 小区code
     * @return 门禁记录信息列表
     * @author Mr.Deng
     * @date 15:31 2018/11/21
     */
    public List<AccessControl> listByCommunityCodes(List<String> communityCodes) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return accessControlMapper.selectList(wrapper);
    }

}
