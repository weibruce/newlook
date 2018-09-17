/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * BaseEntity with primary key as simple UUID generation strategy.
 */
@MappedSuperclass
public class BaseUuidEntity implements Serializable {

    /* fields ------------------------------------------------------ */
    private static final long serialVersionUID = 6741464485141300779L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "oid", unique = true, nullable = false, length = 128)
    protected String oid;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer version;

    /* getters/setters ------------------------------------------------------ */
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
