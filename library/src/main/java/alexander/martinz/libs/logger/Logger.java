/*
 * Copyright (C) 2013 - 2015 Alexander Martinz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package alexander.martinz.libs.logger;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

/**
 * A Logging utility
 */
public class Logger {
    /**
     * If disabled nothing will happen at all
     */
    private static boolean SHOULD_LOG = BuildConfig.DEBUG;

    /**
     * If disabled, the stack trace lookup will be skipped
     */
    private static boolean LOOKUP_DISABLED = false;

    public static final int LOOKUP_TYPE_FULL = 0;
    public static final int LOOKUP_TYPE_SHORT = 1;

    private static int LOOKUP_TYPE = LOOKUP_TYPE_SHORT;

    public static final char VERBOSE = 'v';
    public static final char DEBUG = 'd';
    public static final char INFO = 'i';
    public static final char WARN = 'w';
    public static final char ERROR = 'e';
    public static final char WTF = '?';

    private static final Object[] NULL_OBJECTS = new Object[0];

    /**
     * Enables or disables logging. If disabled all operations will not do anything and return early.
     *
     * @param enable True to enable, false to disable the logger.
     */
    public static synchronized void setEnabled(final boolean enable) {
        SHOULD_LOG = enable;
    }

    /**
     * @return Whether the logger is enabled.
     */
    public static boolean getEnabled() {
        return SHOULD_LOG;
    }

    /**
     * Enables or disables stack trace lookups.<p>
     * When enabled the log line will contain the class name and line number of the method call. <p>
     *
     * @param disableLookup True to disable, false to enable.
     */
    public static synchronized void setLookupDisabled(final boolean disableLookup) {
        LOOKUP_DISABLED = disableLookup;
    }

    /**
     * @return Whether stack trace lookup is enabled or not.
     */
    public static boolean isLookupDisabled() {
        return LOOKUP_DISABLED;
    }

    public static synchronized void setLookupType(final int lookupType) {
        LOOKUP_TYPE = lookupType;
    }

    public static int getLookupType() {
        return LOOKUP_TYPE;
    }

    public static void d(final Object object, final String msg) {
        d(object, msg, NULL_OBJECTS);
    }

    public static void d(final Object object, final String msg, final Object... objects) {
        if (SHOULD_LOG) {
            log(DEBUG, getTag(object), getMessage(msg, objects));
        }
    }

    public static void d(final Object object, final String msg, final Exception exception) {
        if (SHOULD_LOG) {
            log(DEBUG, getTag(object), getMessage(msg), exception);
        }
    }

    public static void e(final Object object, final String msg) {
        e(getTag(object), getMessage(msg), NULL_OBJECTS);
    }

    public static void e(final Object object, final String msg, final Object... objects) {
        if (SHOULD_LOG) {
            log(ERROR, getTag(object), getMessage(msg, objects));
        }
    }

    public static void e(final Object object, final String msg, final Exception exception) {
        if (SHOULD_LOG) {
            log(ERROR, getTag(object), getMessage(msg), exception);
        }
    }

    public static void i(final Object object, final String msg) {
        i(getTag(object), getMessage(msg), NULL_OBJECTS);
    }

    public static void i(final Object object, final String msg, final Object... objects) {
        if (SHOULD_LOG) {
            log(INFO, getTag(object), getMessage(msg, objects));
        }
    }

    public static void i(final Object object, final String msg, final Exception exception) {
        if (SHOULD_LOG) {
            log(INFO, getTag(object), getMessage(msg), exception);
        }
    }

    public static void v(final Object object, final String msg) {
        v(getTag(object), getMessage(msg), NULL_OBJECTS);
    }

    public static void v(final Object object, final String msg, final Object... objects) {
        if (SHOULD_LOG) {
            log(VERBOSE, getTag(object), getMessage(msg, objects));
        }
    }

    public static void v(final Object object, final String msg, final Exception exception) {
        if (SHOULD_LOG) {
            log(VERBOSE, getTag(object), getMessage(msg), exception);
        }
    }

    public static void w(final Object object, final String msg) {
        w(getTag(object), getMessage(msg), NULL_OBJECTS);
    }

    public static void w(final Object object, final String msg, final Object... objects) {
        if (SHOULD_LOG) {
            log(WARN, getTag(object), getMessage(msg, objects));
        }
    }

    public static void w(final Object object, final String msg, final Exception exception) {
        if (SHOULD_LOG) {
            log(WARN, getTag(object), getMessage(msg), exception);
        }
    }

    public static void wtf(final Object object, final String msg) {
        wtf(getTag(object), getMessage(msg), NULL_OBJECTS);
    }

    public static void wtf(final Object object, final String msg, final Object... objects) {
        if (SHOULD_LOG) {
            log(WTF, getTag(object), getMessage(msg, objects));
        }
    }

    public static void wtf(final Object object, final String msg, final Exception exception) {
        if (SHOULD_LOG) {
            log(WTF, getTag(object), getMessage(msg), exception);
        }
    }

    public static void log(final char type, final String tag, final String msg) {
        log(type, tag, msg, null);
    }

    public static void log(final char type, final String tag, final String msg, final Exception exception) {
        switch (type) {
            case VERBOSE: {
                if (exception != null) {
                    Log.v(tag, msg, exception);
                } else {
                    Log.v(tag, msg);
                }
                break;
            }
            case DEBUG: {
                if (exception != null) {
                    Log.d(tag, msg, exception);
                } else {
                    Log.d(tag, msg);
                }
                break;
            }
            case INFO: {
                if (exception != null) {
                    Log.i(tag, msg, exception);
                } else {
                    Log.i(tag, msg);
                }
                break;
            }
            case WARN: {
                if (exception != null) {
                    Log.w(tag, msg, exception);
                } else {
                    Log.w(tag, msg);
                }
                break;
            }
            case ERROR: {
                if (exception != null) {
                    Log.e(tag, msg, exception);
                } else {
                    Log.e(tag, msg);
                }
                break;
            }
            case WTF: {
                if (exception != null) {
                    Log.wtf(tag, msg, exception);
                } else {
                    Log.wtf(tag, msg);
                }
                break;
            }
        }
    }

    /**
     * Turns the passed in object into a string, representing a "Tag". <br/>
     * If a string is passed in we are returning the string, else we are getting the simple name
     * of the object as "Tag".
     *
     * @param object The object, representing a Tag
     * @return The object as Tag
     */
    public static String getTag(final Object object) {
        if (object instanceof String) {
            return ((String) object);
        } else if (object == null) {
            if (LOOKUP_DISABLED) {
                return "Logger";
            }
            final StackTraceElement stackTraceElement = getCurrentStackTrace();
            return ((stackTraceElement != null) ? stackTraceElement.getClassName() : "Logger");
        } else {
            return object.getClass().getSimpleName();
        }
    }

    public static String getMessage(String msg, final Object... objects) {
        if (objects != null && objects.length > 0) {
            msg = String.format(msg, objects);
        }
        final String sourceFileLocation = (LOOKUP_DISABLED ? "" : getSourceFileLocation());
        return String.format("%s --> %s", sourceFileLocation, msg);
    }

    private static StackTraceElement getCurrentStackTrace() {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements == null || stackTraceElements.length < 6) {
            return null;
        }

        return stackTraceElements[6];
    }

    public static String getSourceFileLocation() {
        final StackTraceElement stackTrace = getCurrentStackTrace();
        if (stackTrace == null) {
            return "";
        }
        final String className = stackTrace.getFileName();
        final String methodName = stackTrace.getMethodName();
        final int lineNumber = stackTrace.getLineNumber();

        final StringBuilder sb = new StringBuilder();
        if (LOOKUP_TYPE == LOOKUP_TYPE_FULL) {
            sb.append('[');
        }
        sb.append('(').append(className).append(':').append(lineNumber).append(')');
        if (LOOKUP_TYPE == LOOKUP_TYPE_FULL) {
            sb.append('#').append(methodName).append(']');
        }
        return sb.toString();
    }

    /**
     * If enabled we are logging every violation at the log. Shall NOT be turned on in production!
     *
     * @param enabled True to enable {@link android.os.StrictMode.ThreadPolicy} and {@link android.os.StrictMode.VmPolicy} detection
     */
    public static void setStrictModeEnabled(boolean enabled) {
        StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
        StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();

        if (enabled) {
            threadBuilder
                    .detectAll()
                    .detectCustomSlowCalls()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .penaltyFlashScreen();

            vmBuilder
                    .detectAll()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vmBuilder.detectLeakedRegistrationObjects();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    vmBuilder.detectFileUriExposure();
                }
            }
        }

        StrictMode.setThreadPolicy(threadBuilder.build());
        StrictMode.setVmPolicy(vmBuilder.build());
    }
}
