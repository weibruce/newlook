/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.dal.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;

import com.querer.libra.platform.core.query.pagination.Pager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * All ORM based DAO interfaces.
 */
public interface BaseDao<T, PK extends Serializable> {

    // -------------------------------------------------------------------------
    // Convenience methods for loading individual objects
    // -------------------------------------------------------------------------

    T get(Class<T> entityClass, PK id) throws DataAccessException;

    T load(Class<T> entityClass, PK id) throws DataAccessException;

    List<T> loadAll(Class<T> entityClass) throws DataAccessException;

    void load(Object entity, PK id) throws DataAccessException;

    void refresh(Object entity) throws DataAccessException;

    // -------------------------------------------------------------------------
    // Convenience methods for storing individual objects
    // -------------------------------------------------------------------------

    PK save(Object entity) throws DataAccessException;

    void update(Object entity) throws DataAccessException;

    void persist(Object entity) throws DataAccessException;

    T merge(T entity) throws DataAccessException;

    void delete(Object entity) throws DataAccessException;

    void deleteAll(Collection<?> entities) throws DataAccessException;

    void flush() throws DataAccessException;

    void clear() throws DataAccessException;

    // -------------------------------------------------------------------------
    // Convenience finder methods for HQL strings
    // -------------------------------------------------------------------------

    List<?> find(String queryString, Object... values) throws DataAccessException;

    List<?> findByNamedParam(String queryString, String paramName, Object value) throws DataAccessException;

    List<?> findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DataAccessException;

    // -------------------------------------------------------------------------
    // Convenience finder methods for detached criteria
    // -------------------------------------------------------------------------

    List<?> findByCriteria(DetachedCriteria criteria) throws DataAccessException;

    List<?> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) throws DataAccessException;

    // -------------------------------------------------------------------------
    // Convenience query methods for pagination
    // -------------------------------------------------------------------------

    Pager findPager(Pager pager);

    Pager findPager(Pager pager, Criterion... criterions);

    Pager findPager(Pager pager, Order... orders);

    Pager findPager(Pager pager, Criteria criteria);

    // -------------------------------------------------------------------------
    // Convenience finder methods for named queries
    // -------------------------------------------------------------------------

    // TODO

    // -------------------------------------------------------------------------
    // Convenience query methods for iteration and bulk updates/deletes
    // -------------------------------------------------------------------------

    // TODO

}
