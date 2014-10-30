package com.ten.triplestore.dao.implementation;

import java.util.ArrayList;

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
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.ten.beans.DigitalRightsManagementBean;
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
			Node image = NodeFactory.createURI(TripleStoreConstants.URI_IMAGE + imageId);
						
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
			Node video = NodeFactory.createURI(TripleStoreConstants.URI_VIDEO + videoId);
						
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
			Node audio = NodeFactory.createURI(TripleStoreConstants.URI_AUDIO + audioId);
				
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
			Node text = NodeFactory.createURI(TripleStoreConstants.URI_TEXT + textId);
						
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
	public void queryLearningObject(String learningObjectType) throws Exception{
		
		String LOG_METHOD_NAME = "void queryLearningObject(String)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		try{
			String sparqlQueryString = TripleStoreConstants.PREFIX_TEN_ONTOLOGY 
					    + TripleStoreConstants.PREFIX_TEN_IMAGE			
						+ " SELECT ?learning_object " +
						" WHERE { " +
	                     "   ?learning_object " +
	                     "     a " +
	                     "   TenOntology:" +
	                     learningObjectType +
	                     " }";			
			
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//STEP 2 - Create query
			Query sparql = QueryFactory.create(sparqlQueryString);
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode rdfNode = result.get("learning_object");
			    log.debug(graph + " { " + rdfNode + "  }");
			}
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
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
			Node image = NodeFactory.createURI(TripleStoreConstants.URI_IMAGE + imageId);
			Node predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = NodeFactory.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_IMAGE_OBJECT);
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
			Node video = NodeFactory.createURI(TripleStoreConstants.URI_VIDEO + videoId);
			Node predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = NodeFactory.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_VIDEO_OBJECT);
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
			Node audio = NodeFactory.createURI(TripleStoreConstants.URI_AUDIO + audioId);
			Node predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = NodeFactory.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_AUDIO_OBJECT);
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
			Node text = NodeFactory.createURI(TripleStoreConstants.URI_TEXT + textId);
			Node predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_RDF_TYPE);
			Node predicate_value = NodeFactory.createURI(TripleStoreConstants.URI_TEN_ONTOLOGY_TEXT_OBJECT);
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
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_TITLE);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getTitle());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//Subject
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getSubject())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_SUBJECT);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getSubject());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//description
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getDescription())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_DESCRIPTION);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getDescription());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//source
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getSource())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_SOURCE);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getSource());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//language	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getLanguage())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_LANGUAGE);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getLanguage());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//relation	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getRelation())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_RELATION);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getRelation());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//coverage		
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getCoverage())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_COVERAGE);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getCoverage());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//ADMINISTRATIVE
			//creator
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getCreator())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_CREATOR);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getCreator());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//publisher	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getPublisher())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_PUBLISHER);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getPublisher());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//contributor	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getContributor())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_CONTRIBUTOR);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getContributor());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rights	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getRights())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_RIGHTS);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getRights());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//STRUCTURAL	
			//date		
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getDate())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_DATE);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getDate());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//type	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getType())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_TYPE);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getType());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//format
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getFormat())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_FORMAT);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getFormat());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//identifier	
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getIdentifier())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_DC_IDENTIFIER);
				predicate_value = NodeFactory.createURI(digitalRightsManagementBean.getIdentifier());
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
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_MODIFIED);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getModified());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rightsHolder	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getRightsHolder())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_RIGHTS_HOLDER);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getRightsHolder());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//isPartOf	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getIsPartOf())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_ISPARTOF);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getIsPartOf());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//hasPart	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getHasPart())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_COMMON_HASPART);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getHasPart());
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
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_TRIBE);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getTribe());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//category	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getCategory())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_CATEGORY);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getCategory());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//topic theme
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getTopicTheme())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_TOPIC_THEME);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getTopicTheme());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//subtopic theme
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getSubTopicTheme())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_SUBTPIC_THEME);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getSubTopicTheme());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//rating
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getRating())){
				predicate = NodeFactory.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_RATING);
				predicate_value = NodeFactory.createURI(tenLearningObjectAnnotationsBean.getRating());
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
}
