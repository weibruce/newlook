/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.authz;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.authz.permission.PermissionResolver;

/**
 * AllPermissionResolver.java. Allows all permission authorized. Always return
 * true when check permission. It requires Subject must have one permission.
 * Empty Permission List of Subject cannot pass this the secured checking due to
 * current Shiro implementation, please assign at least one permission to target
 * subject.
 */
public class AllPermissionResolver implements PermissionResolver {

    /* public methods ------------------------------------------------------ */

    /**
     * Return AllPermission to make checking always true.
     */
    public Permission resolvePermission(String permissionString) {
        return new AllPermission();
    }
}
