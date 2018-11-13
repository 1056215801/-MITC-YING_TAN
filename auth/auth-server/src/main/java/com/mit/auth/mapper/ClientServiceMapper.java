package com.mit.auth.mapper;

import com.mit.auth.entity.ClientService;
import tk.mybatis.mapper.common.Mapper;

/**
* client service mapper
* @author: shuyy
* @date: 2018/11/6 17:23
* @company mitesofor
*/
public interface ClientServiceMapper extends Mapper<ClientService> {
    /**
    * 删除
    * @param id
    * @return:
    * @author: shuyy
    * @date: 2018/11/6 17:24
    * @company mitesofor
    */
    void deleteByServiceId(int id);
}