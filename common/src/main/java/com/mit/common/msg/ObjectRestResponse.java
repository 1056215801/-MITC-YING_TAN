package com.mit.common.msg;

/**
 * object rest 相应封装类
 * @author shuyy
 * @date 2018/11/7 16:58
 * @company mitesofor
*/
public class ObjectRestResponse<T> extends BaseResponse {

    private T data;
    private boolean rel;

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }


    public ObjectRestResponse rel(boolean rel) {
        this.setRel(rel);
        return this;
    }


    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
