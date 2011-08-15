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
	
	public void saveAtlasBubbleData(String latStr, String lngStr, String name, String implementationTypeStr, String website,
	                                String imageURL, String notes, String contactName, String contactEmail,
	                                String includeNumberOfPatientsStr, String includeNumberOfObservationsStr,
	                                String includeNumberOfEncountersStr) {
		
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
		getAtlasService().setAtlasBubbleData(data);
	}
	
	public void enableAtlasModule() {
		getAtlasService().enableAtlasModule();
	}
	
	public void disableAtlasModule(String disclaimerAcceptedStr) {
		getAtlasService().disableAtlasModule(Boolean.parseBoolean(disclaimerAcceptedStr));
	}
	
	public void setIncludeSystemConfiguration(String includeSystemConfigurationStr) {
		getAtlasService().setIncludeSystemConfiguration(Boolean.parseBoolean(includeSystemConfigurationStr));
	}
	
	public void setPosition(String latStr, String lngStr) {
		getAtlasService().setPosition(Double.parseDouble(latStr), Double.parseDouble(lngStr));
	}
	
	public void setZoom(String zoomStr) {
		getAtlasService().setZoom(Integer.parseInt(zoomStr));
	}
	
	public void setUsageDisclaimer(String acceptedStr) {
		getAtlasService().setUsageDisclaimerAccepted(Boolean.parseBoolean(acceptedStr));
	}
	
	public String getJsonData() {
		return getAtlasService().getJson(true);
	}
	
	public void postAtlasData() {
		getAtlasService().postAtlasData();
	}
	
	public Boolean getIsDirty() {
		return getAtlasService().getIsDirty();
	}
	
	public String[] updateAndGetStatistics() {
		return getAtlasService().updateAndGetStatistics();
	}
	
	private AtlasService getAtlasService() {
		return (AtlasService) Context.getService(AtlasService.class);
	}
}
