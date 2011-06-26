<meta name="viewport" content="initial-scale=1.0, user-scalable=yes" />
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0px; padding: 0px }
  #map_canvas { height: 100% }

  #infoDiv, #infoDiv-edit {
	float: left;
	display: inline;
	background: 
  }
  #imgDiv, #imgDiv-edit  {
	display: inline;
	float: left;
  }
  #contactDiv, #contactDiv-edit {
	clear: both;
  }
  
  #lblName
  {
	width:200px;
	min-width: 200px;
  }
  
  .placeholder
{
  color: #aaa;
}
</style>

 
 <script src="yqlgeo.js"></script>
 <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"> </script>

<script type="text/javascript">
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
	jQuery(div).find('[placeholder]').each(function() {
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
//	containerView.style.width="300px";
//	containerEdit.style.width="300px";	
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

            // Setup the click event listeners:
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

	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
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
            };
            handle_geolocation_query(position);
        }

        function handle_geolocation_query(position) {
            var myLatlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            marker.setPosition(myLatlng); 
			map.setCenter(myLatlng);
			map.setZoom(14);
        }  
  
</script>


<h2><spring:message code="atlas.manageMarkerLink" /></h2>

<br/>
<table id="containerDiv" style="display:hidden" >
            <tr>
                <td>
                    <img src="http://www.knol-knol.com/google-knol.jpg" width="80px" height="80px" alt ="thumbnail" />
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                              <span id="lblName"><c:out value="${atlasData.name}"/></span>
                            </td>
                        </tr>
                        <tr>
                            <td><span id="lblWebsite"> <c:out value="${atlasData.website}"/></span>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="stats" id="lblPatients"> Patients </span>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="stats" id="lblVisits"> Visits </span> <br/>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="stats" id="lblObservations"> Observations</span> 
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span class="contact" id="lblEmail"> Email</span> </td>
            </tr> 
            <tr>
                <td colspan="2"><span class="contact" id="lblPhone"> Phone </span> </td>
            </tr> 
			<tr>
				<td colspan="2"><a id="editLink" href="">Edit</a></td>
			</tr>
           
        </table>	
		
<table id="containerDiv-edit" style="display:hidden" >
            <tr>
                <td>
                    <div id="imgDiv-edit">
						<img src="http://www.knol-knol.com/google-knol.jpg" width="80px" height="80px" alt ="thumbnail" />
					</div>
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                             <input type="text" id="tbName" placeholder="Name...">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" id="tbWebsite" placeholder="Website...">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" id="cbPatients">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" id="cbObservations">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" id="cbVisits">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="text" id="tbEmail" placeholder="Email.."></td>
            </tr> 
            <tr>
                <td colspan="2"><input type="text" id="tbPhone" placeholder="Phone.."></td>
            </tr> 
			<tr>
				<td colspan="2"><a id="saveLink" href="">Save</a> | <a id="cancelLink" href="">Cancel</a></td>
			</tr>
           
        </table>	
	
  <div id="map_canvas" style="width:70%; height:100%"></div>