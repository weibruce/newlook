/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.authc;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Just a delegate in external AuthenticationInfo case. Fetch authentication
 * info from Authentication and construct this delegate then deliver it to
 * CredentialMatcher for comparison.
 */
public class DelegateAuthenticationInfo implements AuthenticationInfo {

    /* fields ------------------------------------------------------ */

    private Object credentials;

    /* constructors ------------------------------------------------------ */

    public DelegateAuthenticationInfo(Object credentials) {
        this.credentials = credentials;
    }

    /* public methods ------------------------------------------------------ */

    @Override
    public PrincipalCollection getPrincipals() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
