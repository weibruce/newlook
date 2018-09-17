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
import javax.persistence.Table;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * Permission entity. Generally permission is string based.
 * <p>
 * Since this security is based on Apache Shiro, the default shiro permission
 * string format like "user:create" is used. More details please refer:
 * http://shiro.apache.org/permissions.html
 * <p>
 * For simple and clean, multi-parts permission definition is not recommended
 * and should not used in product development. So "user:view, update" is
 * disallowed but define two string permission "user:view" and "user:update" to
 * get 2 permissions. Multi-parts permission has parsed issue in Shiro v1.2.1,
 * again,avoid using it for now.
 * <p>
 * Shiro WildcardPermission is used by default. If individual system wants to
 * define its own permission format, it's also very low effort to do that - just
 * define itw own permission and permission resolver that inject the Shiro
 * SecurityManager. More details about custom permission please refer:
 *
 * @see org.apache.shiro.authz.permission.WildcardPermission
 * @see org.apache.shiro.authz.permission.WildcardPermissionResolver
 */
@Entity
@Table(name = "t_security_permission")
public class Permission extends BusinessEntity {

    /* fields ------------------------------------------------------ */

    @Column(name = "name", length = 32, unique = true, nullable = false)
    private String name;
    @Column(name = "description", length = 128)
    private String description;

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
}
