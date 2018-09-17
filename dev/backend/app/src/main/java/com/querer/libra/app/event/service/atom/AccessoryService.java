/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.entity.Accessory;

@Validated
public interface AccessoryService {

    Optional<Accessory> save(@NotNull Accessory accessory);

    void delete(@NotNull Long oid);

    Optional<Accessory> findById(@NotNull Long oid);

    List<Accessory> findAvailableAccessories(@NotNull Long eventOid, @NotNull Long couponOid, @NotNull Boolean claimed);
}
