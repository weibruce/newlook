/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * Rule entity.
 */
@Entity
@Table(name = "t_biz_rule")
public class Rule extends BusinessEntity {

    /* fields -------------------------------------------------------------- */

    @Column(name = "name", length = 128)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "coupon_oid")
    private Coupon coupon;

    @Column(name = "event_oid")
    private Long eventOid;

    @Column(name = "target_day")
    private Date targetDay;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "probability", precision = 10, scale = 4)
    private BigDecimal probability;

    @Column(name = "day_capacity")
    private Integer dayCapacity;

    @Column(name = "day_am_capacity")
    private Integer dayAmCapacity;

    @Column(name = "day_pm_capacity")
    private Integer dayPmCapacity;

    @Column(name = "enabled")
    private Boolean enabled;

    /* getters/setters ----------------------------------------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Long getEventOid() {
        return eventOid;
    }

    public void setEventOid(Long eventOid) {
        this.eventOid = eventOid;
    }

    public Date getTargetDay() {
        return targetDay;
    }

    public void setTargetDay(Date targetDay) {
        this.targetDay = targetDay;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public Integer getDayCapacity() {
        return dayCapacity;
    }

    public void setDayCapacity(Integer dayCapacity) {
        this.dayCapacity = dayCapacity;
    }

    public Integer getDayAmCapacity() {
        return dayAmCapacity;
    }

    public void setDayAmCapacity(Integer dayAmCapacity) {
        this.dayAmCapacity = dayAmCapacity;
    }

    public Integer getDayPmCapacity() {
        return dayPmCapacity;
    }

    public void setDayPmCapacity(Integer dayPmCapacity) {
        this.dayPmCapacity = dayPmCapacity;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
