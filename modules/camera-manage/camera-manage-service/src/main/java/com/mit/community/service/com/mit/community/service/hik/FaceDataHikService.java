package com.mit.community.service.com.mit.community.service.hik;



import com.mit.community.entity.com.mit.community.entity.hik.FaceInfo;
import com.mit.community.entity.com.mit.community.entity.hik.PersonFaceImages;
import com.mit.community.entity.com.mit.community.entity.hik.PersonFaceSortInfo;
import com.mit.community.mapper.com.mit.community.mapper.hik.PersonFaceImagesMapper;
import com.mit.community.mapper.com.mit.community.mapper.hik.PersonFaceSortInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
public class FaceDataHikService {
	@Autowired
	private PersonFaceSortInfoMapper personFaceSortInfoMapper;
    @Autowired
	private PersonFaceImagesMapper personFaceImagesMapper;

    String  ARTEMIS_PATH = "/artemis";

	/**
	 *
	 * @param
	 * @return
	 * @company mitesofor
	 */

	@Transactional(rollbackFor = Exception.class)
	public int  saveSinglePersonFaceLocal (FaceInfo faceInfo) throws Exception {
		PersonFaceSortInfo personFaceSortInfo=new PersonFaceSortInfo();
		String groupName=faceInfo.getFaceGroupName();
		// １白名单　，２黑名单　，３，陌生人，４　重点人员
		if("白名单".equals(groupName)){
			personFaceSortInfo.setBelongType(1);
		}else if("黑名单".equals(groupName)){
			personFaceSortInfo.setBelongType(2);
		}else if("陌生人".equals(groupName)){
			personFaceSortInfo.setBelongType(3);
		}else if("重点人员".equals(groupName)){
			personFaceSortInfo.setBelongType(4);
		}else{
			personFaceSortInfo.setBelongType(1);
		}

		personFaceSortInfo.setMobile(faceInfo.getMobile());

		java.util.Date date = new java.util.Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime createTime = LocalDateTime.ofInstant(instant, zone);
		personFaceSortInfo.setCreateTime(createTime);
		personFaceSortInfo.setModifiedTime(createTime);
		personFaceSortInfo.setDeleted(0);
		int i=0;
		i=personFaceSortInfoMapper.insert(personFaceSortInfo);
		int personFaceSortInfoId=personFaceSortInfo.getId();
		PersonFaceImages personFaceImages=new PersonFaceImages();
		personFaceImages.setPersonFaceSortInfoId(personFaceSortInfoId);
		personFaceImages.setImgUrl(faceInfo.getFaceUrl());
		personFaceImages.setCreateTime(createTime);
		personFaceImages.setModifiedTime(createTime);
		personFaceImages.setImgUnicodeHK(faceInfo.getIndexCode());
		i=personFaceImagesMapper.insert(personFaceImages);

		return i;
	}


}
