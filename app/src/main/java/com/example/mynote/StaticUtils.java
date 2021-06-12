package com.example.mynote;

import android.content.Context;
import android.content.SharedPreferences;

public class StaticUtils {
    public static void storeLoggedUsername(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
        return sharedPreferences.getString("username", null);
    }

    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", null);
        editor.apply();
    }
}