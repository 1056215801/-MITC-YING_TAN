package com.mit.community.entity;

import lombok.Data;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/7 15:36
 * @Company mitesofor
 * @Description:公告修改实体映射
 */
@Data
public class NoticeVo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 公告状态
     */
    private Integer noticeState;

    /**
     * 标题
     */
    private String title;

    /**
     * 公告渠道
     */
    private Integer noticeChannel;

    /**
     * 公告类型
     */
    private Integer noticeType;

    /**
     * 封面图片域名
     */
    private String portraitFileDomain;

    /**
     * 封面图片文件名
     */
    private String portraitFileName;

    /**
     * 开始日期
     */
    private String startTime;

    /**
     * 到期日期
     */
    private String endTime;

    /**
     * 公告内容
     */
    private String noticeContent;
}



