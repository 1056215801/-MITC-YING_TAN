package com.mit.community.service;

import com.mit.community.entity.AccessCard;
import com.mit.community.mapper.AccessCardMapper;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;
    @Autowired
    private PersonLabelsMapper personLabelsMapper;

    public void save(AccessCard accessCard) {
        accessCardMapper.insert(accessCard);
    }

    public AccessCard getByCardNumAndMac (String cardNum, String mac) {
        return personLabelsMapper.getByCardNumAndMac(cardNum, mac);
    }


}
