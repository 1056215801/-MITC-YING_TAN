package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("dnake_device_info")
public class DnakeDeviceInfo extends BaseEntity{
    private String mac;
    private String ip;
}
