package com.mit.auth.mapper;

import com.mit.auth.entity.Client;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
* client mapper
* @author: shuyy
* @date: 2018/11/6 17:22
* @company mitesofor
*/
public interface ClientMapper extends Mapper<Client> {

//    @Select(" SELECT\n" +
//            "        client.CODE\n" +
//            "      FROM\n" +
//            "          auth_client client\n" +
//            "      INNER JOIN auth_client_service gcs ON gcs.client_id = client.id\n" +
//            "    WHERE\n" +
//            "        gcs.service_id = #{serviceId}")
//    @ResultType(String.class)
    /**
     * 查询所有的client
     * @param serviceId 服务id
     * @return:
     * @author: shuyy
     * @date: 2018/11/6 17:22
     * @company mitesofor
     */
    List<String> selectAllowedClient(String serviceId);

    /**
    * 查询client
    * @param clientId
    * @return:
    * @author: shuyy
    * @date: 2018/11/6 17:23
    * @company mitesofor
    */
    List<Client> selectAuthorityServiceInfo(int clientId);
}