<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="css/page_layout.css">
	
	<title>Tribal Education Network Create Course Page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		
	</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/course/searchlearningobjects.action" method="post" enctype="multipart/form-data">
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
		 <tr><td width='50%'>Course Name </td><td width='50%'><input type="text" id="courseName" name="courseName" value="${courseName}" readonly/></td></tr>
		 <tr><td width='50%'>Keywords </td><td width='50%'><input type="text" id="keywords" name="keywords" value="${keywords}" readonly/></td></tr>
  		 <tr><td></td></tr>
  		 <tr><td></td></tr>
  		 <tr><td>Type of Learning Object </td>
			 <td>
				<select name="typeOfLearningObject">
				<option value="1" <c:if test="${typeOfLearningObject == '1'}">selected</c:if>>Images</option>
				<option value="2" <c:if test="${typeOfLearningObject == '2'}">selected</c:if>>Audio</option>
				<option value="3" <c:if test="${typeOfLearningObject == '3'}">selected</c:if>>Videos</option>
				<option value="4" <c:if test="${typeOfLearningObject == '4'}">selected</c:if>>Texts</option>
		       </select>
		     </td>
	       </tr>
  		 <tr><td><input type="submit" value="Search learning objects"/></td></tr>
	</table>
	<c:if test="${requestScope.mapLearningObjects != null}" >
		<table>
		<c:choose>
			<c:when test="${fn:length(requestScope.mapLearningObjects) == 0 }">
				<tr><td><header><c:out value="${'No matching learning objects found'}" /></header></td></tr>
			</c:when>		
			<c:otherwise>
				<c:forEach var="learningObject" items="${requestScope.mapLearningObjects}">
						<c:set var="learningObjectDetailsBean" value="${learningObject.value}" />
						<!-- Display image -->
						<c:if test="${typeOfLearningObject == '1'}">						
							<tr><td>Image Name:</td><td><c:out value="${learningObjectDetailsBean.fileName}" /></td></tr>
							<tr><td><img src="data:<c:out value='${learningObjectDetailsBean.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.content}'/>" width="100%" height="100%"/></td></tr>
						</c:if>
						
						<!-- Display Audio -->
						<c:if test="${typeOfLearningObject == '2'}">						
							<tr><td>Audio Name:</td><td><c:out value="${learningObjectDetailsBean.fileName}" /></td></tr>
							<tr><td><audio controls>
			  						<source src="data:<c:out value='${learningObjectDetailsBean.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.content}'/>" ></source>
			  						</audio>
			  				</td></tr>
						</c:if>
						
						<!-- Display Video -->
						<c:if test="${typeOfLearningObject == '3'}">						
							<tr><td>Video Name:</td><td><c:out value="${learningObjectDetailsBean.fileName}" /></td></tr>
							<tr><td><video width="100%" height="80%" controls>
						  			 <source src="data:<c:out value='${learningObjectDetailsBean.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.content}'/>" ></source>
						  		</video>
			  				</td></tr>
						</c:if>
						
						<!-- Display Text -->
						<c:if test="${typeOfLearningObject == '4'}">						
							<tr><td>Document Name:</td><td><c:out value="${learningObjectDetailsBean.fileName}" /></td></tr>
							<tr><td><object width="100%" height="80%" >
							  		 <embed src="data:<c:out value='${learningObjectDetailsBean.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.content}'/>" ></embed>
							  	</object>
			  				</td></tr>
						</c:if>
				</c:forEach>
			</c:otherwise>	
		</c:choose>
		</table>
	</c:if>
	</section>		
	<input type="hidden" name="courseId" id="courseId" value="${courseId}" />
	</form>
</body>
</html>