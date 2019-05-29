package com.mit.community.service.alarm.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.mapper.alarm.dao.FireHydrantBlockMapper;
import com.mit.community.model.FireHydrantBlock;
import com.mit.community.model.FireHydrantBlockStatus;
import com.mit.community.service.data.service.FireHydrantBlockStatusService;
import com.mit.community.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 井盖
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class FireHydrantBlockService extends ServiceImpl<FireHydrantBlockMapper, FireHydrantBlock> {

    @Autowired
    private FireHydrantBlockMapper fireHydrantBlockMapper;
    @Autowired
    private FireHydrantBlockStatusService fireHydrantBlockStatusService;

    /**
     * 分页查询
     *
     * @param deviceNum
     * @param warnType
     * @param warnStatus
     * @param gmtWarnStart
     * @param gmtWarnEnd
     * @param pageNum
     * @param pageSize
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.rest.modular.makedata.model.WellLid>
     * @throws
     * @author shuyy
     * @date 2019-01-04 14:50
     * @company mitesofor
     */
    public Page<FireHydrantBlock> listPage(String communityCode, String deviceNum, String devicePlace, Short warnType,
                                           Short warnStatus, LocalDate gmtWarnStart,
                                           LocalDate gmtWarnEnd, Integer pageNum,
                                           Integer pageSize) {

        EntityWrapper<FireHydrantBlock> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(deviceNum)) {
            wrapper.eq("device_num", deviceNum);
        }
        if (StringUtils.isNotBlank(devicePlace)) {
            wrapper.eq("device_place", devicePlace);
        }
        if (null != warnType) {
            wrapper.eq("warn_type", warnType);
        }
        if (null != warnStatus) {
            wrapper.eq("warn_status", warnStatus);
        }
        if (null != gmtWarnStart) {
            wrapper.ge("gmt_warn", gmtWarnStart);
        }
        if (null != gmtWarnEnd) {
            wrapper.le("gmt_warn", gmtWarnEnd);
        }
        wrapper.orderBy("gmt_warn", false);
        Page<FireHydrantBlock> page = new Page<>(pageNum, pageSize);
        List<FireHydrantBlock> fireHydrants = fireHydrantBlockMapper.selectPage(page, wrapper);
        page.setRecords(fireHydrants);
        return page;
    }


    public void insertDataKXWT() {
        String communityCode = "0125caffaae1472b996390e869129cc7";
        String[] deviceNameArray = {"1栋-1单元-1层",
                "1栋-1单元-2层",
                "1栋-1单元-3层",
                "1栋-1单元-4层",
                "1栋-1单元-5层",
                "1栋-1单元-6层",
                "1栋-1单元-7层",
                "1栋-1单元-8层",
                "1栋-1单元-9层",
                "1栋-1单元-10层",
                "1栋-1单元-11层",
                "1栋-1单元-12层",
                "1栋-1单元-13层",
                "1栋-1单元-14层",
                "1栋-1单元-15层",
                "1栋-1单元-16层",
                "1栋-1单元-17层",
                "1栋-2单元-1层",
                "1栋-2单元-2层",
                "1栋-2单元-3层",
                "1栋-2单元-4层",
                "1栋-2单元-5层",
                "1栋-2单元-6层",
                "1栋-2单元-7层",
                "1栋-2单元-8层",
                "1栋-2单元-9层",
                "1栋-2单元-10层",
                "1栋-2单元-11层",
                "1栋-2单元-12层",
                "1栋-2单元-13层",
                "1栋-2单元-14层",
                "1栋-2单元-15层",
                "1栋-2单元-16层",
                "1栋-2单元-17层",
                "2栋-1单元-1层",
                "2栋-1单元-2层",
                "2栋-1单元-3层",
                "2栋-1单元-4层",
                "2栋-1单元-5层",
                "2栋-1单元-6层",
                "2栋-1单元-7层",
                "2栋-1单元-8层",
                "2栋-1单元-9层",
                "2栋-1单元-10层",
                "2栋-1单元-11层",
                "2栋-1单元-12层",
                "2栋-1单元-13层",
                "2栋-1单元-14层",
                "2栋-1单元-15层",
                "2栋-1单元-16层",
                "2栋-1单元-17层",
                "2栋-1单元-18层",
                "2栋-1单元-19层",
                "2栋-1单元-20层",
                "2栋-1单元-21层",
                "2栋-1单元-22层",
                "2栋-1单元-23层",
                "2栋-1单元-24层",
                "2栋-2单元-1层",
                "2栋-2单元-2层",
                "2栋-2单元-3层",
                "2栋-2单元-4层",
                "2栋-2单元-5层",
                "2栋-2单元-6层",
                "2栋-2单元-7层",
                "2栋-2单元-8层",
                "2栋-2单元-9层",
                "2栋-2单元-10层",
                "2栋-2单元-11层",
                "2栋-2单元-12层",
                "2栋-2单元-13层",
                "2栋-2单元-14层",
                "2栋-2单元-15层",
                "2栋-2单元-16层",
                "2栋-2单元-17层",
                "2栋-2单元-18层",
                "2栋-2单元-19层",
                "2栋-2单元-20层",
                "2栋-2单元-21层",
                "2栋-2单元-22层",
                "2栋-2单元-23层",
                "2栋-2单元-24层",
                "3栋-1单元-1层",
                "3栋-1单元-2层",
                "3栋-1单元-3层",
                "3栋-1单元-4层",
                "3栋-1单元-5层",
                "3栋-1单元-6层",
                "3栋-1单元-7层",
                "3栋-1单元-8层",
                "3栋-1单元-9层",
                "3栋-1单元-10层",
                "3栋-1单元-11层",
                "3栋-1单元-12层",
                "3栋-1单元-13层",
                "3栋-1单元-14层",
                "3栋-1单元-15层",
                "3栋-1单元-16层",
                "3栋-1单元-17层",
                "3栋-1单元-18层",
                "3栋-1单元-19层",
                "3栋-1单元-20层",
                "3栋-1单元-21层",
                "3栋-1单元-22层",
                "3栋-1单元-23层",
                "4栋-1单元-1层",
                "4栋-1单元-2层",
                "4栋-1单元-3层",
                "4栋-1单元-4层",
                "4栋-1单元-5层",
                "4栋-1单元-6层",
                "4栋-1单元-7层",
                "4栋-1单元-8层",
                "4栋-1单元-9层",
                "4栋-1单元-10层",
                "4栋-1单元-11层",
                "4栋-1单元-12层",
                "4栋-1单元-13层",
                "4栋-1单元-14层",
                "4栋-1单元-15层",
                "4栋-1单元-16层",
                "4栋-1单元-17层",
                "4栋-2单元-1层",
                "4栋-2单元-2层",
                "4栋-2单元-3层",
                "4栋-2单元-4层",
                "4栋-2单元-5层",
                "4栋-2单元-6层",
                "4栋-2单元-7层",
                "4栋-2单元-8层",
                "4栋-2单元-9层",
                "4栋-2单元-10层",
                "4栋-2单元-11层",
                "4栋-2单元-12层",
                "4栋-2单元-13层",
                "4栋-2单元-14层",
                "4栋-2单元-15层",
                "4栋-2单元-16层",
                "4栋-2单元-17层",
                "5栋-1单元-1层",
                "5栋-1单元-2层",
                "5栋-1单元-3层",
                "5栋-1单元-4层",
                "5栋-1单元-5层",
                "5栋-1单元-6层",
                "5栋-1单元-7层",
                "5栋-1单元-8层",
                "5栋-1单元-9层",
                "5栋-1单元-10层",
                "5栋-1单元-11层",
                "5栋-1单元-12层",
                "5栋-1单元-13层",
                "5栋-1单元-14层",
                "5栋-1单元-15层",
                "5栋-1单元-16层",
                "5栋-1单元-17层",
                "6栋-1单元-1层",
                "6栋-1单元-2层",
                "6栋-1单元-3层",
                "6栋-1单元-4层",
                "6栋-1单元-5层",
                "6栋-1单元-6层",
                "6栋-1单元-7层",
                "6栋-1单元-8层",
                "6栋-1单元-9层",
                "6栋-1单元-10层",
                "6栋-1单元-11层",
                "6栋-1单元-12层",
                "6栋-1单元-13层",
                "6栋-1单元-14层",
                "6栋-1单元-15层",
                "6栋-1单元-16层",
                "6栋-1单元-17层",
                "6栋-2单元-1层",
                "6栋-2单元-2层",
                "6栋-2单元-3层",
                "6栋-2单元-4层",
                "6栋-2单元-5层",
                "6栋-2单元-6层",
                "6栋-2单元-7层",
                "6栋-2单元-8层",
                "6栋-2单元-9层",
                "6栋-2单元-10层",
                "6栋-2单元-11层",
                "6栋-2单元-12层",
                "6栋-2单元-13层",
                "6栋-2单元-14层",
                "6栋-2单元-15层",
                "6栋-2单元-16层",
                "6栋-2单元-17层",
                "7栋-1单元-1层",
                "7栋-1单元-2层",
                "7栋-1单元-3层",
                "7栋-1单元-4层",
                "7栋-1单元-5层",
                "7栋-1单元-6层",
                "7栋-1单元-7层",
                "7栋-1单元-8层",
                "7栋-1单元-9层",
                "7栋-1单元-10层",
                "7栋-1单元-11层",
                "7栋-1单元-12层",
                "7栋-1单元-13层",
                "7栋-1单元-14层",
                "7栋-1单元-15层",
                "7栋-1单元-16层",
                "7栋-2单元-1层",
                "7栋-2单元-2层",
                "7栋-2单元-3层",
                "7栋-2单元-4层",
                "7栋-2单元-5层",
                "7栋-2单元-6层",
                "7栋-2单元-7层",
                "7栋-2单元-8层",
                "7栋-2单元-9层",
                "7栋-2单元-10层",
                "7栋-2单元-11层",
                "7栋-2单元-12层",
                "7栋-2单元-13层",
                "7栋-2单元-14层",
                "7栋-2单元-15层",
                "7栋-2单元-16层",
                "8栋-1单元-1层",
                "8栋-1单元-2层",
                "8栋-1单元-3层",
                "8栋-1单元-4层",
                "8栋-1单元-5层",
                "8栋-1单元-6层",
                "8栋-1单元-7层",
                "8栋-1单元-8层",
                "8栋-1单元-9层",
                "8栋-1单元-10层",
                "8栋-1单元-11层",
                "8栋-1单元-12层",
                "8栋-1单元-13层",
                "8栋-1单元-14层",
                "8栋-1单元-15层",
                "8栋-1单元-16层",
                "8栋-1单元-17层",
                "8栋-2单元-1层",
                "8栋-2单元-2层",
                "8栋-2单元-3层",
                "8栋-2单元-4层",
                "8栋-2单元-5层",
                "8栋-2单元-6层",
                "8栋-2单元-7层",
                "8栋-2单元-8层",
                "8栋-2单元-9层",
                "8栋-2单元-10层",
                "8栋-2单元-11层",
                "8栋-2单元-12层",
                "8栋-2单元-13层",
                "8栋-2单元-14层",
                "8栋-2单元-15层",
                "8栋-2单元-16层",
                "8栋-2单元-17层",
                "9栋-1单元-1层",
                "9栋-1单元-2层",
                "9栋-1单元-3层",
                "9栋-1单元-4层",
                "9栋-1单元-5层",
                "9栋-1单元-6层",
                "9栋-1单元-7层",
                "9栋-1单元-8层",
                "9栋-1单元-9层",
                "9栋-1单元-10层",
                "9栋-1单元-11层",
                "9栋-1单元-12层",
                "9栋-1单元-13层",
                "9栋-1单元-14层",
                "9栋-1单元-15层",
                "9栋-1单元-16层",
                "9栋-1单元-17层",
                "9栋-2单元-1层",
                "9栋-2单元-2层",
                "9栋-2单元-3层",
                "9栋-2单元-4层",
                "9栋-2单元-5层",
                "9栋-2单元-6层",
                "9栋-2单元-7层",
                "9栋-2单元-8层",
                "9栋-2单元-9层",
                "9栋-2单元-10层",
                "9栋-2单元-11层",
                "9栋-2单元-12层",
                "9栋-2单元-13层",
                "9栋-2单元-14层",
                "9栋-2单元-15层",
                "9栋-2单元-16层",
                "9栋-2单元-17层",
                "9栋-3单元-1层",
                "9栋-3单元-2层",
                "9栋-3单元-3层",
                "9栋-3单元-4层",
                "9栋-3单元-5层",
                "9栋-3单元-6层",
                "9栋-3单元-7层",
                "9栋-3单元-8层",
                "9栋-3单元-9层",
                "9栋-3单元-10层",
                "9栋-3单元-11层",
                "9栋-3单元-12层",
                "9栋-3单元-13层",
                "9栋-3单元-14层",
                "9栋-3单元-15层",
                "9栋-3单元-16层",
                "9栋-3单元-17层",
                "10栋-1单元-1层",
                "10栋-1单元-2层",
                "10栋-1单元-3层",
                "10栋-1单元-4层",
                "10栋-1单元-5层",
                "10栋-1单元-6层",
                "10栋-1单元-7层",
                "10栋-1单元-8层",
                "10栋-1单元-9层",
                "10栋-1单元-10层",
                "10栋-1单元-11层",
                "10栋-1单元-12层",
                "10栋-1单元-13层",
                "10栋-1单元-14层",
                "10栋-1单元-15层",
                "10栋-1单元-16层",
                "10栋-1单元-17层",
                "11栋-1单元-1层",
                "11栋-1单元-2层",
                "11栋-1单元-3层",
                "11栋-1单元-4层",
                "11栋-1单元-5层",
                "11栋-1单元-6层",
                "11栋-1单元-7层",
                "11栋-1单元-8层",
                "11栋-1单元-9层",
                "11栋-1单元-10层",
                "11栋-1单元-11层",
                "11栋-1单元-12层",
                "11栋-1单元-13层",
                "11栋-1单元-14层",
                "11栋-1单元-15层",
                "11栋-1单元-16层",
                "11栋-1单元-17层",
                "11栋-2单元-1层",
                "11栋-2单元-2层",
                "11栋-2单元-3层",
                "11栋-2单元-4层",
                "11栋-2单元-5层",
                "11栋-2单元-6层",
                "11栋-2单元-7层",
                "11栋-2单元-8层",
                "11栋-2单元-9层",
                "11栋-2单元-10层",
                "11栋-2单元-11层",
                "11栋-2单元-12层",
                "11栋-2单元-13层",
                "11栋-2单元-14层",
                "11栋-2单元-15层",
                "11栋-2单元-16层",
                "11栋-2单元-17层",
                "1栋旁边",
                "2栋旁边",
                "3栋旁边",
                "4栋旁边",
                "5栋旁边",
                "6栋旁边",
                "7栋旁边",
                "8栋旁边",
                "9栋旁边",
                "10栋旁边",
                "11栋旁边",
                "12栋别墅旁",
                "13栋别墅旁",
                "14栋别墅旁",
                "15栋别墅旁",
                "16栋别墅旁",
                "17栋别墅旁",
                "18栋别墅旁",
                "19栋别墅旁",
                "20栋别墅旁"};

        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 15,
                16, 59, 9);
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 用水、撞倒、渗漏
        Short[] warnTypeArray = {1, 2, 3};
        Short warnType;
        String warnTypeStr;
        String deviceName = "消防栓盖帽感知设备";
        String deviceType = "NB-IoT";
        List<FireHydrantBlock> fireHydrantBlocks = Lists.newArrayListWithExpectedSize(1000);
        List<FireHydrantBlockStatus> fireHydrantBlockStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            int warnNum = RandomUtil.random(1, 4);
            for (int i = 0; i < warnNum; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                int index = RandomUtil.random(1, deviceNameArray.length);
                String devicePlace = deviceNameArray[index];
                String deviceNum = "";
                if (index < 10) {
                    deviceNum = "ytkxwt-gm-00" + index;
                } else if (index < 100) {
                    deviceNum = "ytkxwt-gm-0" + index;
                } else {
                    deviceNum = "ytkxwt-gm-" + index;
                }
                int random = RandomUtil.random(1, 100);
                if (random < 40) {
                    warnType = warnTypeArray[0];
                    warnTypeStr = "用水";
                } else if (random < 58) {
                    warnType = warnTypeArray[1];
                    warnTypeStr = "撞倒";
                } else {
                    warnType = warnTypeArray[2];
                    warnTypeStr = "渗漏";
                }
                String warnContent = "位于【凯翔外滩小区" + devicePlace + "】的消防栓处于【" + warnTypeStr + "】状态，请及时处理！";
                Short warnStatus = 2;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 80) {
                        warnStatus = 2;
                    } else {
                        warnStatus = 1;
                    }
                }
                int hour = RandomUtil.random(6, 24);
                int min = RandomUtil.random(1, 60);
                int second = RandomUtil.random(1, 60);
                LocalDateTime gmtWarn = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                        localDateTime.getDayOfMonth(), hour, min, second);
                FireHydrantBlock fireHydrant = new FireHydrantBlock(communityCode, deviceNum,
                        deviceName, "凯翔外滩小区" + devicePlace, deviceType,
                        warnType, warnContent, warnStatus, gmtWarn);
                fireHydrant.setGmtCreate(LocalDateTime.now());
                fireHydrant.setGmtModified(LocalDateTime.now());
                fireHydrantBlocks.add(fireHydrant);
                FireHydrantBlockStatus fireHydrantBlockStatus = new FireHydrantBlockStatus(communityCode,
                        deviceNum, deviceName,
                        "凯翔外滩小区" + devicePlace,
                        deviceType, warnType, (short)1, gmtWarn);
                fireHydrantBlockStatus.setGmtCreate(LocalDateTime.now());
                fireHydrantBlockStatus.setGmtModified(LocalDateTime.now());
                fireHydrantBlockStatuses.add(fireHydrantBlockStatus);
            }
            localDateTime = localDateTime.plusDays(1);
        }
        if (!fireHydrantBlocks.isEmpty()) {
            this.insertBatch(fireHydrantBlocks);
        }
        if(fireHydrantBlockStatuses.isEmpty()){
            fireHydrantBlockStatusService.insertBatch(fireHydrantBlockStatuses);
        }
    }

    public void insertDataXJB() {
        String communityCode = "b41bf2c76a3a4d5aaece65c15cfc350b";
        String[] deviceNameArray = {"1栋-1单元-1层", "1栋-1单元-2层", "1栋-1单元-3层", "1栋-1单元-4层", "1栋-1单元-5层", "1栋-1单元-6层", "1栋-1单元-7层", "1栋-1单元-8层", "1栋-1单元-9层", "1栋-1单元-10层", "1栋-1单元-11层", "2栋-1单元-1层", "2栋-1单元-2层", "2栋-1单元-3层", "2栋-1单元-4层", "2栋-1单元-5层", "2栋-1单元-6层", "2栋-1单元-7层", "2栋-1单元-8层", "2栋-1单元-9层", "2栋-1单元-10层", "2栋-1单元-11层", "2栋-2单元-1层", "2栋-2单元-2层", "2栋-2单元-3层", "2栋-2单元-4层", "2栋-2单元-5层", "2栋-2单元-6层", "2栋-2单元-7层", "2栋-2单元-8层", "2栋-2单元-9层", "2栋-2单元-10层", "2栋-2单元-11层", "2栋-3单元-1层", "2栋-3单元-2层", "2栋-3单元-3层", "2栋-3单元-4层", "2栋-3单元-5层", "2栋-3单元-6层", "2栋-3单元-7层", "2栋-3单元-8层", "2栋-3单元-9层", "2栋-3单元-10层", "2栋-3单元-11层", "3栋-1单元-1层", "3栋-1单元-2层", "3栋-1单元-3层", "3栋-1单元-4层", "3栋-1单元-5层", "3栋-1单元-6层", "3栋-1单元-7层", "3栋-1单元-8层", "3栋-1单元-9层", "3栋-1单元-10层", "3栋-1单元-11层", "3栋-2单元-1层", "3栋-2单元-2层", "3栋-2单元-3层", "3栋-2单元-4层", "3栋-2单元-5层", "3栋-2单元-6层", "3栋-2单元-7层", "3栋-2单元-8层", "3栋-2单元-9层", "3栋-2单元-10层", "3栋-2单元-11层", "3栋-3单元-1层", "3栋-3单元-2层", "3栋-3单元-3层", "3栋-3单元-4层", "3栋-3单元-5层", "3栋-3单元-6层", "3栋-3单元-7层", "3栋-3单元-8层", "3栋-3单元-9层", "3栋-3单元-10层", "3栋-3单元-11层", "4栋-1单元-1层", "4栋-1单元-2层", "4栋-1单元-3层", "4栋-1单元-4层", "4栋-1单元-5层", "4栋-1单元-6层", "4栋-1单元-7层", "4栋-1单元-8层", "4栋-1单元-9层", "4栋-1单元-10层", "4栋-1单元-11层", "4栋-2单元-1层", "4栋-2单元-2层", "4栋-2单元-3层", "4栋-2单元-4层", "4栋-2单元-5层", "4栋-2单元-6层", "4栋-2单元-7层", "4栋-2单元-8层", "4栋-2单元-9层", "4栋-2单元-10层", "4栋-2单元-11层", "5栋-1单元-1层", "5栋-1单元-2层", "5栋-1单元-3层", "5栋-1单元-4层", "5栋-1单元-5层", "5栋-1单元-6层", "5栋-1单元-7层", "5栋-1单元-8层", "5栋-1单元-9层", "5栋-1单元-10层", "5栋-1单元-11层", "5栋-2单元-1层", "5栋-2单元-2层", "5栋-2单元-3层", "5栋-2单元-4层", "5栋-2单元-5层", "5栋-2单元-6层", "5栋-2单元-7层", "5栋-2单元-8层", "5栋-2单元-9层", "5栋-2单元-10层", "5栋-2单元-11层", "5栋-3单元-1层", "5栋-3单元-2层", "5栋-3单元-3层", "5栋-3单元-4层", "5栋-3单元-5层", "5栋-3单元-6层", "5栋-3单元-7层", "5栋-3单元-8层", "5栋-3单元-9层", "5栋-3单元-10层", "5栋-3单元-11层", "6栋-1单元-1层", "6栋-1单元-2层", "6栋-1单元-3层", "6栋-1单元-4层", "6栋-1单元-5层", "6栋-1单元-6层", "6栋-1单元-7层", "6栋-1单元-8层", "6栋-1单元-9层", "6栋-1单元-10层", "6栋-1单元-11层", "6栋-2单元-1层", "6栋-2单元-2层", "6栋-2单元-3层", "6栋-2单元-4层", "6栋-2单元-5层", "6栋-2单元-6层", "6栋-2单元-7层", "6栋-2单元-8层", "6栋-2单元-9层", "6栋-2单元-10层", "6栋-2单元-11层", "6栋-3单元-1层", "6栋-3单元-2层", "6栋-3单元-3层", "6栋-3单元-4层", "6栋-3单元-5层", "6栋-3单元-6层", "6栋-3单元-7层", "6栋-3单元-8层", "6栋-3单元-9层", "6栋-3单元-10层", "6栋-3单元-11层", "7栋-1单元-1层", "7栋-1单元-2层", "7栋-1单元-3层", "7栋-1单元-4层", "7栋-1单元-5层", "7栋-1单元-6层", "7栋-1单元-7层", "7栋-1单元-8层", "7栋-1单元-9层", "7栋-1单元-10层", "7栋-1单元-11层", "7栋-1单元-12层", "7栋-1单元-13层", "7栋-1单元-14层", "7栋-1单元-15层", "7栋-1单元-16层", "7栋-1单元-17层", "7栋-1单元-18层", "7栋-2单元-1层", "7栋-2单元-2层", "7栋-2单元-3层", "7栋-2单元-4层", "7栋-2单元-5层", "7栋-2单元-6层", "7栋-2单元-7层", "7栋-2单元-8层", "7栋-2单元-9层", "7栋-2单元-10层", "7栋-2单元-11层", "7栋-2单元-12层", "7栋-2单元-13层", "7栋-2单元-14层", "7栋-2单元-15层", "7栋-2单元-16层", "7栋-2单元-17层", "7栋-2单元-18层", "7栋-3单元-1层", "7栋-3单元-2层", "7栋-3单元-3层", "7栋-3单元-4层", "7栋-3单元-5层", "7栋-3单元-6层", "7栋-3单元-7层", "7栋-3单元-8层", "7栋-3单元-9层", "7栋-3单元-10层", "7栋-3单元-11层", "7栋-3单元-12层", "7栋-3单元-13层", "7栋-3单元-14层", "7栋-3单元-15层", "7栋-3单元-16层", "7栋-3单元-17层", "7栋-3单元-18层", "8栋-1单元-1层", "8栋-1单元-2层", "8栋-1单元-3层", "8栋-1单元-4层", "8栋-1单元-5层", "8栋-1单元-6层", "8栋-1单元-7层", "8栋-1单元-8层", "8栋-1单元-9层", "8栋-1单元-10层", "8栋-1单元-11层", "8栋-2单元-1层", "8栋-2单元-2层", "8栋-2单元-3层", "8栋-2单元-4层", "8栋-2单元-5层", "8栋-2单元-6层", "8栋-2单元-7层", "8栋-2单元-8层", "8栋-2单元-9层", "8栋-2单元-10层", "8栋-2单元-11层", "9栋-1单元-1层", "9栋-1单元-2层", "9栋-1单元-3层", "9栋-1单元-4层", "9栋-1单元-5层", "9栋-1单元-6层", "9栋-1单元-7层", "9栋-1单元-8层", "9栋-1单元-9层", "9栋-1单元-10层", "9栋-1单元-11层", "9栋-2单元-1层", "9栋-2单元-2层", "9栋-2单元-3层", "9栋-2单元-4层", "9栋-2单元-5层", "9栋-2单元-6层", "9栋-2单元-7层", "9栋-2单元-8层", "9栋-2单元-9层", "9栋-2单元-10层", "9栋-2单元-11层", "10栋-1单元-1层", "10栋-1单元-2层", "10栋-1单元-3层", "10栋-1单元-4层", "10栋-1单元-5层", "10栋-1单元-6层", "10栋-1单元-7层", "10栋-1单元-8层", "10栋-1单元-9层", "10栋-1单元-10层", "10栋-1单元-11层", "10栋-2单元-1层", "10栋-2单元-2层", "10栋-2单元-3层", "10栋-2单元-4层", "10栋-2单元-5层", "10栋-2单元-6层", "10栋-2单元-7层", "10栋-2单元-8层", "10栋-2单元-9层", "10栋-2单元-10层", "10栋-2单元-11层", "10栋-3单元-1层", "10栋-3单元-2层", "10栋-3单元-3层", "10栋-3单元-4层", "10栋-3单元-5层", "10栋-3单元-6层", "10栋-3单元-7层", "10栋-3单元-8层", "10栋-3单元-9层", "10栋-3单元-10层", "10栋-3单元-11层", "10栋-4单元-1层", "10栋-4单元-2层", "10栋-4单元-3层", "10栋-4单元-4层", "10栋-4单元-5层", "10栋-4单元-6层", "10栋-4单元-7层", "10栋-4单元-8层", "10栋-4单元-9层", "10栋-4单元-10层", "10栋-4单元-11层", "11栋-1单元-1层", "11栋-1单元-2层", "11栋-1单元-3层", "11栋-1单元-4层", "11栋-1单元-5层", "11栋-1单元-6层", "11栋-1单元-7层", "11栋-1单元-8层", "11栋-1单元-9层", "11栋-1单元-10层", "11栋-1单元-11层", "11栋-2单元-1层", "11栋-2单元-2层", "11栋-2单元-3层", "11栋-2单元-4层", "11栋-2单元-5层", "11栋-2单元-6层", "11栋-2单元-7层", "11栋-2单元-8层", "11栋-2单元-9层", "11栋-2单元-10层", "11栋-2单元-11层", "11栋-3单元-1层", "11栋-3单元-2层", "11栋-3单元-3层", "11栋-3单元-4层", "11栋-3单元-5层", "11栋-3单元-6层", "11栋-3单元-7层", "11栋-3单元-8层", "11栋-3单元-9层", "11栋-3单元-10层", "11栋-3单元-11层", "11栋-4单元-1层", "11栋-4单元-2层", "11栋-4单元-3层", "11栋-4单元-4层", "11栋-4单元-5层", "11栋-4单元-6层", "11栋-4单元-7层", "11栋-4单元-8层", "11栋-4单元-9层", "11栋-4单元-10层", "11栋-4单元-11层", "12栋-1单元-1层", "12栋-1单元-2层", "12栋-1单元-3层", "12栋-1单元-4层", "12栋-1单元-5层", "12栋-1单元-6层", "12栋-1单元-7层", "12栋-1单元-8层", "12栋-1单元-9层", "12栋-1单元-10层", "12栋-1单元-11层", "12栋-1单元-12层", "12栋-1单元-13层", "12栋-1单元-14层", "12栋-1单元-15层", "12栋-1单元-16层", "12栋-1单元-17层", "12栋-1单元-18层", "13栋-1单元-1层", "13栋-1单元-2层", "13栋-1单元-3层", "13栋-1单元-4层", "13栋-1单元-5层", "13栋-1单元-6层", "13栋-1单元-7层", "13栋-1单元-8层", "13栋-1单元-9层", "13栋-1单元-10层", "13栋-1单元-11层", "13栋-1单元-12层", "13栋-1单元-13层", "13栋-1单元-14层", "13栋-1单元-15层", "13栋-1单元-16层", "13栋-1单元-17层", "13栋-1单元-18层", "14栋-1单元-1层", "14栋-1单元-2层", "14栋-1单元-3层", "14栋-1单元-4层", "14栋-1单元-5层", "14栋-1单元-6层", "14栋-1单元-7层", "14栋-1单元-8层", "14栋-1单元-9层", "14栋-1单元-10层", "14栋-1单元-11层", "14栋-1单元-12层", "14栋-1单元-13层", "14栋-1单元-14层", "14栋-1单元-15层", "14栋-1单元-16层", "14栋-1单元-17层", "14栋-1单元-18层", "15栋-1单元-1层", "15栋-1单元-2层", "15栋-1单元-3层", "15栋-1单元-4层", "15栋-1单元-5层", "15栋-1单元-6层", "15栋-1单元-7层", "15栋-1单元-8层", "15栋-1单元-9层", "15栋-1单元-10层", "15栋-1单元-11层", "15栋-1单元-12层", "15栋-1单元-13层", "15栋-1单元-14层", "15栋-1单元-15层", "15栋-1单元-16层", "15栋-1单元-17层", "15栋-1单元-18层", "15栋-2单元-1层", "15栋-2单元-2层", "15栋-2单元-3层", "15栋-2单元-4层", "15栋-2单元-5层", "15栋-2单元-6层", "15栋-2单元-7层", "15栋-2单元-8层", "15栋-2单元-9层", "15栋-2单元-10层", "15栋-2单元-11层", "15栋-2单元-12层", "15栋-2单元-13层", "15栋-2单元-14层", "15栋-2单元-15层", "15栋-2单元-16层", "15栋-2单元-17层", "15栋-2单元-18层", "15栋-3单元-1层", "15栋-3单元-2层", "15栋-3单元-3层", "15栋-3单元-4层", "15栋-3单元-5层", "15栋-3单元-6层", "15栋-3单元-7层", "15栋-3单元-8层", "15栋-3单元-9层", "15栋-3单元-10层", "15栋-3单元-11层", "15栋-3单元-12层", "15栋-3单元-13层", "15栋-3单元-14层", "15栋-3单元-15层", "15栋-3单元-16层", "15栋-3单元-17层", "15栋-3单元-18层", "16栋-1单元-1层", "16栋-1单元-2层", "16栋-1单元-3层", "16栋-1单元-4层", "16栋-1单元-5层", "16栋-1单元-6层", "16栋-1单元-7层", "16栋-1单元-8层", "16栋-1单元-9层", "16栋-1单元-10层", "16栋-1单元-11层", "16栋-2单元-1层", "16栋-2单元-2层", "16栋-2单元-3层", "16栋-2单元-4层", "16栋-2单元-5层", "16栋-2单元-6层", "16栋-2单元-7层", "16栋-2单元-8层", "16栋-2单元-9层", "16栋-2单元-10层", "16栋-2单元-11层", "17栋-1单元-1层", "17栋-1单元-2层", "17栋-1单元-3层", "17栋-1单元-4层", "17栋-1单元-5层", "17栋-1单元-6层", "17栋-1单元-7层", "17栋-1单元-8层", "17栋-1单元-9层", "17栋-1单元-10层", "17栋-1单元-11层", "17栋-2单元-1层", "17栋-2单元-2层", "17栋-2单元-3层", "17栋-2单元-4层", "17栋-2单元-5层", "17栋-2单元-6层", "17栋-2单元-7层", "17栋-2单元-8层", "17栋-2单元-9层", "17栋-2单元-10层", "17栋-2单元-11层", "1栋旁边", "2栋旁边-1", "2栋旁边-2", "2栋旁边-3", "3栋旁边-1", "3栋旁边-2", "3栋旁边-3", "4栋旁边-1", "4栋旁边-2", "5栋旁边-1", "5栋旁边-2", "5栋旁边-3", "6栋旁边-1", "6栋旁边-2", "6栋旁边-3", "7栋旁边-1", "7栋旁边-2", "7栋旁边-3", "8栋旁边-1", "8栋旁边-2", "9栋旁边-1", "9栋旁边-2", "10栋旁边-1", "10栋旁边-2", "10栋旁边-3", "10栋旁边-4", "11栋旁边-1", "11栋旁边-2", "11栋旁边-3", "11栋旁边-4", "12栋旁边", "13栋旁边", "14栋旁边", "15栋旁边-1", "15栋旁边-2", "15栋旁边-3", "16栋旁边-1", "16栋旁边-2", "17栋旁边-1", "17栋旁边-2"};
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 15,
                16, 59, 9);
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 用水、撞倒、渗漏
        Short[] warnTypeArray = {1, 2, 3};
        Short warnType;
        String warnTypeStr;
        String deviceName = "消防栓盖帽感知设备";
        String deviceType = "NB-IoT";
        List<FireHydrantBlock> fireHydrantBlocks = Lists.newArrayListWithExpectedSize(1000);
        List<FireHydrantBlockStatus> fireHydrantBlockStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            int warnNum = RandomUtil.random(1, 4);
            for (int i = 0; i < warnNum; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                int index = RandomUtil.random(1, deviceNameArray.length);
                String devicePlace = deviceNameArray[index];
                String deviceNum = "";
                if (index < 10) {
                    deviceNum = "ytxjb-gm-00" + index;
                } else if (index < 100) {
                    deviceNum = "ytxjb-gm-0" + index;
                } else {
                    deviceNum = "ytxjb-gm-" + index;
                }
                int random = RandomUtil.random(1, 100);
                if (random < 40) {
                    warnType = warnTypeArray[0];
                    warnTypeStr = "用水";
                } else if (random < 58) {
                    warnType = warnTypeArray[1];
                    warnTypeStr = "撞倒";
                } else {
                    warnType = warnTypeArray[2];
                    warnTypeStr = "渗漏";
                }
                String warnContent = "位于【心家泊小区" + devicePlace + "】的消防栓处于【" + warnTypeStr + "】状态，请及时处理！";
                Short warnStatus = 2;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 80) {
                        warnStatus = 2;
                    } else {
                        warnStatus = 1;
                    }
                }
                int hour = RandomUtil.random(6, 24);
                int min = RandomUtil.random(1, 60);
                int second = RandomUtil.random(1, 60);
                LocalDateTime gmtWarn = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                        localDateTime.getDayOfMonth(), hour, min, second);
                FireHydrantBlock fireHydrant = new FireHydrantBlock(communityCode,
                        deviceNum, deviceName, "心家泊小区" + devicePlace, deviceType,
                        warnType, warnContent, warnStatus, gmtWarn);
                fireHydrant.setGmtCreate(LocalDateTime.now());
                fireHydrant.setGmtModified(LocalDateTime.now());
                fireHydrantBlocks.add(fireHydrant);
                FireHydrantBlockStatus fireHydrantBlockStatus = new FireHydrantBlockStatus(communityCode,
                        deviceNum, deviceName,
                        "心家泊小区" + devicePlace,
                        deviceType, warnType, (short)1, gmtWarn);
                fireHydrantBlockStatus.setGmtCreate(LocalDateTime.now());
                fireHydrantBlockStatus.setGmtModified(LocalDateTime.now());
                fireHydrantBlockStatuses.add(fireHydrantBlockStatus);
            }
            localDateTime = localDateTime.plusDays(1);
        }
        if (!fireHydrantBlocks.isEmpty()) {
            this.insertBatch(fireHydrantBlocks);
        }
        if(fireHydrantBlockStatuses.isEmpty()){
            fireHydrantBlockStatusService.insertBatch(fireHydrantBlockStatuses);
        }
    }

    public void insertDataYWHDHY() {
        String communityCode = "2c58fbed7bce49778da3b1717241df25";
        String[] deviceNameArray = {"33号1栋-1单元-1层", "33号1栋-1单元-2层", "33号1栋-1单元-3层", "33号1栋-1单元-4层", "33号1栋-1单元-5层", "33号1栋-1单元-6层", "33号1栋-1单元-7层", "33号1栋-2单元-1层", "33号1栋-2单元-2层", "33号1栋-2单元-3层", "33号1栋-2单元-4层", "33号1栋-2单元-5层", "33号1栋-2单元-6层", "33号1栋-2单元-7层", "33号2栋-1单元-1层", "33号2栋-1单元-2层", "33号2栋-1单元-3层", "33号2栋-1单元-4层", "33号2栋-1单元-5层", "33号2栋-1单元-6层", "33号2栋-2单元-1层", "33号2栋-2单元-2层", "33号2栋-2单元-3层", "33号2栋-2单元-4层", "33号2栋-2单元-5层", "33号2栋-2单元-6层", "33号2栋-3单元-1层", "33号2栋-3单元-2层", "33号2栋-3单元-3层", "33号2栋-3单元-4层", "33号2栋-3单元-5层", "33号2栋-3单元-6层", "33号3栋-1单元-1层", "33号3栋-1单元-2层", "33号3栋-1单元-3层", "33号3栋-1单元-4层", "33号3栋-1单元-5层", "33号3栋-1单元-6层", "33号3栋-2单元-1层", "33号3栋-2单元-2层", "33号3栋-2单元-3层", "33号3栋-2单元-4层", "33号3栋-2单元-5层", "33号3栋-2单元-6层", "33号3栋-3单元-1层", "33号3栋-3单元-2层", "33号3栋-3单元-3层", "33号3栋-3单元-4层", "33号3栋-3单元-5层", "33号3栋-3单元-6层", "33号4栋-1单元-1层", "33号4栋-1单元-2层", "33号4栋-1单元-3层", "33号4栋-1单元-4层", "33号4栋-1单元-5层", "33号4栋-2单元-1层", "33号4栋-2单元-2层", "33号4栋-2单元-3层", "33号4栋-2单元-4层", "33号4栋-2单元-5层", "33号4栋-3单元-1层", "33号4栋-3单元-2层", "33号4栋-3单元-3层", "33号4栋-3单元-4层", "33号4栋-3单元-5层", "35号1栋-1单元-1层", "35号1栋-1单元-2层", "35号1栋-1单元-3层", "35号1栋-1单元-4层", "35号1栋-1单元-5层", "35号1栋-1单元-6层", "35号1栋-1单元-7层", "35号1栋-2单元-1层", "35号1栋-2单元-2层", "35号1栋-2单元-3层", "35号1栋-2单元-4层", "35号1栋-2单元-5层", "35号1栋-2单元-6层", "35号1栋-2单元-7层", "35号1栋-3单元-1层", "35号1栋-3单元-2层", "35号1栋-3单元-3层", "35号1栋-3单元-4层", "35号1栋-3单元-5层", "35号1栋-3单元-6层", "35号1栋-3单元-7层", "35号2栋-1单元-1层", "35号2栋-1单元-2层", "35号2栋-1单元-3层", "35号2栋-1单元-4层", "35号2栋-1单元-5层", "35号2栋-1单元-6层", "35号2栋-2单元-1层", "35号2栋-2单元-2层", "35号2栋-2单元-3层", "35号2栋-2单元-4层", "35号2栋-2单元-5层", "35号2栋-2单元-6层", "35号2栋-3单元-1层", "35号2栋-3单元-2层", "35号2栋-3单元-3层", "35号2栋-3单元-4层", "35号2栋-3单元-5层", "35号2栋-3单元-6层", "35号3栋-1单元-1层", "35号3栋-1单元-2层", "35号3栋-1单元-3层", "35号3栋-1单元-4层", "35号3栋-1单元-5层", "35号3栋-1单元-6层", "35号3栋-2单元-1层", "35号3栋-2单元-2层", "35号3栋-2单元-3层", "35号3栋-2单元-4层", "35号3栋-2单元-5层", "35号3栋-2单元-6层", "35号3栋-3单元-1层", "35号3栋-3单元-2层", "35号3栋-3单元-3层", "35号3栋-3单元-4层", "35号3栋-3单元-5层", "35号3栋-3单元-6层", "35号4栋-1单元-1层", "35号4栋-1单元-2层", "35号4栋-1单元-3层", "35号4栋-1单元-4层", "35号4栋-1单元-5层", "35号4栋-1单元-6层", "35号4栋-2单元-1层", "35号4栋-2单元-2层", "35号4栋-2单元-3层", "35号4栋-2单元-4层", "35号4栋-2单元-5层", "35号4栋-2单元-6层", "33号1栋旁边-1", "33号1栋旁边-2", "33号2栋旁边-1", "33号2栋旁边-2", "33号2栋旁边-3", "33号3栋旁边-1", "33号3栋旁边-2", "33号3栋旁边-3", "33号4栋旁边-1", "33号4栋旁边-2", "33号4栋旁边-3", "35号1栋旁边-1", "35号1栋旁边-2", "35号1栋旁边-3", "35号2栋旁边-1", "35号2栋旁边-2", "35号2栋旁边-3", "35号3栋旁边-1", "35号3栋旁边-2", "35号3栋旁边-3", "33号4栋旁边-1", "33号4栋旁边-2"};
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 15,
                16, 59, 9);
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 用水、撞倒、渗漏
        Short[] warnTypeArray = {1, 2, 3};
        Short warnType;
        String warnTypeStr;
        String deviceName = "消防栓盖帽感知设备";
        String deviceType = "NB-IoT";
        List<FireHydrantBlock> fireHydrantBlocks = Lists.newArrayListWithExpectedSize(1000);
        List<FireHydrantBlockStatus> fireHydrantBlockStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            int warnNum = RandomUtil.random(1, 2);
            for (int i = 0; i < warnNum; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                int index = RandomUtil.random(1, deviceNameArray.length);
                String devicePlace = deviceNameArray[index];
                String deviceNum;
                if (index < 10) {
                    deviceNum = "ytywhdhy-gm-00" + index;
                } else if (index < 100) {
                    deviceNum = "ytywhdhy-gm-0" + index;
                } else {
                    deviceNum = "ytywhdhy-gm-" + index;
                }
                int random = RandomUtil.random(1, 100);
                if (random < 40) {
                    warnType = warnTypeArray[0];
                    warnTypeStr = "用水";
                } else if (random < 58) {
                    warnType = warnTypeArray[1];
                    warnTypeStr = "撞倒";
                } else {
                    warnType = warnTypeArray[2];
                    warnTypeStr = "渗漏";
                }
                String warnContent = "位于【鹰王环东花苑小区" + devicePlace + "】的消防栓处于【" + warnTypeStr + "】状态，请及时处理！";
                Short warnStatus = 2;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 80) {
                        warnStatus = 2;
                    } else {
                        warnStatus = 1;
                    }
                }
                int hour = RandomUtil.random(6, 24);
                int min = RandomUtil.random(1, 60);
                int second = RandomUtil.random(1, 60);
                LocalDateTime gmtWarn = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                        localDateTime.getDayOfMonth(), hour, min, second);
                FireHydrantBlock fireHydrant = new FireHydrantBlock(communityCode,
                        deviceNum, deviceName, "鹰王环东花苑小区" + devicePlace, deviceType,
                        warnType, warnContent, warnStatus, gmtWarn);
                fireHydrant.setGmtCreate(LocalDateTime.now());
                fireHydrant.setGmtModified(LocalDateTime.now());
                fireHydrantBlocks.add(fireHydrant);
                FireHydrantBlockStatus fireHydrantBlockStatus = new FireHydrantBlockStatus(communityCode,
                        deviceNum, deviceName,
                        "鹰王环东花苑小区" + devicePlace,
                        deviceType, warnType, (short)1, gmtWarn);
                fireHydrantBlockStatus.setGmtCreate(LocalDateTime.now());
                fireHydrantBlockStatus.setGmtModified(LocalDateTime.now());
                fireHydrantBlockStatuses.add(fireHydrantBlockStatus);
            }
            localDateTime = localDateTime.plusDays(1);
        }
        if (!fireHydrantBlocks.isEmpty()) {
            this.insertBatch(fireHydrantBlocks);
        }
        if(fireHydrantBlockStatuses.isEmpty()){
            fireHydrantBlockStatusService.insertBatch(fireHydrantBlockStatuses);
        }
    }

    public void insertDataNY() {
        String communityCode = "047cd4ab796a419a80a4d362b9da1c8f";
        String[] deviceNameArray = {"1栋-1单元-1层", "1栋-1单元-2层", "1栋-1单元-3层", "1栋-1单元-4层", "1栋-1单元-5层", "1栋-1单元-6层", "1栋-2单元-1层", "1栋-2单元-2层", "1栋-2单元-3层", "1栋-2单元-4层", "1栋-2单元-5层", "1栋-2单元-6层", "1栋-3单元-1层", "1栋-3单元-2层", "1栋-3单元-3层", "1栋-3单元-4层", "1栋-3单元-5层", "1栋-3单元-6层", "3栋-1单元-1层", "3栋-1单元-2层", "3栋-1单元-3层", "3栋-1单元-4层", "3栋-1单元-5层", "3栋-1单元-6层", "3栋-1单元-7层", "3栋-1单元-8层", "3栋-1单元-9层", "3栋-1单元-10层", "3栋-1单元-11层", "3栋-1单元-12层", "3栋-1单元-13层", "3栋-1单元-14层", "3栋-1单元-15层", "3栋-1单元-16层", "3栋-1单元-17层", "3栋-1单元-18层", "4栋-1单元-1层", "4栋-1单元-2层", "4栋-1单元-3层", "4栋-1单元-4层", "4栋-1单元-5层", "4栋-1单元-6层", "4栋-1单元-7层", "4栋-1单元-8层", "4栋-1单元-9层", "4栋-1单元-10层", "4栋-1单元-11层", "4栋-1单元-12层", "4栋-1单元-13层", "4栋-1单元-14层", "4栋-1单元-15层", "4栋-1单元-16层", "4栋-1单元-17层", "4栋-1单元-18层", "4栋-1单元-19层", "4栋-1单元-20层", "4栋-1单元-21层", "4栋-1单元-22层", "4栋-1单元-23层", "4栋-1单元-24层", "4栋-1单元-25层", "4栋-1单元-26层", "4栋-1单元-27层", "4栋-2单元-1层", "4栋-2单元-2层", "4栋-2单元-3层", "4栋-2单元-4层", "4栋-2单元-5层", "4栋-2单元-6层", "4栋-2单元-7层", "4栋-2单元-8层", "4栋-2单元-9层", "4栋-2单元-10层", "4栋-2单元-11层", "4栋-2单元-12层", "4栋-2单元-13层", "4栋-2单元-14层", "4栋-2单元-15层", "4栋-2单元-16层", "4栋-2单元-17层", "4栋-2单元-18层", "4栋-2单元-19层", "4栋-2单元-20层", "4栋-2单元-21层", "4栋-2单元-22层", "4栋-2单元-23层", "4栋-2单元-24层", "4栋-2单元-25层", "4栋-2单元-26层", "4栋-2单元-27层", "5栋-1单元-1层", "5栋-1单元-2层", "5栋-1单元-3层", "5栋-1单元-4层", "5栋-1单元-5层", "5栋-1单元-6层", "5栋-1单元-7层", "5栋-1单元-8层", "5栋-1单元-9层", "5栋-1单元-10层", "5栋-1单元-11层", "5栋-1单元-12层", "5栋-1单元-13层", "5栋-1单元-14层", "5栋-1单元-15层", "5栋-1单元-16层", "5栋-1单元-17层", "5栋-1单元-18层", "5栋-1单元-19层", "5栋-1单元-20层", "5栋-1单元-21层", "5栋-1单元-22层", "5栋-1单元-23层", "5栋-2单元-1层", "5栋-2单元-2层", "5栋-2单元-3层", "5栋-2单元-4层", "5栋-2单元-5层", "5栋-2单元-6层", "5栋-2单元-7层", "5栋-2单元-8层", "5栋-2单元-9层", "5栋-2单元-10层", "5栋-2单元-11层", "5栋-2单元-12层", "5栋-2单元-13层", "5栋-2单元-14层", "5栋-2单元-15层", "5栋-2单元-16层", "5栋-2单元-17层", "5栋-2单元-18层", "5栋-2单元-19层", "5栋-2单元-20层", "5栋-2单元-21层", "5栋-2单元-22层", "5栋-2单元-23层", "6栋-1单元-1层", "6栋-1单元-2层", "6栋-1单元-3层", "6栋-1单元-4层", "6栋-1单元-5层", "6栋-1单元-6层", "6栋-1单元-7层", "6栋-1单元-8层", "6栋-1单元-9层", "6栋-1单元-10层", "6栋-1单元-11层", "6栋-1单元-12层", "6栋-1单元-13层", "6栋-1单元-14层", "6栋-1单元-15层", "6栋-1单元-16层", "6栋-1单元-17层", "6栋-1单元-18层", "6栋-1单元-19层", "6栋-1单元-20层", "6栋-1单元-21层", "6栋-1单元-22层", "6栋-1单元-23层", "6栋-1单元-24层", "1栋旁边-1", "1栋旁边-2", "1栋旁边-3", "3栋旁边", "4栋旁边-1", "4栋旁边-2", "5栋旁边-1", "5栋旁边-2", "6栋旁边"};
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 15,
                16, 59, 9);
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 用水、撞倒、渗漏
        Short[] warnTypeArray = {1, 2, 3};
        Short warnType;
        String warnTypeStr;
        String deviceName = "消防栓盖帽感知设备";
        String deviceType = "NB-IoT";
        List<FireHydrantBlock> fireHydrantBlocks = Lists.newArrayListWithExpectedSize(1000);
        List<FireHydrantBlockStatus> fireHydrantBlockStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            int warnNum = RandomUtil.random(1, 2);
            for (int i = 0; i < warnNum; i++) {
                int index = RandomUtil.random(1, deviceNameArray.length);
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[index];
                String deviceNum;
                if (index < 10) {
                    deviceNum = "ytny-sy-00" + index;
                } else if (index < 100) {
                    deviceNum = "ytny-sy-0" + index;
                } else {
                    deviceNum = "ytny-sy-" + index;
                }
                int random = RandomUtil.random(1, 100);
                if (random < 40) {
                    warnType = warnTypeArray[0];
                    warnTypeStr = "用水";
                } else if (random < 58) {
                    warnType = warnTypeArray[1];
                    warnTypeStr = "撞倒";
                } else {
                    warnType = warnTypeArray[2];
                    warnTypeStr = "渗漏";
                }
                String warnContent = "位于【南苑小区" + devicePlace + "】的消防栓处于【" + warnTypeStr + "】状态，请及时处理！";
                Short warnStatus = 2;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 80) {
                        warnStatus = 2;
                    } else {
                        warnStatus = 1;
                    }
                }
                int hour = RandomUtil.random(6, 24);
                int min = RandomUtil.random(1, 60);
                int second = RandomUtil.random(1, 60);
                LocalDateTime gmtWarn = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                        localDateTime.getDayOfMonth(), hour, min, second);
                FireHydrantBlock fireHydrant = new FireHydrantBlock(communityCode,
                        deviceNum, deviceName, "南苑小区" + devicePlace, deviceType,
                        warnType, warnContent, warnStatus, gmtWarn);
                fireHydrant.setGmtCreate(LocalDateTime.now());
                fireHydrant.setGmtModified(LocalDateTime.now());
                fireHydrantBlocks.add(fireHydrant);
                FireHydrantBlockStatus fireHydrantBlockStatus = new FireHydrantBlockStatus(communityCode,
                        deviceNum, deviceName,
                        "南苑小区" + devicePlace,
                        deviceType, warnType, (short)1, gmtWarn);
                fireHydrantBlockStatus.setGmtCreate(LocalDateTime.now());
                fireHydrantBlockStatus.setGmtModified(LocalDateTime.now());
                fireHydrantBlockStatuses.add(fireHydrantBlockStatus);
            }
            localDateTime = localDateTime.plusDays(1);
        }
        if (!fireHydrantBlocks.isEmpty()) {
            this.insertBatch(fireHydrantBlocks);
        }
        if(fireHydrantBlockStatuses.isEmpty()){
            fireHydrantBlockStatusService.insertBatch(fireHydrantBlockStatuses);
        }
    }
}
