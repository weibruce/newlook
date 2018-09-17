/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.exception;

import com.querer.libra.platform.core.exception.BusinessException;

public class SharedEventNotFoundException extends BusinessException {

    /* constructors -------------------------------------------------------- */

    public SharedEventNotFoundException(String message) {
        super(message);
    }

    public SharedEventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
