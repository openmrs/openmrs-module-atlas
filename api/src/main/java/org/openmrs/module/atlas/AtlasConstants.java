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
 *
 */
public class AtlasConstants {
		
	public final static String GLOBALPROPERTY_ID = "atlasmodule.id";
	public final static String GLOBALPROPERTY_NAME = "atlasmodule.name";
	public final static String GLOBALPROPERTY_LATITUDE= "atlasmodule.latitude";
	public final static String GLOBALPROPERTY_LONGITUDE= "atlasmodule.longitude";
	public final static String GLOBALPROPERTY_WEBSITE = "atlasmodule.website";
	public final static String GLOBALPROPERTY_CONTACT_PHONE_NUMBER = "atlasmodule.contactPhoneNumber";
	public final static String GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS = "atlasmodule.contactEmailAddress";
	public final static String GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS = "atlasmodule.includeNumberOfPatients";
	public final static String GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS = "atlasmodule.includeNumberOfObservations";
	public final static String GLOBALPROPERTY_INCLUDE_NUMBER_OF_VISITS = "atlasmodule.includeNumberOfVisits";
	public final static String GLOBALPROPERTY_NUMBER_OF_PATIENTS = "atlasmodule.numberOfPatients";
	public final static String GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS = "atlasmodule.numberOfObservations";
	public final static String GLOBALPROPERTY_NUMBER_OF_VISITS = "atlasmodule.numberOfVisits";
	
	public final static String POST_ATLAS_DATA_TASK_NAME = "Post Atlas Data Task";
	public final static String POST_ATLAS_DATA_TASK_DESCRIPTION = "Send Atlas information to OpenMRS on a weekly basis.";
	
	public final static String SERVER_URL = "http://redirect.openmrs.org/atlas.php";
}
