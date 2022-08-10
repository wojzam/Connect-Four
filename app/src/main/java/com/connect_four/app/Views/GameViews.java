package com.connect_four.app.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.connect_four.app.Board;
import com.connect_four.app.R;

import static com.connect_four.app.Board.PLAYER_1;

public class GameViews {

    private final Board board;
    private final BoardLayout boardLayout;
    private final TextView text;
    private final int colorPlayer1;
    private final int colorPlayer2;
    private final int colorNeutral;

    public GameViews(LinearLayout mainLayout, Board board) {
        this.board = board;
        this.text = new TextView(mainLayout.getContext());
        this.boardLayout = new BoardLayout(mainLayout.getContext(), board);
        this.colorPlayer1 = ContextCompat.getColor(mainLayout.getContext(), R.color.player1);
        this.colorPlayer2 = ContextCompat.getColor(mainLayout.getContext(), R.color.player2);
        this.colorNeutral = ContextCompat.getColor(mainLayout.getContext(), R.color.white);
        arrangeViews(mainLayout);
    }

    public BoardLayout getBoardLayout() {
        return boardLayout;
    }

    public void updateTextToDescribeBoardStatus() {
        final int currentPlayerID = board.getCurrentPlayerID();
        if (board.currentPlayerWonGame()) {
            if (currentPlayerID == PLAYER_1) {
                text.setText(R.string.player_1_won);
                text.setTextColor(colorPlayer1);
            } else {
                text.setText(R.string.player_2_won);
                text.setTextColor(colorPlayer2);
            }
        } else if (board.isFull()) {
            text.setText(R.string.tie_info);
            text.setTextColor(colorNeutral);
        } else {
            if (currentPlayerID == PLAYER_1) {
                text.setText(R.string.player_1_turn);
                text.setTextColor(colorPlayer1);
            } else {
                text.setText(R.string.player_2_turn);
                text.setTextColor(colorPlayer2);
            }
        }
    }

    private void arrangeViews(LinearLayout mainLayout) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPixel(30, mainLayout.getContext()), 0, 0);
        text.setLayoutParams(layoutParams);
        text.setTypeface(Typeface.SERIF);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        mainLayout.addView(boardLayout);
        mainLayout.addView(text);
    }

    private static int dpToPixel(float dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
