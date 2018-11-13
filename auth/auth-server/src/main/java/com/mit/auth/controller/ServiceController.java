package com.mit.auth.controller;

import com.mit.auth.biz.ClientBiz;
import com.mit.auth.entity.Client;
import com.mit.auth.entity.ClientService;
import com.mit.common.msg.ObjectRestResponse;
import com.mit.common.rest.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("service")
public class ServiceController extends BaseController<ClientBiz,Client>{

    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable int id, String clients){
        baseBiz.modifyClientServices(id, clients);
        return new ObjectRestResponse().rel(true);
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<ClientService> getUsers(@PathVariable int id){
        return new ObjectRestResponse<ClientService>().rel(true).data(baseBiz.getClientServices(id));
    }
}
