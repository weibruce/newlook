/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.dal.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.querer.libra.platform.core.orm.hibernate.LibraHibernateDaoSupport;
import com.querer.libra.platform.core.query.pagination.Pager;
import com.querer.libra.platform.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

/**
 * BaseDao implementation based on HibernateDaoSupport.
 */
public class BaseDaoImpl<T, PK extends Serializable> extends LibraHibernateDaoSupport implements BaseDao<T, PK> {

    /* fields -------------------------------------------------------------- */

    private Class<T> entityClass;

    /* constructor --------------------------------------------------------- */

    public BaseDaoImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    /* public methods ------------------------------------------------------ */

    @SuppressWarnings("unchecked")
    @Override
    public T get(Class<T> entityClass, PK id) throws DataAccessException {
        return (T) getCurrentSession().get(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T load(Class<T> entityClass, PK id) throws DataAccessException {
        return (T) getCurrentSession().load(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> loadAll(Class<T> entityClass) throws DataAccessException {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void load(Object entity, PK id) throws DataAccessException {
        getCurrentSession().load(entity, id);
    }

    @Override
    public void refresh(Object entity) throws DataAccessException {
        getCurrentSession().refresh(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PK save(Object entity) throws DataAccessException {
        return (PK) getCurrentSession().save(entity);
    }

    @Override
    public void update(Object entity) throws DataAccessException {
        getCurrentSession().update(entity);
    }

    @Override
    public void persist(Object entity) throws DataAccessException {
        getCurrentSession().persist(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T merge(T entity) throws DataAccessException {
        return (T) getCurrentSession().merge(entity);
    }

    @Override
    public void delete(Object entity) throws DataAccessException {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll(Collection<?> entities) throws DataAccessException {
        Session session = getCurrentSession();
        entities.forEach(session::delete);
    }

    @Override
    public void flush() throws DataAccessException {
        getCurrentSession().flush();
    }

    @Override
    public void clear() throws DataAccessException {
        getCurrentSession().clear();
    }

    @Override
    public List<?> find(String queryString, Object... values) throws DataAccessException {
        Query queryObject = getCurrentSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject.list();
    }

    @Override
    public List<?> findByNamedParam(String queryString, String paramName, Object value) throws DataAccessException {
        return findByNamedParam(queryString, new String[] {paramName}, new Object[] {value});
    }

    @Override
    public List<?> findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DataAccessException {
        Query queryObject = getCurrentSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                applyNamedParameterToQuery(queryObject, paramNames[i], values[i]);
            }
        }
        return queryObject.list();
    }

    @Override
    public List<?> findByCriteria(DetachedCriteria criteria) throws DataAccessException {
        return findByCriteria(criteria, -1, -1);
    }

    @Override
    public List<?> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults)
            throws DataAccessException {

        Criteria executableCriteria = criteria.getExecutableCriteria(getCurrentSession());
        if (firstResult >= 0) {
            executableCriteria.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            executableCriteria.setMaxResults(maxResults);
        }
        return executableCriteria.list();
    }

    // TODO below page methods to be debug later
    @Override
    public Pager findPager(final Pager pager) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(final Pager pager, final Criterion... criterions) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(final Pager pager, final Order... orders) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(Pager pager, Criteria criteria) {
        Assert.notNull(pager, "pager is required");
        Assert.notNull(criteria, "criteria is required");

        Integer pageNumber = pager.getPageNumber();
        Integer pageSize = pager.getPageSize();
        String searchBy = pager.getSearchBy();
        String keyword = pager.getKeyword();
        String orderBy = pager.getOrderBy();
        Pager.Order order = pager.getOrder();

        if (StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keyword)) {
            if (searchBy.contains(".")) {
                String alias = StringUtils.substringBefore(searchBy, ".");
                criteria.createAlias(alias, alias);
            }
            criteria.add(Restrictions.like(searchBy, "%" + keyword + "%"));
        }

        pager.setTotalCount(criteriaResultTotalCount(criteria));

        if (StringUtils.isNotEmpty(orderBy) && order != null) {
            if (order == Pager.Order.ASC) {
                criteria.addOrder(Order.asc(orderBy));
            } else {
                criteria.addOrder(Order.desc(orderBy));
            }
        }

        // TODO to be identified below usage in development
        // ClassMetadata classMetadata =
        // sessionFactory.getClassMetadata(entityClass);
        // if (!StringUtils.equals(orderBy, ORDER_LIST_PROPERTY_NAME) &&
        // ArrayUtils.contains(classMetadata.getPropertyNames(),
        // ORDER_LIST_PROPERTY_NAME)) {
        // criteria.addOrder(Order.asc(ORDER_LIST_PROPERTY_NAME));
        // criteria.addOrder(Order.desc(CREATE_DATE_PROPERTY_NAME));
        // if (StringUtils.isEmpty(orderBy) || order == null) {
        // pager.setOrderBy(ORDER_LIST_PROPERTY_NAME);
        // pager.setOrder(Pager.Order.asc);
        // }
        // } else if (!StringUtils.equals(orderBy, CREATE_DATE_PROPERTY_NAME) &&
        // ArrayUtils.contains(classMetadata.getPropertyNames(),
        // CREATE_DATE_PROPERTY_NAME)) {
        // criteria.addOrder(Order.desc(CREATE_DATE_PROPERTY_NAME));
        // if (StringUtils.isEmpty(orderBy) || order == null) {
        // pager.setOrderBy(CREATE_DATE_PROPERTY_NAME);
        // pager.setOrder(Pager.Order.desc);
        // }
        // }

        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        pager.setResult(criteria.list());
        return pager;
    }

    /* protected methods --------------------------------------------------- */

    /**
     * Apply the given name parameter to the given Query object.
     * @param queryObject the Query object
     * @param paramName the name of the parameter
     * @param value the value of the parameter
     * @throws HibernateException if thrown by the Query object
     */
    protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value)
            throws HibernateException {

        if (value instanceof Collection) {
            queryObject.setParameterList(paramName, (Collection<?>) value);
        }
        else if (value instanceof Object[]) {
            queryObject.setParameterList(paramName, (Object[]) value);
        }
        else {
            queryObject.setParameter(paramName, value);
        }
    }

    protected T uniqueResult(List<T> list) throws NonUniqueResultException {
        int size = list.size();
        if (size == 0)
            return null;
        T first = list.get(0);
        for (int i = 1; i < size; i++) {
            if (list.get(i) != first) {
                throw new NonUniqueResultException(list.size());
            }
        }
        return first;
    }

    /* private methods ----------------------------------------------------- */

    private int criteriaResultTotalCount(Criteria criteria) {
        Assert.notNull(criteria, "criteria is required");

        int criteriaResultTotalCount = 0;
        try {
            // TODO Below impl to be refined, it's a bit ugly
            CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

            // ? useless get here and set later for projection and
            // resultTransformer
            Projection projection = criteriaImpl.getProjection();
            ResultTransformer resultTransformer = criteriaImpl.getResultTransformer();

            // Remove order criteria in criteria for count total results
            List<CriteriaImpl.OrderEntry> orderEntries = (List) ReflectionUtil.getFieldValue(criteriaImpl,
                    "orderEntries");
            ReflectionUtil.setFieldValue(criteriaImpl, "orderEntries", new ArrayList());
            // get total count of search results
            Integer totalCount = ((Long) criteriaImpl.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (totalCount != null) {
                criteriaResultTotalCount = totalCount;
            }

            // set back the projection and resultTransformer
            criteriaImpl.setProjection(projection);
            if (projection == null) {
                criteriaImpl.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
            }
            if (resultTransformer != null) {
                criteriaImpl.setResultTransformer(resultTransformer);
            }
            // set back the order criteria
            ReflectionUtil.setFieldValue(criteriaImpl, "orderEntries", orderEntries);
        } catch (Exception e) {

        }
        return criteriaResultTotalCount;
    }
}
