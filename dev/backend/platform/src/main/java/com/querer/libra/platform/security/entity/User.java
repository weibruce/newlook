/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * Also known as Account. This class is used as the base object that wants to 
 * use local user containing detail user info, like username, password...etc. 
 * <p>
 * It will be referenced as a field in one specific subject (subclass of
 * Subject) to get reached to specific user information like user principals,
 * credentials defined by each app. So through subject object, client can get
 * user details from this class (subclass).
 */
@Entity
@Table(name = "t_security_user")
public class User extends BusinessEntity {

    /* fields ------------------------------------------------------ */

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_oid")
    @Cascade(CascadeType.ALL)
    private Subject subject;
    
    // Aka. login id. End user use this username to login. App uses this to identify one user.
    @Column(name = "username", length = 32, unique = true, nullable = false)
    private String username;

    // Only end-user input password matches the encrypted password stored in
    // local database,
    // the authentication successes. It's hashed with salt and stored in local
    // DB in most of the cases.
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    
    // the system name user from
    @Column(name = "source_system", length = 128)
    private String sourceSystem;
    // the status of the user, locked or available
    @Column(name = "status_code", length = 32)
    private String statusCode;
    
    @Column(name = "is_first_time_login")
    private Boolean isFirstTimeLogin = true;

    @Column(name = "first_login_date")
    private Date firstLoginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_pwd_time")
    private Date lastUpdatePwdTime;

    /* constructors ------------------------------------------------------ */

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /* public methods ------------------------------------------------------ */

    /**
     * Return specific user principals.
     * <p>
     * It's handy to call this directly to return user principals when client
     * calls {@link Subject Subject#getPrincipals()}. To specific app, this
     * class should know exactly what the user principals are.
     *
     * @return the specific principals of user, like username.
     * @see Subject#getPrincipals()
     */
    @Transient
    public Object getPrincipals() {
        return this.username;
    }

    /**
     * Return specific user credentials.
     * <p>
     * It's handy to call this directly to return user credentials when client
     * calls {@link Subject Subject#getCredentials()}. To specific app, this
     * class should know exactly what the user credentials are.
     *
     * @return the specific credentials of user, like password.
     * @see Subject#getCredentials()
     */
    @Transient
    public Object getCredentials() {
        return this.password;
    }

    /* getters/setters ------------------------------------------------------ */
    
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

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

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	public Date getLastUpdatePwdTime() {
		return lastUpdatePwdTime;
	}

	public void setLastUpdatePwdTime(Date lastUpdatePwdTime) {
		this.lastUpdatePwdTime = lastUpdatePwdTime;
	}

    public Date getFirstLoginDate() {
        return firstLoginDate;
    }

    public void setFirstLoginDate(Date firstLoginDate) {
        this.firstLoginDate = firstLoginDate;
    }


}
