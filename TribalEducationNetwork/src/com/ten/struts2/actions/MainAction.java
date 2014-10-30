package com.ten.struts2.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ten.beans.UserDetailsBean;
import com.ten.utils.Utils;

public class MainAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {
         HttpServletRequest request = ServletActionContext.getRequest();
         String result = ActionConstants.FORWARD_ERROR;
         String user_name = request.getUserPrincipal().getName();
         
         //check if user is logged in
         if(!Utils.isEmptyOrNull(user_name)){
        	 //create user details object and store in session
        	HttpSession session =  request.getSession();
        	
        	UserDetailsBean userDetailsBean = new UserDetailsBean();
        	userDetailsBean.setUser_name(user_name);
        	ArrayList<String> roles = new ArrayList<String>();
        	for(String role : ActionConstants.ALL_ROLES){
        		 if(request.isUserInRole(role)) { 
        			 roles.add(role);
        		 }
        	}
        	userDetailsBean.setRoles(roles);
        	
        	session.setAttribute(ActionConstants.KEY_USER_DETAILS, userDetailsBean);
        	
        	result = ActionConstants.FORWARD_SUCCESS;
         }else{
        	 Exception ex = new Exception();
        	 throw ex;
         }
         return result;
    }
}
