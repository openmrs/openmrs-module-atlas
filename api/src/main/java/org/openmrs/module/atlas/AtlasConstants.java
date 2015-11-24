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

/**
 * In this class we keep the module constants : - global property names - server url to post data to
 * - task name (for scheduler)
 */
public class AtlasConstants {
	
	public final static String GLOBALPROPERTY_ID = "atlas.id";
	
	public final static String GLOBALPROPERTY_NUMBER_OF_PATIENTS = "atlas.numberOfPatients";
	
	public final static String GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS = "atlas.numberOfObservations";
	
	public final static String GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS = "atlas.numberOfEncounters";
	
	/* Atlas Bubble Data properties END */

	public final static String GLOBALPROPERTY_STOP_ASKING_TO_CONFIGURE = "atlas.stopAskingToConfigure";

	public final static String GLOBALPROPERTY_MODULE_ENABLED = "atlas.moduleEnabled";

    public final static String GLOBALPROPERTY_SEND_COUNTS = "atlas.sendCounts";
	
	public final static String GLOBALPROPERTY_IS_DIRTY = "atlas.isDirty";
	
	public final static String POST_ATLAS_DATA_TASK_NAME = "Post Atlas Data Task";
	
	public final static String POST_ATLAS_DATA_TASK_DESCRIPTION = "Send Atlas information to OpenMRS on a weekly basis.";
	
	public final static String SERVER_PING_URL = "https://atlas.openmrs.org/module/ping.php";
    
    public final static String SERVER_URL = "https://atlas.openmrs.org/module";

	public static final String PRIV_MANAGE_ATLAS_DATA = "Manage Atlas Data";
}
