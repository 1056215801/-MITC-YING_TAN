package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AgeConstruction;
import com.mit.community.mapper.AgeConstructionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 年龄结构
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@Service
public class AgeConstructionService extends ServiceImpl<AgeConstructionMapper, AgeConstruction> {

    private final AgeConstructionMapper ageConstructionMapper;

    @Autowired
    public AgeConstructionService(AgeConstructionMapper ageConstructionMapper) {
        this.ageConstructionMapper = ageConstructionMapper;
    }

    /**
     * 查询年龄结构，通过小区code
     *
     * @param communityCode 小区code
     * @return com.mit.community.entity.AgeConstruction
     * @author shuyy
     * @date 2018/11/23 9:52
     * @company mitesofor
     */
    public AgeConstruction getByCommunityCode(String communityCode) {
        EntityWrapper<AgeConstruction> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return ageConstructionMapper.selectList(wrapper).get(0);
    }

    /***
     * 查询住户年龄结构，通过小区code列表
     * @param communityCodeList 小区code列表
     * @return com.mit.community.entity.AgeConstruction
     * @author shuyy
     * @date 2018/11/23 10:04
     * @company mitesofor
    */
    public AgeConstruction getByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<AgeConstruction> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        List<AgeConstruction> ageConstructions = ageConstructionMapper.selectList(wrapper);
        int childNumSum = 0;
        int middleNumSum = 0;
        int oldNumSum = 0;
        int youngNumSum = 0;
        int youthNumSum = 0;
        for (AgeConstruction ageConstruction : ageConstructions) {
            childNumSum += ageConstruction.getChildNum();
            middleNumSum += ageConstruction.getMiddleNum();
            oldNumSum += ageConstruction.getOldNum();
            youngNumSum += ageConstruction.getYoungNum();
            youthNumSum += ageConstruction.getYouthNum();
        }
        return new AgeConstruction(null, childNumSum, youngNumSum,
                youthNumSum, middleNumSum, oldNumSum);

    }

    /***
     * 删除所有
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/11/23 11:50
     * @company mitesofor
    */
    public void remove(){
        ageConstructionMapper.delete(null);
    }
}
