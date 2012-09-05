
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html>
<head>
<title>MyGarderobe File Upload</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="fileuploader.css" rel="stylesheet" type="text/css">	
    <style>    	
		body {font-size:13px; font-family:arial, sans-serif; width:700px; margin:100px auto;}
    </style>	
</head>
<body>

    
	<p>To upload a file, click on the button below. Drag-and-drop is supported in FF, Chrome.</p>
	<p>Progress-bar is supported in FF3.6+, Chrome6+, Safari4+</p>
	
	<div id="file-uploader1">		
		<noscript>			
			<p>Please enable JavaScript to use file uploader.</p>
			<!-- or put a simple form for upload here -->
			<input type="button" value="close" onClick="window.close()"> 
		</noscript>         
	</div>
    
    <script src="fileuploader.js" type="text/javascript"></script>
    <script>        
        function createUploader(){            
            var uploader = new qq.FileUploader({
                element: document.getElementById('file-uploader1'),
                action: '/myGarderobe/upload;jsessionid=<%=(( org.springframework.security.web.authentication.WebAuthenticationDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getDetails()).getSessionId()%>',
                debug: true
            });           
        }
        
        // in your app create uploader as soon as the DOM is ready
        // don't wait for the window to load  
        window.onload = createUploader;     
    </script>    

</body>
</html>