package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Instruction;
import com.mit.community.entity.InstructionDetail;
import com.mit.community.mapper.InstructionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作指南
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@Service
public class InstructionService {
    @Autowired
    private InstructionMapper instructionMapper;
    @Autowired
    private InstructionDetailService instructionDetailService;

    /**
     * 保存
     *
     * @param title      标题
     * @param intro      简介
     * @param userCreate 创建人
     * @param detail     详情
     * @author shuyy
     * @date 2018/12/20 9:38
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String title, String intro, Integer userCreate, String detail, Integer status) {
        Instruction instruction = new Instruction(title, intro, userCreate, null, status);
        instruction.setGmtCreate(LocalDateTime.now());
        instruction.setGmtModified(LocalDateTime.now());
        instructionMapper.insert(instruction);
        InstructionDetail instructionDetail = new InstructionDetail(detail, instruction.getId());
        instructionDetail.setGmtCreate(LocalDateTime.now());
        instructionDetail.setGmtModified(LocalDateTime.now());
        instructionDetailService.save(instructionDetail);
    }

    public void update(Integer id, String title, String intro, Integer userCreate, String detail, Integer status) {
        Instruction instruction = new Instruction();
        instruction.setId(id);
        if (StringUtils.isNotBlank(title)) {
            instruction.setTitle(title);
        }
        if (StringUtils.isNotBlank(intro)) {
            instruction.setIntro(intro);
        }
        if (userCreate != null) {
            instruction.setUserCreate(userCreate);
        }
        if (status != null) {
            instruction.setStatus(status);
        }
        instruction.setGmtModified(LocalDateTime.now());
        instructionMapper.updateById(instruction);
        if (StringUtils.isNotBlank(detail)) {
            InstructionDetail instructionDetail = instructionDetailService.getByInstructionId(id);
            instructionDetail.setDetail(detail);
            instructionDetailService.update(instructionDetail);

        }
    }

    /**
     * 获取操作指南，通过id
     *
     * @param id id
     * @return com.mit.community.entity.Instruction
     * @author shuyy
     * @date 2018/12/20 9:40
     * @company mitesofor
     */
    public Instruction getById(Integer id) {
        return instructionMapper.selectById(id);
    }

    /**
     * 获取操作指南，包括详情
     * @param id id
     * @return com.mit.community.entity.Instruction
     * @author shuyy
     * @date 2018/12/20 10:02
     * @company mitesofor
    */
    public Instruction getDetail(Integer id){
        Instruction instruction = this.getById(id);
        InstructionDetail instructionDetail = instructionDetailService.getByInstructionId(id);
        instruction.setDetail(instructionDetail.getDetail());
        return instruction;
    }


    /**
     * 查询操作指南列表
     * @return java.util.List<com.mit.community.entity.Instruction>
     * @author shuyy
     * @date 2018/12/20 9:49
     * @company mitesofor
     */
    public List<Instruction> list() {
        return instructionMapper.selectList(null);
    }

    /**
     * 删除，通过id
     * @param id id
     * @author shuyy
     * @date 2018/12/20 10:09
     * @company mitesofor
    */
    public void removeById(Integer id){
        instructionMapper.deleteById(id);
    }

    public Page<Instruction> listPage(String title, Integer status, LocalDateTime gmtCreateTimeStart, LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize){
        EntityWrapper<Instruction> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(title)) {
            wrapper.eq("title", title);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("gmt_create", gmtCreateTimeEnd);
        }
        Page<Instruction> page = new Page<>(pageNum, pageSize);
        List<Instruction> instructions = instructionMapper.selectPage(page, wrapper);
        page.setRecords(instructions);
        return page;
    }
}
