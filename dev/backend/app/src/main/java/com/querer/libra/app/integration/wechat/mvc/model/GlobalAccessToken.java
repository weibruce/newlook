/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/
package com.querer.libra.app.integration.wechat.mvc.model;

/**
 * class description goes here.
 */
public class GlobalAccessToken {

    /* fields -------------------------------------------------------------- */

    private String access_token;
    private String expires_in;

    /* public methods ------------------------------------------------------ */

    @Override
    public String toString() {
        return "GlobalAccessToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }

    /* getters/setters ----------------------------------------------------- */

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}