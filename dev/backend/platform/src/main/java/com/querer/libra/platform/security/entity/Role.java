/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity, represents the role user plays in the app.
 * <p>
 * Both Subject and Organization are designed to assign varies of roles to get
 * basePermissions, so that they get authorized to access resources. Subject and
 * Organization are not supported to assigned permission directly. Permission
 * cannot only be granted via Role only.
 */
@Entity
@Table(name = "t_security_role")
public class Role extends BusinessEntity {

    /* fields ------------------------------------------------------ */

    @Column(name = "name", length = 32, unique = true, nullable = false)
    private String name;
    @Column(name = "description", length = 128)
    private String description;

    // Permissions granted to this role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tr_security_role_permission", joinColumns = @JoinColumn(name = "role_oid"), inverseJoinColumns = @JoinColumn(name = "permission_oid"))
    private Set<Permission> permissions = new HashSet<>(0);

    // The subjects having this role
    @ManyToMany(mappedBy = "roles")
    private Set<Subject> subjects = new HashSet<>(0);

    /* getters/setters ------------------------------------------------------ */

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
