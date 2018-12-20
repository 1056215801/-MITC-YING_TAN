package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 老人体检信息表
 * @author Mr.Deng
 * @date 2018/12/18 19:29
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("old_medical")
public class OldMedical extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 发布人
     */
    private String issuer;
    /**
     * 发布时间
     */
    @TableField("issuer_time")
    private LocalDateTime issuerTime;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 登记地点
     */
    private String address;
    /**
     * 活动开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    /**
     * 活动结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    /**
     * 活动状态
     */
    @TableField(exist = false)
    private String oldMedicalStatus;
    /**
     * 活动状态
     */
    @TableField(exist = false)
    private Boolean readStatus;
    /**
     * 详情
     */
    @TableField(exist = false)
    private String content;
    /**
     * 浏览量
     */
    @TableField(exist = false)
    private Integer readNum;

}
