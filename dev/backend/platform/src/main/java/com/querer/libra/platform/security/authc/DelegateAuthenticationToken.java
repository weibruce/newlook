/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.authc;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Just a delegate in external Authentication case. Fetch authentication info
 * from DefaultAuthenticationToken and construct this delegate then deliver it
 * to CredentialMatcher for comparison.
 */
public class DelegateAuthenticationToken implements AuthenticationToken {

    /* fields ------------------------------------------------------ */

    private Object credentials;

    /* constructors ------------------------------------------------------ */

    public DelegateAuthenticationToken(Object credentials) {
        this.credentials = credentials;
    }

    /* public methods ------------------------------------------------------ */

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
