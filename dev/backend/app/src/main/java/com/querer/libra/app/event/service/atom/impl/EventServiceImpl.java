/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom.impl;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.dal.repository.EventRepository;
import com.querer.libra.app.event.domain.entity.Event;
import com.querer.libra.app.event.service.atom.EventService;

@Service
public class EventServiceImpl implements EventService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private EventRepository eventRepository;

    /* public methods ------------------------------------------------------ */

    @Override
    public Optional<Event> save(@NotNull Event event) {
        return eventRepository.softlySave(event);
    }

    @Override
    public void delete(@NotNull Long oid) {
        eventRepository.softlyDelete(oid);
    }

    @Override
    public Optional<Event> findById(@NotNull Long oid) {
        return Optional.ofNullable(eventRepository.findOne(oid));
    }

    @Override
    @Cacheable(value = "querer.app.event", key = "'event.opening.code.' + #openingCode")
    public Optional<Event> findByOpeningCode(@NotNull String openingCode) {
        return eventRepository.findByOpeningCode(openingCode);
    }
}
