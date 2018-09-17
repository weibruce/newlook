/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.dal.repository;

import java.util.Date;
import java.util.List;

import com.querer.libra.app.event.domain.entity.Rule;
import com.querer.libra.platform.core.dal.repository.BaseRepository;

/**
 * Rule repository.
 */
public interface RuleRepository extends BaseRepository<Rule, Long> {

    List<Rule> findByEventOidAndEnabledTrueAndTargetDayBetweenOrderByPriority(Long eventOid, Date statDate, Date endDate);
}
