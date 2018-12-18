package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.mit.community.entity.LostFound;
import com.mit.community.entity.LostFountContent;
import com.mit.community.entity.LostFountReadUser;
import com.mit.community.mapper.LostFoundMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 失物招领业务处理层
 * @author Mr.Deng
 * @date 2018/12/17 20:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class LostFoundService {
    @Autowired
    private LostFoundMapper lostFoundMapper;
    @Autowired
    private LostFountContentService lostFountContentService;
    @Autowired
    private LostFountReadUserService lostFountReadUserService;

    /**
     * 查询所有失物招领简介信息
     * @return 放回失物招领简介信息
     * @author Mr.Deng
     * @date 9:19 2018/12/18
     */
    public List<Map<String, Object>> list(String communityCode) {
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id,title,img_url as imgUrl,receiver_address as receiverAddress,receiver_status as receiverStatus");
        wrapper.eq("community_code", communityCode);
        return lostFoundMapper.selectMaps(wrapper);
    }

    /**
     * 查询失物招领信息 通过失物招领id
     * @param id 失物招领id
     * @return 失物招领信息
     * @author Mr.Deng
     * @date 9:32 2018/12/18
     */
    public Map<String, Object> getById(Integer id) {
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<Map<String, Object>> list = lostFoundMapper.selectMaps(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询失物招领详情信息，通过失物招领id
     * @param id 失物招领id
     * @return 失物招领详情信息
     * @author Mr.Deng
     * @date 9:44 2018/12/18
     */
    public Map<String, Object> getLostFountInfo(Integer id) {
        Map<String, Object> map = this.getById(id);
        LostFountContent lostFountContent = lostFountContentService.listByLostFountId(id);
        String content = StringUtils.EMPTY;
        if (lostFountContent != null) {
            content = lostFountContent.getContent();
        }
        map.put("content", content);
        Integer integer = lostFountReadUserService.countByLostFountId(id);
        map.put("readNum", integer);
        return map;
    }

    /**
     * 查询所有的失物招领信息
     * @param userId 用户id
     * @return 失物招领信息
     * @author Mr.Deng
     * @date 10:02 2018/12/18
     */
    public List<Map<String, Object>> listAll(Integer userId, String communityCode) {
        List<Map<String, Object>> mapList = Lists.newArrayListWithExpectedSize(100);
        List<Map<String, Object>> list = this.list(communityCode);
        if (!list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String id = map.get("id").toString();
                LostFountReadUser lostFountReadUser = lostFountReadUserService.getByUserIdByLostFountId(userId, Integer.parseInt(id));
                if (lostFountReadUser != null) {
                    map.put("readStatus", true);
                } else {
                    map.put("readStatus", false);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

}
