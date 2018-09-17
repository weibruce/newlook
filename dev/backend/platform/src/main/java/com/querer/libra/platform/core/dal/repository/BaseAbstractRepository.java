/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.dal.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * Base repository.
 */
public abstract class BaseAbstractRepository {
    /* fields -------------------------------------------------------------- */

    @PersistenceContext
    protected EntityManager entityManager;

    /* public methods ------------------------------------------------------ */

    /**
     * Return JPAQueryFactory object for subclass to execute query.
     *
     * @return new JPAQueryFactory instance
     */
//    @Bean
    protected JPQLQueryFactory getJPQLQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
