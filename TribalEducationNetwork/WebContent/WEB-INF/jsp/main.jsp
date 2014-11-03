<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<title>Welcome to Tribal Education Network</title>
</head>
<body>
	<form>
	<header>
		<p>Welcome to Tribal Education Network</p>
	</header>
	<section>
		 <!-- Upload links intaker role -->
		 <c:if test="${(sessionScope.user_details.userIntaker == true) || (sessionScope.user_details.userAnnotator == true)}" >
			 <table>
			 <tr><td> UPLOAD LINKS </td></tr>
	  		 <tr><td>
		  		 <div id="upload_links_div">
		  		    <%@include file="include_upload_links.jsp"%>	  		    		 
				 </div>
			 </td></tr>		
			 <tr></tr>
			</table>
		</c:if>
		
		 <!-- Annotate links Annotator role -->
		 <c:if test="${sessionScope.user_details.userAnnotator == true}" >
			 <table>
			 <tr><td> ANNOTATE LINKS </td></tr>
	  		 <tr><td>
		  		 <div id="annotate_links_div">
		  		    <%@include file="include_annotate_links.jsp"%>	  		    		 
				 </div>
			 </td></tr>	
			 <tr></tr>	
			</table>
		</c:if>		

		<!--Course creation links creator role -->
		 <c:if test="${sessionScope.user_details.userCreator == true}" >
			 <table>
			 <tr><td> COURSE CREATION LINK </td></tr>
	  		 <tr><td>
		  		 <div id="course_links_div">
		  		    <%@include file="include_course_links.jsp"%>	  		    		 
				 </div>
			 </td></tr>	
			 <tr></tr>	
			</table>
		</c:if>
		
		<!--Course view links student role -->
		 <c:if test="${sessionScope.user_details.userStudent == true}" >
			 <table>
			 <tr><td> VIEW COURSES LINK </td></tr>
	  		 <tr><td>
		  		 <div id="course_links_div">
		  		    <%@include file="include_view_links.jsp"%>	  		    		 
				 </div>
			 </td></tr>	
			 <tr></tr>	
			</table>
		</c:if>
	</section>
	</form>
</body>
</html>