package com.dnake.enums;

/**
 * 接口参数枚举
 * @author daiwen
 * @date 2017-09-19
 */
public enum InterfaceParamEnum {
    /**
     * 密文
     */
    Cipher("cipher", "密文"),
    /**
     * 签名
     */
    Sig("sig", "签名"),
    /**
     * 验证信息
     */
    Authorization("authorization", "验证信息");

    private String param;
    private String paramDesc;

    InterfaceParamEnum(String status, String desc) {
        this.param = status;
        this.paramDesc = desc;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

}
