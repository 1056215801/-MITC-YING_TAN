package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知已读人员表
 * @author Mr.Deng
 * @date 2018/12/14 10:29
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("notice_read_user")
public class NoticeReadUser extends BaseEntity {
    /**
     * 关联 notice表id字段。
     */
    @TableField("notice_id")
    private Integer noticeId;
    /**
     * 用户id，关联user表id。
     */
    @TableField("user_id")
    private Integer userId;
}
