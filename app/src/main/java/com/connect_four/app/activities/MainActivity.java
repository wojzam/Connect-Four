package com.connect_four.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.connect_four.app.R;
import com.connect_four.app.Settings;
import com.connect_four.app.controller.GameController;
import com.connect_four.app.model.GameModel;

public class MainActivity extends AppCompatActivity {

    private Settings settings;
    private GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        Button settingsButton = findViewById(R.id.settingsButton);

        settings = new Settings(getApplicationContext());
        controller = new GameController(new GameModel(settings), gameLayout);
        controller.restart();

        settingsButton.setOnClickListener(view -> openSettings());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (settings.hasChanged()) {
            settings.update();
            controller.restart();
        }
    }

    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}