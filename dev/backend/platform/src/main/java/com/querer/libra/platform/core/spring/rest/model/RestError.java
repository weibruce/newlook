/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest.model;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 * RestError model for rest error response object.
 */
public class RestError {

    /* fields ------------------------------------------------------ */

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
    private final String developerMessage;
    private final String moreInfoUrl;
    private final Throwable throwable;

    /* constructors ------------------------------------------------------ */

    public RestError(HttpStatus status, String errorCode, String message, String developerMessage, String moreInfoUrl,
            Throwable throwable) {
        if (status == null) {
            throw new NullPointerException("HttpStatus argument cannot be null.");
        }
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.developerMessage = developerMessage;
        this.moreInfoUrl = moreInfoUrl;
        this.throwable = throwable;
    }

    /* public methods ------------------------------------------------------ */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RestError) {
            RestError re = (RestError) o;
            return ObjectUtils.nullSafeEquals(getStatus(), re.getStatus()) && getErrorCode() == re.getErrorCode()
                    && ObjectUtils.nullSafeEquals(getMessage(), re.getMessage())
                    && ObjectUtils.nullSafeEquals(getDeveloperMessage(), re.getDeveloperMessage())
                    && ObjectUtils.nullSafeEquals(getMoreInfoUrl(), re.getMoreInfoUrl())
                    && ObjectUtils.nullSafeEquals(getThrowable(), re.getThrowable());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(new Object[] { getStatus(), getErrorCode(), getMessage(),
                getDeveloperMessage(), getMoreInfoUrl(), getThrowable() });
    }

    public String toString() {
        return new StringBuilder().append(getStatus().value()).append(" (").append(getStatus().getReasonPhrase())
                .append(" )").toString();
    }

    /* getters/setters ------------------------------------------------------ */

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}