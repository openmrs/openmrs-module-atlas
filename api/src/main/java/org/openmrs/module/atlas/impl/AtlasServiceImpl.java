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

import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.atlas.AtlasData;
import org.openmrs.module.atlas.AtlasService;
import org.openmrs.module.atlas.AtlasConstants;
import org.openmrs.module.atlas.ImplementationType;
import org.openmrs.module.atlas.PostAtlasDataQueueTask;
import org.openmrs.scheduler.SchedulerException;
import org.openmrs.scheduler.Task;
import org.openmrs.scheduler.TaskDefinition;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.module.atlas.db.StatisticsDAO;


import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.OpenmrsData;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Console;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.openmrs.GlobalProperty;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.scheduler.SchedulerService;
import org.openmrs.util.DatabaseUpdater.OpenMRSChangeSet;
import org.openmrs.util.OpenmrsConstants;


/**
 * AtlasService Implementation
 */
public class AtlasServiceImpl implements AtlasService {
	
	private Log log = LogFactory.getLog(this.getClass());	
	private StatisticsDAO dao;
	
	
	private StatisticsDAO getStatisticsDAO() {
		return dao;
	}
	
	public void setStatisticsDAO(StatisticsDAO dao) {
		this.dao = dao;
	}
	
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
				String globalProperty = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);
				
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
		
		//System.out.println("GetAtlasData: " + atlasData.toString());
		return atlasData;
    }
  	
    
	/**
     * @see org.openmrs.module.atlas.AtlasService#setAtlasData(org.openmrs.module.atlas.AtlasData)
     */
    @Override
    public void setAtlasData(AtlasData data) throws APIException {
    	AdministrationService svc = null;
		try {
			svc = Context.getAdministrationService();
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
		//System.out.println("SetAtlasData: " + data.toString());
    }

    @Override
    public String[] updateAndGetStatistics() throws APIException {
    	AdministrationService svc = null;
    	String[] statsList = new String[3];
    	try {
			svc = Context.getAdministrationService();
			//get statistics from global properties
			GlobalProperty nrOfObsProp = svc.getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS);
			GlobalProperty nrOfEncountersProp = svc.getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS);
			GlobalProperty nrOfPatientsProp = svc.getGlobalPropertyObject(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS);
			
			String nrOfObservations = nrOfObsProp.getPropertyValue();
			String nrOfEncounters = nrOfEncountersProp.getPropertyValue();
			String nrOfPatients = nrOfPatientsProp.getPropertyValue();
			
			if (nrOfObservations.equals("?")
		    		|| nrOfEncounters.equals("?")
		    		|| nrOfPatients.equals("?")) {
		    		
					//get updated statistics
					nrOfObservations = String.valueOf(getStatisticsDAO().getNumberOfObservations());
					nrOfEncounters = String.valueOf(getStatisticsDAO().getNumberOfEncounters());
					nrOfPatients = String.valueOf(getStatisticsDAO().getNumberOfPatients());
		    		
					//update statistics
					nrOfObsProp.setPropertyValue(nrOfObservations);
					nrOfEncountersProp.setPropertyValue(nrOfEncounters);
					nrOfPatientsProp.setPropertyValue(nrOfPatients);
					
					svc.saveGlobalProperty(nrOfObsProp);
					svc.saveGlobalProperty(nrOfEncountersProp);
					svc.saveGlobalProperty(nrOfPatientsProp);
		    	}
		    	
		    	statsList[0] = String.valueOf(nrOfPatients);
		    	statsList[1] = String.valueOf(nrOfEncounters);
		    	statsList[2] = String.valueOf(nrOfObservations);
		}
		catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
		}
		finally{
			return statsList;
		}
    }
    
    @Override
    public void enableAtlasModule() throws APIException {
    	registerPostAtlasDataTask(60 * 60 * 24 * 7l);
    	setModuleEnabled(true);
    	setUsageDisclaimerAccepted(true);
    	postAtlasData();
    }
    
    
    @Override
    public void disableAtlasModule(Boolean usageDisclaimerAccepted) throws APIException {
    	unregisterTask(AtlasConstants.POST_ATLAS_DATA_TASK_NAME);
    	setModuleEnabled(false);
    	setUsageDisclaimerAccepted(usageDisclaimerAccepted);
    	sendDeleteMessageToServer();
    }

    @Override
    public void setAtlasBubbleData(AtlasData data) throws APIException {
    	AdministrationService svc = null;
		try {
			svc = Context.getAdministrationService();
			String idString = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);

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
		//System.out.println("SetAtlasData: " + data.toString());
    }

    @Override
    public void setIncludeModules(Boolean includeModules) throws APIException {
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

    @Override
    public void setUsageDisclaimerAccepted(Boolean usageDisclaimerAccepted) throws APIException {
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
	
    @Override
    public void setPosition(Double lat, Double lng) throws APIException {
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
    
    @Override
    public void setZoom(Integer zoom) throws APIException {
    	
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
    
    @Override
    public void postAtlasData() throws APIException {
    	//update statistics
    	Long nrOfObservations = getStatisticsDAO().getNumberOfObservations();
		Long nrOfEncounters = getStatisticsDAO().getNumberOfEncounters();
		Long nrOfPatients = getStatisticsDAO().getNumberOfPatients();
		
		AdministrationService svc = null;
		try {
			svc = Context.getAdministrationService();
			svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_OBSERVATIONS, String.valueOf(nrOfObservations)));
			svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_ENCOUNTERS, String.valueOf(nrOfEncounters)));
			svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_NUMBER_OF_PATIENTS, String.valueOf(nrOfPatients)));
		
			//get data
    	String jsonData = getJson(false);
    		//String x = "{\"id\":\"050b2506-b0a3-4414-9ac0-de8b9fbb563a\",\"geolocation\":{\"latitude\": 47.170151,\"longitude\":27.583548999999948},\"name\":\"Another Implementation\",\"notes\":\"Lorem ipsum\"}";
    	    
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
    	catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
		}
    	catch (Exception e) {
    		if (log.isErrorEnabled())
				log.error("Could not post atlas data. Exception:"+e.getMessage());
		}
    }
    
    @Override
    public String getJson(Boolean isPreview) throws APIException {
		AtlasData data = getAtlasData();
	    	StringBuilder sb = new StringBuilder();
	    	String name = data.getName();
	    	if (name == "" && isPreview) {
	    		name = "Preview Name";
	    	}
	    	sb.append("{\"id\" : \"" + data.getId()+ "\", ");
	    	sb.append("\"geolocation\" :  {\"latitude\" : "+ data.getLatitude()+", \"longitude\" : "+ data.getLongitude()+"}, ");
	    	sb.append("\"name\" : \""+name+"\",");
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
	        	sb.append("\"version\" : \""+ OpenmrsConstants.OPENMRS_VERSION + "\",");
	        	Collection<Module> modules = ModuleFactory.getLoadedModules();
	        	sb.append("\"modules\" : [");
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
    
    private boolean registerPostAtlasDataTask(long interval) {
    	return registerTask(AtlasConstants.POST_ATLAS_DATA_TASK_NAME
    		, AtlasConstants.POST_ATLAS_DATA_TASK_DESCRIPTION
    		, PostAtlasDataQueueTask.class,
    		interval);
    }
    
	private boolean registerTask(String name, String description, Class<? extends Task> clazz, long interval) {
		try {
			Context.addProxyPrivilege("Manage Scheduler");
		
			TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(name);
			if (taskDef == null) {
				taskDef = new TaskDefinition();
				taskDef.setTaskClass(clazz.getCanonicalName());
				taskDef.setStartOnStartup(true);
				taskDef.setRepeatInterval(interval);
				taskDef.setStarted(true);
				taskDef.setStartTime(getPreviousMidnight(null));
				taskDef.setName(name);
				taskDef.setUuid(UUID.randomUUID().toString()); 
				taskDef.setDescription(description);
				Context.getSchedulerService().scheduleTask(taskDef);
			}
			
		} catch (SchedulerException ex) {
			log.warn("Unable to register task '" + name + "' with scheduler", ex);
			return false;
		} finally {
			Context.removeProxyPrivilege("Manage Scheduler");
		}
		return true;
	}
	
	/**
	 * Unregisters the named task
	 * @param name the task name
	 */
	private void unregisterTask(String name) {
		TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(name);
		if (taskDef != null)
			Context.getSchedulerService().deleteTask(taskDef.getId());
	}
	
	private void setModuleEnabled(Boolean moduleEnabled) {
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
	
	
	
	private void sendDeleteMessageToServer() {
		AdministrationService svc = null;
    	try {
			svc = Context.getAdministrationService();
			String id = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);
			
			URL u = new URL(AtlasConstants.SERVER_URL+"?id="+ id + "&secret=victor");
	        HttpURLConnection connection = (HttpURLConnection)u.openConnection();
	        connection.setConnectTimeout(30000);
	        connection.setReadTimeout(30000);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("DELETE");

	        int status = connection.getResponseCode();
	        if(status == 200){
	             //  System.out.println("delete ok");
	        } else {
	        	//System.out.println("delete FAILED");
	        }
			
    	}
    	catch (Exception apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not delete atlas data. Exception:"+apiEx.getMessage());
		}
	}
	
	private static Date getPreviousMidnight(Date date) {
	    	// Initialize with date if it was specified
	    	Calendar cal = new GregorianCalendar();
			if (date != null)
				cal.setTime(date);
			  
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
	    }
    
}
