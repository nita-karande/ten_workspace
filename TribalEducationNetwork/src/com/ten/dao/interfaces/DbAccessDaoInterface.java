package com.ten.dao.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.ten.beans.CourseBean;
import com.ten.beans.LearningObjectBean;
import com.ten.beans.LearningObjectDetailsBean;

public interface DbAccessDaoInterface {
	//Method to save image details to database
	public int saveImage(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to change image status to being annotated
	public boolean updateImage(int id) throws Exception;
	
	//Method to save video to database
	public int saveVideo(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to change video status to being annotated
	public boolean updateVideo(int id) throws Exception;
	
	//Method to save audio to database
	public int saveAudio(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to change audio status to being annotated
	public boolean updateAudio(int id) throws Exception;
	
	//Method to save text to database
	public int saveText(File file, String fileName, String fileType, boolean annotated) throws Exception;
	
	//Method to change text status to being annotated
	public boolean updateText(int id) throws Exception;
	
	//Method to get unannotated images from database
	public ArrayList<LearningObjectBean> getUnannotatedImages() throws Exception;
	
	//Method to get image from database
	public LearningObjectDetailsBean getImage(int id) throws Exception;	
	
	//Method to get unannotated videos from database
	public ArrayList<LearningObjectBean> getUnannotatedVideos() throws Exception;
	
	//Method to get video from database
	public LearningObjectDetailsBean getVideo(int id) throws Exception;	
	
	//Method to get unannotated audios from database
	public ArrayList<LearningObjectBean> getUnannotatedAudios() throws Exception;
	
	//Method to get audio from database
	public LearningObjectDetailsBean getAudio(int id) throws Exception;	
	
	//Method to get unannotated texts from database
	public ArrayList<LearningObjectBean> getUnannotatedTexts() throws Exception;
	
	//Method to get text from database
	public LearningObjectDetailsBean getText(int id) throws Exception;	
		
	//Method to get insert course in database
	public int insertCourse(String courseName) throws Exception;
	
	//get learning object content
	public HashMap<String, LearningObjectDetailsBean> getLearningObjectDetails(String type, Set<String> uri)throws Exception;
	
	//get courses
	public ArrayList<CourseBean> getCourses()throws Exception;
}
