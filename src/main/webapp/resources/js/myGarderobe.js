//TODO feature beim umschalten zwischen images und outfits, keinen neuen request sondern über dom manipulation 

var documentReady = false;
var availableTags = ['ajax','deutsch'];//f�r tagging
var idOfTaggedObject = ''; //speichert die id/name des bildes f�r das das tagging gemacht wird
var currentOutfit = new Array();//speichert die bildId des des Outfits
var currentOutfitID = null;
var currentMode = 'image';//image or outfit
var thumbnailSize = '100';
var outfitImageSize = '300';//public static final String sizeMain = "300";
var currentZIndexForNewImagesOfCurrentOutfit = 2;
var appName = "mygarderobe-rfs";
var imageRoot = "/"+appName+"/image/";
var userFolder;
var iconRootPath = 	"/"+appName+"/javax.faces.resource/";
var controlerRootPath = "/"+appName+"/app/";

//initiale position eines bildes beim ausw�hlen zu einem outfit
var init_posy;  
var init_posx;  
var init_zIndex = "2";

function callFunction(pushedJSONData){
	console.log(pushedJSONData);
	var obj = jQuery.parseJSON(pushedJSONData);
	window[obj.functionname](obj.functiondata);
}

//server ajax push new images (diff)
function pushImages(pushedJSONData){
	bilderElement = document.getElementById("bilder");

	for (var i = 0; i < pushedJSONData.length; ++i){
		newImageEntry = createNewImagelistEntry(pushedJSONData[i].fileName,pushedJSONData[i].id);
		bilderElement.appendChild(newImageEntry);
	}
}

function createNewImagelistEntry(filename,id){
	var newImageLi = document.createElement('li');
	
	var newImageDiv = document.createElement('div');
	newImageDiv.setAttribute("class","divImage" );	
	
	var newImage = document.createElement('img');
	newImage.setAttribute("class","listImage" );
	newImage.setAttribute("src", imageRoot+currentUserId+"/"+thumbnailSize+"/"+filename);//siehe ImageProcessingUtil.pushClientCommand()
	
	var newDivForButtons = document.createElement('div');
	newDivForButtons.setAttribute("class","divListButton" );
	
	var buttonChoose = document.createElement('a');
	buttonChoose.setAttribute("class", "visible");
	buttonChoose.setAttribute("onclick", "chooseImage('"+id+"','"+filename+"')");
	var buttonChooseImg = document.createElement('img');
	buttonChooseImg.setAttribute("src", iconRootPath+"ok.ico.jsf?ln=ICO/Blue/32");
	//TODO andere button dazu
	
	newImageLi.appendChild(newImageDiv);
	newImageDiv.appendChild(newImage);
	newImageDiv.appendChild(newDivForButtons);
	newDivForButtons.appendChild(buttonChoose);
	buttonChoose.appendChild(buttonChooseImg);
	
	
	return newImageLi;
}

function getCurrentUserFolder(){	  
	  $.getJSON(controlerRootPath+"getCurrentUserFolder.jsf", function(data) {
//		  alert(data);
		  return userFolder =  data;		 
		});
}


//TODO nicht immer getimages aufrufen sondern img pfade im frontend �ndern
function setThumnailSize(size){
	thumbnailSize = size;
	if(currentMode == "image")
		{getImages();}
	if(currentMode == "outfit")
		{getOutfits();}
	
}

function executeRemoteJavascript(scriptFilename) {
	$.getScript(scriptFilename, function(data, textStatus) {
		console.log(data); // data returned
			console.log(textStatus); // success
			console.log('Load was performed.');

		});
//	alert(data);
}


//TODO request.readyState);  pr�fen ob vollst�ndig geladen

window.onload = function() {
	// alert('start get');
	// getImages();
	// executeRemoteJavascript('test.js');
	//alert('end get');
	
}

function pause(mills){
	var date = new Date();
	var curDate = null;
	do{curDate=new Date();}
	while(curDate-date<mills);
}


function getOutfits(id) {
//	showElement("loadingscreen");
	userFolder = getCurrentUserFolder();
	bilder = document.getElementById("bilder");
	imageFilter = document.getElementById("imageFilter").value;
	
	bilderRootElementGeladen = false;
	while( !bilderRootElementGeladen  ){
		try
		{
			bilderRootElementGeladen = true;
		}
		catch(err)//wenn element noch nicht da in der schleife bleiben
		{
			bilderRootElementGeladen=false;
		} 
	}
	var url = controlerRootPath+"getOutfits.jsf?thumbnailSize="+thumbnailSize;
	if (imageFilter){
		url = controlerRootPath+"getOutfits.jsf?imageFilter="+imageFilter+"&thumbnailSize="+thumbnailSize;;
	}
	var jqxhr = $.get(url, function(data) {
		bilder.innerHTML = data;
			var lastElementLaded=false;
			while( !lastElementLaded  ){
				try
				{
					lastElement = document.getElementById('lastelementfromserver');
					lastElementLaded=true;
					loadJSForModalDialog();//lade funktion tagging fenster
				}
				catch(err)//wenn element noch nicht da in der schleife bleiben
				{
					lastElementLaded=false;
				} 
			}
		});
}

//TODO diese funktion nur einmal initial aufrufen - ab dann inkrementelle funktionen nutzen
function getImages() {
	bilder = document.getElementById("bilder");
	imageFilter = document.getElementById("imageFilter").value;
	bilderRootElementGeladen = false;
	while( !bilderRootElementGeladen  ){
		try
		{
			bilderRootElementGeladen = true;
		}
		catch(err)//wenn element noch nicht da in der schleife bleiben
		{
			bilderRootElementGeladen=false;
		} 
	}
	var url = controlerRootPath+"getImages.jsf?thumbnailSize="+thumbnailSize;
	if (imageFilter){
		url = controlerRootPath+"getImages.jsf?imageFilter="+imageFilter+"&thumbnailSize="+thumbnailSize;
	}
	var jqxhr = $.get(url, function(data) {
//		alert("Data Loaded: " + data);
		bilder.innerHTML = data;
			//hideElement("loadingscreen");
			var lastElementLaded=false;
			while( !lastElementLaded  ){
				try
				{
					lastElement = document.getElementById('lastelementfromserver');
					lastElementLaded=true;
					loadJSForModalDialog();//lade funktion tagging fenster
				}
				catch(err)//wenn element noch nicht da in der schleife bleiben
				{
					lastElementLaded=false;
				} 
			}
		});
	if(currentOutfitID==null){
		currentOutfitID = getCurrentOutfitId();	
		chooseOutfit(currentOutfitID);
	}
	//userFolder = getCurrentUserFolder();
}


function getTagsOfImage(id) {
	//alert('getTagsOfImages '+id);
	
//	showElement("loadingscreen");
	ausgabeDIV = document.getElementById("tagsOfImage");
	
	 
	var jqxhr = $.get(controlerRootPath+"getTagsOfImage.jsf?id="+id+"&type="+currentMode, function(data) {
//		alert("Data Loaded: " + data);
		
		ausgabeDIV.innerHTML = data;
			//hideElement("loadingscreen");
			
		});
}

function hideElement(elementId){
//	alert("hide"+elementId);
	var loadDiv = document.getElementById(elementId);
	loadDiv.style.visibility = 'hidden';	
}

function showElement(elementId){
//	alert("hide"+elementId);
	var loadDiv = document.getElementById(elementId);
	loadDiv.style.visibility = 'visible';	
}


function saveOutfit(bildId,bildName){
	
	currentOutfit.push(bildId);
	outfitNameInput = document.getElementById("outfitName");
	//divImage = document.getElementById('divChosenImage'+bildId);
	
	currentOutfitsString = currentOutfit.join(",");
	//alert(tagInput);
	var resultSaving=false;
	
	if(currentOutfitID){
		// wenn id vorhanden 
		$.getJSON(controlerRootPath+"updateOutfit.jsf",
				  {
			outfitName: outfitNameInput.value,
			currentOutfit: bildId,
			top: init_posy,
			left: init_posx,
			zindex: init_zIndex
				  },
		  function(data) {
					  resultSaving=data;
		    }).success(function() { //wenn im backend gespeichert (image dem outfit zugeordnet) dann im browser auch bild anzeigen
		    	//loadImageInChoiceBox(bildId,bildName);
			 })
	}else{
		// wenn id vorhanden 
		$.getJSON(controlerRootPath+"saveOutfit.jsf",
				  {
			outfitName: outfitNameInput.value,
			currentOutfit: currentOutfitsString,
				  },
		  function(data) {
//					  currentOutfitID=data; //TODO aktivieren
//					  if(currentOutfitID!=null) resultSaving=true; //TODO aktivieren
					  
		    }).success(function() { //wenn im backend gespeichert (image dem outfit zugeordnet) dann im browser auch bild anzeigen
		    	//loadImageInChoiceBox(bildId,bildName);  //TODO aktivieren
			 })
			
			 //TODO
//			 if(saveResult == false){	
//					alert ("Fehler: Bild bereits vorhanden"); 
//					return;} //TODO alert fehler!! //TODO verhindern das solche bilder ausgew�hlt werden k�nnen 
			 	 
	}
	loadImageInChoiceBox(bildId,bildName);
	
}

function addTag(bildId,bildName) {
	alert("add tag");
	
}


//TODO funktion l�schen

function createNewOutfit() {
	//alert(bildId);
	// alert("Data Loaded: " + data);
	getImages();
	var choicebox = document.getElementById("choicebox");
	choicebox.innerHTML = "neues Outfit zusammenstellen";
	outFitInput = document.getElementById('outfitName');
	outFitInput.value = 'neues Outfit';
	currentOutfit = new Array();
	currentOutfitID = null;
	
}

function removeChosenImageFromOutfit(chosenImageName,chosenImageId) {
	
	var choicebox = document.getElementById('choicebox');
	var choiceImage = document.getElementById('divChosenImage'+chosenImageId);	
	choicebox.removeChild(choiceImage);
	
	deleteImageOfOutfit(chosenImageId);
}

function swapVisibility(id){
//	alert("hide / visible" + id );
	//alert(document.getElementById(id).getStyles("visibillity") );
}

function swapAnchorVisibility(id){
//	alert("hide / visible" + id );
	if (document.getElementById('chooselink'+id).getAttribute("class") == "hidden"){
		document.getElementById('chooselink'+id).setAttribute("class", "visible");
				
	} else {
		document.getElementById('chooselink'+id).setAttribute("class", "hidden");
	}
}

function neuesFenster() {
	window
			.open(
					"upload", // URL (or "")
					"meinFensterName", // name
					"width=400,height=300,resizable=1,scrollbars=1,menubar=1,toolbar=1,location=1,status=1");
}

function initContextTestMenu() {
	$('bild7').contextMenu('menu', {
		menuStyle : {
			width : '150px'
		},
		bindings : {
			'func1' : function(t) {
				alert('You choose func 1');
			},
			'func2' : function(t) {
				alert('you choose func 2');
			},
			'func3' : function(t) {
				alert('you choose func3');
			}
		}
	});
}

function test(){
	//alert('newdiv');
	$(function() {
		$( "#draggable" ).draggable();
	});
}

//javascript laden f�r pop overlay fenster tag
function loadJSForModalDialog(){
	/*
	 * SimpleModal Basic Modal Dialog
	 * http://www.ericmmartin.com/projects/simplemodal/
	 * http://code.google.com/p/simplemodal/
	 *
	 * Copyright (c) 2010 Eric Martin - http://ericmmartin.com
	 *
	 * Licensed under the MIT license:
	 *   http://www.opensource.org/licenses/mit-license.php
	 *
	 * Revision: $Id: basic.js 254 2010-07-23 05:14:44Z emartin24 $
	 */
	jQuery(function ($) {
		// Load dialog on click
		$('#div-start-basic-modal-tagging-cklick .addTag').click(function (e) {
			$('#basic-modal-tagging-window').modal();
			//alert('modal window for tagging image  '+idOfTaggedObject);
			getTagsOfImage(idOfTaggedObject);
			return false;
		});
	});
	
	
}

 function loadTagingFunctions(){

	//TODO hier weiter - vom server holn 
	$.getJSON('getAllTags.jsf', function(data) {
		 //alert(' add tag function ');
		  $.each(data, function(key, val) {
			  availableTags.push(  val  );
			 // alert("json function"+availableTags);
		  });
			$( "#tags" ).autocomplete({
				source: availableTags
			});
		});
	
}

function saveTempImageIDForTagging(id){
	idOfTaggedObject = id;
	
}

function saveEnteredTag(id){
	
	tagInput = document.getElementById('tags').value;
	//alert(tagInput);
	$.getJSON("saveTagforImage.jsf",
			  {
				tag: tagInput,
				id: idOfTaggedObject,
				type: currentMode,//outfit or image
			  },
			  function(data) {
			   
			    });
	addNewTagToDisplayedTagList(tagInput);
	addToLoadedTags(tagInput);
	document.getElementById('tags').value = '';//cleanup
}

function addToLoadedTags(newTag){
	availableTags.push( newTag );
}
function addNewTagToDisplayedTagList(newTag){
	
	ausgabeDIV = document.getElementById("tagsOfImage");
	ausgabeDIV.innerHTML = ausgabeDIV.innerHTML.concat(","+newTag); 
}

//function saveEnteredTag(idTextArea) {
//	var textArea = document.getElementById(idTextArea);
//	var input = textArea.value;
////	alert(input+" saved");
//	
//	
//}

//loadTagingFunctions();

bilder = null;
bilderRootElementGeladen = false;
while( !bilderRootElementGeladen  ){
	try
	{
		bilder = document.getElementById("bilder");
		bilderRootElementGeladen = true;
	}
	catch(err)//wenn element noch nicht da in der schleife bleiben
	{
		bilderRootElementGeladen=false;
	} 
}
function deleteImage(id,bildName) {
	if(confirm("Das Bild "+bildName+"l�schen?" )){
		
	
	var jqxhr = $.get(controlerRootPath+"delete.jsf?id="+id+"&type="+currentMode, function(data) {
//		ausgabeDIV.innerHTML = data;
		
		});
	removeImageFromList(id);
	}
}

function deleteOutfit(id,name) {
	if(confirm("Das Outfit "+name+" l�schen?" )){
		
	
	var jqxhr = $.get(controlerRootPath+"delete.jsf?id="+id+"&type="+currentMode, function(data) {
//		ausgabeDIV.innerHTML = data;
		cache: false,
		console.log("neuer zIndex " + data);
		});
	removeImageFromList(id);
	}
}

function removeImageFromList(id){
	
	 var searchID = 'div'+id;
	 var d = document.getElementById(searchID).parentNode;
	 bilder = document.getElementById("bilder");
	 bilder.removeChild(d);
 	
}

function removeOutfitFromList(id){
	
	 var searchID = 'div'+id;
	 var d = document.getElementById(searchID);
	 bilder = document.getElementById("bilder");
	 bilder.removeChild(d);
 	
}

function createTumbnails(size){
	var jqxhr = $.get(controlerRootPath+"createThumbnails.jsf?size="+size, function(data) {
//		ausgabeDIV.innerHTML = data;
		
		});
	
}



function chooseImage(bildId,bildName) {
	
	var saveResult = saveOutfit(bildId,bildName);//bild backendseitig speichern um zu ckecken obs funktioniert und zul�ssig ist
	
	

}

	//bild in die choosebox �bernehmen f�r ein outfit
//bilder werden initial im letzte horizaontal viertel ausgegeben
function loadImageInChoiceBox(bildId,bildName) {	
	swapAnchorVisibility(bildId);
	var divChoice = document.createElement('div'); 
	divChoice.setAttribute('imageId', bildId); 
	divChoice.setAttribute('id', 'divChosenImage'+bildId); 
	divChoice.setAttribute('class', 'divChosenImage'); 
	divChoice.style.position = "absolute"; 
	divChoice.style.top =  init_posy;
	divChoice.style.left = init_posx;
	divChoice.style.zIndex = init_zIndex;
	
	var choiceImage = document.createElement('img');
	choiceImage.setAttribute('src', imageRoot+userFolder+"/"+outfitImageSize+"/"+bildName);
	choiceImage.setAttribute('width', outfitImageSize+'px');		
	choiceImage.setAttribute('id', 'chosenbild'+bildId); 
	//choiceButtonAnchor.style.zIndex = init_zIndex;
	
	var choiceButtonAnchor = document.createElement('a'); 
	//choiceButtonImage.setAttribute('href', '\#');
	choiceButtonAnchor.setAttribute('id', bildId+'removeChosenImageButton');
	//choiceButtonAnchor.style.zIndex = init_zIndex;
	choiceButtonAnchor.setAttribute('class', 'visible');
	choiceButtonAnchor.setAttribute('onclick', 'removeChosenImageFromOutfit(\''+bildName+'\',\''+bildId+'\')');

	
	var choiceButtonImage = document.createElement('img'); 
	choiceButtonImage.setAttribute('src', iconRootPath+'close.ico.jsf?ln=ICO/Blue/24');
	choiceButtonImage.setAttribute('id', bildId+'chosenImageButton');
	//choiceButtonImage.style.zIndex = init_zIndex;
	choiceButtonImage.setAttribute('class', 'listButton');
	
	var choiceButtonAnchor_indexUpChosenImageButton = document.createElement('a'); 
	choiceButtonAnchor_indexUpChosenImageButton.setAttribute('id', bildId+'indexUpChosenImageButton');
	choiceButtonAnchor_indexUpChosenImageButton.setAttribute('class', 'visible');
	choiceButtonAnchor_indexUpChosenImageButton.setAttribute('onclick', 'imagesIndexUP(\''+bildId+'\')');

	var choiceButtonImage_IndexUP = document.createElement('img'); 
	choiceButtonImage_IndexUP.setAttribute('src', iconRootPath+'arrow_up.ico.jsf?ln=ICO/Blue/24');
	choiceButtonImage_IndexUP.setAttribute('id', bildId+'chosenImageButton_IndexUP');
	choiceButtonImage_IndexUP.setAttribute('class', 'listButton');
	
	var choicebox = document.getElementById('choicebox');
	choicebox.appendChild(divChoice);	
	divChoice.appendChild(choiceImage);
	divChoice.appendChild(choiceButtonAnchor);
	divChoice.appendChild(choiceButtonAnchor_indexUpChosenImageButton);
	choiceButtonAnchor.appendChild(choiceButtonImage);
	$( "#divChosenImage"+bildId ).draggable();
	
	choiceButtonAnchor_indexUpChosenImageButton.appendChild(choiceButtonImage_IndexUP);
	
	$( "#divChosenImage"+bildId ).draggable({
   				stop: function(event, ui) { 
					 
					savePositionOfImage(event, ui);
				    console.log('Dragged: ' + $(ui.draggable).attr("bildId"));
		}
	});
	
	//executeRemoteJavascript(controlerRootPath+"getChosenImageJavascriptFunctions.jsf?bildId="+bildId);
}

function chooseOutfit(imageid,outfitid){
	cleanChoiceBox();
	outfitNameInput = document.getElementById("outfitName");
	outfitNameInput.value = name;
	currentOutfitID = outfitid; 
	
	var res = $.getJSON(controlerRootPath+"getCurrentOutfit.jsf" , {outfitid: outfitid, ajax: 'true'}, function(images){
		alert(images);
		for (var i = 0; i < images.length; i++) {
//    	  alert(images[i]);
    	  
		  outfitname = images[i].outfitname;
		  bildId = images[i].imageid;
    	  name = images[i].filename;
    	  bildtop = images[i].top;
    	  bildleft = images[i].left;
    	  init_zIndex = images[i].zindex;
    	  outfitNameInput = document.getElementById("outfitName"); 
		  outfitNameInput.value = outfitname;

    	  var divChoice = document.createElement('div'); 
			divChoice.setAttribute('imageId', bildId); 
			divChoice.setAttribute('id', 'divChosenImage'+bildId); 
			divChoice.setAttribute('class', 'divChosenImage'); 
			divChoice.style.position = "absolute"; 
			divChoice.style.top =  bildtop;
			divChoice.style.left = bildleft;
			divChoice.style.zIndex = init_zIndex;
//			divChoice.style.background = "#EEEEEE";
			var choiceImage = document.createElement('img');
			choiceImage.setAttribute('src', imageRoot+userFolder+"/"+outfitImageSize+"/"+name);
			choiceImage.setAttribute('id', bildId);
			choiceImage.setAttribute('width', outfitImageSize+'px');
			choiceImage.setAttribute('id', bildId+'chosenbild'); 
			//choiceImage.style.zIndex = init_zIndex;
			
			var choiceButtonAnchor = document.createElement('a'); 
			//choiceButtonImage.setAttribute('href', '\#');
			choiceButtonAnchor.setAttribute('id', bildId+'removeChosenImageButton');
			//choiceButtonAnchor.style.zIndex = init_zIndex;
			choiceButtonAnchor.setAttribute('class', 'visible');
			choiceButtonAnchor.setAttribute('onclick', 'removeChosenImageFromOutfit(\''+name+'\',\''+bildId+'\')');
		
			var choiceButtonImage = document.createElement('img'); 
			choiceButtonImage.setAttribute('src', iconRootPath+'close.ico?ln=ICO/Blue/24');
			choiceButtonImage.setAttribute('id', bildId+'chosenImageButton');
			//choiceButtonImage.style.zIndex = init_zIndex;
			choiceButtonImage.setAttribute('class', 'listButton');

			var choiceButtonAnchor_indexUpChosenImageButton = document.createElement('a'); 
			//choiceButtonImage.setAttribute('href', '\#');
			choiceButtonAnchor_indexUpChosenImageButton.setAttribute('id', bildId+'indexUpChosenImageButton');
			//choiceButtonAnchor_indexUpChosenImageButton.style.zIndex = init_zIndex;
			choiceButtonAnchor_indexUpChosenImageButton.setAttribute('class', 'visible');
			choiceButtonAnchor_indexUpChosenImageButton.setAttribute('onclick', 'imagesIndexUP(\''+bildId+'\')');

			var chosenImageButton_IndexUP = document.createElement('img'); 
			chosenImageButton_IndexUP.setAttribute('src', iconRootPath+'arrow_up.ico?ln=ICO/Blue/24');
			chosenImageButton_IndexUP.setAttribute('id', bildId+'chosenImageButton_IndexUP');
			//chosenImageButton_IndexUP.style.zIndex = init_zIndex;
			chosenImageButton_IndexUP.setAttribute('class', 'listButton');
			
			var choicebox = document.getElementById('choicebox');
			choicebox.appendChild(divChoice);	
			divChoice.appendChild(choiceImage);
			divChoice.appendChild(choiceButtonAnchor);
			choiceButtonAnchor.appendChild(choiceButtonImage);
			
			divChoice.appendChild(choiceButtonAnchor_indexUpChosenImageButton);
			choiceButtonAnchor_indexUpChosenImageButton.appendChild(chosenImageButton_IndexUP);
			
			$( "#divChosenImage"+bildId ).draggable({
   				stop: function(event, ui) { 
					 
					savePositionOfImage(event, ui);
				       //console.log('Dragged: ' + $(ui.draggable).attr("id"));
				}
			});
		 
      }
    })
    
}


function cleanChoiceBox(){
		var choicebox = document.getElementById('choicebox');
		choicebox.innerHTML = "";
}




function savePositionOfImage( event, ui ) {
	var imageId = ui.helper[0].attributes[0];

	updateOutfitImage(imageId.value,"divChosenImage"+imageId.value )
}

function updateOutfitImage(imageId,divId ){
//	var divImage = document.getElementById("divChosenImage"+imageId);
	var divImage = document.getElementById(divId);
	$.get(controlerRootPath+"saveImagePosition.jsf?imageid="+imageId+"&outfitid="+currentOutfitID+"&top="+divImage.style.top+"&left="+divImage.style.left+"&zindex="+divImage.style.zIndex, function(data) {
		});

	
}

function deleteImageOfOutfit(imageId){
	$.get(controlerRootPath+"deleteImageOfOutfit.jsf?imageid="+imageId+"&outfitid="+currentOutfitID, function(data) {
		});
}


function imagesIndexUP(imageId){
	
	var choiceImage = document.getElementById('divChosenImage'+imageId);
	var zIndexChooseImage = parseInt(choiceImage.style.zIndex);
	
	
	//var newZIndex = updateImageDBZIndex(imageId,zIndexChooseImage);
	var newZIndex;
	var divImage = document.getElementById("divChosenImage"+imageId);
	$.get(controlerRootPath+"updateImageZPosition.jsf?imageid="+imageId+"&outfitid="+currentOutfitID+"&zindex="+divImage.style.zIndex, function(data) {
		 cache: false,
		newZIndex = data;
		console.log("neuer zIndex " + data);
		
	}).success(function() { 
		console.log("newZIndex " + newZIndex);
		if(newZIndex != null){
			choiceImage.style.zIndex = newZIndex;
			console.log(" update zindex " + choiceImage.style.zIndex + " to  " + newZIndex);
		}
	 })
	
}


function updateImageDBZIndex(imageId,zIndex){
	var newZIndex;
		var divImage = document.getElementById("divChosenImage"+imageId);
		$.get(controlerRootPath+"updateImageZPosition.jsf?imageid="+imageId+"&outfitid="+currentOutfitID+"&zindex="+divImage.style.zIndex, function(data) {
			 cache: false,
			newZIndex = data;
			console.log("neuer zIndex " + data);
		});
	return newZIndex;	
}


function openWindowCropImage(id,filename){
	cropWindow = window.open(controlerRootPath+"cropImage.jsf?i="+id+"&mainsize="+outfitImageSize, "crop image", "width=800,height=600,scrollbars=yes");
}

$(document).ready(function() {       

	//initiale position eines bildes beim ausw�hlen zu einem outfit
	init_posy = document.body.offsetHeight * 15 / 100;  
	init_posx = document.body.offsetWidth - document.body.offsetWidth * 25 / 100; 
	getImages();//TODO lade letzten stand / letzte outfit/image
    
   $("#tags").bind('keyup',function(e){
  		if(e.keyCode == 13) {
  			saveEnteredTag();
  			
  		}
	});
   
});


function getCurrentOutfitId(){
	$.get(controlerRootPath+"getCurrentOutfitId.jsf", function(data) {
		currentOutfitID = data;
	});
return currentOutfitID;	
	
}
userFolder = getCurrentUserFolder();
