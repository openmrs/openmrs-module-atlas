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

import org.openmrs.api.APIException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
public interface AtlasService {
	AtlasData getAtlasData() throws APIException;
	void setAtlasData(AtlasData data) throws APIException;	
	void enableAtlasModule() throws APIException;
	void disableAtlasModule(Boolean usageDisclaimerAccepted) throws APIException;
	void setAtlasBubbleData(AtlasData data) throws APIException;
	void setPosition(Double lat, Double lng) throws APIException;
	void setIncludeModules(Boolean includeModules) throws APIException;
	void setUsageDisclaimerAccepted(Boolean usageDisclaimerAccepted) throws APIException;
    void updateStatistics() throws APIException;
    String[] updateAndGetStatistics() throws APIException;
}
