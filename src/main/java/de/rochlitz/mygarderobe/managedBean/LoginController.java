package de.rochlitz.mygarderobe.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
//@Model
@ManagedBean(name="loginController", eager=true)
@ViewScoped
public class LoginController implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    
    public String login() throws Exception {
	
	FacesContext fc = FacesContext.getCurrentInstance();
	HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
	
	try {
		//Login per Servlet 3.0
		request.login(username, password);

		// Der Principal entspricht dem Usernamen
//		Principal principal = request.getUserPrincipal();
	    
	} catch(Exception e) {
	    e.printStackTrace();
	    return "loginfail.xhtml";
	}
	return "/app/garderobe.xhtml";
    }

    public String logout()  {
	
	FacesContext fc = FacesContext.getCurrentInstance();
//	HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
//	Principal u = req.getUserPrincipal();
	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	if (session != null) {
		session.invalidate();
	}
	fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/login.xhtml");
	return "login.xhtml";
    }
    
    
}
