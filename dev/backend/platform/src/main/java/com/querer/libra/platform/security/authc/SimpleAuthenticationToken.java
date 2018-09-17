/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.authc;

/**
 * Authentication token for classic username and password.
 * <p>
 * It's simple and has both embedded principal and credential in it. Username is
 * the user principal and Password is the user credential.
 */
public class SimpleAuthenticationToken implements DefaultAuthenticationToken {

    /* fields ------------------------------------------------------ */

    private String username;
    private String password;

    /* constructors ------------------------------------------------------ */

    public SimpleAuthenticationToken() {
    }

    public SimpleAuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /* public methods ------------------------------------------------------ */

    /**
     * Return username as principal.
     *
     * @return the username, a simple string as the principal for this token
     */
    @Override
    public Object getPrincipal() {
        return this.username;
    }

    /**
     * Return password as its credential.
     *
     * @return the password, a simple string as the credential for this token
     */
    @Override
    public Object getCredentials() {
        return this.password;
    }

    /* getters/setters ------------------------------------------------------ */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
