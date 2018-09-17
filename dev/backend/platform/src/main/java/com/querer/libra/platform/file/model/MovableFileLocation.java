/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.model;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.querer.libra.platform.util.constant.PlatformConstants;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * File location used for moving files in FILE IO ops, like file uploading, file
 * copying, file moving...etc.
 */
public class MovableFileLocation {

    /* fields ------------------------------------------------------ */

    private String source; // source of file path
    private String destination; // destination of file path

    /* constructors ------------------------------------------------------ */

    public MovableFileLocation() {
    }

    public MovableFileLocation(String source, String destination) {
        this.source = normalizeFileLocation(source);
        this.destination = normalizeFileLocation(destination);
    }

    /* public methods ------------------------------------------------------ */

    /**
     * Normalize file path string in case string containing empty space chars in
     * path.
     *
     * @param location
     *            target file path to be normalized. Location is a comma
     *            separator string with multiple values.
     * @return normalized (trim) path
     */
    public String normalizeFileLocation(String location) {
        String result = null;
        String trimLocation = StringUtils.trimToEmpty(location);
        if (StringUtils.isNotBlank(trimLocation)) {
            String[] locations = StringUtils.split(StringUtils.trimToNull(trimLocation), PlatformConstants.COMMA);
            if (ArrayUtils.isNotEmpty(locations)) {
                result = Arrays.stream(locations).map(e -> StringUtils.trimToEmpty(e))
                        .collect(Collectors.joining(PlatformConstants.COMMA));
            }
        }

        return result;
    }

    /* getters/setters ------------------------------------------------------ */

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = normalizeFileLocation(source);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = normalizeFileLocation(destination);
    }
}
