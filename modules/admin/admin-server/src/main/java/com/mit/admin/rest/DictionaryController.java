package com.mit.admin.rest;

import java.util.Date;
import java.util.List;

import com.mit.admin.biz.DictionaryBiz;
import com.mit.admin.entity.Dictionary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mit.common.context.BaseContextHandler;
import com.mit.common.msg.ObjectRestResponse;
import com.mit.common.rest.BaseController;

/**
 * <p>Description:<p>
 *
 * @Author: Mr.Deng
 * @Date: 2018/11/8 11:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@RequestMapping("dictionary")
public class DictionaryController extends BaseController<DictionaryBiz, Dictionary> {

    /**
     * 通过父code获取所有的子字典 @param @param code @param @return @return
     * List<Dictionary> @throws
     */
    @RequestMapping(value = "/selectByCode/{code}", method = RequestMethod.GET)
    @ResponseBody
    public List<Dictionary> selectByCode(@PathVariable String code) {
        List<Dictionary> list = baseBiz.selectByCode(code);
        return list;
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Dictionary> add(@RequestBody Dictionary entity) {
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setHavueChildren(0);
        entity.setUpdateTime(date);
        entity.setCreateTime(date);
        entity.setUpdateUserId(Integer.parseInt(BaseContextHandler.getUserID()));
        entity.setUpdateUserName(BaseContextHandler.getUsername());
        return super.add(entity);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Dictionary> update(@RequestBody Dictionary entity) {
        Date date = new Date();
        entity.setUpdateTime(date);
        entity.setUpdateUserId(Integer.parseInt(BaseContextHandler.getUserID()));
        entity.setUpdateUserName(BaseContextHandler.getUsername());
        return super.update(entity);
    }
}