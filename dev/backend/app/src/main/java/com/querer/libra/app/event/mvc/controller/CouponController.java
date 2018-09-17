/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querer.libra.app.event.domain.model.UserCouponModel;
import com.querer.libra.app.event.service.business.UserCouponBizService;
import com.querer.libra.app.event.util.AppConstants;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    /* fields -------------------------------------------------------------- */

    private final static Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private UserCouponBizService userCouponBizService;

    /* public methods ------------------------------------------------------ */

    @RequestMapping(value = "/draw", method = RequestMethod.POST)
    public UserCouponModel postDrawCoupon(@RequestBody UserCouponModel userCouponModel, HttpServletRequest request) {
        // validate open id first
        String openId = (String) request.getSession().getAttribute(AppConstants.SESSION_KEY_OPENID);
        logger.info("open id: {}", openId);
        if (StringUtils.isBlank(openId)) {
            // if no openid found in session when draw, return null; or throw biz exception.
            return null;
        }
        return userCouponBizService.doDrawCoupon(userCouponModel);
    }

    @RequestMapping(value = "/{openingCode}", method = RequestMethod.GET)
    public List<UserCouponModel> getQueryUserCoupon(@PathVariable String openingCode, @RequestParam(value = "uid") String uid, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate, no-store");
        return userCouponBizService.findUserCoupon(openingCode, uid);
    }

    @RequestMapping(value = "/use", method = RequestMethod.PUT)
    public UserCouponModel putUseCoupon(@RequestBody UserCouponModel userCouponModel) {
        return userCouponBizService.doUseCoupon(userCouponModel);
    }

}
