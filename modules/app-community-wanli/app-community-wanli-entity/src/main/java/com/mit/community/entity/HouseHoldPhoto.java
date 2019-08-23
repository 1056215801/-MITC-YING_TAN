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
    private String houseHoldId;

    @TableField("photo_url")
    private String photoUrl;

    @TableField("fea_url")
    private String fea_url;

    @TableField("device_num")
    private String deviceNum;

    @TableField("is_upload")
    private int is_upload;//是否成功上传到机器；1否；2是
}
