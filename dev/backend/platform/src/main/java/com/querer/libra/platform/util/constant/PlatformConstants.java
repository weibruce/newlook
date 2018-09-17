/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.util.constant;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * All constants defined here.
 */
public final class PlatformConstants {
    // app launch date
    public static final long APP_LAUNCH_EPOCH_SECOND = LocalDateTime.of(2016, 4, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
            .getEpochSecond();

    // Switcher on/off setting
    public static final Boolean SWITCHER_ON = Boolean.TRUE;
    public static final Boolean SWITCHER_OFF = Boolean.FALSE;

    // Chars
    public static final String EMPTY_STRING = StringUtils.EMPTY;
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String SLASH = "/";
    public static final String BACK_SLASH = "\\";
    public static final String UNDERSCORE = "_";
    public static final String INTEGER_ZERO = "0";
}
