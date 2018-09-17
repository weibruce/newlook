/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.orm.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * HibernateDaoSupport/HibernateTemplate are not recommended to used any more
 * from spring 3.x. But after reviewed the impl of
 * HibernateDaoSupport/HibernateTemplate, it provides really convenient methods
 * to assess hibernate and also give approach to reach plain hibernate session.
 * If Spring 4.x or later deprecated this class, also can copy this class into
 * platform for as a referenced encapsulation.
 * <p>
 * All spring templates (hibernate, jdbc, rest, jpa etc.) have the same pros and
 * cons:
 * <p>
 * Pro: They perform common setup routines for you, let you skip the boilerplate
 * and concentrate on the logic you want.
 * <p>
 * Con: you are coupling your application tightly to the spring framework. For
 * this reason, Spring recommends that HibernateTemplate no longer be used.
 * <p>
 * Specifically, what HibernateTemplate did for you was to automatically open
 * and close sessions and commit or rollback transactions after your code
 * executed. However, all of this can be achieved in an aspect-oriented way
 * using Spring's Declarative Transaction Management.
 */
public abstract class LibraHibernateDaoSupport extends HibernateDaoSupport {

    /* fields -------------------------------------------------------------- */

    @PersistenceContext
    private EntityManager entityManager;

    /* public methods ------------------------------------------------------ */

    // IMPORTANT! Not Work for Hibernate 5.2+.
    // Hibernate 5.2 implements SessionFactory extends javax.persistence.EntityManagerFactory will cause
    // "No unique bean of type [javax.persistence.EntityManagerFactory] is defined: expected single bean but found 2"
    // that conflict with JPA configuration
    //    @Autowired
    //    public void setLibraHibernateSessionFactory(SessionFactory sessionFactory) {
    //        setSessionFactory(sessionFactory);
    //    }

    // IMPORTANT! enable below method and disable above one to support Hibernate 5.2+.
    @Autowired
    public void setLibraHibernateSessionFactory(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        setSessionFactory(sessionFactory);
    }

    /* protected methods --------------------------------------------------- */

    protected Session getCurrentSession() throws DataAccessException {
        return (Session) entityManager.getDelegate();
    }
}
