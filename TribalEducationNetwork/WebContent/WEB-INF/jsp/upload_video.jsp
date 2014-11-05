<!DOCTYPE html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="css/page_layout.css">
	
	<title>Tribal Education Network Image Upload</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function onFileUpload(){
			//check file type
			var supportedFileTypes = ['video/mp4','video/webm','video/ogg'];
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
			oFReader.readAsDataURL(document.getElementById("upload").files[0]);			
			oFReader.onloadend = function(_file) {				
				var obj,
			    source;
		
				obj = document.createElement('video');
				$(obj).attr('width', '100%');
				$(obj).attr('data-height', '80%');
				$(obj).attr('controls', ' ');
				$(obj).attr('preload', 'auto');
				$(obj).attr('data-setup', '{}');
			
				source = document.createElement('source');
				$(source).attr('type', file.type);
				$(source).attr('src',_file.target.result);
			
				$("#uploadPreview_div").empty();
				$("#uploadPreview_div").append(obj);
				$(obj).append(source);
			};
		}
	</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/upload/uploadvideo.action" method="post" enctype="multipart/form-data">
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
  		 <tr><td>
	  		 <div id="annotations_div" style="display:none">
	  		    <%@include file="digital_rights_management.jsp"%>				 
			 </div>
		 </td></tr>
		 
		 <tr><td><input type="submit" value="Upload Video" /></td></tr>
	</table>
	</section>
			
	</form>
</body>
</html>