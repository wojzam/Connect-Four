package com.connectfour.app.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.connectfour.app.R;
import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import static com.connectfour.app.model.Disk.PLAYER_1;

public class GameStateTextView extends AppCompatTextView {

    private final int colorPlayer1;
    private final int colorPlayer2;
    private final int colorNeutral;

    public GameStateTextView(@NonNull Context context) {
        super(context);
        setId(View.generateViewId());
        setTypeface(Typeface.SERIF);
        setTextSize(25);

        colorPlayer1 = ContextCompat.getColor(context, R.color.player1);
        colorPlayer2 = ContextCompat.getColor(context, R.color.player2);
        colorNeutral = ContextCompat.getColor(context, R.color.white);
    }

    public void update(@NonNull Board board) {
        final Disk currentPlayerID = board.getCurrentPlayerDisk();
        if (board.currentPlayerWonGame()) {
            if (currentPlayerID == PLAYER_1) {
                setText(R.string.player_1_won);
                setTextColor(colorPlayer1);
            } else {
                setText(R.string.player_2_won);
                setTextColor(colorPlayer2);
            }
        } else if (board.isFull()) {
            setText(R.string.tie_info);
            setTextColor(colorNeutral);
        } else {
            if (currentPlayerID == PLAYER_1) {
                setText(R.string.player_1_turn);
                setTextColor(colorPlayer1);
            } else {
                setText(R.string.player_2_turn);
                setTextColor(colorPlayer2);
            }
        }
    }

}
