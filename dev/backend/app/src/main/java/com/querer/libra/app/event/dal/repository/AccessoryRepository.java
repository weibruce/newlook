/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.dal.repository;

import java.util.List;

import com.querer.libra.app.event.domain.entity.Accessory;
import com.querer.libra.platform.core.dal.repository.BaseRepository;

/**
 * Accessory repository.
 */
public interface AccessoryRepository extends BaseRepository<Accessory, Long> {

    List<Accessory> findByEventOidAndCouponOidAndClaimedAndActiveTrue(Long eventOid, Long couponOid, Boolean claimed);
}
