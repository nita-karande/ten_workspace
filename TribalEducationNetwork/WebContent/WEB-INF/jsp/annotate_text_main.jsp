<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/page_layout.css">
	
	<title>Tribal Education Network Annotate Text main page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function annotateText(id){
			document.getElementById("learningObjectId").value = id;
			document.annotateTextMainForm.submit();
		}
	</script>
</head>
<body style="background-image: url('${pageContext.request.contextPath}/images/background_annotator.jpg');background-attachment: fixed; background-position: right; background-repeat:no-repeat">
	<form name="annotateTextMainForm" id="annotateTextMainForm" action="${pageContext.request.contextPath}/annotate/annotatetext.action" method="post" enctype="multipart/form-data">
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
		 <tr><td><h1>ANNOTATE TEXT</h1></td></tr>
  		  <c:choose>
	  		  <c:when test="${(requestScope.learningObjects == null) || (fn:length(requestScope.learningObjects) == 0)}">
	  		 	 <tr><td>No texts to be annotated</td></tr>
  			 </c:when>
  			 <c:otherwise>
  			 	<c:forEach items="${requestScope.learningObjects}" var="learningObject">
  			 		 <tr>
 		 				<td><a href="#" onclick="annotateText('${learningObject.id}');"><c:out value="${learningObject.fileName}"></c:out></a></td>
  			 		 </tr>
  			 	</c:forEach>
  			 </c:otherwise>
  		</c:choose>
		
	</table>
	</section>
	<input type="hidden" id="learningObjectId" name="learningObjectId" value=""/>
	<input type="hidden" id="actionType" name="actionType" value="display"/>	
	</form>
</body>
</html>