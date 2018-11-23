package com.mit.community.util;

import com.mit.common.util.DateUtils;
import com.mit.community.entity.IdCardInfo;
import com.mit.community.service.IdCardRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 身份证数据提取工具类
 *
 * @author Mr.Deng
 * @date 2018/11/21 10:05
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Slf4j
@Component
public class IdCardInfoExtractorUtil {

    private final IdCardValidatorUtil idCardValidatorUtil;

    private final IdCardRegionService idCardRegionService;

    @Autowired
    public IdCardInfoExtractorUtil(IdCardValidatorUtil idCardValidatorUtil, IdCardRegionService idCardRegionService) {
        this.idCardValidatorUtil = idCardValidatorUtil;
        this.idCardRegionService = idCardRegionService;
    }

    /**
     * 通过构造方法初始化各个成员属性
     *
     * @param idCard 身份证号
     * @author Mr.Deng
     * @date 16:07 2018/11/20
     */
    public IdCardInfo idCardInfo(String idCard) {
        int idCardLen = 15;
        IdCardInfo idCardInfo = new IdCardInfo();
        try {
            if (idCardValidatorUtil.isValidatedAllIdCard(idCard)) {
                if (idCard.length() == idCardLen) {
                    idCard = idCardValidatorUtil.convertIdCarBy15bit(idCard);
                }
                // 获取省份
                String provinceId = idCard.substring(0, 2);
                String province = idCardRegionService.getByIdCardSum6(provinceId).getAreazone();
                int provinceSize = province.length();
                idCardInfo.setProvince(province);
                //获取城市
                String cityId = idCard.substring(0, 4);
                String city = idCardRegionService.getByIdCardSum6(cityId).getAreazone();
                int citySize = city.length();
                idCardInfo.setCity(city.substring(provinceSize, citySize));
                //获取区域
                String zoneId = idCard.substring(0, 6);
                String zone = idCardRegionService.getByIdCardSum6(zoneId).getAreazone();
                int zoneSize = zone.length();
                idCardInfo.setRegion(zone.substring(provinceSize, zoneSize));
                // 获取性别
                String id17 = idCard.substring(16, 17);
                int two = 2;
                if (Integer.parseInt(id17) % two != 0) {
                    idCardInfo.setGender(0);
                } else {
                    idCardInfo.setGender(1);
                }
                // 获取出生日期
                String birthday = idCard.substring(6, 14);
                LocalDate birthDate = DateUtils.parseStringToLocalDate(birthday, "yyyyMMdd");
                idCardInfo.setBirthday(birthDate);
            }
        } catch (Exception e) {
            log.info("身份证号码错误");
            return idCardInfo;

        }
        return idCardInfo;
    }
}
