/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.entity.ShippingAddress;

@Validated
public interface ShippingAddressService {

    Optional<ShippingAddress> save(@NotNull ShippingAddress shippingAddress);

    void delete(@NotNull Long oid);

    Optional<ShippingAddress> findById(@NotNull Long oid);
}
