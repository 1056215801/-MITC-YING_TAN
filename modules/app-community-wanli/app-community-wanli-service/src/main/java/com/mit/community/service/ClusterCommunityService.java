package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.mapper.ClusterCommunityMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 小区
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class ClusterCommunityService extends ServiceImpl<ClusterCommunityMapper,ClusterCommunity> {

    @Autowired
    private ClusterCommunityMapper clusterCommunityMapper;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HouseholdRoomService householdRoomService;


    /**
     * 获取小区， 通过小区code
     * @param communityCode
     * @return com.mit.community.entity.ClusterCommunity
     * @throws
     * @author shuyy
     * @date 2018/12/12 16:46
     * @company mitesofor
    */
    @Cache(key = "community:communityCode:{1}")
    public ClusterCommunity getByCommunityCode(String communityCode){
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        List<ClusterCommunity> clusterCommunities = clusterCommunityMapper.selectList(wrapper);
        if(clusterCommunities.isEmpty()){
            return null;
        }
        return clusterCommunities.get(0);
    }

    /**
     * 查询小区，通过communityCode列表
     *
     * @param communityCodeList 小区code列表
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @author shuyy
     * @date 2018/11/30 11:20
     * @company mitesofor
     */
    public List<ClusterCommunity> listByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        return clusterCommunityMapper.selectList(wrapper);
    }

    /**
     * 查询用户授权的所有小区，通过用户手机号
     * @param userCellphone 用户手机号
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @author shuyy
     * @date 2018/11/30 11:25
     * @company mitesofor
     */
    public List<ClusterCommunity> listClusterCommunityByUserCellphone(String userCellphone) {
        User user = (User) redisService.get(RedisConstant.USER + userCellphone);
//        List<HouseHold> households = houseHoldService.listByUserId(user.getId());
        List<HouseHold> householdList = houseHoldService.getByCellphone(user.getCellphone());
        if (householdList.isEmpty()) {
            return null;
        }
        List<HouseholdRoom> householdRooms = Lists.newArrayListWithCapacity(10);
        householdList.forEach(item -> {
            householdRooms.addAll(householdRoomService.listByHouseholdId(item.getHouseholdId()));
        });
        Set<String> communityCodeList = householdRooms.parallelStream().map(HouseholdRoom::getCommunityCode).collect(Collectors.toSet());
        return clusterCommunityService.listByCommunityCodeList(new ArrayList<>(communityCodeList));
    }

    /**
     * 查询小区列表，通过城市名称
     *
     * @param cityName 城市名
     * @return 小区信息列表
     * @author Mr.Deng
     * @date 11:53 2018/11/21
     */
    public List<ClusterCommunity> listByCityName(String cityName) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.eq("city_name", cityName);
        return clusterCommunityMapper.selectList(wrapper);
    }

    /**
     *  查询所有省份
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author shuyy
     * @date 2018/12/11 14:07
     * @company mitesofor
    */
    public List<Map<String, Object>> listProvince(){
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.groupBy("province_name");
        wrapper.setSqlSelect("province_name");
        return clusterCommunityMapper.selectMaps(wrapper);
    }
    /**
     * 查询所有城市，通过省份
     * @param province 省份
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author shuyy
     * @date 2018/12/11 14:09
     * @company mitesofor
    */
    public List<Map<String, Object>> listCityByProvince(String province){
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.groupBy("city_name");
        wrapper.eq("province_name", province);
        wrapper.setSqlSelect("city_name");
        return clusterCommunityMapper.selectMaps(wrapper);
    }
     public int save(ClusterCommunity clusterCommunity){


         Integer insert = clusterCommunityMapper.insert(clusterCommunity);
         return insert;
     }

    public ClusterCommunity findMax() {
        EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
        wrapper.orderBy("community_id",false);
        wrapper.last("limit 1");
        ClusterCommunity clusterCommunity = this.baseMapper.selectList(wrapper).get(0);
        return clusterCommunity;
    }

    public Page<ClusterCommunity> getCommunityList(String communityName, String communityCode, String username, String provinceName, String cityName, String areaName, String streetName, String communityType, Integer pageNum, Integer pageSize,String committee) {
               Page<ClusterCommunity> page=new Page<>(pageNum,pageSize);
              EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
              if (StringUtils.isNotEmpty(communityName)){
                  wrapper.like("c.community_name",communityName);
              }
              if (StringUtils.isNotEmpty(communityCode)){
                  wrapper.like("c.community_code",communityCode);
              }
              if (StringUtils.isNotEmpty(provinceName)){
                  wrapper.eq("c.province_name",provinceName);
              }
              if (StringUtils.isNotEmpty(cityName)){
                  wrapper.eq("c.city_name",cityName);
              }
              if (StringUtils.isNotEmpty(areaName)){
                  wrapper.eq("c.area_name",areaName);
              }
              if (StringUtils.isNotEmpty(streetName)){
                  wrapper.eq("c.street_name",streetName);
              }
              if (StringUtils.isNotEmpty(committee))
              {
                  wrapper.eq("c.committee",committee);
              }
              if (StringUtils.isNotEmpty(communityType)){
                  wrapper.like("c.community_type",communityType);
              }
              List<ClusterCommunity> clusterCommunityList=clusterCommunityMapper.selectMyPage(page,wrapper,username);
              page.setRecords(clusterCommunityList);
              return page;
    }

    public List<ClusterCommunity> getByCellPhone(String cellPhone) {
        EntityWrapper<ClusterCommunity> wrapperStreet = new EntityWrapper<>();;
        List<String> communityCode = new ArrayList<>();
        User user = userService.getByCellphone(cellPhone);
        ClusterCommunity clusterCommunity = getByCommunityCode(user.getSerialnumber());
        if ("小区账号".equals(user.getFaceToken())) {
            communityCode.add(user.getSerialnumber());
        } else if ("镇/街道账号".equals(user.getFaceToken())) {
            wrapperStreet.eq("street_name", clusterCommunity.getStreetName());
        } else if ("区级账号".equals(user.getFaceToken())) {
            wrapperStreet.eq("area_name", clusterCommunity.getStreetName());
        }
        List<ClusterCommunity> list = clusterCommunityMapper.selectList(wrapperStreet);
        communityCode = list.parallelStream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCode);
        return clusterCommunityMapper.selectList(wrapper);
    }


    /*public List<ClusterCommunity> getByCellPhone(String cellPhone) {
        EntityWrapper<ClusterCommunity> wrapperStreet = new EntityWrapper<>();;
        List<String> communityCode = new ArrayList<>();
        User user = userService.getByCellphone(cellPhone);
        ClusterCommunity clusterCommunity = getByCommunityCode(user.getSerialnumber());
        if ("小区账号".equals(user.getFaceToken())) {
            communityCode.add(user.getSerialnumber());
        } else if ("镇/街道账号".equals(user.getFaceToken())) {
            wrapperStreet.eq("street_name", clusterCommunity.getStreetName());
        } else if ("区级账号".equals(user.getFaceToken())) {
            wrapperStreet.eq("area_name", clusterCommunity.getStreetName());
        }
        List<ClusterCommunity> list = clusterCommunityMapper.selectList(wrapperStreet);
        communityCode = list.parallelStream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCode);
        return clusterCommunityMapper.selectList(wrapper);
    }*/

}
