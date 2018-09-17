/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.dal.repository;

import java.util.Date;
import java.util.List;

import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.platform.core.dal.repository.BaseRepository;

/**
 * UserCoupon repository.
 */
public interface UserCouponRepository extends BaseRepository<UserCoupon, Long> {

    List<UserCoupon> findByEventOidAndUid(Long eventOid, String uid);

    List<UserCoupon> findByEventOidAndUidAndSubmitTimeBetween(Long eventOid, String uid, Date startDate, Date endDate);

    Long countByRuleOidAndSubmitTimeBetween(Long ruleOid, Date startDate, Date endDate);
}
