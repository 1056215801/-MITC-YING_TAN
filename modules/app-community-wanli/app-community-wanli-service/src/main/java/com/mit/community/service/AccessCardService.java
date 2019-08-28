package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.AccessCard;
import com.mit.community.mapper.AccessCardMapper;

import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;

import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;

}
