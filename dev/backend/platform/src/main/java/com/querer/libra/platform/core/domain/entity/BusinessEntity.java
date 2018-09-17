/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Generic entity that with a unique ID only.
 * <p>
 * Reference: https://dzone.com/articles/hibernate-identity-sequence
 * <p>
 * MySQL not support sequence and GUID not good for debug, so numeric primary
 * key falls to IDENTIFY.
 * <p>
 * IDENTIFY has below cons: - Hibernates disables the JDBC batch support for
 * entities using the IDENTITY generator - the IDENTITY generator strategy
 * doesn’t work with the Table per concrete class inheritance model
 * <p>
 * <p>
 * Mimic sequence generation will intro db table which is not db independent.
 * When change to other DB engines, considering SEQUENCE primary key generation.
 */
@MappedSuperclass
public abstract class BusinessEntity extends BaseAuditableEntity {

    /* fields ------------------------------------------------------ */
    private static final long serialVersionUID = 3698926833107839174L;

    // simple numeric ID generation strategy.
    @Id
    //    @GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid", unique = true, nullable = false)
    protected Long oid;

    /*
     * set active to true by default to avoid cascade insert operation issue.
     * 
     * cascade create/insert operation cannot set cascaded entities active flag 
     * to true by default. So assume that all entity active flag is true by 
     * default in create/insert operations.
     */
    @Column(name = "active")
    private Boolean active = true;

    /* getters/setters ------------------------------------------------------ */

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
