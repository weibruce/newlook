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
 * Event entity.
 */
@Entity
@Table(name = "t_biz_event")
public class Event extends BusinessEntity {

    /* fields -------------------------------------------------------------- */

    @Column(name = "name", length = 128)
    private String name;

    @Column(name = "store_name", length = 128)
    private String storeName;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "opening_code", length = 128)
    private String openingCode;

    /* getters/setters ----------------------------------------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getOpeningCode() {
        return openingCode;
    }

    public void setOpeningCode(String openingCode) {
        this.openingCode = openingCode;
    }
}
