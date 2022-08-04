package com.connect_four.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout boardLayout;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardLayout = findViewById(R.id.boardLayout);
        text = findViewById(R.id.textView);
        Button newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(view -> createNewBoard());

        createNewBoard();
    }

    private void createNewBoard() {
        boardLayout.removeAllViews();
        BoardView boardView = new BoardView(boardLayout, text);
        boardView.createColumns();
    }
}