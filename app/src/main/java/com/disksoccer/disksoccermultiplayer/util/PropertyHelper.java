package com.disksoccer.disksoccermultiplayer.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Pranav
 *         <h1>Property Helper Class</h1>
 *         Helps with the properties (retrieve)
 *         Single instance
 */
public class PropertyHelper {
    // single instance
    private static final PropertyHelper instance = new PropertyHelper();

    // property files
    private static final String PROPERTY_FILE = "app.properties";

    // property keys
    public static final String KEY_WARP_API = "warp_api";
    public static final String KEY_WARP_SECRET = "warp_secret";
    public static final String KEY_FPS_ENGINE = "fps_engine";
    public static final String KEY_FPS_PHYSICS = "fps_physics";

    // instance creation banned
    private PropertyHelper(){}

    /**
     *
     * @return
     */
    public static PropertyHelper getInstance() {
        return instance;
    }

    /**
     * Get property object using context
     * @param context
     * @return property object
     */
    public Properties getProperty(Context context) {
        AssetManager am = context.getAssets();
        Properties p = new Properties();
        try {
            InputStream is = am.open(PROPERTY_FILE);
            p.load(is);
        } catch (IOException e) {
            LogHelper.e(LogHelper.T_EXCEPTION, e.toString());
        }
        return p;
    }

    /**
     * Get string value of property using specific key
     * @param key
     * @param context
     * @return
     */
    public String getPropertyString(String key, Context context) {
        Properties p = getProperty(context);
        return p.getProperty(key);
    }

    /**
     * Get integer value of property using specific key
     * @param key
     * @param context
     * @return
     */
    public int getPropertyInt(String key, Context context) {
        Properties p = getProperty(context);
        return Integer.parseInt(p.getProperty(key));
    }

}
