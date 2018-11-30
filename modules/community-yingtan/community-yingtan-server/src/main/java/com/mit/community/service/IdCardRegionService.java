package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.IdCardRegion;
import com.mit.community.module.pass.mapper.IdCardRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 身份证地区业务层
 *
 * @author Mr.Deng
 * @date 2018/11/21 10:54
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class IdCardRegionService {

    private final IdCardRegionMapper idCardRegionMapper;

    @Autowired
    public IdCardRegionService(IdCardRegionMapper idCardRegionMapper) {
        this.idCardRegionMapper = idCardRegionMapper;
    }

    public IdCardRegion getByIdCardSum6(String idCardSum6) {
        EntityWrapper<IdCardRegion> wrapper = new EntityWrapper<>();
        int idCardLen = idCardSum6.length();
        if (idCardLen == 2) {
            wrapper.eq("province", idCardSum6);
        }
        if (idCardLen == 4) {
            wrapper.eq("city", idCardSum6);
        }
        if (idCardLen == 6) {
            wrapper.eq("zone", idCardSum6);
        }
        return idCardRegionMapper.selectList(wrapper).get(0);
    }
}
