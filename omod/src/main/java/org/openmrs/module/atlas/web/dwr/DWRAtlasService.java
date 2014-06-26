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
	
	public void saveAtlasBubbleData() {
		AtlasData data = new AtlasData();
		getAtlasService().setAtlasBubbleData(data);
	}
	
	public void enableAtlasModule() {
		getAtlasService().enableAtlasModule();
	}
	
	public void disableAtlasModule() {
		getAtlasService().disableAtlasModule();
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
