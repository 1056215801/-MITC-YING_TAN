package com.mit.community.module.userservice.controller;

import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ReportProblemInfo;
import com.mit.community.service.ProblemHandleService;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**上报事件的受理
 * @author xq
 * @date 2019/7/06
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/problemHandle")
@Slf4j
@Api(tags = "小区事件处理")
public class ProblemHandleController {
    @Autowired
    private ProblemHandleService problemHandleService;

    @GetMapping("/getProblemCount")
    @ApiOperation(value = "获取小区停车问题个数", notes = "输入参数：")
    public Result getProblemCount(){
        int count = problemHandleService.getProblemCount("停车");
        return Result.success(count);
    }

    @GetMapping("/getProblem")
    @ApiOperation(value = "获取小区停车问题", notes = "输入参数：")
    public Result getProblem(){
        List<ReportProblemInfo> list = problemHandleService.getProblem("停车");
        return Result.success(list);
    }

    /**
     *
     * @param userId
     * @param reportProblemId
     * @param dept
     * @param content
     * @param images
     * @return
     * @throws Exception
     */
    @PostMapping("/handleProblem")
    @ApiOperation(value = "小区事件受理", notes = "输入参数：userId 受理人id，reportProblemId 事件id，dept 部门，content 内容,images 照片")
    public Result handleProblem(Integer userId ,Integer reportProblemId,String dept,String content,MultipartFile[] images) throws Exception{
        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = UploadUtil.upload(image);
                imageUrls.add(imageUrl);
            }
        }
        problemHandleService.save(userId,reportProblemId,dept,content,imageUrls);
        return Result.success("保存成功");
    }

    @GetMapping("/getHandleProblem")
    @ApiOperation(value = "获取以处理的小区停车问题", notes = "输入参数：")
    public Result getHandleProblem(){
        List<HandleProblemInfo> list = problemHandleService.getHandleProblem("停车");
        return Result.success(list);
    }


    @GetMapping("/getDiffProblemCount")
    @ApiOperation(value = "获取问题个数", notes = "输入参数：")
    public Result getDiffProblemCount(String type){
        int count = problemHandleService.getProblemCount(type);
        return Result.success(count);
    }

    @GetMapping("/getDiffProblem")
    @ApiOperation(value = "获取问题", notes = "输入参数：")
    public Result getDiffProblem(String type){
        List<ReportProblemInfo> list = problemHandleService.getProblem(type);
        return Result.success(list);
    }

    @GetMapping("/getDiffHandleProblem")
    @ApiOperation(value = "获取以处理的问题", notes = "输入参数：")
    public Result getDiffHandleProblem(String type){
        List<HandleProblemInfo> list = problemHandleService.getHandleProblem(type);
        return Result.success(list);
    }
}
