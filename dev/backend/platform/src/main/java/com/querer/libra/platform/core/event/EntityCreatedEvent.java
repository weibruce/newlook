/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * Entity created event in application level. Used for event handlers that need
 * to do special logic when target entity is created in application
 */
public class EntityCreatedEvent<T> implements ResolvableTypeProvider {

    /* fields -------------------------------------------------------------- */

    private T entity;

    /* constructors -------------------------------------------------------- */

    public EntityCreatedEvent(T entity) {
        this.entity  = entity;
    }

    /* public methods ------------------------------------------------------ */

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getEntity()));
    }

    /* getters/setters ----------------------------------------------------- */

    public T getEntity() {
        return entity;
    }
}
