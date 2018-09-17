/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.entity.UserCoupon;

@Validated
public interface UserCouponQueryService {

    List<UserCoupon> findUserCoupons(@NotNull Long eventOid, @NotNull String uid);

    List<UserCoupon> findUserCouponsToday(@NotNull Long eventOid, @NotNull String uid);

    Long countUserCouponsBetweenRange(@NotNull Long ruleOid, @NotNull Date startDate, @NotNull Date endDate);
}
