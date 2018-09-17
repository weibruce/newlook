/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.domain.model;

import java.util.Date;

import com.querer.libra.platform.core.domain.model.common.BaseModel;

public class SharedEventModel extends BaseModel {

    /* fields -------------------------------------------------------------- */

    private String uid;
    private String username;

    private EventModel event;
    private Date sharedOccurTime;
    private Date sharedSubmitTime;

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

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
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
