package com.example.statusupload.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefs {
    public static void setBooleanPref(Context context, String name, boolean value) {
        SharedPreferences pref = context.getSharedPreferences("My_Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(name, value);
        editor.apply();
    }

    public static boolean getBooleanPref(Context context, String name) {
//        SharedPreferences pref = context.getSharedPreferences("My_Pref",MODE_PRIVATE);
//        return pref.getBoolean(name,false);
        return context.getSharedPreferences("My_Pref", MODE_PRIVATE).getBoolean(name, false);

    }

    public static void setStringPref(Context context, String name, String value) {
        SharedPreferences pref = context.getSharedPreferences("My_Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getStringPref(Context context, String name) {
//        SharedPreferences pref = context.getSharedPreferences("My_Pref",MODE_PRIVATE);
//        return pref.getBoolean(name,false);
        return context.getSharedPreferences("My_Pref", MODE_PRIVATE).getString(name,"");

    }
    public static void setIntPref(Context context, String name, int value) {
        SharedPreferences pref = context.getSharedPreferences("My_Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static int getIntPref(Context context, String name) {
//        SharedPreferences pref = context.getSharedPreferences("My_Pref",MODE_PRIVATE);
//        return pref.getInt(name,false);
        return context.getSharedPreferences("My_Pref", MODE_PRIVATE).getInt(name,0);

    }

    public static void clearPref(Context context) {
        context.getSharedPreferences("My_Pref", MODE_PRIVATE).edit().clear().apply();
    }
}
