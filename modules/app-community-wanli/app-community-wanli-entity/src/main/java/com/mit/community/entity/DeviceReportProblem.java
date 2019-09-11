package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("device_report_problem")
public class DeviceReportProblem extends BaseEntity{
    private Integer device_id;
    private String content;
    private String problemType;
    private String address;
    @TableField("device_type")
    private int deviceType;//1狄耐克门禁机；
    private int status;
    //private int mqlzd;//目前流转到什么地方
    @TableField(exist = false)
    private Device device;
}
