package com.mit.community.util;

import lombok.Data;

import java.io.Serializable;


/**
 * 返回结果格式包装类
 *
 * @author shuyy
 * @date 2018年9月4日
 */
@Data
public class Result implements Serializable {
    /**
     * 数据
     */
    private Object object;
    /**
     * 提示
     */
    private String message;
    /**
     * 结果状态：成功or失败
     */
    private boolean resultCode;

    public Result() {

    }

    private Result(String message) {
        this.message = message;
        this.resultCode = false;
    }

    private Result(Object data, String message) {
        this.object = data;
        this.message = message;
        this.resultCode = true;
    }

    private Result(Object data) {
        this.object = data;
        this.message = "success";
        this.resultCode = true;
    }

    public static Result error(String message) {
        return new Result(message);
    }

    public static Result success(Object data, String message) {
        return new Result(data, message);
    }

    public static Result success(Object data) {
        return new Result(data);
    }

}
