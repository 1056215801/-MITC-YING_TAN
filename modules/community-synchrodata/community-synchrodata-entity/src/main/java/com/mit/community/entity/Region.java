package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 地区表
 * @author Mr.Deng
 * @date 2018/12/3 14:30
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@TableName("region")
@Data
public class Region extends BaseEntity {
    /**
     * 城市编码
     */
    @TableField("city_code")
    private String cityCode;
    /**
     * 城市英文名
     */
    @TableField("english_name")
    private String englishName;
    /**
     * 城市中文名
     */
    @TableField("chinses_name")
    private String chinsesName;
    /**
     * 省/市/国家
     */
    private String country;
}
