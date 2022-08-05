package com.connect_four.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        Button newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(view -> game.restart());

        game = new Game(gameLayout);
        game.restart();
    }
}