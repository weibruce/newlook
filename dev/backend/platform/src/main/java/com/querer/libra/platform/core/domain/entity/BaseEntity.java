/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.domain.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Base entity with version enabled and extends Number base primary key from
 * GenericEntity.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /* fields ------------------------------------------------------ */
    private static final long serialVersionUID = 2680900072955325639L;
}
