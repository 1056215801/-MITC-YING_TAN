package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.entity.User;
import com.mit.community.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class UserService {


}
