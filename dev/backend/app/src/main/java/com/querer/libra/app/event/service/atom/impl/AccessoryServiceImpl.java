/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.dal.repository.AccessoryRepository;
import com.querer.libra.app.event.domain.entity.Accessory;
import com.querer.libra.app.event.service.atom.AccessoryService;

@Service
public class AccessoryServiceImpl implements AccessoryService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private AccessoryRepository repository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<Accessory> save(@NotNull Accessory accessory) {
        return repository.softlySave(accessory);
    }

    @Override
    public void delete(@NotNull Long oid) {
        repository.softlyDelete(oid);
    }

    @Override
    public Optional<Accessory> findById(@NotNull Long oid) {
        return Optional.ofNullable(repository.findOne(oid));
    }

    @Override
    public List<Accessory> findAvailableAccessories(@NotNull Long eventOid, @NotNull Long couponOid, @NotNull Boolean claimed) {
        return repository.findByEventOidAndCouponOidAndClaimedAndActiveTrue(eventOid, couponOid, claimed);
    }
}
