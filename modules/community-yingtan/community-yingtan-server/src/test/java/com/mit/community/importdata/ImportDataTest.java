package com.mit.community.importdata;

import com.dnake.common.DnakeWebApiUtil;
import com.dnake.entity.DnakeAppUser;
import org.junit.Test;

import java.util.HashMap;

/**
 * 导入数据测试
 *
 * @author shuyy
 * @date 2018/11/13
 * @company mitesofor
 */
public class ImportDataTest {

    @Test
    public void queryClusterCommunity(){
        String url = "/v1/community/queryClusterCommunity";
        HashMap<String, Object> map = new HashMap<>();
        map.put("clusterAccountId", DnakeAppUser.clusterAccountid);
        DnakeWebApiUtil.invoke(url, map);
    }

}
