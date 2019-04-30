package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


    @AllArgsConstructor//lombok生成全参构造函数
    @NoArgsConstructor//生成无参构造函数
    @Data
    @TableName("feedback")
    public class Test extends BaseEntity{
        /**
         * 反馈内容
         */
        private String content;
        /**
         * 反馈人姓名
         */
        private String feedBackName;
        /**
         * 反馈人联系电话
         */
        private String feedBackMoblie;
        /**
         * 小区名称
         */
        private String communityName;
        /**
         * 到访分区名称
         */
        private String zoneName;
        /**
         * 楼栋名称
         */
        private String buildingName;
        /**
         * 单元名称
         */
        private String unitName;
        /**
         * 房间号
         */
        private String roomNum;
        /**
         * 处理状态
         */
        private String status;
        /**
         * 受理人
         */
        private String receiver;
        /**
         * 受理时间
         */
        private LocalDateTime receiverTime;
    }

