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

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

	private final String url = "http://redirect.openmrs.org/atlas.php";
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
    	//setDummyData();
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
					atlasData.setLatitude(Double.valueOf(globalProperty));
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.LONGITUDE)) != null) {
					atlasData.setLongitude(Double.valueOf(globalProperty));
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.CONTACT_EMAIL_ADDRESS)) != null) {
					atlasData.setContactEmailAddress(globalProperty);
				}
				if ( (globalProperty = svc.getGlobalProperty(GlobalPropertiesKeys.CONTACT_PHONE_NUMBER)) != null) {
					atlasData.setContactPhoneNumber(globalProperty);
				}		
			}
		}
		catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not get atlas data. Exception:"+apiEx.getMessage());
		}
		
		log.debug("GetAtlasData: "+atlasData.toString());
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
			String idString = svc.getGlobalProperty(GlobalPropertiesKeys.ID);

			if (idString == null || idString.trim().equals(""))	{
				svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.ID, data.getId().toString()));
			} 
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.NAME, data.getName()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.WEBSITE, data.getWebsite()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.CONTACT_EMAIL_ADDRESS, data.getContactEmailAddress()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.CONTACT_PHONE_NUMBER, data.getContactPhoneNumber()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.LATITUDE, data.getLatitude().toString()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.LONGITUDE, data.getLongitude().toString()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.INCLUDE_NUMBER_OF_OBSERVATIONS, data.getIncludeNumberOfObservations().toString()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.INCLUDE_NUMBER_OF_PATIENTS, data.getIncludeNumberOfPatients().toString()));
			svc.saveGlobalProperty(new GlobalProperty(GlobalPropertiesKeys.INCLUDE_NUMBER_OF_VISITS, data.getIncludeNumberOfVisits().toString()));
			
			postAtlasData(data);
		}
		catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not set atlas data. Exception:"+apiEx.getMessage());
		}
		
		log.debug("SetAtlasData: " + data.toString());
		log.debug("Post Atlas Data: "+ getJson(data));
    }

    private void postAtlasData(AtlasData data) {
    	
    	if (data.getIncludeNumberOfObservations()) {
    		//
    	}
    	if (data.getIncludeNumberOfPatients()) {
    		//
    	}
    	if (data.getIncludeNumberOfPatients()) {
    		//
    	}
    	
    	String jsonData = getJson(data);
    	try {
    	URL u = new URL(url);
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
    	}
    	catch (Exception e) {
    		if (log.isErrorEnabled())
				log.error("Could not post atlas data. Exception:"+e.getMessage());
		}
    //    if(status == HttpServletResponse.SC_OK){
     //          System.out.println("ok");
     //   }
    }
    

    private String getJson(AtlasData data) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("{ 'geolocation' :  {'lat' : '"+ data.getLatitude()+"', 'lng' : '"+ data.getLongitude()+"'}, ");
    	sb.append("'name' : '"+data.getName()+"',");
        sb.append("'website' : '"+ data.getWebsite() +"',");
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
        sb.append("'phone' : '" + data.getContactPhoneNumber() + "' }  }");
        return sb.toString();
    }
    
    private void setDummyData(){
    	AtlasData data = new AtlasData();
    	data.setId(UUID.fromString("d697e132-1554-440d-9cca-cf472c9686db"));
    	data.setContactEmailAddress("testing@openmrs.org");
    	data.setContactPhoneNumber("123456789");
    	data.setIncludeNumberOfObservations(false);
    	data.setIncludeNumberOfPatients(false);
    	data.setIncludeNumberOfVisits(false);
    	data.setLatitude(47.170151);
    	data.setLongitude(27.58354899999);
    	data.setName("Testing");
    	data.setWebsite("http:\\www.openmrs.org");
    	
    	setAtlasData(data);
    }
    
}
