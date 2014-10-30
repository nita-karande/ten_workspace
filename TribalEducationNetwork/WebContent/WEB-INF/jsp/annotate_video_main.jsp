<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="css/page_layout.css">
	
	<title>Tribal Education Network Annotate Video main page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function annotateVideo(id){
			document.getElementById("learningObjectId").value = id;
			document.annotateVideoMainForm.submit();
		}
	</script>
</head>
<body>
	<form name="annotateVideoMainForm" id="annotateVideoMainForm" action="${pageContext.request.contextPath}/annotate/annotatevideo.action" method="post" enctype="multipart/form-data">
	<header>
		<h1><img src="${pageContext.request.contextPath}/images/TEN_icon.png" alt="TEN Logo" width="60px" height="60px">  Tribal Education Network</h1>
	</header>
	
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
		 <tr><td>ANNOTATE VIDEO</td></tr>
  		  <c:choose>
	  		  <c:when test="${(requestScope.learningObjects == null) || (fn:length(requestScope.learningObjects) == 0)}">
	  		 	 <tr><td>No videos to be annotated</td></tr>
  			 </c:when>
  			 <c:otherwise>
  			 	<c:forEach items="${requestScope.learningObjects}" var="learningObject">
  			 		 <tr>
 		 				<td>Annotate Video <a href="#" onclick="annotateVideo('${learningObject.id}');"><c:out value="${learningObject.fileName}"></c:out></a></td>
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