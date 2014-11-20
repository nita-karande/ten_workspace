<!DOCTYPE html>
<html>
<head><head>
 	 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/page_layout.css">
	<title>Tribal Education Network Login</title>
	<style>
		body.body_class {
			background-attachment: fixed; 
			background-position: center;
			background-color:lightgrey;
			background-image: url('/Ten/images/background_login.jpg');
		}
		
		th.labels {
			font-size: large;
			color:black;
		}
		
		th.headers {
			font-size: xx-large;			
			color:black;
			align:right;
			font-family: sans-serif;
		}	
		
	</style>
</head>
<body class="body_class">
	<form method="POST" action="j_security_check" >
	  <table style="margin:auto;margin-top:280px;">
	  	<tr>
	  		<th colspan="2" class="headers">Tribal Education Network</th>
	  	</tr>
	  	 <tr><td><br></td></tr>
	    <tr>
	      <th align="right" class="labels">Username:</th>
	      <td align="left"><input type="text" name="j_username"></td>
	    </tr>
	    <tr>
	      <th align="right" class="labels">Password:</th>
	      <td align="left"><input type="password" name="j_password"></td>
	    </tr>
	     <tr><td><br></td></tr>
	    <tr>
	      <td align="right"><input type="submit" value="Log In"></td>
	      <td align="left"><input type="reset"></td>
	    </tr>
	  </table>
	  
	  <input type="hidden" name="originalUrl"/>
	</form>
</body>
</html>