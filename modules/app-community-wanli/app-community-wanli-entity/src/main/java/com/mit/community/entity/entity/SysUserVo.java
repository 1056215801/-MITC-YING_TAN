package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author qishengjun
 * @Date Created in 14:29 2019/9/16
 * @Company: mitesofor </p>
 * @Description:~
 */
@Data
public class SysUserVo {

    @ApiModelProperty(value = "主键(新增不用传id,修改需要)")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "省份名称")
    private String provinceName;

    @ApiModelProperty(value = "省份编号")
    private String provinceId;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "城市编号")
    private String cityId;

    @ApiModelProperty(value = "区/县名称")
    private String areaName;

    @ApiModelProperty(value = "区编号")
    private String areaId;

    @ApiModelProperty(value = "镇/街道")
    private String streetName;

    @ApiModelProperty("街道编号")
    private String streetId;

    @ApiModelProperty(value = "居委")
    private String committee;

    @ApiModelProperty("居委编号")
    private String committeeId;

    @ApiModelProperty(value = "管理员姓名")
    private String adminName;

    @ApiModelProperty(value = "账户角色")
    private String role;

    @ApiModelProperty(value = "账号类型")
    private String accountType;

    @ApiModelProperty(value = "联系电话")
    private String phone;


}
