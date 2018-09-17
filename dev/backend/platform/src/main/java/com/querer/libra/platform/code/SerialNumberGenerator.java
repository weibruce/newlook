/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.code;

/**
 * Serial Number (SN) generation interface definition. SN is common to used for
 * various object in app, like transaction, order, case...etc.
 */
public interface SerialNumberGenerator {

    /**
     * Generate sn in the air without any param.
     *
     * @return the generated sn
     */
    String generate();

    /**
     * Generate sn with object oid.
     *
     * @param id
     *            based on object oid to generate sn
     * @return the generated sn number
     */
    String generateById(Long id);

    /**
     * Generate sn with source/target object oid, two sides of business.
     *
     * @param id1
     *            biz id1
     * @param id2
     *            biz id2
     * @return the generated sn number
     */
    String generateByIds(Long id1, Long id2);
}
