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
	public void saveAtlasData(String idStr, String latStr,	String lngStr,
			String name, String website, String imageURL, String phone,	String email,
			String includeNumberOfObservationsStr, String includeNumberOfPatientsStr, String includeNumberOfVisitsStr)
	{
		
		UUID id = UUID.fromString(idStr);
		Double lat = Double.valueOf(latStr);
		Double lng = Double.valueOf(lngStr);
		Boolean includeNumberOfObservations = Boolean.parseBoolean(includeNumberOfObservationsStr);
		Boolean includeNumberOfPatients = Boolean.parseBoolean(includeNumberOfPatientsStr);
		Boolean includeNumberOfVisits = Boolean.parseBoolean(includeNumberOfVisitsStr);
		
			AtlasData data = new AtlasData(id, name, website, imageURL, lat, lng, phone, email, includeNumberOfPatients, includeNumberOfObservations, includeNumberOfVisits);
			System.out.println("DWR" + data.toString());
			Object o = Context.getService(AtlasService.class);
			AtlasService service =  (AtlasService)o;    //new AtlasServiceImpl();//
			service.setAtlasData(data);
	}
}