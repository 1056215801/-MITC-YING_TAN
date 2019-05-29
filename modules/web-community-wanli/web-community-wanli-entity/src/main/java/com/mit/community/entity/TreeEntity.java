package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/24 19:01
 * @Company mitesofor
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreeEntity<T> {

    private String name;

    private String type;

    private AdditionalParameters<T> additionalParameters;

    private T t;

    private Integer id;
}
