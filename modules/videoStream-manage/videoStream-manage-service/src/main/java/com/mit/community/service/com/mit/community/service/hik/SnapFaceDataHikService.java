package com.mit.community.service.com.mit.community.service.hik;


import com.mit.community.entity.com.mit.community.entity.hik.SnapFaceDataHik;
import com.mit.community.mapper.com.mit.community.mapper.hik.SnapFaceDataHikMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SnapFaceDataHikService {
	@Autowired
	private SnapFaceDataHikMapper snapFaceDataHikMapper;

	/**
	 *
	 * @param SnapFaceDataHik 对象
	 * @return
	 * @company mitesofor
	 */
	public int  save (SnapFaceDataHik snapFaceDataHik) {

		int i=  snapFaceDataHikMapper.insert(snapFaceDataHik);
        return i;

	}

}
