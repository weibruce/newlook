/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * Coupon entity.
 */
@Entity
@Table(name = "t_biz_coupon")
public class Coupon extends BusinessEntity {

    /* fields -------------------------------------------------------------- */

    @Column(name = "name", length = 128)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "event_oid")
    private Event event;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
