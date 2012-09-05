package de.rochlitz.mygarderobe.jpa;

import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import de.rochlitz.mygarderobe.beans.FileBean;
import de.rochlitz.mygarderobe.jpa.entity.Image;
import de.rochlitz.mygarderobe.jpa.entity.ImageOutfit;
import de.rochlitz.mygarderobe.jpa.entity.ImageTag;
import de.rochlitz.mygarderobe.jpa.entity.Outfit;
import de.rochlitz.mygarderobe.jpa.entity.OutfitTag;
import de.rochlitz.mygarderobe.jpa.entity.Tag;
import de.rochlitz.mygarderobe.jpa.entity.User;

@Stateless(name="SuperDAO")
@TransactionManagement(TransactionManagementType.CONTAINER ) //ist nicht unbedingt notwendig da Stateless bereits die ejb transaction aus dem container nutzt
public class SuperDAO  {
  
    //Transaktionshandling durch  EJB-Container ("CMT").
    @PersistenceContext( unitName="primary", name="persistence/em" )
    public EntityManager entityManager;
    
//    @Resource 
//    private UserTransaction utx;
    
    // Injects EJB context 
    @Resource
    private SessionContext sessioncontext;
    
    final public static String RESOURCE_TYPE_IMAGE = "image";
    final public static String RESOURCE_TYPE_OUTFIT = "outfit";
    @Inject  
    private Logger log;
    
 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveTag(String id, String tagText, String type) {
	// EntityManagerHelper.log("saving Tag for Images persistance",
	// Level.INFO, null);
	// TODO isNotEmpty tagText

	Tag tag = null;
	try {

	    tag = new Tag();
	    tag.setUser(getCurentUser());
	    tag.setTagText(tagText);
		entityManager.find(Tag.class, tag);
		entityManager.persist(tag);

	} catch (Exception e) {
	    log.log(Level.ALL, "save ImagesTag failed");
	    throw new RuntimeException("save ImagesTag failed" + e.getMessage());
	}
    	
	

	if (type.equalsIgnoreCase(SuperDAO.RESOURCE_TYPE_IMAGE)) {
	    try {
		Image foundImage = new Image();
		foundImage.setImageId(Integer.valueOf(id));
		entityManager.find(Image.class,foundImage);
		ImageTag it = new ImageTag();
		it.setTag(tag);
		it.setImage(foundImage);
		entityManager.persist(it);

	    } catch (RuntimeException re) {
		log.log(Level.ALL,re.getMessage());
		throw re;
	    }
	} else if (type.equalsIgnoreCase(SuperDAO.RESOURCE_TYPE_OUTFIT)) {

	    try {

		Outfit foundOutfit = new Outfit();
		foundOutfit.setOutfitId(Integer.valueOf(id));
		entityManager.find(Outfit.class, foundOutfit);

		OutfitTag it = new OutfitTag();
		
		it.setTag(tag);
		it.setOutfit(foundOutfit);
		entityManager.persist(it);

	    } catch (RuntimeException re) {
		log.log(Level.ALL,re.getMessage());
		throw re;
	    }
	}

    }

    // SELECT Tag . *
    // FROM Tag, image, image_tag
    // WHERE image.filename = 'IMAG0267.jpg'
    // AND image.image_id = image_tag.image_id
    // AND Tag.tag_id = image_tag.tag_id
    // LIMIT 0 , 30

    public List<Tag> getTagsOfResource(String id, String type) {
	// EntityManagerHelper.log("finding Image instance with property: "
	// + propertyName + ", value: " + value, Level.INFO, null);
	try {
	    String queryString = null;
	    if (type.equalsIgnoreCase(SuperDAO.RESOURCE_TYPE_IMAGE)) {
		queryString = "SELECT DISTINCT t FROM Image i,ImageTag it, Tag t WHERE i.imageId=:id AND i.imageId = it.image.imageId AND it.tag.tagId = t.tagId";
	    } else if (type.contentEquals(SuperDAO.RESOURCE_TYPE_OUTFIT)) {
		queryString = "SELECT DISTINCT t FROM Outfit i,OutfitTag it, Tag t WHERE i.outfitId=:id AND i.outfitId = it.outfit.outfitId AND it.tag.tagId = t.tagId";
	    }
	    Query query = entityManager.createQuery(queryString);
	    query.setParameter("id", Integer.valueOf(id));
	    List result = query.getResultList();
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.ALL,re.getMessage());
	    
	    throw re;
	}
    }

    
    public User getCurentUser() {	
	FacesContext context = FacesContext.getCurrentInstance();
	HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
	Principal userPrinc = request.getUserPrincipal();
	return getUserByUserName( userPrinc.getName());
    }

    public User getUserByUserName(String username) {
	User user=null;
	String queryString = "SELECT DISTINCT u FROM User u WHERE username = :username";
	Query query=null;

	try {
	    query = entityManager.createQuery(queryString);
	    query.setParameter("username", username);
	    user = (User)query.getResultList().get(0);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    log.log(Level.ALL,e.getMessage());
	}
	
//	log.log(Level.FINEST,"current user in session " + username + " has id: " + user.getUserId());
	return user;

    }

    // SELECT images.* FROM Tag,images,image_tags WHERE Tag.tag_text='braun' AND
    // images.image_id = image_tags.image_id AND Tag.tag_id = image_tags.tag_id
//    @Interceptors(Monitoring.class)
    public List<Image> getImagesOfTag(String tags) {
	try {
	    
	    User u = getCurentUser();
	    String queryString = "SELECT DISTINCT i FROM Image i";
	    if (tags != null) {
		queryString = "SELECT DISTINCT i FROM Image i,ImageTag it, Tag t WHERE t.tagText=:Tag AND i.imageId = it.image.imageId AND t.tagId = it.tag.tagId AND i.user.userId=:userid";
	    } else {
		queryString = queryString.concat(" WHERE i.user.userId=:userid");
	    }
	    Query query = entityManager.createQuery(queryString);
	    query.setParameter("userid", u.getUserId());

	    // TODO hier mehrere Tag erm�glichen !?
	    if (tags != null) {
		query.setParameter("Tag", tags);
	    }

	    List result = query.getResultList();
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.ALL,re.getMessage());
	    
	    throw re;
	}
    }

    // SELECT images.* FROM Tag,images,image_tags WHERE Tag.tag_text='braun' AND
    // images.image_id = image_tags.image_id AND Tag.tag_id = image_tags.tag_id

    public List<Outfit> getOutfitsOfTag(String tags) {
	Integer userid = getCurentUser().getUserId();
	try {
	    String queryString = "SELECT DISTINCT o FROM Outfit o WHERE";
	    if (tags != null) {
		queryString = "SELECT DISTINCT o FROM Outfit o,OutfitTag it, Tag t WHERE t.tagText=:Tag AND o.outfitId = it.outfit.outfitId AND t.tagId = it.tag.tagId AND";
	    }
	    queryString = queryString.concat(" o.user.userId=:userid");
	    Query query = entityManager.createQuery(queryString);
	    query.setParameter("userid", userid);

	    // TODO hier mehrere Tag erm�glichen 
	    if (tags != null) {
		query.setParameter("Tag", tags);
	    }
	    List<Outfit> result = query.getResultList();
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.ALL,re.getMessage());
	    
	    throw re;
	}
    }

 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int saveOutfit(String outfitName, String imagesIds) {
	Outfit o = null;
	try {
	    outfitName = checkCreateOutfitName(outfitName);
	    o = new Outfit();
	    o.setName(outfitName);

	    o.setUser(this.getCurentUser());
	    entityManager.persist(o);
	    saveImagesForOutfit(imagesIds, o);

	} catch (Exception e) {
	    //TODO log
	    log.log(Level.ALL,e.getMessage());
	    sessioncontext.getRollbackOnly();
	    throw new PersistenceException(" "+this.getClass().getName()+".saveOutfit() Exception aufgetreten, Persistenzrollback durchgeführt.");//dies
	}
	return o.getOutfitId();
    }

 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String updateOutfit(String outfitName, String imageId, String leftPosition, String topPosition, String zindex) {

	Outfit o = new Outfit();
	o.setName(outfitName);
	try {

	    entityManager.find(Outfit.class, o);

	    //TODO ???
	    //	    if (outfitDB.size() > 0) {
//		o = outfitDB.get(0);
//	    } else {// neues outfit
//		return String.valueOf(saveOutfit(outfitName, imageId));
//	    }
	    o.setName(outfitName);
	    if (imageAlreadyInOutfit(imageId, outfitName))
		return null;// image ist bereit in outfit - TODO ggf. sp�ter
			    // entsprechende meldung an user (�ber exception
			    // l�sen)

	    ImageOutfit io = new ImageOutfit();
	    io.setLeftPosition(leftPosition);
	    io.setTopPosition(topPosition);
	    io.setOutfit(o);
	    io.setZindex(Integer.valueOf(zindex));
	    Image i = new Image();
	    i.setImageId(Integer.valueOf(imageId));
	    entityManager.find(Image.class,i);
	    io.setImage(i);

	    Set<ImageOutfit> s = new HashSet<ImageOutfit>();
	    s.add(io);
	    o.setImageOutfits(s);

	    entityManager.persist(o);

	    
		entityManager.persist(io);

	    // saveImagesForOutfit(imageId, o.getOutfitId());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    log.log(Level.ALL,"rollback wird gestartet, "+e.getMessage());
	    sessioncontext.getRollbackOnly();
	    return "false";
	}
	// return String.valueOf(o.getOutfitId());
	return "true";

    }

    // pr�fen ob image bereits zu outfit geh�rt
    private boolean imageAlreadyInOutfit(String imageId, String outfitName) {

	    // TODO hier sql count verwenden: select * FROM image_outfit, outfit
	    // WHERE outfit.outfit_id=image_outfit.outfit_id AND
	    // outfit.name='outfit1' AND image_outfit.image_id = 158
	    String queryString = "SELECT * FROM image_outfit, outfit WHERE outfit.outfit_id=image_outfit.outfit_id AND outfit.name=:outfitname AND image_outfit.image_id =:imageId";
	    Query query = entityManager.createQuery(queryString);

	    query.setParameter("imageId", imageId);
	    query.setParameter("outfitname", outfitName);
	    List result = query.getResultList();
	    if (result.size() >= 1)
		return true;
	    else
		return false;

	 
    }

    public String checkCreateOutfitName(String outfitName) {
	List<Outfit> outfitDB = this.findObjectByProperties("Outfit", "name", "user_id", outfitName, this.getCurentUser().getUserId()); // checken
																	// ob
																	// name
																	// bereits
																	// vergeben
	if (outfitDB.size() > 0) {
	    outfitName = checkCreateOutfitName(outfitName.concat("1"));// TODO
								       // hier
								       // letzte
								       // zahl
								       // hochz�hlen
	}
	return outfitName;
    }

 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveImagesForOutfit(String imagesIds, Outfit o) {

	StringTokenizer st = new StringTokenizer(imagesIds, ",");
	ImageOutfit oi = new ImageOutfit();
	try {
	    while (st.hasMoreElements()) {
	        String imageId = (String) st.nextElement();
	        Image i = entityManager.find(Image.class, Integer.parseInt(imageId) );
	        oi.setImage(i);
	        oi.setOutfit(o);
	        oi.setLeftPosition("500px");// TODO
	        oi.setTopPosition("50px");// TODO
	        oi.setZindex(2);
	        entityManager.persist(oi);
	    }
	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    log.log(Level.ALL,e.getMessage());
	}

    }

    // SELECT * FROM image,image_outfit where image_outfit.outfit_id = '1' AND
    // image.image_id = image_outfit.image_id
    public List<Image> getImagesOfOutfit(Integer outfit_Id) {
	try {
	    String queryString = "SELECT DISTINCT i FROM Image i,ImageOutfit oi WHERE oi.outfit.outfitId=:outfit_Id AND i.imageId = oi.image.imageId  ";
	    Query query = entityManager.createQuery(queryString);
	    query.setParameter("outfit_Id", outfit_Id);
	    List<Image> result = query.getResultList();
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.ALL," get images of Tag failed" + re.getMessage());
	    
	    throw re;
	}
    }

    //TODO berechtigung prüfen
 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(String id, String type) {

	if (type.equalsIgnoreCase(SuperDAO.RESOURCE_TYPE_IMAGE)) {
	    Image i = entityManager.find(Image.class, Integer.parseInt(id));
	    i.setImageId(Integer.parseInt(id));
	    entityManager.remove(i);
	    // TODO l�sche bild von festplatte (asynchron / parallel thread)
	}

	if (type.equalsIgnoreCase(SuperDAO.RESOURCE_TYPE_OUTFIT)) {
	    Outfit o ;
	    o = entityManager.find(Outfit.class,  Integer.parseInt(id));
	    entityManager.remove(o);
	    User user = getCurentUser();
	    user.setCurrentOutfitId(null);
	    entityManager.persist(user);
	}

    }

    
    public List findObjectByProperties(String model, String propertyName, String propertyName1, Object value, Object value1) {
//	log.log(Level.FINEST,"finding Outfit instance with property: " + propertyName + ", value: " + value);
	try {
	    String queryString = "SELECT o FROM Outfit o, User u LEFT JOIN FETCH o.user WHERE o.name = :name AND u.userId = :userid ";

	    Query queryObject = entityManager.createQuery(queryString);
	    queryObject.setParameter("name", value);
	    queryObject.setParameter("userid", value1);
	    List returnlist = queryObject.getResultList();
	    return returnlist;
	} catch (Exception re) {
	    log.log(Level.ALL,"find by property name failed", re);
	    return null;//TODO ??
	}
    }

 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImageOutfit(String outfitid, String imageid, String top, String left, String zindex) {
	    try {

	        Outfit o = (Outfit) entityManager.find(Outfit.class, Integer.parseInt(outfitid.trim()));
	        Set<ImageOutfit> images = o.getImageOutfits();

	        Iterator<ImageOutfit> iter = images.iterator();
	        while (iter.hasNext()) {
        	    	ImageOutfit io = iter.next();
        	    	if (imageid.equalsIgnoreCase(String.valueOf(io.getImage().getImageId()))) {
        	    	    if (left != null)
        	    		io.setLeftPosition(left);
        	    	    if (top != null)
        	    		io.setTopPosition(top);
        	    	    if (zindex != null)
        	    		io.setZindex(Integer.valueOf(zindex));
        	    	    
        //	    	    entityManager.flush();
        	    	}
	        }
	    } catch (NumberFormatException e) {
	        // TODO Auto-generated catch block
		log.log(Level.ALL,e.getMessage());
    	    } catch (NullPointerException e) {
        	    // TODO Auto-generated catch block
    		log.log(Level.ALL,e.getMessage());
    	    } catch (Exception e) {
	    // TODO Auto-generated catch block
		log.log(Level.ALL,e.getMessage());
	    }
    }

    
    //TODO - prüfen ob das letzte image gelöscht wurde, wenn keine images an einem outfit hängen kann es nicht mehr in der gallery ausgegeben werden 
 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImageOfOutfit(String outfitId, String imageId) {
	try {
	    Outfit o = entityManager.find(Outfit.class, Integer.valueOf(outfitId));
	    Iterator<ImageOutfit> iter = o.getImageOutfits().iterator();
	    while (iter.hasNext()) {
	        ImageOutfit imageOutfit = iter.next();
	        if (imageId.equalsIgnoreCase(String.valueOf(imageOutfit.getImage().getImageId()))) {

	    	    entityManager.remove(imageOutfit);
	    	    o.getImageOutfits().remove(imageOutfit);
	    	    entityManager.flush();

	        }
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    log.log(Level.ALL,e.getMessage());
	}
    }

    // ermittelt den n�chst h�ren zindex eines anderen bildes und setzt diesen
    // einen h�her
 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer updateImageZPositionofOutfit(String outfitid, String imageid, String zindex) {
	ImageOutfit currentImageOutfit = null;
	Integer nextHigherElementID = null;
	Integer nextHigherElementZIndex = null;

	try {
	    Outfit o = entityManager.find(Outfit.class, Integer.parseInt(outfitid));
	    Set<ImageOutfit> images = o.getImageOutfits();

	    Iterator<ImageOutfit> iter = images.iterator();

	    while (iter.hasNext()) {
		ImageOutfit io = iter.next();
		if (imageid.equalsIgnoreCase(String.valueOf(io.getImage().getImageId()))) {
		    currentImageOutfit = io;
		} else {
		    if (Integer.valueOf(io.getZindex()) > Integer.valueOf(zindex)) {
			if (nextHigherElementID == null) {
			    nextHigherElementID = io.getRecordId();
			    nextHigherElementZIndex = io.getZindex();
			} else {
			    if (nextHigherElementZIndex > Integer.valueOf(io.getZindex())) {
				nextHigherElementID = io.getRecordId();
				nextHigherElementZIndex = io.getZindex();
			    }
			}
		    }

		}
	    }
	    if (nextHigherElementID == null){
		nextHigherElementZIndex = new Integer(zindex);
	    }
	   
	    if (currentImageOutfit == null) {
		throw new IllegalArgumentException("imageOutfit not found");
	    } else{
		
		entityManager.flush();
		    
	    }
	    currentImageOutfit.setZindex(nextHigherElementZIndex++);

	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    log.log(Level.ALL,e.getMessage());
		sessioncontext.getRollbackOnly();
	    //TODO richtig?
	}

	return nextHigherElementZIndex;
    }

    public void setCurrentOutfitId(String oufitId){
	    User user = this.getCurentUser();
	    user.setCurrentOutfitId( Integer.parseInt(oufitId) );
	    entityManager.flush();
    }

 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Image saveImageEntry(FileBean file) {

	User currentUser = this.getCurentUser(); 
	Image image = null; 
	    		
	    image = new Image();
	    image.setFilename(file.getName()); 
	    image.setName("");
	    image.setUser(currentUser);
	    this.entityManager.persist( image );
	    			
	return image;
    }

    public <T> T find(Class<T> entityClass, Object primaryKey) {
	return entityManager.find(entityClass,primaryKey);

    }
}