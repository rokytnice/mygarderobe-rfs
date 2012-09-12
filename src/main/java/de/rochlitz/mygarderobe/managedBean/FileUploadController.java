/**
 * 
 */
package de.rochlitz.mygarderobe.managedBean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import de.rochlitz.mygarderobe.bean.utils.ImageProcessingUtil;
import de.rochlitz.mygarderobe.beans.FileBean;
import de.rochlitz.mygarderobe.constants.MyGarderobeConstant;
import de.rochlitz.mygarderobe.jpa.entity.Image;

/**
 * @author Ilya Shaikovsky
 *
 */
@ManagedBean(name="fileUploadController", eager=true)
@ViewScoped
public class FileUploadController implements Serializable{//Note that the bean needs to implement Serializable as it will be stored in the view map which is in turn stored in the sessio
    
    /**
     * 
     */
    private static final long serialVersionUID = 5821450156651483565L;
    private ArrayList<FileBean> files = new ArrayList<FileBean>();
    private int uploadsAvailable = 10;
    private boolean autoUpload = true;
    private boolean useFlash = false;
    @Inject
    PushClientCommandManager pushClientCommandManager;
    
    

    
    @EJB(beanName="imageProcessingUtil")
    private ImageProcessingUtil imageProcessingUtil;
    @Inject private Logger log;
    
    public int getSize() {
        if (getFiles().size()>0){
            return getFiles().size();
        }else 
        {
            return 0;
        }
    }

    public FileUploadController() {
    }

    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer)object).getData());
    }
    public void listener(FileUploadEvent event) {
	UploadedFile item = event.getUploadedFile();
        FileBean file = new FileBean();
        file.setLength(item.getData().length);
        file.setName(item.getName());
        file.setData(item.getData());
        files.add(file);
        uploadsAvailable--;
        
        try {
            List<Image> imageList = imageProcessingUtil.persistFile(file);
	    pushClientCommand(imageList);
	} catch (ServletException e) {
	    log.log(Level.SEVERE,e.getMessage());
	} catch (IOException e) {
	    log.log(Level.SEVERE,e.getMessage());
	} catch (Exception e) {
	    log.log(Level.SEVERE,e.getMessage());	    
	}
    }  
      
    /**
     * ruft ajax push manager auf
     * @param commandJavascript
     * @throws Exception 
     */
    private void callPushManager(String commandJavascript) throws Exception {
	pushClientCommandManager.setClientCommand(commandJavascript);
	pushClientCommandManager.sendClientCommand();
	
    }
    
    
    /**
     * erzeugt json array mit javascriptfunction und parameter zum senden per push an client
     * @param imageList
     * @throws Exception 
     */
    private void pushClientCommand(List<Image> imageList) throws Exception {
	
	//TODO benutze JSONObject obj = new JSONObject();	  LinkedHashMap m1 = new LinkedHashMap();
	Iterator<Image> iter = imageList.iterator();
	String commandJavascript = " {\"functionname\": \""+MyGarderobeConstant.javascript_Function_name_push_images+"\", \"functiondata\" :[ ";
	
	boolean printKomma = false;
	while(iter.hasNext()){
	    Image image =  iter.next();
	    if(printKomma) commandJavascript.concat(",");
	    commandJavascript = commandJavascript.concat("{\"fileName\":\""+image.getFilename()+"\", \"id\":\""+image.getImageId()+"\"} ");
	    printKomma=true;
	}
	commandJavascript = commandJavascript.concat("]}");
	try {
	    callPushManager(commandJavascript);
	    log.log(Level.FINE, "ajax push command was send: "+commandJavascript );
	} catch (Exception e) {
	    log.log(Level.SEVERE, "Fehler bei PuchClientCommand.sendClient " + e.getMessage());
	    throw new Exception( );
	}
	
	
    }


    public String clearUploadData() {
        files.clear();
        setUploadsAvailable(5);
        return null;
    }
    
    public long getTimeStamp(){
        return System.currentTimeMillis();
    }
    
    public ArrayList<FileBean> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileBean> files) { 
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }

}