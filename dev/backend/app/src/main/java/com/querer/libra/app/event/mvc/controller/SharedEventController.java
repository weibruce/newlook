/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.querer.libra.app.event.domain.model.SharedEventModel;
import com.querer.libra.app.event.service.business.SharedEventBizService;

@RestController
@RequestMapping("/shared-events")
public class SharedEventController {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private SharedEventBizService sharedEventBizService;

    /* public methods ------------------------------------------------------ */

    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public SharedEventModel postShareEvent(@RequestBody SharedEventModel sharedEventModel) {
        return sharedEventBizService.doSharedEvent(sharedEventModel);
    }
}
