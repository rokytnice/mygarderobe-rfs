<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.User"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.Outfit"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.ImageOutfit"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.OutfitDAO"%>
<%@page import="de.rochlitz.mygarderobe.oldjpa.SuperDAO"%>

<%
    String outfitid = request.getParameter("outfitid");
SuperDAO daoU = new SuperDAO();
User user = daoU.getCurentUser();

if(outfitid==null||outfitid.equalsIgnoreCase("null")){
    outfitid=null;
    outfitid = String.valueOf( user.getCurrentOutfitId() );
}else{
    daoU.setCurrentOutfitId( outfitid );
}
if(outfitid==null||outfitid.equalsIgnoreCase("null")){
	out.print(false);
}else{
    
	OutfitDAO oDAO = new OutfitDAO();
	Outfit o = oDAO.findById(Integer.parseInt(outfitid));
	if(o == null){
    out.print(false);
	}
	else{
    
		
		Set<ImageOutfit> images =     o.getImageOutfits();
		java.util.Iterator<ImageOutfit> iter = images.iterator();
		
		out.print("[" );
		while(iter.hasNext()){
			ImageOutfit io = iter.next();
			out.print( "{");
			out.print( "\"outfitname\": " + "\"" +o.getName() + "\",");
			out.print( "\"imageid\": " + "\"" +io.getImage().getImageId() + "\",");
			out.print( "\"filename\": " + "\"" +io.getImage().getFilename() + "\",");
			out.print( "\"name\": " + "\"" +io.getImage().getName() + "\",");
			out.print( "\"top\": " + "\"" +io.getTopPosition() + "\",");
			out.print( "\"left\": " + "\"" +io.getLeftPosition() + "\",");
			out.print( "\"zindex\": " + "\"" +io.getZindex() + "\"");
			out.print( "}");
			if(iter.hasNext()) out.print(",");
		}
		out.print("]" );
	}
}
%>