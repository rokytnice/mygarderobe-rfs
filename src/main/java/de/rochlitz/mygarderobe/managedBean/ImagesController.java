package de.rochlitz.mygarderobe.managedBean;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import de.rochlitz.mygarderobe.jpa.SuperDAO;
import de.rochlitz.mygarderobe.jpa.entity.Image;
import de.rochlitz.mygarderobe.jpa.entity.ImageOutfit;
import de.rochlitz.mygarderobe.jpa.entity.Outfit;

@ManagedBean(name = "imagesController", eager = true)
@ViewScoped
public class ImagesController {

    @EJB(beanName = "SuperDAO")
    SuperDAO dao;

    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();


    private String imageFilter;
    private String thumbnailSize;
    private String currentOutfitID;
    private Logger log;
    
    public List<Image> getImages() {

	imageFilter = ec.getRequestParameterMap().get("imageFilter");
	thumbnailSize = ec.getRequestParameterMap().get("thumbnailSize");
	List<Image> images = dao.getImagesOfTag(imageFilter);// TODO später DTO
							      // machen?
	return images;
    }

    public String getCurrentUserId() {
	return dao.getCurentUser().getUserId().toString();
    }

    public String getImageFilter() {
	return imageFilter;
    }

    public void setImageFilter(String imageFilter) {
	this.imageFilter = imageFilter;
    }


    public void deleteImageOrOutfit() {
	String id = ec.getRequestParameterMap().get("id");
	String type = ec.getRequestParameterMap().get("type");//TODO prüfe auf typ?
	dao.delete(id, type);
    }

    public void deleteImageOfOutfit() {
	String id = ec.getRequestParameterMap().get("outfitid");
	String type = ec.getRequestParameterMap().get("imageid");//TODO prüfe auf typ?
	dao.deleteImageOfOutfit(id, type);
    }
    
    public String saveOutfit(){
	
	String outfitName = ec.getRequestParameterMap().get("outfitName");
	String images = ec.getRequestParameterMap().get("currentOutfit");
	dao.saveOutfit(outfitName, images);
	return "saveOutfitResult.xhtml"; 
	
    }
    
    public String getCurrentOutfitId(){
	if (currentOutfitID==null)
	    currentOutfitID= dao.getCurentUser().getCurrentOutfitId().toString();
	return currentOutfitID;
    }

    public String getCurrentOutfit(){
        
        String outfitid = null;
	if(outfitid==null||outfitid.equalsIgnoreCase("null")){
            outfitid=null;
            outfitid = String.valueOf( dao.getCurentUser().getCurrentOutfitId() );
        }else{
            dao.setCurrentOutfitId( outfitid );
        }
        if(outfitid==null||outfitid.equalsIgnoreCase("null")){
            log.log(Level.ALL,"keine outfitid vorhanden");
            throw new IllegalArgumentException("keine outfitid vorhanden");
        	
        }else{
            
        	Outfit o = dao.find(Outfit.class,Integer.parseInt(outfitid));
        	if(o == null){
        	    log.log(Level.ALL,"keine outfitid vorhanden");
                    throw new IllegalArgumentException("keine outfitid vorhanden");
        	}
        	else{
        		Set<ImageOutfit> images =     o.getImageOutfits();
        		java.util.Iterator<ImageOutfit> iter = images.iterator();
        		
        		JSONArray outfit = new JSONArray();
        		
        		while(iter.hasNext()){
        			ImageOutfit io = iter.next();
                		JSONObject image = new JSONObject();
                		try {
				    image.put("outfitname", io.getImage().getFilename());
				    image.put("imageid", io.getImage().getImageId());
				    image.put("filename", io.getImage().getFilename() );
				    image.put("name", io.getImage().getName());
				    image.put("top", io.getTopPosition());
				    image.put("left", io.getLeftPosition());
				    image.put("zindex", io.getZindex() );
				    outfit.put(image);
				} catch (JSONException e) {
				    // TODO Auto-generated catch block
				    log.log(Level.ALL,e.getMessage());
				}
                		
        		}
        		String result =outfit.toString();
        		return result;
        	}
        }
    }
    
    public String updateOutfit(){
	String outfitName = ec.getRequestParameterMap().get("outfitName");
	String image = ec.getRequestParameterMap().get("currentOutfit");
	String top = ec.getRequestParameterMap().get("top");
	String left = ec.getRequestParameterMap().get("left");
	String zindex = ec.getRequestParameterMap().get("zindex");
	String result =   dao.updateOutfit(outfitName,image,left,top,zindex) ;
	return result;
    }
    
    public List<Outfit> getOutfits(){
	 String imageFilter = ec.getRequestParameterMap().get("imageFilter");
		java.util.List<Outfit> outfits = dao.getOutfitsOfTag(imageFilter);
		return outfits;
    }
    
    public String getThumbnailSize(){
	String thumbsizefromrequest = ec.getRequestParameterMap().get("thumbnailSize");//TODO konfiguriebar machen
	if(thumbsizefromrequest==null)
	    return thumbnailSize;
	return thumbsizefromrequest;
    }
    
    public void saveImagePosition(){
	    try {
		String outfitid = ec.getRequestParameterMap().get("outfitid");
		String imageid = ec.getRequestParameterMap().get("imageid");
		String top = ec.getRequestParameterMap().get("top");
		String left = ec.getRequestParameterMap().get("left");
		String zindex = ec.getRequestParameterMap().get("zindex");
		dao.updateImageOutfit(outfitid,imageid,top,left,zindex);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
    }
    

}
