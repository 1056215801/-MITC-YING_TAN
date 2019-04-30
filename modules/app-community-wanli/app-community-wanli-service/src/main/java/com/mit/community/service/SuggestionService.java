package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Suggestion;
import com.mit.community.mapper.SuggestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;

    public Page<Suggestion> listPage(String communityCode, String type, String status, LocalDateTime gmtCreateTimeStart, LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize) {
        EntityWrapper<Suggestion> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(type)) {
            wrapper.eq("type", type);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", status);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("gmt_create", gmtCreateTimeEnd);
        }
        wrapper.orderBy("gmt_create", false);
        Page<Suggestion> page = new Page<>(pageNum, pageSize);
        List<Suggestion> suggestions =  suggestionMapper.selectPage(page, wrapper);
        page.setRecords(suggestions);
        return page;

    }

}
