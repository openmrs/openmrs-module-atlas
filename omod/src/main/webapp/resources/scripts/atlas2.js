var $j = jQuery.noConflict();
var containerEdit;

$j(document).ready (function() {
    containerEdit = document.getElementById('content');
 	initializeGutter();
});

function isChecked(id, container) {
	 if ($j(id, container).attr('checked') == true)
		 return "true";
	 if ($j(id, container).attr('checked') == "true")
		 return "true";
	 if ($j(id, container).attr('checked') == 'checked')
		 return "true";
	 return "false";
}

function saveAtlasBubbleDataOnServer() {
    
	var includeNumberOfPatients = isChecked('#atlas-edit-cbPatients', containerEdit);
	var includeNumberOfObservations = isChecked('#atlas-edit-cbObservations', containerEdit);
	var includeNumberOfEncounters = isChecked('#atlas-edit-cbEncounters', containerEdit);
	
	DWRAtlasService.saveAtlasBubbleData(
			includeNumberOfPatients, includeNumberOfObservations, includeNumberOfEncounters);
            
	getIsDirtyFromServer();
}

function getIsDirtyFromServer() {
	// if you want to update the dirty state from the server, uncomment the
	// setTimeout call and comment the getIsDirtyFromServerCallback call
	// setTimeout("DWRAtlasService.getIsDirty(getIsDirtyFromServerCallback)",500);
	getIsDirtyFromServerCallback(true);
}

function getIsDirtyFromServerCallback(isDirty) {
	if (isDirty &&  $j('#atlas-btnEnable').is(':visible')) {
		//$j('#atlas-gutter-updateAtlasNowLink').show();
	} else {
		//$j('#atlas-gutter-updateAtlasNowLink').hide();
	}
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
function setIncludeNbObs(value) {
	DWRAtlasService.setIncludeNbObs(value);
	getIsDirtyFromServer();
}
function setIncludeNbEncounters(value) {
	DWRAtlasService.setIncludeNbEncounters(value);
	getIsDirtyFromServer();
}
function setIncludeNbPatients(value) {
	DWRAtlasService.setIncludeNbPatients(value);
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
	$btnEnabled = $j('#atlas-btnEnabled');
	$btnDisabled = $j('#atlas-btnDisabled');
	$cbIncludeSystemConfiguration = $j('#atlas-includeSysConf');
    $cbIncludeNbObs = $j('#atlas-includeNbObs');
    $cbIncludeNbPatients = $j('#atlas-includeNbPatients');
    $cbIncludeNbEncounters = $j('#atlas-includeNbEncounters');
    
	//$updateAtlasNowLink = $j('#atlas-gutter-updateAtlasNowLink');
	
	$cbIncludeSystemConfiguration.click(function() {
		setIncludeSystemConfigurationOnServer($j(this).is(':checked'));
	});
    $cbIncludeNbObs.click(function() {
		setIncludeNbObs($j(this).is(':checked'));
	});
    $cbIncludeNbPatients.click(function() {
		setIncludeNbPatients($j(this).is(':checked'));
	});
    $cbIncludeNbEncounters.click(function() {
		setIncludeNbEncounters($j(this).is(':checked'));
	});
	
	if (!$j('#atlas-disclaimer').is(':checked')) {
		  $btnDisabled.addClass("disabled");
          $btnDisabled.attr("disabled", true);
          $cbIncludeSystemConfiguration.attr('disabled', true);
          $cbIncludeNbPatients.attr('disabled', true);
          $cbIncludeNbEncounters.attr('disabled', true);
          $cbIncludeNbObs.attr('disabled', true);
         
	}
	
	$j('#atlas-disclaimer').click(function() {
	    if ($j(this).is(':checked')) {
	    	if ($btnDisabled.attr("disabled") === true || 
                    $btnDisabled.attr("disabled") === 'disabled') {
	    		$btnDisabled.attr("disabled", false);
                $btnDisabled.removeClass("disabled");
	    		$cbIncludeSystemConfiguration.attr("disabled", false);
                $cbIncludeNbPatients.attr('disabled', false);
                $cbIncludeNbEncounters.attr('disabled', false);
                $cbIncludeNbObs.attr('disabled', false);
	    	}
	    } else {
            if ($btnEnabled.is(':visible')) {
                $btnDisabled.show();
                $btnEnabled.hide();
                //$updateAtlasNowLink.hide();
                disableAtlasModuleOnServer($j('#atlas-disclaimer').is(':checked'));
	       }  
           $btnDisabled.attr("disabled", true);
           $btnDisabled.addClass("disabled");
           $cbIncludeSystemConfiguration.attr("disabled", true);
           $cbIncludeNbPatients.attr('disabled', true);
           $cbIncludeNbEncounters.attr('disabled', true);
           $cbIncludeNbObs.attr('disabled', true);
	    } 
	    setUsageDiscailmerOnServer($j(this).is(':checked'));
	});
	
	$btnEnabled.click(function(event) {
        if ($j('#atlas-disclaimer').is(':checked')){
            $btnDisabled.show();
            $btnEnabled.hide();
            //$updateAtlasNowLink.hide();
            disableAtlasModuleOnServer($j('#atlas-disclaimer').is(':checked'));
            event.preventDefault();
        }
	});
    
    $btnDisabled.click(function(event) {
        if ($j('#atlas-disclaimer').is(':checked')) {
            $btnEnabled.show();
            $btnDisabled.hide();
            //$updateAtlasNowLink.hide();
            enableAtlasModuleOnServer();
            event.preventDefault();
        }
	});
	
	//$updateAtlasNowLink.click(function (event) {
	//	sendPostCommandToServer();
		// comment the next line if you use the DWR service to get isDirty value
		//$updateAtlasNowLink.hide();
	//	event.preventDefault();
	//});
}