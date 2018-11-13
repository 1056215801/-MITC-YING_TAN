package com.mit.common.util;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author shuyy
 * @date 2018/11/8 9:21
 * @company mitesofor
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        String page = "page";
        String limit = "limit";
        //分页参数
        if (params.get(page) != null) {
            this.page = Integer.parseInt(params.get(page).toString());
        }
        if (params.get(limit) != null) {
            this.limit = Integer.parseInt(params.get(limit).toString());
        }
        this.remove(page);
        this.remove(limit);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
