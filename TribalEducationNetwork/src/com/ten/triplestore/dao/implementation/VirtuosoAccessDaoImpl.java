package com.ten.triplestore.dao.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

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
					
			graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI,m_ds);
			
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
			
			sparqlQueryString.append(" SELECT DISTINCT ?learning_object ");
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
	
/*public HashMap<String, ArrayList<String>> queryRecommendedLearningObjects(StudentAnnotationsBean studentAnnotationsBean, String keywords) throws Exception{
		
		String LOG_METHOD_NAME = "void queryLearningObject(StudentAnnotationsBean studentAnnotationsBean, ArrayList<String> orSearchTerms)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		ArrayList<String> imagesList = new ArrayList<String>();
		ArrayList<String> audioList = new ArrayList<String>();
		ArrayList<String> videoList = new ArrayList<String>();
		ArrayList<String> textList = new ArrayList<String>();
		HashMap<String, ArrayList<String>> returnMap = new HashMap<String, ArrayList<String>>();
		try{
		
			StringBuffer sparqlQueryString = new StringBuffer();
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_ONTOLOGY);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_IMAGE);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_AUDIO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_VIDEO);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_TEXT);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_RDF);
			sparqlQueryString.append(TripleStoreConstants.PREFIX_DUBLIN_CORE);
			
			sparqlQueryString.append(" SELECT DISTINCT ?learning_object ?learningObjectType ");
			sparqlQueryString.append(" WHERE { ");
			sparqlQueryString.append(" ?learning_object a ?learningObjectType");
			sparqlQueryString.append(" ;?predicate ?object");	
			
			//exclude type predicate
			sparqlQueryString.append(" . FILTER (");
	        sparqlQueryString.append(" (?predicate != rdf:type)");
	        
			//add filter for orSearchTerms
	        if(!Utils.isEmptyOrNull(keywords)){
	        	ArrayList<String> orSearchTerms = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(keywords);
				while (st.hasMoreTokens()) {
					orSearchTerms.add(st.nextToken().trim());
				}
				
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
	        }
	        sparqlQueryString.append(" )");
	        
	        //add learning object type preference
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredLearningObjectType())){
				sparqlQueryString.append("FILTER ( ?learningObjectType = TenOntology:" +  studentAnnotationsBean.getPreferredLearningObjectType() + " )");
			}else{
				sparqlQueryString.append("FILTER ((?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE + " )");
				sparqlQueryString.append("|| (?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT + " )");
				sparqlQueryString.append("|| (?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO + " )");
				sparqlQueryString.append("|| (?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO + " )");
				sparqlQueryString.append(" )");
			}
			
			//Add tribe
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getTribe())){
				sparqlQueryString.append(" OPTIONAL { ?learning_object <" + TripleStoreConstants.URI_PREDICATE_TEN_LO_TRIBE + "> ?tribe . ");
				sparqlQueryString.append(" FILTER (regex(?tribe, \"" + studentAnnotationsBean.getTribe() + "\", \"i\" ))}" );
			}
			
			//Add language preference
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredLanguage())){
				sparqlQueryString.append(" OPTIONAL { ?learning_object <" + TripleStoreConstants.URI_PREDICATE_DC_LANGUAGE + "> ?language . ");
				sparqlQueryString.append(" FILTER (regex(?language, \"" + studentAnnotationsBean.getPreferredLanguage() + "\", \"i\" ))}" );
			}
			
			//Add preferred text type
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredTextContent())){
				sparqlQueryString.append(" OPTIONAL { ?learning_object <" + TripleStoreConstants.URI_PREDICATE_TEN_TEXT_TYPE + "> ?textType . ");
				sparqlQueryString.append(" FILTER (regex(?textType, \"" + studentAnnotationsBean.getPreferredTextContent() + "\", \"i\" ))}" );
			}
			
			//Add preferred image type
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredImageContent())){
				sparqlQueryString.append(" OPTIONAL { ?learning_object <" + TripleStoreConstants.URI_PREDICATE_TEN_IMAGE_TYPE + "> ?imageType . ");
				sparqlQueryString.append(" FILTER (regex(?imageType, \"" + studentAnnotationsBean.getPreferredImageContent() + "\", \"i\" )) }" );
			}
			
			sparqlQueryString.append(" }");
			
			log.debug("SEARCH QUERY:  " + sparqlQueryString.toString());
			
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			Query sparql = QueryFactory.create(sparqlQueryString.toString());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql.toString(), graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode rdfNodeSubject = result.get("learning_object");
			    RDFNode rdfNodeObject = result.get("learningObjectType");
			    if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE).equals(rdfNodeObject.toString())){
			    	imagesList.add(rdfNodeSubject.toString());
			    }else if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO).equals(rdfNodeObject.toString())){
			    	audioList.add(rdfNodeSubject.toString());
			    }else if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO).equals(rdfNodeObject.toString())){
			    	videoList.add(rdfNodeSubject.toString());
			    }else if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT).equals(rdfNodeObject.toString())){
			    	videoList.add(rdfNodeSubject.toString());
			    }
			 }
			
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE, imagesList);
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO, audioList);
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO, videoList);
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT, textList);
			
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		return returnMap;
	}*/

public HashMap<String, ArrayList<String>> queryRecommendedLearningObjects(StudentAnnotationsBean studentAnnotationsBean, String keywords) throws Exception{
	
	String LOG_METHOD_NAME = "void queryLearningObject(StudentAnnotationsBean studentAnnotationsBean, ArrayList<String> orSearchTerms)";
	log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
	ArrayList<String> imagesList = new ArrayList<String>();
	ArrayList<String> audioList = new ArrayList<String>();
	ArrayList<String> videoList = new ArrayList<String>();
	ArrayList<String> textList = new ArrayList<String>();
	HashMap<String, ArrayList<String>> returnMap = new HashMap<String, ArrayList<String>>();
	try{
		
		StringBuffer sparqlQueryString = new StringBuffer();
		sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_ONTOLOGY);
		sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_IMAGE);
		sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_AUDIO);
		sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_VIDEO);
		sparqlQueryString.append(TripleStoreConstants.PREFIX_TEN_TEXT);
		sparqlQueryString.append(TripleStoreConstants.PREFIX_RDF);
		sparqlQueryString.append(TripleStoreConstants.PREFIX_DUBLIN_CORE);		
		sparqlQueryString.append(" SELECT DISTINCT ?learning_object ?learningObjectType ");
		sparqlQueryString.append(" WHERE { ");
			
		//base query with basic keywords conditions
		StringBuffer baseQuery =  new StringBuffer();		
		baseQuery.append(" ?learning_object a ?learningObjectType");
		baseQuery.append(" ;?predicate ?object");	
		
		//exclude type predicate
		baseQuery.append(" . FILTER (");
		baseQuery.append(" (?predicate != rdf:type)");  
		
		//add filter for keywords
        if(!Utils.isEmptyOrNull(keywords)){
        	ArrayList<String> orSearchTerms = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(keywords);
			while (st.hasMoreTokens()) {
				orSearchTerms.add(st.nextToken().trim());
			}
			
        	int i = 0;
        	if((orSearchTerms != null) && (orSearchTerms.size()>0)){
	        	i=0;
	        	baseQuery.append(" && ");
		        for(String searchTerm:orSearchTerms){
		        	baseQuery.append("(regex(?object, \"");
		        	baseQuery.append(searchTerm);
		        	baseQuery.append("\", \"i\"))");
		        	//if this is not the last element add ||
		        	if(i != (orSearchTerms.size()-1)){
		        		baseQuery.append(" || ");
		        	}
		        	i++;
		        }	
        	}
        }
        baseQuery.append(" )");
        
        //add learning object type preference
		if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredLearningObjectType())){
			baseQuery.append("FILTER ( ?learningObjectType = TenOntology:" +  studentAnnotationsBean.getPreferredLearningObjectType() + " )");
		}else{
			baseQuery.append("FILTER ((?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE + " )");
			baseQuery.append("|| (?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT + " )");
			baseQuery.append("|| (?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO + " )");
			baseQuery.append("|| (?learningObjectType = TenOntology:" +  TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO + " )");
			baseQuery.append(" )");
		}
			
		boolean upperCondition = false;
		//Add tribe
		if(!Utils.isEmptyOrNull(studentAnnotationsBean.getTribe())){
			if(upperCondition){
				sparqlQueryString.append( " UNION " );
			}
			sparqlQueryString.append(" { ");
			sparqlQueryString.append(baseQuery.toString());			
			sparqlQueryString.append(" ?learning_object <" + TripleStoreConstants.URI_PREDICATE_TEN_LO_TRIBE + "> ?tribe . ");
			sparqlQueryString.append(" FILTER (regex(?tribe, \"" + studentAnnotationsBean.getTribe() + "\", \"i\" ))" );
			sparqlQueryString.append(" } ");
			upperCondition = true;
		}
		
		//Add language preference
		if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredLanguage())){
			if(upperCondition){
				sparqlQueryString.append( " UNION " );
			}
			sparqlQueryString.append(" { ");
			sparqlQueryString.append(baseQuery.toString());
			sparqlQueryString.append(" ?learning_object <" + TripleStoreConstants.URI_PREDICATE_DC_LANGUAGE + "> ?language . ");
			sparqlQueryString.append(" FILTER (regex(?language, \"" + studentAnnotationsBean.getPreferredLanguage() + "\", \"i\" ))" );
			sparqlQueryString.append(" } ");
			upperCondition = true;
		}
		
		//Add preferred text type
		if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredTextContent())){
			if(upperCondition){
				sparqlQueryString.append( " UNION " );
			}
			sparqlQueryString.append(" { ");
			sparqlQueryString.append(baseQuery.toString());
			sparqlQueryString.append(" ?learning_object <" + TripleStoreConstants.URI_PREDICATE_TEN_TEXT_TYPE + "> ?textType . ");
			sparqlQueryString.append(" FILTER (regex(?textType, \"" + studentAnnotationsBean.getPreferredTextContent() + "\", \"i\" ))" );
			sparqlQueryString.append(" } ");
			upperCondition = true;
		}
		
		//Add preferred image type
		if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredImageContent())){
			if(upperCondition){
				sparqlQueryString.append( " UNION " );
			}
			sparqlQueryString.append(" { ");
			sparqlQueryString.append(baseQuery.toString());
			sparqlQueryString.append(" { ?learning_object <" + TripleStoreConstants.URI_PREDICATE_TEN_IMAGE_TYPE + "> ?imageType . ");
			sparqlQueryString.append(" FILTER (regex(?imageType, \"" + studentAnnotationsBean.getPreferredImageContent() + "\", \"i\" )) " );
			sparqlQueryString.append(" } ");
			upperCondition = true;
		}
		
		sparqlQueryString.append(" }");
		
		log.debug("SEARCH QUERY:  " + sparqlQueryString.toString());
		
		if(upperCondition == true){
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			Query sparql = QueryFactory.create(sparqlQueryString.toString());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql.toString(), graph);
	
			//STEP 3 - Execute
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
			    RDFNode rdfNodeSubject = result.get("learning_object");
			    RDFNode rdfNodeObject = result.get("learningObjectType");
			    if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE).equals(rdfNodeObject.toString())){
			    	imagesList.add(rdfNodeSubject.toString());
			    }else if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO).equals(rdfNodeObject.toString())){
			    	audioList.add(rdfNodeSubject.toString());
			    }else if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO).equals(rdfNodeObject.toString())){
			    	videoList.add(rdfNodeSubject.toString());
			    }else if((TripleStoreConstants.URI_TEN_ONTOLOGY + TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT).equals(rdfNodeObject.toString())){
			    	videoList.add(rdfNodeSubject.toString());
			    }
			 }
		}		
		returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE, imagesList);
		returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO, audioList);
		returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO, videoList);
		returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT, textList);
		
	}catch (Exception ex) {
		log.error(ex);
		throw ex;
	}finally{			
		log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
	}
	return returnMap;
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

		try{
					
			tripleList.addAll(addAdministrativeAnnotationTriples(subject, digitalRightsManagementBean));
						
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
	 * Add descriptive tags
	 */
	public ArrayList<Triple> addDescriptiveAnnotationTriples(Node subject ,TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean){
		
		String LOG_METHOD_NAME = "ArrayList<Triple> addDescriptiveAnnotationTriples(Node, TenLearningObjectAnnotationsBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		try{
			//DESCRIPTIVE
			//title
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getTitle())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_TITLE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getTitle());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//Subject
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getSubject())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_SUBJECT);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getSubject());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//description
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getDescription())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_DESCRIPTION);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getDescription());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//source
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getSource())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_SOURCE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getSource());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//language	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getLanguage())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_LANGUAGE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getLanguage());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//relation	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getRelation())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_RELATION);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getRelation());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//coverage		
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getCoverage())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_COVERAGE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getCoverage());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//tribe
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getTribe())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_TRIBE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getTribe());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}			
			
			//rating
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getRating())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_LO_RATING);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getRating());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//image type
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getImageType())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_IMAGE_TYPE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getImageType());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//text type
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getTextType())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_TEXT_TYPE);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getTextType());
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
	 * add structural annotation triples
	 */
	public ArrayList<Triple> addStructuralAnnotationTriples(Node subject ,TenLearningObjectAnnotationsBean tenLearningObjectAnnotationsBean){
		
		String LOG_METHOD_NAME = "ArrayList<Triple> addStructuralAnnotationTriples(Node, DigitalRightsManagementBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		try{
			//STRUCTURAL	
			//date of annotation
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getDate())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_DATE_ANNOTATION);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getDate());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//format
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getFormat())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_FORMAT);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getFormat());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//identifier	
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getIdentifier())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_IDENTIFIER);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getIdentifier());
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
	 * Add administrative annotation triples
	 */
	public ArrayList<Triple> addAdministrativeAnnotationTriples(Node subject ,DigitalRightsManagementBean digitalRightsManagementBean){
		
		String LOG_METHOD_NAME = " ArrayList<Triple> addAdministrativeAnnotationTriples(Node, DigitalRightsManagementBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
		
		ArrayList<Triple> tripleList = new ArrayList<>();
		Node predicate = null, predicate_value=null;
		try{						
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
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getRights());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//date of learning object creation
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getDate())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_DATE);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getDate());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//intaker
			if(!Utils.isEmptyOrNull(digitalRightsManagementBean.getIntaker())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_INTAKER);
				predicate_value = Node.createLiteral(digitalRightsManagementBean.getIntaker());
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
			//annotator name
			if(!Utils.isEmptyOrNull(tenLearningObjectAnnotationsBean.getAnnotator())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_ANNOTATOR);
				predicate_value = Node.createLiteral(tenLearningObjectAnnotationsBean.getAnnotator());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			tripleList.add(new Triple(subject, predicate, predicate_value));
			
			tripleList.addAll(addDescriptiveAnnotationTriples(subject, tenLearningObjectAnnotationsBean));
			
			tripleList.addAll(addStructuralAnnotationTriples(subject, tenLearningObjectAnnotationsBean));						
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
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getDescription())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_DC_DESCRIPTION);
				predicate_value = Node.createLiteral(courseAnnotationBean.getDescription());
				tripleList.add(new Triple(subject, predicate, predicate_value));
			}
			
			//keywords
			if(!Utils.isEmptyOrNull(courseAnnotationBean.getKeywords())){
				predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_KEYWORDS);
				predicate_value = Node.createLiteral(courseAnnotationBean.getKeywords());
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
			uris = queryLearningObject(type, orSearchTerms, andSearchTermsList);				
						
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
	public  HashMap<String, ArrayList<String>> queryDefaultLearningObjects(String keywords, String andSearchTerms) throws Exception {
		HashMap<String, ArrayList<String>> returnMap = new HashMap<String, ArrayList<String>>();
		
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
			
			//query images
			ArrayList<String> imagesList = new ArrayList<String>();		
			imagesList = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE, orSearchTerms, andSearchTermsList);				
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_IMAGE, imagesList);
			
			//query audios
			ArrayList<String> audioList = new ArrayList<String>();		
			audioList = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO, orSearchTerms, andSearchTermsList);				
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_AUDIO, audioList);
			
			//query videos
			ArrayList<String> videoList = new ArrayList<String>();		
			videoList = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO, orSearchTerms, andSearchTermsList);				
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_VIDEO, videoList);
			
			//query text
			ArrayList<String> textList = new ArrayList<String>();		
			textList = queryLearningObject(TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT, orSearchTerms, andSearchTermsList);				
			returnMap.put(TripleStoreConstants.LEARNING_OBJECT_TYPE_TEXT, textList);
			
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
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql.toString(), graph);
	
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
			    
			    //tribe
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_TRIBE)){
			    	studentAnnotationsBean.setTribe(objectNode.toString());
			    }
			    
			    //learning object type preference
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TYPE)){
			    	studentAnnotationsBean.setPreferredLearningObjectType(objectNode.toString());
			    }
			    
			    //learning object text preference
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TEXT_TYPE)){
			    	studentAnnotationsBean.setPreferredTextContent(objectNode.toString());
			    }
			    
			    //learning object image preference
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_IMAGE_TYPE)){
			    	studentAnnotationsBean.setPreferredImageContent(objectNode.toString());
			    }
			    
			   //preferred language
			    if(predicateNode.toString().contains(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LANGUAGE)){
			    	studentAnnotationsBean.setPreferredLanguage(objectNode.toString());
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

	@Override
	public void updateStudentAnnotations(String user_name,
			StudentAnnotationsBean studentAnnotationsBean) throws Exception {
		String LOG_METHOD_NAME = "void updateStudentAnnotations(String, StudentAnnotationsBean)";
		log.debug(this.getClass() + TripleStoreConstants.LOG_BEGIN + LOG_METHOD_NAME);
	
		try{
			//STEP 1 - Connect to virtuoso database
			VirtGraph graph = new VirtGraph (TripleStoreConstants.VIRTUOSO_GRAPH_URI, m_ds);
			
			//Begin transaction
			log.debug(TripleStoreConstants.LOG_BEGIN_TRANSACTION);
			graph.getTransactionHandler().begin();
			
			StringBuffer sparqlDeleteString = new StringBuffer("");
			//delete tribe information
			sparqlDeleteString.append("DELETE FROM GRAPH <http://localhost:8890/DAV/home/ten_user/graph> { " + "<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_TRIBE + ">" + " ?tribe . " + "}");
			sparqlDeleteString.append(" WHERE {<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_TRIBE + ">" + " ?tribe . " + "}");
			
			//delete language			
			sparqlDeleteString.append("DELETE FROM GRAPH <http://localhost:8890/DAV/home/ten_user/graph> { " + "<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LANGUAGE + ">" + " ?language . " + "}");
			sparqlDeleteString.append(" WHERE {<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LANGUAGE + ">" + " ?language . " + "}");
			
			//delete learning object type
			sparqlDeleteString.append("DELETE FROM GRAPH <http://localhost:8890/DAV/home/ten_user/graph> { " + "<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TYPE + ">" + " ?lotype . " + "}");
			sparqlDeleteString.append(" WHERE {<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TYPE + ">" + " ?lotype . " + "}");
			
			//delete text content type
			sparqlDeleteString.append("DELETE FROM GRAPH <http://localhost:8890/DAV/home/ten_user/graph> { " + "<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TEXT_TYPE + ">" + " ?textType . " + "}");
			sparqlDeleteString.append(" WHERE {<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TEXT_TYPE + ">" + " ?textType . " + "}");
			
			//delete image content type
			sparqlDeleteString.append("DELETE FROM GRAPH <http://localhost:8890/DAV/home/ten_user/graph> { " + "<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_IMAGE_TYPE + ">" + " ?imageType . " + "}");
			sparqlDeleteString.append(" WHERE {<" + TripleStoreConstants.URI_STUDENT + user_name + "> " + "<" + TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_IMAGE_TYPE + ">" + " ?imageType . " + "}");
			VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(sparqlDeleteString.toString(), graph);
            vur.exec();           
			
        	List<Triple> addTripleList = new ArrayList<Triple>();
 			Node predicate_value = null;			
			//tribe
			Node student = Node.createURI(TripleStoreConstants.URI_STUDENT + user_name);
			Node predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_TRIBE);
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getTribe())){
				predicate_value = Node.createLiteral(studentAnnotationsBean.getTribe());	
				addTripleList.add(new Triple(student, predicate, predicate_value));
			}
						
			//language
			student = Node.createURI(TripleStoreConstants.URI_STUDENT + user_name);
			predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LANGUAGE);
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredLanguage())){
				predicate_value = Node.createLiteral(studentAnnotationsBean.getPreferredLanguage());	
				addTripleList.add(new Triple(student, predicate, predicate_value));
			}
			
			//preferred learning object type
			student = Node.createURI(TripleStoreConstants.URI_STUDENT + user_name);
			predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TYPE);
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredLearningObjectType())){
				predicate_value = Node.createLiteral(studentAnnotationsBean.getPreferredLearningObjectType());	
				addTripleList.add(new Triple(student, predicate, predicate_value));
			}
			
			//preferred text content type
			student = Node.createURI(TripleStoreConstants.URI_STUDENT + user_name);
			predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_TEXT_TYPE);
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredTextContent())){
				predicate_value = Node.createLiteral(studentAnnotationsBean.getPreferredTextContent());	
				addTripleList.add(new Triple(student, predicate, predicate_value));
			}
			
			//preferred image content type
			student = Node.createURI(TripleStoreConstants.URI_STUDENT + user_name);
			predicate = Node.createURI(TripleStoreConstants.URI_PREDICATE_TEN_STUDENT_PREFERRED_LO_IMAGE_TYPE);
			if(!Utils.isEmptyOrNull(studentAnnotationsBean.getPreferredImageContent())){
				predicate_value = Node.createLiteral(studentAnnotationsBean.getPreferredImageContent());	
				addTripleList.add(new Triple(student, predicate, predicate_value));
			}
							
			//insert triples
			for(int i=0; i<addTripleList.size(); i++){
				graph.add(addTripleList.get(i));
			}
			
			//End transaction
			graph.getTransactionHandler().commit();
			
			log.debug(TripleStoreConstants.LOG_END_TRANSACTION);
			
		}catch (Exception ex) {
			log.error(ex);
			throw ex;
		}finally{			
			log.debug(this.getClass() + TripleStoreConstants.LOG_END + LOG_METHOD_NAME);
		}
		return;		
	}
}
