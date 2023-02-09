package com.connect_four.app.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.connect_four.app.R;
import com.connect_four.app.model.Board;
import com.connect_four.app.model.Disk;

import static com.connect_four.app.model.Disk.PLAYER_1;
import static com.connect_four.app.views.GameView.dpToPixel;

public class GameStateTextView extends AppCompatTextView {

    private final int colorPlayer1;
    private final int colorPlayer2;
    private final int colorNeutral;

    public GameStateTextView(Context context) {
        super(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = dpToPixel(50, context);
        layoutParams.setMargins(0, margin, 0, margin);
        layoutParams.gravity = Gravity.CENTER;

        setLayoutParams(layoutParams);
        setTypeface(Typeface.SERIF);
        setTextSize(25);

        colorPlayer1 = ContextCompat.getColor(context, R.color.player1);
        colorPlayer2 = ContextCompat.getColor(context, R.color.player2);
        colorNeutral = ContextCompat.getColor(context, R.color.white);
    }

    public void update(Board board) {
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
