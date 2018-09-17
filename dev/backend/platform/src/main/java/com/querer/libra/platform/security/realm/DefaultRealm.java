/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.realm;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.querer.libra.platform.security.authc.DefaultAuthenticationToken;
import com.querer.libra.platform.security.entity.Permission;
import com.querer.libra.platform.security.entity.Role;
import com.querer.libra.platform.security.entity.Subject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This default realm can be used in most of the cases when credentials are
 * going to be matched at local, no matter the credentials are stored in
 * local/remote.
 * <p>
 * The subclass inherited from this DefaultRealm needs to provide the
 * implementation of method {#loadSubjectFromStore()} to load user principals
 * and credentials. The loading logic is up to its situation, either local data
 * source or remote user repository. Then it's in charge of constructing
 * authentication info for later authentication and permission collection for
 * authorization.
 */
public abstract class DefaultRealm extends AuthorizingRealm {

    /* constructors ------------------------------------------------------ */

    /**
     * subclass also needs to call this super constructor and provide its own
     * constructor.
     */
    public DefaultRealm() {
        // subclass also needs to provide its unique realm name
        setName("DefaultRealm");
        // subclass also needs to provide the token this realm supports
        setAuthenticationTokenClass(DefaultAuthenticationToken.class);
        /**
         * one more thing about the credential matcher, it's injected via spring
         * xml configuration, see more details in shiro-security.xml
         */
    }

    /*
     * protected methods ------------------------------------------------------
     */

    /**
     * Subclass of DefaultRealm must implement this method to provide the logic
     * how to obtain user security information ,like principals and credentials
     * loading from local db or remote LDAP/ADAM servers.
     * <p>
     * In the case that credentials match in local, subclass only needs to
     * implement this method, other things are handled by this default realm.
     *
     * @param principal
     *            the principal of user in pass-by token
     * @return the subject load from datasource with pass-by principal
     */
    protected abstract Subject loadSubjectFromStore(Object principal);

    /**
     * AuthenticationInfo is collected in this method by each specific realm.
     * How to load user info with pass-by principal from datasource and
     * construct the Shiro AuthenticationInfo giving back to Shiro for
     * authentication.
     *
     * @param authcToken
     *            the token with user pass-by principals and credentials info
     *            from client
     * @return the user authentication info stored in datasource, it'll be used
     *         for further authentication and authorization
     * @throws AuthenticationException
     *             any exception during this process cause authentication fail
     *             and will be wrapped as AuthenticationException and throw out
     * @see AuthorizingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        Object principal = authcToken.getPrincipal();
        Subject subject = loadSubjectFromStore(principal);

        if (subject != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(subject.getPrincipals(),
                    subject.getCredentials(), getName());
            return info;
        } else {
            return null;
        }
    }

    /**
     * AuthorizationInfo (roles and permissions) is collected here for target
     * subject to check whether subject has authorization to access specific
     * resource.
     * <p>
     * Ideally speaking, this method doesn't care where the authorization info
     * is loaded from, local or remote. Because Role and Permission is modeled
     * in local, its subclass will get the authorization info from local
     * database. So if refine role/permission domain, it's also not quit a lot
     * effort to support load external authorization info.
     *
     * @param principals
     *            the principals of target subject
     * @return the roles and permissions detail info of target subject
     * @see AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // if multiple realms used, each realm will be checked here to authorize
        // principals; so it may return empty
        // collection here. If empty collection, means this realm is not
        // relevant realm for current principal; just
        // ignore it and pass to next realm for checking. No idea why Shiro
        // doesn't check token class and use right
        // realm to check authority only.
        Collection principalCollection = principals.fromRealm(getName());
        if (CollectionUtils.isEmpty(principalCollection)) {
            return null;
        }

        Object principal = principalCollection.iterator().next();
        // TODO this method was called in doGetAuthenticationInfo once. Is it
        // possible cache the user subject?
        Subject subject = loadSubjectFromStore(principal);

        if (subject != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // collect User level roles
            collectRoleAmdPermission(info, subject.getRoles());
            return info;
        } else {
            return null;
        }
    }

    /**
     * Collect all permissions in role list and add to AuthorizationInfo.
     *
     * @param info
     *            the authorization info to add permission
     * @param roles
     *            the role list containing permissions
     */
    protected void collectRoleAmdPermission(SimpleAuthorizationInfo info, Set<Role> roles) {
        for (Role role : roles) {
            info.addRole(role.getName());
            Collection<String> permissions = new HashSet<String>();
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getName());
            }
            info.addStringPermissions(permissions);
        }
    }

}