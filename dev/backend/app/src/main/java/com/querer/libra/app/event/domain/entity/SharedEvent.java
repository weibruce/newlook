/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;

@Entity
@Table(name = "t_biz_shared_event")
public class SharedEvent extends BusinessEntity {

    /* fields -------------------------------------------------------------- */

    @Column(name = "uid", length = 256)
    private String uid;
    @Column(name = "username", length = 128)
    private String username;

    @Column(name = "event_oid")
    private Long eventOid;
    @Column(name = "shared_occur_time")
    private Date sharedOccurTime;
    @Column(name = "shared_submit_time")
    private Date sharedSubmitTime;

    @Column(name = "shared_friend_uid", length = 256)
    private String sharedFriendUid;

    /* getters/setters ----------------------------------------------------- */

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getEventOid() {
        return eventOid;
    }

    public void setEventOid(Long eventOid) {
        this.eventOid = eventOid;
    }

    public Date getSharedOccurTime() {
        return sharedOccurTime;
    }

    public void setSharedOccurTime(Date sharedOccurTime) {
        this.sharedOccurTime = sharedOccurTime;
    }

    public Date getSharedSubmitTime() {
        return sharedSubmitTime;
    }

    public void setSharedSubmitTime(Date sharedSubmitTime) {
        this.sharedSubmitTime = sharedSubmitTime;
    }

    public String getSharedFriendUid() {
        return sharedFriendUid;
    }

    public void setSharedFriendUid(String sharedFriendUid) {
        this.sharedFriendUid = sharedFriendUid;
    }
}
