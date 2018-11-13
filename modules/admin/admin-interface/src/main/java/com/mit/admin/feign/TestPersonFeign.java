package com.mit.admin.feign;

import com.mit.admin.entity.TestPerson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mit.common.msg.ObjectRestResponse;

//@FeignClient(value = "ace-admin",configuration = FeignConfiguration.class)
//@FeignClient(value = "ace-admin")
public interface TestPersonFeign {
    /**
     * @param entity
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:05 2018/11/9
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    ObjectRestResponse<TestPerson> add(@RequestBody TestPerson entity);
}
