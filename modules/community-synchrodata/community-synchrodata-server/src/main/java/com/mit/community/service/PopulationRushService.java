package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.PopulationRush;
import com.mit.community.mapper.AccessControlMapper;
import com.mit.community.mapper.PopulationRushMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 人流高峰
 *
 * @author shuyy
 * @date 2018/11/23
 * @company mitesofor
 */
@Service
public class PopulationRushService {

    private final PopulationRushMapper populationRushMapper;

    private final AccessControlMapper accessControlMapper;

    @Autowired
    public PopulationRushService(PopulationRushMapper populationRushMapper, AccessControlMapper accessControlMapper) {
        this.populationRushMapper = populationRushMapper;
        this.accessControlMapper = accessControlMapper;
    }

    /***
     * 保存
     * @param populationRush 人流高峰
     * @author shuyy
     * @date 2018/11/23 16:44
     * @company mitesofor
    */
    public void save(PopulationRush populationRush){
        populationRushMapper.insert(populationRush);
    }

    /**
     * 更新，通过小区code
     * @param populationRush 人流高峰
     * @author shuyy
     * @date 2018/11/23 16:52
     * @company mitesofor
    */
    public void updateByCommunityCode( PopulationRush populationRush){
        EntityWrapper<PopulationRush> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", populationRush.getCommunityCode());
        Integer count = populationRushMapper.selectCount(wrapper);
        if(count == 0){
            populationRushMapper.insert(populationRush);
        }else{
            populationRush.setGmtModified(LocalDateTime.now());
            populationRushMapper.update(populationRush, wrapper);
        }
    }

    /***
     * 统计人流高峰，通过小区code
     * @param communityCode 小区code
     * @return com.mit.community.entity.PopulationRush
     * @author shuyy
     * @date 2018/11/23 16:45
     * @company mitesofor
    */
    public PopulationRush countByCommunityCode(String communityCode){
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
//
//        * 下面的代码是统计最近一个月的人流高峰
//        * LocalDateTime now = LocalDateTime.now();
//        LocalDateTime lastMonth = now.minusMonths(1);
//        wrapper.le("access_time", now).ge("access_time", lastMonth);
        List<AccessControl> accessControls = accessControlMapper.selectList(wrapper);
        int mondayNum = 0;
        int tuesdayNum = 0;
        int wednesdayNum = 0;
        int thursdayNum = 0;
        int fridayNum = 0;
        int saturdayNum = 0;
        int sundayNum = 0;
        for (AccessControl accessControl : accessControls) {
            LocalDateTime accessTime = accessControl.getAccessTime();
            DayOfWeek dayOfWeek = accessTime.getDayOfWeek();
            switch (dayOfWeek){
                case MONDAY:
                    mondayNum++;
                    break;
                case TUESDAY:
                    tuesdayNum++;
                    break;
                case WEDNESDAY:
                    wednesdayNum++;
                    break;
                case THURSDAY:
                    thursdayNum++;
                    break;
                case FRIDAY:
                    fridayNum++;
                    break;
                case SATURDAY:
                    saturdayNum++;
                    break;
                default:
                    sundayNum++;
            }
        }
        PopulationRush populationRush = new PopulationRush(communityCode, mondayNum, tuesdayNum, wednesdayNum,
                thursdayNum, fridayNum, saturdayNum, sundayNum);
        populationRush.setGmtCreate(LocalDateTime.now());
        populationRush.setGmtModified(LocalDateTime.now());
        return populationRush;
    }

    /***
     * 查询人流高峰，通过小区code
     * @param communityCode 小区code
     * @return com.mit.community.entity.PopulationRush
     * @author shuyy
     * @date 2018/11/23 17:51
     * @company mitesofor
    */
    public PopulationRush listByCommunityCode(String communityCode){
        EntityWrapper<PopulationRush> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        List<PopulationRush> populationRushes = this.populationRushMapper.selectList(wrapper);
        if(!populationRushes.isEmpty()){
            return populationRushes.get(0);
        }else{
            return null;
        }
    }

    /***
     * 查询人流高峰，通过小区code列表
     * @param communityCodeList 小区code列表
     * @return com.mit.community.entity.PopulationRush
     * @author shuyy
     * @date 2018/11/23 17:51
     * @company mitesofor
     */
    public PopulationRush listByCommunityCodeList(List<String> communityCodeList){
        EntityWrapper<PopulationRush> wrapper = new EntityWrapper<>();
        wrapper.in("communityCode", communityCodeList);
        List<PopulationRush> populationRushes = this.populationRushMapper.selectList(wrapper);
        int monday = 0;
        int tuesday = 0;
        int wednesday = 0;
        int thursday = 0;
        int friday = 0;
        int saturday = 0;
        int sunday = 0;
        for (PopulationRush populationRush : populationRushes) {
            monday += populationRush.getMonday();
            tuesday += populationRush.getTuesday();
            wednesday += populationRush.getWednesday();
            thursday += populationRush.getThursday();
            friday += populationRush.getFriday();
            saturday += populationRush.getSaturday();
            sunday += populationRush.getSunday();
        }
        if(populationRushes.isEmpty()){
            return null;
        }else{
            return new PopulationRush(null, monday, tuesday, wednesday,
                    thursday, friday, saturday, sunday);
        }

    }



}
