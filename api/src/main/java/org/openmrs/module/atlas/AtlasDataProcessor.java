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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.openmrs.GlobalProperty;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;



/**
 *
 */
public class AtlasDataProcessor {

	private  Log log = LogFactory.getLog(this.getClass());	
	
	   public void postAtlasData(AtlasData data) {
	    	
	        	
	    	String jsonData = getJson(data);
	    	try {
		    	URL u = new URL(AtlasConstants.SERVER_URL);
		        HttpURLConnection connection = (HttpURLConnection)u.openConnection();
		        connection.setConnectTimeout(30000);
		        connection.setReadTimeout(30000);
		        connection.setDoOutput(true);
		        connection.setRequestMethod("POST");
		       
		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		        writer.write(jsonData);
		        writer.close(); 
	
		        System.out.println(jsonData);
		        int status = connection.getResponseCode();
		        if(status == 200){
		               System.out.println("ok");
		        }
	    	}
	    	catch (Exception e) {
	    		if (log.isErrorEnabled())
					log.error("Could not post atlas data. Exception:"+e.getMessage());
			}
	    }
	    
	   private String getJson(AtlasData data) {
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("{ 'geolocation' :  {'lat' : '"+ data.getLatitude()+"', 'lng' : '"+ data.getLongitude()+"'}, ");
	    	sb.append("'name' : '"+data.getName()+"',");
	        sb.append("'website' : '"+ data.getWebsite() +"',");
	        sb.append("'imageURL' : '"+ data.getImageURL() +"',");
	        if (data.getIncludeNumberOfPatients()) {
	        	sb.append("'patients' : '"+ data.getNumberOfPatients() + "',");
	        }
	        if (data.getIncludeNumberOfObservations()) {
	        	sb.append("'observations' : '"+ data.getNumberOfObservations() + "',");
	        }
	        if (data.getIncludeNumberOfVisits()) {
	        	sb.append("'visits' : '"+ data.getNumberOfVisits() + "',");
	        }
	        sb.append("'contact_details' :  {'email' : '" + data.getContactEmailAddress() + "',");
	        sb.append("'phone' : '" + data.getContactName() + "' }  }");
	        return sb.toString();
	    }

	   public AtlasData getAtlasData()  {
	    	AdministrationService svc = null;
	    	AtlasData atlasData = new AtlasData();
			try {
				
				svc = Context.getAdministrationService();
				String idString = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);

				if (idString == null || idString.trim().equals(""))	{
					atlasData.setId(UUID.randomUUID());		
				} else {
					String globalProperty;
					atlasData.setId(UUID.fromString(idString));
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE)) != null) {
						atlasData.setWebsite(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME)) != null) {
						atlasData.setName(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_IMAGE_URL)) != null) {
						atlasData.setImageURL(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE)) != null) {
						atlasData.setLatitude(Double.valueOf(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE)) != null) {
						atlasData.setLongitude(Double.valueOf(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS)) != null) {
						atlasData.setContactEmailAddress(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_PHONE_NUMBER)) != null) {
						atlasData.setContactName(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS)) != null) {
						atlasData.setIncludeNumberOfObservations(Boolean.parseBoolean(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS)) != null) {
						atlasData.setIncludeNumberOfPatients(Boolean.parseBoolean(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_VISITS)) != null) {
						atlasData.setIncludeNumberOfVisits(Boolean.parseBoolean(globalProperty));
					}
					
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS)) != null) {
						atlasData.setNumberOfObservations(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS)) != null) {
						atlasData.setNumberOfPatients(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_VISITS)) != null) {
						atlasData.setNumberOfVisits(globalProperty);
					}
				}
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not get atlas data. Exception:"+apiEx.getMessage());
			}
			
			System.out.println("GetAtlasData: " + atlasData.toString());
			return atlasData;
	    }
	  	
	    public void setAtlasData(AtlasData data)  {
	    	AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				String idString = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);

				if (idString == null || idString.trim().equals(""))	{
					svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
				} 
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME, data.getName()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE, data.getWebsite()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMAGE_URL, data.getImageURL()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS, data.getContactEmailAddress()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_PHONE_NUMBER, data.getContactName()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE, data.getLatitude().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS, data.getIncludeNumberOfObservations().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS, data.getIncludeNumberOfPatients().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_VISITS, data.getIncludeNumberOfVisits().toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
			System.out.println("SetAtlasData: " + data.toString());
	    }
	    
	  
	    
}
