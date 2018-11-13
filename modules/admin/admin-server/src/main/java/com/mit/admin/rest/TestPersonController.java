package com.mit.admin.rest;

import com.mit.admin.biz.TestPersonBizc;
import com.mit.admin.entity.TestPerson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mit.common.msg.ObjectRestResponse;
import com.mit.common.rest.BaseController;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/test/testPerson")
@Api(value="测试人员：测试分布式事务")
public class TestPersonController extends BaseController<TestPersonBizc, TestPerson>{

	private TestPersonBizc testPersonController;
	
	@RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    @Override
    public ObjectRestResponse<TestPerson> add(@RequestBody TestPerson entity){
		testPersonController.insert(entity);;
        return new ObjectRestResponse<TestPerson>();
    }
}
