package com.mit.community.service;

import com.mit.community.entity.FaceComparisonData;
import com.mit.community.entity.FaceSimilarity;
import com.mit.community.entity.PersonInfo;
import com.mit.community.entity.SnapFaceData;
import com.mit.community.mapper.BaiDuFaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaiDuFaceService {
	@Autowired
	private BaiDuFaceMapper baiDuFaceMapper;
	
	public List<SnapFaceData> getNoAddSnapFacePhoto() {
		return baiDuFaceMapper.getNoAddSnapFacePhoto();
	}
	
	public void saveFaceToken(Integer id, String faceToken) {
		baiDuFaceMapper.saveFaceToken(id, faceToken);
	}
	
	public List<FaceComparisonData> getNoAddComparisonFacePhoto() {
		return baiDuFaceMapper.getNoAddComparisonFacePhoto();
	}
	
	public void saveComparisonFaceToken(Integer id, String faceToken) {
		baiDuFaceMapper.saveComparisonFaceToken(id, faceToken);
	}
	
	public List<PersonInfo> getNoAddUserFacePhoto() {
		return baiDuFaceMapper.getNoAddUserFacePhoto();
	}
	
	public void saveUserFaceToken(Integer id, String faceToken, String userInfo) {
		baiDuFaceMapper.saveUserFaceToken(id, faceToken, userInfo);
	}
	
	public List<FaceSimilarity> getFaceSimilarityRecords() {
		return baiDuFaceMapper.getFaceSimilarityRecords();
	}
	
	public void saveFaceSimilarity(double similarity, Integer id) {
		baiDuFaceMapper.saveFaceSimilarity(similarity, id);
	}
	
	public List<SnapFaceData> getNoDetectSnapPhoto(){
		return baiDuFaceMapper.getNoDetectSnapPhoto();
	}

	public void saveSnapFaceDetect(Integer id, int age, double beauty, String expression,
			String sex, String glasses, String race, String mood) {
		// TODO Auto-generated method stub
		baiDuFaceMapper.saveSnapFaceDetect(id, age, beauty, expression, sex, glasses, race, mood);
	}

	public List<FaceComparisonData> getNoDetectComparisionPhoto() {
		// TODO Auto-generated method stub
		return baiDuFaceMapper.getNoDetectComparisionPhoto();
	}

	public void saveComparisonFaceDetect(Integer id, int age, double beauty,
			String expression, String sex, String glasses, String race,
			String mood) {
		// TODO Auto-generated method stub
		baiDuFaceMapper.saveComparisonFaceDetect(id, age, beauty, expression, sex, glasses, race, mood);
	}

	public List<PersonInfo> getPersonInfo() {
		// TODO Auto-generated method stub
		return baiDuFaceMapper.getPersonInfo();
	}

	public List<FaceSimilarity> getUncheckIsStrange() {
		List<FaceSimilarity> list = baiDuFaceMapper.getUncheckIsStrange();
		return list;
	}

	public void upIsStrange(Integer id, int isStrange) {
		baiDuFaceMapper.upIsStrange(id, isStrange);
	}
	public void upIsSynchro(Integer id, int isSynchro) {
		baiDuFaceMapper.upIsSynchro(id, isSynchro);
	}
}
