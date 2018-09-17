/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.authc.credential;

import com.querer.libra.platform.security.authc.DefaultAuthenticationToken;
import com.querer.libra.platform.security.authc.DelegateAuthenticationInfo;
import com.querer.libra.platform.security.authc.DelegateAuthenticationToken;
import com.querer.libra.platform.security.entity.Credential;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * Default implementation of hash credential matcher.
 * <p>
 * It's right now just a sample since Shiro v1.2.1 provides a good utility class
 * {@link org.apache.shiro.authc.credential.PasswordService}. PasswordService
 * can work well in most of the normal case.
 * <p>
 * But in the case that PasswordService cannot satisfy our demands, we need to
 * have our own credential matcher implemented. So here provides a sample usage.
 * When we have external principals and credentials to deal with, and more
 * having our own credential matching mechanism, we need to implement the
 * matching approach by our own as in this class (or inherit from the closest
 * CredentialMatcher).
 */
public class DefaultCredentialsMatcher extends HashedCredentialsMatcher {

    /* public methods ------------------------------------------------------ */

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // make sure the token and authentication info are supported
        DefaultAuthenticationToken authenticationToken = (DefaultAuthenticationToken) token;
        Credential securityCredentials = (Credential) info.getCredentials();

        // Prepare the credentials in token.
        // Because the credential details are stored in an externalized POJO
        // (Credential). In order to compatible
        // with Shiro matching mechanism, use DelegateAuthenticationToken to
        // wrap the real fields of credential then
        // use this delegate token to do matching jobs.
        DelegateAuthenticationToken delegateToken = new DelegateAuthenticationToken(
                authenticationToken.getCredentials());
        Object tokenHashedCredentials = hashProvidedCredentials(delegateToken, info);

        // The delegate for the credential in AuthenticationInfo does the same
        // thing as above credential case.
        AuthenticationInfo delegateInfo = new DelegateAuthenticationInfo(securityCredentials.fetchCredential());
        Object accountCredentials = getCredentials(delegateInfo);

        // match the credentials in token and info
        return equals(tokenHashedCredentials, accountCredentials);
    }

}
