/*
 * *************************************************************************
 *                         Cherry Corporation
 *           © cherry, (2015). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 * *************************************************************************
 */

package com.querer.libra.platform.core.spring.rest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation used to mark the method needs to do wrap json payload in envelope or not.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface RestPayload {
    /**
     * True return raw payload; false return wrapped payload in envelope.
     *
     * @return payload wrapper strategy
     */
    boolean rawMode() default false;
}
