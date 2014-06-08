<%
ui.decorateWith("appui", "standardEmrPage")
ui.includeCss("uicommons", "styleguide/index.css")
ui.includeJavascript("atlas", "atlas2.js")
%>
<script src="/${ contextPath }/dwr/interface/DWRAtlasService.js"></script>
<script src="/${ contextPath }/dwr/engine.js"></script>
<script type="text/javascript">
    var jq = jQuery;
</script>
<style>
#atlas {
    min-height: 600px;
    max-height: 1000px;
    width: 100%;
    overflow:hidden;
}
#body-wrapper .style-guide {
    width: initial;
    margin-top:0px;
   -moz-border-radius: 0px;
   -webkit-border-radius: 0px;
   -o-border-radius: 0px;
   -ms-border-radius: 0px;
   -khtml-border-radius: 0px;
   border-radius: 0px
}
#activate {
    padding:20px;
    width:600px;
}
</style>
<script type="text/javascript">
    var breadcrumbs = [
    { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("atlas.title")}"}
    ];
    jq(function() {
        jq("#tabs").tabs();
    });
</script>
<body data-spy="scroll" data-target="#menu">
    <h1>
        ${ ui.message("atlas.title") }
    </h1>
    <div id="tabs">
        <ul>
            <li><a href="#home">${ ui.message("atlas.home") }</a></li>
            <li><a href="#body-wrapper">${ ui.message("atlas.manage") }</a></li>
        </ul>
        <div id="home">
            <iframe src="http://localhost/openmrs-contrib-atlas/public/module?uuid=${data.id}" name="atlas" id="atlas"></iframe>
        </div>
        <div id="body-wrapper" class="style-guide"> 
            <aside id="menu-container">
                <section id="menu" >
                    <h3>${ ui.message("atlas.configuration") }</h3>
                    <ul id="menu-list" class="nav">
<!--
                        <li>
                            <a href="#login">OpenMRS ID</a>
                        </li> 
-->
                        <li>
                            <a href="#markers">Send Data</a>
                        </li>
                        <li>
                            <a href="#data">Configuration Sharing</a>
                        </li>
                    </ul>
                </section>
            </aside>

            <div id="content" style="width:70%;padding-left:20px;">
<!--
                <article id="login">
                    <h2>OpenMRS ID</h2>
                    <section>
                        <p>Connect with your OpenMRS ID to link this implementation
                            to your markers.</p><br>
                    </section>
                    <section>
                        <div id="error-message" class="note-container">
                            <div class="note warning">
                                <div class="text">
                                    <i class="icon-lock medium"></i>
                                    <p>Your are not logged-in yet</p>
                                </div>
                            </div>
                        </div>
                    </section>
                </article>
-->
                <article id="markers">
                    <h2>Enable data sending</h2>
                    <section>
                        <fieldset>
                            <legend>Send Information</legend>
                            <p><br>
                                <input type="checkbox" id="atlas-disclaimer"
                                    <% if (data.usageDisclamerAccepted) { %>
                                checked="checked"
                                    <% } %>
                                </input>
                                <label><b>Send information periodically</b></label>
                            </p>
                            <em>${ ui.message("atlas.userAgreement") }<br><small>
                                ${ ui.message("atlas.userAgreementParanthesis") }</small>
                            </em><br>
<!--
                            <span class="select-arrow">
                                <br>
                                <select>
                                    <option>Marker site name</option>
                                    <option>Marker site name 2</option>
                                </select>
                            </span>
-->
                            <p>
                                <br>
                                <a id="atlas-btnDisabled"
                                <% if (data.moduleEnabled == true) { %>
                                    style="display: none" 
                                 <% } %>
                                 class="button confirm">
                                    <i class ="icon-upload-alt"></i>
                                    Enable information sending
                                </a>
                                <a id="atlas-btnEnabled"
                                <% if (data.moduleEnabled == false) { %>
                                    style="display: none" 
                                 <% } %>
                                 class="button cancel">
                                    <i class ='icon-stop'></i> 
                                    Disable information sending
                                </a>
                            </p>
                        </fieldset>
                    </section>
                </article>
                <article id="data">
                    <h2>Configuration sharing</h2>
                    <section>
                        <fieldset>
                            <legend>Sharing</legend><br>
                            <em>Select information you want to send.</em><br><br>
                            <input type="checkbox" id="atlas-includeNbPatients"
                                <% if (data.includeNumberOfPatients) { %>
                                    checked="checked"
                                <% } %>
                            ></input>
                            <label>Share numbers of <b>patients</b></label><br>
                            <input type="checkbox" id="atlas-includeNbEncounters"
                                <% if (data.includeNumberOfEncounters) { %>
                                    checked="checked"
                                <% } %>
                            ></input>
                            <label>Share number of <b>encounters</b></label><br>
                            <input type="checkbox" id="atlas-includeNbObs"
                                <% if (data.includeNumberOfObservations) { %>
                                    checked="checked"
                                <% } %>
                            ></input>
                            <label>Share number of <b>observations</b></label><br>
                            <input type="checkbox" id="atlas-includeSysConf"
                                <% if (data.includeSystemConfiguration) { %>
                                    checked="checked"
                                <% } %>
                            ></input>
                            <label>Share my <b>system configuration</b> with OpenMRS</b>
                                <em><small>(will not be shown on the Atlas).</small></em>
                            </label><br><br>
                            <small><a href=""><i class="icon-info-sign small"></i>  What will be send ?</a></small>
                        </fieldset>
                    </section>
                </article>
            </div>
        </div>
    </div>
</body>