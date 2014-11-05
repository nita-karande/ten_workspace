<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="css/page_layout.css">
	
	<title>Tribal Education Network Annotate Text main page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function annotateText(id){
			document.annotateTextMainForm.submit();
		}
	</script>
</head>
<body>
	<form name="annotateTextForm" id="annotateTextMainForm" action="${pageContext.request.contextPath}/annotate/annotatetext.action" method="post" enctype="multipart/form-data">
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
		 <tr><td>Annotate Text</td></tr>
  		  <c:choose>
	  		  <c:when test="${requestScope.learningObjectDetailsBean == null}">
	  		 	 <tr><td>Text cannot be found</td><td></td></tr>
  			 </c:when>
  			 <c:otherwise>  			 	
 			 <c:set var="objectType" scope="request" value="Text"/>
	 		  <tr>
				<td>Text Preview</td>
				<td></td>
			  </tr>
			  <tr>
			  	<td>
				<object width="100%" height="80%" >
			  		 <embed src="data:<c:out value='${learningObjectDetailsBean.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.content}'/>" ></embed>
			  	</object>
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