/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.model;

/**
 * Image clipping quality level definition, from 10 (lowest) ~ 90 (highest).
 */
public enum ImageQualityType {

    /* enum definition ------------------------------------------------------ */

    Q10("q10"), // the lowest quality
    Q20("q20"), Q30("q30"), Q40("q40"), Q50("q50"), // the common used quality
    Q60("q60"), Q70("q70"), Q80("q80"), Q90("q90"); // the top quality

    /* fields ------------------------------------------------------ */

    private String code; // unique code per enum term

    /* constructors ------------------------------------------------------ */

    /**
     * Private constructor for enum only.
     *
     * @param code
     *            target unique code
     */
    ImageQualityType(String code) {
        this.code = code;
    }

    /* public methods ------------------------------------------------------ */

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
