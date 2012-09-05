<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>

<%
    String id = request.getParameter("id");
	String type = request.getParameter("type");
	
	SuperDAO u = new SuperDAO();
	u.delete(id,type);
%>



