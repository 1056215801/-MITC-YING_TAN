package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/24 19:05
 * @Company mitesofor
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdditionalParameters<T> {

    private Map<String, Object> children;
}
