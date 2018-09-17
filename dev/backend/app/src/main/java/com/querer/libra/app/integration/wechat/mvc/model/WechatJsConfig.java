/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/
package com.querer.libra.app.integration.wechat.mvc.model;

import java.util.List;

/**
 * class description goes here.
 */
public class WechatJsConfig {

    /* fields -------------------------------------------------------------- */

    private String debug;
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;
    private List<String> jsApiList;

    /* public methods ------------------------------------------------------ */

    @Override
    public String toString() {
        return "WechatJsConfig{" +
                "debug='" + debug + '\'' +
                ", appId='" + appId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", signature='" + signature + '\'' +
                ", jsApiList=" + jsApiList +
                '}';
    }

    /* getters/setters ----------------------------------------------------- */

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(List<String> jsApiList) {
        this.jsApiList = jsApiList;
    }
}