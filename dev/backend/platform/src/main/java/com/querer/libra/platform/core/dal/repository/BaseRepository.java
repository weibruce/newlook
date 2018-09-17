/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.dal.repository;

import java.io.Serializable;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

/**
 * Base repository.
 */
@NoRepositoryBean
public interface BaseRepository<T extends BusinessEntity, ID extends Serializable> extends LibraRepository<T, ID>, QueryDslPredicateExecutor<T> {
}
