package com.georgopoulos.sudoku.main.views;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import java.io.Serializable;

/**
 * Created by rascal on 8/6/17.
 */

public class SudokuCell extends android.support.v7.widget.AppCompatEditText{
    Context c;
    public SudokuCell(Context context) {
        super(context);
        c = context;


        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(1);
        setFilters(filter);
        setTextAlignment(EditText.TEXT_ALIGNMENT_CENTER);
        setBackgroundColor(Color.TRANSPARENT);
        setTextSize(16);
        setTextColor(Color.BLACK);
        setPadding(0, 0, 0, 0);
        setEnabled(false);
        setRawInputType(InputType.TYPE_CLASS_NUMBER);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //to disable soft keyboard
    @Override
    public boolean onCheckIsTextEditor() {
        return false;
    }


}
