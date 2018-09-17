/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest;

import com.querer.libra.platform.core.exception.BusinessException;
import com.querer.libra.platform.core.spring.rest.error.DefaultRestErrorResolver;
import com.querer.libra.platform.core.spring.rest.error.MapRestErrorConverter;
import com.querer.libra.platform.core.spring.rest.error.RestErrorConverter;
import com.querer.libra.platform.core.spring.rest.error.RestErrorResolver;
import com.querer.libra.platform.core.spring.rest.model.RestError;
import com.querer.libra.platform.core.spring.rest.model.RestResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Renders a response with a RESTful Error representation based on the error format
 */
public class RestExceptionHandler extends AbstractHandlerExceptionResolver implements InitializingBean {
    /* fields ------------------------------------------------------ */

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    private HttpMessageConverter<?>[] messageConverters = null;

    private List<HttpMessageConverter<?>> allMessageConverters = null;

    private RestErrorResolver errorResolver;

    private RestErrorConverter<?> errorConverter;

    private Boolean logBusinessException = false;

    /* constructors ------------------------------------------------------ */

    public RestExceptionHandler() {
        // enable log at warn level by default
        setWarnLogCategory(this.getClass().getName());

        this.errorResolver = new DefaultRestErrorResolver();
        this.errorConverter = new MapRestErrorConverter();
    }

    /* public methods ------------------------------------------------------ */

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();

        // user configured values take precedence:
        if (this.messageConverters != null && this.messageConverters.length > 0) {
            converters.addAll(CollectionUtils.arrayToList(this.messageConverters));
        }

        // defaults next:
        new HttpMessageConverterHelper().addDefaults(converters);

        this.allMessageConverters = converters;
    }

    /* protected methods --------------------------------------------------- */

    /**
     * Actually resolve the given exception that got thrown during on handler
     * execution, returning a ModelAndView that represents a specific error page
     * if appropriate.
     * <p>
     * May be overridden in subclasses, in order to apply specific exception
     * checks. Note that this template method will be invoked <i>after</i>
     * checking whether this resolved applies ("mappedHandlers" etc), so an
     * implementation may simply proceed with its actual exception handling.
     *
     * @param request
     *            current HTTP request
     * @param response
     *            current HTTP response
     * @param handler
     *            the executed handler, or <code>null</code> if none chosen at
     *            the time of the exception (for example, if multipart
     *            resolution failed)
     * @param exception
     *            the exception that got thrown during handler execution
     * @return a corresponding ModelAndView to forward to, or <code>null</code>
     *         for default processing
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) {

        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        RestErrorResolver resolver = getErrorResolver();

        RestError error = resolver.resolveError(webRequest, handler, exception);
        if (error == null) {
            return null;
        }

        ModelAndView mav = null;
        try {
            mav = getModelAndView(webRequest, handler, error);
        } catch (Exception invocationEx) {
            log.error("Acquiring ModelAndView for Exception [{}] resulted in an exception {}.", exception, invocationEx);
        }
        return mav;
    }

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        if (!this.logBusinessException && ex instanceof BusinessException) {
            return;
        } else {
            super.logException(ex, request);
            if (log.isInfoEnabled()) {
                log.info("exception stacktrace: ", ex);
            }
        }
    }

    /* private methods ------------------------------------------------------ */

    private ModelAndView getModelAndView(ServletWebRequest webRequest, Object handler, RestError restError)
            throws Exception {
        applyStatusIfPossible(webRequest, restError);

        RestResponseEnvelope restResponseEnvelope = new RestResponseEnvelope(false);
        HttpStatus httpStatus = restError.getStatus();
        restResponseEnvelope
                .setStatus(httpStatus != null ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());

        Object error = restError; // default the error instance in case they
                                  // don't configure an error converter
        RestErrorConverter converter = getErrorConverter();
        if (converter != null) {
            error = converter.convert(restError);
        }
        restResponseEnvelope.addError(error);

        Object body = restResponseEnvelope;
        return handleResponseBody(body, webRequest);
    }

    private void applyStatusIfPossible(ServletWebRequest webRequest, RestError error) {
        if (!WebUtils.isIncludeRequest(webRequest.getRequest())) {
            webRequest.getResponse().setStatus(error.getStatus().value());
        }
    }

    private ModelAndView handleResponseBody(Object body, ServletWebRequest webRequest)
            throws ServletException, IOException {

        HttpInputMessage inputMessage = new ServletServerHttpRequest(webRequest.getRequest());

        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        if (acceptedMediaTypes.isEmpty()) {
            acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
        }

        MediaType.sortByQualityValue(acceptedMediaTypes);

        HttpOutputMessage outputMessage = new ServletServerHttpResponse(webRequest.getResponse());

        Class<?> bodyType = body.getClass();

        List<HttpMessageConverter<?>> converters = this.allMessageConverters;

        if (converters != null) {
            for (MediaType acceptedMediaType : acceptedMediaTypes) {
                for (HttpMessageConverter messageConverter : converters) {
                    if (messageConverter.canWrite(bodyType, acceptedMediaType)) {
                        messageConverter.write(body, acceptedMediaType, outputMessage);
                        // return empty model and view to short circuit the
                        // iteration and to let
                        // Spring know that we've rendered the view ourselves:
                        return new ModelAndView();
                    }
                }
            }
        }

        log.warn("Could not find HttpMessageConverter that supports return type [{}] and {}", bodyType,
                acceptedMediaTypes);

        return null;
    }

    /* getters/setters ------------------------------------------------------ */

    public void setMessageConverters(HttpMessageConverter<?>[] messageConverters) {
        this.messageConverters = messageConverters;
    }

    public RestErrorResolver getErrorResolver() {
        return this.errorResolver;
    }

    public void setErrorResolver(RestErrorResolver errorResolver) {
        this.errorResolver = errorResolver;
    }

    public RestErrorConverter<?> getErrorConverter() {
        return errorConverter;
    }

    public void setErrorConverter(RestErrorConverter<?> errorConverter) {
        this.errorConverter = errorConverter;
    }

    // leverage Spring's existing default setup behavior:
    private static final class HttpMessageConverterHelper extends WebMvcConfigurationSupport {
        public void addDefaults(List<HttpMessageConverter<?>> converters) {
            addDefaultHttpMessageConverters(converters);
        }
    }

    public Boolean getLogBusinessException() {
        return logBusinessException;
    }

    public void setLogBusinessException(Boolean logBusinessException) {
        this.logBusinessException = logBusinessException;
    }
}
