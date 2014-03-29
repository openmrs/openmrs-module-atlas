package org.openmrs.module.atlas.test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import org.junit.Before;
import org.junit.After;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.atlas.AtlasConstants;
import org.openmrs.module.atlas.AtlasData;
import org.openmrs.module.atlas.AtlasService;
import org.openmrs.api.APIException;
import org.openmrs.module.atlas.impl.AtlasServiceImpl;
import org.openmrs.scheduler.Task;
import org.openmrs.scheduler.TaskDefinition;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class AtlasServiceTest extends BaseModuleContextSensitiveTest {
	
	String id = "73b13680-c4a0-11e0-962b-0800200c9a66";
	
	AtlasService atlasSrv;
	
	AtlasData data;
	
	@Before
	public void runBeforeEachTest() throws Exception {
		atlasSrv = Context.getService(AtlasService.class); //new AtlasServiceImpl();//
		data = getTestAtlasData();
		saveTestAtlasDataToGlobalProperties(data);
	}
	
	@After
	public void runAfter() throws Exception {
		deleteTestAtlasDataFromGlobalProperties(data);
	}
	
	public AtlasData getTestAtlasData() {
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
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude().toString()));
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
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ATLAS_VERSION, data.getModuleVersion()
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
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude().toString()));
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
                svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ATLAS_VERSION, data.getModuleVersion()
		        .toString()));
	}
	
	/**
	 * @see AtlasService#disableAtlasModule(Boolean)
	 * @verifies unregister the PostAtlasDataQueueTask
	 */
	@Test
	public void disableAtlasModule_shouldUnregisterThePostAtlasDataQueueTask() throws Exception {
		atlasSrv.enableAtlasModule();
		atlasSrv.disableAtlasModule(true);
		
		TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(AtlasConstants.POST_ATLAS_DATA_TASK_NAME);
		assertTrue("The task should be null", taskDef == null);
	}
	
	/**
	 * @see AtlasService#disableAtlasModule(Boolean)
	 * @verifies set the atlas.usageDisclaimerAccepted to the usageDisclaimerAccepted parameter
	 *           value
	 */
	@Test
	public void disableAtlasModule_shouldSetTheAtlasusageDisclaimerAcceptedToTheUsageDisclaimerAcceptedParameterValue()
	                                                                                                                   throws Exception {
		atlasSrv.disableAtlasModule(false);
		AtlasData updatedData = atlasSrv.getAtlasData();
		assertTrue("The UsageDisclaimerAccepted should be false", updatedData.getUsageDisclamerAccepted() == false);
		
		atlasSrv.disableAtlasModule(true);
		updatedData = atlasSrv.getAtlasData();
		assertTrue("The UsageDisclaimerAccepted should be true", updatedData.getUsageDisclamerAccepted() == true);
		
	}
	
	//todo victor
	//	/**
	//	 * @see AtlasService#disableAtlasModule(Boolean)
	//	 * @verifies send a delete message to the server
	//	 */
	//	@Test
	//	public void disableAtlasModule_shouldSendADeleteMessageToTheServer() throws Exception {
	//		//TODO auto-generated
	//		Assert.fail("Not yet implemented");
	//	}
	
	/**
	 * @see AtlasService#enableAtlasModule()
	 * @verifies register a PostAtlasDataQueueTask
	 */
	@Test
	public void enableAtlasModule_shouldRegisterAPostAtlasDataQueueTask() throws Exception {
		atlasSrv.enableAtlasModule();
		
		TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(AtlasConstants.POST_ATLAS_DATA_TASK_NAME);
		assertTrue("The task should not be null", taskDef != null);
		
		atlasSrv.disableAtlasModule(true);
	}
	
	/**
	 * @see AtlasService#enableAtlasModule()
	 * @verifies set the atlas.usageDisclaimerAccepted to true
	 */
	@Test
	public void enableAtlasModule_shouldSetTheAtlasusageDisclaimerAcceptedToTrue() throws Exception {
		setGlobalProperty(AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED, "false");
		atlasSrv.enableAtlasModule();
		
		Boolean disclaimer = Boolean.parseBoolean(getGlobalPropertyObject(
		    AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED).getPropertyValue());
		assertTrue("The UsageDisclaimerAccepted should be true", disclaimer == true);
		
		atlasSrv.disableAtlasModule(true);
	}
	
	/**
	 * @see AtlasService#getAtlasData()
	 * @verifies initialize with default values (see constructor in AtlasData.java) all AtlasData
	 *           fields, except id, that do not have corresponding GlobalProperties
	 */
	@Test
	public void getAtlasData_shouldInitializeWithDefaultValuesSeeConstructorInAtlasDatajavaAllAtlasDataFieldsExceptIdThatDoNotHaveCorrespondingGlobalProperties()
	                                                                                                                                                             throws Exception {
		AtlasData newData = atlasSrv.getAtlasData();
		
		assertTrue("notes should be empty", newData.getNotes() == "");
		assertTrue("notes should be empty", newData.getNotes() == "");
		assertTrue("contactEmailAddress should be empty", newData.getContactEmailAddress() == "");
		assertTrue("contactName should be empty", newData.getContactName() == "");
		assertTrue("imageURL should be empty", newData.getImageURL() == "");
		assertTrue("website should be empty", newData.getWebsite() == "");
		assertTrue("notes should be empty", newData.getNotes() == "");
		assertTrue("includeSystemConfiguration should be true", newData.getIncludeSystemConfiguration() == true);
		assertTrue("usageDisclamerAccepted should be false", newData.getUsageDisclamerAccepted() == false);
		assertTrue("isDirty should be true", newData.getIsDirty() == true);
		assertTrue("moduleEnabled should be false", newData.getModuleEnabled() == false);
		assertTrue("includeNumberOfPatients should be false", newData.getIncludeNumberOfPatients() == false);
		assertTrue("includeNumberOfObservations should be false", newData.getIncludeNumberOfObservations() == false);
		assertTrue("includeNumberOfEncounters should be false", newData.getIncludeNumberOfEncounters() == false);
		assertTrue("the number of Patients should be \"?\"", newData.getNumberOfPatients() == "?");
		assertTrue("the number of Observations should be \"?\"", newData.getNumberOfObservations() == "?");
		assertTrue("the number of Encounters should be \"?\"", newData.getNumberOfEncounters() == "?");
		assertTrue("the zoom should be 3", newData.getZoom() == 3);
		assertTrue("implementationType should be 0", newData.getImplementationType() == 0);
		assertTrue("longitude should be 0.0", newData.getLongitude() == 0.0);
		assertTrue("latitude should be 0.0", newData.getLatitude() == 0.0);
                assertTrue("atlas version should be null", newData.getModuleVersion() == "");
	}
	
	/**
	 * @see AtlasService#getAtlasData()
	 * @verifies throw java.lang.NullPointerException when atlas.id GlobalPproperty does not exist
	 */
	@Test
	public void getAtlasData_shouldThrowJavalangIllegalArgumentExceptionWhenAtlasidGlobalPropertyDoesNotExist()
	                                                                                                           throws Exception {
		Boolean correctError = false;
		try {
			AdministrationService svc = Context.getAdministrationService();
			
			GlobalProperty prop = svc.getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_ID);
			svc.purgeGlobalProperty(prop);
			AtlasData updatedData = atlasSrv.getAtlasData();
			System.out.println(updatedData.toString());
		}
		catch (Exception ex) {
			if (ex instanceof java.lang.NullPointerException) {
				correctError = true;
			}
		}
		finally {
			assertTrue("Exception should be of type java.lang.NullPointerException", correctError);
		}
		
	}
	
	/**
	 * @see AtlasService#getAtlasData()
	 * @verifies throw java.lang.IllegalArgumentException when atlas.id GlobalPproperty is not a
	 *           valid UUID
	 */
	@Test
	public void getAtlasData_shouldThrowJavalangIllegalArgumentExceptionWhenAtlasidGlobalPropertyIsNotAValidUUID()
	                                                                                                              throws Exception {
		Boolean correctError = false;
		try {
			AdministrationService svc = Context.getAdministrationService();
			setGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, "invalid UUID");
			AtlasData updatedData = atlasSrv.getAtlasData();
		}
		catch (Exception ex) {
			if (ex instanceof java.lang.IllegalArgumentException) {
				correctError = true;
			}
		}
		finally {
			assertTrue("Exception should be of type java.lang.IllegalArgumentException", correctError);
		}
	}
	
	/**
	 * @see AtlasService#postAtlasData()
	 * @verifies set atlas.isDirty GlobalProperty to false
	 */
	@Test
	public void postAtlasData_shouldSetAtlasisDirtyGlobalPropertyToFalse() throws Exception {
		setGlobalProperty(AtlasConstants.GLOBALPROPERTY_IS_DIRTY, "true");
		atlasSrv.postAtlasData();
		Boolean isDirty = Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_IS_DIRTY)
		        .getPropertyValue());
		assertTrue("IsDirty should be false", isDirty == false);
		
		atlasSrv.disableAtlasModule(false);
	}
	
	/**
	 * @see AtlasService#postAtlasData()
	 * @verifies update the atlas.numberOfPatients GlobalProperty with the number of non-voided
	 *           patients
	 */
	@Test
	public void postAtlasData_shouldUpdateTheAtlasnumberOfPatientsGlobalPropertyWithTheNumberOfNonvoidedPatients()
	                                                                                                              throws Exception {
		atlasSrv.postAtlasData();
		GlobalProperty prop = getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS);
		
		assertTrue("After post, value of numberOfPatients should not be \"?\"", prop.getPropertyValue() != "?");
	}
	
		/**
		 * @see AtlasService#postAtlasData()
		 * @verifies update the atlas.numberOfEncounters GlobalProperty with the number of non-voided encounters
		 */
		@Test
		public void postAtlasData_shouldUpdateTheAtlasnumberOfEncountersGlobalPropertyWithTheNumberOfNonvoidedEncounters() throws Exception {
			atlasSrv.postAtlasData();
			GlobalProperty prop = getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS);
			
			assertTrue("After post, value of numberOfEncounters should not be \"?\"", prop.getPropertyValue() != "?");

		}
		
		/**
		 * @see AtlasService#postAtlasData()
		 * @verifies update the atlas.numberOfObservations GlobalProperty with the number of non-voided observations
		 */
		@Test
		public void postAtlasData_shouldUpdateTheAtlasnumberOfObservationsGlobalPropertyWithTheNumberOfNonvoidedObservations() throws Exception {
			atlasSrv.postAtlasData();
			GlobalProperty prop = getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS);
			
			assertTrue("After post, value of numberOfObservations should not be \"?\"", prop.getPropertyValue() != "?");
		}
	
	/**
	 * @see AtlasService#setAtlasBubbleData(AtlasData)
	 * @verifies only set the AtlasData GlobalProperties that are related to the Atlas Bubble (see
	 *           AtlasData.java)
	 */
	@Test
	public void setAtlasBubbleData_shouldOnlySetTheAtlasDataGlobalPropertiesThatAreRelatedToTheAtlasBubble()
	                                                                                                        throws Exception {
		Double latitude = 123D;
		Double longitude = 123D;
		Integer zoom = 12;
		Boolean usageDisclamerAccepted = true;
		Boolean moduleEnabled = true;
		Boolean includeSystemConfiguration = true;
		
		AtlasData newData = getTestAtlasData();
		newData.setLatitude(latitude);
		newData.setLongitude(longitude);
		newData.setZoom(zoom);
		newData.setUsageDisclamerAccepted(usageDisclamerAccepted);
		newData.setModuleEnabled(moduleEnabled);
		newData.setIncludeSystemConfiguration(includeSystemConfiguration);

		atlasSrv.setAtlasData(newData);
		
		newData.setLatitude(0D);
		newData.setLongitude(0D);
		newData.setZoom(0);
		newData.setUsageDisclamerAccepted(!usageDisclamerAccepted);
		newData.setModuleEnabled(!moduleEnabled);
		newData.setIncludeSystemConfiguration(!includeSystemConfiguration);
		
		atlasSrv.setAtlasBubbleData(newData);
		
		assertTrue(
		    "Non bubble properties should not have changed",
		    Double.parseDouble(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_LATITUDE).getPropertyValue()) == latitude);
		assertTrue(
		    "Non bubble properties should not have changed",
		    Double.parseDouble(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_LONGITUDE).getPropertyValue()) == longitude);
		assertTrue("Non bubble properties should not have changed",
		    Integer.parseInt(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_ZOOM).getPropertyValue()) == zoom);
		assertTrue("Non bubble properties should not have changed", Boolean.parseBoolean(getGlobalPropertyObject(
		    AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED).getPropertyValue()) == usageDisclamerAccepted);
		assertTrue(
		    "Non bubble properties should not have changed",
		    Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED).getPropertyValue()) == moduleEnabled);
		assertTrue("Non bubble properties should not have changed", Boolean.parseBoolean(getGlobalPropertyObject(
		    AtlasConstants.GLOBALPROPERTY_INCLUDE_SYSTEM_CONFIGURATION).getPropertyValue()) == includeSystemConfiguration);
		
	}
	
	/**
	 * @see AtlasService#setAtlasBubbleData(AtlasData)
	 * @verifies set atlas.isDirty GlobalProperty to true
	 */
	@Test
	public void setAtlasBubbleData_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
		AtlasData newData = getTestAtlasData();
		atlasSrv.setAtlasBubbleData(newData);
		assertTrue("IsDirty should be true.",
		    Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_IS_DIRTY).getPropertyValue()) == true);
	}
	
	/**
	 * @see AtlasService#setAtlasData(AtlasData)
	 * @verifies set atlas.isDirty GlobalProperty to true
	 */
	@Test
	public void setAtlasData_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
		AtlasData newData = getTestAtlasData();
		atlasSrv.setAtlasData(newData);
		assertTrue("IsDirty should be true.",
		    Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_IS_DIRTY).getPropertyValue()) == true);
	}
	
	/**
	 * @see AtlasService#setIncludeSystemConfiguration(Boolean)
	 * @verifies set atlas.isDirty GlobalProperty to true
	 */
	@Test
	public void setIncludeSystemConfiguration_shouldNotModifyAtlasisDirtyGlobalProperty() throws Exception {
		setGlobalProperty(AtlasConstants.GLOBALPROPERTY_IS_DIRTY, "false");
		atlasSrv.setIncludeSystemConfiguration(true);
		assertTrue(
		    "IsDirty should remained unchanged (should be false).",
		    Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_IS_DIRTY).getPropertyValue()) == false);
	}
	
	/**
	 * @see AtlasService#setPosition(Double,Double)
	 * @verifies set atlas.isDirty GlobalProperty to true
	 */
	@Test
	public void setPosition_shouldSetAtlasisDirtyGlobalPropertyToTrue() throws Exception {
		atlasSrv.setPosition(2D, 2D);
		assertTrue("IsDirty should be true.",
		    Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_IS_DIRTY).getPropertyValue()) == true);
	}
	
		
		/**
		 * @see AtlasService#updateAndGetStatistics()
		 * @verifies update statistics when one of them has the default value ("?") in GlobalProperties
		 */
		@Test
		public void updateAndGetStatistics_shouldUpdateStatisticsWhenOneOfThemHasTheDefaultValueInGlobalProperties() throws Exception {
			//this step is usually done when the module is activated, so we do it in code here
			setGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS, "?");
			setGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS, "?");
			setGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS, "?");
			
			String[] stats = atlasSrv.updateAndGetStatistics();
			
			for (int i=0; i<3; ++i) {
				if (stats[i] == "?") {
					fail("None of the returned values should be \"?\"");
				}
			}
			
			GlobalProperty prop = getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS);
			assertTrue("After updateAndGetStatistics, value of numberOfObservations should not be \"?\"", prop.getPropertyValue() != "?");
			
			prop = getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS);
			assertTrue("After updateAndGetStatistics, value of numberOfPatients should not be \"?\"", prop.getPropertyValue() != "?");
			
			prop = getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS);
			assertTrue("After updateAndGetStatistics, value of numberOfEncounters should not be \"?\"", prop.getPropertyValue() != "?");
		}
	
	private void setGlobalProperty(String name, String value) {
		AdministrationService svc = null;
		try {
			svc = Context.getAdministrationService();
			GlobalProperty prop = svc.getGlobalPropertyObject(name);
			if (prop == null) {
				svc.saveGlobalProperty(new GlobalProperty(name, value));
			} else {
				prop.setPropertyValue(value.toString());
				svc.saveGlobalProperty(prop);
			}
		}
		catch (APIException apiEx) {
			System.out.println("Could not set global property: (" + name + " - " + value + "). Exception:"
			        + apiEx.getMessage());
		}
	}
	
	private GlobalProperty getGlobalPropertyObject(String name) {
		AdministrationService svc = null;
		GlobalProperty prop = null;
		try {
			svc = Context.getAdministrationService();
			prop = svc.getGlobalPropertyObject(name);
			return prop;
		}
		catch (APIException apiEx) {
			System.out.println("Could not get global property: (" + name + "). Exception:" + apiEx.getMessage());
		}
		
		return prop;
	}
}
