package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.InstructionDetail;
import com.mit.community.mapper.InstructionDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作指南详情
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@Service
public class InstructionDetailService {

    @Autowired
    private InstructionDetailMapper instructionDetailMapper;

    /**
     * 保存
     *
     * @param instructionDetail 操作指南详情
     * @author shuyy
     * @date 2018/12/20 9:46
     * @company mitesofor
     */
    public void save(InstructionDetail instructionDetail) {
        instructionDetailMapper.insert(instructionDetail);
    }

    /**
     * 获取操作指南详情，通过操作指南id
     *
     * @param instructionId 操作指南id
     * @return com.mit.community.entity.InstructionDetail
     * @author shuyy
     * @date 2018/12/20 9:44
     * @company mitesofor
     */
    public InstructionDetail getByInstructionId(Integer instructionId) {
        EntityWrapper<InstructionDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("instruction_id", instructionId);
        List<InstructionDetail> instructionDetails = instructionDetailMapper.selectList(wrapper);
        if (instructionDetails.isEmpty()) {
            return null;
        }
        return instructionDetails.get(0);
    }

    /**
     * 更新
     *
     * @param instructionDetail 操作指南详情
     * @author shuyy
     * @date 2018/12/20 9:47
     * @company mitesofor
     */
    public void update(InstructionDetail instructionDetail) {
        instructionDetailMapper.updateById(instructionDetail);
    }
}
