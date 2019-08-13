package com.mit.community.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("video_qfqk_stream_url")
@Data
public class QfqzUrl {

    private int id;
    private String url;
}
