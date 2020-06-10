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
        connected = isModuleConnect("${atlasData.serverUrl}", "${atlasData.id}", "${atlasData.token}");
    }
    function receiveMessage(event){
      updateModulefromServer();
      if (event.data === "save")
        sendPostCommandToServer();
      if (event.data === "countsEnabled")
        setSendCounts(true);
      if (event.data =="countsDisabled")
        setSendCounts(false);
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
        <iframe src="${atlasData.serverUrlEncoded}" name="atlas" id="atlas"></iframe>
        <div class="note-container" id="module-control" style="display:none">
            <div class="note" id="disabled" style="background-color: rgba(255, 95, 95, 0.73);  
            <% if (atlasData.moduleEnabled) {%> display: none; <% } %> margin-left: -75px; min-width: 475px;">
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
            <div class="note" id="enabled" style="background-color: rgba(156, 214, 16, 0.83); <%  if (!atlasData.moduleEnabled) {%>
                    display: none; <% } %> margin-left: 0px;">
                
                <div class="text">
                    <p><strong>${ ui.message("atlas.autoUpdates")}</strong></p>
                    <div id="enabled-button"class="btn-group btn-toggle"> 
                        <button class="btn btn-xs btn-default active">${ ui.message("atlas.buttonDisabled")}</button>
                        <button class="btn btn-xs btn-success active">${ ui.message("atlas.buttonEnabled")}</button>
                    </div>
                </div>
                <div class="text">
                    <p><strong>${ ui.message("atlas.sendCounts")}</strong></p>
                    <div id="counts-button" class="btn-group btn-toggle">
                        <div id="counts-enabled" style="<%  if (!atlasData.sendCounts) {%>
                        display: none;<% } %> ">
                            <button class="btn btn-xs btn-default active">${ ui.message("atlas.buttonDisabled")}</button>
                            <button class="btn btn-xs btn-success active">${ ui.message("atlas.buttonEnabled")}</button>
                        </div>
                        <div id="counts-disabled" style="<%  if (atlasData.sendCounts) {%>
                        display: none;<% } %> ">
                            <button class="btn btn-xs btn-danger active">${ ui.message("atlas.buttonDisabled")}</button>
                            <button class="btn btn-xs btn-default active">${ ui.message("atlas.buttonEnabled")}</button>
                        </div>
                    </div>
                </div>
                <div class="atlas-show-dialog"><i class="icon-info-sign small" style="vertical-align: middle"></i> ${ ui.message("atlas.dataSend")}</div>
            </div>
        </div>
        <div class="note-container" style="display:none" id="unlinked">
            <div class="note" style="background-color: rgba(255, 95, 95, 0.73); margin-left: -75px; min-width: 475px;">
                <div class="text">
                    <p style="text-align: center"><strong>${ ui.message("atlas.unlinked")}</strong></p>
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

    <script>
        window.addEventListener("message", function(event) {
            if(event.data === "atlas loaded") {
                var atlasFrame = document.getElementsByTagName('iframe')[0].contentWindow;
                atlasFrame.postMessage("module_id:${atlasData.id}", "*");
                atlasFrame.postMessage("token:${atlasData.token}", "*");
                atlasFrame.postMessage("has_site:"+connected, "*");
            } 
        }, false);
    </script>

    <% if (!atlasData.moduleEnabled && !stopAskingToConfigure) { %>
        <div>
            <a href="?stopAskingToConfigure=true">${ ui.message("atlas.stopAskingToConfigure") }</a>
        </div>
    <% } %>
</body>