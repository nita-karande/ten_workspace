package com.ten.beans;

import java.util.Date;

/**
 * @author Nita Karande
 * This is the bean used data transfer between jsp and action classes
 * This class will fields for dublin core annotations
 */
public class DigitalRightsManagementBean {
	//AdminstrativeTags
	String creator;
	String publisher;
	String contributor;
	String rights;	
	String intaker;
	String date;
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	public String getIntaker() {
		return intaker;
	}
	public void setIntaker(String intaker) {
		this.intaker = intaker;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public DigitalRightsManagementBean(){
		super();
	
		creator = "";
		publisher = "";
		contributor = "";
		rights = "";
		intaker = "";
		date = "";
	}
}
