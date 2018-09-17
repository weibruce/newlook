/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Base runtime exception used internally in current application.
 */
public abstract class BaseRuntimeException extends NestedRuntimeException {

    /* constructors ------------------------------------------------------ */
    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
