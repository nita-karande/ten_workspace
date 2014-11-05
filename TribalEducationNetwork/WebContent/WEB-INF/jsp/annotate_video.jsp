<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="css/page_layout.css">
	
	<title>Tribal Education Network Annotate Video page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function annotateVideo(id){
			document.annotateVideoMainForm.submit();
		}
	</script>
</head>
<body>
	<form name="annotateVideoForm" id="annotateVideoMainForm" action="${pageContext.request.contextPath}/annotate/annotatevideo.action" method="post" enctype="multipart/form-data">
	<%@include file="include_header.jsp"%>
	
	<section>
	<table>
		<tr><td>
		<s:if test="hasActionErrors()">
	        <s:actionerror/>
		</s:if>
		</td></tr>
	</table>
	
	<table>
		<tr><td>
			<s:if test="hasActionMessages()">
		  	    <s:actionmessage/>
			</s:if>
		</td></tr>
	</table>
	
	<table>
		 <tr><td>Annotate Video</td></tr>
  		  <c:choose>
	  		  <c:when test="${requestScope.learningObjectDetailsBean == null}">
	  		 	 <tr><td>Video cannot be found</td><td></td></tr>
  			 </c:when>
  			 <c:otherwise> 
  			 <c:set var="objectType" scope="request" value="Video"/>
	 		  <tr>
				<td>Video Preview</td>
				<td></td>
			  </tr>
			  <tr>
			  	<td>
			  		<video width="100%" height="80%" controls>
			  			 <source src="data:<c:out value='${learningObjectDetailsBean.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.content}'/>" ></source>
			  		</video>
			  	</td>				
 		 	  </tr>
 			  <tr>
				<td colspan="2">
		  		 <div id="annotations_div">
		  		    <%@include file="ten_annotations.jsp"%>	  		    		 
				 </div>
			   </td>
 			  </tr>		 		  			 	
  			 </c:otherwise>
  		</c:choose>		
	</table>
	</section>
		<input type="hidden" id="learningObjectId" name="learningObjectId" value="${learningObjectDetailsBean.id}"/>
		<input type="hidden" id="actionType" name="actionType" value="annotate"/>	
	</form>
</body>
</html>