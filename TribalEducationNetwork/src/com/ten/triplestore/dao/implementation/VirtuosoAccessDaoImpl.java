package com.ten.triplestore.dao.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import virtuoso.jdbc3.VirtuosoConnectionPoolDataSource;
import virtuoso.jdbc3.VirtuosoDataSource;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.ten.beans.CourseAnnotationsBean;
import com.ten.beans.DigitalRightsManagementBean;
import com.ten.beans.StudentAnnotationsBean;
import com.ten.beans.TenLearningObjectAnnotationsBean;
import com.ten.triplestore.dao.interfaces.TriplestoreAccessDaoInterface;
import com.ten.utils.Utils;

/**
 * @author Nita Karande
 * This class contains implementation methods for accessing triple store and functionality related to it
 */
public class VirtuosoAccessDaoImpl implements TriplestoreAccessDaoInterface{
	static Logger log = Logger.getLogger(VirtuosoAccessDaoImpl.class);
	
	private static DataSource  m_ds = null;
	//create datasource
    static  
    {  
        try
        {
        	Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			m_ds = (VirtuosoConnectionPoolDataSource)envContext.lookup(TripleStoreConstants.VIRTUOSO_JNDI_LOOKUP_NAME);
        }
        catch (Exception e)
        {
            log.error(e);
            m_ds = null;
        }
    }
	
	@Override
	/**
	 * This method is invoked by uploadImageAction to store annotations of image in triple store
	 * imageId passed as parameter forms part of URI to uniquely identify the image in triplestore
	 * imageId is the primary key id created while storing the image in database
	 */
	public boolean insertImageAnnotations(TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean, int imageId) throws Exception
	{		
		String LOG_METHOD_NAME = "insertImageAnnotations(TenLearningObjectAnnotationsBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node image = Node.createURI(TripleStoreConstants.URI_IMAGE + imageId);
						
			//add ten common annotation triples
			tripleList.addAll(createTenCommonAnnotationTriples(image, tenLearningObjectAnnotationsBean));
			
			//add ten annotation triples
			tripleList.addAll(createTenLearningObjectAnnotationTriples(image, tenLearningObjectAnnotationsBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}

	@Override
	/**
	 * This method is invoked by uploadVideoAction to store annotations of video in triple store
	 * videoId passed as parameter forms part of URI to uniquely identify the video in triplestore
	 * videoId is the primary key id created while storing the video in database
	 */
	public boolean insertVideoAnnotations(
			TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean,
			int videoId) throws Exception {
		String LOG_METHOD_NAME = "insertVideoAnnotations(TenLearningObjectAnnotationsBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		VirtGraph graph = null;
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			VirtuosoDataSource ds = (VirtuosoConnectionPoolDataSource )envContext.lookup(TripleStoreConstants.VIRTUOSO_JNDI_LOOKUP_NAME);
			
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node video = Node.createURI(TripleStoreConstants.URI_VIDEO + videoId);
						
			//add ten common annotation triples
			tripleList.addAll(createTenCommonAnnotationTriples(video, tenLearningObjectAnnotationsBean));
			
			//add ten annotation triples
			tripleList.addAll(createTenLearningObjectAnnotationTriples(video, tenLearningObjectAnnotationsBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}

	@Override
	/**
	 * This method is invoked by uploadAudioAction to store annotations of audio in triple store
	 * audioId passed as parameter forms part of URI to uniquely identify the audio in triplestore
	 * audioId is the primary key id created while storing the audio in database
	 */
	public boolean insertAudioAnnotations(
			TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean,
			int audioId) throws Exception {
		
		String LOG_METHOD_NAME = "insertAudioAnnotations(TenLearningObjectAnnotationsBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			VirtuosoDataSource ds = (VirtuosoConnectionPoolDataSource )envContext.lookup(TripleStoreConstants.VIRTUOSO_JNDI_LOOKUP_NAME);
			
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node audio = Node.createURI(TripleStoreConstants.URI_AUDIO + audioId);
				
			//add ten common annotation triples
			tripleList.addAll(createTenCommonAnnotationTriples(audio, tenLearningObjectAnnotationsBean));
			
			//add ten annotation triples
			tripleList.addAll(createTenLearningObjectAnnotationTriples(audio, tenLearningObjectAnnotationsBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}

	@Override
	/**
	 * This method is invoked by uploadTextAction to store annotations of text/document in triple store
	 * textId passed as parameter forms part of URI to uniquely identify the text/document in triplestore
	 * textId is the primary key id created while storing the text/document in database
	 */
	public boolean insertTextAnnotations(
			TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean,
			int textId) throws Exception {
		String LOG_METHOD_NAME = "insertTextAnnotations(TenLearningObjectAnnotationsBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node text = Node.createURI(TripleStoreConstants.URI_TEXT + textId);
						
			//add ten common annotation triples
			tripleList.addAll(createTenCommonAnnotationTriples(text, tenLearningObjectAnnotationsBean));
			
			//add ten annotation triples
			tripleList.addAll(createTenLearningObjectAnnotationTriples(text, tenLearningObjectAnnotationsBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}
	
	@Override
	/**
	 * This method is used to query triple store for 
	 */
	/*public ArrayList<String> queryLearningObject(String learningObjectType, ArrayList<String> orSearchTerms, ArrayList<String> andSearchTerms) throws Exception{
		
		String LOG_METHOD_NAME = "void queryLearningObject(String)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		ArrayList<String> returnValue = new ArrayList<String>();
		
		try{
		
			StringBuffer sparqlQueryString = new StringBuffer();
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_ONTOLOGY);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_IMAGE);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_AUDIO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_VIDEO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_TEXT);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_RDF);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_DUBLIN_CORE);
			
			sparqlQueryString.append(" SELECT ?learning_object ?predicate ?object ");
			sparqlQueryString.append(" WHERE { ");
			sparqlQueryString.append(" ?learning_object a TenOntology:");
			sparqlQueryString.append(learningObjectType);	
			sparqlQueryString.append(" ;?predicate ?object .");	
			
			//add filter for orSearchTerms
			sparqlQueryString.append(" FILTER (");				
			int i=0;
	        for(String searchTerm:orSearchTerms){
	        	sparqlQueryString.append("(regex(?object, \"");
	        	sparqlQueryString.append(searchTerm);
	        	sparqlQueryString.append("\", \"i\"))");
	        	//if this is not the last element add ||
	        	if(i != (orSearchTerms.size()-1)){
	        		sparqlQueryString.append(" || ");
	        	}
	        	i++;
	        }	
	        //exclude type predicate
	        sparqlQueryString.append(" && (?predicate != rdf:type)");
	        
	        //add filter for ann search terms
	        if((andSearchTerms != null) && (andSearchTerms.size()>0)){
	        	i=0;
	        	sparqlQueryString.append(" && ");
	        	for(String searchTerm:andSearchTerms){
		        	sparqlQueryString.append("(regex(?object, \"");
		        	sparqlQueryString.append(searchTerm);
		        	sparqlQueryString.append("\", \"i\"))");
		        	//if this is not the last element add ||
		        	if(i != (andSearchTerms.size()-1)){
		        		sparqlQueryString.append(" && ");
		        	}
		        	i++;
		        }	
	        }
			sparqlQueryString.append(" ) .}");
			
			log.debug("SEARCH QUERY:  " + sparqlQueryString.toString());
			
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//STEP 2 - Create query
			Query sparql = QueryFactory.create(sparqlQueryString.toString());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode rdfNode = result.get("learning_object");
			    returnValue.add(rdfNode.toString());
			    log.debug(graph + " { " + rdfNode + "  }");
			}
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		return returnValue;
	}*/
	
public ArrayList<String> queryLearningObject(String learningObjectType, ArrayList<String> orSearchTerms, ArrayList<String> andSearchTerms) throws Exception{
		
		String LOG_METHOD_NAME = "void queryLearningObject(String)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		ArrayList<String> returnValue = new ArrayList<String>();
		
		try{
		
			StringBuffer sparqlQueryString = new StringBuffer();
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_ONTOLOGY);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_IMAGE);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_AUDIO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_VIDEO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_TEXT);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_RDF);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_DUBLIN_CORE);
			
			sparqlQueryString.append(" SELECT ?learning_object ?predicate ?object ");
			sparqlQueryString.append(" WHERE { ");
			sparqlQueryString.append(" ?learning_object a TenOntology:");
			sparqlQueryString.append(learningObjectType);	
			sparqlQueryString.append(" ;?predicate ?object");	
			
			//add predicate object for each and condition
			for(int i=1; i==andSearchTerms.size(); i++){
				sparqlQueryString.append("; ?predicate" + i + " ?object" + i);
			}
			
			//exclude type predicate
			sparqlQueryString.append(" . FILTER (");
	        sparqlQueryString.append(" (?predicate != rdf:type)");
	        
			//add filter for orSearchTerms	
	        int i = 0;
	        if((orSearchTerms != null) && (orSearchTerms.size()>0)){
	        	i=0;
	        	sparqlQueryString.append(" && ");
		        for(String searchTerm:orSearchTerms){
		        	sparqlQueryString.append("(regex(?object, \"");
		        	sparqlQueryString.append(searchTerm);
		        	sparqlQueryString.append("\", \"i\"))");
		        	//if this is not the last element add ||
		        	if(i != (orSearchTerms.size()-1)){
		        		sparqlQueryString.append(" || ");
		        	}
		        	i++;
		        }	
	        }
	        
	        //add filter for and search terms
	        if((andSearchTerms != null) && (andSearchTerms.size()>0)){
	        	i=0;
	        	sparqlQueryString.append(" && ");
	        	for(String searchTerm:andSearchTerms){
	        		sparqlQueryString.append("(regex(?object" + (i+1) + ", \"");
	        		sparqlQueryString.append(searchTerm);
	        		sparqlQueryString.append("\", \"i\"))");
		        	//if this is not the last element add ||
		        	if(i != (andSearchTerms.size()-1)){
		        		sparqlQueryString.append(" && ");
		        	}
		        	i++;
		        }	
	        }
	        
	        sparqlQueryString.append(" ) .}");
			
			log.debug("SEARCH QUERY:  " + sparqlQueryString.toString());
			
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//STEP 2 - Create query
			Query sparql = QueryFactory.create(sparqlQueryString.toString());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode rdfNode = result.get("learning_object");
			    returnValue.add(rdfNode.toString());
			    log.debug(graph + " { " + rdfNode + "  }");
			}
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		return returnValue;
	}

	@Override
	public boolean insertImageDigitalRightsManagementData(
			DigitalRightsManagementBean digitalRightsManagementBean, int imageId)
			throws Exception {
		String LOG_METHOD_NAME = "insertImageDigitalRightsManagementData(DigitalRightsManagementBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node image = Node.createURI(TripleStoreConstants.URI_IMAGE + imageId);
			Node predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = Node.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_IMAGE_OBJECT);
			tripleList.add(new Triple(image, predicate, predicate_value));
			
			//add dc annotation tuples
			tripleList.addAll(createDigitalRightsAnnotationTriples(image, digitalRightsManagementBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}

	@Override
	public boolean insertVideoDigitalRightsManagementData(
			DigitalRightsManagementBean digitalRightsManagementBean, int videoId)
			throws Exception {
		String LOG_METHOD_NAME = "insertVideoDigitalRightsManagementData(digitalRightsManagementBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		VirtGraph graph = null;
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			VirtuosoDataSource ds = (VirtuosoConnectionPoolDataSource )envContext.lookup(TripleStoreConstants.VIRTUOSO_JNDI_LOOKUP_NAME);
			
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node video = Node.createURI(TripleStoreConstants.URI_VIDEO + videoId);
			Node predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = Node.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_VIDEO_OBJECT);
			tripleList.add(new Triple(video, predicate, predicate_value));
			
			//add dc annotation tuples
			tripleList.addAll(createDigitalRightsAnnotationTriples(video, digitalRightsManagementBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}

	@Override
	public boolean insertAudioDigitalRightsManagementData(
			DigitalRightsManagementBean digitalRightsManagementBean, int audioId)
			throws Exception {
		String LOG_METHOD_NAME = "insertAudioDigitalRightsManagementData(DigitalRightsManagementBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			VirtuosoDataSource ds = (VirtuosoConnectionPoolDataSource )envContext.lookup(TripleStoreConstants.VIRTUOSO_JNDI_LOOKUP_NAME);
			
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node audio = Node.createURI(TripleStoreConstants.URI_AUDIO + audioId);
			Node predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = Node.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_AUDIO_OBJECT);
			tripleList.add(new Triple(audio, predicate, predicate_value));
			
			//add dc annotation tuples
			tripleList.addAll(createDigitalRightsAnnotationTriples(audio, digitalRightsManagementBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}

	@Override
	public boolean insertTextDigitalRightsManagementData(
			DigitalRightsManagementBean digitalRightsManagementBean, int textId)
			throws Exception {
		String LOG_METHOD_NAME = "insertTextDigitalRightsManagementData(DigitalRightsManagementBean, int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node text = Node.createURI(TripleStoreConstants.URI_TEXT + textId);
			Node predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = Node.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_TEXT_OBJECT);
			tripleList.add(new Triple(text, predicate, predicate_value));
			
			//add dc annotation tuples
			tripleList.addAll(createDigitalRightsAnnotationTriples(text, digitalRightsManagementBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}
	
	
	/**
	 * This method is invoked for creating Dublin core annotation triples of images,audios,videos and text
	 * It creates an arraylist containing triples for the the learning object dublic core annotations, to be inserted in triple store.
	 */
	public ArrayList<Triple> createDigitalRightsAnnotationTriples(Node subject ,DigitalRightsManagementBean digitalRightsManagementBean){
		
		String LOG_METHOD_NAME = "ArrayList createDigitalRightsAnnotationTriples(Node, DigitalRightsManagementBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		try{
			//DESCRIPTIVE
			//title
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getTitle())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_TITLE);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getTitle());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//Subject
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getSubject())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_SUBJECT);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getSubject());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//description
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getDescription())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_DESCRIPTION);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getDescription());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//source
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getSource())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_SOURCE);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getSource());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//language	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getLanguage())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_LANGUAGE);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getLanguage());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//relation	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getRelation())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_RELATION);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getRelation());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//coverage		
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getCoverage())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_COVERAGE);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getCoverage());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//ADMINISTRATIVE
			//creator
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getCreator())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_CREATOR);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getCreator());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//publisher	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getPublisher())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_PUBLISHER);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getPublisher());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//contributor	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getContributor())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_CONTRIBUTOR);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getContributor());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rights	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getRights())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_RIGHTS);
				predicate_value = Node.createURI(digitalRightsManagementBean.getRights());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//STRUCTURAL	
			//date		
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getDate())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_DATE);
				predicate_value = Node.createURI(digitalRightsManagementBean.getDate());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//type	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getType())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_TYPE);
				predicate_value = Node.createURI(digitalRightsManagementBean.getType());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//format
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getFormat())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_FORMAT);
				predicate_value = Node.createURI(digitalRightsManagementBean.getFormat());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//identifier	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getIdentifier())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_IDENTIFIER);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getIdentifier());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}			
		}catch(Exception ex)
		{
			log.error(ex);
			throw ex;
		}finally{
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		
		return tripleList;
	}
	
	/**
	 * This method is invoked for creating annotations defined by Tribal Education Network Ontology, and those common for learning objects 
	 * as well as other elearning objects like courses, pages etc.
	 * It creates an arraylist containing triples for annotations common to all the objects in ten, to be inserted in triple store
	 */
	public ArrayList<Triple> createTenCommonAnnotationTriples(Node subject ,TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean){
		
		String LOG_METHOD_NAME = "ArrayList createTenCommonAnnotationTriples(Node, TenLearningObjectAnnotationsBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		
		try{
			//modified
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getModified())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_MODIFIED);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getModified());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rightsHolder	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getRightsHolder())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_RIGHTS_HOLDER);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getRightsHolder());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//isPartOf	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getIsPartOf())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_ISPARTOF);
				predicate_value = Node.createURI(tenLearningObjectAnnotationsBean.getIsPartOf());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//hasPart	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getHasPart())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_HASPART);
				predicate_value = Node.createURI(tenLearningObjectAnnotationsBean.getHasPart());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}			
		}catch(Exception ex)
		{
			log.error(ex);
			throw ex;
		}finally{
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		
		return tripleList;
	}
	
	/**
	 * This method is invoked for creating annotations defined by Tribal Education Network Ontology, and those specific to learning objects. 
	 * Creates an arraylist containing triples specific to ten learning objects, to be inserted in triple store
	 */
	public ArrayList<Triple> createTenLearningObjectAnnotationTriples(Node subject ,TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean){
		
		String LOG_METHOD_NAME = "ArrayList createTenLearningObjectAnnotationTriples(Node, TenLearningObjectAnnotationsBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		try{
			//tribe
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getTribe())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_TRIBE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getTribe());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//category	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getCategory())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_CATEGORY);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getCategory());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//topic theme
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getTopicTheme())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_TOPIC_THEME);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getTopicTheme());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//subtopic theme
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getSubTopicTheme())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_SUBTPIC_THEME);
				predicate_value = Node.createURI(tenLearningObjectAnnotationsBean.getSubTopicTheme());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rating
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getRating())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_RATING);
				predicate_value = Node.createURI(tenLearningObjectAnnotationsBean.getRating());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}			
		}catch(Exception ex)
		{
			log.error(ex);
			throw ex;
		}finally{
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		
		return tripleList;
	}
	
	/**
	 * 
	 */
	public boolean insertCourseAnnotations(CourseAnnotationsBean courseAnnotationsBean, int courseId) throws Exception{
		String LOG_METHOD_NAME = "boolean insertCourseAnnotations(CourseAnnotationsBean , int)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		VirtGraph graph = null;
		try{
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
	
			ArrayList<Triple> tripleList = new ArrayList<Triple>();			
			Node course = Node.createURI(TripleStoreConstants.URI_COURSE + courseId);
			Node predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = Node.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_COURSE_OBJECT);
			tripleList.add(new Triple(course, predicate, predicate_value));
			
			//add course annotation tuples
			tripleList.addAll(createCourseAnnotationTriples(course, courseAnnotationsBean));
			
			for(int i=0; i<tripleList.size(); i++){
				graph.add(tripleList.get(i));
			}
		
			//End transaction
			graph.getTransactionHandler().commit();
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
	   	return true;
	}
	
	/**
	 * This method is invoked for creating annotations defined by Tribal Education Network Ontology, and those common for learning objects 
	 * as well as other elearning objects like courses, pages etc.
	 * It creates an arraylist containing triples for annotations common to all the objects in ten, to be inserted in triple store
	 */
	public ArrayList<Triple> createCourseAnnotationTriples(Node subject ,CourseAnnotationsBean courseAnnotationBean){
		
		String LOG_METHOD_NAME = "ArrayList<Triple> createCourseAnnotationTriples(Node subject ,CourseAnnotationsBean courseAnnotationBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		
		try{
			
			//modified
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getModified())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_MODIFIED);
				predicate_value = Node.createLiteral(courseAnnotationBean.getModified());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rightsHolder	
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getRightsHolder())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_RIGHTS_HOLDER);
				predicate_value = Node.createLiteral(courseAnnotationBean.getRightsHolder());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//isPartOf	
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getIsPartOf())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_ISPARTOF);
				predicate_value = Node.createLiteral(courseAnnotationBean.getIsPartOf());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//hasPart	
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getHasPart())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_HASPART);
				predicate_value = Node.createLiteral(courseAnnotationBean.getHasPart());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}	
			
			//creator
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getCreator())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_CREATOR);
				predicate_value = Node.createLiteral(courseAnnotationBean.getCreator());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//description 
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getHasPart())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_DESCRIPTION);
				predicate_value = Node.createURI(courseAnnotationBean.getHasPart());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//keywords
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getKeywords())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_KEYWORDS);
				predicate_value = Node.createURI(courseAnnotationBean.getKeywords());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
		}catch(Exception ex)
		{
			log.error(ex);
			throw ex;
		}finally{
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		
		return tripleList;
	}

	@Override
	public HashMap<String, TenLearningObjectAnnotationsBean> searchLearningObjects(
			String type, String keywords, String andSearchTerms) throws Exception {
		HashMap<String, TenLearningObjectAnnotationsBean> returnMap = new HashMap<String, TenLearningObjectAnnotationsBean>();
		TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean = null;
		
		String LOG_METHOD_NAME = "HashMap<String, TenLearningObjectAnnotationsBean> searchLearningObjects(String type, String keywords)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		VirtGraph graph = null;
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			VirtuosoDataSource ds = (VirtuosoConnectionPoolDataSource )envContext.lookup(TripleStoreConstants.VIRTUOSO_JNDI_LOOKUP_NAME);
			
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, ds);
			
			//separate the search terms
			ArrayList<String> orSearchTerms = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(keywords);
			while (st.hasMoreTokens()) {
				orSearchTerms.add(st.nextToken().trim());
			}
			
			ArrayList<String> andSearchTermsList = new ArrayList<String>();
			StringTokenizer andSt = new StringTokenizer(andSearchTerms);
			while (andSt.hasMoreTokens()) {
				andSearchTermsList.add(andSt.nextToken().trim());
			}
			
			ArrayList<String> uris = new ArrayList<String>();		
			if (TripleStoreConstants.SEARCH_IMAGE.equals(type)){
				uris = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE, orSearchTerms, andSearchTermsList);				
			}else if (TripleStoreConstants.SEARCH_AUDIO.equals(type)){
				uris = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO, orSearchTerms, andSearchTermsList);	
			}else if (TripleStoreConstants.SEARCH_VIDEO.equals(type)){
				uris = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO, orSearchTerms, andSearchTermsList);	
			}else if (TripleStoreConstants.SEARCH_TEXT.equals(type)){
				uris = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT, orSearchTerms, andSearchTermsList);	
			}else{
				//throw new Exception("Invalid search type"); or search all
			}	
			
			for(String uri: uris){
				tenLearningObjectAnnotationsBean = new TenLearningObjectAnnotationsBean();
				returnMap.put(uri, tenLearningObjectAnnotationsBean);
			}
		}catch (Exception ex) {
			if(graph!=null){
				graph.getTransactionHandler().abort();
			}
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}		
		return returnMap;
	}

	@Override
	public CourseAnnotationsBean getCourseAnnotations(int courseId)
			throws Exception {
		String LOG_METHOD_NAME = "CourseAnnotationsBean getCourseAnnotations(int courseId)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		CourseAnnotationsBean courseAnnotationsBean = new CourseAnnotationsBean();
		
		try{		
			StringBuffer sparqlQueryString = new StringBuffer();
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_ONTOLOGY);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_IMAGE);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_AUDIO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_VIDEO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_TEXT);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_RDF);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_DUBLIN_CORE);
			
			sparqlQueryString.append(" SELECT ?predicate ?object ");
			sparqlQueryString.append(" WHERE { ");
			sparqlQueryString.append("<" + TripleStoreConstants.URI_COURSE + courseId + ">");
			sparqlQueryString.append(" a TenOntology:");
			sparqlQueryString.append(TripleStoreConstants.TYPE_COURSE);	
			sparqlQueryString.append(" ;?predicate ?object .");			
			sparqlQueryString.append(" }");
			
			log.debug("SEARCH QUERY:  " + sparqlQueryString.toString());
			
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//STEP 2 - Create query
			Query sparql = QueryFactory.create(sparqlQueryString.toString());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode predicateNode = result.get("predicate");
			    RDFNode objectNode = result.get("object");
			    
			    log.debug(graph + " { " + predicateNode + " ; " + objectNode + "  }");
			    
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_MODIFIED)){
			    	//modified
			    	courseAnnotationsBean.setModified(objectNode.toString());
			    }else if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_RIGHTS_HOLDER)){
			    	//rights holder
			    	courseAnnotationsBean.setRightsHolder(objectNode.toString());
			    }else if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_ISPARTOF)){
			    	//isPartOf
			    	courseAnnotationsBean.setIsPartOf(objectNode.toString());
			    }else if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_HASPART)){
			    	//hasPart
			    	courseAnnotationsBean.setHasPart(objectNode.toString());
			    }else if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_DC_CREATOR)){
			    	//creator
			    	courseAnnotationsBean.setCreator(objectNode.toString());
			    }else if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_DC_DESCRIPTION)){
			    	//description
			    	courseAnnotationsBean.setDescription(objectNode.toString());
			    }else if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_KEYWORDS)){
			    	//keywords
			    	courseAnnotationsBean.setKeywords(objectNode.toString());
			    }
			}
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		return courseAnnotationsBean;
	}

	@Override
	public StudentAnnotationsBean getStudentAnnotations(String user_name)
			throws Exception {
		String LOG_METHOD_NAME = "CourseAnnotationsBean getStudentAnnotations(String user_name)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		StudentAnnotationsBean studentAnnotationsBean = new StudentAnnotationsBean();
		
		try{		
			StringBuffer sparqlQueryString = new StringBuffer();
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_ONTOLOGY);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_IMAGE);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_AUDIO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_VIDEO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_TEXT);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_RDF);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_DUBLIN_CORE);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_STUDENT);
			
			sparqlQueryString.append(" SELECT ?predicate ?object ");
			sparqlQueryString.append(" WHERE { ");
			sparqlQueryString.append("<" + TripleStoreConstants.URI_STUDENT + user_name + ">");
			sparqlQueryString.append(" a TenOntology:");
			sparqlQueryString.append(TripleStoreConstants.TYPE_STUDENT);	
			sparqlQueryString.append(" ;?predicate ?object .");			
			sparqlQueryString.append(" }");
			
			log.debug("SEARCH QUERY:  " + sparqlQueryString.toString());
			
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//STEP 2 - Create query
			Query sparql = QueryFactory.create(sparqlQueryString.toString());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode predicateNode = result.get("predicate");
			    RDFNode objectNode = result.get("object");
			    
			    log.debug(graph + " { " + predicateNode + " ; " + objectNode + "  }");
			    
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_LO_TRIBE)){
			    	//tribe
			    	studentAnnotationsBean.setTribe(objectNode.toString());
			    }
			}
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		return studentAnnotationsBean;
	}
}
