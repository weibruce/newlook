/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Security utils class to deal with security stuffs. It wraps with Shiro
 * security APIs and provides handy functions for external clients.
 */
public class SecurityContextHelper {

    /* fields -------------------------------------------------------------- */

    private final static Logger logger = LoggerFactory.getLogger(SecurityContextHelper.class);

    /* public methods ------------------------------------------------------ */

    /**
     * Get username (principal) of current login user.
     *
     * @return the username in current session
     */
    public static String getCurrentLoginUsername() {
        String principal = null;
        try {
            if (SecurityContextHelper.isAuthenticated()) {
                Object primaryPrincipal = SecurityContextHelper.getCurrentUser().getPrincipal();
                if (primaryPrincipal != null) {
                    principal = (String) primaryPrincipal;
                }
            }
        } catch (Exception e) {
            // absorb the exception cause job running thread does not have valid user info.
            logger.debug("exception occur when intend to get current login username: {}", e.getMessage());
        }
        return principal;
    }

    /**
     * Get current user in current context.
     *
     * @return the user in current thread.
     */
    public static Subject getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser;
    }

    /**
     * @see org.apache.shiro.subject.Subject#getPrincipal()
     */
    public static Object getPrincipal() {
        Subject currentUser = getCurrentUser();
        return currentUser.getPrincipal();
    }

    /**
     * @see org.apache.shiro.subject.Subject#getPrincipals()
     */
    public static PrincipalCollection getPrincipals() {
        Subject currentUser = getCurrentUser();
        return currentUser.getPrincipals();
    }

    /**
     * @see org.apache.shiro.subject.Subject#isPermitted(String)
     */
    public static boolean isPermitted(String permission) {
        Subject currentUser = getCurrentUser();
        return currentUser.isPermitted(permission);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isPermitted(org.apache.shiro.authz.Permission)
     */
    public static boolean isPermitted(Permission permission) {
        Subject currentUser = getCurrentUser();
        return currentUser.isPermitted(permission);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isPermitted(String...)
     */
    public static boolean[] isPermitted(String... permissions) {
        Subject currentUser = getCurrentUser();
        return currentUser.isPermitted(permissions);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isPermitted(List)
     */
    public static boolean[] isPermitted(List<Permission> permissions) {
        Subject currentUser = getCurrentUser();
        return currentUser.isPermitted(permissions);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isPermittedAll(String...)
     */
    public static boolean isPermittedAll(String... permissions) {
        Subject currentUser = getCurrentUser();
        return currentUser.isPermittedAll(permissions);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isPermittedAll(Collection)
     */
    public static boolean isPermittedAll(Collection<Permission> permissions) {
        Subject currentUser = getCurrentUser();
        return currentUser.isPermittedAll(permissions);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkPermission(String)
     */
    public static void checkPermission(String permission) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkPermission(permission);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkPermission(org.apache.shiro.authz.Permission)
     */
    public static void checkPermission(Permission permission) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkPermission(permission);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkPermissions(String...)
     */
    public static void checkPermissions(String... permissions) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkPermissions(permissions);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkPermissions(Collection)
     */
    public static void checkPermissions(Collection<Permission> permissions) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkPermissions(permissions);
    }

    /**
     * @see org.apache.shiro.subject.Subject#hasRole(String)
     */
    public static boolean hasRole(String roleIdentifier) {
        Subject currentUser = getCurrentUser();
        return currentUser.hasRole(roleIdentifier);
    }

    /**
     * @see org.apache.shiro.subject.Subject#hasRoles(List)
     */
    public static boolean[] hasRoles(List<String> roleIdentifiers) {
        Subject currentUser = getCurrentUser();
        return currentUser.hasRoles(roleIdentifiers);
    }

    /**
     * @see org.apache.shiro.subject.Subject#getPrincipal()
     */
    public static boolean hasAllRoles(Collection<String> roleIdentifiers) {
        Subject currentUser = getCurrentUser();
        return currentUser.hasAllRoles(roleIdentifiers);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkRole(String)
     */
    public static void checkRole(String roleIdentifier) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkRole(roleIdentifier);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkRoles(Collection)
     */
    public static void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkRoles(roleIdentifiers);
    }

    /**
     * @see org.apache.shiro.subject.Subject#checkRole(String)
     */
    public static void checkRoles(String... roleIdentifiers) throws AuthorizationException {
        Subject currentUser = getCurrentUser();
        currentUser.checkRoles(roleIdentifiers);
    }

    /**
     * @see org.apache.shiro.subject.Subject#login(org.apache.shiro.authc.AuthenticationToken)
     */
    public static void login(AuthenticationToken token) throws AuthenticationException {
        login(token, false);
    }

    /**
     * @see org.apache.shiro.subject.Subject#login(org.apache.shiro.authc.AuthenticationToken)
     */
    public static void login(AuthenticationToken token, boolean logout) throws AuthenticationException {
        Subject currentUser = getCurrentUser();
        // logout already login user
        if (logout && currentUser.isAuthenticated()) {
            currentUser.logout();
        }
        // authenticate token and login
        currentUser.login(token);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isAuthenticated()
     */
    public static boolean isAuthenticated() {
        Subject currentUser = getCurrentUser();
        return currentUser.isAuthenticated();
    }

    /**
     * @see org.apache.shiro.subject.Subject#isRemembered()
     */
    public static boolean isRemembered() {
        Subject currentUser = getCurrentUser();
        return currentUser.isRemembered();
    }

    /**
     * @see org.apache.shiro.subject.Subject#getSession()
     */
    public static Session getSession() {
        Subject currentUser = getCurrentUser();
        return currentUser.getSession();
    }

    /**
     * @see org.apache.shiro.subject.Subject#getSession()
     */
    public static Session getSession(boolean create) {
        Subject currentUser = getCurrentUser();
        return currentUser.getSession(create);
    }

    /**
     * @see org.apache.shiro.subject.Subject#logout()
     */
    public static void logout() {
        Subject currentUser = getCurrentUser();
        currentUser.logout();
    }

    /**
     * @see org.apache.shiro.subject.Subject#execute(Callable)
     */
    public static <V> V execute(Callable<V> callable) throws ExecutionException {
        Subject currentUser = getCurrentUser();
        return currentUser.execute(callable);
    }

    /**
     * @see org.apache.shiro.subject.Subject#execute(Runnable)
     */
    public static void execute(Runnable runnable) {
        Subject currentUser = getCurrentUser();
        currentUser.execute(runnable);
    }

    /**
     * @see org.apache.shiro.subject.Subject#associateWith(Callable)
     */
    public static <V> Callable<V> associateWith(Callable<V> callable) {
        Subject currentUser = getCurrentUser();
        return currentUser.associateWith(callable);
    }

    /**
     * @see org.apache.shiro.subject.Subject#associateWith(Runnable)
     */
    public static Runnable associateWith(Runnable runnable) {
        Subject currentUser = getCurrentUser();
        return currentUser.associateWith(runnable);
    }

    /**
     * @see org.apache.shiro.subject.Subject#runAs(org.apache.shiro.subject.PrincipalCollection)
     */
    public static void runAs(PrincipalCollection principals) throws NullPointerException, IllegalStateException {
        Subject currentUser = getCurrentUser();
        currentUser.runAs(principals);
    }

    /**
     * @see org.apache.shiro.subject.Subject#isRunAs()
     */
    public static boolean isRunAs() {
        Subject currentUser = getCurrentUser();
        return currentUser.isRunAs();
    }

    /**
     * @see org.apache.shiro.subject.Subject#getPreviousPrincipals()
     */
    public static PrincipalCollection getPreviousPrincipals() {
        Subject currentUser = getCurrentUser();
        return currentUser.getPreviousPrincipals();
    }

    /**
     * @see org.apache.shiro.subject.Subject#releaseRunAs()
     */
    public static PrincipalCollection releaseRunAs() {
        Subject currentUser = getCurrentUser();
        return currentUser.releaseRunAs();
    }
}
