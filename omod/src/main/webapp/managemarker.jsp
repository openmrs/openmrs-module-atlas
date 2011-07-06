<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:htmlInclude file="/moduleResources/atlas/atlas.css" />
<openmrs:htmlInclude file="/moduleResources/atlas/atlas.js" />
<openmrs:htmlInclude file="/moduleResources/atlas/yqlgeo.js" />

<meta name="viewport" content="initial-scale=1.0, user-scalable=yes" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"> </script>
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
                              <span id="lblName" class="spanView"><c:out value="${atlasData.name}"/></span>
                              <span id="lblNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.namePlaceHolder" /></span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<span id="lblWebsite" class="spanView"><c:out value="${atlasData.website}"/></span>
                            	<span id="lblWebsitePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.websitePlaceHolder" /></span>
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
                <td colspan="2">
                	<span class="contact spanView" id="lblEmail"><c:out value="${atlasData.contactPhoneNumber}"/></span>
                	<span id="lblEmailPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.emailPlaceHolder" /></span>
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<span class="contact spanView" id="lblPhone"><c:out value="${atlasData.contactEmailAddress}"/></span>
                	<span id="lblPhonePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.phonePlaceHolder" /></span>
                </td>
            </tr> 
			<tr>
				<td colspan="2">
						<a id="editLink" href=""><spring:message code="atlas.edit"/></a>
				</td>
			</tr>
           	<tr>
                <td colspan="2">
					<br /><br />
                </td>
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
                             <input type="text" id="tbName" placeholder="<spring:message code="atlas.namePlaceHolder" />">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" id="tbWebsite" placeholder="<spring:message code="atlas.websitePlaceHolder" />">
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="cbPatients">
                            	<label for="cbPatients">
                            		<spring:message code="atlas.includeNrOfPatients"/>
                            	</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="cbObservations">
                            	<label for="cbObservations">
                            		<spring:message code="atlas.includeNrOfObservations"/>
                            	</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="cbVisits">
                            	<label for="cbVisits">
                            		<spring:message code="atlas.includeNrOfVisits"/>
                            	</label>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                	<input type="text" id="tbEmail" placeholder="<spring:message code="atlas.emailPlaceHolder" />">
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<input type="text" id="tbPhone" placeholder="<spring:message code="atlas.phonePlaceHolder" />">
                </td>
            </tr> 
			<tr>
				<td colspan="2">
					<a id="saveLink" href=""><spring:message code="atlas.save"/></a> | 
					<a id="cancelLink" href=""><spring:message code="atlas.cancel"/></a>
				</td>
			</tr>
			<tr>
                <td colspan="2">
                	<br />
                </td>
            </tr> 
           
        </table>	
	
  <div id="leftColumnDiv"> 
  	<form method="post">
		<input id="btnSubmit" type="submit" value="<spring:message code="atlas.submit" />" />
  		<input type="hidden" id="atlasID" name="atlasID" value="${atlasData.id}"/> 
  		<input type="hidden" id="atlasLatitude" name="atlasLatitude" value="${atlasData.latitude}"/>
  		<input type="hidden" id="atlasLongitude" name="atlasLongitude" value="${atlasData.longitude}"/> 
  		<input type="hidden" id="atlasName" name="atlasName" value="${atlasData.name}"/>
  		<input type="hidden" id="atlasWebsite" name="atlasWebsite" value="${atlasData.website}"/> 
  		<input type="hidden" id="atlasContactPhoneNumber" name="atlasContactPhoneNumber" value="${atlasData.contactPhoneNumber}"/> 
  		<input type="hidden" id="atlasContactEmailAddress" name="atlasDontactEmailAddress" value="${atlasData.contactEmailAddress}"/> 
  		<input type="hidden" id="atlasIncludeNumberOfPatients" name="atlasIncludeNumberOfPatients" value="${atlasData.includeNumberOfPatients}"/> 
  		<input type="hidden" id="atlasIncludeNumberOfObservations" name="atlasIncludeNumberOfObservations" value="${atlasData.includeNumberOfObservations}"/> 
  		<input type="hidden" id="atlasIncludeNumberOfVisits" name="atlasIncludeNumberOfVisits" value="${atlasData.includeNumberOfVisits}"/> 
	</form>
  </div>
  <div id="rightColumnDiv">
  	 <input type="text" value="" id="searchbox" style=" width:800px;height:30px; font-size:15px;" 
  	  placeholder="<spring:message code="atlas.addressPlaceHolder" />" >
  	 <div id="mapCanvas"></div>
  </div>	
  

<%@ include file="/WEB-INF/template/footer.jsp"%>
