/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.domain.entity.Event;
import com.querer.libra.app.event.domain.entity.SharedEvent;
import com.querer.libra.app.event.domain.model.EventModel;
import com.querer.libra.app.event.domain.model.SharedEventModel;
import com.querer.libra.app.event.exception.EventClosedException;
import com.querer.libra.app.event.exception.InvalidEventException;
import com.querer.libra.app.event.exception.ParameterRequiredException;
import com.querer.libra.app.event.exception.SharedEventNotFoundException;
import com.querer.libra.app.event.service.atom.EventService;
import com.querer.libra.app.event.service.atom.SharedEventService;
import com.querer.libra.app.event.service.business.SharedEventBizService;

@Service
public class SharedEventBizServiceImpl implements SharedEventBizService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private SharedEventService sharedEventService;

    @Autowired
    private EventService eventService;

    @Autowired
    private Mapper beanMapper;

    /* public methods ------------------------------------------------------ */

    @Override
    public SharedEventModel doSharedEvent(@NotNull SharedEventModel sharedEventModel) {
        // validate params
        String uid = sharedEventModel.getUid();
        String openingCode = null;
        EventModel eventModel = sharedEventModel.getEvent();
        if (eventModel != null) {
            openingCode = eventModel.getOpeningCode();
        }
        if (StringUtils.isBlank(uid) && StringUtils.isBlank(openingCode)) {
            throw new ParameterRequiredException("share event error, uid or opening code is missing.");
        }

        // find event
        Optional<Event> eventOptional = eventService.findByOpeningCode(openingCode);
        if (!eventOptional.isPresent()) {
            throw new SharedEventNotFoundException("shared event cannot be found.");
        }
        Event event = eventOptional.get();

        // validate event attributes
        Date now = new Date();
        Date startTime = event.getStartTime();
        Date endTime = event.getEndTime();
        if (startTime != null && endTime != null) {
            if (!(now.after(startTime) && now.before(endTime))) {
                throw new EventClosedException("the event is closed.");
            }
        } else {
            throw new InvalidEventException("the event has invalid start time or end time.");
        }

        // save share event
        SharedEvent sharedEvent = new SharedEvent();
        sharedEvent.setUid(uid);
        sharedEvent.setEventOid(event.getOid());
        sharedEvent.setSharedOccurTime(sharedEventModel.getSharedOccurTime());
        sharedEvent.setSharedSubmitTime(now);

        sharedEvent = sharedEventService.save(sharedEvent).get();

        return beanMapper.map(sharedEvent, SharedEventModel.class);
    }

    @Override
    public List<SharedEventModel> findSharedEvents(@NotNull String openingCode, @NotNull String uid) {
        // validate params
        if (StringUtils.isBlank(uid) && StringUtils.isBlank(openingCode)) {
            throw new ParameterRequiredException("share event error, uid or opening code is missing.");
        }

        // find event
        Optional<Event> eventOptional = eventService.findByOpeningCode(openingCode);
        if (!eventOptional.isPresent()) {
            throw new SharedEventNotFoundException("shared event cannot be found.");
        }
        Event event = eventOptional.get();

        List<SharedEvent> sharedEventList = sharedEventService.findByEventOidAndUid(event.getOid(), uid);
        List<SharedEventModel> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sharedEventList)) {
            result = sharedEventList.stream().map(e -> beanMapper.map(e, SharedEventModel.class)).collect(Collectors.toList());
        }

        return result;
    }
}
