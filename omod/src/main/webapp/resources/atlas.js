var marker;
var map;
var containerEdit;
var containerView;

jQuery(document).ready (function() {
	containerEdit = document.getElementById('containerDiv-edit');
	containerView = document.getElementById('containerDiv'); 
	BindPlaceHolder();
	StyleContainers();
	jQuery('.placeholder').each(function () {
		$this = jQuery(this);
		if ($this.text() == '');
	});
	jQuery(containerEdit).hide();
	jQuery(containerView).hide();
 	InitializeMap();
});

function BindPlaceHolder() {
	jQuery('[placeholder]').focus(function() {
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
  }
}).blur().parents('form').submit(function() {
 // RemovePlaceHolderText(jQuery(this));
});
}

function RemovePlaceHolderText(div) {
	div.find('[placeholder]').each(function() {
		var input = jQuery(this);
		if (input.val() == input.attr('placeholder')) {
			input.val('');
		}
	})
}

function BindEvents(infowindow) {
	jQuery('#editLink').click(function(e) {
		View2Edit();
		jQuery(containerEdit).show();
		infowindow.setContent(containerEdit);
		return false;
	});
	
	jQuery('#saveLink').click(function(e) {
		containerEdit = infowindow.getContent();
		Edit2View();
		infowindow.setContent(containerView);
		return false;
	});
	
	jQuery('#cancelLink').click(function(e) {
		infowindow.setContent(containerView);
		return false;
	});
}

	
function View2Edit() {
	jQuery('#tbName', containerEdit).val(jQuery('#lblName', containerView).text());
	jQuery('#tbWebsite', containerEdit).val(jQuery('#lblWebsite', containerView).text());
	jQuery('#tbEmail', containerEdit).val(jQuery('#lblEmail', containerView).text());
	jQuery('#tbPhone', containerEdit).val(jQuery('#lblPhone', containerView).text());
}

function Edit2View() {
	RemovePlaceHolderText(containerEdit)
	jQuery('#lblName', containerView).text(jQuery('#tbName', containerEdit).val());
	jQuery('#lblWebsite', containerView).text(jQuery('#tbWebsite', containerEdit).val());
	jQuery('#lblEmail', containerView).text(jQuery('#tbEmail', containerEdit).val());
	jQuery('#lblPhone', containerView).text(jQuery('#tbPhone', containerEdit).val());
}

function ViewIsEmpty() {
	return (   (jQuery('#lblName', containerView).text() == '')
			&& (jQuery('#lblWebsite', containerView).text() == '')
			&& (jQuery('#lblEmail', containerView).text() == '')
			&& (jQuery('#lblPhone', containerView).text() == ''))
}

function StyleContainers() {
    containerView.style.width="300px";
	containerEdit.style.width="300px";
	
	
}
 function LocateMeControl(controlDiv) {

            // Set CSS styles for the DIV containing the control
            // Setting padding to 5 px will offset the control
            // from the edge of the map
            controlDiv.style.padding = '5px';

            // Set CSS for the control border
            var controlUI = document.createElement('DIV');
            controlUI.style.backgroundColor = 'white';
            controlUI.style.borderStyle = 'solid';
            controlUI.style.borderWidth = '2px';
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
            // Chicago
            google.maps.event.addDomListener(controlUI, 'click', function () {
                initiate_geolocation();
            });

        }

	function InitializeMap() {
	var myLatlng = new google.maps.LatLng(-25.363882,131.044922);
	var myOptions = {
		zoom: 4,
		center: myLatlng,
		panControl: true,
		zoomControl: true,
		mapTypeControl: true,
		scaleControl: false,
		streetViewControl: false,
		overviewMapControl: false,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	}

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

	
	//containerView = document.getElementById('containerDiv')
	
	google.maps.event.addListener(marker, 'click', function() {
		 if (ViewIsEmpty()) {
			jQuery(containerEdit).show();
			infowindow.setContent(containerEdit);
		} else { 
			jQuery(containerView).show();
			infowindow.setContent(containerView);
		}
		infowindow.open(map,marker);
	});

    var locateMeControlDiv = document.createElement('DIV');
    var locateMeControl = new LocateMeControl(locateMeControlDiv);
    locateMeControlDiv.index = 1;
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(locateMeControlDiv);		
  }
/*
google.maps.event.addListener(infowindow, 'domready', function() {
document.getElementById("editLnk").addEvent("click", function(e) {
        alert('sss');
		e.stop();
        console.log("hi!");
    });
   
   jQuery('#editLnk').click( function(e) {
        alert('sss');
		e.stop();
        console.log("hi!");
    });
});
*/

   function initiate_geolocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(handle_geolocation_query, handle_errors);
            }
            else {
                yqlgeo.get('visitor', normalize_yql_response);
            }
        }

        function handle_errors(error) {
            switch (error.code) {
                case error.PERMISSION_DENIED: alert("user did not share geolocation data");
                    break;

                case error.POSITION_UNAVAILABLE: alert("could not detect current position");
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
			/*,
                address:
            {
                city: response.place.locality2.content,
                region: response.place.admin1.content,
                country: response.place.country.content
            }*/
            };
            handle_geolocation_query(position);
        }

        function handle_geolocation_query(position) {
            var myLatlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            marker.setPosition(myLatlng); 
			map.setCenter(myLatlng);
			map.setZoom(14);
        }  
  