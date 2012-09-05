<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>

<%
    String outfitName = request.getParameter("outfitName");
	String images = request.getParameter("currentOutfit");
	SuperDAO uDao = new SuperDAO();
	String result = String.valueOf(uDao.saveOutfit(outfitName,images));
	out.print(result);
//new DAOUtility().saveTagForExistingImage(image,tag);
%>



