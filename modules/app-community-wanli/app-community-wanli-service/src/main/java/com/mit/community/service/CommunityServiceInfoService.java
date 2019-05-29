package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.CommunityServiceInfo;
import com.mit.community.entity.CommunityServiceInfoDetail;
import com.mit.community.mapper.CommunityServiceInfoDetailMapper;
import com.mit.community.mapper.CommunityServiceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 社区门诊业务表
 *
 * @author Mr.Deng
 * @date 2018/12/5 11:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class CommunityServiceInfoService {
    @Autowired
    private CommunityServiceInfoMapper communityServiceInfoMapper;
    @Autowired
    private CommunityServiceInfoDetailService communityServiceInfoDetailService;
    @Autowired
    private CommunityServiceInfoDetailMapper communityServiceInfoDetailMapper;

    /**
     * 查询社区门诊信息，通过社区code
     *
     * @param communityCode 小区code
     * @param type          社区服务类型.关联字典code community_service_type 社区服务类型 1、社区门诊2、开锁换锁3、送水到家
     * @return 社区门诊信息列表
     * @author Mr.Deng
     * @date 11:34 2018/12/5
     */
    public List<CommunityServiceInfo> listByCommunityCode(String communityCode, String type) {
        EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("type", type);
        return communityServiceInfoMapper.selectList(wrapper);
    }

    /**
     * 查询社区服务信息
     *
     * @param minLng 最小经度
     * @param maxLng 最大经度
     * @param minLat 最小纬度
     * @param maxLat 最大纬度
     * @param type   社区服务类型.关联字典code community_service_type
     * @return 社区服务信息
     * @author Mr.Deng
     * @date 9:45 2018/12/14
     */
    public List<CommunityServiceInfo> listByCoordinate(double minLng, double maxLng, double minLat, double maxLat, String type) {
        EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
        // longitude>=? and longitude =<? and latitude>=? latitude=<?
        //{minLng, maxLng, minLat, maxLat};
        wrapper.ge("longitude", minLng);
        wrapper.le("longitude", maxLng);
        wrapper.ge("latitude", minLat);
        wrapper.le("latitude", maxLat);
        wrapper.eq("type", type);
        return communityServiceInfoMapper.selectList(wrapper);
    }

    /**
     * 查找附近一千米的服务点
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param type      社区服务类型.关联字典code community_service_type
     * @return 社区服务信息
     * @author Mr.Deng
     * @date 9:25 2018/12/14
     */
    public List<CommunityServiceInfo> findNeighPosition(double longitude, double latitude, String type) {
        //先计算查询点的经纬度范围
        //地球半径千米
        double r = 6371;
        //0.5千米距离
        double dis = 1;
        double dLng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
        //角度转为弧度
        dLng = dLng * 180 / Math.PI;
        double dLat = dis / r;
        dLat = dLat * 180 / Math.PI;
        double minLat = latitude - dLat;
        double maxLat = latitude + dLat;
        double minLng = longitude - dLng;
        double maxLng = longitude + dLng;
//        String hql = "from Property where longitude>=? and longitude =<? and latitude>=? latitude=<? and state=0";
//        Object[] values = {minLng, maxLng, minLat, maxLat};
//        List<CommunityPhoneService> list = find(hql, values);
        return this.listByCoordinate(minLng, maxLng, minLat, maxLat, type);
    }

    /**
     * 保存社区服务
     *
     * @param communityCode 小区code
     * @param name          名
     * @param intro         简介
     * @param businessHours 营业时间
     * @param address       地址
     * @param cellphone     电话
     * @param longitude     经度
     * @param latitude      维度
     * @param image         图片
     * @param type          类型
     * @param creatorUserId 创建人
     * @param detail        详情
     * @author shuyy
     * @date 2018/12/20 16:57
     * @company mitesofor
     */
//    @Transactional(rollbackFor = Exception.class)
//    public void save(String communityCode, String name, String intro, String businessHours, String address, String cellphone,
//                     Double longitude, Double latitude, String image, String type, Integer creatorUserId, String detail, Integer appraise) {
//        CommunityServiceInfo communityServiceInfo = new CommunityServiceInfo(communityCode, name, intro, businessHours, address,
//                cellphone, 0, 0, longitude, latitude, image, type, creatorUserId, null, 1, appraise);
//        communityServiceInfo.setGmtCreate(LocalDateTime.now());
//        communityServiceInfo.setGmtModified(LocalDateTime.now());
//        communityServiceInfoMapper.insert(communityServiceInfo);
//        CommunityServiceInfoDetail communityServiceInfoDetail = new CommunityServiceInfoDetail(communityServiceInfo.getId(), detail);
//        communityServiceInfoDetail.setGmtCreate(LocalDateTime.now());
//        communityServiceInfoDetail.setGmtModified(LocalDateTime.now());
//        communityServiceInfoDetailService.save(communityServiceInfoDetail);
//    }

    /**
     * 更新
     *
     * @param id            id
     * @param name          名
     * @param intro         简介
     * @param businessHours 营业时间
     * @param address       地址
     * @param cellphone     电话
     * @param longitude     经度
     * @param latitude      维度
     * @param image         图片
     * @param creatorUserId 创建人
     * @param detail        详情
     * @author shuyy
     * @date 2018/12/20 17:18
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String name, String intro, String businessHours, String address, String cellphone,
                       Double longitude, Double latitude, String image, Integer creatorUserId, String detail, Integer appraise, Integer status) {

        CommunityServiceInfo communityServiceInfo = new CommunityServiceInfo();
        communityServiceInfo.setId(id);
        if (StringUtils.isNotBlank(name)) {
            communityServiceInfo.setName(name);
        }
        if (StringUtils.isNotBlank(intro)) {
            communityServiceInfo.setIntro(intro);
        }
        if (StringUtils.isNotBlank(businessHours)) {
            communityServiceInfo.setBusinessHours(businessHours);
        }
        if (StringUtils.isNotBlank(address)) {
            communityServiceInfo.setAddress(address);
        }
        if (StringUtils.isNotBlank(cellphone)) {
            communityServiceInfo.setCellphone(cellphone);
        }
        if (longitude != null) {
            communityServiceInfo.setLongitude(longitude);
        }
        if (latitude != null) {
            communityServiceInfo.setLatitude(latitude);
        }
        if (StringUtils.isNotBlank(image)) {
            communityServiceInfo.setIntro(image);
        }
        if (creatorUserId != null) {
            communityServiceInfo.setCreatorUserId(creatorUserId);
        }
        if (appraise != null) {
            communityServiceInfo.setAppraise(appraise);
        }
        if (status != null) {
            communityServiceInfo.setStatus(status);
        }
        if (StringUtils.isNotBlank(detail)) {
            CommunityServiceInfoDetail communityServiceInfoDetail = communityServiceInfoDetailService.getByCommunityServiceInfoId(id);
            communityServiceInfoDetail.setDetail(detail);
            communityServiceInfoDetailService.update(communityServiceInfoDetail);
        }
        communityServiceInfoMapper.updateById(communityServiceInfo);
    }

    /**
     * 分页查询
     *
     * @param communityCode 小区code
     * @param type          类型
     * @param pageNum       当前页
     * @param pageSize      分页大小
     * @return java.util.List<com.mit.community.entity.CommunityServiceInfo>
     * @author shuyy
     * @date 2018/12/20 17:25
     * @company mitesofor
     */
    public Page<CommunityServiceInfo> listPage(String communityCode, String type, String name, Integer status, Integer appraise, Integer pageNum, Integer pageSize) {
        EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();

        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(type)) {
            wrapper.eq("type", type);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("name", name);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (appraise != null) {
            wrapper.eq("appraise", appraise);
        }
        Page<CommunityServiceInfo> page = new Page<>(pageNum, pageSize);
        List<CommunityServiceInfo> communityServiceInfos = communityServiceInfoMapper.selectPage(page, wrapper);
        page.setRecords(communityServiceInfos);
        return page;
    }

    /**
     * 获取社区服务，包括详情，通过id
     *
     * @param id id
     * @return com.mit.community.entity.CommunityServiceInfo
     * @author shuyy
     * @date 2018/12/21 10:51
     * @company mitesofor
     */
    public CommunityServiceInfo getDetailById(Integer id) {
        CommunityServiceInfo communityServiceInfo = communityServiceInfoMapper.selectById(id);
        CommunityServiceInfoDetail communityServiceInfoDetail = communityServiceInfoDetailService.getByCommunityServiceInfoId(id);
        communityServiceInfo.setDetail(communityServiceInfoDetail.getDetail());
        return communityServiceInfo;
    }

    /**
     * 删除
     *
     * @param id 社区服务id
     * @author shuyy
     * @date 2018/12/21 15:28
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        communityServiceInfoMapper.deleteById(id);
        communityServiceInfoDetailService.removeByCommunityServiceId(id);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/16 15:22
     * @Company mitesofor
     * @Description:~分页查询门诊服务
     */
    public Page<CommunityServiceInfo> listPatientByPage(String communityCode, String name, String time, Integer status,
                                                        Integer appraise, Integer pageNum, Integer pageSize, String type) {
        EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("name", name);
        }
        if (StringUtils.isNotBlank(time)) {
            wrapper.like("business_hours", time);
        }
        if (status != null) {
            if (status == 0) {
                wrapper.eq("isdel", 1);
            } else {
                wrapper.eq("status", status);
            }
        }
        if (appraise != null) {
            wrapper.eq("appraise", appraise);
        }
        if (StringUtils.isNotBlank(type)) {
            if (type.equals("outpatient")) {
                wrapper.eq("type", 1);
            }
            if (type.equals("dooropen")) {
                wrapper.eq("type", 2);
            }
            if (type.equals("waterservice")) {
                wrapper.eq("type", 3);
            }
        }
        wrapper.eq("isdel", 0);
        wrapper.orderBy("gmt_create", false);
        Page<CommunityServiceInfo> page = new Page<>(pageNum, pageSize);
        List<CommunityServiceInfo> communityServiceInfos = communityServiceInfoMapper.selectPage(page, wrapper);
        for (CommunityServiceInfo serviceInfo : communityServiceInfos) {
            CommunityServiceInfoDetail detail = communityServiceInfoDetailService.getByCommunityServiceInfoId(serviceInfo.getId());
            if (detail != null) {
                serviceInfo.setDetail(detail.getDetail());
            }
        }
        page.setRecords(communityServiceInfos);
        return page;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/17 10:12
     * @Company mitesofor
     * @Description:~保存门诊服务
     */
    public void savePatientService(String communityCode, Integer id, String name, String intro,
                                   String morning, String afternoon, String portraitFileDomain,
                                   String portraitFileName, String phone, String address, String detail,
                                   Integer appraise, Integer userid, Integer serviceType) {
        if (id == null) {//新增
            String imgUrl = portraitFileDomain + portraitFileName;
            String businessHours = morning + "," + afternoon;
            CommunityServiceInfo serviceInfo = new CommunityServiceInfo(communityCode, name, intro, businessHours, address, phone,
                    null, null, 0, 0, imgUrl, serviceType, userid, null, 1, appraise, 0);
            serviceInfo.setGmtModified(LocalDateTime.now());
            serviceInfo.setGmtCreate(LocalDateTime.now());
            communityServiceInfoMapper.insert(serviceInfo);
            if (StringUtils.isNotBlank(detail)) {
                CommunityServiceInfoDetail serviceInfoDetail = new CommunityServiceInfoDetail(serviceInfo.getId(), detail);
                serviceInfoDetail.setGmtModified(LocalDateTime.now());
                serviceInfoDetail.setGmtCreate(LocalDateTime.now());
                communityServiceInfoDetailService.save(serviceInfoDetail);
            }
        } else {//修改
            String imgUrl = portraitFileDomain + portraitFileName;
            String businessHours = morning + "," + afternoon;
            CommunityServiceInfo serviceInfo = new CommunityServiceInfo(communityCode, name, intro, businessHours, address, phone,
                    null, null, 0, 0, imgUrl, serviceType, userid, null, 1, appraise, 0);
            serviceInfo.setGmtModified(LocalDateTime.now());
            serviceInfo.setId(id);
            communityServiceInfoMapper.updateById(serviceInfo);
            if (StringUtils.isNotBlank(detail)) {
                CommunityServiceInfoDetail serviceInfoDetail = communityServiceInfoDetailService.getByCommunityServiceInfoId(id);
                if (serviceInfoDetail != null) {
                    serviceInfoDetail.setDetail(detail);
                    serviceInfoDetail.setGmtModified(LocalDateTime.now());
                    communityServiceInfoDetailMapper.updateById(serviceInfoDetail);
                } else {
                    CommunityServiceInfoDetail serviceDetail = new CommunityServiceInfoDetail(serviceInfo.getId(), detail);
                    serviceInfoDetail.setGmtModified(LocalDateTime.now());
                    serviceInfoDetail.setGmtCreate(LocalDateTime.now());
                    communityServiceInfoDetailService.save(serviceInfoDetail);
                }
            }
        }
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/17 14:41
     * @Company mitesofor
     * @Description:~假删除
     */
    public String delete(Integer id) {
        String msg = "";
        try {
            EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("id", id);
            List<CommunityServiceInfo> list = communityServiceInfoMapper.selectList(wrapper);
            CommunityServiceInfo serviceInfo = list.get(0);
            serviceInfo.setIsdel(1);
            communityServiceInfoMapper.updateById(serviceInfo);
            msg = "success";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return msg;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/17 14:54
     * @Company mitesofor
     * @Description:~启用门诊信息
     */
    public String enable(Integer id) {
        String msg = "";
        try {
            EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("id", id);
            List<CommunityServiceInfo> list = communityServiceInfoMapper.selectList(wrapper);
            CommunityServiceInfo serviceInfo = list.get(0);
            serviceInfo.setStatus(1);
            communityServiceInfoMapper.updateById(serviceInfo);
            msg = "success";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return msg;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/17 14:54
     * @Company mitesofor
     * @Description:~停用门诊信息
     */
    public String stop(Integer id) {
        String msg = "";
        try {
            EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("id", id);
            List<CommunityServiceInfo> list = communityServiceInfoMapper.selectList(wrapper);
            CommunityServiceInfo serviceInfo = list.get(0);
            serviceInfo.setStatus(2);
            communityServiceInfoMapper.updateById(serviceInfo);
            msg = "success";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return msg;
    }
}
