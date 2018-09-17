/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.dal.repository.RuleRepository;
import com.querer.libra.app.event.domain.entity.Rule;
import com.querer.libra.app.event.service.atom.RuleService;
import com.querer.libra.app.event.util.DateTimeUtil;

@Service
public class RuleServiceImpl implements RuleService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private RuleRepository ruleRepository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<Rule> save(@NotNull Rule rule) {
        return ruleRepository.softlySave(rule);
    }

    @Override
    public void delete(@NotNull Long oid) {
        ruleRepository.softlyDelete(oid);
    }

    @Override
    public Optional<Rule> findById(@NotNull Long oid) {
        return Optional.ofNullable(ruleRepository.findOne(oid));
    }

    @Override
    @Cacheable(value = "querer.app.event.rules", key = "'rules.event.' + #eventOid + '.' + #today")
    public List<Rule> findRulesToday(@NotNull Long eventOid, @NotNull Date today) {
        Date startDate = DateTimeUtil.getStartOfDay(today);
        Date endDate = DateTimeUtil.getEndOfDay(today);
        return ruleRepository.findByEventOidAndEnabledTrueAndTargetDayBetweenOrderByPriority(eventOid, startDate, endDate);
    }
}
