package com.georgopoulos.sudoku.main.logic;

import java.util.Random;

public class NumberGenerator{



    public static int[][] getArray(){

        int[][] sudokuDigits;
        int failCounter;
        Random rNum = new Random();
        sudokuDigits = new int[9][9];
        failCounter = 0;

        for (int i = 0; i < 9; i++) {
                for (int ii = 0; ii < 9; ii++) {
                    boolean passNumber = false;

                    while (!passNumber)
                    {
                        int tempInt = rNum.nextInt(9) + 1;

                        if ((checkVertical(tempInt, i, sudokuDigits))
                                && (checkHorizontal(tempInt, ii, sudokuDigits)) &&
                                (checkSectors(tempInt, i, ii, sudokuDigits))) {
                            sudokuDigits[i][ii] = tempInt;
                            passNumber = true;
                            failCounter = 0;
                        }
                        else {
                            failCounter += 1;
                        }
                        if (failCounter > 50) {
                            sudokuDigits = new int[9][9];
                            i = 0;
                            ii = 0;
                        }
                    }
                }
        }
    return sudokuDigits;
    }




    private static boolean checkVertical(int rNum, int row, int[][] sudokuDigits)
    {
        boolean validity = true;

        for (int i = 0; i < 9; i++)
        {
            if (rNum == sudokuDigits[row][i])
                validity = false;
        }
        return validity;
    }

    private static boolean checkHorizontal(int rNum, int column, int[][] sudokuDigits)
    {
        boolean validity = true;
        for (int i = 0; i < 9; i++) {
            if (rNum == sudokuDigits[i][column]) {
                validity = false;
            }
        }
        return validity;
    }

    private static boolean checkSectors(int rNum, int row, int column, int[][] sudokuDigits)
    {
        boolean validity = true;

        if ((row < 3) && (column < 3)) {
            for (int i = 0; i < 3; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row < 3) && (column > 2) && (column < 6)) {
            for (int i = 0; i < 3; i++) {
                for (int ii = 3; ii < 6; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row < 3) && (column > 5)) {
            for (int i = 0; i < 3; i++) {
                for (int ii = 6; ii < 9; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }


        if ((row < 6) && (row > 2) && (column < 3)) {
            for (int i = 3; i < 6; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row < 6) && (row > 2) && (column < 6) && (column > 2)) {
            for (int i = 3; i < 6; i++) {
                for (int ii = 3; ii < 6; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row < 6) && (row > 2) && (column > 5)) {
            for (int i = 3; i < 6; i++) {
                for (int ii = 6; ii < 9; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row > 5) && (column < 3)) {
            for (int i = 6; i < 9; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row > 5) && (column > 2) && (column < 6)) {
            for (int i = 6; i < 9; i++) {
                for (int ii = 3; ii < 6; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }

        if ((row > 5) && (column > 5)) {
            for (int i = 6; i < 9; i++) {
                for (int ii = 6; ii < 9; ii++) {
                    if (rNum == sudokuDigits[i][ii]) {
                        validity = false;
                    }
                }
            }
        }
        return validity;
    }


}
