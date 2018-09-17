/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.model;

import com.querer.libra.platform.core.domain.model.common.BaseModel;

public class ShippingAddressModel extends BaseModel {

    /* fields -------------------------------------------------------------- */

    private Long userCouponOid;
    private String uid;

    private String name;
    private String cellphone;
    private String address;

    private Boolean sent;

    /* getters/setters ----------------------------------------------------- */

    public Long getUserCouponOid() {
        return userCouponOid;
    }

    public void setUserCouponOid(Long userCouponOid) {
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

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }
}
