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
                              <span id="lblName"><c:out value="${atlasData.name}"/></span>
                            </td>
                        </tr>
                        <tr>
                            <td><span id="lblWebsite"> <c:out value="${atlasData.website}"/></span>
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
                <td colspan="2"><span class="contact" id="lblEmail"> Email</span> </td>
            </tr> 
            <tr>
                <td colspan="2"><span class="contact" id="lblPhone"> Phone </span> </td>
            </tr> 
			<tr>
				<td colspan="2"><a id="editLink" href="">Edit</a></td>
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
                             <input type="text" id="tbName" placeholder="Name...">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" id="tbWebsite" placeholder="Website...">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" id="cbPatients">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" id="cbObservations">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" id="cbVisits">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="text" id="tbEmail" placeholder="Email.."></td>
            </tr> 
            <tr>
                <td colspan="2"><input type="text" id="tbPhone" placeholder="Phone.."></td>
            </tr> 
			<tr>
				<td colspan="2"><a id="saveLink" href="">Save</a> | <a id="cancelLink" href="">Cancel</a></td>
			</tr>
           
        </table>	
	
  <div id="leftColumnDiv"> 
  	<form method="post">
		<input type="submit" value="<spring:message code="atlas.submit" />" />
  		<input type="hidden" name="atlasDataID" value="${atlasData.id}"/>      
	</form>
  </div>	
  <div id="mapCanvas"></div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
