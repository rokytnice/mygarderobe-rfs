package de.rochlitz.mygarderobe.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Stateless
public class JSONObjectRenderer {
    
    @Inject private static Logger log;
    
    public static void sendJSONResponce(ExternalContext ec, Object jsonobject){
	HttpServletResponse response =  (HttpServletResponse) ec.getResponse();
	response.setContentType("application/json, text/javascript");
	try {
	    response.getWriter().write(jsonobject.toString());
	} catch (IOException e) {
	    log.log(Level.ALL,e.getMessage());
	}
	FacesContext.getCurrentInstance().responseComplete();
	//TODO prüfen ob contentype zurückgesetzt werden muss???
    }
    

}
