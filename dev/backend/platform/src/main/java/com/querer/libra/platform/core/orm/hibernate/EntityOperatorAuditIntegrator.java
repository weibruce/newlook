/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.orm.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 * Hibernate integrator to register events.
 */
public class EntityOperatorAuditIntegrator implements Integrator {

    /* public methods ------------------------------------------------------ */

    /**
     * @see Integrator#integrate(Metadata, SessionFactoryImplementor, SessionFactoryServiceRegistry)
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        final EventListenerRegistry eventListenerRegistry = sessionFactoryServiceRegistry.getService(EventListenerRegistry.class);

        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, ShiroEntityOperationListener.class);
        eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, ShiroEntityOperationListener.class);
    }

    /**
     * @see Integrator#disintegrate(SessionFactoryImplementor, SessionFactoryServiceRegistry)
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    }

}
