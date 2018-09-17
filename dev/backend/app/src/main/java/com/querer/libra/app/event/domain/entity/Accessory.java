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
 * Accessory entity.
 */
@Entity
@Table(name = "t_biz_accessory")
public class Accessory extends BusinessEntity {

    /* fields -------------------------------------------------------------- */

    @Column(name = "uid", length = 256)
    private String uid;

    @Column(name = "event_oid")
    private Long eventOid;
    @Column(name = "coupon_oid")
    private Long couponOid;
    @Column(name = "rule_oid")
    private Long ruleOid;

    @Column(name = "content", length = 512)
    private String content;
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "claimed")
    private Boolean claimed;
    @Column(name = "claimed_time")
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
