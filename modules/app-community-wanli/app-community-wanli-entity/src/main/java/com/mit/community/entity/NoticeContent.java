package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知通告内容表
 *
 * @author Mr.Deng
 * @date 2018/12/3 14:37
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice_content")
@Data
public class NoticeContent extends BaseEntity {
    /**
     * 通知id。关联notice表id
     */
    @TableField("notice_id")
    private Integer noticeId;
    /**
     * 内容
     */
    private String content;

}
