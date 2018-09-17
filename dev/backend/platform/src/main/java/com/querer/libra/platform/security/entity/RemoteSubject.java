/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.entity;

import javax.persistence.Transient;

/**
 * Remote subject entity used in the case the user principal and credential are
 * not stored locally but in remote data source.
 * <p>
 * It separates user principal and credential into two new entities. This also
 * make supporting varies of principals and credentials. Principal could be
 * single field, like username, also could be multi-fields like combine username
 * & organization ID. Same as credential, it also could required confidential
 * pin code more than just password. All probabilities depends on how to extend
 * its subclass.
 */
// Uncomment below JPA annotations to init this entity into database in remote
// data store case
// @Entity
// @Table(name = "T_SEC_REMOTE_SUBJECT")
// @Proxy(lazy = false)
public class RemoteSubject extends Subject {

    /* fields ------------------------------------------------------ */

    private Principal principal;
    private Credential credential;

    /* constructors ------------------------------------------------------ */

    public RemoteSubject() {
    }

    public RemoteSubject(Principal principal, Credential credential) {
        this.principal = principal;
        this.credential = credential;
    }

    /* public methods ------------------------------------------------------ */

    /**
     * Up to the specific subclass implementation of principal entity
     *
     * @see Subject#getPrincipals()
     */
    @Override
    @Transient
    public Object getPrincipals() {
        return this.principal;
    }

    /**
     * Up to the specific subclass implementation of credential entity
     *
     * @see Subject#getCredentials()
     */
    @Override
    @Transient
    public Object getCredentials() {
        return this.credential;
    }

    /* getters/setters ------------------------------------------------------ */

    // @OneToOne
    // @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    // @OneToOne
    // @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

}
