package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ExpressAddress;
import com.mit.community.mapper.ExpressAddressMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ExpressAddressService
 * @author Mr.Deng
 * @date 2018/12/14 16:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ExpressAddressService {
    @Autowired
    private ExpressAddressMapper expressAddressMapper;
    @Autowired
    private ExpressInfoService expressInfoService;
    @Autowired
    private ExpressReadUserService expressReadUserService;

    /**
     * 添加快递位置信息
     * @param communityCode  小区code
     * @param name           快递名称
     * @param address        领取地址
     * @param imgUrl         图片地址
     * @param createUserName 创建人姓名
     * @author Mr.Deng
     * @date 16:03 2018/12/26
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String communityCode, String name, String address, String imgUrl, String createUserName) {
        ExpressAddress expressAddress = new ExpressAddress();
        expressAddress.setCommunityCode(communityCode);
        expressAddress.setName(name);
        expressAddress.setAddress(address);
        expressAddress.setImgUrl(imgUrl);
        expressAddress.setCreateUserName(createUserName);
        expressAddress.setGmtCreate(LocalDateTime.now());
        expressAddress.setGmtModified(LocalDateTime.now());
        expressAddressMapper.insert(expressAddress);
    }

    /**
     * 更新快递数据
     * @param id             快递位置id
     * @param name           快递名
     * @param address        领取位置
     * @param imageUrl       图片地址
     * @param createUserName 添加人
     * @author Mr.Deng
     * @date 17:17 2018/12/26
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String name, String address, String imageUrl, String createUserName) {
        ExpressAddress expressAddress = new ExpressAddress();
        expressAddress.setId(id);
        if (StringUtils.isNotBlank(name)) {
            expressAddress.setName(name);
        }
        if (StringUtils.isNotBlank(address)) {
            expressAddress.setAddress(address);
        }
        if (StringUtils.isNotBlank(imageUrl)) {
            expressAddress.setImgUrl(imageUrl);
        }
        if (StringUtils.isNotBlank(createUserName)) {
            expressAddress.setCreateUserName(createUserName);
        }
        expressAddress.setGmtModified(LocalDateTime.now());
        expressAddressMapper.updateById(expressAddress);
    }

    /**
     * 删除快递位置信息
     * @param id 快递位置信息
     * @author Mr.Deng
     * @date 17:30 2018/12/26
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        expressAddressMapper.deleteById(id);
    }

    /**
     * 查询快递位置信息，通过小区code
     * @param communityCode 小区code
     * @return 快递位置信息
     * @author Mr.Deng
     * @date 17:02 2018/12/14
     */
    public Page<ExpressAddress> listByCommunityCodePage(Integer userId, String communityCode, Integer pageNum, Integer pageSize) {
        EntityWrapper<ExpressAddress> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        Page<ExpressAddress> page = new Page<>(pageNum, pageSize);
        List<ExpressAddress> expressAddresses = expressAddressMapper.selectPage(page, wrapper);
        if (!expressAddresses.isEmpty()) {
            for (ExpressAddress expressAddress : expressAddresses) {
                Integer integer = expressInfoService.countNotExpressNum(userId, expressAddress.getId());
                Integer total = expressInfoService.countExpressNum(userId, expressAddress.getId());
                Integer readNum = expressReadUserService.countByUserIdAndExpressAddressId(userId, expressAddress.getId());
                if (total > readNum) {
                    expressAddress.setReadStatus(false);
                } else {
                    expressAddress.setReadStatus(true);
                }
                expressAddress.setExpressNum(integer);
                expressAddress.setTotal(total);
            }
        }
        page.setRecords(expressAddresses);
        return page;
    }

    /**
     * 分页获取本小区快递地址信息
     * @param communityCode  小区code
     * @param name           快递名称
     * @param address        快递地址
     * @param createUserName 领取位置
     * @param pageNum        页数
     * @param pageSize       一页数量
     * @return 分页快递地址信息
     * @author Mr.Deng
     * @date 9:23 2018/12/27
     */
    public Page<ExpressAddress> listPage(String communityCode, String name, String address, String createUserName,
                                         Integer pageNum, Integer pageSize) {
        Page<ExpressAddress> page = new Page<>(pageNum, pageSize);
        EntityWrapper<ExpressAddress> wrapper = new EntityWrapper<>();
        wrapper.orderBy("gmt_modified", false);
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("name", name);
        }
        if (StringUtils.isNotBlank(address)) {
            wrapper.eq("address", address);
        }
        if (StringUtils.isNotBlank(createUserName)) {
            wrapper.eq("create_user_name", createUserName);
        }
        List<ExpressAddress> expressAddresses = expressAddressMapper.selectPage(page, wrapper);
        page.setRecords(expressAddresses);
        return page;
    }

}
