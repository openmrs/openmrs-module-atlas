/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.atlas;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Atlas Service contains all the methods needed to manipulate the atlas data
 */
/**
 *
 */
@Transactional
public interface AtlasService {
	
	/**
	 * Method returning the atlas data
	 * 
	 * @return The Atlas Data
	 * @throws APIException
	 * 
	 * @should throw java.lang.NullPointerException when atlas.id GlobalProperty does not exist
	 * @should throw java.lang.IllegalArgumentException when atlas.id GlobalPproperty is not a valid UUID
	 * @should initialize with default values (see constructor in AtlasData.java) all AtlasData fields, except id, that do not have corresponding GlobalProperties
	 * @Authorized({ "Manage Atlas Data" })
	 */
	AtlasData getAtlasData() throws APIException;
	
	/**
	 * Method setting the atlas data
	 * 
	 * @param data The atlas data
	 * @throws APIException
	 * 
	 * @should set atlas.isDirty GlobalProperty to true
	 */
	@Authorized({ "Manage Atlas Data" })
	void setAtlasData(AtlasData data) throws APIException;
	
	/**
	 * Enable the Atlas Module
	 * 
	 * @throws APIException
	 * 
	 * @should register a PostAtlasDataQueueTask
	 * @should set the atlas.usageDisclaimerAccepted to true
	 */
	@Authorized({ "Manage Atlas Data" })
	void enableAtlasModule() throws APIException;
	
	/**
	 * Disable the atlas module and set the atlas.usageDisclaimerAccepted global property
	 * 
	 * @param usageDisclaimerAccepted The value of the atlas.usageDisclaimerAccepted global property
	 * @throws APIException
	 * 
	 * @should unregister the PostAtlasDataQueueTask
	 * @should set the atlas.usageDisclaimerAccepted to the usageDisclaimerAccepted parameter value
	 */
	@Authorized({ "Manage Atlas Data" })
	void disableAtlasModule(Boolean usageDisclaimerAccepted) throws APIException;
	
	/**
	 * Method that sets only the bubble related Atlas Data NOTE: Even if this method receives all
	 * the atlas data, only those properties that appear in the bubble are set
	 * 
	 * @param data The Atlas Data
	 * @throws APIException
	 * 
	 * @should only set the AtlasData GlobalProperties that are related to the Atlas Bubble (see AtlasData.java)
	 * @should set atlas.isDirty GlobalProperty to true
	 */
	@Authorized({ "Manage Atlas Data" })
	void setAtlasBubbleData(AtlasData data) throws APIException;
	
	/**
	 * Method that sets the position (latitude and longitude) of the implementation
	 * 
	 * @param lat The latitude coordinate
	 * @param lng The longitude coordinate
	 * @throws APIException
	 * 
	 * @should set atlas.isDirty GlobalProperty to true
	 */
	@Authorized({ "Manage Atlas Data" })
	void setPosition(Double lat, Double lng) throws APIException;
	
	/**
	 * Method that sets the zoom level of the map
	 * 
	 * @param zoom The zoom level
	 * @throws APIException
	 */
	@Authorized({ "Manage Atlas Data" })
	void setZoom(Integer zoom) throws APIException;
	
	/**
	 * Method that sets the atlas.includeSystemConfiguration global property
	 * 
	 * @param includeSystemConfiguration
	 * @throws APIException
	 * 
	 * @should set atlas.isDirty GlobalProperty to true
	 */
	@Authorized({ "Manage Atlas Data" })
	void setIncludeSystemConfiguration(Boolean includeSystemConfiguration) throws APIException;
	
	/**
	 * Method that sets the atlas.usageDisclaimerAccepted global property
	 * 
	 * @param usageDisclaimerAccepted
	 * @throws APIException
	 */
	@Authorized({ "Manage Atlas Data" })
	void setUsageDisclaimerAccepted(Boolean usageDisclaimerAccepted) throws APIException;
	
	/**
	 * Method that gets the atlas data from global property and posts it to the OpenMRS server
	 * 
	 * @throws APIException
	 * 
	 * @should not modify atlas.isDirty GlobalProperty
	 * @should update the atlas.numberOfPatients GlobalProperty with the number of non-voided patients
	 * @should update the atlas.numberOfEncounters GlobalProperty with the number of non-voided encounters
	 * @should update the atlas.numberOfObservations GlobalProperty with the number of non-voided observations
	 */
	@Authorized({ "Manage Atlas Data" })
	void postAtlasData() throws APIException;
	
	/**
	 * Method that gets the statistics (number of non-voided patients, encounters, observations) from
	 * global properties. If one of these has the default value ("?"), then it gets the updated
	 * statistics, save them to global properties, and returns them as an array of Strings, in the
	 * following order: patients, encounters, observations
	 * 
	 * @return An array of 3 Strings representing the number of non-voided patients, encounters,
	 *         observations, in this order
	 * @throws APIException
	 * 
	 * @should update statistics when one of them has the default value ("?") in GlobalProperties
	 */
	@Authorized({ "Manage Atlas Data" })
	String[] updateAndGetStatistics() throws APIException;
	
	/**
	 * Method that returns false if the atlas data has not changed since the last post, true
	 * otherwise
	 * 
	 * @return False if the atlas data has not changed since the last post, true otherwise
	 * @throws APIException
	 */
	@Authorized({ "Manage Atlas Data" })
	Boolean getIsDirty() throws APIException;
	
	/**
	 * Method that returns the String containing the json data that will be sent to the OpenMRS
	 * server. If it is a preview and no name is specified, fill the name with a default value
	 * 
	 * @param isPreview
	 * @return The String containing the json data that will be sent to the OpenMRS server.
	 * @throws APIException
	 */
	@Authorized({ "Manage Atlas Data" })
	String getJson(Boolean isPreview) throws APIException;

    /**
	 * Method that sets the atlas.includeNumberOfPatients global property
	 * 
	 * @param includeNumberOfPatients
	 * @throws APIException
	 * 
	 * @should set atlas.isDirty GlobalProperty to true
	 */
    @Authorized({ "Manage Atlas Data" })
    public void setIncludeNumberOfPatients(Boolean includeNumberOfPatients) throws APIException;
    
    /**
	 * Method that sets the atlas.includeNumberOfEncounters global property
	 * 
	 * @param includeNumberOfEncounters
	 * @throws APIException
	 * 
	 * @should set atlas.isDirty GlobalProperty to true
	 */
    @Authorized({ "Manage Atlas Data" })
    public void setIncludeNumberOfEncounters(Boolean includeNumberOfEncounters) throws APIException;

    /**
	 * Method that sets the atlas.includeNumberOfObservations global property
	 * 
	 * @param includeNumberOfObservations
	 * @throws APIException
	 * 
	 * @should set atlas.isDirty GlobalProperty to true
	 */
    @Authorized({ "Manage Atlas Data" })
    public void setIncludeNumberOfObservations(Boolean includeNumberOfObservations) throws APIException;
}
