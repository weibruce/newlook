/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.dal.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.querer.libra.platform.core.dal.repository.LibraRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.util.Assert;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * Enable soft or logically save/delete functions for all repositories.
 *
 * @param <T> Business entity
 * @param <ID> ID
 */
public class LibraRepositoryImpl<T extends BusinessEntity, ID extends Serializable>
        extends QueryDslJpaRepository<T, ID> implements LibraRepository<T, ID> {

    /* fields -------------------------------------------------------------- */

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ID> entityInformation;

    public LibraRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    /* public methods ------------------------------------------------------ */

    /**
     * @see LibraRepository#softlySave(BusinessEntity)
     */
    @Transactional
    @Override
    public Optional<T> softlySave(T entity) {
        if (this.entityInformation.isNew(entity)) {
            if (entity != null) entity.setActive(true);
            entityManager.persist(entity);
        } else {
            Assert.isTrue(entity.getActive(), "The inactive entity cannot be saved!");
            entityManager.merge(entity);
        }

        return Optional.ofNullable(entity);
    }

    /**
     * @see LibraRepository#softlySave(Iterable)
     */
    @Transactional
    @Override
    public List<T> softlySave(Iterable<T> entities) {
        List<T> result = new ArrayList<T>();

        if (entities == null) {
            return result;
        }

        for (T entity : entities) {
            Optional<T> entityOptional = softlySave(entity);
            if (entityOptional.isPresent()) {
                result.add(entityOptional.get());
            }
        }

        return result;
    }

    /**
     * @see LibraRepository#softlyDelete(Serializable)
     */
    @Transactional
    @Override
    public void softlyDelete(ID id) {
        Assert.notNull(id, "The given id must not be null!");

        T entity = findOne(id);

        if (entity == null) {
            throw new EmptyResultDataAccessException(
                    String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1);
        }

        softlyDelete(entity);
    }

    /**
     * @see LibraRepository#softlySave(BusinessEntity)
     */
    @Transactional
    @Override
    public void softlyDelete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");

        entity.setActive(false);
        save(entity);
    }
}
