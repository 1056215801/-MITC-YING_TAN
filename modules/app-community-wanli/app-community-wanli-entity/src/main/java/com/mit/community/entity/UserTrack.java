package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户访客表
 * @author Mr.Deng
 * @date 2018/12/11 19:23
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("user_track")
public class UserTrack extends BaseEntity {
    /**
     * 用户id，关联人员id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 访问时间
     */
    @TableField("gmt_visit")
    private LocalDateTime gmtVisit;
}
