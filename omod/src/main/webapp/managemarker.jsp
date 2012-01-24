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
<div id="atlas-view-containerDiv" class='site-bubble' style="display: hidden">
	<div class='site-name'>
		<span id="atlas-view-lblName" class="spanView"><c:out
									value="${atlasData.name}" />
		</span>
	</div>
	<div class='site-panel'>
		<img id="atlas-view-imgImplementation" class='site-image'
			src="${atlasData.imageURL}" width="80px" height="80px"
			alt="thumbnail" />
		<div class='site-url'>
			<a id="atlas-view-aWebsite" target='_blank'
						href="${atlasData.website}" class="spanView"><span
							id="atlas-view-lblWebsite"><c:out
									value="${atlasData.website}" /></span> 
			</a>
	   </div>
	   <div class='site-count'>
	   		<span class="stats" id="atlas-view-lblPatients"
						<c:if test="${atlasData.includeNumberOfPatients == false}">
										style="display: none"
							    </c:if>>
							<span id="atlas-view-lblPatientsNr"><c:out value="${atlasData.numberOfPatients}" /></span> <spring:message	code="atlas.patients" />
							<br />
			</span> 
	   </div>
	    <div class='site-count'>
	   		<span class="stats" id="atlas-view-lblEncounters"
						<c:if test="${atlasData.includeNumberOfEncounters == false}">
										style="display: none"
							   </c:if>>
							<span id="atlas-view-lblEncountersNr"><c:out value="${atlasData.numberOfEncounters}" /> </span> <spring:message code="atlas.encounters" />
							<br />
			</span> 
	   </div>
	    <div class='site-count'>
	   		<span class="stats" id="atlas-view-lblObservations"
						<c:if test="${atlasData.includeNumberOfObservations == false}">
										style="display: none"
							   </c:if>>
							<span id="atlas-view-lblObservationsNr"><c:out value="${atlasData.numberOfObservations}" /></span> <spring:message code="atlas.observations" />
			</span>
	   </div>
	   <div class='site-contact' id="atlas-view-divContact"><span class='site-label'><spring:message code="atlas.contactNameLabel" /></span><span class="site-contact spanView"
			id="atlas-view-lblContactName"><c:out
					value="${atlasData.contactName}" /></span>
		
		<a href="mailto:${atlasData.contactEmailAddress}" id="atlas-view-aEmail" class="site-email"><img  alt="mail" id="atlas-view-imgEmail"
			src="../../moduleResources/atlas/mail.png" width='15px' height='15px'/></a>
	  </div>
	</div>
  <fieldset id="atlas-view-fieldsetNotes" class='site-notes'><legend><spring:message code="atlas.notesLabel" /></legend><span class="spanView" id="atlas-view-lblNotes"><c:out
					value="${atlasData.notes}" /></span>
  </fieldset>
  <div class='site-type'> 
  	<span id="atlas-view-lblImplementationType" class="site-type"></span> 
  </div>
  <div>
			<span style="float: right"> <a
				id="atlas-view-editLink" href=""><spring:message
						code="atlas.edit" /></a></span>
   </div>
  <div>
  	<br /><br /><br /><br />
  </div>			
  </div>
  
  <div id="atlas-edit-containerDiv" class='site-bubble' style="display: hidden">
	<div class="atlas-edit-div site-name">
			<input type="text" id="atlas-edit-tbName"
						placeholder="<spring:message code="atlas.namePlaceHolder" />">
						<span id="atlas-edit-nameError" class="fail"
						style="display: none;">*</span>
	</div>
	<div class='site-panel'>
		<div id="atlas-edit-imgDiv"  class='site-image' style="width: 100px; height: 150px">
				<textarea rows="6" cols="9"
				id="atlas-edit-tbImage" style="resize: none;"
					placeholder="<spring:message code="atlas.imagePlaceHolder" />"></textarea>
		</div>
		<div class="atlas-edit-div site-url">
			<input type="text" id="atlas-edit-tbWebsite"
						placeholder="<spring:message code="atlas.websitePlaceHolder" />">
		</div>
		<div class="atlas-edit-div site-count">
			<input type="checkbox" id="atlas-edit-cbPatients"
						<c:if test="${atlasData.includeNumberOfPatients == true}">
										checked="checked"
									</c:if>>
						<label for="atlas-edit-cbPatients"> <spring:message
								code="atlas.includeNrOfPatients" /> </label>
			</div>
			<div class="atlas-edit-div site-count">	
			<input type="checkbox" id="atlas-edit-cbEncounters"
						<c:if test="${atlasData.includeNumberOfEncounters == true}">
							    		checked="checked"
							    	</c:if>>
						<label for="atlas-edit-cbEncounters"> <spring:message
								code="atlas.includeNrOfEncounters" /> </label>
		   </div>
		   <div class="atlas-edit-div site-count">	
		   <input type="checkbox" id="atlas-edit-cbObservations"
						<c:if test="${atlasData.includeNumberOfObservations == true}">
							    		checked="checked"
							    	</c:if>>
						<label for="atlas-edit-cbObservations"> <spring:message
								code="atlas.includeNrOfObservations" /> </label>
		  </div>
	
	   <div class='site-contact'>
		   <span class='site-label'><spring:message code="atlas.contactNameLabel" /></span>
		   <span style="display: table-cell">
		   <input type="text" id="atlas-edit-tbContactName" class="site-contact"
				placeholder="<spring:message code="atlas.contactNamePlaceHolder" />">
				<br/>
			<input type="text" id="atlas-edit-tbEmail" style="margin-top: 3px"
				placeholder="<spring:message code="atlas.emailPlaceHolder" />">	
			</span>	
		</div>
	</div>
  <fieldset class='site-notes'><legend><spring:message code="atlas.notesLabel" /></legend>
  	<textarea rows="3" cols="40"
				id="atlas-edit-tbNotes" style="resize: none;"
				placeholder="<spring:message code="atlas.notesPlaceHolder"/>"></textarea>
  </fieldset>
  <div class="atlas-edit-div">			
			<span id="atlas-edit-lblImplementationTypeLabel"
						class="spanView"><spring:message
								code="atlas.implementationTypeLabel" /></span> <input type="text"
						id="atlas-edit-tbType" style="width: 100px" readonly="readonly">
						<a id="atlas-edit-changeTypeLink" href=""><spring:message
								code="atlas.changeTypeLink" /></a>
  </div>
  <div>
			<span style="float: right"> <a
				id="atlas-edit-saveLink" href=""><spring:message
						code="atlas.save" /> </a> | <a id="atlas-edit-cancelLink" href=""><spring:message
						code="atlas.cancel" /> </a> </span>
   </div>
  <div>
  	<br /><br /><br />
  </div>			
  </div>




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
	<input type="text" value="" id="atlas-map-searchbox"
		style="width: 800px; height: 30px; font-size: 15px;"
		placeholder="<spring:message code="atlas.addressPlaceHolder" />">
	<div id="atlas-map-canvas"></div>
</div>

<div id="changeTypeDialog">
	<form>
		<b><span id="selectTypeText"><spring:message
					code="atlas.selectType" /> </span>:</b> <br> <br>
		<c:forEach items="${atlasData.implementationTypes}" var="impType"
			varStatus="loopStatus">
			<input type="radio" id="rbType${loopStatus.index}" class="rbTypes"
				name="rbTypes"
				value="<spring:message code="atlas.implementationType${impType}"/>">
			<spring:message code="atlas.implementationType${impType}" />
			<br>
		</c:forEach>
		<br> <br> <input id="btnTypeSave" type="submit"
			value="<spring:message code="atlas.save" />" /> <input
			id="btnTypeCancel" type="submit"
			value="<spring:message code="atlas.cancel" />" />
	</form>
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

</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
