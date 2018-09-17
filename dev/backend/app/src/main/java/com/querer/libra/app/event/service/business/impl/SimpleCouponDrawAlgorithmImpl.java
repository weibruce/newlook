/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.querer.libra.app.event.domain.entity.Event;
import com.querer.libra.app.event.domain.entity.Rule;
import com.querer.libra.app.event.domain.entity.SharedEvent;
import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.app.event.exception.ExhaustedDrawChancesException;
import com.querer.libra.app.event.service.atom.RuleService;
import com.querer.libra.app.event.service.atom.SharedEventService;
import com.querer.libra.app.event.service.business.CouponDrawAlgorithm;
import com.querer.libra.app.event.service.business.UserCouponQueryService;
import com.querer.libra.app.event.util.DateTimeUtil;

public class SimpleCouponDrawAlgorithmImpl implements CouponDrawAlgorithm {

    /* fields -------------------------------------------------------------- */

    private final static Logger logger = LoggerFactory.getLogger(SimpleCouponDrawAlgorithmImpl.class);

    private final static int MAX_NUMBER = 10000;

    private int timesPerDay = 1;

    @Autowired
    private UserCouponQueryService userCouponQueryService;

    @Autowired
    private SharedEventService sharedEventService;

    @Autowired
    private RuleService ruleService;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<UserCoupon> drawCoupon(Event event, String uid) {
        if (event == null || StringUtils.isBlank(uid)) {
            return Optional.empty();
        }

        // check user opportunity first
        int times = this.timesPerDay;
        Date now = new Date();
        Date startDate = DateTimeUtil.getStartOfDay(now);
        Date endDate = DateTimeUtil.getEndOfDay(now);
        List<SharedEvent> sharedEventList = sharedEventService.findByUidAndBetweenDates(event.getOid(), uid, startDate, endDate);
        if (CollectionUtils.isNotEmpty(sharedEventList)) {
            times += 1;
        }

        List<UserCoupon> userCouponList = userCouponQueryService.findUserCouponsToday(event.getOid(), uid);
        if (times <= CollectionUtils.size(userCouponList)) {
            throw new ExhaustedDrawChancesException("user has tried out all the chances today.");
        }

        // load event and rules for today
        List<Rule> ruleList = ruleService.findRulesToday(event.getOid(), now);

        Rule hitRule = null;
        BigDecimal probability = BigDecimal.ZERO;
        BigDecimal hitRandomNumber = BigDecimal.ZERO;

        if (CollectionUtils.isNotEmpty(ruleList)) {
            // IMPORTANT! filter number "1"
            int randomNumber = MAX_NUMBER;
            do {
                randomNumber = ThreadLocalRandom.current().nextInt(1, MAX_NUMBER);
            } while (randomNumber == 1);
            // calc probability
            BigDecimal hitNumber = new BigDecimal(randomNumber).divide(new BigDecimal(MAX_NUMBER));

            //logger.debug("user {} draw coupon with hit rate number: {} ", uid, hitNumber);

            // match
            for (Rule rule : ruleList) {
                BigDecimal ruleProbability = rule.getProbability();
                if (ruleProbability != null) {
                    probability = probability.add(ruleProbability);
                    if (hitNumber.compareTo(probability) > 0) {
                        continue;
                    } else {
                        // check constraints
                        if (isMatchRuleConstraints(rule)) {
                            hitRule = rule;
                            hitRandomNumber = hitNumber;
                            break;
                        }
                    }
                }
            }
        }

        // hit coupons
        UserCoupon userCoupon = null;
        if (hitRule != null) {
            userCoupon = new UserCoupon();
            userCoupon.setUid(uid);
            userCoupon.setEventOid(event.getOid());
            userCoupon.setRuleOid(hitRule.getOid());
            userCoupon.setHitRandomNumber(hitRandomNumber);
            userCoupon.setCoupon(hitRule.getCoupon());

            //logger.info("draw user coupon: rule={}, hitRandomNumber={}, coupon={}", hitRule.getOid(), hitRandomNumber, hitRule.getCoupon().getName());
        }

        return Optional.ofNullable(userCoupon);
    }

    /* private methods ----------------------------------------------------- */

    private boolean isMatchRuleConstraints(Rule rule) {
        boolean result = true;

        // process day capacity first
        Date now = new Date();
        Date startDate = DateTimeUtil.getStartOfDay(now);
        Date endDate = DateTimeUtil.getEndOfDay(now);
        // TODO need sync protection here, there is little ratio that concurrent issue occur
        result = validateCapacity(rule.getOid(), rule.getDayCapacity(), startDate, endDate);
        // if total user coupons exceeds day capacity, then end
        if (!result) {
            return result;
        }

        if (rule.getDayAmCapacity() != null || rule.getDayPmCapacity() != null) {
            LocalDateTime nowLdt = DateTimeUtil.dateToLocalDateTime(now);
            if (rule.getDayAmCapacity() != null) {
                if (nowLdt.getHour() <= 12) {
                    Date middayDate = getMiddayDate();
                    // AM
                    result = validateCapacity(rule.getOid(), rule.getDayAmCapacity(), startDate, middayDate);
                }
            }

            if (rule.getDayPmCapacity() != null) {
                if (nowLdt.getHour() > 12) {
                    Date middayDate = getMiddayDate();
                    // PM
                    result = validateCapacity(rule.getOid(), rule.getDayPmCapacity(), middayDate, endDate);
                }
            }
        }

        return result;
    }

    private Date getMiddayDate() {
        LocalDate today = LocalDate.now();
        LocalTime midday = LocalTime.of(12, 0); // 12:00
        LocalDateTime middayLdt = LocalDateTime.of(today, midday);
        Date middayDate = DateTimeUtil.localDateTimeToDate(middayLdt);
        return middayDate;
    }

    private boolean validateCapacity(Long ruleOid, Integer capacityNumber, Date startDate, Date endDate) {
        boolean result = true;
        Long fullDayCountNumber = userCouponQueryService.countUserCouponsBetweenRange(ruleOid, startDate, endDate);
        if (capacityNumber != null) {
            // if total user coupons exceeds capacity, then end
            if (NumberUtils.compare(capacityNumber, fullDayCountNumber) <= 0) {
                result = false;
            }
        }
        return result;
    }

    /* getters/setters ----------------------------------------------------- */

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }
}
