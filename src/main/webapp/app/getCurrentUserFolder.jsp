<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>
<%
    SuperDAO d = new SuperDAO();
String userID = d.getCurentUser().getUserId().toString();
out.print(userID.trim());
%>