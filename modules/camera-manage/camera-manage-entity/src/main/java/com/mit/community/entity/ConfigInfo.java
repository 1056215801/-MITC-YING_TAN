package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("config_info")
public class ConfigInfo {
    private String ip;
    private String port;
    private String appKey;
    private String appSecret;
}
