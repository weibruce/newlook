/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.domain.entity.ShippingAddress;
import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.app.event.domain.model.ShippingAddressModel;
import com.querer.libra.app.event.domain.model.UserCouponModel;
import com.querer.libra.app.event.service.atom.ShippingAddressService;
import com.querer.libra.app.event.service.atom.UserCouponService;
import com.querer.libra.app.event.service.business.ShippingAddressBizService;
import com.querer.libra.app.event.service.business.UserCouponBizService;
import com.querer.libra.platform.core.exception.BusinessException;

@Service
public class ShippingAddressBizServiceImpl implements ShippingAddressBizService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserCouponBizService userCouponBizService;

    @Autowired
    private Mapper beanMapper;

    /* public methods ------------------------------------------------------ */

    @Override
    public ShippingAddressModel saveShippingAddress(@NotNull ShippingAddressModel shippingAddressModel) {
        String uid = shippingAddressModel.getUid();
        if (StringUtils.isBlank(uid)) {
            throw new BusinessException("uid is empty.");
        }

        // mark as used
        Long userCouponOid = shippingAddressModel.getUserCouponOid();
        Optional<UserCoupon> userCouponOptional = userCouponService.findById(userCouponOid);
        if (!userCouponOptional.isPresent()) {
            throw new BusinessException("user coupon cannot be found.");
        }
        UserCouponModel userCouponModel = new UserCouponModel();
        userCouponModel.setOid(userCouponOid);
        userCouponModel.setUid(uid);
        userCouponBizService.doUseCoupon(userCouponModel);

        // save shipping address
        ShippingAddress shippingAddress = beanMapper.map(shippingAddressModel, ShippingAddress.class);
        shippingAddress.setSubmitTimestamp(new Date());

        shippingAddress = shippingAddressService.save(shippingAddress).get();
        shippingAddressModel = beanMapper.map(shippingAddress, ShippingAddressModel.class);

        return shippingAddressModel;
    }
}
