package com.mit.community.module.instruction.controller;

import com.mit.community.entity.Instruction;
import com.mit.community.service.InstructionService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作指南
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/instruction")
@Slf4j
@Api(tags = "操作指南")
public class InstructionController {

    @Autowired
    private InstructionService instructionService;

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 10:04
     * @company mitesofor
     */
    @GetMapping("/getInstructionById")
    @ApiOperation(value = "获取操作指南，通过id", notes = "传参：id 操作指南id")
    public Result getInstructionById(Integer id) {
        Instruction instruction = instructionService.getDetail(id);
        return Result.success(instruction);
    }
    /**
     *
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/20 10:05
     * @company mitesofor
     */
    @GetMapping("/listInstruction")
    @ApiOperation(value = "查询操作指南列表", notes = "")
    public Result listInstruction() {
        List<Instruction> instructions = instructionService.list();
        return Result.success(instructions);
    }
}
