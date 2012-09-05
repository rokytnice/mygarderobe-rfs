<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%
//out.print("<meta http-equiv=\"cache-control\" content=\"no-cache\">");
	String bildId = request.getParameter("bildId");
	 // out.print("   <script type=\"text/javascript\" />  alert('getChosenImageJavascriptFunctions.jsp'); ");
	  out.print("   	$(function() {		$( \"#chosenbild"+bildId+"\" ).draggable();	});");
	 // out.print("   </script> ");    
%>

