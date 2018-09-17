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

public class AccessoryModel extends BaseModel {

    /* fields -------------------------------------------------------------- */

    private String uid;

    private Long eventOid;
    private Long couponOid;
    private Long ruleOid;

    private String content;
    private Integer quantity;

    private Boolean claimed;
    private Date claimedTime;

    /* getters/setters ----------------------------------------------------- */

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getEventOid() {
        return eventOid;
    }

    public void setEventOid(Long eventOid) {
        this.eventOid = eventOid;
    }

    public Long getCouponOid() {
        return couponOid;
    }

    public void setCouponOid(Long couponOid) {
        this.couponOid = couponOid;
    }

    public Long getRuleOid() {
        return ruleOid;
    }

    public void setRuleOid(Long ruleOid) {
        this.ruleOid = ruleOid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }

    public Date getClaimedTime() {
        return claimedTime;
    }

    public void setClaimedTime(Date claimedTime) {
        this.claimedTime = claimedTime;
    }
}
