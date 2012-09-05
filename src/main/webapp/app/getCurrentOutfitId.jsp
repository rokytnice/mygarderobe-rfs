<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>
<%
    SuperDAO daoU = new SuperDAO();
try{
    out.clear();
    out.print(daoU.getCurentUser().getCurrentOutfitId());
	}catch(Exception e){
		//TODO errohandling
		;
	}
%>