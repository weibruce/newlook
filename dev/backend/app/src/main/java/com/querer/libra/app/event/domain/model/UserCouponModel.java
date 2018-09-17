/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.model;

import java.util.Date;

import com.querer.libra.platform.core.domain.model.common.BaseModel;

public class UserCouponModel extends BaseModel {

    /* fields -------------------------------------------------------------- */

    private String uid;

    private CouponModel coupon;
    private EventModel event;

    private String barcode;

    private Date occurTime;
    private Date submitTime;

    private Date startTime;
    private Date endTime;

    private Boolean used;
    private String usedBy;
    private Date usedOccurTime;
    private Date usedSubmitTime;

    /* getters/setters ----------------------------------------------------- */

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public CouponModel getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponModel coupon) {
        this.coupon = coupon;
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public Date getUsedOccurTime() {
        return usedOccurTime;
    }

    public void setUsedOccurTime(Date usedOccurTime) {
        this.usedOccurTime = usedOccurTime;
    }

    public Date getUsedSubmitTime() {
        return usedSubmitTime;
    }

    public void setUsedSubmitTime(Date usedSubmitTime) {
        this.usedSubmitTime = usedSubmitTime;
    }
}
