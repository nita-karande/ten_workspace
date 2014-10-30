package com.ten.beans;

import java.util.ArrayList;

import com.ten.struts2.actions.ActionConstants;
/**
 * @author Nita Karande
 * This is the bean used data transfer between jsp and action classes
 * This class will fields for dublin core annotations
 */
public class UserDetailsBean {
	String user_name;
	ArrayList<String> roles;
	boolean userIntaker;
	boolean userAnnotator;
	boolean userCreator;
	boolean userStudent;
	boolean userMentor;
	
	public UserDetailsBean(){
		roles = new ArrayList<String>();
		user_name = "";
		userIntaker = false;
		userAnnotator = false;
		userCreator = false;
		userStudent = false;
		userMentor = false;
	}
	
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
		for(String role:roles){
			if(role.equals(ActionConstants.ROLE_INTAKER)){
				userIntaker = true;
			}
			
			if(role.equals(ActionConstants.ROLE_ANNOTATOR)){
				userAnnotator = true;
			}
			
			if(role.equals(ActionConstants.ROLE_CREATOR)){
				userCreator = true;
			}
			
			if(role.equals(ActionConstants.ROLE_MENTOR)){
				userMentor = true;
			}
			
			if(role.equals(ActionConstants.ROLE_STUDENT)){
				userStudent = true;
			}
		}
	}	
	
	public boolean isUserIntaker() {
		return userIntaker;
	}

	public boolean isUserAnnotator() {
		return userAnnotator;
	}

	public boolean isUserCreator() {
		return userCreator;
	}

	public boolean isUserStudent() {
		return userStudent;
	}

	public boolean isUserMentor() {
		return userMentor;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public ArrayList<String> getRoles() {
		return roles;
	}	
}
