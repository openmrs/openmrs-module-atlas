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


<h2><spring:message code="atlas.manageMarkerLink" /></h2>


<br/>

<table id="atlas-view-containerDiv" style="display:hidden" >
            <tr>
                <td>
                    <img id ="atlas-view-imgImplementation" src="${atlasData.imageURL}" width="80px" height="80px" alt ="thumbnail" />
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                              <span id="atlas-view-lblName" class="spanView"><c:out value="${atlasData.name}"/></span>
<!--                               <span id="atlas-view-lblNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.namePlaceHolder" /></span> -->
                            </td>
                        </tr>
                         <tr>
                            <td>
                            <span>
                              <span id="atlas-view-lblImplementationTypeLabel" class="spanView"><spring:message code="atlas.implementationTypeLabel" /></span>
                              <span id="atlas-view-lblImplementationType" class="spanView"></span>
                            </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<a id="atlas-view-aWebsite" target='_blank' href="${atlasData.website}" class="spanView"><span id="atlas-view-lblWebsite"><c:out value="${atlasData.website}"/></span></a>
<!--                             	<span id="atlas-view-lblWebsitePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.websitePlaceHolder" /></span> -->
                            </td>
                        </tr>
                        <tr>
                            <td>
                            <span class="stats" id="atlas-view-lblPatients" 
                            	<c:if test="${atlasData.includeNumberOfPatients == false}">
										style="display: none"
							    </c:if>
                            >
                            	<span id="atlas-view-lblPatientsNr"><c:out value="${atlasData.numberOfPatients}"/> </span> <spring:message code="atlas.patients" /><br/>
                            </span> 
                            <span class="stats" id="atlas-view-lblEncounters" 
                               <c:if test="${atlasData.includeNumberOfEncounters == false}">
										style="display: none"
							   </c:if>
                            >
                            	<span id="atlas-view-lblEncountersNr"><c:out value="${atlasData.numberOfEncounters}"/> </span> <spring:message code="atlas.encounters" /><br/>
                            </span> 
                            <span class="stats" id="atlas-view-lblObservations"
                               <c:if test="${atlasData.includeNumberOfObservations == false}">
										style="display: none"
							   </c:if>
                            >
                            	 <span id="atlas-view-lblObservationsNr"><c:out value="${atlasData.numberOfObservations}"/> </span> <spring:message code="atlas.observations" />
                            </span> 
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
              <tr>
                <td colspan="2">
                	<span class="spanView" id="atlas-view-lblNotes"><c:out value="${atlasData.notes}"/></span>
<!--                 	<span id="atlas-view-lblNotesPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.notesPlaceHolder" /></span> -->
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<span class="contact spanView" id="atlas-view-lblContactName"><c:out value="${atlasData.contactName}"/></span>
<!--                 	<span id="atlas-view-lblContactNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.contactNamePlaceHolder" /></span> -->
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<img alt="mail" id="atlas-view-imgEmail" src="../../moduleResources/atlas/envelope.jpeg" height="20px" width="30px">
                	<a href="" id="atlas-view-aEmail">
                	 <span class="contact spanViewParent" id="atlas-view-lblEmail"><c:out value="${atlasData.contactEmailAddress}"/></span>
                	</a>
<!--                 	<span id="atlas-view-lblEmailPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.emailPlaceHolder" /></span> -->
                </td>
            </tr> 
			<tr>
				<td colspan="2">
					<span style="float: right">
						<a id="atlas-view-editLink" href=""><spring:message code="atlas.edit"/></a>
					</span>
				</td>
			</tr>
           	<tr>
                <td colspan="2">
					<br /><br />
                </td>
            </tr> 
        </table>	
		
<table id="atlas-edit-containerDiv" style="display:hidden" >
            <tr>
                <td>
                    <div id="atlas-edit-imgDiv">
						<input type="text" id="atlas-edit-tbImage" style="width:90px;" placeholder="<spring:message code="atlas.imagePlaceHolder" />">
					</div>
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                             <input type="text" id="atlas-edit-tbName" placeholder="<spring:message code="atlas.namePlaceHolder" />">
                             <span id="atlas-edit-nameError" class="fail" style="display: none;">*</span> 
                            </td>
                        </tr>
                         <tr>
                            <td>
                             <span id="atlas-edit-lblImplementationTypeLabel" class="spanView"><spring:message code="atlas.implementationTypeLabel" /></span>
                             <input type="text" id="atlas-edit-tbType" style="width: 100px" readonly="readonly">
                             <a id="atlas-edit-changeTypeLink" href=""><spring:message code="atlas.changeTypeLink"/></a> 
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" id="atlas-edit-tbWebsite" placeholder="<spring:message code="atlas.websitePlaceHolder" />">
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="atlas-edit-cbPatients" 
                            		<c:if test="${atlasData.includeNumberOfPatients == true}">
										checked="checked"
									</c:if>
                            	>
                            	<label for="atlas-edit-cbPatients">
                            		<spring:message code="atlas.includeNrOfPatients"/>
                            	</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<input type="checkbox" id="atlas-edit-cbEncounters" 
                            		<c:if test="${atlasData.includeNumberOfEncounters == true}">
							    		checked="checked"
							    	</c:if>
							    >
                            	<label for="atlas-edit-cbEncounters">
                            		<spring:message code="atlas.includeNrOfEncounters"/>
                            	</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                          		<input type="checkbox" id="atlas-edit-cbObservations" 
                          			<c:if test="${atlasData.includeNumberOfObservations == true}">
							    		checked="checked"
							    	</c:if>
                          		>
                            	<label for="atlas-edit-cbObservations">
                            		<spring:message code="atlas.includeNrOfObservations"/>
                            	</label>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                	<textarea rows="3" cols="40" id="atlas-edit-tbNotes" style="resize: none;" placeholder="<spring:message code="atlas.notesPlaceHolder"/>"></textarea>
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<input type="text" id="atlas-edit-tbContactName" placeholder="<spring:message code="atlas.contactNamePlaceHolder" />">
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<input type="text" id="atlas-edit-tbEmail" placeholder="<spring:message code="atlas.emailPlaceHolder" />">
                </td>
            </tr> 
			<tr>
				<td colspan="2">
					<span style="float: right">
						<a id="atlas-edit-saveLink" href=""><spring:message code="atlas.save"/></a> | 
						<a id="atlas-edit-cancelLink" href=""><spring:message code="atlas.cancel"/></a>
					</span>
				</td>
			</tr>
			<tr>
                <td colspan="2">
                	<br />
                </td>
            </tr> 
           
        </table>	
	
  <div id="atlas-gutter-leftColumnDiv"> 
  	<form method="post">
  		<input type="hidden" id="atlas-hidden-latitude" name="atlas-hidden-latitude" value="${atlasData.latitude}"/>
  		<input type="hidden" id="atlas-hidden-longitude" name="atlas-hidden-longitude" value="${atlasData.longitude}"/> 
  		<input type="hidden" id="atlas-hidden-zoom" name="atlas-hidden-zoom" value="${atlasData.zoom}"/> 
  	
  	    <input type="hidden" id="atlas-hidden-implementationTypeOrdinal" value="${atlasData.implementationType}" >
  	    
  		<b class="boxHeader"><spring:message code="atlas.enableDisableHeader"/></b>
		<div class="box">
        <table>
        	<tr>
        		<td>
        
        <input type="checkbox" id="atlas-gutter-cbDisclaimer"
                            		<c:if test="${atlasData.usageDisclamerAccepted == true}">
										checked="checked"
									</c:if>
                            	>
                                </td>
                                <td>
                            	<label for="atlas-gutter-cbDisclaimer" style="font-size: 1.2em">
                            		<spring:message code="atlas.userAgreement"/>
                            	</label>
                            	<br/>
                            	<span style="font-size: 0.9em">
                            		<spring:message code="atlas.userAgreementParanthesis"/>
                            	</span>
                            			</td>
        	</tr>
        <tr><td colspan="2">
        <div class="atlas-gutter-divCenter">
  		<input id="atlas-gutter-btnEnable" type="submit" class="atlas-gutter-btnEnabled" onclick=""
  					<c:if test="${atlasData.moduleEnabled == false}">
										style="display: none"
					</c:if>
  		 value="<spring:message code="atlas.buttonEnabled" />" 
  		 /> 
  		<input id="atlas-gutter-btnDisable" type="submit" class="atlas-gutter-btnDisabled" onclick=""
  					<c:if test="${atlasData.moduleEnabled == true}">
										style="display: none"
					</c:if>
  		value="<spring:message code="atlas.buttonDisabled" />"
  		 /> 
  		 </div>
  		 
  		</td></tr>
  			<tr>
  				<td>
  		<input type="checkbox" id="atlas-gutter-cbIncludeModules"
                            		<c:if test="${atlasData.includeModules == true}">
										checked="checked"
									</c:if>
                            	>
                            	</td>
                            	<td>
                            	<label for="atlas-gutter-cbIncludeModules" style="font-size: 1.2em">
                            		<spring:message code="atlas.addModulesLabel"/>
                            	</label>
		<br>
		<a id="atlas-gutter-includeModulesTip" href="" title="<spring:message code="atlas.addModulesTip" />"><spring:message code="atlas.addModulesLink" /></a>
				</td>
  			</tr>
  		</table>       
        </div>
	</form>
  </div>
  <div id="atlas-map-rightColumnDiv">
  	 <input type="text" value="" id="atlas-map-searchbox" style=" width:800px;height:30px; font-size:15px;" 
  	  placeholder="<spring:message code="atlas.addressPlaceHolder" />" >
  	 <div id="atlas-map-canvas"></div>
  </div>	
  
  <div id="changeTypeDialog">
  	<form>
  	<b><span id="selectTypeText"><spring:message code="atlas.selectType"/></span>:</b>
  	<br><br>
  	<c:forEach items="${atlasData.implementationTypes}" var="impType" varStatus="loopStatus">
               <input type="radio" id="rbType${loopStatus.index}" class="rbTypes" name="rbTypes" value="<spring:message code="atlas.implementationType${impType}"/>" >
               <spring:message code="atlas.implementationType${impType}"/>
               <br>
    </c:forEach>  
    <br><br>
    <input id="btnTypeSave" type="submit" value="<spring:message code="atlas.save" />"  /> 
    <input id="btnTypeCancel" type="submit" value="<spring:message code="atlas.cancel" />"  /> 
    </form>  
  </div>
  
  <div id="atlas-gutter-sentInfo">
  	<span>
  		<span id="atlas-gutter-sentInfoTitle" style="display: none"><spring:message code="atlas.whatWillBeSentInfoTitle"/></span>
  		<spring:message code="atlas.whatWillBeSentInfo" />
  		<br><br>
  		<textarea id="atlas-gutter-jsonData" cols="50" rows="10" readonly="readonly"></textarea>
  	</span>
  </div>
  
  </div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
