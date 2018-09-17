/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.domain.entity.Accessory;
import com.querer.libra.app.event.service.atom.AccessoryService;
import com.querer.libra.app.event.service.business.AccessoryBizService;

@Service
public class AccessoryBizServiceImpl implements AccessoryBizService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private AccessoryService accessoryService;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<Accessory> findAvailableOne(@NotNull Long eventOid, @NotNull Long couponOid) {
        Accessory availableOne = null;
        List<Accessory> accessoryList = accessoryService.findAvailableAccessories(eventOid, couponOid, Boolean.FALSE);
        if (CollectionUtils.isNotEmpty(accessoryList)) {
            availableOne = accessoryList.get(0);
        }
        return Optional.ofNullable(availableOne);
    }
}
