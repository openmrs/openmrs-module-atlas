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
import org.openmrs.module.atlas.AtlasDataProcessor;
import org.openmrs.module.atlas.AtlasService;
import org.openmrs.module.atlas.AtlasConstants;

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
	
	private Log log = LogFactory.getLog(this.getClass());	
	private AtlasDataProcessor processor = null;
	
	
    /**
     * 
     */
    public AtlasServiceImpl() {
	   processor = new AtlasDataProcessor();
    }
	/**
     * @see org.openmrs.module.atlas.AtlasService#getAtlasData()
     */
    @Override
    public AtlasData getAtlasData() throws APIException {
    	AtlasData data = processor.getAtlasData();
		return data;
    }
  	
    
	/**
     * @see org.openmrs.module.atlas.AtlasService#setAtlasData(org.openmrs.module.atlas.AtlasData)
     */
    @Override
    public void setAtlasData(AtlasData data) throws APIException {
    	processor.setAtlasData(data);
    	processor.postAtlasData(data);
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
