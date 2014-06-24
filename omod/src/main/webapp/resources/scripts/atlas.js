var $j = jQuery.noConflict();
var containerEdit;
var dialog;

$j(document).ready(function() {
    containerEdit = document.getElementById('content');
    initializeAtlas();
    initializeDialog();
});

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
function isModuleConnect(url) {
    $j.ajax({
        url: url,
        type: "GET",
        dataType: "json",
    })
    .done(function(response) {
        auth = response;
        if (auth.length > 0) {
            $j('#unlinked').hide();
            $j('#module-control').show();
            return (connected = true);
        } else {
            $j('#module-control').hide();
            $j('#unlinked').show();
            return (connected = false);
        }
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $j('#enabled').hide();
        $j('#disabled').hide();
        alert("Module is not connnected - Please try again ! - ");
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

function getJsonDataFromServer() {
    DWRAtlasService.getJsonData(getJsonDataFromServerCallback);
}
function getJsonDataFromServerCallback(jsonData) {
    $j('#atlas-dialog-jsonData').val(jsonData);
}
function initializeAtlas() {
    $btnEnabled = $j('#enabled-button');
    $btnDisabled = $j('#disabled-button');
    $divEnabled = $j('#enabled');
    $divDisabled = $j('#disabled');
    
    //$updateAtlasNowLink = $j('#atlas-gutter-updateAtlasNowLink');
    $btnEnabled.click(function(event) {
        $divDisabled.show();
        $divEnabled.hide();
        //$updateAtlasNowLink.hide();
        disableAtlasModuleOnServer(true);
        event.preventDefault();
    });

    $btnDisabled.click(function(event) {
        $divEnabled.show();
        $divDisabled.hide();
        //$updateAtlasNowLink.hide();
        enableAtlasModuleOnServer();
        event.preventDefault();
    });

    //$updateAtlasNowLink.click(function (event) {
    //	sendPostCommandToServer();
    // comment the next line if you use the DWR service to get isDirty value
    //$updateAtlasNowLink.hide();
    //	event.preventDefault();
    //});
}