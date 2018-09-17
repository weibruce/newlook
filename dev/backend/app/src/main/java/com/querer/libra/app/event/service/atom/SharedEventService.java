/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.entity.SharedEvent;

@Validated
public interface SharedEventService {

    Optional<SharedEvent> save(@NotNull SharedEvent sharedEvent);

    void delete(@NotNull Long oid);

    Optional<SharedEvent> findById(@NotNull Long oid);

    List<SharedEvent> findByUidAndBetweenDates(@NotNull Long eventOid, @NotNull String uid, @NotNull Date startDate, @NotNull Date endDate);

    List<SharedEvent> findByEventOidAndUid(@NotNull Long eventOid, @NotNull String uid);
}
