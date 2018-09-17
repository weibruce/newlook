/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.entity.Event;
import com.querer.libra.app.event.domain.entity.UserCoupon;

@Validated
public interface CouponDrawAlgorithm {

    Optional<UserCoupon> drawCoupon(@NotNull Event event, @NotNull String uid);
}
