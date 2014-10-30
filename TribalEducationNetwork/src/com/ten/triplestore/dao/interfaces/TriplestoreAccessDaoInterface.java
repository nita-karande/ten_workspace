package com.ten.triplestore.dao.interfaces;
import com.ten.beans.DigitalRightsManagementBean;
import com.ten.beans.TenLearningObjectAnnotationsBean;

public interface TriplestoreAccessDaoInterface {
	public void queryLearningObject(String learningObjectType) throws Exception;	
	
	//Insert image digital rights management data in triple store
	public boolean insertImageDigitalRightsManagementData(DigitalRightsManagementBean digitalRightsManagementBean, int imageId) throws Exception;
		
	//Insert image annotations data in triple store
	public boolean insertImageAnnotations(TenLearningObjectAnnotationsBean learningObjectAnnotationsBean, int imageId) throws Exception;
	
	//Insert video digital rights management data in triple store
	public boolean insertVideoDigitalRightsManagementData(DigitalRightsManagementBean digitalRightsManagementBean, int videoId) throws Exception;
	
	//Insert video digital rights management data in triple store
	public boolean insertVideoAnnotations(TenLearningObjectAnnotationsBean TenLearningObjectAnnotationsBean, int videoId) throws Exception;
	
	//Insert audio digital rights management data in triple store
	public boolean insertAudioDigitalRightsManagementData(DigitalRightsManagementBean digitalRightsManagementBean, int audioId) throws Exception;
	
	//Insert audio annotations data in triple store
	public boolean insertAudioAnnotations(TenLearningObjectAnnotationsBean TenLearningObjectAnnotationsBean, int audioId) throws Exception;
	
	//Insert text digital rights management data in triple store
	public boolean insertTextDigitalRightsManagementData(DigitalRightsManagementBean digitalRightsManagementBean, int textId) throws Exception;
	
	//Insert text annotations data in triple store
	public boolean insertTextAnnotations(TenLearningObjectAnnotationsBean TenLearningObjectAnnotationsBean, int textId) throws Exception;
}
