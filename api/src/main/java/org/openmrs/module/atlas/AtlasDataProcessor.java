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

import java.io.Console;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.openmrs.GlobalProperty;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.scheduler.SchedulerService;
import org.openmrs.util.DatabaseUpdater.OpenMRSChangeSet;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;



/**
 *
 */
public class AtlasDataProcessor {

	private  Log log = LogFactory.getLog(this.getClass());	
	
	   public void postAtlasData(AtlasData data) {
	    	String jsonData = getJson(data);
	    	try {
//	    		String x = "{\"id\":\"1234\",\"geolocation\":{\"latitude\":0.123,\"longitude\":36.321},\"name\":\"Another 2 Implementation\",\"notes\":\"Lorem ipsum\"}";
	    	    
		    	URL u = new URL(AtlasConstants.SERVER_URL);
		        HttpURLConnection connection = (HttpURLConnection)u.openConnection();
		        connection.setConnectTimeout(30000);
		        connection.setReadTimeout(30000);
		        connection.setDoOutput(true);
		        connection.setRequestMethod("POST");
		        connection.setRequestProperty("Content-Type", "application/json");
		        
		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		        writer.write(jsonData);
		        writer.close(); 
	
		        System.out.println(jsonData);
		        int status = connection.getResponseCode();
		        if(status == 200){
		               System.out.println("sent ok");
		        } else {
		        	System.out.println("sent FAILED");
		        }
		        
		        	}
	    	catch (Exception e) {
	    		if (log.isErrorEnabled())
					log.error("Could not post atlas data. Exception:"+e.getMessage());
			}
	    }
	    

	   
	   private String getJson(AtlasData data) {
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("{\"id\" : \"" + data.getId()+ "\", ");
	    	sb.append("\"geolocation\" :  {\"latitude\" : "+ data.getLatitude()+", \"longitude\" : "+ data.getLongitude()+"}, ");
	    	sb.append("\"name\" : \""+data.getName()+"\",");
	    	sb.append("\"type\" : \""+ ImplementationType.values()[data.getImplementationType()]+"\",");
	        sb.append("\"website\" : \""+ data.getWebsite() +"\",");
	        sb.append("\"notes\" : \""+ data.getNotes() +"\",");
	        sb.append("\"imageURL\" : \""+ data.getImageURL() +"\",");
	        if (data.getIncludeNumberOfPatients()) {
	        	sb.append("\"patients\" : "+ data.getNumberOfPatients() + ",");
	        }
	        if (data.getIncludeNumberOfObservations()) {
	        	sb.append("\"observations\" : "+ data.getNumberOfObservations() + ",");
	        }
	        if (data.getIncludeNumberOfEncounters()) {
	        	sb.append("\"encounters\" : "+ data.getNumberOfEncounters() + ",");
	        }
	        sb.append("\"contact_details\" :  {\"email\" : \"" + data.getContactEmailAddress() + "\",");
	        sb.append("\"contact_name\" : \"" + data.getContactName() + "\" }");
	        
	        if (data.getIncludeModules()) {
	        	sb.append(", \"data\" : {");
	        	sb.append("\"version\" : "+ OpenmrsConstants.OPENMRS_VERSION + ",");
	        	Collection<Module> modules = ModuleFactory.getLoadedModules();
	        	sb.append(", \"modules\" : [");
	   		    for (Module mod : modules) {
	   		    	sb.append("{\"id\": \"" + mod.getModuleId() + "\",");
	   			    sb.append("\"name\": \"" + mod.getName() + "\",");
	   			    sb.append("\"version\": \"" + mod.getVersion() + "\",");
	   			    sb.append("\"active\" : \"" + mod.isStarted() + "\"},");
	   		    }
	   		    //delete last "," 
	   		    sb.deleteCharAt(sb.length() - 1);
  			    sb.append("]}");
  			    
	        }
	        sb.append("}");
	        return sb.toString();
	    }

	   public AtlasData getAtlasData()  {
	    	AdministrationService svc = null;
	    	AtlasData atlasData = new AtlasData();
			try {
				svc = Context.getAdministrationService();
					String globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);
					globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);
					atlasData.setId(UUID.fromString(globalProperty));
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE)) != null) {
						atlasData.setWebsite(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME)) != null) {
						atlasData.setName(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_IMPLEMENTATION_TYPE)) != null) {
						atlasData.setImplementationType(Integer.valueOf(globalProperty));
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
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ZOOM)) != null) {
						atlasData.setZoom(Integer.valueOf(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NOTES)) != null) {
						atlasData.setNotes(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS)) != null) {
						atlasData.setContactEmailAddress(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_NAME)) != null) {
						atlasData.setContactName(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS)) != null) {
						atlasData.setIncludeNumberOfObservations(Boolean.parseBoolean(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS)) != null) {
						atlasData.setIncludeNumberOfPatients(Boolean.parseBoolean(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_ENCOUNTERS)) != null) {
						atlasData.setIncludeNumberOfEncounters(Boolean.parseBoolean(globalProperty));
					}
					
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS)) != null) {
						atlasData.setNumberOfObservations(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS)) != null) {
						atlasData.setNumberOfPatients(globalProperty);
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS)) != null) {
						atlasData.setNumberOfEncounters(globalProperty);
					}
					
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_MODULES)) != null) {
						atlasData.setIncludeModules(Boolean.parseBoolean(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED)) != null) {
						atlasData.setModuleEnabled(Boolean.parseBoolean(globalProperty));
					}
					if ( (globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED)) != null) {
						atlasData.setUsageDisclamerAccepted(Boolean.parseBoolean(globalProperty));
					}
				}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not get atlas data. Exception:"+apiEx.getMessage());
			}
			
			System.out.println("GetAtlasData: " + getJson(atlasData));
			System.out.println("GetAtlasData: " + atlasData.toString());
			return atlasData;
	    }
	  	
	    public void setAtlasData(AtlasData data)  {
	    	AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
			/*	String idString = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);

				if (idString == null || idString.trim().equals(""))	{
					svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
				} 
				*/
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME, data.getName()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMPLEMENTATION_TYPE, data.getImplementationType().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE, data.getWebsite()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMAGE_URL, data.getImageURL()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS, data.getContactEmailAddress()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_NAME, data.getContactName()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NOTES, data.getNotes()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE, data.getLatitude().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS, data.getIncludeNumberOfObservations().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS, data.getIncludeNumberOfPatients().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_ENCOUNTERS, data.getIncludeNumberOfEncounters().toString()));
				
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED, data.getUsageDisclamerAccepted().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_MODULES, data.getIncludeModules().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED, data.getModuleEnabled().toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
			System.out.println("SetAtlasData: " + data.toString());
	    }
	    
	    public void setAtlasBubbleData(AtlasData data)  {
	    	AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				String idString = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);

				if (idString == null || idString.trim().equals(""))	{
					svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, data.getId().toString()));
				} 
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NAME, data.getName()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMPLEMENTATION_TYPE, data.getImplementationType().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_WEBSITE, data.getWebsite()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_IMAGE_URL, data.getImageURL()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_EMAIL_ADDRESS, data.getContactEmailAddress()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_CONTACT_NAME, data.getContactName()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NOTES, data.getNotes()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE, data.getLatitude().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, data.getLongitude().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_OBSERVATIONS, data.getIncludeNumberOfObservations().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_PATIENTS, data.getIncludeNumberOfPatients().toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_NUMBER_OF_ENCOUNTERS, data.getIncludeNumberOfEncounters().toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
			System.out.println("SetAtlasData: " + data.toString());
	    }
	    
        
	    public void setModuleEnabled(Boolean moduleEnabled) {
			AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_MODULE_ENABLED, moduleEnabled.toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
		}
	    
	    public void setUsageDisclaimerAccepted(Boolean usageDisclaimerAccepted) {
			AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_USAGE_DISCLAIMER_ACCEPTED, usageDisclaimerAccepted.toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
		}
		
	    public void setIncludeModules(Boolean includeModules) {
			AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_INCLUDE_MODULES, includeModules.toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
		}
	    
	    public void setStatistics(Long numberOfPatients, Long numberOfEncounters, Long numberOfObservations) {
	    	AdministrationService svc = null;
			try {
				System.out.println(numberOfEncounters+"XXXXXXXXXXXXXXXXX");
				svc = Context.getAdministrationService();
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS, numberOfObservations.toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS, numberOfPatients.toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS, numberOfEncounters.toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
	    }
	    
	    
	    public void setZoom(Integer zoom) {
			AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ZOOM, zoom.toString()));
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
		}
	    
	    public void setPosition(Double lat, Double lng) {
			AdministrationService svc = null;
			try {
				svc = Context.getAdministrationService();
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LATITUDE, lat.toString()));
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_LONGITUDE, lng.toString()));
				
			}
			catch (APIException apiEx) {
				if (log.isErrorEnabled())
					log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
			}
		}
}
