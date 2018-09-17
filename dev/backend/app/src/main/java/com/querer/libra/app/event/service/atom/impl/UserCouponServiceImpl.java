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

import com.querer.libra.app.event.dal.repository.UserCouponRepository;
import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.app.event.service.atom.UserCouponService;

@Service
public class UserCouponServiceImpl implements UserCouponService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private UserCouponRepository userCouponRepository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<UserCoupon> save(@NotNull UserCoupon userCoupon) {
        return userCouponRepository.softlySave(userCoupon);
    }

    @Override
    public void delete(@NotNull Long oid) {
        userCouponRepository.softlyDelete(oid);
    }

    @Override
    public Optional<UserCoupon> findById(@NotNull Long oid) {
        return Optional.ofNullable(userCouponRepository.findOne(oid));
    }
}
