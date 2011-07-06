var marker;
var map;
var containerEdit;
var containerView;
var firstTimeEdit = 1;

jQuery(document).ready (function() {
	containerEdit = document.getElementById('containerDiv-edit');
	containerView = document.getElementById('containerDiv'); 
	BindPlaceHolder(containerEdit);
	StyleContainers();
// jQuery('.placeholder').each(function () {
// $this = jQuery(this);
// if ($this.text() == '');
// });
	jQuery(containerEdit).hide();
	jQuery(containerView).hide();
 	InitializeMap();
 	bindSearchBox();
 	BindPlaceHolder(jQuery('#rightColumnDiv'));
 	jQuery("#btnSubmit").click(function() {
 		CopyValuesFromViewToHiddenFields();
 	});
});

/*
 * Function called before form submit
 */
function CopyValuesFromViewToHiddenFields() {
	var position = marker.getPosition();
	var div = jQuery("#leftColumnDiv");
	jQuery('#atlasName', div).val(jQuery('#lblName', containerView).text());
	jQuery('#atlasWebsite', div).val(jQuery('#lblWebsite', containerView).text());
	jQuery('#atlasContactPhoneNumber', div).val(jQuery('#lblEmail', containerView).text());
	jQuery('#atlasContactEmailAddress', div).val(jQuery('#lblPhone', containerView).text());
	jQuery('#atlasLatitude', div).val(position.lat());
	jQuery('#atlasLongitude', div).val(position.lng());
	jQuery('#atlasIncludeNumberOfPatients', div).val(true);
	jQuery('#atlasIncludeNumberOfObservations', div).val(true);
	jQuery('#atlasIncludeNumberOfVisits', div).val(true);
}

/*
 * Bind events to input elements that have the placeholder attribute
 * This is done in case the clients browser does not support the placeholder attr
 */
function BindPlaceHolder(div) {
jQuery('[placeholder]', div).focus(function() {
  var input = jQuery(this);
  if (input.val() == input.attr('placeholder')) {
    input.val('');
    input.removeClass('placeholder');
  }
}).blur(function() {
  var input = jQuery(this);
  if (input.val() == '' || input.val() == input.attr('placeholder')) {
    input.addClass('placeholder');
    input.val(input.attr('placeholder'));
  } else {
	  input.removeClass('placeholder');
  }
}).blur().parents('form').submit(function() {
	//remove the values from the input elements, so they do not get submitted
   RemovePlaceHolderText(jQuery(this));
});
}

function RemovePlaceHolderText(div) {
	jQuery('[placeholder]' , div).each(function() {
		var input = jQuery(this);
		if (input.val() == input.attr('placeholder')) {
			input.val('');
		}
	});
}

function RemovePlaceHolderClass(div) {
	jQuery('[placeholder]', div).each(function() {
	  var input = jQuery(this);
	  if (input.val() != input.attr('placeholder')) {
	    input.removeClass('placeholder');
	  }
	});
}
/*
 * Bind events to edit, save, cancel buttons in the google info window
 */
function BindEvents(infowindow) {
	jQuery('#editLink').click(function(e) {
		View2Edit();
		RemovePlaceHolderClass(containerEdit);
		jQuery(containerEdit).show();
		infowindow.setContent(containerEdit);
		return false;
	});
	
	jQuery('#saveLink').click(function(e) {
		containerEdit = infowindow.getContent();
		Edit2View();
		jQuery(containerView).show();
		infowindow.setContent(containerView);
		return false;
	});
	
	jQuery('#cancelLink').click(function(e) {
		infowindow.setContent(containerView);
		return false;
	});
}

/*
 * This is used to provide some sort of placeholders to labels in the view info window,
 * when they do not contain text
 */
function SetViewPlaceholders() {
	jQuery('.spanView', containerView).each(function() {
		$this = jQuery(this);
		if (StringIsEmpty($this.text())) {
			$this.siblings('.labelPlaceHolder').show();
		} else {
			$this.siblings('.labelPlaceHolder').hide();
		}
	});
}

function StringIsEmpty(str) {
	return jQuery.trim(str).length == 0;
}

/*
 * Copy values from the view container to the edit container 
 */
function View2Edit() {
	jQuery('#tbName', containerEdit).val(jQuery('#lblName', containerView).text());
	jQuery('#tbWebsite', containerEdit).val(jQuery('#lblWebsite', containerView).text());
	jQuery('#tbEmail', containerEdit).val(jQuery('#lblEmail', containerView).text());
	jQuery('#tbPhone', containerEdit).val(jQuery('#lblPhone', containerView).text());
}

/*
 * Copy values from the edit container to the view container 
 */
function Edit2View() {
	RemovePlaceHolderText(containerEdit);
	jQuery('#lblName', containerView).text(jQuery('#tbName', containerEdit).val());
	jQuery('#lblWebsite', containerView).text(jQuery('#tbWebsite', containerEdit).val());
	jQuery('#lblEmail', containerView).text(jQuery('#tbEmail', containerEdit).val());
	jQuery('#lblPhone', containerView).text(jQuery('#tbPhone', containerEdit).val());
	
	SetViewPlaceholders();
}

function ViewIsEmpty() {
	return (   (jQuery('#lblName', containerView).text() == '')
			&& (jQuery('#lblWebsite', containerView).text() == '')
			&& (jQuery('#lblEmail', containerView).text() == '')
			&& (jQuery('#lblPhone', containerView).text() == ''));
}

function StyleContainers() {
    containerView.style.width="350px";
	containerEdit.style.width="350px";
	
	
}

function GetCurrentLatLng() {
	var lng = jQuery('#atlasLongitude').val();
	var lat = jQuery('#atlasLatitude').val();
	if ( lng != '' && lat != '' ){
		return new google.maps.LatLng(lat, lng);
	} else { 
		return null;
	}
}

/*
 * build the Locate Me control, so we can embed it in the google map
 */
 function LocateMeControl(controlDiv) {

            // Set CSS styles for the DIV containing the control
            // Setting padding to 5 px will offset the control
            // from the edge of the map
            controlDiv.style.padding = '5px';

            // Set CSS for the control border
            var controlUI = document.createElement('DIV');
            controlUI.style.backgroundColor = 'White';
            controlUI.style.borderStyle = 'solid';
            controlUI.style.borderWidth = '1px';
            controlUI.style.cursor = 'pointer';
            controlUI.style.textAlign = 'center';
            controlUI.title = 'Click to find your position';
            controlDiv.appendChild(controlUI);

            // Set CSS for the control interior
            var controlText = document.createElement('DIV');
            controlText.style.fontFamily = 'Arial,sans-serif';
            controlText.style.fontSize = '12px';
            controlText.style.paddingLeft = '4px';
            controlText.style.paddingRight = '4px';
            controlText.innerHTML = '<b>Locate Me</b>';
            controlUI.appendChild(controlText);

            // Setup the click event listeners: simply set the map to
            google.maps.event.addDomListener(controlUI, 'click', function () {
                initiate_geolocation();
            });
 }

 function PinToCenterControl(controlDiv) {

     // Set CSS styles for the DIV containing the control
     // Setting padding to 5 px will offset the control
     // from the edge of the map
     controlDiv.style.padding = '5px';

     // Set CSS for the control border
     var controlUI = document.createElement('DIV');
     controlUI.style.backgroundColor = 'White';
     controlUI.style.borderStyle = 'solid';
     controlUI.style.borderWidth = '1px';
     controlUI.style.cursor = 'pointer';
     controlUI.style.textAlign = 'center';
     controlUI.title = 'Click drop your Atlas Marker in the center of the map';
     controlDiv.appendChild(controlUI);

     // Set CSS for the control interior
     var controlText = document.createElement('DIV');
     controlText.style.fontFamily = 'Arial,sans-serif';
     controlText.style.fontSize = '12px';
     controlText.style.paddingLeft = '4px';
     controlText.style.paddingRight = '4px';
     controlText.innerHTML = '<b>Pin to Center</b>';
     controlUI.appendChild(controlText);

     // Setup the click event listeners: simply set the map to
     google.maps.event.addDomListener(controlUI, 'click', function () {
         pinToCenter();
     });
}
 
 function InitializeMap() {
	var myLatlng = GetCurrentLatLng();
	var zoom = 12;
	if (myLatlng == null) {
		myLatlng = new google.maps.LatLng(47.112002,27.706801);
		zoom = 4;
	}
	var myOptions = {
		zoom: zoom,
		center: myLatlng,
		panControl: true,
		zoomControl: true,
		mapTypeControl: true,
		scaleControl: false,
		streetViewControl: false,
		overviewMapControl: false,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};

	map = new google.maps.Map(document.getElementById("mapCanvas"), myOptions);
	var infowindow = new google.maps.InfoWindow();
	BindEvents(infowindow);
	
	google.maps.event.addListener(infowindow, 'closeclick', function() {
		var container = infowindow.getContent();
		if (jQuery(container).attr('id') == 'containerDiv') {
			containerView = container;
		} else {
			containerEdit = container;
		}
	});
	
	marker = new google.maps.Marker({
		position: myLatlng,
		draggable: true,
		map: map,
		title:"OpenMRS"
	});

	
	// containerView = document.getElementById('containerDiv')
	
	google.maps.event.addListener(marker, 'click', function() {
		 if (ViewIsEmpty()) {
			jQuery(containerEdit).show();
			infowindow.setContent(containerEdit);
		} else { 
			SetViewPlaceholders();
			jQuery(containerView).show();
			infowindow.setContent(containerView);
		}
		infowindow.open(map,marker);
	});

	
    var locateMeControlDiv = document.createElement('DIV');
    var locateMeControl = new LocateMeControl(locateMeControlDiv);
    locateMeControlDiv.index = 1;
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(locateMeControlDiv);	
    
    var pinToCenterDiv = document.createElement('DIV');
    var pinToCenterControl = new PinToCenterControl(pinToCenterDiv);
    pinToCenterDiv.index = 1;
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(pinToCenterDiv);		
  }


 function initiate_geolocation() {
	 if (navigator.geolocation) {
		 navigator.geolocation.getCurrentPosition(handle_geolocation_query, handle_errors);
     } else {
    	 yqlgeo.get('visitor', normalize_yql_response);
     }
 }
 
 function pinToCenter() {
	 marker.setPosition(map.getCenter());
 }
 
 //todo localized strings
 function handle_errors(error) {
	 switch (error.code) {
	 	case error.PERMISSION_DENIED: 
	 			alert("user did not share geolocation data");
                break;
        case error.POSITION_UNAVAILABLE: 
        		alert("could not detect current position");
                break;
        case error.TIMEOUT: alert("retrieving position timedout");
                break;
        default: alert("unknown error");
                break;
     }
}
 
 function normalize_yql_response(response) {
	 if (response.error) {
		 var error = { code: 0 };
         handle_error(error);
         return;
     }
	 var position = {
			 coords:
             {
				 latitude: response.place.centroid.latitude,
                 longitude: response.place.centroid.longitude
             }
			/*
			 * , address: { city: response.place.locality2.content, region:
			 * response.place.admin1.content, country:
			 * response.place.country.content }
			 */
    };
    handle_geolocation_query(position);
}

function handle_geolocation_query(position) {
	var myLatlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    marker.setPosition(myLatlng); 
	map.setCenter(myLatlng);
	map.setZoom(14);
}  
  
function bindSearchBox() {
	var geocoder = new google.maps.Geocoder();
//	$(function() {
	jQuery("#searchbox").autocomplete({
			source: function(request, response) {
				if (geocoder == null){
					geocoder = new google.maps.Geocoder();
                }
				geocoder.geocode( {'address': request.term }, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						var searchLoc = results[0].geometry.location;
                        var lat = results[0].geometry.location.lat();
                        var lng = results[0].geometry.location.lng();
                        var latlng = new google.maps.LatLng(lat, lng);
                     // var bounds = results[0].geometry.bounds;
                        geocoder.geocode({'latLng': latlng}, function(results1, status1) {
                        	if (status1 == google.maps.GeocoderStatus.OK) {
                        		if (results1[1]) {
                        			response(jQuery.map(results1, function(loc) {
                        				return {
                        					label  : loc.formatted_address,
                        					value  : loc.formatted_address,
                        					location   : loc.geometry.location
                        				};
                        			}));	
                               }
                             }
                           });
                   }
               });
          },
          select: function(event,ui){
        	  // var pos = ui.item.position;
        	  // var lct = ui.item.locType;
        	  // var bounds = ui.item.bounds;
             if (ui.item.location != null) {
            	 var position = {
                     coords:
                     {
                         latitude: ui.item.location.lat(),
                         longitude: ui.item.location.lng()
                     }
            	 };
                 handle_geolocation_query(position);
             }
         }
      });
//            });   
}