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

import com.querer.libra.app.event.domain.entity.UserCoupon;

@Validated
public interface UserCouponService {

    Optional<UserCoupon> save(@NotNull UserCoupon userCoupon);

    void delete(@NotNull Long oid);

    Optional<UserCoupon> findById(@NotNull Long oid);
}
