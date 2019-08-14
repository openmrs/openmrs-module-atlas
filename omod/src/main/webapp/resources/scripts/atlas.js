var $j = jQuery.noConflict();
var dialog;

function initializeDialog() {
    dialog = emr.setupConfirmationDialog({
        selector: '#atlas-dialog-data',
        actions: {
            confirm: function() {
                dialog.close();
            }
        }
    });
    $j(".atlas-show-dialog").click(function() {
        getJsonDataFromServer();
        dialog.show();
    })
    $j(".toast-item-close").click(function() {
        dialog.close();
    });
}
function isModuleConnect(url, module_id, token) {
    $j.ajax({
        url: url,
        type: "POST",
        data: encodeURIComponent("module_id")+"="+encodeURIComponent(module_id)+"&"+encodeURIComponent("token")+"="+encodeURIComponent(token)
    })
    .done(function(response) {
        auth = response;
        if (auth) {
            $j('#unlinked').hide();
            $j('#module-control').show();
            return (connected = true);
        } else {
            $j('#module-control').hide();
            $j('#unlinked').show();
            disableAtlasModuleOnServer();
            return (connected = false);
        }
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $j('#module-control').hide();
        $j('#unlinked').hide();
        if (jqXHR.status !== 401)
            alert("Module is not connnected - Please try again ! - ");
        else
            disableAtlasModuleOnServer();
        return (connected = false);
    });
}
function isChecked(id, container) {
    if ($j(id, container).attr('checked') == true)
        return "true";
    if ($j(id, container).attr('checked') == "true")
        return "true";
    if ($j(id, container).attr('checked') == 'checked')
        return "true";
    return "false";
}

function getIsDirtyFromServer() {
    // if you want to update the dirty state from the server, uncomment the
    // setTimeout call and comment the getIsDirtyFromServerCallback call
    // setTimeout("DWRAtlasService.getIsDirty(getIsDirtyFromServerCallback)",500);
    getIsDirtyFromServerCallback(true);
}

function getIsDirtyFromServerCallback(isDirty) {
    if (isDirty && $j('#enabled').is(':visible')) {
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

function disableAtlasModuleOnServer() {
    DWRAtlasService.disableAtlasModule();
}

function getJsonDataFromServer() {
    DWRAtlasService.getJsonData(getJsonDataFromServerCallback);
}

function setSendCounts(sendCounts) {
    DWRAtlasService.setSendCounts(sendCounts);
}
function getJsonDataFromServerCallback(jsonData) {
    $j('#atlas-dialog-jsonData').val(jsonData);
}
function initializeAtlas() {
    $btnEnabled = $j('#enabled-button');
    $btnDisabled = $j('#disabled-button');
    $btnCountsEnabled = $j('#counts-enabled');
    $btnCountsDisabled = $j('#counts-disabled');
    $divEnabled = $j('#enabled');
    $divDisabled = $j('#disabled');
    
    $btnEnabled.click(function(event) {
        $divDisabled.show();
        $divEnabled.hide();
        $btnCountsDisabled.show();
        $btnCountsEnabled.hide();
        disableAtlasModuleOnServer();
        event.preventDefault();
    });

    $btnDisabled.click(function(event) {
        $divEnabled.show();
        $divDisabled.hide();
        enableAtlasModuleOnServer();
        event.preventDefault();
    });

    $btnCountsEnabled.click(function(event) {
        $btnCountsDisabled.show();
        $btnCountsEnabled.hide();
        setSendCounts(false);
        event.preventDefault();
    });

    $btnCountsDisabled.click(function(event) {
        $btnCountsEnabled.show();
        $btnCountsDisabled.hide();
        setSendCounts(true);
        event.preventDefault();
    });
}
function initializeWhatWillBeSentModalWindow() {
	var titleText = $j("#atlas-gutter-sentInfoTitle").text();
	var $whatWillBeSendWindow = $j("#atlas-gutter-sentInfo");
	$whatWillBeSendWindow.dialog({ autoOpen: false
        , modal: true
        , width: 500
        , title : titleText
       });
 	
    $j(".atlas-show-dialog").click(function() {
        getJsonDataFromServer();
        $whatWillBeSendWindow.dialog('open');
    });
}