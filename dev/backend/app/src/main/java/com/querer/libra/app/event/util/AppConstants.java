/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.util;

import org.apache.commons.lang3.StringUtils;

/**
 * All constants used in this app defined here.
 */
public final class AppConstants {

    // Unique query result
    public static final int QUERY_UNIQUE_RESULT = 1;

    // software delete flag. Entity is active or inactive.
    public static final Boolean ENTITY_ACTIVE = Boolean.TRUE;
    public static final Boolean ENTITY_INACTIVE = Boolean.FALSE;

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
    public static final String DASH = "-";
    public static final String COLON = ":";
    public static final String PERCENT_SIGN = "%";
    public static final String SEMICOLON = ";";
    public static final String UP_RIGHT_SLASH = "|";
    public static final String SPACE = " ";

    // wechat integration
    public final static String SITE_DOMAIN = "http://www.5hac.com";
    public final static String REDIRECT_URL = SITE_DOMAIN + "/querer/service/wechat/access-token";
    public final static String SCOPE_BASE = "snsapi_base";
    public final static String SCOPE_USERINFO = "snsapi_userinfo";

    public final static String SESSION_KEY_ACCESS_TOKEN = "com.querer.newlook.security.wechat.access.token.session.key";
    public final static String SESSION_KEY_OPENID = "com.querer.newlook.security.wechat.openid.session.key";

    public final static String OPENID_COOKIE_NAME = "com.querer.newlook.security.wechat.openid.cookie.name";
}
