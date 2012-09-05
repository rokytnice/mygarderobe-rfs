/**
 * 
 */
package de.rochlitz.mygarderobe.managedBean;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import de.rochlitz.mygarderobe.beans.FileBean;
import de.rochlitz.mygarderobe.utils.ImageProcessingUtil;

/**
 * @author Ilya Shaikovsky
 *
 */
@ManagedBean(name="fileUploadController", eager=true)
@ViewScoped
public class FileUploadController{
    
    private ArrayList<FileBean> files = new ArrayList<FileBean>();
    private int uploadsAvailable = 10;
    private boolean autoUpload = true;
    private boolean useFlash = false;
    

    
    @EJB(beanName="imageProcessingUtil")
    private ImageProcessingUtil imageProcessingUtil;
    
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
    public void listener(FileUploadEvent event) throws Exception{
	UploadedFile item = event.getUploadedFile();
        FileBean file = new FileBean();
        file.setLength(item.getData().length);
        file.setName(item.getName());
        file.setData(item.getData());
        files.add(file);
        uploadsAvailable--;
        
        imageProcessingUtil.persistFile(file);
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