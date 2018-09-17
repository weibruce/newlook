/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * RestStatus enumeration. Status of rest result status.
 */
public enum RestStatus {

    /* enum definition ------------------------------------------------------ */

    SUCCESS("success"), FAILED("failed"), ERROR("error");

    /* fields ------------------------------------------------------ */

    private String code; // unique code per enum term

    /* constructors ------------------------------------------------------ */

    /**
     * Private constructor for enum only.
     *
     * @param code
     *            target unique code
     */
    RestStatus(String code) {
        this.code = code;
    }

    /* public methods ------------------------------------------------------ */

    /**
     * Find enum element by string code.
     *
     * @param code
     *            the code value of enum element
     * @return return enum element if found otherwise null
     */
    public static RestStatus fromCode(String code) {
        Optional<RestStatus> result = Arrays.stream(RestStatus.values())
                .filter(e -> StringUtils.equalsIgnoreCase(e.getCode(), code)).findFirst();
        return result.orElse(null);
    }

    /**
     * Get unique enum code for one term.
     *
     * @return unique enum code for one term
     */
    public String code() {
        return this.code;
    }

    /**
     * Get unique enum code for one term.
     *
     * @return unique enum code for one term
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Override toString()
     *
     * @return code to string for term
     */
    public String toString() {
        return this.code;
    }
}
