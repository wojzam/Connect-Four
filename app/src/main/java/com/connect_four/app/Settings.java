package com.connect_four.app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.connect_four.app.model.Disk;

import java.util.Objects;

import static com.connect_four.app.model.Disk.PLAYER_1;
import static com.connect_four.app.model.Disk.PLAYER_2;

public class Settings {

    private static final String SINGLE_PLAYER_KEY = "single_player";
    private static final String DIFFICULTY_KEY = "difficulty";
    private static final String FIRST_TURN_KEY = "first_turn";
    private final SharedPreferences sharedPreferences;
    private boolean singlePlayer;
    private int difficulty;
    private String firstTurn;

    public Settings(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        update();
    }

    public void update() {
        singlePlayer = sharedPreferences.getBoolean(SINGLE_PLAYER_KEY, true);
        difficulty = sharedPreferences.getInt(DIFFICULTY_KEY, 6);
        firstTurn = sharedPreferences.getString(FIRST_TURN_KEY, "0");
    }

    public boolean hasChanged() {
        return singlePlayer != sharedPreferences.getBoolean(SINGLE_PLAYER_KEY, true) ||
                difficulty != sharedPreferences.getInt(DIFFICULTY_KEY, 6) ||
                !Objects.equals(firstTurn, sharedPreferences.getString(FIRST_TURN_KEY, "0"));
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SINGLE_PLAYER_KEY, value);
        editor.apply();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Disk getAIPlayerDisk() {
        if (Objects.equals(firstTurn, "0")) {
            return PLAYER_2;
        }
        return PLAYER_1;
    }

}
