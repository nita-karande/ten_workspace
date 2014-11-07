package com.ten.struts2.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ten.beans.CourseAnnotationsBean;
import com.ten.dao.implementation.DbAccessDaoImpl;
import com.ten.dao.interfaces.DbAccessDaoInterface;
import com.ten.triplestore.dao.implementation.VirtuosoAccessDaoImpl;
import com.ten.triplestore.dao.interfaces.TriplestoreAccessDaoInterface;

/**
 * 
 * @author Nita Karande
 * This action invoked by create_course.jsp and main.jsp
 * It invokes method to create course and store it to database and store annotations in triplestore
 */
public class CreateCourseAction extends ActionSupport{

	static Logger log = Logger.getLogger(CreateCourseAction.class);
	
	private static final long serialVersionUID = 1L;
	CourseAnnotationsBean courseAnnotationsBean;
    String courseName;

	public CourseAnnotationsBean getCourseAnnotationsBean() {
		return courseAnnotationsBean;
	}

	public void setCourseAnnotationsBean(CourseAnnotationsBean courseAnnotationsBean) {
		this.courseAnnotationsBean = courseAnnotationsBean;
	}
    	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * This method is configured to be invoked in struts.xml, for creating courses and storing course related annotations in triple store.
	 * It makes calls to mysql dao implementation to store the course in database
	 * It also stores annotations for course in triple store 
	 */
	public String execute() throws Exception {
		//Get request method invoked
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String method = request.getMethod();
		String result = ActionConstants.FORWARD_SHOWJSP;
		
		if(ActionConstants.METHOD_POST.equalsIgnoreCase(method)){
			try{
				//Insert course into Mysql database
				DbAccessDaoInterface dbAccessDaoInterface = new DbAccessDaoImpl();
				int courseId = dbAccessDaoInterface.insertCourse(this.courseName);
				
				request.setAttribute("courseId", courseId);
				request.setAttribute("keywords", this.courseAnnotationsBean.getKeywords());
				request.setAttribute("description", this.courseAnnotationsBean.getDescription());
				
				//Insert annotations in Triplestore
				TriplestoreAccessDaoInterface tdbAccessDaoInterface = new VirtuosoAccessDaoImpl();
				tdbAccessDaoInterface.insertCourseAnnotations(this.courseAnnotationsBean, courseId);
							
	           //course created successfully
			   addActionMessage(ActionConstants.CREATE_COURSE_SUCCESS_MSG);
	           result = ActionConstants.FORWARD_SUCCESS;
			}catch(Exception ex){
				log.error(ex);
				reset();				
				addActionError(ActionConstants.CREATE_COURSE_ERROR_MSG);
				result = ActionConstants.FORWARD_INPUT;
			}           
		}else if(ActionConstants.METHOD_GET.equalsIgnoreCase(method)){	
			reset();
			result = ActionConstants.FORWARD_SHOWJSP;;
		}
		return result;
	}	
	
	public void reset(){
		this.courseName = "";
		this.courseAnnotationsBean = new CourseAnnotationsBean();
	}
}
