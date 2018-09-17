/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.dal.repository.UserCouponRepository;
import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.app.event.service.business.UserCouponQueryService;
import com.querer.libra.app.event.util.DateTimeUtil;

@Service
public class UserCouponQueryServiceImpl implements UserCouponQueryService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private UserCouponRepository userCouponRepository;

    /* public methods ------------------------------------------------------ */

    @Override
    public List<UserCoupon> findUserCoupons(@NotNull Long eventOid, @NotNull String uid) {
        return userCouponRepository.findByEventOidAndUid(eventOid, uid);
    }

    @Override
    public List<UserCoupon> findUserCouponsToday(@NotNull Long eventOid, @NotNull String uid) {
        Date now = new Date();
        Date startDate = DateTimeUtil.getStartOfDay(now);
        Date endDate = DateTimeUtil.getEndOfDay(now);
        return userCouponRepository.findByEventOidAndUidAndSubmitTimeBetween(eventOid, uid, startDate, endDate);
    }

    @Override
    public Long countUserCouponsBetweenRange(@NotNull Long ruleOid, @NotNull Date startDate, @NotNull Date endDate) {
        return userCouponRepository.countByRuleOidAndSubmitTimeBetween(ruleOid, startDate, endDate);
    }
}
