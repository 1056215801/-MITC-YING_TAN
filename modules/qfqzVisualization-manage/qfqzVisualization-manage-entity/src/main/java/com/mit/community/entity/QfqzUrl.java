package com.mit.community.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("video_qfqz_stream_url")
@Data
public class QfqzUrl {

    private int id;
    private String url;
    private String userId;
}
