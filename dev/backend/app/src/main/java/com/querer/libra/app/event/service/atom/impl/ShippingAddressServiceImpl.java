/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom.impl;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.dal.repository.ShippingAddressRepository;
import com.querer.libra.app.event.domain.entity.ShippingAddress;
import com.querer.libra.app.event.service.atom.ShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private ShippingAddressRepository repository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<ShippingAddress> save(@NotNull ShippingAddress shippingAddress) {
        return repository.softlySave(shippingAddress);
    }

    @Override
    public void delete(@NotNull Long oid) {
        repository.softlyDelete(oid);
    }

    @Override
    public Optional<ShippingAddress> findById(@NotNull Long oid) {
        return Optional.ofNullable(repository.findOne(oid));
    }
}
