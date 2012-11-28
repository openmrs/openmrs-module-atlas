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

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class AtlasModuleActivator extends BaseModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void willStart() {
		log.info("Starting Atlas Module");
	}
	
	/**
	 * @see org.openmrs.module.Activator#startup()
	 */
	@Override
	public void started() {
		log.info("Setting implementation id");
		try {
			AdministrationService svc = Context.getAdministrationService();
			String idString = svc.getGlobalProperty(AtlasConstants.GLOBALPROPERTY_ID);
			
			//if not found, generate UUID for Atlas ID
			if (idString == null || idString.trim().equals("")) {
				svc.saveGlobalProperty(new GlobalProperty(AtlasConstants.GLOBALPROPERTY_ID, UUID.randomUUID().toString()));
			}
		}
		catch (APIException apiEx) {
			if (log.isErrorEnabled())
				log.error("Could not set atlas id. Exception:" + apiEx.getMessage());
		}
		
	}
	
	/**
	 * @see org.openmrs.module.Activator#shutdown()
	 */
	@Override
	public void willStop() {
		log.info("Shutting down Atlas Module");
		Object o = Context.getService(AtlasService.class);
		AtlasService service = (AtlasService) o;
		service.disableAtlasModule(false);
	}
	
}
