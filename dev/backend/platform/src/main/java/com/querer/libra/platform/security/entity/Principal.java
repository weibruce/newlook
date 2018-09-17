/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.security.entity;

import com.querer.libra.platform.core.domain.entity.GenericEntity;

/**
 * Base class for the case using external principal. Extend this class based on
 * each app specific requirements.
 * <p>
 * In the case that principal is not kept in local but from external datasource,
 * like from LDAP, remote server or other 3rd party services. Define a subclass
 * to extend this base class to represent remote user principal info. When load
 * user principal from remote successfully, fulfill the principal data in this
 * subclass object and keep in local for further use..
 */
// TODO the hibernate annotation would be removed later since it's used for mock
// data only in demo
// @Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Principal extends GenericEntity {
}
