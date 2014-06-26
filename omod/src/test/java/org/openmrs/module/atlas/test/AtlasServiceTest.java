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
		data.setId(UUID.fromString(id));
		return data;
	}
	
	public void saveTestAtlasDataToGlobalProperties(AtlasData data) {
		AdministrationService svc = Context.getAdministrationService();
		
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
		svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED, data.getModuleEnabled()
		        .toString()));
	}
	
	public void deleteTestAtlasDataFromGlobalProperties(AtlasData data) {
		AdministrationService svc = Context.getAdministrationService();
		
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
		svc.purgeGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED, data.getModuleEnabled()
		        .toString()));
	}
	
	/**
	 * @see AtlasService#disableAtlasModule(Boolean)
	 * @verifies unregister the PostAtlasDataQueueTask
	 */
	@Test
	public void disableAtlasModule_shouldUnregisterThePostAtlasDataQueueTask() throws Exception {
		atlasSrv.enableAtlasModule();
		atlasSrv.disableAtlasModule();
		
		TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(AtlasConstants.POST_ATLAS_DATA_TASK_NAME);
		assertTrue("The task should be null", taskDef == null);
	}
	
	/**
	 * @see AtlasService#enableAtlasModule()
	 * @verifies register a PostAtlasDataQueueTask
	 */
	@Test
	public void enableAtlasModule_shouldRegisterAPostAtlasDataQueueTask() throws Exception {
		atlasSrv.enableAtlasModule();
		
		TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(AtlasConstants.POST_ATLAS_DATA_TASK_NAME);
		assertTrue("The task should not be null", taskDef != null);
		
		atlasSrv.disableAtlasModule();
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
		
		assertTrue("isDirty should be true", newData.getIsDirty() == true);
		assertTrue("moduleEnabled should be false", newData.getModuleEnabled() == false);
		assertTrue("the number of Patients should be \"?\"", newData.getNumberOfPatients() == "?");
		assertTrue("the number of Observations should be \"?\"", newData.getNumberOfObservations() == "?");
		assertTrue("the number of Encounters should be \"?\"", newData.getNumberOfEncounters() == "?");
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
		
		atlasSrv.disableAtlasModule();
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
		Boolean moduleEnabled = true;
		
		AtlasData newData = getTestAtlasData();
		newData.setModuleEnabled(moduleEnabled);
		
		atlasSrv.setAtlasData(newData);

		newData.setModuleEnabled(!moduleEnabled);
		
		atlasSrv.setAtlasBubbleData(newData);
        
		assertTrue(
		    "Non bubble properties should not have changed",
		    Boolean.parseBoolean(getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED).getPropertyValue()) == moduleEnabled);
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
