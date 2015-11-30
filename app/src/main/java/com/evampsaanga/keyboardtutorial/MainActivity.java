package com.evampsaanga.keyboardtutorial;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.evampsaanga.keyboardtutorial.softkeyboard.LatinKeyboard;
import com.evampsaanga.keyboardtutorial.softkeyboard.LatinKeyboardView;
import com.evampsaanga.keyboardtutorial.softkeyboard.SoftKeyboard;

/**
 * Created by root on 11/23/2015.
 */
public class MainActivity extends Activity {
    MyKeyBoard kbdNumView,kbdTxtView;
    EditText etNum,etText,etTextSimple;
    Keyboard kbdNum, kbdQuerty;

    KeyBoardActionListener kbdActionListener;

    LatinKeyboard latinKeyboard;
    LatinKeyboardView latinKeyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        etNum = (EditText) findViewById(R.id.et_num);
        etText = (EditText) findViewById(R.id.et_text);
        etTextSimple = (EditText) findViewById(R.id.et_text_simple);

        kbdNumView = (MyKeyBoard)findViewById(R.id.kbd_num_keyboard);
        kbdTxtView = (MyKeyBoard)findViewById(R.id.kbd_txt_keyboard);
        latinKeyboardView = (LatinKeyboardView) findViewById(R.id.kbd_global_keyboard);

        kbdNum = new Keyboard(this,R.xml.kbd_num);
        kbdQuerty = new Keyboard(this,R.xml.kbdquerty);

        latinKeyboard= new LatinKeyboard(this,R.xml.symbols);

        kbdNumView.setKeyboard(kbdNum);
        kbdTxtView.setKeyboard(kbdQuerty);
        latinKeyboardView.setKeyboard(latinKeyboard);

        kbdNumView.setOnKeyboardActionListener(new KeyBoardActionListener(this, kbdNumView));
        kbdTxtView.setOnKeyboardActionListener(new KeyBoardActionListener(this, kbdTxtView));
//        latinKeyboardView.setOnKeyboardActionListener(new SoftKeyboard());

        kbdNumView.registerEditText(R.id.et_num, this);
        kbdTxtView.registerEditText(R.id.et_text, this);

    }
}
