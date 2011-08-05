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
 * In this class we keep the module constants :
 *  - global property names
 *  - server url to post data to
 *  - task name (for scheduler)
 */
public class AtlasConstants {
		
	public final static String GLOBALPROPERTY_ID = "atlas.id";
	public final static String GLOBALPROPERTY_NAME = "atlas.name";
	public final static String GLOBALPROPERTY_LATITUDE= "atlas.latitude";
	public final static String GLOBALPROPERTY_LONGITUDE= "atlas.longitude";
	public final static String GLOBALPROPERTY_ZOOM= "atlas.zoom";
	public final static String GLOBALPROPERTY_IMPLEMENTATION_TYPE = "atlas.implementationType";
	public final static String GLOBALPROPERTY_WEBSITE = "atlas.website";
	public final static String GLOBALPROPERTY_IMAGE_URL= "atlas.imageURL";
	public final static String GLOBALPROPERTY_NOTES = "atlas.notes";
	public final static String GLOBALPROPERTY_CONTACT_NAME = "atlas.contactName";
	public final static String GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS = "atlas.contactEmailAddress";
	public final static String GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS = "atlas.includeNumberOfPatients";
	public final static String GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS = "atlas.includeNumberOfObservations";
	public final static String GLOBALPROPERTY_INCLUDE_NUMBER_OF_ENCOUNTERS = "atlas.includeNumberOfEncounters";
	public final static String GLOBALPROPERTY_NUMBER_OF_PATIENTS = "atlas.numberOfPatients";
	public final static String GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS = "atlas.numberOfObservations";
	public final static String GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS = "atlas.numberOfEncounters";
	
	public final static String GLOBALPROPERTY_MODULE_ENABLED = "atlas.moduleEnabled";
	public final static String GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED = "atlas.usageDisclaimerAccepted";
	public final static String GLOBALPROPERTY_INCLUDE_MODULES = "atlas.includeModules";
	
	public final static String GLOBALPROPERTY_IS_DIRTY = "atlas.isDirty";
	
	public final static String POST_ATLAS_DATA_TASK_NAME = "Post Atlas Data Task";
	public final static String POST_ATLAS_DATA_TASK_DESCRIPTION = "Send Atlas information to OpenMRS on a weekly basis.";
	
	public final static String SERVER_URL = "http://burkeware.com/atlas/ping.php";
}
