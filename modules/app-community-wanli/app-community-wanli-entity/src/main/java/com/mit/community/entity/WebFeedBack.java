package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor//lombok生成全参构造函数
@NoArgsConstructor//生成无参构造函数
@Data
@TableName("feedback")
public class WebFeedBack extends BaseEntity {
    /**
     * 社区编码
     */
    @TableField(exist = false)
    private String communityCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 反馈人姓名
     */
    @TableField(exist = false)
    private String feedBackName;
    /**
     * 反馈人联系电话
     */
    @TableField(exist = false)
    private String feedBackMoblie;
    /**
     * 处理状态
     */
    private String status;
    /**
     * 受理人
     */
    @TableField("handler")
    private Integer receiver;
    /**
     * 受理人姓名
     */
    @TableField(exist = false)
    private String handlename;
    /**
     * 受理时间
     */
    @TableField("handletime")
    private LocalDateTime receiverTime;
    /**
     * 处理备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField(exist = false)
    private LocalDateTime createTime;
}
