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
package org.openmrs.module.atlas.impl;

import org.openmrs.module.atlas.AtlasData;
import org.openmrs.module.atlas.AtlasService;
import org.openmrs.module.atlas.GlobalPropertiesKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.OpenmrsData;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;

/**
 *
 */
public class AtlasServiceImpl implements AtlasService {

	private Log log = LogFactory.getLog(this.getClass());	
	
	
    /**
     * 
     */
    public AtlasServiceImpl() {
	   
    }
	/**
     * @see org.openmrs.module.atlas.AtlasService#getAtlasData()
     */
    @Override
    public AtlasData getAtlasData() throws APIException {
    	AdministrationService svc = null;
    	
    	AtlasData atlasData = new AtlasData();
		try {
			
			svc = Context.getAdministrationService();
			String idString = svc.getGlobalProperty(GlobalPropertiesKeys.ID);

			if (idString == null || idString.trim().equals(""))	{
				atlasData.setId(UUID.randomUUID());		
			} else {
				String globalProperty;
				atlasData.setId(UUID.fromString(idString));
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.WEBSITE)) != null) {
					atlasData.setWebsite(globalProperty);
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.NAME)) != null) {
					atlasData.setName(globalProperty);
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.LATITUDE)) != null) {
					atlasData.setLatitude(globalProperty);
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.LONGITUDE)) != null) {
					atlasData.setLongitude(globalProperty);
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.CONTACT_EMAIL_ADDRESS)) != null) {
					atlasData.setContactEmailAddress(globalProperty);
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.CONTACT_PHONE_NUMBER)) != null) {
					atlasData.setContactPhoneNumber(globalProperty);
				}
				
				
			}
			svc.getGlobalProperty(GlobalPropertiesKeys.NAME);
		}
		catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not get atlas data. Exception:"+apiEx.getMessage());
		}
		
		log.debug("GetAtlasData: "+atlasData.toString());
		return atlasData;
    }

    
    private List<GlobalProperty> getGlobalPropertiesFromData(AtlasData data, Boolean firstTime) {
    	List<GlobalProperty> globalProperties = new ArrayList<GlobalProperty>();
    	if (firstTime) {
    		globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.ID, data.getId().toString()));
    	}
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.NAME, data.getName()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.WEBSITE, data.getWebsite()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.CONTACT_EMAIL_ADDRESS, data.getContactEmailAddress()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.CONTACT_PHONE_NUMBER, data.getContactPhoneNumber()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.LATITUDE, data.getLatitude()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.LONGITUDE, data.getLongitude()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.INCLUDE_NUMBER_OF_OBSERVATIONS, data.getIncludeNumberOfObservations().toString()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.INCLUDE_NUMBER_OF_PATIENTS, data.getIncludeNumberOfPatients().toString()));
    	globalProperties.add(new GlobalProperty(GlobalPropertiesKeys.INCLUDE_NUMBER_OF_VISITS, data.getIncludeNumberOfVisits().toString()));
    	return globalProperties;
    }
    	
    
	/**
     * @see org.openmrs.module.atlas.AtlasService#setAtlasData(org.openmrs.module.atlas.AtlasData)
     */
    @Override
    public void setAtlasData(AtlasData data) throws APIException {
    	AdministrationService svc = null;
		try {
			
			svc = Context.getAdministrationService();
			String idString = svc.getGlobalProperty(GlobalPropertiesKeys.ID);

			if (idString == null || idString.trim().equals(""))	{
				//first time
				svc.saveGlobalProperties(getGlobalPropertiesFromData(data, true));
			} else {
				svc.saveGlobalProperties(getGlobalPropertiesFromData(data, false));
			}
		}
		catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
		}
		
		log.debug("SetAtlasData: " + data.toString());   
    }

}
