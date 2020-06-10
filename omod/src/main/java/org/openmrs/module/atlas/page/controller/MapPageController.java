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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author alex
 */
public class MapPageController {
    
    public void controller(PageModel model,
                           @RequestParam(value = "stopAskingToConfigure", required = false) Boolean stopAskingToConfigure) {
        AtlasService service = Context.getService(AtlasService.class);

        if (stopAskingToConfigure != null && stopAskingToConfigure) {
            service.setStopAskingToConfigure(true);
        }

        AtlasData data = service.getAtlasData();
        model.addAttribute("atlasData", data);
        model.addAttribute("stopAskingToConfigure", service.getStopAskingToConfigure());
    }
}
