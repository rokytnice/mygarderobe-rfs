package de.rochlitz.mygarderobe.constants;

import java.io.File;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="myGarderobeConstant", eager=true) 
@ApplicationScoped
public class MyGarderobeConstant   {

    	public static String upload_dir_name = File.separator+"temp"+File.separator+"uploads";
//    	public static String destination_dir_name = "resources"+File.separator+"uploads";;// = System.getProperty("uploadpath");
//    	public static String tmp_dir_path = File.separator+"temp";// = System.getProperty("temp");
    	public static String appName = "mygarderobe-rfs";// = System.getProperty("temp");
	public static String imagesServletName = "image";// = System.getProperty("temp");
	public static String  iconRootPath = 	"/"+appName+"/javax.faces.resource/";
	
	//TODO konfigurierbar machen
	public  final static String size100 = "100";
	public  final static String size150 = "150";
	public  final static String size200 = "200";
	public  final static String sizeMain = "300";
	
	public final static String javascript_Function_name_push_images = "pushImages";
	
	public String getAppName() {
	    return appName;
	}


	public MyGarderobeConstant() {
	    super();
	}
    
	public  String getUpload_dir_name() {
	    return upload_dir_name;
	}
	public  void setUpload_dir_name(String upload_dir_name) {
	    MyGarderobeConstant.upload_dir_name = upload_dir_name;
	}
	
	public  String getSize100() {
	    return size100;
	}
	public  String getSize150() {
	    return size150;
	}
	public  String getSize200() {
	    return size200;
	}
	public  String getSizemain() {
	    return sizeMain;
	}


	public static String getImagesServletName() {
	    return imagesServletName;
	}


	public static void setImagesServletName(String imagesServletName) {
	    MyGarderobeConstant.imagesServletName = imagesServletName;
	}


	public static String getJavascriptFunctionNamePushImages() {
	    return javascript_Function_name_push_images;
	}


	public static void setAppName(String appName) {
	    MyGarderobeConstant.appName = appName;
	}


	public static String getIconRootPath() {
	    return iconRootPath;
	}


	public static void setIconRootPath(String iconRootPath) {
	    MyGarderobeConstant.iconRootPath = iconRootPath;
	}
	
//	public MyGarderobeConstant(String upload_dir_name, String destination_dir_name, String tmp_dir_path) {
//	    super();
//	    this.upload_dir_name = upload_dir_name;
//	    this.destination_dir_name = destination_dir_name;
//	    this.tmp_dir_path = tmp_dir_path;
//	}

  

//    public Constants(){
	
//	if(os.equalsIgnoreCase("Linux") ){
//	    DESTINATION_DIR_NAME = "/usr/local/apache-tomcat/apache-tomcat-6.0.29/webapps/myGarderobe/"+Constants.UPLOAD_DIR_NAME;
//	    TMP_DIR_PATH = "/tmp";
//	}else{
//	    DESTINATION_DIR_NAME = System.getProperty("uploadpath");
//	    TMP_DIR_PATH  = System.getProperty("temp");
//	}
	
//    }

	
	
}
