/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.google.common.base.Stopwatch;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.domain.entity.Accessory;
import com.querer.libra.app.event.domain.entity.Event;
import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.app.event.domain.model.UserCouponModel;
import com.querer.libra.app.event.exception.EventClosedException;
import com.querer.libra.app.event.exception.EventNotFoundException;
import com.querer.libra.app.event.exception.InvalidUserCouponException;
import com.querer.libra.app.event.exception.NotYourCouponException;
import com.querer.libra.app.event.exception.ParameterRequiredException;
import com.querer.libra.app.event.exception.UserCouponExpiredException;
import com.querer.libra.app.event.exception.UserCouponIsUsedException;
import com.querer.libra.app.event.exception.UserCouponNotFoundException;
import com.querer.libra.app.event.service.atom.AccessoryService;
import com.querer.libra.app.event.service.atom.EventService;
import com.querer.libra.app.event.service.atom.UserCouponService;
import com.querer.libra.app.event.service.business.AccessoryBizService;
import com.querer.libra.app.event.service.business.CouponDrawAlgorithm;
import com.querer.libra.app.event.service.business.UserCouponBizService;
import com.querer.libra.app.event.service.business.UserCouponQueryService;

@Service
public class UserCouponBizServiceImpl implements UserCouponBizService {

    /* fields -------------------------------------------------------------- */

    private final static Logger logger = LoggerFactory.getLogger(UserCouponBizServiceImpl.class);

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserCouponQueryService userCouponQueryService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CouponDrawAlgorithm couponDrawAlgorithm;

    @Autowired
    private AccessoryBizService accessoryBizService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private Mapper beanMapper;

    /* public methods ------------------------------------------------------ */

    @Override
    public UserCouponModel doDrawCoupon(@NotNull UserCouponModel userCouponModel) {
        // check uid
        String uid = userCouponModel.getUid();
        if (StringUtils.isBlank(uid)) {
            throw new ParameterRequiredException("uid is required.");
        }

        // check event
        String openingCode = StringUtils.EMPTY;
        if (userCouponModel.getEvent() != null) {
            openingCode = userCouponModel.getEvent().getOpeningCode();
        }

        Optional<Event> eventOptional = eventService.findByOpeningCode(openingCode);
        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException("cannot find event with opening code: " + openingCode);
        }
        Event event = eventOptional.get();
        if (!event.getEnabled()) {
            throw new EventClosedException("event is closed");
        }

        // TODO sync issue when draw...
        Stopwatch timer = Stopwatch.createStarted();
        Optional<UserCoupon> userCouponOptional = couponDrawAlgorithm.drawCoupon(event, uid);
        long elapsedSeconds = timer.stop().elapsed().getSeconds();
        if (2 < elapsedSeconds) {
            logger.warn("draw coupon method elapsed over 2 seconds, cost : " + timer);
        }

        UserCouponModel model;
        if (userCouponOptional.isPresent()) {
            Date now = new Date();
            UserCoupon userCoupon = userCouponOptional.get();

            userCoupon.setUid(uid);
            userCoupon.setEventOid(event.getOid());

            userCoupon.setOccurTime(userCouponModel.getOccurTime());
            userCoupon.setSubmitTime(now);

            userCoupon.setStartTime(event.getStartTime());
            userCoupon.setEndTime(event.getEndTime());

            userCoupon.setUsed(Boolean.FALSE);

            // obtain barcode accessory
            Long couponOid = userCoupon.getCoupon().getOid();
            // TODO sync control
            Optional<Accessory> accessoryOptional = accessoryBizService.findAvailableOne(event.getOid(), couponOid);
            if (accessoryOptional.isPresent()) {
                Accessory barcodeAccessory = accessoryOptional.get();
                // update accessory only when quantity is 1
                if (barcodeAccessory.getQuantity().intValue() == 1) {
                    barcodeAccessory.setUid(userCoupon.getUid());
                    barcodeAccessory.setClaimed(Boolean.TRUE);
                    barcodeAccessory.setClaimedTime(now);
                    barcodeAccessory.setRuleOid(userCoupon.getRuleOid());
                    accessoryService.save(barcodeAccessory);
                }
                userCoupon.setBarcode(barcodeAccessory.getContent());
            }

            // TODO cache in mem and persist later
            userCoupon = userCouponService.save(userCoupon).get();

            model = beanMapper.map(userCoupon, UserCouponModel.class);
        } else {
            // if cannot hit one rule, return null to client?
            model = null;
        }

        return model;
    }

    @Override
    public List<UserCouponModel> findUserCoupon(@NotNull String openingCode, @NotNull String uid) {
        Optional<Event> eventOptional = eventService.findByOpeningCode(openingCode);
        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException("cannot find event with opening code: " + openingCode);
        }

        List<UserCoupon> userCouponList = userCouponQueryService.findUserCoupons(eventOptional.get().getOid(), uid);
        List<UserCouponModel> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userCouponList)) {
            result = userCouponList.stream().map(e -> beanMapper.map(e, UserCouponModel.class)).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public UserCouponModel doUseCoupon(@NotNull UserCouponModel userCouponModel) {

        Long couponOid = userCouponModel.getOid();
        String uid = userCouponModel.getUid();
        if (couponOid == null || StringUtils.isBlank(uid)) {
            throw new ParameterRequiredException("cannot use this coupon {user coupon oid is " + couponOid + " uid is " + uid);
        }

        Optional<UserCoupon> userCouponOptional = userCouponService.findById(couponOid);
        if (!userCouponOptional.isPresent()) {
            throw new UserCouponNotFoundException("the user coupon is going to be used cannot be found: " + couponOid);
        }

        // validate biz
        UserCoupon userCoupon = userCouponOptional.get();
        if (!StringUtils.equalsIgnoreCase(uid, userCoupon.getUid())) {
            throw new NotYourCouponException("the user coupon is not belonged to you");
        }

        if (userCoupon.getUsed()) {
            throw new UserCouponIsUsedException("the user coupon has been used already.");
        }

        Date now = new Date();
        Date startTime = userCoupon.getStartTime();
        Date endTime = userCoupon.getEndTime();
        if (startTime != null && endTime != null) {
            if (!(now.after(startTime) && now.before(endTime))) {
                throw new UserCouponExpiredException("the user coupon is expired or not in event period.");
            }
        } else {
            throw new InvalidUserCouponException("the user coupon is not valid because it have invalid start time or end time.");
        }

        // update coupon as used
        userCoupon.setUsed(Boolean.TRUE);
        userCoupon.setUsedBy(uid);
        userCoupon.setUsedOccurTime(userCouponModel.getUsedOccurTime());
        userCoupon.setUsedSubmitTime(new Date());
        userCoupon = userCouponService.save(userCoupon).get();

        UserCouponModel model = beanMapper.map(userCoupon, UserCouponModel.class);
        return model;
    }
}
