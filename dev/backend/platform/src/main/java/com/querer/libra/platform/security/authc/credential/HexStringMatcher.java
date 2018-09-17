/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.authc.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.PasswordMatcher;

import com.querer.libra.platform.security.authc.DefaultAuthenticationToken;
import com.querer.libra.platform.security.entity.Credential;

/**
 * A simple matcher based on PasswordMatcher and PasswordService integrated with
 * external credential storage. Since PasswordService provides a handy approach
 * to hash one string field, encode/decode and compare, it's quite good to
 * utilize existing PasswordService for single field matching.
 */
public class HexStringMatcher extends PasswordMatcher {

    /* protect methods ------------------------------------------------------ */

    /**
     * Get the credential in CiCredential in token to do matching.
     * PasswordMatcher will hash it with same algorithm stored in datasource,
     * then use it as the credential in token to compare with the credential
     * stored in AuthenticationInfo.
     *
     * @param token
     *            the pass-by token having the credential filled in by client
     * @return the actual credential fields in CiCredential
     */
    @Override
    protected Object getSubmittedPassword(AuthenticationToken token) {
        DefaultAuthenticationToken ciToken = (DefaultAuthenticationToken) token;
        return ciToken != null ? ciToken.getCredentials() : null;
    }

    /**
     * Get the credential in CiCredential in storage to do matching.
     *
     * @param storedAccountInfo
     *            the stored authentication info
     * @return the field of credential defined in CiCredential
     */
    @Override
    protected Object getStoredPassword(AuthenticationInfo storedAccountInfo) {
        Object stored = null;
        if (storedAccountInfo != null) {
            Credential credentials = (Credential) storedAccountInfo.getCredentials();
            stored = credentials.fetchCredential();
            if (stored instanceof char[]) {
                stored = new String((char[]) stored);
            }
        }
        return stored;
    }
}
