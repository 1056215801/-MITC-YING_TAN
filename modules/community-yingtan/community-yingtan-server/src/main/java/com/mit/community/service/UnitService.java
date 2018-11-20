package com.mit.community.service;

import com.mit.community.entity.Unit;
import com.mit.community.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * 单元业务类
 *
 * @author Mr.Deng
 * @date 2018/11/14 17:18
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class UnitService {
    private final UnitMapper unitMapper;

    @Autowired
    public UnitService(UnitMapper unitMapper) {
        this.unitMapper = unitMapper;
    }

    /**
     * 保存单元信息
     *
     * @param unit 单元信息
     * @author Mr.Deng
     * @date 17:19 2018/11/14
     */
    public void save(Unit unit) {
        unitMapper.insert(unit);
    }

    /**
     * 获取所有的单元信息
     *
     * @return 单元信息列表
     * @author Mr.Deng
     * @date 17:20 2018/11/14
     */
    public List<Unit> list() {
        return unitMapper.selectList(null);
    }
}
