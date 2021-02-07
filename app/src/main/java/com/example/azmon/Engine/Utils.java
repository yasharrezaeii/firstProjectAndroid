package com.example.azmon.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Utils {
    public static final String NAME_SHARED_PREFERENCES = "Azmon";

    private Activity activity;

    public Utils(Activity activity) {
        this.activity = activity;
    }

    public void goTo(Class _class, boolean isFinish) {
        activity.startActivity(new Intent(activity, _class));
        if (isFinish)
            activity.finish();
    }

    public Object getSharedPreferences(String key, Object defaultValue, String nameSharedPreference) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(nameSharedPreference, Context.MODE_PRIVATE);
        if (defaultValue instanceof String)
            return sharedPreferences.getString(key, (String) defaultValue);
        else if (defaultValue instanceof Integer)
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        else if (defaultValue instanceof Float)
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        else if (defaultValue instanceof Boolean)
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        else if (defaultValue instanceof Long)
            return sharedPreferences.getLong(key, (Long) defaultValue);
        return null;
    }

    public void setSharedPreferences(String key, Object value, String nameSharedPreference) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(nameSharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String)
            editor.putString(key, (String) value);
        else if (value instanceof Integer)
            editor.putInt(key, (Integer) value);
        else if (value instanceof Float)
            editor.putFloat(key, (Float) value);
        else if (value instanceof Boolean)
            editor.putBoolean(key, (Boolean) value);
        else if (value instanceof Long)
            editor.putLong(key, (Long) value);
        editor.apply();
    }
}
