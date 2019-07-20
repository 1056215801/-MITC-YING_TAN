package com.mit.community.entity.com.mit.community.entity.hik;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data

@TableName("person_face_images")
public class PersonFaceImages {


    private int id;
    @TableField("img_url")
    private String imgUrl;
    @TableField("create_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @TableField("modified_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedTime;
    @TableField("person_face_sort_info_id")
    private int personFaceSortInfoId;

    @TableField("factory_name")
    private String  factoryName;
    @TableField("img_unicode_HK")
    private String  imgUnicodeHK;
}
