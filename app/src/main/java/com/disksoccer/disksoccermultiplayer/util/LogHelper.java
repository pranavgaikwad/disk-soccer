package com.disksoccer.disksoccermultiplayer.util;

import android.util.Log;

/**
 * @author Pranav
 *         <h1>Log Helper Class</h1>
 *         All logs are to be printed using this class as future provisions can be made to
 *         log details on remote server in order to get bug reports.
 */
public class LogHelper {

    // log keys
    public static final String T_EXCEPTION = "<exception>";
    public static final String T_GENERAL = "<general>";

    public static final void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static final void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static final void v(String tag, String msg) {
        Log.v(tag, msg);
    }
}
