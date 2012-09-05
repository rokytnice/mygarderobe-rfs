<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.lang.System"%>
<%@page import="java.util.Set"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.ImageOutfit"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.Image"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.Outfit"%>

<%
    String imageFilter = request.getParameter("imageFilter");
	SuperDAO daoU = new SuperDAO();
	String uploadDirName = de.rochlitz.mygarderobe.de.rochlitz.mygarderobe.jpa.MyGarderobeConstant.upload_dir_name + "/" + daoU.getCurentUser().getUserId();

	java.util.List<Outfit> outfits = daoU.getOutfitsOfTag(imageFilter);
	java.util.Iterator  iter = outfits.iterator();
	String thumnailSize = request.getParameter("thumbnailSize");//TODO konfiguriebar machen
	
	while(iter.hasNext()){
    	Outfit outfit = (Outfit) iter.next();
    	int ii = outfit.getOutfitId();
    	java.util.List<Image> imagesOfOutfit = daoU.getImagesOfOutfit(ii);
    	java.util.Iterator  iter2 = imagesOfOutfit.iterator();
    	
    	
    	//TODO erstes image als repräsentation für die gallerie der outfits - später andere lösung 
    	if(!iter2.hasNext()) continue;
    	Image image = (Image) iter2.next();
    	int i = image.getImageId();
    	out.print("<div style=\"position:relative;top:0px; right:0px; z-index:2; display: inline; \" class=\"divListButton\" id=\"div"+outfit.getOutfitId()+"\">");
    	out.print("<li>\n");
			out.print("<img src=\"");
    	out.print(uploadDirName+"/"+thumnailSize+"/"+image.getFilename());
    	out.print("\" class=\"listImage\" title=\" bild titel"+i+"\" alt=\"bild name\""+i+"\"  name=\"bild"+i+"\" id=\"bild"+i+"\" />");
    	
    	out.print("</li>\n");
			  
    	//TODO href kann raus - mouse over dafür rein
	  //TODO positin der pixel in abhänggkeit von thumbnail größe
	  			 
			  out.print("<a  onclick=\"chooseOutfit('"+i+"','"+outfit.getOutfitId()+"')\" id='chooselink"+outfit.getOutfitId()+"' class=\"visible\"><img src=\"ICO/Blue/24/ok.ico\" class=\"listButton\"></img></a>");
			  out.print("<a  onclick=\"idOfTaggedObject= '"+outfit.getOutfitId()+"';tagOutfit = '"+outfit.getOutfitId()+"';loadTagingFunctions("+outfit.getOutfitId()+");\" class='addTag' id='addTag"+outfit.getOutfitId()+"'  ><img src=\"ICO/Blue/24/chat.ico\" class=\"listButton\"></img></a>");
			  out.print("<a  onclick=\"deleteOutfit('"+outfit.getOutfitId()+"','"+outfit.getName()+"')\" id='deleteTag"+outfit.getOutfitId()+"' class=\"visible\"><img src=\"ICO/Blue/32/close.ico\" class=\"listButton\"></img></a>");
//			  out.print("<a  onclick=\"editName('"+i+"','"+outfit.getOutfitId()+"')\" id='chooselink"+outfit.getOutfitId()+"' class=\"visible\"><p style=\"position:relative;top:-30px; right:200px; z-index:2; display: inline; font-weight: bold; font-size:12px;\">"+outfit.getName()+"</p></a>");
			  out.print("</div>");
    }
    out.print("<div style='display:none' id=\"lastelementfromserver\"></div>");
%>



