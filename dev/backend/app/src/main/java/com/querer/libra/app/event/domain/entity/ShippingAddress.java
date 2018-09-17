/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * ShippingAddress entity.
 */
@Entity
@Table(name = "t_biz_shipping_address")
public class ShippingAddress extends BusinessEntity {

    /* fields -------------------------------------------------------------- */

    @Column(name = "user_coupon_oid")
    private String userCouponOid;
    @Column(name = "uid", length = 256)
    private String uid;

    @Column(name = "name", length = 128)
    private String name;
    @Column(name = "cellphone", length = 32)
    private String cellphone;
    @Column(name = "address", length = 256)
    private String address;

    @Column(name = "submit_timestamp")
    private Date submitTimestamp;

    @Column(name = "sent")
    private Boolean sent;
    @Column(name = "sent_timestamp")
    private Date sentTimestamp;

    /* getters/setters ----------------------------------------------------- */

    public String getUserCouponOid() {
        return userCouponOid;
    }

    public void setUserCouponOid(String userCouponOid) {
        this.userCouponOid = userCouponOid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getSubmitTimestamp() {
        return submitTimestamp;
    }

    public void setSubmitTimestamp(Date submitTimestamp) {
        this.submitTimestamp = submitTimestamp;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Date getSentTimestamp() {
        return sentTimestamp;
    }

    public void setSentTimestamp(Date sentTimestamp) {
        this.sentTimestamp = sentTimestamp;
    }
}
