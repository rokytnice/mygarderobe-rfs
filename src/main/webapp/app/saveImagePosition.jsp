<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.ImageOutfit"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>
<%
    String outfitid = request.getParameter("outfitid");
String imageid = request.getParameter("imageid");
String top = request.getParameter("top");
String left = request.getParameter("left");
String zindex = request.getParameter("zindex");

SuperDAO daoU = new SuperDAO();
daoU.updateImageOutfit(outfitid,imageid,top,left,zindex);
%>