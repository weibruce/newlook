/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.exception;

import com.querer.libra.platform.core.exception.SystemException;

/**
 * Exceptions occur while operate against file repository.
 */
public class FileRepositoryException extends SystemException {
    /* constructors    ------------------------------------------------------*/

    public FileRepositoryException(String message) {
        super(message);
    }

    public FileRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
