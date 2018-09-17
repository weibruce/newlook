/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.orm.hibernate;

import org.apache.commons.lang3.StringUtils;

import com.querer.libra.platform.security.util.SecurityContextHelper;

/**
 * Use shiro helper to get entity operation executor.
 */
public class ShiroEntityOperationListener extends EntityOperationListener {

    /* protected methods ----------------------------------------------------*/

    /**
     * Get operator name on insert op.
     *
     * @return name of insert operator
     */
    protected String getInsertOperator() {
        return getCurrentUsername(DEFAULT_CREATE_OPERATOR);
    }

    /**
     * Get operator name on update op.
     *
     * @return name of update operator
     */
    protected String getUpdateOperator() {
        return getCurrentUsername(DEFAULT_UPDATE_OPERATOR);
    }

    /* private methods ------------------------------------------------------*/

    private String getCurrentUsername(String defaultUsername) {
        String entityOperator = SecurityContextHelper.getCurrentLoginUsername(); // TODO try to mock/interface this for Integration Test
        if (StringUtils.isBlank(entityOperator)) {
            entityOperator = defaultUsername;
        }
        return entityOperator;
    }
}
