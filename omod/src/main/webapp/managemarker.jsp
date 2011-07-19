<%@page import="org.openmrs.module.atlas.ImplementationType"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:htmlInclude file="/scripts/jQuery/dataTables/dataTables.css" />
<openmrs:htmlInclude file="/moduleResources/atlas/atlas.css" />
<openmrs:htmlInclude file="/moduleResources/atlas/atlas.js" />
<openmrs:htmlInclude file="/moduleResources/atlas/yqlgeo.js" />
<openmrs:htmlInclude file="/moduleResources/atlas/yqlgeo.js" />

<meta name="viewport" content="initial-scale=1.0, user-scalable=yes" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"> </script>
<script src="<openmrs:contextPath/>/dwr/interface/DWRAtlasService.js"></script>
<script>
 
function x() {
	CopyValuesFromViewToHiddenFields();
    DWRAtlasService.saveAtlasData(jQuery("#atlasID").val(), jQuery("#atlasLatitude").val(), jQuery("#atlasLongitude").val()
		, jQuery("#atlasName").val(), jQuery("#atlasWebsite").val(), jQuery("#atlasImageURL").val()
		, jQuery("#atlasContactName").val(), jQuery("#atlasContactEmailAddress").val()
		, jQuery("#atlasIncludeNumberOfObservations").val()
		, jQuery("#atlasIncludeNumberOfPatients").val()
		, jQuery("#atlasIncludeNumberOfEncounters").val());
}
 
</script>

<h2><spring:message code="atlas.manageMarkerLink" /></h2>


<br/>

<table id="containerDiv" style="display:hidden" >
            <tr>
                <td>
                    <img id ="imgImplementation" src="${atlasData.imageURL}" width="80px" height="80px" alt ="thumbnail" />
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                              <span id="lblName" class="spanView"><c:out value="${atlasData.name}"/></span>
<!--                               <span id="lblNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.namePlaceHolder" /></span> -->
                            </td>
                        </tr>
                         <tr>
                            <td>
                            <span>
                              <span id="lblImplementationTypeLabel" class="spanView"><spring:message code="atlas.implementationTypeLabel" /></span>
                              <span id="lblImplementationType" class="spanView"></span>
                            </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<a id="aWebsite" target='_blank' href="${atlasData.website}" class="spanView"><span id="lblWebsite"><c:out value="${atlasData.website}"/></span></a>
<!--                             	<span id="lblWebsitePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.websitePlaceHolder" /></span> -->
                            </td>
                        </tr>
                        <tr>
                            <td>
                            <span class="stats" id="lblPatients" 
                            	<c:if test="${atlasData.includeNumberOfPatients == false}">
										style="display: none"
							    </c:if>
                            >
                            	<span id="lblPatientsNr"><c:out value="${atlasData.numberOfPatients}"/> </span> <spring:message code="atlas.patients" /><br/>
                            </span> 
                            <span class="stats" id="lblEncounters" 
                               <c:if test="${atlasData.includeNumberOfEncounters == false}">
										style="display: none"
							   </c:if>
                            >
                            	<span id="lblEncountersNr"><c:out value="${atlasData.numberOfEncounters}"/> </span> <spring:message code="atlas.encounters" /><br/>
                            </span> 
                            <span class="stats" id="lblObservations"
                               <c:if test="${atlasData.includeNumberOfObservations == false}">
										style="display: none"
							   </c:if>
                            >
                            	 <span id="lblObservationsNr"><c:out value="${atlasData.numberOfObservations}"/> </span> <spring:message code="atlas.observations" />
                            </span> 
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
              <tr>
                <td colspan="2">
                	<span class="spanView" id="lblNotes"><c:out value="${atlasData.notes}"/></span>
<!--                 	<span id="lblNotesPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.notesPlaceHolder" /></span> -->
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<span class="contact spanView" id="lblContactName"><c:out value="${atlasData.contactName}"/></span>
<!--                 	<span id="lblContactNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.contactNamePlaceHolder" /></span> -->
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<img alt="mail" id="imgEmail" src="/openmrs/moduleResources/atlas/envelope.jpeg" height="20px" width="30px">
                	<a href="" id="aEmail">
                	 <span class="contact spanViewParent" id="lblEmail"><c:out value="${atlasData.contactEmailAddress}"/></span>
                	</a>
<!--                 	<span id="lblEmailPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.emailPlaceHolder" /></span> -->
                </td>
            </tr> 
			<tr>
				<td colspan="2">
					<span style="float: right">
						<a id="editLink" href=""><spring:message code="atlas.edit"/></a>
					</span>
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
						<input type="text" id="tbImage" style="width:90px;" placeholder="<spring:message code="atlas.imagePlaceHolder" />">
					</div>
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                             <input type="text" id="tbName" placeholder="<spring:message code="atlas.namePlaceHolder" />">
                             <span id="nameError" class="fail" style="display: none;">*</span> 
                            </td>
                        </tr>
                         <tr>
                            <td>
                             <span id="lblImplementationTypeLabel" class="spanView"><spring:message code="atlas.implementationTypeLabel" /></span>
                             <input type="text" id="tbType" style="width: 137px" readonly="readonly">
                             <a id="changeTypeLink" href=""><spring:message code="atlas.changeTypeLink"/></a> 
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" id="tbWebsite" placeholder="<spring:message code="atlas.websitePlaceHolder" />">
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="cbPatients" 
                            		<c:if test="${atlasData.includeNumberOfPatients == true}">
										checked="checked"
									</c:if>
                            	>
                            	<label for="cbPatients">
                            		<spring:message code="atlas.includeNrOfPatients"/>
                            	</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="cbEncounters" 
                            		<c:if test="${atlasData.includeNumberOfEncounters == true}">
							    		checked="checked"
							    	</c:if>
							    >
                            	<label for="cbEncounters">
                            		<spring:message code="atlas.includeNrOfEncounters"/>
                            	</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                          		<input type="checkbox" id="cbObservations" 
                          			<c:if test="${atlasData.includeNumberOfObservations == true}">
							    		checked="checked"
							    	</c:if>
                          		>
                            	<label for="cbObservations">
                            		<spring:message code="atlas.includeNrOfObservations"/>
                            	</label>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                	<textarea rows="3" cols="40" id="tbNotes" style="resize: none;" placeholder="<spring:message code="atlas.notesPlaceHolder"/>"></textarea>
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<input type="text" id="tbContactName" placeholder="<spring:message code="atlas.contactNamePlaceHolder" />">
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<input type="text" id="tbEmail" placeholder="<spring:message code="atlas.emailPlaceHolder" />">
                </td>
            </tr> 
			<tr>
				<td colspan="2">
					<span style="float: right">
						<a id="saveLink" href=""><spring:message code="atlas.save"/></a> | 
						<a id="cancelLink" href=""><spring:message code="atlas.cancel"/></a>
					</span>
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
		
  		<input type="hidden" id="atlasID" name="atlasID" value="${atlasData.id}"/> 
  		<input type="hidden" id="atlasLatitude" name="atlasLatitude" value="${atlasData.latitude}"/>
  		<input type="hidden" id="atlasLongitude" name="atlasLongitude" value="${atlasData.longitude}"/> 
  		<input type="hidden" id="atlasZoom" name="atlasZoom" value="${atlasData.zoom}"/> 
  	
  	    <input type="hidden" id="implementationTypeOrdinal" value="${atlasData.implementationType}" >
  	    
  	     <input type="hidden" id="implementationTypeLength" value="${atlasData.implementationTypesLength}" >
              	
  		<b class="boxHeader"><spring:message code="atlas.enableDisableHeader"/></b>
		<div class="box">
        <table>
        	<tr>
        		<td>
        
        <input type="checkbox" id="cbDisclaimer"
                            		<c:if test="${atlasData.usageDisclamerAccepted == true}">
										checked="checked"
									</c:if>
                            	>
                                </td>
                                <td>
                            	<label for="cbDisclaimer" style="font-size: 1.2em">
                            		<spring:message code="atlas.userAgreement"/>
                            	</label>
                            	<br/>
                            	<span style="font-size: 0.9em">
                            		<spring:message code="atlas.userAgreementParanthesis"/>
                            	</span>
                            			</td>
        	</tr>
        <tr><td colspan="2">
        <div class="divCenter">
  		<input id="btnEnable" type="submit" class="btnEnabled" onclick=""
  					<c:if test="${atlasData.moduleEnabled == false}">
										style="display: none"
					</c:if>
  		 value="<spring:message code="atlas.buttonEnabled" />" 
  		 /> 
  		<input id="btnDisable" type="submit" class="btnDisabled" onclick=""
  					<c:if test="${atlasData.moduleEnabled == true}">
										style="display: none"
					</c:if>
  		value="<spring:message code="atlas.buttonDisabled" />"
  		 /> 
  		 </div>
  		 
  		</td></tr>
  		</table> 
  		</div>    
  		 
		<br/><br/>
  		<b class="boxHeader"><spring:message code="atlas.helpOpenMRS"/></b>
  		<div class="box">
  		<table>
  			<tr>
  				<td>
  		<input type="checkbox" id="cbIncludeModules"
                            		<c:if test="${atlasData.includeModules == true}">
										checked="checked"
									</c:if>
                            	>
                            	</td>
                            	<td>
                            	<label for="cbIncludeModules" style="font-size: 1.2em">
                            		<spring:message code="atlas.addModulesLabel"/>
                            	</label>
		<a id="includeModulesTip" href="" title="<spring:message code="atlas.addModulesTip" />"><spring:message code="atlas.addModulesLink" /></a>
				</td>
  			</tr>
  		</table>       
        </div>
	</form>
  </div>
  <div id="rightColumnDiv">
  	 <input type="text" value="" id="searchbox" style=" width:800px;height:30px; font-size:15px;" 
  	  placeholder="<spring:message code="atlas.addressPlaceHolder" />" >
  	 <div id="mapCanvas"></div>
  </div>	
  
  <div id="changeTypeDialog">
  	<form>
  	<c:forEach items="${atlasData.implementationTypes}" var="impType" varStatus="loopStatus">
               <input type="radio" id="rbType${loopStatus.index}" class="rbTypes" name="rbTypes" value="<spring:message code="atlas.implementationType${impType}"/>" >
               <spring:message code="atlas.implementationType${impType}"/>
               <br>
    </c:forEach>  
    <input id="btnTypeSave" type="submit" value="<spring:message code="atlas.save" />"  /> 
    <input id="btnTypeCancel" type="submit" value="<spring:message code="atlas.cancel" />"  /> 
    
    </form>  
  </div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
