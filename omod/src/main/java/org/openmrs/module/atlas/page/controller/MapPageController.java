/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmrs.module.atlas.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.atlas.AtlasData;
import org.openmrs.module.atlas.AtlasService;
import org.openmrs.ui.framework.page.PageModel;

/**
 *
 * @author alex
 */
public class MapPageController {
    
    public void controller(PageModel model) {
        AtlasService service = (AtlasService) Context.getService(AtlasService.class);
        AtlasData data = service.getAtlasData();
        model.addAttribute("data", data);
    }
}
