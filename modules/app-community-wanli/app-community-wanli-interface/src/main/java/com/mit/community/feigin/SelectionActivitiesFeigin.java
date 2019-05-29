package com.mit.community.feigin;

import com.mit.community.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 精选活动
 *
 * @author shuyy
 * @date 2018/12/25
 * @company mitesofor
 */
@FeignClient("app-community-wanli")
public interface SelectionActivitiesFeigin {
    /**
     * 查询精选活动
     *
     * @param selectionActivitiesId 精选活动id
     * @author Mr.Deng
     * @date 10:35 2019/3/7
     */
    @GetMapping("/api/app/communitywanli/userService/getBySelectionActivitiesId")
    Result getBySelectionActivitiesId(@RequestParam("selectionActivitiesId") Integer selectionActivitiesId);
}
