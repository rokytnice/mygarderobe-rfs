<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" 
%><%@taglib uri="http://www.springframework.org/tags/form" prefix="form"
%><%@taglib uri="http://www.springframework.org/tags" prefix="spring"
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="garderobe">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <div align="center">
	<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
		<div id ="login_holder">
			<div id ="login_area">				
				<table width="350px">
					<tr>
						<td colspan="2" style="text-align: right;">
						<!-- Änderung der Sprache -->
						<a href="?lang=en"><img src="<c:url value='/media/images/flag_en.png'/>" alt="English"></a>     			
					    <a href="?lang=de"><img src="<c:url value='/media/images/flag_de.png'/>" alt="German"></a>
						</td>
					</tr>
					<tr>				        
				       <td colspan="2" >
					       	<span style="font-size: 13px; font-weight: 700; border-bottom: 1px dotted #ccc; display: block;"> 
 						
					       </span><br />
				       <c:if test="${not empty param.login_error}">
					      <font color="red">
					       <% // <spring:message code="text.failureloginmessage"/>.<br/>
					         // <spring:message code="text.grund"/>: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
					        %>
					      </font>
		    		   </c:if>				       				      
				       </td>				       
				    </tr>
				    <tr>
				        <td>
username
				        </td>
				        <td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td>
				    </tr>
				    <tr>
				        <td>
				        passwort
				        </td>
				        <td><input type='password' name='j_password' /></td>
				    </tr>
				     <tr>
				        <td></td>
				        <td><input type="checkbox" name="_spring_security_remember_me">
				        wiedererkennen
				        </td>
				    </tr>
				    <tr>
				        <td></td>
				        <td>
				            <input type="submit" type="submit" value="anmelden"/>
				            <br />
				        </td>
			    	</tr>
				</table>				
			</div>
		</div>
	</form>
</div>