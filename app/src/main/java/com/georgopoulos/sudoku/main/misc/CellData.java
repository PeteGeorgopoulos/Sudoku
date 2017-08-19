package com.georgopoulos.sudoku.main.misc;

import android.graphics.Color;

import java.io.Serializable;


/**
 * Created by rascal on 8/18/17.
 */

public class CellData implements Serializable {
    private String numString;
    private Boolean isBase;
    private String difficulty;
    private int time;

    public CellData(){ }

    public CellData(String num, Boolean isBase, String difficulty, int time){
        this.setNumString(num);
        this.setBase(isBase);
        this.setDifficulty(difficulty);
        this.setTime(time);

    }

    public String getNumString() {
        return numString;
    }

    public void setNumString(String numString) {
        this.numString = numString;
    }


    public Boolean getBase() {
        return isBase;
    }

    public void setBase(Boolean base) {
        isBase = base;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

