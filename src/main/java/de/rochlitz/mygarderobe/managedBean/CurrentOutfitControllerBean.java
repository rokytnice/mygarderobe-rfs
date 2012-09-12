package de.rochlitz.mygarderobe.managedBean;

import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import de.rochlitz.mygarderobe.jpa.SuperDAO;


@ManagedBean(name = "currentOutfitControllerBean", eager = true)
@ViewScoped
public class CurrentOutfitControllerBean {

    public CurrentOutfitControllerBean() {
	// TODO Auto-generated constructor stub
    }

    @EJB(beanName = "SuperDAO")
    SuperDAO dao;
    String outfitname;
    String outfitId;
    @Inject  
    private Logger log;

    public String getOutfitId() {
        return outfitId;
    }

    public void setOutfitId(String outfitId) {
        this.outfitId = outfitId;
    }

    public String getOutfitname() {
        return outfitname;
    }

    public void setOutfitname(String outfitname) {
        this.outfitname = outfitname;
         Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
         this.outfitId  = (String) map.get("currentOutfitID");
         try {
	    dao.updateCurrentOutfitName(outfitname, outfitId);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    log.severe("setOutfitname() error while update outfitname : "+e.getMessage());
	}
         
    }
    
    

}
