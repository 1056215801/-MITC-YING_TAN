package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 精选活动详情
 * @author Mr.Deng
 * @date 2018/12/19 20:15
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("selection_activities_content")
public class SelectionActivitiesContent extends BaseEntity {
    /**
     * 关联selection_activites表id
     */
    @TableField("selection_activities_id")
    private Integer selectionActivitiesId;
    /**
     * 详情
     */
    private String content;

}
