<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.lang.System"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.Tag"%>

<%
    String id = request.getParameter("id");
	String type = request.getParameter("type");
	SuperDAO uDao = new SuperDAO();
	java.util.List<Tag> results = uDao.getTagsOfResource(id,type);
	 
	
	java.util.Iterator<Tag> iter = results.iterator();
	String separator = ",";
	int i= 0;
	while(iter.hasNext()){
		Tag tag = iter.next();
		out.print(tag.getTagText());
		if(i!=results.size()-1 && results.size()>=1 ){
		out.print(separator);
		}
		i++;
		
	}
	
 	//out.print(bildId+": static tag1, static tag2");
%>



