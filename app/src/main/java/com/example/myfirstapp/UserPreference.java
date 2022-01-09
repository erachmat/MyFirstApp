package com.example.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    // Membuat konstanta
    private final String KEY_NAME = "name";
    private final String KEY_EMAIL = "email";
    private final String KEY_USER = "userkey";

    // Bagian Meregistrasi File SharePref di dalam aplikasi
    private SharedPreferences preferences;

    UserPreference(Context context) {
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // <!---- ---> //

    public void setName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public String getName() {
        return preferences.getString(KEY_NAME, null);
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    public void setKey(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER, key);
        editor.apply();
    }

    public String getKey() {
        return preferences.getString(KEY_USER, null);
    }

    public void removeData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_NAME).commit();
        editor.remove(KEY_EMAIL).commit();
        editor.remove(KEY_USER).commit();
    }

    public String checkData() {
        String stringMember = preferences.getString(KEY_NAME, "");
        return stringMember;
    }
}
