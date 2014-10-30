package com.ten.beans;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class LearningObjectDetailsBean extends LearningObjectBean {
	byte[] content;
	String fileType;
	
	public String getContent() {
		return Base64.encode(this.content);
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}	
}
