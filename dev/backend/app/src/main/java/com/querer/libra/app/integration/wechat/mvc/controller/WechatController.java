/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.integration.wechat.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.querer.libra.app.event.util.AppConstants;
import com.querer.libra.app.integration.wechat.mvc.model.WebAccessToken;
import com.querer.libra.app.integration.wechat.mvc.model.WechatCode;
import com.querer.libra.app.integration.wechat.mvc.model.WechatJsConfig;
import com.querer.libra.app.integration.wechat.service.WechatIntegrationService;
import com.querer.libra.platform.core.spring.rest.annotation.RestPayload;

@RestController
@RequestMapping("/wechat")
public class WechatController {

    /* fields -------------------------------------------------------------- */

    private final static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatIntegrationService wechatIntegrationService;

    /* public methods ------------------------------------------------------ */

    @RequestMapping("/from")
    @RestPayload(rawMode = true)
    public ModelAndView getToken(String openingCode, String state) {
        String responseType = "code";
        String redirectUrl = wechatIntegrationService.getCodeUrl(responseType, openingCode, state);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @RequestMapping("/access-token")
    @RestPayload(rawMode = true)
    public ModelAndView getWebAccessToken(WechatCode wechatCode, HttpServletRequest request) {
        WebAccessToken webAccessToken = wechatIntegrationService.getWebAccessToken(wechatCode.getCode());

        String redirectUrl = AppConstants.SITE_DOMAIN;
        if (webAccessToken != null) {
            String openId = webAccessToken.getOpenid();
            if (openId == null) {
                throw new RuntimeException("open id is null");
            }

            HttpSession session = request.getSession();

            session.setAttribute(AppConstants.SESSION_KEY_OPENID, webAccessToken.getOpenid());
            //logger.info("AppConstants.SESSION_KEY_OPENID : {}", webAccessToken.getOpenid());

            //redirectUrl = redirectUrl + "?openid=" + openId;
        }

        String openingCode = wechatCode.getOpeningCode();
        String fromState = wechatCode.getState();
        if (StringUtils.isNotBlank(fromState) && StringUtils.isNotBlank(fromState)) {
            redirectUrl = new StringBuilder(redirectUrl).append("?openingCode=").append(openingCode).append("&from=").append(fromState).toString();
        }

        //logger.info("home url: {}", redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @RequestMapping("/jsconfig")
    public WechatJsConfig getWechatJsConfig(@RequestParam(name = "url") String url, HttpServletRequest request) {
        // get full request url
        StringBuilder fullRequestUrl = new StringBuilder();
        fullRequestUrl.append(url);
        //logger.info("fullRequestUrl: {}", fullRequestUrl.toString());

        WechatJsConfig wechatJsConfig = wechatIntegrationService.getWechatJsConfig(fullRequestUrl.toString());
        return wechatJsConfig;
    }
}
