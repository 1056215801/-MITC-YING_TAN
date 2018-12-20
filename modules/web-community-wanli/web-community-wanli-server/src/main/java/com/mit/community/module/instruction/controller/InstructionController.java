package com.mit.community.module.instruction.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Instruction;
import com.mit.community.entity.SysUser;
import com.mit.community.service.InstructionService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private RedisService redisService;

    /**
     * 保存操作指南
     *
     * @param request
     * @param title   标题
     * @param intro   简介
     * @param detail  详情
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/20 9:56
     * @company mitesofor
     */
    @PostMapping("/saveInstruction")
    @ApiOperation(value = "保存操作指南", notes = "传参：title 操作指南标题， intro 简介， detail 详情")
    public Result saveInstruction(HttpServletRequest request, String title, String intro, String detail) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Integer userId = user.getId();
        instructionService.save(title, intro, userId, detail);
        return Result.success("操作成功");
    }


    /**
     * @param request
     * @param id 操作指南id
     * @param title 操作指南标题
     * @param intro 简介
     * @param detail 详情
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 9:59
     * @company mitesofor
    */
    @PutMapping("/updateInstruction")
    @ApiOperation(value = "修改操作指南", notes = "传参：id 操作指南id， title 操作指南标题，" +
            " intro 简介， detail 详情")
    public Result updateInstruction(HttpServletRequest request,
                                    Integer id, String title, String intro, String detail) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Integer userId = user.getId();
        instructionService.update(id, title, intro, userId, detail);
        return Result.success("操作成功");
    }

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
    @ApiOperation(value = "查询操作指南列表")
    public Result listInstruction() {
        List<Instruction> instructions = instructionService.list();
        return Result.success(instructions);
    }

    /**
     *
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 10:10
     * @company mitesofor
    */
    @DeleteMapping("/removeInstruction")
    @ApiOperation(value = "删除操作指南列表",  notes = "传参：id 操作指南id")
    public Result removeInstruction(Integer id) {
        instructionService.removeById(id);
        return Result.success("操作成功");
    }

}