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


import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Atlas Service contains all the methods needed to manipulate the atlas data
 */
@Transactional
public interface AtlasService {
	@Authorized({"Manage Atlas Data"})
	AtlasData getAtlasData() throws APIException;
	@Authorized({"Manage Atlas Data"})
	void setAtlasData(AtlasData data) throws APIException;
	@Authorized({"Manage Atlas Data"})
	void enableAtlasModule() throws APIException;
	@Authorized({"Manage Atlas Data"})
	void disableAtlasModule(Boolean usageDisclaimerAccepted) throws APIException;
	@Authorized({"Manage Atlas Data"})
	void setAtlasBubbleData(AtlasData data) throws APIException;
	@Authorized({"Manage Atlas Data"})
	void setPosition(Double lat, Double lng) throws APIException;
	@Authorized({"Manage Atlas Data"})
	void setZoom(Integer zoom) throws APIException;
	@Authorized({"Manage Atlas Data"})
	void setIncludeModules(Boolean includeModules) throws APIException;
	@Authorized({"Manage Atlas Data"})
	void setUsageDisclaimerAccepted(Boolean usageDisclaimerAccepted) throws APIException;
	@Authorized({"Manage Atlas Data"})
    void postAtlasData() throws APIException;
	@Authorized({"Manage Atlas Data"})
    String[] updateAndGetStatistics() throws APIException;
	@Authorized({"Manage Atlas Data"})
    Boolean getIsDirty() throws APIException;
	@Authorized({"Manage Atlas Data"})
    String getJson(Boolean isPreview) throws APIException;
}
