<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="de.rochlitz.ImageProcessingUtil"%>

<%
    String size = request.getParameter("size");
    de.rochlitz.mygarderobe.beans.ImageProcessingUtil ip = new de.rochlitz.mygarderobe.beans.ImageProcessingUtil();
    String uloadPath = getServletContext().getRealPath(de.rochlitz.mygarderobe.de.rochlitz.mygarderobe.jpa.MyGarderobeConstant.DESTINATION_DIR_NAME);
    ip.createThumbnailsForImages(Integer.valueOf(size),uloadPath);
%>



