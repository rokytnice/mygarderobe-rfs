<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.lang.System"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.TagDAO"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.Tag"%>

<%
	TagDAO tDao = new TagDAO();
	java.util.List<Tag> results = tDao.findAll();
	
	java.util.Iterator<Tag> iter = results.iterator();
	String separator = ",";
	int i= 0;
	
	out.print("{");
	while(iter.hasNext()){
		Tag tag = iter.next();
		out.print("\"" + tag.getTagId() +"\" : \""+ tag.getTagText() +"\"");
		if(i!=results.size()-1 && results.size()>=1 ){
		out.print(separator);
		}
		i++;
		
	}
	

 	out.print("}");
%>



