package com.evampsaanga.keyboardtutorial;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * Created by root on 11/23/2015.
 */

public class MyKeyBoard extends KeyboardView {
    Activity targetActivity;
    Context context;
    public MyKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }
    @Override
    public void onDraw(Canvas canvas) {
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == 66001) {
                NinePatchDrawable npd
                        = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.greeneasy);
                npd.setBounds(key.x, key.y, key.x + (key.width - 1 ), key.y + (key.height - 5));
                npd.draw(canvas);

//	        } else {
//	            NinePatchDrawable npd
//	                = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.yellow);
//	            npd.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
//	            npd.draw(canvas);
            }

            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(60);
            paint.setColor(Color.WHITE);

            if (key.label != null) {
                canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                        (key.y + 15) + (key.height / 2), paint);
            } else {
                key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                key.icon.draw(canvas);
            }
        }
    }

    public boolean isCustomKeyboardVisible() {
        return this.getVisibility() == View.VISIBLE;
    }

    /** Make the CustomKeyboard visible, and hide the system keyboard for view v. */
    public void showCustomKeyboard( View v ) {
        this.setVisibility(View.VISIBLE);
        this.setEnabled(true);
        if( v!=null ) ((InputMethodManager)targetActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /** Make the CustomKeyboard invisible. */
    public void hideCustomKeyboard() {
        this.setVisibility(View.GONE);
        this.setEnabled(false);
    }

    public void registerEditText(int resid, Activity host) {
        this.targetActivity = host;
        // Find the EditText 'resid'
        EditText edittext= (EditText)targetActivity.findViewById(resid);
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showCustomKeyboard(v); else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }
}
