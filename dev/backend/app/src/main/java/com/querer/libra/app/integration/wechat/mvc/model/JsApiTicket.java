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
public class JsApiTicket {

    /* fields -------------------------------------------------------------- */

    private String errcode;
    private String errmsg;
    private String ticket;
    private String expires_in;

    /* public methods ------------------------------------------------------ */

    @Override
    public String toString() {
        return "JsApiTicket{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", ticket='" + ticket + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }

    /* getters/setters ----------------------------------------------------- */

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}