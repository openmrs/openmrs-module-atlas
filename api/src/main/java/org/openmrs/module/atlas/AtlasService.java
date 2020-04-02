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
	 * <strong>Should</strong> throw java.lang.NullPointerException when atlas.id GlobalProperty does not exist
	 * <strong>Should</strong> throw java.lang.IllegalArgumentException when atlas.id GlobalPproperty is not a valid UUID
	 * <strong>Should</strong> initialize with default values (see constructor in AtlasData.java) all AtlasData fields, except id, that do not have corresponding GlobalProperties
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	AtlasData getAtlasData() throws APIException;
	
	/**
	 * Method setting the atlas data
	 * 
	 * @param data The atlas data
	 * @throws APIException
	 * 
	 * <strong>Should</strong> set atlas.isDirty GlobalProperty to true
	 */
	@Authorized({AtlasConstants.PRIV_MANAGE_ATLAS_DATA})
	void setAtlasData(AtlasData data) throws APIException;
	
	/**
	 * Enable the Atlas Module
	 * 
	 * @throws APIException
	 * 
	 * <strong>Should</strong> register a PostAtlasDataQueueTask
	 * <strong>Should</strong> set the atlas.usageDisclaimerAccepted to true
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	void enableAtlasModule() throws APIException;
	
	/**
	 * Disable the atlas module and set the atlas.usageDisclaimerAccepted global property
	 * 
	 * @param usageDisclaimerAccepted The value of the atlas.usageDisclaimerAccepted global property
	 * @throws APIException
	 * 
	 * <strong>Should</strong> unregister the PostAtlasDataQueueTask
	 * <strong>Should</strong> set the atlas.usageDisclaimerAccepted to the usageDisclaimerAccepted parameter value
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	void disableAtlasModule() throws APIException;
	
	/**
	 * Method that sets only the bubble related Atlas Data NOTE: Even if this method receives all
	 * the atlas data, only those properties that appear in the bubble are set
	 * 
	 * @param data The Atlas Data
	 * @throws APIException
	 * 
	 * <strong>Should</strong> only set the AtlasData GlobalProperties that are related to the Atlas Bubble (see AtlasData.java)
	 * <strong>Should</strong> set atlas.isDirty GlobalProperty to true
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	void setAtlasBubbleData(AtlasData data) throws APIException;
	
	/**
	 * Method that gets the atlas data from global property and posts it to the OpenMRS server
	 * 
	 * @throws APIException
	 * 
	 * <strong>Should</strong> not modify atlas.isDirty GlobalProperty
	 * <strong>Should</strong> update the atlas.numberOfPatients GlobalProperty with the number of non-voided patients
	 * <strong>Should</strong> update the atlas.numberOfEncounters GlobalProperty with the number of non-voided encounters
	 * <strong>Should</strong> update the atlas.numberOfObservations GlobalProperty with the number of non-voided observations
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
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
	 * <strong>Should</strong> update statistics when one of them has the default value ("?") in GlobalProperties
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	String[] updateAndGetStatistics() throws APIException;
	
	/**
	 * Method that returns false if the atlas data has not changed since the last post, true
	 * otherwise
	 * 
	 * @return False if the atlas data has not changed since the last post, true otherwise
	 * @throws APIException
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	Boolean getIsDirty() throws APIException;
	
	/**
	 * Method that returns the String containing the json data that will be sent to the OpenMRS
	 * server. If it is a preview and no name is specified, fill the name with a default value
	 * 
	 * @param isPreview
	 * @return The String containing the json data that will be sent to the OpenMRS server.
	 * @throws APIException
	 */
	@Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
	String getJson(Boolean isPreview) throws APIException;

    /**
     * Method that sets the atlas.sendCounts global property
     *
     * @param sendCounts
     */
    @Authorized({ AtlasConstants.PRIV_MANAGE_ATLAS_DATA })
    void setSendCounts(Boolean sendCounts);

	/**
	 * Sets whether or not the user has said they no longer want to be asked to configure the Atlas
	 */
	void setStopAskingToConfigure(boolean stopAskingToConfigure);

	/**
	 * @return whether or not the user has said they no longer want to be asked to configure the Atlas
     */
	boolean getStopAskingToConfigure();
}
