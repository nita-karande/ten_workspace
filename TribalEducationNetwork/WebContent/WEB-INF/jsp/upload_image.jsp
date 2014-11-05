<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="css/page_layout.css">
	
	<title>Tribal Education Network Image Upload</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function onFileUpload(){
			//check for valid file type
			var supportedFileTypes = ['image/jpeg','image/gif','image/jpg','image/png','image/bmp'];
			var file = document.getElementById("upload").files[0];
			if(supportedFileTypes.indexOf(file.type) == -1){
				alert("File type not supported");
				$("#uploadPreview_div").empty();
				$("#uploadPreview_div").hide();
				document.getElementById("upload").value = '';
				$("#annotations_div").hide();
				return false;
			}
			$("#uploadPreview_div").show();
			$("#annotations_div").show();
			
			var oFReader = new FileReader();
			oFReader.readAsDataURL(file);			
			oFReader.onload = function (_file) {
		        var obj = document.createElement('img');
				$(obj).attr('src', _file.target.result);
				$(obj).attr('width', '100%');
				$(obj).attr('height', '100%');
				
		    	$("#uploadPreview_div").empty();
		    	$("#uploadPreview_div").append(obj);
		  };
		}
	</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/upload/uploadimage.action" method="post" enctype="multipart/form-data">
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
		 <tr><td><input type="file" id="upload" name="upload" size="50" onchange="onFileUpload()"/></td></tr>
		 <tr><td><div id="uploadPreview_div" style="display:none;"></div></td></tr>
	</table>
	<table>
  		 <tr><td>
	  		 <div id="annotations_div" style="display:none;">
	  		    <%@include file="digital_rights_management.jsp"%>	  		    		 
			 </div>
		 </td></tr>		 
		 <tr><td><input type="submit" value="Upload Image" /></td></tr>
	</table>
	</section>
			
	</form>
</body>
</html>