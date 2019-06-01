package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/31 19:18
 * @Company mitesofor
 * @Description:问卷结果
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("investigation_survey_result")
public class SurveyResult extends BaseEntity {

    private Integer userId;

    private Integer exercisesId;

    private String answers;
}
