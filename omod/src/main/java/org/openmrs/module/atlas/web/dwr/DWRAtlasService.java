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
package org.openmrs.module.atlas.web.dwr;

import java.util.UUID;

import org.openmrs.api.context.Context;
import org.openmrs.module.atlas.AtlasData;
import org.openmrs.module.atlas.AtlasService;


/**
 *
 */
public class DWRAtlasService {
	public void saveAtlasBubbleData(String latStr,	String lngStr,
			String name, String implementationTypeStr, String website, String imageURL, 
			String notes, String contactName, String contactEmail,
			String includeNumberOfPatientsStr, String includeNumberOfObservationsStr, String includeNumberOfEncountersStr)
	{
		
		Double lat = Double.valueOf(latStr);
		Double lng = Double.valueOf(lngStr);
		Integer implementationType = Integer.parseInt(implementationTypeStr);
		Boolean includeNumberOfObservations = Boolean.parseBoolean(includeNumberOfObservationsStr);
		Boolean includeNumberOfPatients = Boolean.parseBoolean(includeNumberOfPatientsStr);
		Boolean includeNumberOfEncounters = Boolean.parseBoolean(includeNumberOfEncountersStr);
		
			AtlasData data = new AtlasData();
			data.setName(name);
			data.setImplementationType(implementationType);
			data.setWebsite(website);
			data.setImageURL(imageURL);
			data.setLatitude(lat);
			data.setLongitude(lng);
			data.setNotes(notes);
			data.setContactName(contactName);
			data.setContactEmailAddress(contactEmail);
			data.setIncludeNumberOfPatients(includeNumberOfPatients);
			data.setIncludeNumberOfObservations(includeNumberOfObservations);
			data.setIncludeNumberOfEncounters(includeNumberOfEncounters);
			 
			Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;  
			service.setAtlasBubbleData(data);
	}
	
     public void enableAtlasModule() {
    	 Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			service.enableAtlasModule();
     }


      public void disableAtlasModule(String disclaimerAcceptedStr) {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			service.disableAtlasModule(Boolean.parseBoolean(disclaimerAcceptedStr));
      }
      
      public void setIncludeModules(String includeModulesStr) {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			service.setIncludeModules(Boolean.parseBoolean(includeModulesStr));
      }
      
      public void setPosition(String latStr, String lngStr) {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			service.setPosition(Double.parseDouble(latStr), Double.parseDouble(lngStr));
      }
      
      public void setZoom(String zoomStr) {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			service.setZoom(Integer.parseInt(zoomStr));
      }
      
      public void setUsageDisclaimer(String acceptedStr) {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			service.setUsageDisclaimerAccepted(Boolean.parseBoolean(acceptedStr));
      }
      
      public String getJsonData() {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			return service.getJson();
      }
      
      public String[] updateAndGetStatistics() {
    	  Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;   
			return service.updateAndGetStatistics();
      }
}
