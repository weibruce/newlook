/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring.rest.error;

import org.springframework.http.HttpStatus;

import com.querer.libra.platform.core.spring.rest.model.RestError;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple {@code RestErrorConverter} implementation that creates a new Map
 * instance based on the specified RestError instance. Some
 * {@link org.springframework.http.converter.HttpMessageConverter
 * HttpMessageConverter}s (like a JSON converter) can easily automatically
 * convert Maps to response bodies. The map is populated with the following
 * default name/value pairs:
 * <p>
 * <table>
 * <tr>
 * <th>Key (a String)</th>
 * <th>Value (an Object)</th>
 * <th>Notes</th>
 * </tr>
 * <tr>
 * <td>status</td>
 * <td>restError.{@link RestError#getStatus()
 * getStatus()}.{@link org.springframework.http.HttpStatus#value() value()}</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>code</td>
 * <td>restError.{@link RestError#getErrorCode()}</td>
 * <td>Only set if {@code code > 0}</td>
 * </tr>
 * <tr>
 * <td>message</td>
 * <td>restError.{@link RestError#getMessage() getMessage()}</td>
 * <td>Only set if {@code message != null}</td>
 * </tr>
 * <tr>
 * <td>developerMessage</td>
 * <td>restError.{@link RestError#getDeveloperMessage()
 * getDeveloperMessage()}</td>
 * <td>Only set if {@code developerMessage != null}</td>
 * </tr>
 * <tr>
 * <td>moreInfo</td>
 * <td>restError.{@link RestError#getMoreInfoUrl() getMoreInfoUrl()}</td>
 * <td>Only set if {@code moreInfoUrl != null}</td>
 * </tr>
 * </table>
 * <p>
 * The map key names are customizable via setter methods (setStatusKey,
 * setMessageKey, etc).
 */
public class MapRestErrorConverter implements RestErrorConverter<Map> {
    /* public methods ------------------------------------------------------ */

    @Override
    public Map convert(RestError restError) {
        Map<String, Object> m = new LinkedHashMap<>();

        HttpStatus status = restError.getStatus();
        m.put(RestErrorConstants.DEFAULT_STATUS_KEY, status.value());

        String errorCode = restError.getErrorCode();
        if (errorCode != null) {
            m.put(RestErrorConstants.DEFAULT_ERROR_CODE_KEY, errorCode);
        }

        String message = restError.getMessage();
        if (message != null) {
            m.put(RestErrorConstants.DEFAULT_MESSAGE_KEY, message);
        }

        String developerMessage = restError.getDeveloperMessage();
        if (developerMessage != null) {
            m.put(RestErrorConstants.DEFAULT_DEVELOPER_MESSAGE_KEY, developerMessage);
        }

        String moreInfoUrl = restError.getMoreInfoUrl();
        if (moreInfoUrl != null) {
            m.put(RestErrorConstants.DEFAULT_MORE_INFO_URL_KEY, moreInfoUrl);
        }

        return m;
    }
}
