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

import com.querer.libra.app.event.domain.entity.SharedEvent;
import com.querer.libra.platform.core.dal.repository.BaseRepository;

public interface SharedEventRepository extends BaseRepository<SharedEvent, Long> {

    List<SharedEvent> findByEventOidAndUidAndSharedSubmitTimeBetween(Long eventOid, String uid, Date startDate, Date endDate);

    List<SharedEvent> findByEventOidAndUid(Long eventOid, String uid);
}
