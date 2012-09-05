<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>

<%
    //out.print("<meta http-equiv=\"cache-control\" content=\"no-cache\">");
	
	File uploadVerz = new File(getServletContext().getRealPath(de.rochlitz.mygarderobe.de.rochlitz.mygarderobe.jpa.MyGarderobeConstant.DESTINATION_DIR_NAME));
	
	for(int i=0; i<uploadVerz.listFiles().length;i++){
	out.println("alert('bild"+i+"'); ");
//		out.println("function initContextMenu"+i+"() { ");
			out.println("$('#bild"+i+"').contextMenu('menu', {");
			out.println("  	menuStyle: {");
			out.println(" 		width: '150px'");
			out.println("	},");
			out.println("	bindings: {");
		    out.println("'func1': function(t) {");
			    out.println("alert('You choose func 1');");
		    out.println("},");
		    out.println("'func2': function(t) {");
			    out.println("alert('you choose func 2');");
		    out.println("},");
		    out.println("'func3': function(t) {");
			    out.println("alert('you choose func3');");
		    out.println("}");
		    out.println("}");
		    out.println("  });");
	//	    out.println("}");
    
    }
%>



