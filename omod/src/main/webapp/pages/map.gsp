<%
ui.decorateWith("appui", "standardEmrPage")
ui.includeJavascript("atlas", "atlas.js")
ui.includeCss("atlas", "bootstrap-btn.css")
ui.includeCss("atlas", "atlas-2.0.css")
%>
<script src="/${ contextPath }/dwr/interface/DWRAtlasService.js"></script>
<script src="/${ contextPath }/dwr/engine.js"></script>
<script type="text/javascript">
    var jq = jQuery;
    var breadcrumbs = [
    { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("atlas.title")}"}
    ];
    var connected;
    function updateModulefromServer() {
        connected = isModuleConnect("${data.serverUrl}/auth?uuid=${data.id}&callback=getAuth");
    }
    function receiveMessage(event){
      updateModulefromServer();
      addEventListener("message", receiveMessage, false);
    }
    jq(document).ready(function() {
        addEventListener("message", receiveMessage, false);
        initializeAtlas();
        initializeDialog();
    });
</script>
<body>
    <div id="home">
        <iframe src="${data.serverUrl}?uuid=${data.id}&patients=${data.numberOfPatients}&encounters=
                ${data.numberOfEncounters}&observations=${data.numberOfObservations}" name="atlas" id="atlas"></iframe>
        <div class="note-container" id="module-control" style="display:none">
            <div class="note" id="disabled" style="background-color: rgba(255, 95, 95, 0.73);  
            <% if (data.moduleEnabled) {%> display: none; <% } %> margin-left: -100px;">
                <div class="text">
                    <p><strong>${ ui.message("atlas.disabled")}</strong></p>
                    <br><p><strong>${ ui.message("atlas.autoUpdates")}</strong></p>
                    <div id="disabled-button" class="btn-group btn-toggle"> 
                        <button class="btn btn-xs btn-danger active">${ ui.message("atlas.buttonDisabled")}</button>
                        <button class="btn btn-xs btn-default active">${ ui.message("atlas.buttonEnabled")}</button>
                    </div>
                </div>
                <div class ="atlas-show-dialog"><i class="icon-info-sign small" style="vertical-align: middle"></i> ${ ui.message("atlas.dataSend")}</div>
            </div>
            <div class="note" id="enabled" style="background-color: rgba(156, 214, 16, 0.53); <%  if (!data.moduleEnabled) {%>
                    display: none; <% } %> margin-left: 0px;">
                
                <div class="text">
                    <p><strong>${ ui.message("atlas.autoUpdates")}</strong></p>
                    <div id="enabled-button"class="btn-group btn-toggle"> 
                        <button class="btn btn-xs btn-default active">${ ui.message("atlas.buttonDisabled")}</button>
                        <button class="btn btn-xs btn-success active">${ ui.message("atlas.buttonEnabled")}</button>
                    </div>
                </div>
                <div class="atlas-show-dialog"><i class="icon-info-sign small" style="vertical-align: middle"></i> ${ ui.message("atlas.dataSend")}</div>
            </div>
        </div>
        <div class="note-container" style="display:none" id="unlinked">
            <div class="note" style="background-color: rgba(255, 95, 95, 0.73); margin-left: -100px;">
                <div class="text">
                    <p><strong>${ ui.message("atlas.unlinked")}</strong></p>
                </div>
            </div>
        </div>
    </div>
    <div class="dialog" id="atlas-dialog-data" style="display: none; width:530px">
        <div class="dialog-header">
            <i class="icon-info-sign"></i>
            <h3>${ ui.message("atlas.whatWillBeSentInfoTitle")} </h3>
            <div class="toast-item-close"></div>
        </div>
        <div class="dialog-content">
            <textarea
                id="atlas-dialog-jsonData" cols="50" rows="10" readonly="readonly"
                style="resize: none;"></textarea>
        </div>
    </div>
</body>