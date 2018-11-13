package com.mit.admin.rest;

import com.mit.admin.biz.GateLogBiz;
import com.mit.admin.entity.GateLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mit.common.rest.BaseController;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:21
 * @company mitesofor
*/
@Controller
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz, GateLog> {
//    @RequestMapping(value = "/page",method = RequestMethod.GET)
//    @ResponseBody
//    public TableResultResponse<GateLog> page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1")int offset, String name){
//        Example example = new Example(GateLog.class);
//        if(StringUtils.isNotBlank(name)) {
//            example.createCriteria().andLike("menu", "%" + name + "%");
//        }
//        int count = baseBiz.selectCountByExample(example);
//        PageHelper.startPage(offset, limit);
//        return new TableResultResponse<GateLog>(count,baseBiz.selectByExample(example));
//    }
}
