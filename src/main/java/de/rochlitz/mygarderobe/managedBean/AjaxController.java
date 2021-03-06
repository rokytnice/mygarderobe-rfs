package de.rochlitz.mygarderobe.managedBean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import de.rochlitz.mygarderobe.jpa.SuperDAO;

@ManagedBean(name = "ajaxController", eager = true)
@ViewScoped
public class AjaxController implements Serializable{//Note that the bean needs to implement Serializable as it will be stored in the view map which is in turn stored in the sessio

    /**
     * 
     */
    private static final long serialVersionUID = -6489086561688873010L;

    @EJB(beanName = "SuperDAO")
    SuperDAO dao;

    String currentUserID;
    
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
  
    public String getCurrentUserFolderJSON(){
  	String userID = dao.getCurentUser().getUserId().toString();
  	this.currentUserID=userID;
  	JSONObject jsono = null;
  	try {
  	    jsono = new JSONObject();
  	    jsono.put("userfolder", userID.trim());
  	} catch (JSONException e) {
  	    // TODO Auto-generated catch block
  	    e.printStackTrace();
  	}
  	return jsono.toString();
      }
    
    public String getCurrentUserFolder(){
	String userID = dao.getCurentUser().getUserId().toString();
	this.currentUserID=userID;
	return  userID.trim();
    }
    
    public String getCurrentFolder(ActionEvent parameter){
	 String userID = dao.getCurentUser().getUserId().toString();
	 this.currentUserID=userID;
	 return userID.trim();
    }

    public String getCurrentUserID() {
        return getCurrentUserFolder();
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }
    

   

}
