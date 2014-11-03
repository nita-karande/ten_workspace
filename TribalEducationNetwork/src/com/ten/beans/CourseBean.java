package com.ten.beans;

/**
 * @author Nita Karande
 * This is the bean used data transfer between jsp and action classes
 * This class will fields for dublin core annotations
 */
public class CourseBean {
	int courseId;
	String courseName;
	
	public CourseBean(){
		this.courseName ="";
		this.courseId = 0;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
