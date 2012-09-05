<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.ImageOutfit"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>
<%
    String outfitid = request.getParameter("outfitid");
String imageid = request.getParameter("imageid");

SuperDAO daoU = new SuperDAO();
try{
	daoU.deleteImageOfOutfit(outfitid,imageid);
	}catch(Exception e){
		//TODO errohandling
		out.print(e);
	}
%>