package com.ten.dao.implementation;

/**
 * @author Nita Karande
 * This class contains constants used in dao classes
 */
public interface DaoConstants {
	public static final String DB_JNDI_LOOKUP_NAME = "jdbc/TenDB";
	
	public static final String INSERT_IMAGE_PROCEDURE_CALL = "call INSERT_IMAGE(?,?,?,?,?)";
	
	public static final String INSERT_VIDEO_PROCEDURE_CALL = "call INSERT_VIDEO(?,?,?,?,?)";
	
	public static final String INSERT_AUDIO_PROCEDURE_CALL = "call INSERT_AUDIO(?,?,?,?,?)";
	
	public static final String INSERT_TEXT_PROCEDURE_CALL = "call INSERT_TEXT(?,?,?,?,?)";
	
	public static final String INSERT_COURSE_PROCEDURE_CALL = "call INSERT_COURSE(?,?)";
	
	public static final String GET_UNANNOTATED_IMAGES_SQL = "select IMAGE_ID, FILE_NAME from IMAGES_TABLE where ANNOTATED=?";
	
	public static final String GET_IMAGE_SQL = "select FILE_NAME, FILE_TYPE, CONTENT from IMAGES_TABLE where IMAGE_ID=?";	
	
	public static final String GET_UNANNOTATED_VIDEOS_SQL = "select VIDEO_ID, FILE_NAME from VIDEOS_TABLE where ANNOTATED=?";
	
	public static final String GET_VIDEO_SQL = "select FILE_NAME, FILE_TYPE, CONTENT from VIDEOS_TABLE where VIDEO_ID=?";	
	
	public static final String GET_UNANNOTATED_AUDIOS_SQL = "select AUDIO_ID, FILE_NAME from AUDIOS_TABLE where ANNOTATED=?";
	
	public static final String GET_AUDIO_SQL = "select FILE_NAME, FILE_TYPE, CONTENT from AUDIOS_TABLE where AUDIO_ID=?";
		
	public static final String GET_UNANNOTATED_TEXTS_SQL = "select TEXT_ID, FILE_NAME from TEXTS_TABLE where ANNOTATED=?";
	
	public static final String GET_TEXT_SQL = "select FILE_NAME, FILE_TYPE, CONTENT from TEXTS_TABLE where TEXT_ID=?";
	
	public static final String GET_COURSES_SQL = "select COURSE_ID, NAME from COURSES_TABLE";
	
	public static final String UPDATE_IMAGE_SQL = "update IMAGES_TABLE set ANNOTATED=? where IMAGE_ID=?";
	
	public static final String UPDATE_AUDIO_SQL = "update AUDIOS_TABLE set ANNOTATED=? where AUDIO_ID=?";
	
	public static final String UPDATE_VIDEO_SQL = "update VIDEOS_TABLE set ANNOTATED=? where VIDEO_ID=?";
	
	public static final String UPDATE_TEXT_SQL = "update TEXTS_TABLE set ANNOTATED=? where TEXT_ID=?";	
	
	public static final String LOG_BEGIN = " Begin ";
	
	public static final String LOG_END = " End ";
}