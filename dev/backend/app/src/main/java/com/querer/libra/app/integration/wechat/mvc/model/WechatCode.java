/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/
package com.querer.libra.app.integration.wechat.mvc.model;

public class WechatCode {

    /* fields -------------------------------------------------------------- */

    private String code;
    private String state;
    private String openingCode;

    /* public methods ------------------------------------------------------ */

    @Override
    public String toString() {
        return "WechatCode{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                ", openingCode='" + openingCode + '\'' +
                '}';
    }

   /* getters/setters ----------------------------------------------------- */

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOpeningCode() {
        return openingCode;
    }

    public void setOpeningCode(String openingCode) {
        this.openingCode = openingCode;
    }
}