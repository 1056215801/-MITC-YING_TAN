package com.mit.community.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 身份证地址表
 *
 * @author Mr.Deng
 * @date 2018/11/21 10:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@TableName("idcard_region")
@Data
public class IdCardRegion implements Serializable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 省份编码
     */
    private String province;
    /**
     * 城市编码
     */
    private String city;
    /**
     * 区域编码
     */
    private String zone;
    /**
     * 地区名
     */
    private String areazone;
}
