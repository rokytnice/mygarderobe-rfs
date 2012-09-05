<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>

<%
    String bildId = request.getParameter("bildId");
	
	File uploadVerz = new File(getServletContext().getRealPath(de.rochlitz.mygarderobe.de.rochlitz.mygarderobe.jpa.MyGarderobeConstant.DESTINATION_DIR_NAME));
    for(int i=0; i<uploadVerz.listFiles().length;i++){
		if(bildId.equalsIgnoreCase(Integer.toString(i))){
		
	//    	out.print("<div id=\"dragBild"+i+"\" style=\"width: 50px; height: 50px; cursor: move; background: none repeat scroll 0% 0% ;  position: relative; z-index: 1; \">");
	//    	out.print("<li>\n");
	//    	out.print("<p>\n");
			
			out.print("<img src=\"");
    	out.print(de.rochlitz.mygarderobe.de.rochlitz.mygarderobe.jpa.MyGarderobeConstant.upload_dir_name+"/"+uploadVerz.listFiles()[i].getName());
    	out.print("\" class=\"bild\" title=\" bild titel"+i+"\" alt=\"bild name\""+i+"\" width=\"50%\" name=\"bild"+i+"\" id=\"_chosenbild"+i+"\" />");
    	
	 //   	out.print("<div id=\"bild"+i+"\">**************");
	//	  	out.print("</div>\n");
	  //  	out.print("</p>\n");
	//    	out.print("</li>\n");
	  //  	out.print("</div>\n");
	  

	  
    }
    
    }
%>



