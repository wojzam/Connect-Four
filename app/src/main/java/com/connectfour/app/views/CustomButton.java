package com.connectfour.app.views;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.connectfour.app.R;

/**
 * The {@code CustomButton} is a custom button used for this application.
 */
public class CustomButton extends AppCompatButton {

    /**
     * This constructor is not supported.
     *
     * @param context the context to use
     * @throws UnsupportedOperationException always thrown, as this constructor is not supported
     */
    public CustomButton(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    /**
     * Constructs a new instance of {@code CustomButton}.
     *
     * @param context   the context to use
     * @param textResId the resource ID of the text to set on the button
     */
    public CustomButton(@NonNull Context context, int textResId) {
        super(context);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        setId(View.generateViewId());
        setBackgroundResource(R.drawable.button_bg);
        setLayoutParams(params);
        setText(textResId);
    }
}
