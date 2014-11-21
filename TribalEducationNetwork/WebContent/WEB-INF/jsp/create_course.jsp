<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/page_layout.css">
	
	<title>Tribal Education Network Create Course Page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		
	</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/course/create.action" method="post" enctype="multipart/form-data">
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
	
	<table style="width:700px">
		 <tr>
		 	<td width='50%'>Course Name </td><td width='50%'><input type="text" id="courseName" name="courseName" /></td></tr>
  		 <tr>
  		 	<td colspan="2">
	  			<%@include file="course_annotations.jsp"%>	  
	  		</td>		    		 
		 </tr>		 
		 <tr><td><input type="submit" value="Create Course" /></td></tr>
	</table>
	</section>
			
	</form>
</body>
</html>