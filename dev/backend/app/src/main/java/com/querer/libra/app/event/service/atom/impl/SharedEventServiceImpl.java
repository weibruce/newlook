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
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.dal.repository.SharedEventRepository;
import com.querer.libra.app.event.domain.entity.SharedEvent;
import com.querer.libra.app.event.service.atom.SharedEventService;

@Service
public class SharedEventServiceImpl implements SharedEventService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private SharedEventRepository sharedEventRepository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<SharedEvent> save(@NotNull SharedEvent sharedEvent) {
        return sharedEventRepository.softlySave(sharedEvent);
    }

    @Override
    public void delete(@NotNull Long oid) {
        sharedEventRepository.softlyDelete(oid);
    }

    @Override
    public Optional<SharedEvent> findById(@NotNull Long oid) {
        return Optional.ofNullable(sharedEventRepository.findOne(oid));
    }

    @Override
    public List<SharedEvent> findByUidAndBetweenDates(@NotNull Long eventOid, @NotNull String uid, @NotNull Date startDate, @NotNull Date endDate) {
        return sharedEventRepository.findByEventOidAndUidAndSharedSubmitTimeBetween(eventOid, uid, startDate, endDate);
    }

    @Override
    public List<SharedEvent> findByEventOidAndUid(@NotNull Long eventOid, @NotNull String uid) {
        return sharedEventRepository.findByEventOidAndUid(eventOid, uid);
    }
}
