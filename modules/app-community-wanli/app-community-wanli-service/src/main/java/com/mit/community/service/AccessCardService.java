package com.mit.community.service;

import com.mit.community.entity.AccessCard;
import com.mit.community.mapper.AccessCardMapper;
<<<<<<< HEAD
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.population.service.PersonLabelsService;
=======
>>>>>>> remotes/origin/newdev
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessCardService {
    @Autowired
    private AccessCardMapper accessCardMapper;
<<<<<<< HEAD
    @Autowired
    private PersonLabelsMapper personLabelsMapper;
=======
>>>>>>> remotes/origin/newdev

    public void save(AccessCard accessCard) {
        accessCardMapper.insert(accessCard);
    }
<<<<<<< HEAD

    public AccessCard getByCardNumAndMac (String cardNum, String mac) {
        return personLabelsMapper.getByCardNumAndMac(cardNum, mac);
    }


=======
>>>>>>> remotes/origin/newdev
}
