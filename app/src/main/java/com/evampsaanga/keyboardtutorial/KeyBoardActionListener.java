package com.evampsaanga.keyboardtutorial;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 11/25/2015.
 */
public class KeyBoardActionListener implements KeyboardView.OnKeyboardActionListener {

    Activity targetActivity;
    MyKeyBoard mKeyboardView;

    boolean caps = false;
    boolean kbdalphanumeric = false;

    public final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
    public final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
    public final static int CodePrev     = 55000;
    public final static int CodeAllLeft  = 55001;
    public final static int CodeLeft     = 55002;
    public final static int CodeRight    = 55003;
    public final static int CodeAllRight = 55004;
    public final static int CodeNext     = 55005;
    public final static int CodeClear    = 55006;
    public final static int DisableKey   =  -25;

    public KeyBoardActionListener(Activity targetActivity, MyKeyBoard mKeyboardView){
        this.targetActivity = targetActivity;
        this.mKeyboardView = mKeyboardView;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        View focusCurrent = targetActivity.getWindow().getCurrentFocus();
        if( focusCurrent==null || focusCurrent.getClass()!= EditText.class ) return;
        EditText edittext = (EditText) focusCurrent;
        Editable editable = edittext.getText();
        int start = edittext.getSelectionStart();
        // Apply the key to the edittext
        if( primaryCode==CodeCancel ) {
            hideCustomKeyboard();
        } else if( primaryCode==CodeDelete ) {
            if( editable!=null && start>0 ) editable.delete(start - 1, start);
        } else if( primaryCode==CodeClear ) {
            if( editable!=null ) editable.clear();
        } else if( primaryCode==CodeLeft ) {
            if( start>0 ) edittext.setSelection(start - 1);
        } else if( primaryCode==CodeRight ) {
            if (start < edittext.length()) edittext.setSelection(start + 1);
        } else if( primaryCode==CodeAllLeft ) {
            edittext.setSelection(0);
        } else if( primaryCode==CodeAllRight ) {
            edittext.setSelection(edittext.length());
        } else if (primaryCode == 65000){
            if (!caps){
                mKeyboardView.setKeyboard(new Keyboard(targetActivity,R.xml.kbd_qwerty_capital));
                mKeyboardView.invalidateAllKeys();
                caps= true;
            }else if(caps){
                mKeyboardView.setKeyboard(new Keyboard(targetActivity,R.xml.kbdquerty));
                mKeyboardView.invalidateAllKeys();
                caps= false;
            }

        }else if (primaryCode == 65002){
            if (!kbdalphanumeric){
                mKeyboardView.setKeyboard(new Keyboard(targetActivity,R.xml.kbd_qwerty_num));
                mKeyboardView.invalidateAllKeys();
                kbdalphanumeric= true;
            }else if(kbdalphanumeric){
                mKeyboardView.setKeyboard(new Keyboard(targetActivity,R.xml.kbdquerty));
                mKeyboardView.invalidateAllKeys();
                kbdalphanumeric= false;
            }

        }else if(primaryCode==DisableKey){

        }else if(primaryCode==66001){
            Toast.makeText(targetActivity,"Your Next Action Here",Toast.LENGTH_SHORT).show();
        }  else{ // insert character
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {
        
    }
    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /** Make the CustomKeyboard visible, and hide the system keyboard for view v. */
    public void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null ) ((InputMethodManager)targetActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /** Make the CustomKeyboard invisible. */
    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }



}
