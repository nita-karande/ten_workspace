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
	<form action="${pageContext.request.contextPath}/view/coursedetails.action" method="post" enctype="multipart/form-data">
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
		 <tr><td width='50%'>Course Name </td><td width='50%'><input type="text" id="courseName" name="courseName" value="${courseName}" readonly/></td></tr>
		 <tr><td width='50%'>Description </td><td width='50%'><input type="text" value="${requestScope.courseAnnotationsBean.description}" readonly/></td></tr>
		 <tr><td></td></tr>
	</table>
	<br>
	<!-- RECOMMENDED LEARNING OBJECTS -->
	<table>
	<c:if test="${requestScope.mapLearningObjects != null}" >
		<tr><td width="50%"><header><b><c:out value="${'RECOMMENDED LEARNING OBJECTS'}" /></b></header></td></tr>
		<c:choose>
			<c:when test="${recommendedLearningObjects == 0}">
				<tr><td><header><c:out value="${'No recommended learning objects other than the default ones found'}" /></header></td></tr>
			</c:when>		
			<c:otherwise>				
				<c:forEach var="learningObjectMapEntry" items="${requestScope.mapLearningObjects}">
						<c:set var="learningObjectsList" value="${learningObjectMapEntry.value}" />
						<!-- Display image -->
						<c:if test="${learningObjectMapEntry.key == 'Image'}">
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">		
								<tr><td>Image Name:</td><td><c:out value="${learningObjectDetailsBean.value.fileName}" /></td></tr>
								<tr><td><img src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" width="100%" height="100%"/></td></tr>
							</c:forEach>
						</c:if>
						
						<!-- Display Audio -->
						<c:if test="${learningObjectMapEntry.key == 'Audio'}">	
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">					
								<tr><td>Audio Name:</td><td><c:out value="${learningObjectDetailsBean.value.fileName}" /></td></tr>
								<tr><td><audio controls>
				  						<source src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" ></source>
				  						</audio>
				  				</td></tr>
			  				</c:forEach>
						</c:if>
						
						<!-- Display Video -->
						<c:if test="${learningObjectMapEntry.key == 'Video'}">	
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">					
								<tr><td><video width="100%" height="80%" controls>
							  			 <source src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" ></source>
							  		</video>
				  				</td></tr>
			  				</c:forEach>
						</c:if>
						
						<!-- Display Text -->
						<c:if test="${learningObjectMapEntry.key == 'Text'}">	
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">						
								<tr><td>Document Name:</td><td><c:out value="${learningObjectDetailsBean.value.fileName}" /></td></tr>
								<tr><td><object width="100%" height="80%" >
								  		 <embed src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" ></embed>
								  	</object>
				  				</td></tr>
			  				</c:forEach>
						</c:if>
				</c:forEach>
			</c:otherwise>	
		</c:choose>
	</c:if>	
	<tr><td></td></tr>
	<tr><td></td></tr>
	<!-- DEFAULT LEARNING OBJECTS -->
	<c:if test="${requestScope.mapDefaultLearningObjects != null}" >
		<tr><td width="50%"><header><b><c:out value="${'DEFAULT LEARNING OBJECTS'}" /></b></header></td></tr>
		<c:choose>
			<c:when test="${defaultLearningObjects == 0 }">
				<tr><td><header><c:out value="${'No default learning objects other than the recommended ones found'}" /></header></td><td></td></tr>
			</c:when>		
			<c:otherwise>				
				<c:forEach var="learningObjectMapEntry" items="${requestScope.mapDefaultLearningObjects}">
						<c:set var="learningObjectsList" value="${learningObjectMapEntry.value}" />
						<!-- Display image -->
						<c:if test="${learningObjectMapEntry.key == 'Image'}">
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">		
								<tr><td>Image Name:</td><td><c:out value="${learningObjectDetailsBean.value.fileName}" /></td></tr>
								<tr><td><img src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" width="100%" height="100%"/></td></tr>
							</c:forEach>
						</c:if>
						
						<!-- Display Audio -->
						<c:if test="${learningObjectMapEntry.key == 'Audio'}">	
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">					
								<tr><td>Audio Name:</td><td><c:out value="${learningObjectDetailsBean.value.fileName}" /></td></tr>
								<tr><td><audio controls>
				  						<source src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" ></source>
				  						</audio>
				  				</td></tr>
			  				</c:forEach>
						</c:if>
						
						<!-- Display Video -->
						<c:if test="${learningObjectMapEntry.key == 'Video'}">	
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">					
								<tr><td><video width="100%" height="80%" controls>
							  			 <source src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" ></source>
							  		</video>
				  				</td></tr>
			  				</c:forEach>
						</c:if>
						
						<!-- Display Text -->
						<c:if test="${learningObjectMapEntry.key == 'Text'}">	
							<c:forEach var="learningObjectDetailsBean" items="${learningObjectsList}">						
								<tr><td>Document Name:</td><td><c:out value="${learningObjectDetailsBean.value.fileName}" /></td></tr>
								<tr><td><object width="100%" height="80%" >
								  		 <embed src="data:<c:out value='${learningObjectDetailsBean.value.fileType}'></c:out>;base64,<c:out value='${learningObjectDetailsBean.value.content}'/>" ></embed>
								  	</object>
				  				</td></tr>
			  				</c:forEach>
						</c:if>
				</c:forEach>
			</c:otherwise>	
		</c:choose>
	</c:if>
	</table>
	</section>		
	<input type="hidden" name="courseId" id="courseId" value="${courseId}" />
	<input type="hidden" name="actionName" id="actionName" value="search" />
	</form>
</body>
</html>