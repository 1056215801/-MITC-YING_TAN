package com.mit.community.module.list.controller;

import com.mit.community.entity.ListInterfaceData;
import com.mit.community.service.ListService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "获取左侧设备列表")
@RequestMapping(value = "/list")
public class ListController {
    @Autowired
    private ListService listService;

    @RequestMapping("/getDeviceList")
    @ApiOperation(value = "获取设备列表")
    public Result getDeviceList() {
        ListInterfaceData data = listService.getDeviceList();
            return Result.success(data);
        }

        @RequestMapping("/update")
        @ApiOperation(value = "收藏/取消收藏",notes = "参数：id 设备id，isCollect 是否收藏（0不收藏，1收藏）")
        public Result update(Integer id, int isCollect) {
            listService.updateCollect(id, isCollect);
            if (isCollect == 0) {
            return Result.success("取消收藏成功");
        } else {
            return Result.success("收藏成功");
        }
    }



}
