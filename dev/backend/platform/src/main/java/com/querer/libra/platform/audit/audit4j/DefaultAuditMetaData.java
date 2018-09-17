/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.audit.audit4j;

import org.audit4j.core.MetaData;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.querer.libra.platform.security.util.SecurityContextHelper;

import java.util.Optional;

/**
 * Default audit metadata for all audit logs.
 */
public class DefaultAuditMetaData implements MetaData {

    /* fields ------------------------------------------------------ */

    public final static String UNKNOWN_ACTOR = "unknown actor";
    public final static String UNKNOWN_ORIGIN = "unknown origin";

    /* public methods ------------------------------------------------------ */

    @Override
    public String getActor() {
        Optional<String> origin = Optional.empty();
        try {
            if (SecurityContextHelper.isAuthenticated()) {
                String username = SecurityContextHelper.getCurrentLoginUsername();
                origin = Optional.ofNullable(username);
            }
        } finally {
            return origin.orElse(UNKNOWN_ACTOR);
        }
    }

    @Override
    public String getOrigin() {
        Optional<String> origin = Optional.empty();
        try {
            String remoteAddress = 
                    ((ServletRequestAttributes) RequestContextHolder
                            .currentRequestAttributes())
                            .getRequest()
                            .getRemoteAddr();
            origin = Optional.ofNullable(remoteAddress);
        } finally {
            return origin.orElse(UNKNOWN_ORIGIN);
        }
    }
}
