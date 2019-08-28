package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qishengjun
 * @Date Created in 17:25 2019/8/23
 * @Company: mitesofor </p>
 * @Description:~
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("house_message")
public class HouseMessage extends BaseEntity {
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "房屋id")
    @TableField("room_id")
    private Integer roomId;

    @ApiModelProperty(value = "房屋编码")
    @TableField("house_code")
    private String houseCode;

    @ApiModelProperty(value = "房屋类别：1楼房；2别墅；3平房；4其他")
    @TableField("house_type")
    private String houseType;

    @ApiModelProperty(value = "房屋用途：1居住；2商业；3办公；4仓储")
    @TableField("house_usage")
    private String houseUsage;

    @ApiModelProperty(value = "居住现状：1自住；2出租；3自租；4空置；5日租；6治安复杂")
    @TableField("living_status")
    private String livingStatus;

    @ApiModelProperty(value = "产权人姓名")
    @TableField("owner_name")
    private String ownerName;

    @ApiModelProperty(value = "证件类型：1身份证；2护照；3居住证；4军人证")
    @TableField("certificate_type")
    private String certificateType;

    @ApiModelProperty(value = "证件号码")
    @TableField("certificate_code")
    private String certificateCode;
    @ApiModelProperty(value = "联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "房屋地址")
    @TableField("address")
    private  String address;


}
