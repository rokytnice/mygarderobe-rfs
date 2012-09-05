<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- resize image -->
<script type="text/javascript" src="javascripts/prototype.js"></script>
<script type="text/javascript" src="javascripts/scriptaculous.js?load=effects,builder,dragdrop" language="javascript"></script>
<script type="text/javascript" src="cropper/cropper.uncompressed.js"></script>

<style type="text/css">

		* html { font-size: 100%; }



		body {

			font-family: "Trebuchet MS", Helvetica, Arial, san-serif;

			font-size: 0.8em;

			background-color: #FFF;

			color: #000;

			margin: 0;

		}



		h1 {

			font-size: 1.8em;

		}



		h2 {

			margin-top: 0;

			font-size: 1.2em;

		}



		#wrapper {

			margin: 1.5em;

		}



		fieldset {

			padding: 0.75em 1em;

			margin-bottom: 1em;

		}



		form select,

		form input {

			margin: 0 0.5em;

		}



		#testWrap {

			float: left;

			width: 500px;

			margin-right: 2em;

		}



		#previewOuterWrap {

			float: left;

			width: 120px;

			display: none;			margin-right: 2em;

		}



		#previewWrap {

			border: 1px solid #333;

		}





		#results {

			float: left;

		}



		#results div {

			margin: 0.25em 0;

		}



		#results label {

			float: left;

			width: 4.5em;

		}

		

		#instructions {

			clear: both;

			padding-top: 1em;

		}

		

		#instructions li {

			margin-left: 1.8em;

		}

	</style>


<link rel="stylesheet" href="cropper/cropper.css" type="text/css"></link></head>
<body>

<img src="/myGarderobe/<%=de.rochlitz.mygarderobe.de.rochlitz.mygarderobe.jpa.MyGarderobeConstant.upload_dir_name%>/8/imag0267.jpg" alt="Test image" id="testImage" />
<div id="previewWrap"></div>
 
<script type="text/javascript" language="javascript">
/**

		 * A little manager that allows us to swap the image dynamically

		 * for the dynamic example

		 *

		 */

		var CropImageManager = {

			/**

			 * Holds the current Cropper.Img object

			 * @var obj

			 */

			curCrop: null,

			

			/**

			 * Initialises the cropImageManager

			 *

			 * @access public

			 * @return void

			 */

			init: function() {

				this.attachCropper();

			},

			

			/**

			 * Handles the changing of the select to change the image, the option value

			 * is a pipe seperated list of imgSrc|width|height

			 * 

			 * @access public

			 * @param obj event

			 * @return void

			 */

			onChange: function( e ) {

				var vals = $F( Event.element( e ) ).split('|');

				this.setImage( vals[0], vals[1], vals[2] ); 

			},

			

			/**

			 * Sets the image within the element & attaches/resets the image cropper

			 *

			 * @access private

			 * @param string Source path of new image

			 * @param int Width of new image in pixels

			 * @param int Height of new image in pixels

			 * @return void

			 */

			setImage: function( imgSrc, w, h ) {

				$( 'testImage' ).src = imgSrc;

				$( 'testImage' ).width = w;

				$( 'testImage' ).height = h;

				this.attachCropper();

			},

			

			/** 

			 * Attaches/resets the image cropper

			 *

			 * @access private

			 * @return void

			 */

			attachCropper: function() {

				if( this.curCrop == null ) this.curCrop = new Cropper.Img( 'testImage', { onEndCrop: onEndCrop } );

				else this.curCrop.reset();

			},

			

			/**

			 * Removes the cropper

			 *

			 * @access public

			 * @return void

			 */

			removeCropper: function() {

				if( this.curCrop != null ) {

					this.curCrop.remove();

				}

			},

			

			/**

			 * Resets the cropper, either re-setting or re-applying

			 *

			 * @access public

			 * @return void

			 */

			resetCropper: function() {

				this.attachCropper();

			}

		};





		// setup the callback function

		function onEndCrop( coords, dimensions ) {

			$( 'x1' ).value = coords.x1;

			$( 'y1' ).value = coords.y1;

			$( 'x2' ).value = coords.x2;

			$( 'y2' ).value = coords.y2;

			$( 'width' ).value = dimensions.width;

			$( 'height' ).value = dimensions.height;

		}



		function loadExample( type ) {

			switch( type ) {

				case( 'minWidth' ) :

					new Cropper.Img( 'testImage', { minWidth: 220, onEndCrop: onEndCrop } );

					break;

				case( 'minHeight' ) :

					new Cropper.Img( 'testImage', { minHeight: 120, onEndCrop: onEndCrop } );

					break;

				case( 'minDimensions' ) :

					new Cropper.Img( 'testImage', { minWidth: 220, minHeight: 120, onEndCrop: onEndCrop } );

					break;

				case( 'ratioFourThree' ) :

					new Cropper.Img( 'testImage', { ratioDim: { x: 220, y: 165 }, displayOnInit: true, onEndCrop: onEndCrop } );

					break;

				case( 'ratioSixteenNine' ) :

					new Cropper.Img( 'testImage', { ratioDim: { x: 220, y: 124 }, displayOnInit: true, onEndCrop: onEndCrop } );

					break;

				case( 'preview' ) :

					new Cropper.ImgWithPreview( 'testImage', { previewWrap: 'previewWrap', minWidth: 120, minHeight: 120, ratioDim: { x: 120, y: 120 }, onEndCrop: onEndCrop } );

					break;

				case( 'dynamic' ) :

					CropImageManager.init();

					Event.observe( $('removeCropper'), 'click', CropImageManager.removeCropper.bindAsEventListener( CropImageManager ), false );

					Event.observe( $('resetCropper'), 'click', CropImageManager.resetCropper.bindAsEventListener( CropImageManager ), false );

					Event.observe( $('imageChoice'), 'change', CropImageManager.onChange.bindAsEventListener( CropImageManager ), false );

					break;

				case( 'basic' ) :

				default :

					new Cropper.Img( 'testImage', { onEndCrop: onEndCrop } );

			}

		}



		Event.observe( window, 'load', function() { loadExample( 'basic' ); } );

</script>
 


</body>
</html>