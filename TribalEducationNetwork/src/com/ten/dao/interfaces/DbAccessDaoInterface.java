package com.ten.dao.interfaces;

import java.io.File;
import java.util.ArrayList;

import com.ten.beans.LearningObjectBean;
import com.ten.beans.LearningObjectDetailsBean;

public interface DbAccessDaoInterface {
	//Method to save image details to database
	public int saveImage(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to save video to database
	public int saveVideo(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to save audio to database
	public int saveAudio(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to save text to database
	public int saveText(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to get unannotated images from database
	public ArrayList<LearningObjectBean> getUnannotatedImages() throws Exception;
	
	//Method to get image from database
	public LearningObjectDetailsBean getImage(int id) throws Exception;	
}
