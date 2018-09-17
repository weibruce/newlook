/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.querer.libra.platform.core.spring.rest.model.RestError;
import com.querer.libra.platform.core.spring.rest.model.RestErrorBuilder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Default {@code RestErrorResolver} implementation that converts discovered
 * Exceptions to {@link RestError} instances.
 */
public class DefaultRestErrorResolver implements RestErrorResolver, MessageSourceAware, InitializingBean {

    /* fields -------------------------------------------------------------- */

    private static final Logger log = LoggerFactory.getLogger(DefaultRestErrorResolver.class);

    private String developmentMode;

    private Map<String, RestError> exceptionMappings = Collections.emptyMap();

    private Map<String, String> exceptionMappingDefinitions = Collections.emptyMap();

    private MessageSource messageSource;
    private LocaleResolver localeResolver;

    private boolean defaultEmptyCodeToStatus;
    private String defaultDeveloperMessage;
    private String defaultMoreInfoUrl;

    /* constructors -------------------------------------------------------- */

    public DefaultRestErrorResolver() {
        this.defaultEmptyCodeToStatus = true;
        this.defaultDeveloperMessage = RestErrorConstants.DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }

    private static int getRequiredInt(String key, String value) {
        try {
            int anInt = Integer.valueOf(value);
            return Math.max(-1, anInt);
        } catch (NumberFormatException e) {
            String message = "Configuration element '" + key + "' requires an integer value.  The value "
                    + "specified: " + value;
            throw new IllegalArgumentException(message, e);
        }
    }

    /* public methods ------------------------------------------------------ */

    private static int getInt(String key, String value) {
        try {
            return getRequiredInt(key, value);
        } catch (IllegalArgumentException iae) {
            return 0;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // populate with some defaults:
        Map<String, String> definitions = createDefaultExceptionMappingDefinitions();

        // add in user-specified mappings (will override defaults as necessary):
        if (this.exceptionMappingDefinitions != null && !this.exceptionMappingDefinitions.isEmpty()) {
            definitions.putAll(this.exceptionMappingDefinitions);
        }

        this.exceptionMappings = toRestErrors(definitions);
    }

    @Override
    public RestError resolveError(ServletWebRequest request, Object handler, Exception exception) {

        RestError template = mappingToRestErrorTemplate(exception);
        if (template == null) {
            return null;
        }

        RestErrorBuilder builder = new RestErrorBuilder();
        builder.setStatus(getStatusValue(template, request, exception));
        builder.setErrorCode(getErrorCode(template, request, exception));
        builder.setMoreInfoUrl(getMoreInfoUrl(template, request, exception));
        builder.setThrowable(exception);

        String message = getMessage(template, request, exception);
        if (message != null) {
            builder.setMessage(message);
        }

        if (RestErrorConstants.DEVELOPMENT_MODE_DEBUG.equalsIgnoreCase(getDevelopmentMode())) {
            message = getDeveloperMessage(template, request, exception);
            if (message != null) {
                builder.setDeveloperMessage(message);
            }
        }

        return builder.build();
    }

    /* protected methods ---------------------------------------------------- */

    protected final Map<String, String> createDefaultExceptionMappingDefinitions() {
        Map<String, String> definitionMap = new LinkedHashMap<>();

        // 400
        applyDefinition(definitionMap, HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
        applyDefinition(definitionMap, MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST);
        applyDefinition(definitionMap, TypeMismatchException.class, HttpStatus.BAD_REQUEST);
        applyDefinition(definitionMap, "javax.validation.ValidationException", HttpStatus.BAD_REQUEST);

        // 404
        applyDefinition(definitionMap, NoSuchRequestHandlingMethodException.class, HttpStatus.NOT_FOUND);
        applyDefinition(definitionMap, "org.hibernate.ObjectNotFoundException", HttpStatus.NOT_FOUND);

        // 405
        applyDefinition(definitionMap, HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED);

        // 406
        applyDefinition(definitionMap, HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE);

        // 409
        // can't use the class directly here as it may not be an available
        // dependency:
        applyDefinition(definitionMap, "org.springframework.dao.DataIntegrityViolationException", HttpStatus.CONFLICT);

        // 415
        applyDefinition(definitionMap, HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        return definitionMap;
    }

    protected Map<String, RestError> toRestErrors(Map<String, String> definitionMap) {
        if (CollectionUtils.isEmpty(definitionMap)) {
            return Collections.emptyMap();
        }

        Map<String, RestError> map = new LinkedHashMap<>(definitionMap.size());
        for (Map.Entry<String, String> entry : definitionMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            RestError template = toRestError(value);
            map.put(key, template);
        }

        return map;
    }

    protected RestError toRestError(String exceptionConfig) {
        String[] values = StringUtils.commaDelimitedListToStringArray(exceptionConfig);
        if (values == null || values.length == 0) {
            throw new IllegalStateException(
                    "Invalid config mapping.  Exception names must map to a string configuration.");
        }

        RestErrorBuilder builder = new RestErrorBuilder();

        boolean statusSet = false;
        boolean errorCodeSet = false;
        boolean messageSet = false;
        boolean developerMessageSet = false;
        boolean moreInfoSet = false;

        for (String value : values) {

            String trimmedVal = StringUtils.trimWhitespace(value);

            // check to see if the value is an explicitly named key/value pair:
            String[] pair = StringUtils.split(trimmedVal, "=");
            if (pair != null) {
                // explicit attribute set:
                String pairKey = StringUtils.trimWhitespace(pair[0]);
                if (!StringUtils.hasText(pairKey)) {
                    pairKey = null;
                }
                String pairValue = StringUtils.trimWhitespace(pair[1]);
                if (!StringUtils.hasText(pairValue)) {
                    pairValue = null;
                }

                if (RestErrorConstants.DEFAULT_STATUS_KEY.equalsIgnoreCase(pairKey)) {
                    int statusCode = getRequiredInt(pairKey, pairValue);
                    builder.setStatus(statusCode);
                    statusSet = true;
                } else if (RestErrorConstants.DEFAULT_ERROR_CODE_KEY.equalsIgnoreCase(pairKey)) {
                    builder.setErrorCode(pairValue);
                    errorCodeSet = true;
                } else if (RestErrorConstants.DEFAULT_MESSAGE_KEY.equalsIgnoreCase(pairKey)) {
                    builder.setMessage(pairValue);
                    messageSet = true;
                } else if (RestErrorConstants.DEFAULT_DEVELOPER_MESSAGE_KEY.equalsIgnoreCase(pairKey)) {
                    builder.setDeveloperMessage(pairValue);
                    developerMessageSet = true;
                } else if (RestErrorConstants.DEFAULT_MORE_INFO_URL_KEY.equalsIgnoreCase(pairKey)) {
                    builder.setMoreInfoUrl(pairValue);
                    moreInfoSet = true;
                }

            } else {
                // not a key/value pair - use heuristics to determine what value
                // is being set:
                int val;
                if (!statusSet) {
                    val = getInt("status", trimmedVal);
                    if (val > 0) {
                        builder.setStatus(val);
                        statusSet = true;
                        continue;
                    }
                }
                if (!errorCodeSet && trimmedVal.toLowerCase().startsWith("0x")) {
                    builder.setErrorCode(trimmedVal);
                    errorCodeSet = true;
                    continue;
                }
                if (!moreInfoSet && trimmedVal.toLowerCase().startsWith("http")) {
                    builder.setMoreInfoUrl(trimmedVal);
                    moreInfoSet = true;
                    continue;
                }
                if (!messageSet) {
                    builder.setMessage(trimmedVal);
                    messageSet = true;
                    continue;
                }
                if (!developerMessageSet) {
                    builder.setDeveloperMessage(trimmedVal);
                    developerMessageSet = true;
                    continue;
                }
                if (!moreInfoSet) {
                    builder.setMoreInfoUrl(trimmedVal);
                    moreInfoSet = true;
                    continue;
                }
            }
        }

        return builder.build();
    }

    protected int getStatusValue(RestError template, ServletWebRequest request, Exception exception) {
        return template.getStatus().value();
    }

    protected String getErrorCode(RestError template, ServletWebRequest request, Exception exception) {
        String errorCode = template.getErrorCode();
        if (errorCode == null && defaultEmptyCodeToStatus) {
            errorCode = String.valueOf(getStatusValue(template, request, exception));
        }
        return errorCode;
    }

    protected String getMoreInfoUrl(RestError template, ServletWebRequest request, Exception exception) {
        String moreInfoUrl = template.getMoreInfoUrl();
        if (moreInfoUrl == null) {
            moreInfoUrl = this.defaultMoreInfoUrl;
        }
        return moreInfoUrl;
    }

    protected String getMessage(RestError template, ServletWebRequest request, Exception exception) {
        return getMessage(template.getMessage(), request, exception);
    }

    protected String getDeveloperMessage(RestError template, ServletWebRequest request, Exception exception) {
        String developerMessage = template.getDeveloperMessage();
        if (developerMessage == null && defaultDeveloperMessage != null) {
            developerMessage = defaultDeveloperMessage;
        }
        if (RestErrorConstants.DEFAULT_MESSAGE_VALUE.equals(developerMessage)) {
            developerMessage = template.getMessage();
        }
        return getMessage(developerMessage, request, exception);
    }

    /**
     * Returns the response status message to return to the client, or
     * {@code null} if no status message should be returned.
     *
     * @return the response status message to return to the client, or
     *         {@code null} if no status message should be returned.
     */
    protected String getMessage(String message, ServletWebRequest webRequest, Exception exception) {

        if (message != null) {
            if (message.equalsIgnoreCase("null") || message.equalsIgnoreCase("off")) {
                return null;
            }
            if (message.equalsIgnoreCase(RestErrorConstants.DEFAULT_EXCEPTION_MESSAGE_VALUE)) {
                message = exception.getMessage();
            }
            if (messageSource != null) {
                Locale locale = null;
                if (localeResolver != null) {
                    locale = localeResolver.resolveLocale(webRequest.getRequest());
                }
                message = messageSource.getMessage(message, null, message, locale);
            }
        }

        return message;
    }

    /* private methods ------------------------------------------------------ */

    private void applyDefinition(Map<String, String> map, Class clazz, HttpStatus status) {
        applyDefinition(map, clazz.getName(), status);
    }

    private void applyDefinition(Map<String, String> map, String key, HttpStatus status) {
        map.put(key, definitionFor(status));
    }

    private String definitionFor(HttpStatus status) {
        return status.value() + ", " + RestErrorConstants.DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }

    /**
     * Returns the config-time 'template' RestError instance configured for the
     * specified Exception, or {@code null} if a match was not found.
     * <p>
     * The config-time template is used as the basis for the RestError
     * constructed at runtime.
     *
     * @param exception
     * @return the template to use for the RestError instance to be constructed.
     */
    private RestError mappingToRestErrorTemplate(Exception exception) {

        Map<String, RestError> mappings = this.exceptionMappings;
        if (CollectionUtils.isEmpty(mappings)) {
            return null;
        }

        RestError template = null;
        String dominantMapping = null;
        int deepest = Integer.MAX_VALUE;
        for (Map.Entry<String, RestError> entry : mappings.entrySet()) {
            String key = entry.getKey();
            int depth = getDepth(key, exception);
            if (depth >= 0 && depth < deepest) {
                deepest = depth;
                dominantMapping = key;
                template = entry.getValue();
            }
        }

        if (template != null) {
            log.debug(
                    "Resolving to RestError template '{}' for exception of type [{}], based on exception mapping [{}]",
                    template, exception.getClass().getName(), dominantMapping);
        }

        return template;
    }

    /**
     * Return the depth to the superclass matching.
     * <p>
     * 0 means ex matches exactly. Returns -1 if there's no match. Otherwise,
     * returns depth. Lowest depth wins.
     */
    private int getDepth(String exceptionMapping, Exception ex) {
        return getDepth(exceptionMapping, ex.getClass(), 0);
    }

    private int getDepth(String exceptionMapping, Class exceptionClass, int depth) {
        if (exceptionClass.getName().contains(exceptionMapping)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
    }

    /* getters/setters ------------------------------------------------------ */

    public String getDevelopmentMode() {
        return developmentMode;
    }

    public void setDevelopmentMode(String developmentMode) {
        this.developmentMode = developmentMode;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLocaleResolver(LocaleResolver resolver) {
        this.localeResolver = resolver;
    }

    public void setExceptionMappingDefinitions(Map<String, String> exceptionMappingDefinitions) {
        this.exceptionMappingDefinitions = exceptionMappingDefinitions;
    }

    public void setDefaultMoreInfoUrl(String defaultMoreInfoUrl) {
        this.defaultMoreInfoUrl = defaultMoreInfoUrl;
    }

    public void setDefaultEmptyCodeToStatus(boolean defaultEmptyCodeToStatus) {
        this.defaultEmptyCodeToStatus = defaultEmptyCodeToStatus;
    }

    public void setDefaultDeveloperMessage(String defaultDeveloperMessage) {
        this.defaultDeveloperMessage = defaultDeveloperMessage;
    }
}
