/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.model.UserCouponModel;

@Validated
public interface UserCouponBizService {

    UserCouponModel doDrawCoupon(@NotNull UserCouponModel userCouponModel);

    List<UserCouponModel> findUserCoupon(@NotNull String openingCode, @NotNull String uid);

    UserCouponModel doUseCoupon(@NotNull UserCouponModel userCouponModel);
}
