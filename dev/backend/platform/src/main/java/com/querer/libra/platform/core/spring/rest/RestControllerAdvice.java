/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest;

import java.lang.reflect.Method;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.querer.libra.platform.core.spring.rest.annotation.RestPayload;
import com.querer.libra.platform.core.spring.rest.model.RestResponseEnvelope;

/**
 * Controller advice implementation for rest response.
 */
@ControllerAdvice
public class RestControllerAdvice implements ResponseBodyAdvice<Object> {

    /* public methods ------------------------------------------------------ */

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        Method restMethod = returnType.getMethod();
        RestPayload restPayload = restMethod.getAnnotation(RestPayload.class);
        if (restPayload != null && restPayload.rawMode()) {
            return body;
        } else {
            return body instanceof RestResponseEnvelope ? body : new RestResponseEnvelope(body);
        }
    }
}
