package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.constants.Constants;
import com.mit.community.entity.User;
import com.mit.community.entity.UserTrack;
import com.mit.community.mapper.UserTrackMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户足迹业务层
 * @author Mr.Deng
 * @date 2018/12/11 19:27
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Slf4j
@Service
public class UserTrackService {
    @Autowired
    private UserTrackMapper userTrackMapper;
    @Autowired
    private UserService userService;

    /**
     * 添加用户足迹表
     * @param userTrack 用户足迹信息
     * @author Mr.Deng
     * @date 19:28 2018/12/11
     */
    public void save(UserTrack userTrack) {
        userTrack.setGmtCreate(LocalDateTime.now());
        userTrack.setGmtModified(LocalDateTime.now());
        userTrackMapper.insert(userTrack);
    }

    /**
     * 查询用户足迹信息，通过用户id
     * @param userId 用户id
     * @return 用户足迹信息列表
     * @author Mr.Deng
     * @date 19:30 2018/12/11
     */
    public List<UserTrack> listByUserId(Integer userId) {
        EntityWrapper<UserTrack> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return userTrackMapper.selectList(wrapper);
    }

    /**
     * 添加足迹
     * @param cellphone 手机号
     * @param title     标题
     * @param content   内容
     * @author Mr.Deng
     * @date 9:24 2018/12/12
     */
    public void addUserTrack(String cellphone, String title, String content) {
        //添加足迹记录
        //是否进行足迹记录
        if (Constants.USER_TRACK_TYPE) {
            User user = userService.getByCellphone(cellphone);
            if (user != null) {
                UserTrack userTrack = new UserTrack(user.getId(), title, content, LocalDateTime.now());
                this.save(userTrack);
                log.info(cellphone + "-" + title + "添加成功");
            } else {
                log.info(cellphone + "-" + title + "添加失败");
            }
        }
    }

}
