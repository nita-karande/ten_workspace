package com.ten.struts2.actions;

/**
 * @author Nita Karande
 * This class contains constants used in action classes
 */
public interface ActionConstants {
	public static final String ROLE_INTAKER = "intaker";
	
	public static final String ROLE_ANNOTATOR = "annotator";
	
	public static final String ROLE_CREATOR = "creator";
	
	public static final String ROLE_STUDENT = "student";
	
	public static final String ROLE_MENTOR = "mentor";
	
	public static final String[] ALL_ROLES = {ROLE_INTAKER, ROLE_ANNOTATOR, ROLE_CREATOR, ROLE_STUDENT, ROLE_MENTOR};
	
	public static final String KEY_USER_DETAILS = "user_details";
	
	public static final String KEY_STUDENT_ANNOTATIONS = "student_annotations";
	
	public static final String FILE_UPLOAD_SUCCESS_MSG = "File uploaded successfully ";
	
	public static final String FILE_UPLOAD_ERROR_MSG = "File upload failed ";
	
	public static final String RETRIEVE_IMAGES_ERROR_MSG = "Failed to retrieve images ";
	
	public static final String RETRIEVE_AUDIOS_ERROR_MSG = "Failed to retrieve audios ";
	
	public static final String RETRIEVE_VIDEOS_ERROR_MSG = "Failed to retrieve videos ";
	
	public static final String RETRIEVE_TEXTS_ERROR_MSG = "Failed to retrieve texts ";
	
	public static final String CREATE_COURSE_SUCCESS_MSG = "Course created successfully ";
	
	public static final String CREATE_COURSE_ERROR_MSG = "Failed to create course ";	
	
	public static final String VIEW_COURSE_ERROR_MSG = "Failed to view courses ";
	
	public static final String SEARCH_LEARNING_OBJECTS_ERROR_MSG = "Learning objects cannot be searched ";
	
	public static final String ACTION_DISPLAY = "display";	
	
	public static final String ACTION_ANNOTATE = "annotate";
	
	public static final String METHOD_POST = "POST";
	
	public static final String METHOD_GET = "GET";
	
	public static final String FORWARD_SHOWJSP = "showjsp";
	
	public static final String FORWARD_INPUT = "input";
	
	public static final String FORWARD_SUCCESS = "success";
	
	public static final String FORWARD_NEXT_ACTION = "nextaction";
	
	public static final String FORWARD_ERROR = "error";
}
