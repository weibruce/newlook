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

import com.querer.libra.app.event.dal.repository.CouponRepository;
import com.querer.libra.app.event.domain.entity.Coupon;
import com.querer.libra.app.event.service.atom.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private CouponRepository couponRepository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<Coupon> save(@NotNull Coupon coupon) {
        return couponRepository.softlySave(coupon);
    }

    @Override
    public void delete(@NotNull Long oid) {
        couponRepository.softlyDelete(oid);
    }

    @Override
    public Optional<Coupon> findById(@NotNull Long oid) {
        return Optional.ofNullable(couponRepository.findOne(oid));
    }
}
