package com.mit.community.service;

import com.mit.community.entity.AccessCard;
import com.mit.community.mapper.AccessCardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;

    public void save(AccessCard accessCard) {
        accessCardMapper.insert(accessCard);
    }
}
