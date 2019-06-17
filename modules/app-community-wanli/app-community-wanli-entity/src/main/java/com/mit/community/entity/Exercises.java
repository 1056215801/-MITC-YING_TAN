package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/31 19:13
 * @Company mitesofor
 * @Description:问卷题目
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("investigation_exercises")
public class Exercises extends BaseEntity {

    /**
     * 问卷id
     */
    private Integer modelId;
    /**
     * 描述
     */
    private String describe;
    /**
     * 题目类型
     */
    private String type;
    /**
     * 选项个数
     */
    private Integer count;
    /**
     * 选项内容
     */
    private Integer options;

}
