package com.ten.dao.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.graph.Triple;
import com.ten.beans.CourseBean;
import com.ten.beans.LearningObjectBean;
import com.ten.beans.LearningObjectDetailsBean;
import com.ten.dao.interfaces.DbAccessDaoInterface;
import com.ten.triplestore.dao.implementation.TripleStoreConstants;

/**
 * @author Nita Karande
 * This class contains implementation method for access Relational database Mysql and functionality related to it
 */
public class DbAccessDaoImpl implements DbAccessDaoInterface{

	static Logger log = Logger.getLogger(DbAccessDaoImpl.class);
	
	private static DataSource  m_ds = null;
	//create datasource
    static  
    {  
        try
        {
        	Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			m_ds = (DataSource)envContext.lookup(DaoConstants.DB_JNDI_LOOKUP_NAME);
        }
        catch (Exception e)
        {
            log.error(e);
            m_ds = null;
        }
    }
    
    /**
     * get connection to database from connection pooled datasource
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {  
        return m_ds.getConnection();             
    }
	
	@Override
	/**
	 * This method is invoked by uploadImageAction to store the image to database.
	 * Method returns an integer which is the primary key of the image stored in database.
	 * This image is related to its annotations stored in triple store through the image id primary key.
	 */
	public int saveImage(File file, String fileName, String fileType,  boolean annotated) throws Exception
	{
		String LOG_METHOD_NAME = "int saveImage(File, String, String, boolean)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		CallableStatement callableStatement = null;
		
		// declare FileInputStream object to store binary stream of given image.
		FileInputStream fis = null;		
		int image_id = 0;		
		try {
			connection = getConnection();
			
			String sql_call = DaoConstants.INSERT_IMAGE_PROCEDURE_CALL;
			callableStatement = connection.prepareCall(sql_call);
			fis = new FileInputStream(file);
			callableStatement.setBinaryStream(1, (InputStream)fis, (int)(file.length()));
			callableStatement.setString(2, fileType);
			callableStatement.setString(3, fileName);
			callableStatement.setInt(4, annotated?1:0);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			 
			// execute store procedure
			callableStatement.executeUpdate();
			
			image_id = callableStatement.getInt(5);
			
			if(image_id == 0){
				throw new Exception("Image cannot be uploaded");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally{
			// close all the connections.
			connection.close();
			callableStatement.close();
			fis.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return image_id;
	}
	
	@Override
	/**
	 * This method is invoked by annotateImageAction to change the status of image as being annotated.
	 */
	public boolean updateImage(int id) throws Exception
	{
		String LOG_METHOD_NAME = "boolean updateImage(int)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		boolean result = false;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.UPDATE_IMAGE_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, id); //set as annotated
			int rowsChanged = preparedStatement.executeUpdate();
			
			if(rowsChanged == 0){
				throw new Exception("Image cannot be updated");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return result;
	}
	
	@Override
	/**
	 * This method is invoked by annotateVideoAction to change the status of video as being annotated.
	 */
	public boolean updateVideo(int id) throws Exception
	{
		String LOG_METHOD_NAME = "boolean updateVideo(int)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		boolean result = false;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.UPDATE_VIDEO_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 1); //set as annotated
			preparedStatement.setInt(2, id); 
			int rowsChanged = preparedStatement.executeUpdate();
			
			if(rowsChanged == 0){
				throw new Exception("Video cannot be updated");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return result;
	}
	
	@Override
	/**
	 * This method is invoked by annotateAudioAction to change the status of audio as being annotated.
	 */
	public boolean updateAudio(int id) throws Exception
	{
		String LOG_METHOD_NAME = "boolean updateAudio(int)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		boolean result = false;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.UPDATE_AUDIO_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 1); //set as annotated
			preparedStatement.setInt(2, id);
			int rowsChanged = preparedStatement.executeUpdate();
			
			if(rowsChanged == 0){
				throw new Exception("Audio cannot be updated");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return result;
	}

	@Override
	/**
	 * This method is invoked by annotateTextAction to change the status of text as being annotated.
	 */
	public boolean updateText(int id) throws Exception
	{
		String LOG_METHOD_NAME = "boolean updateText(int)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		boolean result = false;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.UPDATE_TEXT_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 1); //set as annotated
			preparedStatement.setInt(2, id);
			int rowsChanged = preparedStatement.executeUpdate();
			
			if(rowsChanged == 0){
				throw new Exception("Text cannot be updated");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return result;
	}
	
	@Override
	/**
	 * This method is invoked by uploadVideoAction to store the video to database.
	 * Method returns an integer which is the primary key of the video stored in database.
	 * This video is related to its annotations stored in triple store through the video id primary key.
	 */
	public int saveVideo(File file, String fileName, String fileType, boolean annotated)
			throws Exception 
	{
		String LOG_METHOD_NAME = "int saveVideo(File, String, String, boolean)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		CallableStatement callableStatement = null;
		
		// declare FileInputStream object to store binary stream of given image.
		FileInputStream fis = null;		
		int video_id = 0;
		
		try {
			connection = getConnection();
			
			String sql_call = DaoConstants.INSERT_VIDEO_PROCEDURE_CALL;
			callableStatement = connection.prepareCall(sql_call);
			fis = new FileInputStream(file);
			callableStatement.setBinaryStream(1, (InputStream)fis, (int)(file.length()));
			callableStatement.setString(2, fileType);
			callableStatement.setString(3, fileName);
			callableStatement.setInt(4, annotated?1:0);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			 
			// execute store procedure
			callableStatement.executeUpdate();
			
			video_id = callableStatement.getInt(5);	
			
			if(video_id == 0){
				throw new Exception("Video cannot be uploaded");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			callableStatement.close();
			fis.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}
		return video_id;
	}

	@Override
	/**
	 * This method is invoked by uploadAudioAction to store the audio to database.
	 * Method returns an integer which is the primary key of the audio stored in database.
	 * This audio is related to its annotations stored in triple store through the audio id primary key.
	 */
	public int saveAudio(File file, String fileName, String fileType, boolean annotated)
			throws Exception 
	{
		String LOG_METHOD_NAME = "int saveAudio(File, String, String, boolean)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		CallableStatement callableStatement = null;
		
		// declare FileInputStream object to store binary stream of given image.
		FileInputStream fis = null;		
		int audio_id = 0;
		
		try {
			connection = getConnection();
			
			String sql_call = DaoConstants.INSERT_AUDIO_PROCEDURE_CALL;
			callableStatement = connection.prepareCall(sql_call);
			fis = new FileInputStream(file);
			callableStatement.setBinaryStream(1, (InputStream)fis, (int)(file.length()));
			callableStatement.setString(2, fileType);
			callableStatement.setString(3, fileName);
			callableStatement.setInt(4, annotated?1:0);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			 
			// execute store procedure
			callableStatement.executeUpdate();
			
			audio_id = callableStatement.getInt(5);		
			if(audio_id == 0){
				throw new Exception("Audio cannot be uploaded");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			callableStatement.close();
			fis.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}
		return audio_id;
	}

	@Override
	/**
	 * This method is invoked by uploadTextAction to store the text to database.
	 * Method returns an integer which is the primary key of the text stored in database.
	 * This text is related to its annotations stored in triple store through the text id primary key.
	 */
	public int saveText(File file, String fileName, String fileType, boolean annotated)
			throws Exception 
	{
		String LOG_METHOD_NAME = "int saveText(File, String, String, boolean)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		CallableStatement callableStatement = null;
		
		// declare FileInputStream object to store binary stream of given image.
		FileInputStream fis = null;		
		int text_id = 0;
		
		try {
			connection = getConnection();
			
			String sql_call = DaoConstants.INSERT_TEXT_PROCEDURE_CALL;
			callableStatement = connection.prepareCall(sql_call);
			fis = new FileInputStream(file);
			callableStatement.setBinaryStream(1, (InputStream)fis, (int)(file.length()));
			callableStatement.setString(2, fileType);
			callableStatement.setString(3, fileName);
			callableStatement.setInt(4, annotated?1:0);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			 
			// execute store procedure
			callableStatement.executeUpdate();
			
			text_id = callableStatement.getInt(5);	
			if(text_id == 0){
				throw new Exception("Text cannot be uploaded");
			}
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			connection.close();
			callableStatement.close();
			fis.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return text_id;
	}

	@Override
	public ArrayList<LearningObjectBean> getUnannotatedImages()
			throws Exception {
		String LOG_METHOD_NAME = "ArrayList<LearningObjectBean> getUnannotatedImages()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		ArrayList<LearningObjectBean> listLearningObjects = new ArrayList<LearningObjectBean>();
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_UNANNOTATED_IMAGES_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				LearningObjectBean learningObjectBean = new LearningObjectBean();
				learningObjectBean.setId(rset.getInt(1));
				learningObjectBean.setFileName(rset.getString(2));
				listLearningObjects.add(learningObjectBean);
		     }    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return listLearningObjects;
	}
	
	@Override
	public LearningObjectDetailsBean getImage(int id)
			throws Exception {
		String LOG_METHOD_NAME = "LearningObjectDetailsBean getImage()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		LearningObjectDetailsBean learningObjectDetailsBean = null;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_IMAGE_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				learningObjectDetailsBean = new LearningObjectDetailsBean();
				learningObjectDetailsBean.setId(id);
				learningObjectDetailsBean.setFileName(rset.getString(1));
				learningObjectDetailsBean.setFileType(rset.getString(2));
				Blob blob = rset.getBlob(3);
				learningObjectDetailsBean.setContent(blob.getBytes(1,(int)blob.length()));
			}    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return learningObjectDetailsBean;
	}
	
	@Override
	public ArrayList<LearningObjectBean> getUnannotatedVideos()
			throws Exception {
		String LOG_METHOD_NAME = "ArrayList<LearningObjectBean> getUnannotatedVideos()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		ArrayList<LearningObjectBean> listLearningObjects = new ArrayList<LearningObjectBean>();
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_UNANNOTATED_VIDEOS_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				LearningObjectBean learningObjectBean = new LearningObjectBean();
				learningObjectBean.setId(rset.getInt(1));
				learningObjectBean.setFileName(rset.getString(2));
				listLearningObjects.add(learningObjectBean);
		     }    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return listLearningObjects;
	}
	
	@Override
	public LearningObjectDetailsBean getVideo(int id)
			throws Exception {
		String LOG_METHOD_NAME = "LearningObjectDetailsBean getVideo()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		LearningObjectDetailsBean learningObjectDetailsBean = null;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_VIDEO_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				learningObjectDetailsBean = new LearningObjectDetailsBean();
				learningObjectDetailsBean.setId(id);
				learningObjectDetailsBean.setFileName(rset.getString(1));
				learningObjectDetailsBean.setFileType(rset.getString(2));
				Blob blob = rset.getBlob(3);
				learningObjectDetailsBean.setContent(blob.getBytes(1,(int)blob.length()));
			}    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return learningObjectDetailsBean;
	}
	
	@Override
	public ArrayList<LearningObjectBean> getUnannotatedAudios()
			throws Exception {
		String LOG_METHOD_NAME = "ArrayList<LearningObjectBean> getUnannotatedAudios()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		ArrayList<LearningObjectBean> listLearningObjects = new ArrayList<LearningObjectBean>();
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_UNANNOTATED_AUDIOS_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				LearningObjectBean learningObjectBean = new LearningObjectBean();
				learningObjectBean.setId(rset.getInt(1));
				learningObjectBean.setFileName(rset.getString(2));
				listLearningObjects.add(learningObjectBean);
		     }    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return listLearningObjects;
	}
	
	@Override
	public LearningObjectDetailsBean getAudio(int id)
			throws Exception {
		String LOG_METHOD_NAME = "LearningObjectDetailsBean getAudio()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		LearningObjectDetailsBean learningObjectDetailsBean = null;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_AUDIO_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				learningObjectDetailsBean = new LearningObjectDetailsBean();
				learningObjectDetailsBean.setId(id);
				learningObjectDetailsBean.setFileName(rset.getString(1));
				learningObjectDetailsBean.setFileType(rset.getString(2));
				Blob blob = rset.getBlob(3);
				learningObjectDetailsBean.setContent(blob.getBytes(1,(int)blob.length()));
			}    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return learningObjectDetailsBean;
	}
	
	@Override
	public ArrayList<LearningObjectBean> getUnannotatedTexts()
			throws Exception {
		String LOG_METHOD_NAME = "ArrayList<LearningObjectBean> getUnannotatedTexts()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		ArrayList<LearningObjectBean> listLearningObjects = new ArrayList<LearningObjectBean>();
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_UNANNOTATED_TEXTS_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				LearningObjectBean learningObjectBean = new LearningObjectBean();
				learningObjectBean.setId(rset.getInt(1));
				learningObjectBean.setFileName(rset.getString(2));
				listLearningObjects.add(learningObjectBean);
		     }    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return listLearningObjects;
	}
	
	@Override
	public LearningObjectDetailsBean getText(int id)
			throws Exception {
		String LOG_METHOD_NAME = "LearningObjectDetailsBean getText()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		LearningObjectDetailsBean learningObjectDetailsBean = null;
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_TEXT_SQL;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				learningObjectDetailsBean = new LearningObjectDetailsBean();
				learningObjectDetailsBean.setId(id);
				learningObjectDetailsBean.setFileName(rset.getString(1));
				learningObjectDetailsBean.setFileType(rset.getString(2));
				Blob blob = rset.getBlob(3);
				learningObjectDetailsBean.setContent(blob.getBytes(1,(int)blob.length()));
			}    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return learningObjectDetailsBean;
	}
	
	@Override
	/**
	 * This method is invoked by CreateCourseAction to store the newly created course to database.
	 * Method returns an integer which is the primary key of the course stored in database.
	 * This course is related to its annotations stored in triple store through the image id primary key.
	 */
	public int insertCourse(String courseName) throws Exception
	{
		String LOG_METHOD_NAME = "int insertCourse(String courseName)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		CallableStatement callableStatement = null;
		
		// declare result variable		
		int course_id = 0;		
		try {
			connection = getConnection();
			
			String sql_call = DaoConstants.INSERT_COURSE_PROCEDURE_CALL;
			callableStatement = connection.prepareCall(sql_call);
			callableStatement.setString(1, courseName);
			callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
			 
			// execute store procedure
			callableStatement.executeUpdate();
			
			course_id = callableStatement.getInt(2);
			if(course_id == 0){
				throw new Exception("Course cannot be saved");
			}
			
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally{
			// close all the connections.
			connection.close();
			callableStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return course_id;
	}

	@Override
	public HashMap<String, LearningObjectDetailsBean> getLearningObjectDetails(
			String type, Set<String> uriSet) throws Exception {
		
		String LOG_METHOD_NAME = "HashMap<String, LearningObjectDetailsBean> getLearningObjectDetails(String type, Set<String> uri)";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		HashMap<String, LearningObjectDetailsBean> returnMap = new HashMap<String, LearningObjectDetailsBean>();
		LearningObjectDetailsBean learningObjectDetails = null;
		int indexOf = 0, id = 0;
		
		try {
			for(String uri:uriSet){
				indexOf = uri.lastIndexOf("#");
				id = Integer.parseInt(uri.substring(indexOf + 1));
				
				if(TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE.equals(type)){
					learningObjectDetails = getImage(id);
				}else if(TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO.equals(type)){		
					learningObjectDetails = getAudio(id);
				}else if(TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO.equals(type)){
					learningObjectDetails = getVideo(id);
				}else if(TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT.equals(type)){
					learningObjectDetails = getText(id);
				}
				returnMap.put(uri, learningObjectDetails);	
			}		
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally{
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}
		return returnMap;
	}

	@Override
	public ArrayList<CourseBean> getCourses() throws Exception {
		String LOG_METHOD_NAME = "ArrayList<CourseBean> getCourses()";
		log.debug(this.getClass() + DaoConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		// declare a connection by using Connection interface 
		Connection connection = null;

		// Declare prepare statement.
		PreparedStatement preparedStatement = null;
		
		// result set
		ResultSet rset = null;
		
		//return result
		ArrayList<CourseBean> listCourseBean = new ArrayList<CourseBean>();
		
		try {
			connection = getConnection();
			
			String sql = DaoConstants.GET_COURSES_SQL;
			preparedStatement = connection.prepareStatement(sql);
			rset = preparedStatement.executeQuery();
			
			while (rset.next ())
		    {   
				CourseBean courseBean = new CourseBean();
				courseBean.setCourseId(rset.getInt(1));
				courseBean.setCourseName(rset.getString(2));
				listCourseBean.add(courseBean);
		     }    
		}catch (Exception ex) {
			// catch if found any exception during rum time.
			log.error(ex);
			throw ex;
		}finally {
			// close all the connections.
			rset.close();
			connection.close();
			preparedStatement.close();
			log.debug(this.getClass() + DaoConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return listCourseBean;
	}
}
