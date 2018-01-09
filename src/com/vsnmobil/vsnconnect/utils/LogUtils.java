/**
 * @author    Rajiv Manivannan <rajiv@contus.in>
 * @copyright  Copyright (C) 2014 VSNMobil. All rights reserved.
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 */
package com.vsnmobil.vsnconnect.utils;

import android.util.Log;

import com.vsnmobil.vsnconnect.BuildConfig;

/**
 * LogUtils.java 
 * To get the different types of log in the application we can use this LogUtils class.
 * By default before creating the signed APK the application will be in debug-able mode
 * Once, you created the signed APK, SDK itself change to debug-able false.Since,we have 
 * checked the condition BuildConfig.DEBUG and showing log.
 */
public class LogUtils {

    private static final String LOG_PREFIX = "VSN_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class<?> cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message, cause);
        }
    }

    public static void LOGV(final String tag, String message) {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void LOGI(final String tag, String message) {
        if (BuildConfig.DEBUG) {
        Log.i(tag, message);
        }
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
        Log.i(tag, message, cause);
        }
    }

    public static void LOGW(final String tag, String message) {
        if (BuildConfig.DEBUG) {
        Log.w(tag, message);
        }
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
        Log.w(tag, message, cause);
        }
    }

    public static void LOGE(final String tag, String message) {
        if (BuildConfig.DEBUG) {
        Log.e(tag, message);
        }
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
        Log.e(tag, message, cause);
        }
    }

    private LogUtils() {
    }
}
