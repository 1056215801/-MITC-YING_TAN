package com.mit.community.feigin;

import com.mit.community.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 精选活动
 *
 * @author shuyy
 * @date 2018/12/25
 * @company mitesofor
 */
@FeignClient("app-community-wanli")
public interface SelectionActivitiesFeigin {

    @GetMapping("/getBySelectionActivitiesId")
    Result getBySelectionActivitiesId(Integer selectionActivitiesId);

}
