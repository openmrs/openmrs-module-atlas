<%@page import="org.openmrs.module.atlas.*"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=yes" />
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=true">
</script>

<script  type="text/javascript">
//this script dinamically adds the jQuery and jQuery UI scripts to the page if they aren't loaded
//NOTE: once open mrs 1.6 is not supported anymore you don't need this script
if (typeof jQuery == 'undefined') {
	document.write("<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js'><\/script>");
	document.write("<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/jquery-ui.min.js'><\/script>");
}

	$j(document).ready(function() {
		
		$j('#atlas-gutter-btnDisable').click(function(event) {
			if (!ViewIsEmpty()) {
				$j('#atlas-gutter-btnDisable').hide();
				$j('#atlas-gutter-btnEnable').show();
				enableAtlasModuleOnServer();
			} else {
				alert('<spring:message code="atlas.implementationNameIsEmpty" />');
			}
			event.preventDefault();
		});
		
	 });
</script>
<script src="<openmrs:contextPath/>/dwr/interface/DWRAtlasService.js"></script>
<openmrs:htmlInclude file="/scripts/jQuery/dataTables/dataTables.css" />
<openmrs:htmlInclude file="/moduleResources/atlas/jquery-ui.atlas.css" />

<openmrs:htmlInclude file="/moduleResources/atlas/atlas.css" />
<openmrs:htmlInclude file="/moduleResources/atlas/atlas.js" />
<openmrs:htmlInclude file="/moduleResources/atlas/yqlgeo.js" />
<openmrs:htmlInclude file="/moduleResources/atlas/yqlgeo.js" />


<h2>
	<spring:message code="atlas.manageMarkerLink" />
</h2>
<br />

<openmrs:require privilege="Manage Atlas Data" otherwise="/login.htm"
	redirect="/index.htm" />

<div id="atlas-gutter-leftColumnDiv">
	<form method="post">
		<input type="hidden" id="atlas-hidden-latitude"
			name="atlas-hidden-latitude" value="${atlasData.latitude}" /> <input
			type="hidden" id="atlas-hidden-longitude"
			name="atlas-hidden-longitude" value="${atlasData.longitude}" /> <input
			type="hidden" id="atlas-hidden-zoom" name="atlas-hidden-zoom"
			value="${atlasData.zoom}" /> <input type="hidden"
			id="atlas-hidden-implementationTypeOrdinal"
			value="${atlasData.implementationType}"> <b class="boxHeader"><spring:message
				code="atlas.enableDisableHeader" /> </b>
		<div class="box">
			<table>
				<tr>
					<td><input type="checkbox" id="atlas-gutter-cbDisclaimer"
						<c:if test="${atlasData.usageDisclamerAccepted == true}">
										checked="checked"
									</c:if>>
					</td>
					<td><label for="atlas-gutter-cbDisclaimer"
						style="font-size: 1.2em"> <spring:message
								code="atlas.userAgreement" /> </label> <br /> <span
						style="font-size: 0.9em"> <spring:message
								code="atlas.userAgreementParanthesis" /> </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="atlas-gutter-divCenter">
							<input id="atlas-gutter-btnEnable" type="submit"
								class="atlas-gutter-btnEnabled" onclick=""
								<c:if test="${atlasData.moduleEnabled == false}">
										style="display: none"
					</c:if>
								value="<spring:message code="atlas.buttonEnabled" />" /> <input
								id="atlas-gutter-btnDisable" type="submit"
								class="atlas-gutter-btnDisabled" onclick=""
								<c:if test="${atlasData.moduleEnabled == true}">
										style="display: none"
					</c:if>
								value="<spring:message code="atlas.buttonDisabled" />" /> <br>
							<div id="atlas-gutter-updateAtlasNowDiv"
								style="display: block; min-height: 20px;">
								<a id="atlas-gutter-updateAtlasNowLink" href=""
									<c:if test="${atlasData.moduleEnabled == false ||  atlasData.isDirty == false}">
											style="display: none"
			</c:if>
									title="<spring:message code="atlas.updateAtlasNowLink" />"><spring:message
										code="atlas.updateAtlasNowLink" /> </a>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox"
						id="atlas-gutter-cbIncludeSystemConfiguration"
						<c:if test="${atlasData.includeSystemConfiguration == true}">
										checked="checked"
									</c:if>>
					</td>
					<td><label for="atlas-gutter-cbIncludeSystemConfiguration"
						style="font-size: 1.2em"> <spring:message
								code="atlas.addSystemConfigurationLabel" /> </label> <br> <a
						id="atlas-gutter-includeSystemConfigurationTip" href=""
						title="<spring:message code="atlas.addSystemConfigurationTip" />"><spring:message
								code="atlas.addSystemConfigurationLink" /> </a>
					</td>
				</tr>
			</table>
		</div>

	</form>
</div>
<div id="atlas-map-rightColumnDiv">
	<iframe src="http://107.170.156.44?module=1" name="atlas" style="height: 100%;width: 100%" id="atlas"></iframe>
</div>

<div id="atlas-gutter-sentInfo">
	<span> <span id="atlas-gutter-sentInfoTitle"
		style="display: none"><spring:message
				code="atlas.whatWillBeSentInfoTitle" /> </span> <spring:message
			code="atlas.whatWillBeSentInfo" /> <br> <br> <textarea
			id="atlas-gutter-jsonData" cols="50" rows="10" readonly="readonly"
			style="resize: none;"></textarea> <br>
		<div style="margin: auto; width: 50px;">
			<input id="atlas-gutter-sentInfo-close" type="submit"
				value="<spring:message code="atlas.ok" />" />
		</div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
