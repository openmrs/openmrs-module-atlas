package org.openmrs.module.atlas;


import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import org.junit.Before;
import org.junit.After;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class AtlasServiceTest extends BaseModuleContextSensitiveTest {
	
	String id = "73b13680-c4a0-11e0-962b-0800200c9a66";
	AtlasService atlasSrv;
	
	//@Before
	public void runBeforeEachTest() throws Exception {
		atlasSrv = Context.getService(AtlasService.class);
		AtlasData data = getTestAtlasData();
		saveTestAtlasDataToGlobalProperties(data);
	}
	
	//@After
	public void runAfter() throws Exception {
		AtlasData data = getTestAtlasData();
		deleteTestAtlasDataFromGlobalProperties(data);
	}
	
	public AtlasData getTestAtlasData() {
		System.out.println("get");
		AtlasData data = new AtlasData();
		data.setName("Module_BDT");
		data.setId(UUID.fromString(id));
		
		return data;
	}
	
	public void saveTestAtlasDataToGlobalProperties(AtlasData data) {
		AdministrationService svc = Context.getAdministrationService();
		
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME, data.getName()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMPLEMENTATION_TYPE, data
		        .getImplementationType().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE, data.getWebsite()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMAGE_URL, data.getImageURL()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS, data
		        .getContactEmailAddress()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_NAME, data.getContactName()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NOTES, data.getNotes()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE, data.getLatitude().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude()
		        .toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS, data
		        .getIncludeNumberOfObservations().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS, data
		        .getIncludeNumberOfPatients().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_ENCOUNTERS, data
		        .getIncludeNumberOfEncounters().toString()));
		
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED, data
		        .getUsageDisclamerAccepted().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_SYSTEM_CONFIGURATION, data
		        .getIncludeSystemConfiguration().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED, data.getModuleEnabled()
		        .toString()));
	}
	
	public void deleteTestAtlasDataFromGlobalProperties(AtlasData data) {
		AdministrationService svc = Context.getAdministrationService();
		
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME, data.getName()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMPLEMENTATION_TYPE, data
		        .getImplementationType().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE, data.getWebsite()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMAGE_URL, data.getImageURL()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS, data
		        .getContactEmailAddress()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_NAME, data.getContactName()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NOTES, data.getNotes()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE, data.getLatitude().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude()
		        .toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS, data
		        .getIncludeNumberOfObservations().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS, data
		        .getIncludeNumberOfPatients().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_ENCOUNTERS, data
		        .getIncludeNumberOfEncounters().toString()));
		
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED, data
		        .getUsageDisclamerAccepted().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_SYSTEM_CONFIGURATION, data
		        .getIncludeSystemConfiguration().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED, data.getModuleEnabled()
		        .toString()));
	}
	/**
	 * @see AtlasService#disableAtlasModule(Boolean)
	 * @verifies unregister the PostAtlasDataQueueTask
	 */
	@Test
	public void disableAtlasModule_shouldUnregisterThePostAtlasDataQueueTask() throws Exception {
		//TODO auto-generated
		//fail("Not yet implemented");
	}
//	
//	/**
//	 * @see AtlasService#disableAtlasModule(Boolean)
//	 * @verifies set the atlas.usageDisclaimerAccepted to the usageDisclaimerAccepted parameter value
//	 */
//	@Test
//	public void disableAtlasModule_shouldSetTheAtlasusageDisclaimerAcceptedToTheUsageDisclaimerAcceptedParameterValue()
//	                                                                                                                   throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#disableAtlasModule(Boolean)
//	 * @verifies send a delete message to the server
//	 */
//	@Test
//	public void disableAtlasModule_shouldSendADeleteMessageToTheServer() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#enableAtlasModule()
//	 * @verifies register a PostAtlasDataQueueTask
//	 */
//	@Test
//	public void enableAtlasModule_shouldRegisterAPostAtlasDataQueueTask() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#enableAtlasModule()
//	 * @verifies set the atlas.usageDisclaimerAccepted to true
//	 */
//	@Test
//	public void enableAtlasModule_shouldSetTheAtlasusageDisclaimerAcceptedToTrue() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#enableAtlasModule()
//	 * @verifies send the atlas data to the server through an Http Post
//	 */
//	@Test
//	public void enableAtlasModule_shouldSendTheAtlasDataToTheServerThroughAnHttpPost() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#getAtlasData()
//	 * @verifies initialize with default values (see constructor in AtlasData.java) all AtlasData fields, except id, that do not have corresponding GlobalProperties
//	 */
//	@Test
//	public void getAtlasData_shouldInitializeWithDefaultValuesSeeConstructorInAtlasDatajavaAllAtlasDataFieldsExceptIdThatDoNotHaveCorrespondingGlobalProperties()
//	                                                                                                                                                             throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#getAtlasData()
//	 * @verifies throw java.lang.IllegalArgumentException when atlas.id GlobalPproperty does not exist
//	 */
//	@Test
//	public void getAtlasData_shouldThrowJavalangIllegalArgumentExceptionWhenAtlasidGlobalPpropertyDoesNotExist()
//	                                                                                                            throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#getAtlasData()
//	 * @verifies throw java.lang.IllegalArgumentException when atlas.id GlobalPproperty is not a valid UUID
//	 */
//	@Test
//	public void getAtlasData_shouldThrowJavalangIllegalArgumentExceptionWhenAtlasidGlobalPpropertyIsNotAValidUUID()
//	                                                                                                               throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#postAtlasData()
//	 * @verifies set atlas.isDirty GlobalProperty to false
//	 */
//	@Test
//	public void postAtlasData_shouldSetAtlasisDirtyGlobalPropertyToFalse() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#postAtlasData()
//	 * @verifies update the atlas.numberOfPatients GlobalProperty with the number of non-voided patients
//	 */
//	@Test
//	public void postAtlasData_shouldUpdateTheAtlasnumberOfPatientsGlobalPropertyWithTheNumberOfNonvoidedPatients()
//	                                                                                                              throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#postAtlasData()
//	 * @verifies update the atlas.numberOfEncounters GlobalProperty with the number of non-voided encounters
//	 */
//	@Test
//	public void postAtlasData_shouldUpdateTheAtlasnumberOfEncountersGlobalPropertyWithTheNumberOfNonvoidedEncounters()
//	                                                                                                                  throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#postAtlasData()
//	 * @verifies update the atlas.numberOfObservations GlobalProperty with the number of non-voided observations
//	 */
//	@Test
//	public void postAtlasData_shouldUpdateTheAtlasnumberOfObservationsGlobalPropertyWithTheNumberOfNonvoidedObservations()
//	                                                                                                                      throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#setAtlasBubbleData(AtlasData)
//	 * @verifies only set the AtlasData GlobalProperties that are related to the Atlas Bubble (see AtlasData.java)
//	 */
//	@Test
//	public void setAtlasBubbleData_shouldOnlySetTheAtlasDataGlobalPropertiesThatAreRelatedToTheAtlasBubbleSeeAtlasDatajava()
//	                                                                                                                        throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#setAtlasBubbleData(AtlasData)
//	 * @verifies set atlas.isDirty GlobalProperty to true
//	 */
//	@Test
//	public void setAtlasBubbleData_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#setAtlasData(AtlasData)
//	 * @verifies set atlas.isDirty GlobalProperty to true
//	 */
//	@Test
//	public void setAtlasData_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#setIncludeSystemConfiguration(Boolean)
//	 * @verifies set atlas.isDirty GlobalProperty to true
//	 */
//	@Test
//	public void setIncludeSystemConfiguration_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#setPosition(Double,Double)
//	 * @verifies set atlas.isDirty GlobalProperty to true
//	 */
//	@Test
//	public void setPosition_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
//	
//	/**
//	 * @see AtlasService#updateAndGetStatistics()
//	 * @verifies update statistics when one of them has the default value ("?") in GlobalProperties
//	 */
//	@Test
//	public void updateAndGetStatistics_shouldUpdateStatisticsWhenOneOfThemHasTheDefaultValueInGlobalProperties()
//	                                                                                                            throws Exception {
//		//TODO auto-generated
//		Assert.fail("Not yet implemented");
//	}
}