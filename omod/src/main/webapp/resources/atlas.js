var marker;
var infowindow;
var map;
var containerEdit;
var containerView;
var $typeWindow;
var imgPlaceholder = 'http://a0.twimg.com/profile_images/672560906/OpenMRS-twitter-icon_bigger.png';
var mailtoSubject = "?Subject=OpenMRS%20contact";

jQuery(document).ready (function() {
	containerEdit = document.getElementById('atlas-edit-containerDiv');
	containerView = document.getElementById('atlas-view-containerDiv'); 
	BindPlaceHolder(containerEdit);
	StyleContainers();

	jQuery(containerEdit).hide();
	jQuery(containerView).hide();
 	InitializeMap();
 	bindSearchBox();
 	BindPlaceHolder(jQuery('#atlas-map-rightColumnDiv'));
 	
 	initializeGutter();
 	
 	$typeWindow = jQuery("#changeTypeDialog");
 	BindEventsChangeTypeModalWindow();
 	initializeImplementationType();
 	initializeWhatWillBeSentModalWindow();
 	getStatisticsFromServer();
});

function initializeWhatWillBeSentModalWindow() {
	var titleText = jQuery("#atlas-gutter-sentInfoTitle").text();
	var $whatWillBeSendWindow = jQuery("#atlas-gutter-sentInfo");
	$whatWillBeSendWindow.dialog({ autoOpen: false
        , modal: true
        , width: 500
        , title : titleText
       });
 	
 	jQuery("#atlas-gutter-includeModulesTip").click(function() {
 		if (jQuery('#atlas-gutter-btnEnable').is(":visible")) {
 			getJsonDataFromServer();
 		} else {
 			jQuery('#atlas-gutter-jsonData', jQuery("#atlas-gutter-sentInfo")).val("");
 		}
 		$whatWillBeSendWindow.dialog('open');
 		return false;
 	});
}

function saveAtlasBubbleDataOnServer() {
	var position = marker.getPosition();
	var name = jQuery('#atlas-view-lblName', containerView).text();
	var website = jQuery('#atlas-view-lblWebsite', containerView).text();
	var contactName = jQuery('#atlas-view-lblContactName', containerView).text();
	var contactEmailAddress = jQuery('#atlas-view-lblEmail', containerView).text();
	var notes = jQuery('#atlas-view-lblNotes', containerView).text();
	var implementationType = jQuery('#atlas-hidden-implementationTypeOrdinal').val();
	var latitude = position.lat();
	var longitude = position.lng();
	var includeNumberOfPatients = jQuery('#atlas-edit-cbPatients', containerEdit).attr('checked');
	var includeNumberOfObservations = jQuery('#atlas-edit-cbObservations', containerEdit).attr('checked');
	var includeNumberOfEncounters = jQuery('#atlas-edit-cbEncounters', containerEdit).attr('checked');
	var imgSrc = jQuery('#atlas-view-imgImplementation', containerView).attr('src');
	if (imgSrc != imgPlaceholder) {
		//jQuery('#atlasImageURL', div).val(imgSrc);
	} else {
		imgSrc = '';
	}
	
	DWRAtlasService.saveAtlasBubbleData(latitude, longitude, 
			name, implementationType ,website, imgSrc
			,notes, contactName, contactEmailAddress
			,includeNumberOfPatients, includeNumberOfObservations, includeNumberOfEncounters);
}

function getJsonDataFromServer() {
	DWRAtlasService.getJsonData(getJsonDataFromServerCallback);
}

function getJsonDataFromServerCallback(jsonData) {
	jQuery('#atlas-gutter-jsonData', jQuery("#atlas-gutter-sentInfo")).val(jsonData);
}

function getStatisticsFromServer() {
	DWRAtlasService.updateAndGetStatistics(getStatisticsFromServerCallback);
}

function getStatisticsFromServerCallback(stats) {
	jQuery('#atlas-view-lblEncountersNr', containerView).text(stats[1]);
	jQuery('#atlas-view-lblPatientsNr', containerView).text(stats[0]);
	jQuery('#atlas-view-lblObservationsNr', containerView).text(stats[2]);
}

function enableAtlasModuleOnServer() {
	DWRAtlasService.enableAtlasModule();
}

function disableAtlasModuleOnServer(cbDisclamerIsChecked) {
	DWRAtlasService.disableAtlasModule(cbDisclamerIsChecked);
}

function setIncludeModulesOnServer(value) {
	DWRAtlasService.setIncludeModules(value);
}

function updatePositionOnServer() {
var position = marker.getPosition();
	DWRAtlasService.setPosition(position.lat(), position.lng());
}

function updateZoomOnServer() {
	var zoom = map.getZoom();
		DWRAtlasService.setZoom(zoom.toString());
	}

function setUsageDiscailmerOnServer(value) {
	DWRAtlasService.setUsageDisclaimer(value);
} 

function initializeGutter() {
	$btnEnabled = jQuery('#atlas-gutter-btnEnable');
	$btnDisabled = jQuery('#atlas-gutter-btnDisable');
	$cbIncludeModules = jQuery('#atlas-gutter-cbIncludeModules');
	
	$cbIncludeModules.click(function() {
		setIncludeModulesOnServer(jQuery(this).is(':checked'));
	});
	
	if (!jQuery('#atlas-gutter-cbDisclaimer').is(':checked')) {
		  $btnDisabled.addClass('atlas-gutter-btnDisabledDisabled');
          $btnDisabled.removeClass('atlas-gutter-btnDisabled');
          $btnDisabled.attr("disabled", true);
          $cbIncludeModules.attr("disabled", true);
	}
	
	jQuery('#atlas-gutter-cbDisclaimer').click(function() {
	    if (jQuery(this).is(':checked')) {
	    	if ($btnDisabled.attr("disabled") == true) {
	    		$btnDisabled.attr("disabled", false);
	    		$btnDisabled.removeClass('atlas-gutter-btnDisabledDisabled');
	    		$btnDisabled.addClass('atlas-gutter-btnDisabled');
	    		$cbIncludeModules.attr("disabled", false);
	    	}
	    } else {
	       if ($btnEnabled.is(':visible')) {
	           $btnEnabled.click();
	       }  
	       $btnDisabled.addClass('atlas-gutter-btnDisabledDisabled');
           $btnDisabled.removeClass('atlas-gutter-btnDisabled');
           $btnDisabled.attr("disabled", true);
           $cbIncludeModules.attr("disabled", true);
	    } 
	    setUsageDiscailmerOnServer(jQuery(this).is(':checked'));
	});
	
	$btnEnabled.click(function() {
		$btnDisabled.show();
		$btnEnabled.hide();
		disableAtlasModuleOnServer(jQuery('#atlas-gutter-cbDisclaimer').is(':checked'));
		return false;
	});
	
	$btnDisabled.click(function() {
		$btnDisabled.hide();
		$btnEnabled.show();
		enableAtlasModuleOnServer();
		return false;
	});
}
function initializeImplementationType() {
 	var $impl = jQuery('#atlas-hidden-implementationTypeOrdinal');
 	
	jQuery("#atlas-edit-tbType", containerEdit).val(getImplementationType($impl.val()));
	jQuery('#atlas-view-lblImplementationType', containerView).text(getImplementationType($impl.val()));
}
function getImplementationType(ord) {
	var str = '#rbType'+ord;
	return jQuery(str, $typeWindow).val();
}

function changeImplementationType(ord) {
	jQuery("#atlas-edit-tbType",containerEdit).val(getImplementationType(ord));
	jQuery('#atlas-hidden-implementationTypeOrdinal').val(ord);
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

function BindEventsChangeTypeModalWindow() {
	var titleText = jQuery('#selectTypeText', $typeWindow).text();
	$typeWindow.dialog({ autoOpen: false
		               , modal: true
		               , title : titleText
		              });
	
	jQuery('#atlas-edit-changeTypeLink').click(function(e) {
		var rbId = "#rbType"+jQuery('#atlas-hidden-implementationTypeOrdinal').val();
		jQuery(rbId,$typeWindow).attr('checked', true);
		$typeWindow.dialog('open');
		return false;
	});
	
	jQuery('#btnTypeSave').click(function(e) {
		var typeOrd = jQuery('input[type=radio]:checked',$typeWindow).attr('id').substring(6); //6 = length("rbType") 
		changeImplementationType(typeOrd);
		$typeWindow.dialog('close');
		return false;
	});
	
	jQuery('#btnTypeCancel').click(function(e) {
		$typeWindow.dialog('close');
		return false;
	});
}
/*
 * Bind events to edit, save, cancel buttons in the google info window
 */
function BindEvents(infowindow) {
	jQuery('#atlas-view-editLink').click(function(e) {
		View2Edit();
		BindPlaceHolder(containerEdit);
		validateInput(containerEdit);
		jQuery(containerEdit).show();
		infowindow.setContent(containerEdit);
		return false;
	});
	
	jQuery('#atlas-edit-saveLink').click(function(e) {
		containerEdit = infowindow.getContent();
		if (validateInput(containerEdit)) {
			Edit2View();
			jQuery(containerView).show();
			infowindow.setContent(containerView);
			saveAtlasBubbleDataOnServer();
		} else {
			infowindow.setContent(containerEdit);
		}
		return false;
	});
	
	jQuery('#atlas-edit-cancelLink').click(function(e) {
		if (!ViewIsEmpty()) {
			jQuery(containerView).show();
			infowindow.setContent(containerView);
			return false;
		} else {
			infowindow.close();
			return false;
		}
	});
}

function validateInput(div) {
	var input = jQuery('#atlas-edit-tbName', div);
	if (StringIsEmpty(input.val())
			||  (input.val() == input.attr('placeholder'))) {
		jQuery('#atlas-edit-nameError', div).show();
		return false;
	} else {
		jQuery('#atlas-edit-nameError', div).hide();
		return true;
	}
}
/*
 * This is used to provide some sort of placeholders to labels in the view info window,
 * when they do not contain text
 */

function SetViewPlaceholders() {
//	jQuery('.spanView', containerView).each(function() {
//		$this = jQuery(this);
//		if (StringIsEmpty($this.text())) {
//			$this.siblings('.labelPlaceHolder').show();
//		} else {
//			$this.siblings('.labelPlaceHolder').hide();
//		}
//	});
//	
//	jQuery('.spanViewParent', containerView).each(function() {
//		$this = jQuery(this);
//		if (StringIsEmpty($this.text())) {
//			$this.parent().siblings('.labelPlaceHolder').show();
//		} else {
//			$this.parent().siblings('.labelPlaceHolder').hide();
//		}
//	});
	
	var img = jQuery('#atlas-view-imgImplementation', containerView);
	if (StringIsEmpty(img.attr('src'))) {
		img.attr('src', imgPlaceholder);
	} 
}


function StringIsEmpty(str) {
	return jQuery.trim(str).length == 0;
}

/*
 * Copy values from the view container to the edit container 
 */
function View2Edit() {
	jQuery('#atlas-edit-tbName', containerEdit).val(jQuery('#atlas-view-lblName', containerView).text());
	jQuery('#atlas-edit-tbWebsite', containerEdit).val(jQuery('#atlas-view-lblWebsite', containerView).text());
	jQuery('#atlas-edit-tbEmail', containerEdit).val(jQuery('#atlas-view-lblEmail', containerView).text());
	jQuery('#atlas-edit-tbContactName', containerEdit).val(jQuery('#atlas-view-lblContactName', containerView).text());
	jQuery('#atlas-edit-tbNotes', containerEdit).val(jQuery('#atlas-view-lblNotes', containerView).text());
	jQuery("#atlas-edit-tbType", containerEdit).val(jQuery('#atlas-view-lblImplementationType', containerView).text());
	if (jQuery('#atlas-view-imgImplementation', containerView).attr('src') != imgPlaceholder) {
		jQuery('#atlas-edit-tbImage', containerEdit).val(jQuery('#atlas-view-imgImplementation', containerView).attr('src'));
	} else {
		jQuery('#atlas-edit-tbImage', containerEdit).val("");
	}
}


function SetElementsInView(isAfterEdit) {
	
	SetViewPlaceholders();
	if (jQuery('#atlas-hidden-implementationTypeOrdinal').val() == '0') {
		jQuery('#atlas-view-lblImplementationType', containerView).parent().hide();
	} else {
		jQuery('#atlas-view-lblImplementationType', containerView).parent().show();
	}
	
	
	var email;
	if (isAfterEdit) {
		email = jQuery('#atlas-edit-tbEmail', containerEdit).val();
	} else {
		email = jQuery('#atlas-view-lblEmail', containerView).text();
	}
	if (!StringIsEmpty(email)) {
		jQuery('#atlas-view-lblEmail', containerView).text(email);
		jQuery('#atlas-view-aEmail',containerView).attr('href','mailto:'+email+mailtoSubject);
		jQuery('#atlas-view-imgEmail',containerView).show();
		jQuery('#atlas-view-aEmail',containerView).show();
	} else {
		//if (isAfterEdit) {
			jQuery('#atlas-view-imgEmail',containerView).hide();
			jQuery('#atlas-view-lblEmail', containerView).text(email);
			jQuery('#atlas-view-aEmail',containerView).hide();
	//	}
	}
	
}
/*
 * Copy values from the edit container to the view container 
 */
function Edit2View() {
	RemovePlaceHolderText(containerEdit);
	
	jQuery('#atlas-view-imgImplementation', containerView).attr('src', jQuery("#atlas-edit-tbImage", containerEdit).val());
	jQuery('#atlas-view-lblName', containerView).text(jQuery('#atlas-edit-tbName', containerEdit).val());
	jQuery('#atlas-view-lblWebsite', containerView).text(jQuery('#atlas-edit-tbWebsite', containerEdit).val());
	jQuery('#atlas-view-aWebsite', containerView).attr('href', jQuery('#atlas-edit-tbWebsite', containerEdit).val());
	jQuery('#atlas-view-lblContactName', containerView).text(jQuery('#atlas-edit-tbContactName', containerEdit).val());
	jQuery('#atlas-view-lblNotes', containerView).text(jQuery('#atlas-edit-tbNotes', containerEdit).val());
	jQuery('#atlas-view-lblImplementationType', containerView).text(jQuery("#atlas-edit-tbType", containerEdit).val());
	
	if (jQuery('#atlas-edit-cbEncounters', containerEdit).attr('checked')) {
		jQuery('#atlas-view-lblEncounters', containerView).show();
	} else {
		jQuery('#atlas-view-lblEncounters', containerView).hide();
	}
	if (jQuery('#atlas-edit-cbPatients', containerEdit).attr('checked')) {
		jQuery('#atlas-view-lblPatients', containerView).show();
	} else {
		jQuery('#atlas-view-lblPatients', containerView).hide();
	}
	if (jQuery('#atlas-edit-cbObservations', containerEdit).attr('checked')) {
		jQuery('#atlas-view-lblObservations', containerView).show();
	} else {
		jQuery('#atlas-view-lblObservations', containerView).hide();
	}
	
	SetElementsInView(true);
}

function ViewIsEmpty() {
	return  (jQuery('#atlas-view-lblName', containerView).text() == '');
}

function StyleContainers() {
    containerView.style.width="370px";
	containerEdit.style.width="370px";
	
	
}

function GetCurrentLatLng() {
	var lng = jQuery('#atlas-hidden-longitude').val();
	var lat = jQuery('#atlas-hidden-latitude').val();
	if ( lng != '0.0' && lat != '0.0' ){
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

 function MarkerToCenterControl(controlDiv) {

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
     controlUI.title = 'Click to move the Atlas marker in the center of the map';
     controlDiv.appendChild(controlUI);

     // Set CSS for the control interior
     var controlText = document.createElement('DIV');
     controlText.style.fontFamily = 'Arial,sans-serif';
     controlText.style.fontSize = '12px';
     controlText.style.paddingLeft = '4px';
     controlText.style.paddingRight = '4px';
     controlText.innerHTML = '<b>Center Marker</b>';
     controlUI.appendChild(controlText);

     // Setup the click event listeners: simply set the map to
     google.maps.event.addDomListener(controlUI, 'click', function () {
         markerToCenter();
     });
}
 
 function InitializeMap() {
	var myLatlng = GetCurrentLatLng();
	var zoom = parseInt(jQuery('#atlas-hidden-zoom').val());
	if (myLatlng == null) {
		myLatlng = new google.maps.LatLng(-0.351560293992, 23.642578125);
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

	map = new google.maps.Map(document.getElementById("atlas-map-canvas"), myOptions);
	infowindow = new google.maps.InfoWindow();
	BindEvents(infowindow);
	
	
	google.maps.event.addListener(map, 'zoom_changed', function() {
		//console.log("in zoom changed");
		updateZoomOnServer();
	});
	
	google.maps.event.addListener(infowindow, 'closeclick', function() {
		var container = infowindow.getContent();
		if (jQuery(container).attr('id') == 'atlas-view-containerDiv') {
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

	
	// containerView = document.getElementById('atlas-view-containerDiv')
	
	google.maps.event.addListener(marker, 'click', function() {
		 if (ViewIsEmpty()) {
			jQuery(containerEdit).show();
			infowindow.setContent(containerEdit);
		} else { 
			SetElementsInView(false);
			jQuery(containerView).show();
			infowindow.setContent(containerView);
		}
		infowindow.open(map,marker);
	});
	
	google.maps.event.addListener(marker, 'dragend', function() {
		updatePositionOnServer();
	});
	
    var markerToCenterDiv = document.createElement('DIV');
    var markerToCenterControl = new MarkerToCenterControl(markerToCenterDiv);
    markerToCenterDiv.index = 1;
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(markerToCenterDiv);	
	
    var locateMeControlDiv = document.createElement('DIV');
    var locateMeControl = new LocateMeControl(locateMeControlDiv);
    locateMeControlDiv.index = 1;
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(locateMeControlDiv);	
	
  }


 function initiate_geolocation() {
	 if (navigator.geolocation) {
		 navigator.geolocation.getCurrentPosition(handle_geolocation_query, handle_errors);
     } else {
    	 yqlgeo.get('visitor', normalize_yql_response);
     }
 }
 
 function markerToCenter() {
	 marker.setPosition(map.getCenter());
	 updatePositionOnServer();
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
	updatePositionOnServer();
}  
  
function bindSearchBox() {
	var geocoder = new google.maps.Geocoder();
	jQuery("#atlas-map-searchbox").autocomplete({
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
             if (ui.item.location != null) {
            	 var position = {
                     coords:
                     {
                         latitude: ui.item.location.lat(),
                         longitude: ui.item.location.lng()
                     }
            	 };
                 handle_geolocation_query(position);
                 jQuery('.ui-autocomplete').hide();
             }
         }
      });
}