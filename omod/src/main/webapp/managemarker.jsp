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
		, jQuery("#atlasIncludeNumberOfVisits").val());
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
                              <span id="lblNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.namePlaceHolder" /></span>
                            </td>
                        </tr>
                         <tr>
                            <td>
                              <span id="lblImplementationTypeLabel" class="spanView"><spring:message code="atlas.implementationTypeLabel" /></span>
                              <span id="lblImplementationType" class="spanView"></span>
                            
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<a id="aWebsite" target='_blank' href="${atlasData.website}" class="spanView"><span id="lblWebsite"><c:out value="${atlasData.website}"/></span></a>
                            	<span id="lblWebsitePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.websitePlaceHolder" /></span>
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
                            <span class="stats" id="lblVisits" 
                               <c:if test="${atlasData.includeNumberOfVisits == false}">
										style="display: none"
							   </c:if>
                            >
                            	<span id="lblVisitsNr"><c:out value="${atlasData.numberOfVisits}"/> </span> <spring:message code="atlas.visits" /><br/>
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
                	<span id="lblNotesPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.notesPlaceHolder" /></span>
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<span class="contact spanView" id="lblContactName"><c:out value="${atlasData.contactName}"/></span>
                	<span id="lblContactNamePlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.contactNamePlaceHolder" /></span>
                </td>
            </tr> 
            <tr>
                <td colspan="2">
                	<img alt="mail" src="/openmrs/moduleResources/atlas/envelope.jpeg" height="20px" width="30px">
                	<a href="" id="aEmail">
                	 <span class="contact spanViewParent" id="lblEmail"><c:out value="${atlasData.contactEmailAddress}"/></span>
                	</a>
                	<span id="lblEmailPlaceHolder" class="labelPlaceHolder"><spring:message code="atlas.emailPlaceHolder" /></span>
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
                             
                             <div id="btnNextType" class="paginate_enabled_next" title="Next">
                             </div>
                             <div id="btnPreviousType" class="paginate_enabled_previous" title="Previous">
                             </div>
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
                            	<input type="checkbox" id="cbVisits" 
                            		<c:if test="${atlasData.includeNumberOfVisits == true}">
							    		checked="checked"
							    	</c:if>
							    >
                            	<label for="cbVisits">
                            		<spring:message code="atlas.includeNrOfVisits"/>
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
                	<textarea rows="3" cols="40" id="tbNotes" style="resize: none;" placeholder="<spring:message code="atlas.notesPlaceHolder"/>">
                	</textarea>
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
  		<input type="hidden" id="atlasImageURL" name="atlasImageURL" value="${atlasData.imageURL}"/> 
  		<input type="hidden" id="atlasLatitude" name="atlasLatitude" value="${atlasData.latitude}"/>
  		<input type="hidden" id="atlasLongitude" name="atlasLongitude" value="${atlasData.longitude}"/> 
  		<input type="hidden" id="atlasName" name="atlasName" value="${atlasData.name}"/>
  		<input type="hidden" id="atlasWebsite" name="atlasWebsite" value="${atlasData.website}"/> 
  		<input type="hidden" id="atlasContactName" name="atlasContactName" value="${atlasData.contactName}"/> 
  		<input type="hidden" id="atlasContactEmailAddress" name="atlasContactEmailAddress" value="${atlasData.contactEmailAddress}"/> 
  		<input type="hidden" id="atlasIncludeNumberOfPatients" name="atlasIncludeNumberOfPatients" value="${atlasData.includeNumberOfPatients}"/> 
  		<input type="hidden" id="atlasIncludeNumberOfObservations" name="atlasIncludeNumberOfObservations" value="${atlasData.includeNumberOfObservations}"/> 
  		<input type="hidden" id="atlasIncludeNumberOfVisits" name="atlasIncludeNumberOfVisits" value="${atlasData.includeNumberOfVisits}"/>
  	
  	    <input type="hidden" id="implementationTypeOrdinal" value="${atlasData.implementationType}" >
  	    
  	    <% String prefix = "implementationType"; Integer cnt = 0;
  	       for (ImplementationType type: ImplementationType.values())  { 
  	       String str = prefix+ cnt.toString();
  	       %>
  	    	 <input type="hidden" id="<%= str %>" value="<%= type.toString() %>" >
  	    <% cnt++; } %>
  	     <input type="hidden" id="implementationTypeLength" value="<%= cnt %>" >
  	    	
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
  

<%@ include file="/WEB-INF/template/footer.jsp"%>
