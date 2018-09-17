/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.integration.wechat.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.querer.libra.app.event.util.AppConstants;
import com.querer.libra.app.integration.wechat.mvc.model.GlobalAccessToken;
import com.querer.libra.app.integration.wechat.mvc.model.JsApiTicket;
import com.querer.libra.app.integration.wechat.mvc.model.WebAccessToken;
import com.querer.libra.app.integration.wechat.mvc.model.WechatJsConfig;
import com.querer.libra.app.integration.wechat.service.WechatIntegrationService;
import com.querer.libra.platform.code.UniqueCodeGenerator;

@Service
public class WechatIntegrationServiceImpl implements WechatIntegrationService {

    /* fields -------------------------------------------------------------- */

    private final static Logger logger = LoggerFactory.getLogger(WechatIntegrationServiceImpl.class);

    private static GlobalAccessToken globalAccessTokenObject;
    private static String globalAccessTokenString;
    //private static String refreshTokenString;

    private static JsApiTicket jsApiTicketObject;
    private static String jsApiTicketString;

    @Value("${wechat.newlook.app.id}")
    private String appId;
    //private String appId = "wx49664e1ce4ad153e";

    @Value("${wechat.newlook.app.secret}")
    private String appSecret;
    //private String appSecret = "0a0d51f77a7bbcd239d42912223130a9";

    @Autowired
    private UniqueCodeGenerator uniqueCodeGenerator;

    private RestTemplate restTemplate = getRestTemplate();

    private static final List<String> jsApiList = Arrays.asList("onMenuShareTimeline", "onMenuShareAppMessage");

    /* public methods ------------------------------------------------------ */

    @Override
    public String getCodeUrl(@NotNull String responseType, String openingCode, String state) {
        String redirectUrl = AppConstants.REDIRECT_URL;
        try {
            if (StringUtils.isNotBlank(openingCode)) {
                redirectUrl = new StringBuilder(AppConstants.REDIRECT_URL)
                        .append("?openingCode=")
                        .append(openingCode)
                        .toString();
            }
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (Exception e) {
            logger.error("encode url with exception: {}", e);
            return redirectUrl;
        }

        if (StringUtils.isBlank(state)) {
            state = StringUtils.EMPTY;
        }
        StringBuilder tokenUrl = new StringBuilder();
        tokenUrl
                .append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
                .append(appId)
                .append("&redirect_uri=")
                .append(redirectUrl)
                .append("&response_type=")
                .append(responseType)
                .append("&scope=")
                .append(AppConstants.SCOPE_BASE)
                .append("&state=")
                .append(state)
                .append("#wechat_redirect");

        String url = tokenUrl.toString();
        //logger.debug("url : {}", url);
        return url;
    }

    @Override
    public WebAccessToken getWebAccessToken(@NotNull String code) {
        // otherwise get a new access token from wechat
        StringBuilder accessTokenUrl = new StringBuilder();
        accessTokenUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(appId)
                .append("&secret=")
                .append(appSecret)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        WebAccessToken webAccessToken = sendUrlToGetWebAccessToken(accessTokenUrl.toString());
        return webAccessToken;
    }

    @Override
    public WechatJsConfig getWechatJsConfig(@NotNull String url) {
        WechatJsConfig wechatJsConfig = new WechatJsConfig();

        try {
            String jsApiTicket = getJsApiTicket();
            long configTimestamp = new Date().getTime();
            String nonceStr = uniqueCodeGenerator.generateCodeAlphanumeric(16);

            StringBuilder tobeSign = new StringBuilder();
            tobeSign.append("jsapi_ticket=").append(jsApiTicket)
                    .append("&noncestr=").append(nonceStr)
                    .append("&timestamp=").append(configTimestamp)
                    .append("&url=").append(url);
            String string1 = tobeSign.toString();
            logger.debug("string1: {}", string1);
            String signature = Hashing.sha1().hashString(string1, Charsets.UTF_8).toString();

            wechatJsConfig.setDebug("false");
            wechatJsConfig.setAppId(appId);
            wechatJsConfig.setTimestamp(String.valueOf(configTimestamp));
            wechatJsConfig.setNonceStr(nonceStr);
            wechatJsConfig.setSignature(signature);
            wechatJsConfig.setJsApiList(jsApiList);
        } catch (Exception e) {
            logger.error("get JsApiTicket with exception: {}", e);
        }

        return wechatJsConfig;
    }

    @Override
    public void refreshGlobalAccessToken() {
        // if refresh global token is not null
        logger.info("refresh global token process begins.");

        // otherwise get a new global access token
        StringBuilder globalAccessTokenUrl = new StringBuilder();
        globalAccessTokenUrl
                .append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=")
                .append(appId)
                .append("&secret=")
                .append(appSecret);

        initializeGlobalAccessToken(globalAccessTokenUrl.toString());
    }

    @Override
    public void refreshJsApiTicket() {
        // if refresh token is not null
        logger.info("refresh js api ticket process begins.");
        if (StringUtils.isBlank(globalAccessTokenString)) {
            logger.info("global access token is blank : {}", globalAccessTokenString);
            return;
        }

        // otherwise get a new js api ticket from wechat
        initializeJsApiTicket();
    }

    /* private methods ----------------------------------------------------- */

    private GlobalAccessToken initializeGlobalAccessToken(String url) {
        GlobalAccessToken globalAccessToken = null;
        try {
            globalAccessToken = restTemplate.getForObject(url.toString(), GlobalAccessToken.class);
            logger.info("globalAccessToken : {}", globalAccessToken);

            // cache access token
            if (globalAccessToken != null) {
                globalAccessTokenObject = globalAccessToken;
                globalAccessTokenString = globalAccessToken.getAccess_token();
            }
        } catch (Exception e) {
            logger.error("get global access token with exception: {}", e);
        }

        return globalAccessToken;
    }

    private WebAccessToken sendUrlToGetWebAccessToken(String url) {
        WebAccessToken webAccessToken = null;
        try {
            webAccessToken = restTemplate.getForObject(url.toString(), WebAccessToken.class);
            //logger.info("webAccessToken : {}", webAccessToken);
        } catch (Exception e) {
            logger.error("get access token with exception: {}", e);
        }

        return webAccessToken;
    }

    private String getJsApiTicket() {
        if (jsApiTicketString != null) {
            return jsApiTicketString;
        }

        return initializeJsApiTicket();
    }

    private String initializeJsApiTicket() {
        if (StringUtils.isBlank(globalAccessTokenString)) {
            return StringUtils.EMPTY;
        }

        StringBuilder jsApiTicketUrl = new StringBuilder();
        jsApiTicketUrl
                .append("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=")
                .append(globalAccessTokenString)
                .append("&type=jsapi");

        try {
            JsApiTicket jsApiTicket = restTemplate.getForObject(jsApiTicketUrl.toString(), JsApiTicket.class);
            logger.info("jsApiTicket : {}", jsApiTicket);

            // cache access token
            if (jsApiTicket != null) {
                jsApiTicketObject = jsApiTicket;
                jsApiTicketString = jsApiTicket.getTicket();
            }
        } catch (Exception e) {
            logger.error("get JsApiTicket with exception: {}", e);
        }

        return jsApiTicketString;
    }

    private RestTemplate getRestTemplate() {
        this.restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour

        //converter.setSupportedMediaTypes(Arrays.asList({MediaType.ALL}));
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(new MediaType("application", "*+json"));
        mediaTypes.add(MediaType.TEXT_PLAIN);

        converter.setSupportedMediaTypes(mediaTypes);
        messageConverters.add(converter);

        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}
