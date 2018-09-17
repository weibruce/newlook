/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.dal.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

@NoRepositoryBean
public interface LibraRepository<T extends BusinessEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    /**
     * Softly save business entity that set active flag to 1.
     *
     * @param entity target business entity to save
     * @return saved entity
     */
    Optional<T> softlySave(T entity);

    /**
     * Softly save business entity collections that set active flag to 1.
     *
     * @param entities<T> target business entity collection to save
     * @return saved entity list
     */
    List<T> softlySave(Iterable<T> entities);

    /**
     * Delete business entity logically by id. Active flag will be set to 0.
     *
     * @param id target id of business entity
     */
    void softlyDelete(ID id);

    /**
     * Delete business entity logically. Active flag will be set to 0.
     *
     * @param entity target business entity
     */
    void softlyDelete(T entity);
}
