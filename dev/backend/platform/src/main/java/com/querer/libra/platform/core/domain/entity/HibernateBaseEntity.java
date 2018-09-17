/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base entity with version enabled and extends Number base primary key from
 * GenericEntity.
 */
@MappedSuperclass
public abstract class HibernateBaseEntity extends BaseEntity {

    /* fields ------------------------------------------------------ */
    @Version
    @Column(name = "version", nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer version;

    /* getters/setters ------------------------------------------------------ */
    @JsonIgnore
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
