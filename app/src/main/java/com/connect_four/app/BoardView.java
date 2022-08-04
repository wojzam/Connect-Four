package com.connect_four.app;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class BoardView {

    private final Board board;
    private final LinearLayout layout;
    private final TextView text;
    private final ArrayList<ColumnLayout> columns = new ArrayList<>();

    public BoardView(@NonNull LinearLayout layout, @NonNull TextView text) {
        this.board = new Board();
        this.layout = layout;
        this.text = text;
        updateTextView();
    }

    public void createColumns() {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(layout.getContext(), board.getHeight());
            int column = i;
            columnLayout.setOnClickListener(view -> {
                if (board.insertIntoColumn(column)) {
                    columnLayout.refresh(board.getColumnValues(column));

                    if(board.wonGame() || board.isFull()){
                        text.setText("Koniec gry");
                        endGame();
                    } else {
                        board.changePlayer();
                        updateTextView();
                    }
                }
            });

            layout.addView(columnLayout);
            columns.add(columnLayout);
        }
    }

    private void updateTextView() {
        switch (board.getCurrentPlayerID()) {
            case PLAYER_1:
                text.setText("Czerwony");
                text.setTextColor(ContextCompat.getColor(layout.getContext(), R.color.player1));
                break;
            case PLAYER_2:
                text.setText("Żółty");
                text.setTextColor(ContextCompat.getColor(layout.getContext(), R.color.player2));
                break;
            default:
                text.setText("");
        }
    }

    private void endGame(){
        for(ColumnLayout columnLayout: columns){
            columnLayout.setOnClickListener(null);
            columnLayout.setClickable(false);
        }
    }
}
