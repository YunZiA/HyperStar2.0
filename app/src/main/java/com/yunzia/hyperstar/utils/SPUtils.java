package com.yunzia.hyperstar.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;


public class SPUtils {

    public static SPUtils xsp;
    public SharedPreferences sp;

    private SPUtils() {

    }

    public static synchronized SPUtils getInstance() {
        if (xsp == null) {
            xsp = new SPUtils();
        }
        return xsp;
    }

    /**
     * 初始化
     * @param context android context to access Shared Preferences
     */
    public void init(Context context) {
        sp = context.getSharedPreferences("HyperStar_SP", Context.MODE_PRIVATE);
    }

    /**
     * 下面的是读取数据
     * @param key key of the preference
     * @param def default value of the preference
     * @return the string
     */
    public static String getString(String key, String def) {
        if(SPUtils.getInstance().sp == null){
            return def;
        }
        return SPUtils.getInstance().sp.getString(key, def);
    }

    public static int getInt(String key, int def) {
        if(SPUtils.getInstance().sp == null){
            return def;
        }
        Log.d("ggc", "getInt: "+SPUtils.getInstance().sp.getInt(key, def));
        return SPUtils.getInstance().sp.getInt(key, def);
    }

    public static float getFloat(String key, float def) {
        if(SPUtils.getInstance().sp == null){
            return def;
        }
        return SPUtils.getInstance().sp.getFloat(key, def);
    }

    /** @noinspection unused*/
    public static long getLong(String key, long def) {
        if(SPUtils.getInstance().sp == null){
            return def;
        }
        return SPUtils.getInstance().sp.getLong(key, def);
    }
    public static boolean getBoolean(String key, boolean def) {
        if(SPUtils.getInstance().sp == null){
            return def;
        }
        return SPUtils.getInstance().sp.getBoolean(key, def);
    }

    /**
     * 下面是保存数据
     * @param key key of the preference
     * @param v value to set
     * @return true if the preference is changed
     */
    public static boolean setString(String key, String v) {
        if(SPUtils.getInstance().sp == null){
            return false;
        }
        return SPUtils.getInstance().sp.edit().putString(key, v).commit();
    }

    public static boolean setInt(String key, int v) {
        if(SPUtils.getInstance().sp == null){
            return false;
        }
        return SPUtils.getInstance().sp.edit().putInt(key, v).commit();
    }

    public static boolean setBoolean(String key, boolean v) {
        if(SPUtils.getInstance().sp == null){
            return false;
        }
        return SPUtils.getInstance().sp.edit().putBoolean(key, v).commit();
    }
    public static boolean setFloat(String key, float v) {
        if(SPUtils.getInstance().sp == null){
            return false;
        }
        return SPUtils.getInstance().sp.edit().putFloat(key, v).commit();
    }

    public static boolean setLong(String key, long v) {
        if(SPUtils.getInstance().sp == null){
            return false;
        }
        return SPUtils.getInstance().sp.edit().putLong(key, v).commit();
    }

    public static boolean clearPreferences() {
        SharedPreferences pref = SPUtils.getInstance().sp;
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        return editor.commit();
    }

    public static void getAllPreferences(ArrayList<SP> sputils) {
        SharedPreferences pref = SPUtils.getInstance().sp;
        Map<String, ?> allEntries = pref.getAll();
        Log.d("SPUtils", "Key: " + allEntries);

        String type  = "SPUtils";

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (value) {
                case Integer i -> sputils.add(new SP(type, key, SP.type_int, i));
                case Boolean b -> sputils.add(new SP(type, key, SP.type_boolean, b));
                case Float v -> sputils.add(new SP(type, key, SP.type_float, v));
                case Long l -> sputils.add(new SP(type, key, SP.type_long, l));
                /// String or default or null
                case null, default -> sputils.add(new SP(type, key, SP.type_string, value));
            }
        }

    }


    /** @noinspection unused*/
    public static JsonElement getAllPreferencesAsJsonElement(ArrayList<SP> preferencesUtil) {
        SharedPreferences pref = PreferencesUtil.getInstance().sp;
        Map<String, ?> allEntries = pref.getAll();

        String type  = "PreferencesUtil";
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Log.d("SharedPref", "Key: " + key + ", Value: " + value.toString());

            switch (value) {
                case Integer i -> preferencesUtil.add(new SP(type, key, SP.type_int, i));
                case Boolean b -> preferencesUtil.add(new SP(type, key, SP.type_boolean, b));
                case Float v -> preferencesUtil.add(new SP(type, key, SP.type_float, v));
                case Long l -> preferencesUtil.add(new SP(type, key, SP.type_long, l));
                default -> preferencesUtil.add(new SP(type, key, SP.type_string, value));
            }
        }
        return gson.toJsonTree(preferencesUtil,new TypeToken<ArrayList<SP>>() {}.getType());
    }


}


