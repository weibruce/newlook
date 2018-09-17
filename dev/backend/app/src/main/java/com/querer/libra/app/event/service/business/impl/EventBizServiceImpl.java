/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.business.impl;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querer.libra.app.event.domain.entity.Event;
import com.querer.libra.app.event.domain.model.EventModel;
import com.querer.libra.app.event.exception.EventNotFoundException;
import com.querer.libra.app.event.service.atom.EventService;
import com.querer.libra.app.event.service.business.EventBizService;

@Service
public class EventBizServiceImpl implements EventBizService {

    /* fields -------------------------------------------------------------- */

    @Autowired
    private EventService eventService;

    @Autowired
    private Mapper beanMapper;

    /* public methods ------------------------------------------------------ */

    @Override
    public EventModel findByOpeningCode(@NotNull String openingCode) {
        EventModel model = null;
        Optional<Event> eventOptional = eventService.findByOpeningCode(openingCode);
        if (eventOptional.isPresent()) {
            model = beanMapper.map(eventOptional.get(), EventModel.class);
        } else {
            throw new EventNotFoundException("cannot find event with opening code: " + openingCode);
        }
        return model;
    }
}
