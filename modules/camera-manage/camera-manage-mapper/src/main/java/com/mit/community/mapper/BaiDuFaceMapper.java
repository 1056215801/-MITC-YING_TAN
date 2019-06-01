package com.mit.community.mapper;

import com.mit.community.entity.FaceComparisonData;
import com.mit.community.entity.FaceSimilarity;
import com.mit.community.entity.PersonInfo;
import com.mit.community.entity.SnapFaceData;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BaiDuFaceMapper {
	public List<SnapFaceData> getNoAddSnapFacePhoto();
	public void saveFaceToken(@Param("id") Integer id, @Param("faceToken") String faceToken);
	public List<FaceComparisonData> getNoAddComparisonFacePhoto();
	public void saveComparisonFaceToken(@Param("id") Integer id, @Param("faceToken") String faceToken);
	public List<PersonInfo> getNoAddUserFacePhoto();
	public void saveUserFaceToken(@Param("id") Integer id, @Param("faceToken") String faceToken, @Param("userInfo") String userInfo);
	public List<FaceSimilarity> getFaceSimilarityRecords();
	public void saveFaceSimilarity(@Param("similarity") double similarity, @Param("id") Integer id);
	public List<SnapFaceData> getNoDetectSnapPhoto();
	public void saveSnapFaceDetect(@Param("id") Integer id, @Param("age") int age, @Param("beauty") double beauty, @Param("expression") String expression,
                                   @Param("sex") String sex, @Param("glasses") String glasses, @Param("race") String race, @Param("mood") String mood);
	public List<FaceComparisonData> getNoDetectComparisionPhoto();
	public void saveComparisonFaceDetect(@Param("id") Integer id, @Param("age") int age, @Param("beauty") double beauty,
                                         @Param("expression") String expression, @Param("sex") String sex, @Param("glasses") String glasses, @Param("race") String race,
                                         @Param("mood") String mood);
	public List<PersonInfo> getPersonInfo();
	public List<FaceSimilarity> getUncheckIsStrange();
	public void upIsStrange(@Param("id")Integer id, @Param("isStrange")int isStrange);
	public void upIsSynchro(@Param("id")Integer id, @Param("isSynchro")int isSynchro);
}
