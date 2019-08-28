package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("household_photo")
public class HouseHoldPhoto extends BaseEntity {
    @TableField("household_id")
    private Integer houseHoldId;

    @TableField("photo_url_net")
    private String photoUrlNet;

    @TableField("photo_url")
    private String photoUrl;//本地保存的地址

    @TableField("fea_url")
    private String feaUrl;

    @TableField("device_group_id")
    private Integer deviceGroupId;

    @TableField("is_upload")
    private int isUpload;//是否成功上传到机器；1否；2是

    @TableField("device_num")
    private String deviceNum;
}
