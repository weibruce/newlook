/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest.model;

import org.springframework.http.HttpStatus;

/**
 * Builder for RestError
 */
public class RestErrorBuilder {

    /* fields ------------------------------------------------------ */

    private HttpStatus status;
    private String errorCode;
    private String message;
    private String developerMessage;
    private String moreInfoUrl;
    private Throwable throwable;

    /* public methods ------------------------------------------------------ */

    public RestErrorBuilder setStatus(int statusCode) {
        this.status = HttpStatus.valueOf(statusCode);
        return this;
    }

    public RestErrorBuilder setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public RestErrorBuilder setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public RestErrorBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public RestErrorBuilder setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }

    public RestErrorBuilder setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
        return this;
    }

    public RestErrorBuilder setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public RestError build() {
        if (this.status == null) {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new RestError(this.status, this.errorCode, this.message, this.developerMessage, this.moreInfoUrl,
                this.throwable);
    }
}
