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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * It stands for each activity entity (user, subject, client...) in one app.
 * <p>
 * It doesn't care the subject detail information. It's designed to use one
 * unique ID to represent the subject, to establish relationships with
 * principals,credentials, baseRoles, permissions and organizations. So the
 * detail subject info, like principals and credentials can be loaded from local
 * or external remote store.
 * <p>
 * Each app should have its own subject class that extends this Subject to
 * extend its info in terms of its system security requirements. By inheritance
 * of this class, it gets the organization and role functions handy.
 * <p>
 * Two abstract functions (@see #getPrincipals() and @see #getCredentials())
 * need to be override by subclass to return subject's detail principals and
 * credentials.
 */
@Entity
@Table(name = "t_security_subject")
public class Subject extends BusinessEntity {

    /* fields ------------------------------------------------------ */

    @OneToOne(fetch = FetchType.EAGER, optional = true, mappedBy = "subject", orphanRemoval = true)
    private User user; // user authentication info is stored in this class

    /**
     * Unique identifier standing for a specific subject in application. It is
     * supposed to used to represent one specific user in app that even no idea
     * about the user detail info and where it comes from (local/remote store
     * whatever).
     */
    @Column(name = "identifier", length = 128, unique = true, nullable = false)
    private String identifier;

    // The roles assigned on current subject
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tr_security_subject_role", joinColumns = {
            @JoinColumn(name = "subject_oid", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "role_oid", nullable = false, updatable = false) })
    private Set<Role> roles = new HashSet<>(0);

    /* public methods ------------------------------------------------------ */

    /**
     * Return principals of current subject for all security usages.
     * <p>
     * All subclasses need to implement this interface to return valid
     * principals for authc, authz, finding specific subject entities...etc. The
     * principals stored in either local or remote store load by realm should be
     * filled in this interface, which represents its true identification in
     * system.
     * <p>
     * Since principals might be single field or multiple fields, the return
     * object must be generic Object. Each subclass, specific tokens and realms
     * should know its specific type then process with it.
     *
     * @return the principals of current subject
     */
    @Transient
    public Object getPrincipals() {
        if (this.user != null) {
            return this.user.getPrincipals();
        }
        return null;
    }

    /**
     * Return credentials of current subject for authentication..
     * <p>
     * All subclasses need to implement this interface to return valid
     * credentials stored in system, used for authenticate the user pass-by
     * credentials in token. If the two credentials match, the user is indeed
     * the one what he tell who he is; otherwise, authentication fails.
     * <p>
     * Since credentials might be single field or multiple fields, the return
     * object must be generic Object. Each subclass, specific tokens and realms
     * should know its specific type then do matching job with it.
     *
     * @return the credentials of current subject
     */
    @Transient
    public Object getCredentials() {
        if (this.user != null) {
            return this.user.getCredentials();
        }
        return null;
    }

    /* getters/setters ------------------------------------------------------ */

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
