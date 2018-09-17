/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Envelope for rest result.
 */
public class RestResponseEnvelope implements Serializable {

    /* fields ------------------------------------------------------ */

    private static final long serialVersionUID = -4543091673496753902L;

    private boolean success = false;

    private int status;

    private Object data; // always display data attribute

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;

    /* constructors ------------------------------------------------------ */

    public RestResponseEnvelope(boolean success) {
        this.success = success;
    }

    public RestResponseEnvelope(Object data) {
        this.success = true;
        this.status = HttpStatus.OK.value();
        this.data = data;
    }

    /* public methods ------------------------------------------------------ */

    public RestResponseEnvelope addError(Object error) {
        this.error = error;
        return this;
    }

    /* getters/setters ------------------------------------------------------ */

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
