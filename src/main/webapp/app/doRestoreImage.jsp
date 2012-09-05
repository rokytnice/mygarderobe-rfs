<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.*"%>
<%@page import="de.rochlitz.ImageProcessingUtil"%>
<%
    String filename = request.getParameter("filename");


de.rochlitz.mygarderobe.beans.ImageProcessingUtil imageUtil = new de.rochlitz.mygarderobe.beans.ImageProcessingUtil();
imageUtil.doRestoreImage(filename,request);
%>