/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.orm.hibernate;

import com.querer.libra.platform.core.domain.entity.BaseAuditableEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

/**
 * Class description goes here. Refer
 * http://anshuiitk.blogspot.com/2010/11/hibernate-pre-database-opertaion-event.html
 */
public class EntityOperationListener implements PreInsertEventListener, PreUpdateEventListener {

    /* fields ------------------------------------------------------ */
    private final static Log logger = LogFactory.getLog(EntityOperationListener.class);

    protected final static String DEFAULT_CREATE_OPERATOR = "default create operator (-1)";
    protected final static String DEFAULT_UPDATE_OPERATOR = "default update operator (-2)";

    /* public methods ------------------------------------------------------ */
    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        BaseAuditableEntity auditableEntity = isBaseAuditableEntity(event.getEntity());
        if (auditableEntity == null)
            return false;

        String entityOperator = getInsertOperator();
        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
        // inserts
        auditableEntity.setCreateBy(entityOperator);
        setValue(state, propertyNames, BaseAuditableEntity.CREATE_BY, entityOperator, auditableEntity);

        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        BaseAuditableEntity auditableEntity = isBaseAuditableEntity(event.getEntity());
        if (auditableEntity == null)
            return false;

        String entityOperator = getUpdateOperator();

        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
        // inserts
        setValue(state, propertyNames, BaseAuditableEntity.CREATE_BY, auditableEntity.getCreateBy(), auditableEntity);
        // updates
        auditableEntity.setUpdateBy(entityOperator);
        setValue(state, propertyNames, BaseAuditableEntity.UPDATE_BY, entityOperator, auditableEntity);

        return false;
    }

    /*
     * protected methods ------------------------------------------------------
     */

    /**
     * Get operator name on insert op.
     *
     * @return name of insert operator
     */
    protected String getInsertOperator() {
        return DEFAULT_CREATE_OPERATOR;
    }

    /**
     * Get operator name on update op.
     *
     * @return name of update operator
     */
    protected String getUpdateOperator() {
        return DEFAULT_UPDATE_OPERATOR;
    }

    /**
     * Determine object is instance of BaseAuditableEntity.
     *
     * @param object
     *            target object to be converted
     * @return converted entity otherwise return null
     */
    protected BaseAuditableEntity isBaseAuditableEntity(Object object) {
        BaseAuditableEntity entity = null;
        if (object instanceof BaseAuditableEntity) {
            entity = (BaseAuditableEntity) object;
        }
        return entity;
    }

    /**
     * Set value in hibernate state so that persistance can be effected.
     */
    protected void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value,
            Object entity) {
        int index = ArrayUtils.indexOf(propertyNames, propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        } else {
            logger.error("Field '" + propertyToSet + "' not found on entity '" + entity.getClass().getName() + "'.");
        }
    }
}
