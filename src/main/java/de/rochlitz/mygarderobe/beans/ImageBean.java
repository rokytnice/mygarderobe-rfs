package de.rochlitz.mygarderobe.beans;



import java.net.URL;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;

import de.rochlitz.mygarderobe.jpa.entity.Image;

@Stateful
@ConversationScoped
public class ImageBean {
   
    Image image;
    URL path;
    
    
    public ImageBean() {
	super();
	// TODO Auto-generated constructor stub
    }

    public ImageBean(Image image2) {
	this.image=image2;
    }
    
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public URL getPath() {
        return path;
    }
    public void setPath(URL url) {
        this.path = url;
    }
    
    
}
