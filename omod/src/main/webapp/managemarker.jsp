<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<meta name="viewport" content="initial-scale=1.0, user-scalable=yes"/>

<openmrs:htmlInclude file="/moduleResources/atlas/scripts/jquery-1.11.1.min.js"/>
<openmrs:htmlInclude file="/moduleResources/atlas/scripts/jquery-ui.min.js"/>
<script src="<openmrs:contextPath/>/dwr/interface/DWRAtlasService.js"></script>
<openmrs:htmlInclude file="/moduleResources/atlas/styles/atlas.css"/>
<openmrs:htmlInclude file="/moduleResources/atlas/styles/bootstrap-btn.css"/>
<openmrs:htmlInclude file="/moduleResources/atlas/scripts/atlas.js"/>

<script type="text/javascript">
    var jq = jQuery.noConflict();
    var connected;
    function updateModulefromServer() {
        connected = isModuleConnect("${atlasData.serverUrl}/auth?uuid=${atlasData.id}&callback=getAuth");
    }
    function receiveMessage(event) {
        updateModulefromServer();
        if (event.data === "save")
            sendPostCommandToServer();
        if (event.data === "countsEnabled")
            setSendCounts(true);
        if (event.data == "countsDisabled")
            setSendCounts(false);
        addEventListener("message", receiveMessage, false);
    }
    jq(document).ready(function () {
        addEventListener("message", receiveMessage, false);
        initializeAtlas();
        initializeWhatWillBeSentModalWindow();
    });
</script>

<h2>
    <spring:message code="atlas.manageMarkerLink"/>
</h2>
<br/>

<openmrs:require privilege="Manage Atlas Data" otherwise="/login.htm"
                 redirect="/index.htm"/>

<div id="atlas-map">
    <iframe src="${atlasData.serverUrlEncoded}"
            name="atlas" style="height: 550px;width: 100%" id="atlas"></iframe>

    <div class="note-container" id="module-control" style="display:none">
        <div class="note" id="disabled" style="background-color: rgba(255, 95, 95, 0.73);  
             <c:if test="${atlasData.moduleEnabled}"> display: none; </c:if> margin-left: -100px;">
            <div class="text">
                <strong><spring:message code="atlas.disabled"/></strong><br>
                <strong><spring:message code="atlas.autoUpdates"/></strong>

                <div id="disabled-button" class="btn-group btn-toggle">
                    <button class="btn btn-xs btn-danger active"><spring:message
                            code="atlas.buttonDisabled"/></button>
                    <button class="btn btn-xs btn-default active"><spring:message
                            code="atlas.buttonEnabled"/></button>
                </div>
            </div>
            <div class="atlas-show-dialog"><i class="icon-info-sign small" style="vertical-align: middle"></i>
                <spring:message
                        code="atlas.dataSend"/>
            </div>
        </div>

        <div class="note" id="enabled" style="background-color: rgba(156, 214, 16, 0.53);
                 <c:if test="${not atlasData.moduleEnabled}"> display: none; </c:if> margin-left: 0px;">
            <div class="text">
                <strong><spring:message code="atlas.autoUpdates"/></strong>

                <div id="enabled-button" class="btn-group btn-toggle">
                    <button class="btn btn-xs btn-default active"><spring:message
                            code="atlas.buttonDisabled"/></button>
                    <button class="btn btn-xs btn-success active"><spring:message
                            code="atlas.buttonEnabled"/></button>
                </div>
            </div>
            <div class="text">
                <strong><spring:message code="atlas.sendCounts"/></strong>
                <div id="counts-button" class="btn-group btn-toggle">
                    <div id="counts-enabled" style="<c:if test="${not atlasData.sendCounts}">
                    display: none;</c:if>">
                        <button class="btn btn-xs btn-default active"><spring:message
                                code="atlas.buttonDisabled"/></button>
                        <button class="btn btn-xs btn-success active"><spring:message
                                code="atlas.buttonEnabled"/></button>
                    </div>

                    <div id="counts-disabled" style="<c:if test="${atlasData.sendCounts}">
                    display: none;</c:if>" >
                        <button class="btn btn-xs btn-danger active"><spring:message
                                code="atlas.buttonDisabled"/></button>
                        <button class="btn btn-xs btn-default active"><spring:message
                                code="atlas.buttonEnabled"/></button>
                    </div>
                </div>
            </div>
            <div class="atlas-show-dialog"><i class="icon-info-sign small" style="vertical-align: middle"></i>
                <spring:message code="atlas.dataSend"/></div>
        </div>
    </div>
    <div class="note-container" style="display:none" id="unlinked">
        <div class="note" style="background-color: rgba(255, 95, 95, 0.73); margin-left: -200px; width: 475px;">
            <div class="text">
                <p style="text-align: center"><strong><spring:message code="atlas.unlinked"/></strong></p>
            </div>
        </div>
    </div>
</div>

<div id="atlas-gutter-sentInfo" style="display: none">
    <br><br><textarea
        id="atlas-dialog-jsonData" cols="75" rows="10" readonly="readonly"
        style="resize: none;"></textarea>
</div>


<%@ include file="/WEB-INF/template/footer.jsp" %>