<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>

<%
    String outfitName = request.getParameter("outfitName");
	String image = request.getParameter("currentOutfit");
	String top = request.getParameter("top");
	String left = request.getParameter("left");
	String zindex = request.getParameter("zindex");
	SuperDAO uDao = new SuperDAO();
	String result =   uDao.updateOutfit(outfitName,image,left,top,zindex) ;
	out.print(result);
//new DAOUtility().saveTagForExistingImage(image,tag);
%>



