package com.connect_four.app;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class Settings {

    private static final String SINGLE_PLAYER_KEY = "single_player";
    private static final String DIFFICULTY_KEY = "difficulty";
    private final Context context;

    public Settings(Context context) {
        this.context = context;
    }

    public boolean getSinglePlayer() {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SINGLE_PLAYER_KEY, true);
    }

    public int getDifficulty() {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(DIFFICULTY_KEY, 5);
    }
}
