/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.georgopoulos.sudoku.main.logic;


/**
 * Set diff object instead of textfield object passed as the current array.
 */
public class ArrayChecker {

    public static boolean checkIndividualNumber(int[][] currentArray, int num, int row, int col){
        if(!checkVertical(num,currentArray,row,col)){
            return false;
        }
        if(!checkHorizontal(num,currentArray,row,col)){
            return false;
        }
        if(!checkSector(num,currentArray,row,col))
            return false;
        return true;
    }

    public static boolean checkNumbers(int[][] currentArray){
        boolean validity = true;
        try{
            for(int i = 0; i < 9; i++){
                for(int ii = 0; ii < 9; ii++){
                    int num = currentArray[i][ii];
                    if(!checkVertical(num,currentArray,i,ii)){
                        validity = false;
                    }
                    if(!checkHorizontal(num,currentArray,i,ii)){
                        validity = false;
                    }
                    if(!checkSector(num,currentArray,i,ii))
                        validity = false;
                }
            }
        }
        catch(NumberFormatException e){
            //make toast msg!
            // "Incomplete Board!";

            validity = false;
        }

        return validity;
    }
    //checks verical (may have mixed up vertical and horizontal
    public static boolean checkVertical(int num, int[][] currentArray,
                                        int i, int ii){

        boolean validity = true;
        String si = String.valueOf(i);
        String sii = String.valueOf(ii);

        for(int j = 0; j<9; j++){
            String sj = String.valueOf(j);

            if(num == 0){
                //make toast msg "0s are not allowed you CHEATER!!");

                validity = false;
                break;
            }
            if(num == (currentArray[i][j])

                    && !(si+sii).equals(si+sj)){
                validity = false;
            }
        }


        return validity;
    }

    //checks horizontal
    public static boolean checkHorizontal(int num, int[][] currentArray,
                                          int i, int ii){
        boolean validity = true;
        String si = String.valueOf(i);
        String sii = String.valueOf(ii);

        for(int j = 0; j<9; j++){
            String sj = String.valueOf(j);
            if(num == currentArray[j][ii]
                    && !(si+sii).equals(sj+ii)){
                validity = false;
            }
        }
        return validity;
    }

    //checks squares
    public static boolean checkSector(int rNum, int[][]
            currentArray,int row, int column){

        boolean validity = true;

        if ((row < 3) && (column < 3)) {
            for (int i = 0; i < 3; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if(rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)){
                        validity = false;
                    }
                }
            }
        }

        if ((row < 3) && (column > 2) && (column < 6)) {
            for (int i = 0; i < 3; i++) {
                for (int ii = 3; ii < 6; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)) {
                        validity = false;
                    }
                }
            }
        }

        if ((row < 3) && (column > 5)) {
            for (int i = 0; i < 3; i++) {
                for (int ii = 6; ii < 9; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)) {
                        validity = false;
                    }
                }
            }
        }


        if ((row < 6) && (row > 2) && (column < 3)) {
            for (int i = 3; i < 6; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)) {
                        validity = false;
                    }
                }
            }
        }

        if ((row < 6) && (row > 2) && (column < 6) && (column > 2)) {
            for (int i = 3; i < 6; i++) {
                for (int ii = 3; ii < 6; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)){
                        validity = false;
                    }
                }
            }
        }

        if ((row < 6) && (row > 2) && (column > 5)) {
            for (int i = 3; i < 6; i++) {
                for (int ii = 6; ii < 9; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)){
                        validity = false;
                    }
                }
            }
        }

        if ((row > 5) && (column < 3)) {
            for (int i = 6; i < 9; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)){
                        validity = false;
                    }
                }
            }
        }

        if ((row > 5) && (column > 2) && (column < 6)) {
            for (int i = 6; i < 9; i++) {
                for (int ii = 3; ii < 6; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)){
                        validity = false;
                    }
                }
            }
        }

        if ((row > 5) && (column > 5)) {
            for (int i = 6; i < 9; i++) {
                for (int ii = 6; ii < 9; ii++) {
                    String si = String.valueOf(i);
                    String sii = String.valueOf(ii);
                    String sRow = String.valueOf(row);
                    String sColumn = String.valueOf(column);
                    if (rNum == currentArray[i][ii]
                            && !(si+sii).equals(sRow+sColumn)){
                        validity = false;
                    }
                }
            }
        }
        return validity;

    }
}
