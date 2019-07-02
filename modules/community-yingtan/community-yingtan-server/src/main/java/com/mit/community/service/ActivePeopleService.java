package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ActivePeople;
import com.mit.community.module.pass.mapper.ActivePeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活跃人数
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@Service
public class ActivePeopleService {

    private final ActivePeopleMapper activePeopleMapper;

    @Autowired
    public ActivePeopleService(ActivePeopleMapper activePeopleMapper) {
        this.activePeopleMapper = activePeopleMapper;
    }

    /**
     * 保存
     *
     * @param activePeople 活跃人数
     * @author shuyy
     * @date 2018/11/22 11:33
     * @company mitesofor
     */
    public void save(ActivePeople activePeople) {
        this.activePeopleMapper.insert(activePeople);
    }

    /***
     * 删除所有
     * @author shuyy
     * @date 2018/11/22 11:53
     * @company mitesofor
     */
    public void remove() {
        activePeopleMapper.delete(null);
    }

    /***
     * 查询活跃人数， 通过小区code
     * @param communityCode 小区code
     * @return com.mit.community.entity.ActivePeople
     * @author shuyy
     * @date 2018/11/22 14:20
     * @company mitesofor
     */
    public ActivePeople getByCommunityCode(String communityCode) {
        EntityWrapper<ActivePeople> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        List<ActivePeople> list = this.activePeopleMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
