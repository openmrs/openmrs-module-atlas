var $j = jQuery.noConflict();
var marker;
var infowindow;
var map;
var containerEdit;
var containerView;
var $typeWindow;
var imgPlaceholder = 'http://a0.twimg.com/profile_images/672560906/OpenMRS-twitter-icon_bigger.png';
var mailtoSubject = "?Subject=OpenMRS%20contact";

$j(document).ready (function() {
	containerEdit = document.getElementById('atlas-edit-containerDiv');
	containerView = document.getElementById('atlas-view-containerDiv'); 
	BindPlaceHolder(containerEdit);
	StyleContainers();

	$j(containerEdit).hide();
	$j(containerView).hide();
 	InitializeMap();
 	bindSearchBox();
 	BindPlaceHolder($j('#atlas-map-rightColumnDiv'));
 	
 	initializeGutter();
 	
 	$typeWindow = $j("#changeTypeDialog");
 	BindEventsChangeTypeModalWindow();
 	initializeImplementationType();
 	initializeWhatWillBeSentModalWindow();
 	getStatisticsFromServer();
});

function initializeWhatWillBeSentModalWindow() {
	var titleText = $j("#atlas-gutter-sentInfoTitle").text();
	var $whatWillBeSendWindow = $j("#atlas-gutter-sentInfo");
	$whatWillBeSendWindow.dialog({ autoOpen: false
        , modal: true
        , width: 500
        , title : titleText
       });
 	
 	$j("#atlas-gutter-includeSystemConfigurationTip").click(function() {
 		getJsonDataFromServer();
 		$whatWillBeSendWindow.dialog('open');
 		return false;
 	});
 	
 	$j("#atlas-gutter-sentInfo-close", $whatWillBeSendWindow).click(function() {
 		$whatWillBeSendWindow.dialog('close');
 	});
}

function saveAtlasBubbleDataOnServer() {
	var position = marker.getPosition();
	var name = $j.trim($j('#atlas-view-lblName', containerView).text());
	var website = $j.trim($j('#atlas-view-lblWebsite', containerView).text());
	var contactName = $j.trim($j('#atlas-view-lblContactName', containerView).text());
	var contactEmailAddress = $j.trim($j('#atlas-view-lblEmail', containerView).text());
	var notes = $j.trim($j('#atlas-view-lblNotes', containerView).text());
	var implementationType = $j.trim($j('#atlas-hidden-implementationTypeOrdinal').val());
	var latitude = position.lat();
	var longitude = position.lng();
	var includeNumberOfPatients = $j.trim($j('#atlas-edit-cbPatients', containerEdit).attr('checked'));
	var includeNumberOfObservations = $j.trim($j('#atlas-edit-cbObservations', containerEdit).attr('checked'));
	var includeNumberOfEncounters = $j.trim($j('#atlas-edit-cbEncounters', containerEdit).attr('checked'));
	var imgSrc = $j.trim($j('#atlas-view-imgImplementation', containerView).attr('src'));
	if (imgSrc != imgPlaceholder) {
		// $j('#atlasImageURL', div).val(imgSrc);
	} else {
		imgSrc = '';
	}
	
	DWRAtlasService.saveAtlasBubbleData(latitude, longitude, 
			name, implementationType ,website, imgSrc
			,notes, contactName, contactEmailAddress
			,includeNumberOfPatients, includeNumberOfObservations, includeNumberOfEncounters);
	
	getIsDirtyFromServer();
}

function getIsDirtyFromServer() {
	// if you want to update the dirty state from the server, uncomment the
	// setTimeout call and comment the getIsDirtyFromServerCallback call
	// setTimeout("DWRAtlasService.getIsDirty(getIsDirtyFromServerCallback)",500);
	getIsDirtyFromServerCallback(true);
}

function getIsDirtyFromServerCallback(isDirty) {
	if (isDirty &&  $j('#atlas-gutter-btnEnable').is(':visible')) {
		$j('#atlas-gutter-updateAtlasNowLink').show();
	} else {
		$j('#atlas-gutter-updateAtlasNowLink').hide();
	}
}

function getJsonDataFromServer() {
	DWRAtlasService.getJsonData(getJsonDataFromServerCallback);
}

function getJsonDataFromServerCallback(jsonData) {
	$j('#atlas-gutter-jsonData', $j("#atlas-gutter-sentInfo")).val(jsonData);
}

function getStatisticsFromServer() {
	DWRAtlasService.updateAndGetStatistics(getStatisticsFromServerCallback);
}

function getStatisticsFromServerCallback(stats) {
	$j('#atlas-view-lblEncountersNr', containerView).text(stats[1]);
	$j('#atlas-view-lblPatientsNr', containerView).text(stats[0]);
	$j('#atlas-view-lblObservationsNr', containerView).text(stats[2]);
}

function enableAtlasModuleOnServer() {
	DWRAtlasService.enableAtlasModule();
}

function sendPostCommandToServer() {
	DWRAtlasService.postAtlasData();
	getIsDirtyFromServer();
}

function disableAtlasModuleOnServer(cbDisclamerIsChecked) {
	DWRAtlasService.disableAtlasModule(cbDisclamerIsChecked);
}

function setIncludeSystemConfigurationOnServer(value) {
	DWRAtlasService.setIncludeSystemConfiguration(value);
	getIsDirtyFromServer();
}

function updatePositionOnServer() {
	var position = marker.getPosition();
	DWRAtlasService.setPosition(position.lat(), position.lng());
	getIsDirtyFromServer();
}

function updateZoomOnServer() {
		var zoom = map.getZoom();
		DWRAtlasService.setZoom(zoom.toString());
	}

function setUsageDiscailmerOnServer(value) {
	DWRAtlasService.setUsageDisclaimer(value);
} 

function initializeGutter() {
	$btnEnabled = $j('#atlas-gutter-btnEnable');
	$btnDisabled = $j('#atlas-gutter-btnDisable');
	$cbIncludeSystemConfiguration = $j('#atlas-gutter-cbIncludeSystemConfiguration');
	$updateAtlasNowLink = $j('#atlas-gutter-updateAtlasNowLink');
	
	$cbIncludeSystemConfiguration.click(function() {
		setIncludeSystemConfigurationOnServer($j(this).is(':checked'));
	});
	
	if (!$j('#atlas-gutter-cbDisclaimer').is(':checked')) {
		  $btnDisabled.addClass('atlas-gutter-btnDisabledDisabled');
          $btnDisabled.removeClass('atlas-gutter-btnDisabled');
          $btnDisabled.attr("disabled", true);
          $cbIncludeSystemConfiguration.attr("disabled", true);
	}
	
	$j('#atlas-gutter-cbDisclaimer').click(function() {
	    if ($j(this).is(':checked')) {
	    	if ($btnDisabled.attr("disabled") == true || $btnDisabled.attr("disabled") == 'disabled') {
	    		$btnDisabled.attr("disabled", false);
	    		$btnDisabled.removeClass('atlas-gutter-btnDisabledDisabled');
	    		$btnDisabled.addClass('atlas-gutter-btnDisabled');
	    		$cbIncludeSystemConfiguration.attr("disabled", false);
	    	}
	    } else {
	       if ($btnEnabled.is(':visible')) {
	           $btnEnabled.click();
	       }  
	       $btnDisabled.addClass('atlas-gutter-btnDisabledDisabled');
           $btnDisabled.removeClass('atlas-gutter-btnDisableatlas-gutter-jsonDatad');
           $btnDisabled.attr("disabled", true);
           $cbIncludeSystemConfiguration.attr("disabled", true);
	    } 
	    setUsageDiscailmerOnServer($j(this).is(':checked'));
	});
	
	$btnEnabled.click(function(event) {
		$btnDisabled.show();
		$btnEnabled.hide();
		$updateAtlasNowLink.hide();
		disableAtlasModuleOnServer($j('#atlas-gutter-cbDisclaimer').is(':checked'));
		event.preventDefault();
	});
	
	$btnDisabled.click(function(event) {
		if (!ViewIsEmpty()) {
			$btnDisabled.hide();
			$btnEnabled.show();
			enableAtlasModuleOnServer();
		}
		event.preventDefault();
	});
	
	$updateAtlasNowLink.click(function (event) {
		sendPostCommandToServer();
		// comment the next line if you use the DWR service to get isDirty value
		$updateAtlasNowLink.hide();
		event.preventDefault();
	});
}
function initializeImplementationType() {
 	var $impl = $j('#atlas-hidden-implementationTypeOrdinal');
 	
	$j("#atlas-edit-tbType", containerEdit).val(getImplementationType($impl.val()));
	$j('#atlas-view-lblImplementationType', containerView).text(getImplementationType($impl.val()));
}
function getImplementationType(ord) {
	var str = '#rbType'+ord;
	return $j(str, $typeWindow).val();
}

function changeImplementationType(ord) {
	$j("#atlas-edit-tbType",containerEdit).val(getImplementationType(ord));
	$j('#atlas-hidden-implementationTypeOrdinal').val(ord);
}

/*
 * Bind events to input elements that have the placeholder attribute This is
 * done in case the clients browser does not support the placeholder attr
 */
function BindPlaceHolder(div) {
$j('[placeholder]', div).focus(function() {
  var input = $j(this);
  if (input.val() == input.attr('placeholder')) {
    input.val('');
    input.removeClass('placeholder');
  }
}).blur(function() {
  var input = $j(this);
  if (input.val() == '' || input.val() == input.attr('placeholder')) {
    input.addClass('placeholder');
    input.val(input.attr('placeholder'));
  } else {
	  input.removeClass('placeholder');
  }
}).blur().parents('form').submit(function() {
	// remove the values from the input elements, so they do not get submitted
   RemovePlaceHolderText($j(this));
});
}

function RemovePlaceHolderText(div) {
	$j('[placeholder]' , div).each(function() {
		var input = $j(this);
		if (input.val() == input.attr('placeholder')) {
			input.val('');
		}
	});
}

function RemovePlaceHolderClass(div) {
	$j('[placeholder]', div).each(function() {
	  var input = $j(this);
	  if (input.val() != input.attr('placeholder')) {
	    input.removeClass('placeholder');
	  }
	});
}

function BindEventsChangeTypeModalWindow() {
	var titleText = $j('#selectTypeText', $typeWindow).text();
	$typeWindow.dialog({ autoOpen: false
		               , modal: true
		               , title : titleText
		              });
	
	$j('#atlas-edit-changeTypeLink').click(function(e) {
		var rbId = "#rbType"+$j('#atlas-hidden-implementationTypeOrdinal').val();
		$j(rbId,$typeWindow).attr('checked', true);
		$typeWindow.dialog('open');
		return false;
	});
	
	$j('#atlas-edit-tbType').click(function() {
		$j('#atlas-edit-changeTypeLink').click();
	});
	
	$j('#btnTypeSave').click(function(e) {
		var typeOrd = $j('input[type=radio]:checked',$typeWindow).attr('id').substring(6); // 6 =
																							// length("rbType")
		changeImplementationType(typeOrd);
		$typeWindow.dialog('close');
		return false;
	});
	
	$j('#btnTypeCancel').click(function(e) {
		$typeWindow.dialog('close');
		return false;
	});
}
/*
 * Bind events to edit, save, cancel buttons in the google info window
 */
function BindEvents(infowindow) {
	$j('#atlas-view-editLink').click(function(e) {
		View2Edit();
		BindPlaceHolder(containerEdit);
		validateInput(containerEdit);
		$j(containerEdit).show();
		infowindow.setContent(containerEdit);
		return false;
	});
	
	$j('#atlas-edit-saveLink').click(function(e) {
		containerEdit = infowindow.getContent();
		if (validateInput(containerEdit)) {
			Edit2View();
			$j(containerView).show();
			infowindow.setContent(containerView);
			saveAtlasBubbleDataOnServer();
		} else {
			infowindow.setContent(containerEdit);
		}
		return false;
	});
	
	$j('#atlas-edit-cancelLink').click(function(e) {
		if (!ViewIsEmpty()) {
			$j(containerView).show();
			infowindow.setContent(containerView);
			return false;
		} else {
			infowindow.close();
			return false;
		}
	});
}

function validateInput(div) {
	var input = $j('#atlas-edit-tbName', div);
	if (StringIsEmpty(input.val())
			||  (input.val() == input.attr('placeholder'))) {
		$j('#atlas-edit-nameError', div).show();
		return false;
	} else {
		$j('#atlas-edit-nameError', div).hide();
		return true;
	}
}
/*
 * This is used to provide some sort of placeholders to labels in the view info
 * window, when they do not contain text
 */

function SetViewPlaceholders() {
	var img = $j('#atlas-view-imgImplementation', containerView);
	if (StringIsEmpty(img.attr('src'))) {
		img.attr('src', imgPlaceholder);
	} 
}


function StringIsEmpty(str) {
	return $j.trim(str).length == 0;
}

/*
 * Copy values from the view container to the edit container
 */
function View2Edit() {
	$j('#atlas-edit-tbName', containerEdit).val($j('#atlas-view-lblName', containerView).text());
	$j('#atlas-edit-tbWebsite', containerEdit).val($j('#atlas-view-lblWebsite', containerView).text());
	$j('#atlas-edit-tbEmail', containerEdit).val($j('#atlas-view-lblEmail', containerView).text());
	$j('#atlas-edit-tbContactName', containerEdit).val($j('#atlas-view-lblContactName', containerView).text());
	$j('#atlas-edit-tbNotes', containerEdit).val($j('#atlas-view-lblNotes', containerView).text());
	$j("#atlas-edit-tbType", containerEdit).val($j('#atlas-view-lblImplementationType', containerView).text());
	if ($j('#atlas-view-imgImplementation', containerView).attr('src') != imgPlaceholder) {
		$j('#atlas-edit-tbImage', containerEdit).val($j('#atlas-view-imgImplementation', containerView).attr('src'));
	} else {
		$j('#atlas-edit-tbImage', containerEdit).val("");
	}
}


function SetElementsInView(isAfterEdit) {
	
	SetViewPlaceholders();
	if ($j('#atlas-hidden-implementationTypeOrdinal').val() == '0') {
		$j('#atlas-view-lblImplementationType', containerView).parent().hide();
	} else {
		$j('#atlas-view-lblImplementationType', containerView).parent().show();
	}
	
	
	var email;
	if (isAfterEdit) {
		email = $j('#atlas-edit-tbEmail', containerEdit).val();
	} else {
		email = $j('#atlas-view-lblEmail', containerView).text();
	}
	if (!StringIsEmpty(email)) {
		$j('#atlas-view-lblEmail', containerView).text(email);
		$j('#atlas-view-aEmail',containerView).attr('href','mailto:'+email+mailtoSubject);
		$j('#atlas-view-imgEmail',containerView).show();
		$j('#atlas-view-aEmail',containerView).show();
	} else {
			$j('#atlas-view-imgEmail',containerView).hide();
			$j('#atlas-view-lblEmail', containerView).text(email);
			$j('#atlas-view-aEmail',containerView).hide();
	}
	
}
/*
 * Copy values from the edit container to the view container
 */
function Edit2View() {
	RemovePlaceHolderText(containerEdit);
	
	$j('#atlas-view-imgImplementation', containerView).attr('src', $j("#atlas-edit-tbImage", containerEdit).val());
	$j('#atlas-view-lblName', containerView).text($j('#atlas-edit-tbName', containerEdit).val());
	$j('#atlas-view-lblWebsite', containerView).text($j('#atlas-edit-tbWebsite', containerEdit).val());
	$j('#atlas-view-aWebsite', containerView).attr('href', $j('#atlas-edit-tbWebsite', containerEdit).val());
	$j('#atlas-view-lblContactName', containerView).text($j('#atlas-edit-tbContactName', containerEdit).val());
	$j('#atlas-view-lblNotes', containerView).text($j('#atlas-edit-tbNotes', containerEdit).val());
	$j('#atlas-view-lblImplementationType', containerView).text($j("#atlas-edit-tbType", containerEdit).val());
	
	if ($j('#atlas-edit-cbEncounters', containerEdit).attr('checked')) {
		$j('#atlas-view-lblEncounters', containerView).show();
	} else {
		$j('#atlas-view-lblEncounters', containerView).hide();
	}
	if ($j('#atlas-edit-cbPatients', containerEdit).attr('checked')) {
		$j('#atlas-view-lblPatients', containerView).show();
	} else {
		$j('#atlas-view-lblPatients', containerView).hide();
	}
	if ($j('#atlas-edit-cbObservations', containerEdit).attr('checked')) {
		$j('#atlas-view-lblObservations', containerView).show();
	} else {
		$j('#atlas-view-lblObservations', containerView).hide();
	}
	
	SetElementsInView(true);
}

function ViewIsEmpty() {
	return  StringIsEmpty($j('#atlas-view-lblName', containerView).text());
}

function StyleContainers() {
    containerView.style.width="370px";
	containerEdit.style.width="370px";
}

function GetCurrentLatLng() {
	var lng = $j('#atlas-hidden-longitude').val();
	var lat = $j('#atlas-hidden-latitude').val();
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

 /*
  * build the Marker to Center control, so we can embed it in the google map
  */
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
	var zoom = parseInt($j('#atlas-hidden-zoom').val());
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
		// console.log("in zoom changed");
		updateZoomOnServer();
	});
	
	google.maps.event.addListener(infowindow, 'closeclick', function() {
		var container = infowindow.getContent();
		if ($j(container).attr('id') == 'atlas-view-containerDiv') {
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

	google.maps.event.addListener(marker, 'click', function() {
		 if (ViewIsEmpty()) {
			$j(containerEdit).show();
			infowindow.setContent(containerEdit);
		} else { 
			SetElementsInView(false);
			$j(containerView).show();
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
 
 // todo localized strings
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
	$j("#atlas-map-searchbox").autocomplete({
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
                        			response($j.map(results1, function(loc) {
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
                 $j('.ui-autocomplete').hide();
             }
         }
      });
}