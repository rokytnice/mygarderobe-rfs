package de.rochlitz.mygarderobe.bean.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;

import de.rochlitz.mygarderobe.beans.FileBean;
import de.rochlitz.mygarderobe.constants.MyGarderobeConstant;
import de.rochlitz.mygarderobe.jpa.SuperDAO;
import de.rochlitz.mygarderobe.jpa.entity.Image;
import de.rochlitz.mygarderobe.managedBean.PushClientCommandManager;

@Stateless(name = "imageProcessingUtil")
public class ImageProcessingUtil {

    @EJB(beanName = "SuperDAO")
    public SuperDAO dao;
    @Inject
    private Logger log;

    public static void rotateImageOnStorage(String filename, String uploadPath) {
	// String uploadVerz =
	// getServletContext().getRealPath(DESTINATION_DIR_PATH);//vm parameter
	// lesen
	// String filename = "A1.JPG";
	String fileFormatType = filename.substring(filename.indexOf('.') + 1);

	BufferedImage img = null;
	File image = null;
	try {
	    image = new File(uploadPath + "\\" + filename);
	    img = ImageIO.read(image);
	} catch (IOException e) {
	}

	img = rotateImage(img, 90);
	try {
	    BufferedImage bi = img; // retrieve image
	    ImageIO.write(bi, fileFormatType, image);
	} catch (IOException e) {
	    // TODO LOGGER
	    System.err.print(e.getMessage());
	}
	System.out.print(uploadPath + filename + " rotated");
    }

    public static BufferedImage rotateImage(BufferedImage img, int dir1) {
	AffineTransform affineTransform = AffineTransform.getRotateInstance(Math.toRadians(dir1), img.getWidth() / 2, img.getHeight() / 2);
	BufferedImage rotatedImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
	g.setTransform(affineTransform);
	g.drawImage(img, 0, 0, null);
	return rotatedImage;
    }


    public void createThumbnails(int newWidthSizePixel, String uloadPath, List<Image> images) throws IOException {

	Iterator<de.rochlitz.mygarderobe.jpa.entity.Image> iter = images.iterator();
	while (iter.hasNext()) {
	    de.rochlitz.mygarderobe.jpa.entity.Image image = iter.next();
	    String fileName = image.getFilename();
	    String pathToFile = uloadPath + File.separator + dao.getCurentUser().getUserId() + File.separator + fileName;

	    BufferedImage img1 = null;
	    BufferedImage scaledImg = null;
	    File file = new File(pathToFile);
	    if (file.isDirectory())
		continue;
	    try {
		img1 = ImageIO.read(new File(pathToFile));
		scaledImg = Scalr.resize(img1, newWidthSizePixel);

		File f = new File(uloadPath + File.separator + dao.getCurentUser().getUserId() + File.separator + newWidthSizePixel);
		if (!f.isDirectory()) {
		    f.mkdir();
		}
		ImageIO.write(scaledImg, "jpeg", new File(f.getAbsolutePath() + File.separator + fileName));
		log.log(Level.FINE, "File created: "+f.getAbsolutePath() + File.separator + fileName);
	    } catch (IOException e1) {
		log.log(Level.SEVERE, e1.getMessage());
		throw new IOException(e1);
	    } // load image

	}
    }

    public static BufferedImage getThumbnail(BufferedImage image, int maxThumbWidth, int maxThumbHeight, int hints) {
	BufferedImage thumbnail = null;
	if (image != null) {
	    AffineTransform tx = new AffineTransform();
	    // Determine scale so image is not larger than the max height and/or
	    // width.
	    double scale = scaleToFit(image.getWidth(), image.getHeight(), maxThumbWidth, maxThumbHeight);

	    tx.scale(scale, scale);

	    double d1 = (double) image.getWidth() * scale;
	    double d2 = (double) image.getHeight() * scale;
	    thumbnail = new BufferedImage(((int) d1) < 1 ? 1 : (int) d1, // don\"t
									 // allow
									 // width
									 // to
									 // be
									 // less
									 // than
									 // 1
		    ((int) d2) < 1 ? 1 : (int) d2, // don\"t allow height to be
						   // less than 1
		    image.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_RGB : image.getType());
	    Graphics2D g2d = thumbnail.createGraphics();
	    // g2d.setRenderingHints(hints);
	    g2d.drawImage(image, tx, null);
	    g2d.dispose();
	}
	return thumbnail;
    }

    private static double scaleToFit(double w1, double h1, double w2, double h2) {
	double scale = 1.0D;
	if (w1 > h1) {
	    if (w1 > w2)
		scale = w2 / w1;
	    h1 *= scale;
	    if (h1 > h2)
		scale *= h2 / h1;
	} else {
	    if (h1 > h2)
		scale = h2 / h1;
	    w1 *= scale;
	    if (w1 > w2)
		scale *= w2 / w1;
	}
	return scale;
    }

    
    //TODO ejb transaction rollback wenn exception ????????
    public void doCropImage(String fileName, String x1, String y1, String x2, String y2, String width, String height, HttpServletRequest request,
	    String outfitImageSize) throws Exception {
	// String uploadPath =
	// request.getRealPath(constants.destination_dir_name);
	String uploadPath = MyGarderobeConstant.upload_dir_name;
	// String pathToFile = uloadPath + File.separator +
	// daoU.getCurentUser().getUserId() + File.separator +
	// de.rochlitz.mygarderobe.Constants.sizeMain + File.separator +
	// fileName.toLowerCase();
	String pathToEditedFile = uploadPath + File.separator + dao.getCurentUser().getUserId() + File.separator + outfitImageSize + File.separator
		+ fileName;
	String pathToFileOrginalFile = uploadPath + File.separator + dao.getCurentUser().getUserId() + File.separator + fileName;

	// backup org file
	copyFile(pathToFileOrginalFile, pathToFileOrginalFile + ".org");
	File fileOrginal = new File(pathToFileOrginalFile);
	BufferedImage imageOrginal = null;
	File fileEdited = new File(pathToEditedFile);
	BufferedImage imageEdited = null;
	try {
	    imageOrginal = ImageIO.read(fileOrginal);
	    imageEdited = ImageIO.read(fileEdited);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	BufferedImage clipped = null;
	try {
	    int w = Integer.valueOf(width) * imageOrginal.getWidth() / imageEdited.getWidth();
	    int h = Integer.valueOf(height) * imageOrginal.getHeight() / imageEdited.getHeight();
//	    int x0 = (Integer.valueOf(x2) * 100 / Integer.valueOf(imageEdited.getWidth())) * imageOrginal.getWidth() / 100;
//	    int y0 = (Integer.valueOf(y2) * 100 / Integer.valueOf(imageEdited.getHeight())) * imageOrginal.getHeight() / 100;
	    int x = (Integer.valueOf(x1) * 100 / Integer.valueOf(imageEdited.getWidth())) * imageOrginal.getWidth() / 100;
	    int y = (Integer.valueOf(y1) * 100 / Integer.valueOf(imageEdited.getHeight())) * imageOrginal.getHeight() / 100;
	    clipped = imageOrginal.getSubimage(x, y, w, h);
	} catch (RasterFormatException rfe) {
	    System.out.println("raster format error: " + rfe.getMessage());
	    return;
	}
	try {
	    ImageIO.write(clipped, "jpeg", new File(pathToFileOrginalFile));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	createThumnailsOfImage(fileName, uploadPath);
    }

    private void createThumnailsOfImage(String fileName, String uploadPath) throws Exception {
	// thumbnails neu erzeugen
	Image image = new Image();
	image.setFilename(fileName);
	image.setName("");
	image.setUser(dao.getCurentUser());
	List<Image> imageList = new ArrayList<Image>();
	imageList.add(image);
	createAllThumbnails(uploadPath, imageList);
    }

    public void copyFile(String orgPath, String destPath) {
	BufferedImage orgImage = null;
	File oFile = new File(orgPath);
	File dFile = new File(destPath);

	if (dFile.exists())
	    return;

	try {
	    orgImage = ImageIO.read(oFile);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	try {
	    ImageIO.write(orgImage, "jpeg", new File(destPath));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public void createAllThumbnails(String uploadPath, List<Image> imageList) throws Exception {

	try {
	    this.createThumbnails(Integer.valueOf(MyGarderobeConstant.size100), uploadPath, imageList);
	    this.createThumbnails(Integer.valueOf(MyGarderobeConstant.size150), uploadPath, imageList);
	    this.createThumbnails(Integer.valueOf(MyGarderobeConstant.size200), uploadPath, imageList);
	    this.createThumbnails(Integer.valueOf(MyGarderobeConstant.sizeMain), uploadPath, imageList);
	} catch (NumberFormatException e) {
	    log.log(Level.SEVERE,  e.getMessage());
	    throw new Exception(e);
	} catch (IOException e) {
	    log.log(Level.SEVERE,   e.getMessage() );
	    throw new Exception(e);
	}
	

    }

    //TODO ejb transaction rollback wenn exception?
    public void doRestoreImage(String filename, HttpServletRequest request) throws Exception {
	// String uploadPath =
	// request.getRealPath(constants.destination_dir_name);
	String uploadPath = MyGarderobeConstant.upload_dir_name;
	String pathToFileOrginalFile = uploadPath + File.separator + dao.getCurentUser().getUserId() + File.separator + filename;
	// backup org file

	if (!(new File(pathToFileOrginalFile + ".org").exists()))
	    return;
	new File(pathToFileOrginalFile).delete();
	copyFile(pathToFileOrginalFile + ".org", pathToFileOrginalFile); // TODO
									 // meldung
									 // an
									 // user
									 // ausgeben
									 // -
									 // wenn
									 // kein
									 // backup
									 // vorhanden
	createThumnailsOfImage(filename, uploadPath);

    }

    // TODO check max upload file size
    public void persistFiles(ArrayList<FileBean> list) throws ServletException, IOException {

	Iterator<FileBean> it = list.iterator();
	while (it.hasNext()) {
	    FileBean fb = it.next();
	    try {
		persistFile(fb);
	    } catch (Exception e) {
		log.log(Level.SEVERE, "Fehler bei persitieren einer datei " + e.getMessage());
//		    throw new Exception(001);
	    }

	}
    }

 // Defines transaction attribute for method 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Image> persistFile(FileBean file) throws ServletException, IOException, Exception {
	String lowerFileName = file.getName().toLowerCase();
	
	file.setName(lowerFileName);
	try {
	    File destinationDir = new File(MyGarderobeConstant.upload_dir_name + File.separator + dao.getCurentUser().getUserId());
	    if (!destinationDir.isDirectory()) {
		destinationDir.mkdir();
	    }
	    FileOutputStream fos = null;
	    String filename = file.getName();// request.getHeader("X-File-Name");
	    try {
		String path = destinationDir.getAbsolutePath() + File.separator + filename;
		fos = new FileOutputStream(path);
		fos.write(file.getData());

	    } finally {
		try {
		    fos.close();
		    // is.close();
		} catch (IOException ignored) {
		}
	    }

	    Image image = dao.saveImageEntry(file);

	    String uploadPath = MyGarderobeConstant.upload_dir_name;
	    List<Image> imageList = new ArrayList<Image>();
	    imageList.add(image);
	    createAllThumbnails(uploadPath, imageList);
	    return imageList;
	    
	} catch (Exception ex) {
	    log.log(Level.SEVERE, "Fehler bei PuchClientCommand.sendClient " + ex.getMessage());
	    throw new Exception( );
	}

    }
    
    
    
   

   
}
