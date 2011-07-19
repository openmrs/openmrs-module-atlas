var marker;
var map;
var containerEdit;
var containerView;
var $typeWindow;
var imgPlaceholder = 'http://a0.twimg.com/profile_images/672560906/OpenMRS-twitter-icon_bigger.png';
var mailtoSubject = "?Subject=OpenMRS%20contact";

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
 	
 	initializeGutter();
 	
 	$typeWindow = jQuery("#changeTypeDialog");
 	BindEventsChangeTypeModalWindow();
 	initializeImplementationType();
 	
 	getStatisticsFromServer();
 	jQuery("#includeModulesTip").click(function() {
 		return false;
 	});
});

function saveAtlasBubbleDataOnServer() {
	var position = marker.getPosition();
	
	var id = jQuery("#atlasID").val();
	var name = jQuery('#lblName', containerView).text();
	var website = jQuery('#lblWebsite', containerView).text();
	var contactName = jQuery('#lblContactName', containerView).text();
	var contactEmailAddress = jQuery('#lblEmail', containerView).text();
	var notes = jQuery('#lblNotes', containerView).text();
	var implementationType = jQuery('#implementationTypeOrdinal').val();
	var latitude = position.lat();
	var longitude = position.lng();
	var includeNumberOfPatients = jQuery('#cbPatients', containerEdit).attr('checked');
	var includeNumberOfObservations = jQuery('#cbObservations', containerEdit).attr('checked');
	var includeNumberOfEncounters = jQuery('#cbEncounters', containerEdit).attr('checked');
	var imgSrc = jQuery('#imgImplementation', containerView).attr('src');
	if (imgSrc != imgPlaceholder) {
		//jQuery('#atlasImageURL', div).val(imgSrc);
	} else {
		imgSrc = '';
	}
	
	DWRAtlasService.saveAtlasBubbleData(id, latitude, longitude, 
			name, implementationType ,website, imgSrc
			,notes, contactName, contactEmailAddress
			,includeNumberOfPatients, includeNumberOfObservations, includeNumberOfEncounters);
}

function getStatisticsFromServer() {
	DWRAtlasService.updateAndGetStatistics(getStatisticsFromServerCallback);
}

function getStatisticsFromServerCallback(stats) {
	console.log(stats[0]);
	console.log(stats[1]);
	console.log(stats[2]);
	jQuery('#lblEncountersNr', containerView).text(stats[1]);
	jQuery('#lblPatientsNr', containerView).text(stats[0]);
	jQuery('#lblObservationsNr', containerView).text(stats[2]);
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

function initializeGutter() {
	$btnEnabled = jQuery('#btnEnable');
	$btnDisabled = jQuery('#btnDisable');
	
	jQuery('#cbIncludeModules').click(function() {
		setIncludeModulesOnServer(jQuery(this).is(':checked'));
	});
	
	if (!jQuery('#cbDisclaimer').is(':checked')) {
		  $btnDisabled.addClass('btnDisabledDisabled');
          $btnDisabled.removeClass('btnDisabled');
          $btnDisabled.attr("disabled", true);
	}
	
	jQuery('#cbDisclaimer').click(function() {
	    if (jQuery(this).is(':checked')) {
	    	if ($btnDisabled.attr("disabled") == true) {
	    		$btnDisabled.attr("disabled", false);
	    		$btnDisabled.removeClass('btnDisabledDisabled');
	    		$btnDisabled.addClass('btnDisabled');
	    	}
	    } else {
	       if ($btnEnabled.is(':visible')) {
	           $btnEnabled.click();
	       }  
	       $btnDisabled.addClass('btnDisabledDisabled');
           $btnDisabled.removeClass('btnDisabled');
           $btnDisabled.attr("disabled", true);
	    } 
	});
	
	$btnEnabled.click(function() {
		$btnDisabled.show();
		$btnEnabled.hide();
		disableAtlasModuleOnServer(jQuery('#cbDisclaimer').is(':checked'));
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
 	var $impl = jQuery('#implementationTypeOrdinal');
 	
	jQuery("#tbType", containerEdit).val(getImplementationType($impl.val()));
	jQuery('#lblImplementationType', containerView).text(getImplementationType($impl.val()));
//	jQuery('#btnPreviousType',containerEdit ).click(function () {
//		changeImplementationType(parseInt($impl.val()) - 1);
//	});
//	
//	jQuery('#btnNextType',containerEdit).click(function () {
//		changeImplementationType(parseInt($impl.val()) + 1);
//	});
}
function getImplementationType(ord) {
	var str = '#rbType'+ord;
	return jQuery(str, $typeWindow).val();
}

function getImplementationTypeLength() {
	return parseInt(jQuery("#implementationTypeLength").val());
}
function changeImplementationType(ord) {
	jQuery("#tbType",containerEdit).val(getImplementationType(ord));
	jQuery('#implementationTypeOrdinal').val(ord);
}

/*
function CopyValuesFromViewToHiddenFields() {
	var position = marker.getPosition();
	var div = jQuery("#leftColumnDiv");
	jQuery('#atlasName', div).val(jQuery('#lblName', containerView).text());
	jQuery('#atlasWebsite', div).val(jQuery('#lblWebsite', containerView).text());
	jQuery('#atlasContactName', div).val(jQuery('#lblEmail', containerView).text());
	jQuery('#atlasContactEmailAddress', div).val(jQuery('#lblContactName', containerView).text());
	jQuery('#atlasLatitude', div).val(position.lat());
	jQuery('#atlasLongitude', div).val(position.lng());
	jQuery('#atlasIncludeNumberOfPatients', div).val(jQuery('#cbPatients', containerEdit).attr('checked'));
	jQuery('#atlasIncludeNumberOfObservations', div).val(jQuery('#cbObservations', containerEdit).attr('checked'));
	jQuery('#atlasIncludeNumberOfEncounters', div).val(jQuery('#cbEncounters', containerEdit).attr('checked'));
	var imgSrc = jQuery('#imgImplementation', containerView).attr('src');
	if (imgSrc != imgPlaceholder) {
		jQuery('#atlasImageURL', div).val(imgSrc);
	} else {
		jQuery('#atlasImageURL', div).val('');
	}
}
*/

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
	$typeWindow.dialog({ autoOpen: false, title : "Change Type"});
	
	jQuery('#changeTypeLink').click(function(e) {
		var rbId = "#rbType"+jQuery('#implementationTypeOrdinal').val();
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
	jQuery('#editLink').click(function(e) {
		View2Edit();
		RemovePlaceHolderClass(containerEdit);
		validateInput(containerEdit);
		jQuery(containerEdit).show();
		infowindow.setContent(containerEdit);
		return false;
	});
	
	jQuery('#saveLink').click(function(e) {
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
	
	jQuery('#cancelLink').click(function(e) {
		jQuery(containerView).show();
		infowindow.setContent(containerView);
		return false;
	});
}

function validateInput(div) {
	var input = jQuery('#tbName', div);
	if (StringIsEmpty(input.val())
			||  (input.val() == input.attr('placeholder'))) {
		jQuery('#nameError', div).show();
		return false;
	} else {
		jQuery('#nameError', div).hide();
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
	
	var img = jQuery('#imgImplementation', containerView);
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
	jQuery('#tbName', containerEdit).val(jQuery('#lblName', containerView).text());
	jQuery('#tbWebsite', containerEdit).val(jQuery('#lblWebsite', containerView).text());
	jQuery('#tbEmail', containerEdit).val(jQuery('#lblEmail', containerView).text());
	jQuery('#tbContactName', containerEdit).val(jQuery('#lblContactName', containerView).text());
	jQuery('#tbNotes', containerEdit).val(jQuery('#lblNotes', containerView).text());
	jQuery("#tbType", containerEdit).val(jQuery('#lblImplementationType', containerView).text());
	if (jQuery('#imgImplementation', containerView).attr('src') != imgPlaceholder) {
		jQuery('#tbImage', containerEdit).val(jQuery('#imgImplementation', containerView).attr('src'));
	} else {
		jQuery('#tbImage', containerEdit).val("");
	}
}


function SetElementsInView(isAfterEdit) {
	
	SetViewPlaceholders();
	if (jQuery('#implementationTypeOrdinal').val() == '0') {
		jQuery('#lblImplementationType', containerView).parent().hide();
	} else {
		jQuery('#lblImplementationType', containerView).parent().show();
	}
	
	
	var email;
	if (isAfterEdit) {
		email = jQuery('#tbEmail', containerEdit).val();
	} else {
		email = "";
	}
	if (!StringIsEmpty(email)) {
		jQuery('#lblEmail', containerView).text(email);
		jQuery('#aEmail',containerView).attr('href','mailto:'+email+mailtoSubject);
		jQuery('#imgEmail',containerView).show();
	} else {
		jQuery('#imgEmail',containerView).hide();
	}
	
}
/*
 * Copy values from the edit container to the view container 
 */
function Edit2View() {
	RemovePlaceHolderText(containerEdit);
	
	jQuery('#imgImplementation', containerView).attr('src', jQuery("#tbImage", containerEdit).val());
	jQuery('#lblName', containerView).text(jQuery('#tbName', containerEdit).val());
	jQuery('#lblWebsite', containerView).text(jQuery('#tbWebsite', containerEdit).val());
	jQuery('#aWebsite', containerView).attr('href', jQuery('#tbWebsite', containerEdit).val());
	jQuery('#lblContactName', containerView).text(jQuery('#tbContactName', containerEdit).val());
	jQuery('#lblNotes', containerView).text(jQuery('#tbNotes', containerEdit).val());
	jQuery('#lblImplementationType', containerView).text(jQuery("#tbType", containerEdit).val());
	
	if (jQuery('#cbEncounters', containerEdit).attr('checked')) {
		jQuery('#lblEncounters', containerView).show();
	} else {
		jQuery('#lblEncounters', containerView).hide();
	}
	if (jQuery('#cbPatients', containerEdit).attr('checked')) {
		jQuery('#lblPatients', containerView).show();
	} else {
		jQuery('#lblPatients', containerView).hide();
	}
	if (jQuery('#cbObservations', containerEdit).attr('checked')) {
		jQuery('#lblObservations', containerView).show();
	} else {
		jQuery('#lblObservations', containerView).hide();
	}
	
	SetElementsInView(true);
}

function ViewIsEmpty() {
	return (   (jQuery('#lblName', containerView).text() == '')
			&& (jQuery('#lblWebsite', containerView).text() == '')
			&& (jQuery('#lblEmail', containerView).text() == '')
			&& (jQuery('#lblContactName', containerView).text() == ''));
}

function StyleContainers() {
    containerView.style.width="350px";
	containerEdit.style.width="350px";
	
	
}

function GetCurrentLatLng() {
	var lng = jQuery('#atlasLongitude').val();
	var lat = jQuery('#atlasLatitude').val();
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
	var zoom = parseInt(jQuery('#atlasZoom').val());
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

	map = new google.maps.Map(document.getElementById("mapCanvas"), myOptions);
	var infowindow = new google.maps.InfoWindow();
	BindEvents(infowindow);
	
	
	google.maps.event.addListener(map, 'zoom_changed', function() {
		console.log("in zoom changed");
		updateZoomOnServer();
	});
	
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