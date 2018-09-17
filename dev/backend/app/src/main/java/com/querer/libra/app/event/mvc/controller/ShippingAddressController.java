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

import com.querer.libra.app.event.domain.model.ShippingAddressModel;
import com.querer.libra.app.event.service.business.ShippingAddressBizService;

@RestController
@RequestMapping("/shipping-addresses")
public class ShippingAddressController {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private ShippingAddressBizService shippingAddressBizService;

    /* public methods ------------------------------------------------------ */

    @RequestMapping(method = RequestMethod.POST)
    public ShippingAddressModel postSave(@RequestBody ShippingAddressModel shippingAddressModel) {
        return shippingAddressBizService.saveShippingAddress(shippingAddressModel);
    }
}
