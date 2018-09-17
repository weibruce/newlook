/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/
package com.querer.libra.app.integration.wechat.service;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.integration.wechat.mvc.model.WebAccessToken;
import com.querer.libra.app.integration.wechat.mvc.model.WechatJsConfig;

@Validated
public interface WechatIntegrationService {

    String getCodeUrl(@NotNull String responseType, String openingCode, String state);

    WebAccessToken getWebAccessToken(@NotNull String code);

    WechatJsConfig getWechatJsConfig(@NotNull String url);

    void refreshGlobalAccessToken();

    //void refreshAccessToken();

    void refreshJsApiTicket();


}