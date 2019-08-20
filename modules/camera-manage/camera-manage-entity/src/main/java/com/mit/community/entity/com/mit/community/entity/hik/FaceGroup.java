package com.mit.community.entity.com.mit.community.entity.hik;

import io.swagger.annotations.ApiModelProperty;

public class FaceGroup {

   // @ApiModelProperty(required = true)
    private String  indexCode;  //人脸分组的唯一标识
    @ApiModelProperty(required = true)
    private String  name ;      //分组名称
    @ApiModelProperty(required = true)
    private String  description;  //分组描述

    public FaceGroup(){

    }
    public FaceGroup(String indexCode, String name, String description) {
        this.indexCode = indexCode;
        this.name = name;
        this.description = description;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
