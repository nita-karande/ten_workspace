package com.ten.struts2.actions;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ten.beans.LearningObjectDetailsBean;
import com.ten.beans.TenLearningObjectAnnotationsBean;
import com.ten.dao.implementation.DbAccessDaoImpl;
import com.ten.dao.interfaces.DbAccessDaoInterface;
import com.ten.triplestore.dao.implementation.VirtuosoAccessDaoImpl;
import com.ten.triplestore.dao.interfaces.TriplestoreAccessDaoInterface;

/**
 * 
 * @author Nita Karande
 * This action invoked by upload_image.jsp 
 * It invokes method to upload image to database and store annotations in triplestore
 */
public class SearchLearningObjectsAction extends ActionSupport{

	static Logger log = Logger.getLogger(SearchLearningObjectsAction.class);
	
	private static final long serialVersionUID = 1L;
	private String typeOfLearningObject;
	private String courseName;
	private String courseId;
	private String keywords;
	HashMap<String, LearningObjectDetailsBean> mapLearningObjects;
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getTypeOfLearningObject() {
		return typeOfLearningObject;
	}

	public void setTypeOfLearningObject(String typeOfLearningObject) {
		this.typeOfLearningObject = typeOfLearningObject;
	}

	public HashMap<String, LearningObjectDetailsBean> getMapLearningObjects() {
		return mapLearningObjects;
	}

	public void setMapLearningObjects(
			HashMap<String, LearningObjectDetailsBean> mapLearningObjects) {
		this.mapLearningObjects = mapLearningObjects;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * This method is configured to be invoked in struts.xml, for image file uploading and annotations.
	 * It makes calls to mysql dao implementation to store the uploaded file to database
	 * It also stores annotations for image in triple store 
	 */
	public String execute() throws Exception {
		//Get request method invoked
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String method = request.getMethod();
		String result = ActionConstants.FORWARD_SHOWJSP;
		
		if(ActionConstants.METHOD_POST.equalsIgnoreCase(method)){
			try{
				//get learning objects from triple stores
				TriplestoreAccessDaoInterface tdbAccessDaoInterface = new VirtuosoAccessDaoImpl();
				HashMap<String,TenLearningObjectAnnotationsBean> mapLearningObjects = tdbAccessDaoInterface.searchLearningObjects(this.typeOfLearningObject, this.keywords, "");
				
				//get learning object contents
				DbAccessDaoInterface dbAccessDaoInterface = new DbAccessDaoImpl();
				this.mapLearningObjects = dbAccessDaoInterface.getLearningObjectDetails(this.typeOfLearningObject, mapLearningObjects.keySet());
							
	            result = ActionConstants.FORWARD_SUCCESS;
			}catch(Exception ex){
				log.error(ex);
				reset();				
				addActionError(ActionConstants.SEARCH_LEARNING_OBJECTS_ERROR_MSG);
				result = ActionConstants.FORWARD_INPUT;
			}           
		}else if(ActionConstants.METHOD_GET.equalsIgnoreCase(method)){	
			reset();
			result = ActionConstants.FORWARD_SHOWJSP;;
		}
		return result;
	}	
	
	public void reset(){
		this.typeOfLearningObject = "";
		this.mapLearningObjects = null;
	}
}
