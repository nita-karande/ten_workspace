package com.ten.struts2.actions;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ten.beans.DigitalRightsManagementBean;
import com.ten.dao.implementation.DbAccessDaoImpl;
import com.ten.dao.interfaces.DbAccessDaoInterface;
import com.ten.triplestore.dao.implementation.VirtuosoAccessDaoImpl;
import com.ten.triplestore.dao.interfaces.TriplestoreAccessDaoInterface;
/**
 * 
 * @author Nita Karande
 * This action invoked by upload_text.jsp 
 * It invokes method to upload text to database and store annotations in triplestore
 */
public class UploadTextAction extends ActionSupport{

	static Logger log = Logger.getLogger(UploadTextAction.class);
	
	private static final long serialVersionUID = 1L;
	private File file;
    private String contentType;
    private String fileName;
    private DigitalRightsManagementBean digitalRightsManagementBean;
        
    public void setUpload(File file) {
       this.file = file;
    }

	public File getUpload() {
		return file;
	}
	
    public void setUploadContentType(String contentType) {
       this.contentType = contentType;
    }

	public String getUploadContentType() {
		return contentType;
	}

    public void setUploadFileName(String filename) {
       this.fileName = filename;
    }

	public String getUploadFileName() {
		return fileName;
	}

	public DigitalRightsManagementBean getDigitalRightsManagementBean() {
		return digitalRightsManagementBean;
	}

	public void setDigitalRightsManagementBean(
			DigitalRightsManagementBean digitalRightsManagementBean) {
		this.digitalRightsManagementBean = digitalRightsManagementBean;
	}

	/**
	 * This method is configured to be invoked in struts.xml, for text file uploading and annotations.
	 * It makes calls to mysql dao implementation to store the uploaded file to database
	 * It also stores annotations for text in triple store 
	 */
	public String execute() throws Exception {
		//Get request method invoked
		HttpServletRequest request = ServletActionContext.getRequest();
		String method = request.getMethod();
		String result = ActionConstants.FORWARD_SHOWJSP;
		
		if(ActionConstants.METHOD_POST.equalsIgnoreCase(method)){
			try{
				//Insert text to RDBMS database
				DbAccessDaoInterface dbAccessDaoInterface = new DbAccessDaoImpl();
				int textId = dbAccessDaoInterface.saveText(this.file, this.fileName, this.contentType, false);
				
				//Insert annotation data in Triplestore
				TriplestoreAccessDaoInterface tdbAccessDaoInterface = new VirtuosoAccessDaoImpl();
				tdbAccessDaoInterface.insertTextDigitalRightsManagementData(this.digitalRightsManagementBean, textId);
							
				//File uploaded successfully
				addActionMessage(ActionConstants.FILE_UPLOAD_SUCCESS_MSG);
				result = ActionConstants.FORWARD_SUCCESS;
			}catch(Exception ex){
				log.error(ex);
				reset();				
				addActionError(ActionConstants.FILE_UPLOAD_ERROR_MSG);
				result = ActionConstants.FORWARD_INPUT;
			}           
		}else if(ActionConstants.METHOD_GET.equalsIgnoreCase(method)){	
			reset();
			result = ActionConstants.FORWARD_SHOWJSP;;
		}
		return result;
	}	
	
	public void reset(){
		this.contentType = "";
		this.fileName = "";
		this.file= null;
		digitalRightsManagementBean = new DigitalRightsManagementBean();
	}
}
