package com.georgopoulos.sudoku.main.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.georgopoulos.sudoku.R;
import com.georgopoulos.sudoku.main.logic.ArrayChecker;
import com.georgopoulos.sudoku.main.logic.NumberGenerator;
import com.georgopoulos.sudoku.main.logic.TimeThread;
import com.georgopoulos.sudoku.main.misc.CellData;
import com.georgopoulos.sudoku.main.views.SudokuCell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[][] intArray;

    String[][] stringArray = new String[9][9];
    CellData[][] cellData = new CellData[9][9];
    SudokuCell[][] sudokuArray;
    Button btn1, btn2, btn3,btn4,btn5,btn6,btn7,btn8,btn9, clearButton;
    GridLayout sudokuBoard;
    Display display;
    String currentDifficulty = "easy", currentTime = "00:00:00";
    CellListener cellListener = new CellListener();
    ButtonListener buttonListener = new ButtonListener(this);
    MenuItem newItem, saveLoadItem, checkItem, settingsItem, saveItem, loadItem;
    TextView scoreText, champText, timerText;
    EditText input;
    TimeThread time;
    final String fileName = "abxy1187";
    final int easy = 25, medium = 50, hard = 100, impossible = 200;
    ObjectOutputStream outputStream;




    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            timerText = (TextView) findViewById(R.id.timerText);
            timerText.setText(currentTime);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        input = new EditText(this);
        input.setMaxLines(1);
        input.setMaxWidth(100);
        champText = (TextView) findViewById(R.id.champText);
        //instantiations
        sudokuArray = new SudokuCell[9][9];
        display = getWindowManager().getDefaultDisplay();
        sudokuBoard = (GridLayout) findViewById(R.id.sudokuBoard);
        scoreText = (TextView) findViewById(R.id.scoreText);
        time = new TimeThread(this);


        setItems();
        setButtons();
        populateBoard();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){


            //sets up a new game
            //gives alert box just in case
            case R.id.newItem :
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to start a new game?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }})
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
                break;

            //saves data to internal storage
            case R.id.saveItem:
                if(time.isAlive()) {
                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to save your game?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveBoardData();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    dialog2.create().show();
                    break;
                }
                else {
                    break;
                }

            //gets and populates data from internal storage
            case R.id.loadItem:
                AlertDialog.Builder dialog3 = new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to load previously saved game?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loadBoardData();
                            }})
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialog3.create().show();
                break;


            case R.id.checkItem :
                if(time.isAlive()) {
                    AlertDialog.Builder dialog4 = new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to check your board?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkGame();
                                }})
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    dialog4.create().show();
                    break;
                }
                else
                    break;

            //if settings menu item is clicked
            case R.id.settingsItem :
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
                break;
        }

        return true;
    }

    //result of settings activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            currentDifficulty = data.getStringExtra("difficulty");
            time.endThread();
            setBestTime();
        }
    }

    //matches menu items to xml
    private void setItems(){

        newItem = (MenuItem) findViewById(R.id.newItem);
        saveLoadItem = (MenuItem) findViewById(R.id.saveLoadItem);
        saveItem = (MenuItem) findViewById(R.id.saveItem);
        loadItem = (MenuItem) findViewById(R.id.loadItem);
        checkItem = (MenuItem) findViewById(R.id.checkItem);
        settingsItem = (MenuItem) findViewById(R.id.settingsItem);
    }

    //matches up buttons to xml
    private void setButtons(){
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(buttonListener);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(buttonListener);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(buttonListener);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(buttonListener);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(buttonListener);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(buttonListener);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(buttonListener);
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(buttonListener);
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(buttonListener);
        clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v2 = getCurrentFocus();
                if(v2 instanceof SudokuCell) {
                    ((SudokuCell) v2).setText("");
                }
            }
        });
    }

    //runs setlankCells() for a specific amount of random cells based on difficulty
    public void populateNumbers(){

        //removes numbers on board based on difficulty
        if(currentDifficulty.equalsIgnoreCase("impossible")) {
            Random rand = new Random();
            int numOne, numTwo;


            for (int i = 0; i < impossible; i++) {
                numOne = rand.nextInt(9);
                numTwo = rand.nextInt(9);
                setBlankCell(numOne, numTwo);

            }
        }
        else if(currentDifficulty.equalsIgnoreCase("hard")) {
            Random rand = new Random();
            int numOne, numTwo;


            for (int i = 0; i < hard; i++) {
                numOne = rand.nextInt(9);
                numTwo = rand.nextInt(9);
                setBlankCell(numOne, numTwo);

            }
        }
        else if(currentDifficulty.equalsIgnoreCase("medium")) {
            Random rand = new Random();
            int numOne, numTwo;


            for (int i = 0; i < medium; i++) {
                numOne = rand.nextInt(9);
                numTwo = rand.nextInt(9);
                setBlankCell(numOne, numTwo);

            }
        }
        else if(currentDifficulty.equalsIgnoreCase("easy")) {
            Random rand = new Random();
            int numOne, numTwo;

            for (int i = 0; i < easy; i++) {
                numOne = rand.nextInt(9);
                numTwo = rand.nextInt(9);
                setBlankCell(numOne, numTwo);

            }
        }
        else{

        }
    }

    //sets board and starts thread
    public void startGame(){
        resetCellText();
        setNumbers();
        setBestTime();

        if(time.isAlive()){
            time.reset();
        }
        else {
            time = new TimeThread(this);
            time.start();
        }

    }

    //brings cells to original state
    public void resetCellText(){
        for(int i = 0; i<9; i++) {
            for (int ii = 0; ii < 9; ii++) {
                sudokuArray[i][ii].setTextColor(Color.BLACK);
                sudokuArray[i][ii].setTypeface(null, Typeface.NORMAL);
                sudokuArray[i][ii].setEnabled(false);
            }
        }
    }

    //prepares cells for interaction of user.
    public void setBlankCell(int col, int row){
        sudokuArray[col][row].setText("");
        sudokuArray[col][row].setEnabled(true);
        sudokuArray[col][row].setTypeface(null, Typeface.BOLD);
        sudokuArray[col][row].setTextColor(Color.BLUE);
        sudokuArray[col][row].setOnFocusChangeListener(cellListener);

    }


    //resets array and populates board
    public void setNumbers(){

        //creates 2d array of ints in a valid sudoku array
        intArray = NumberGenerator.getArray();

        //set numbers to board
        for(int i = 0; i<9; i++) {
            for (int ii = 0; ii < 9; ii++) {
                sudokuArray[i][ii].setText(String.valueOf(intArray[i][ii]));
            }
        }

        populateNumbers();
    }

    //used to set thread time to save point
    public void setCurrentTime(String time){
        currentTime = time;
    }

    //uses shared prefs to get current best times and sets to textview
    public void setBestTime(){
        if(currentDifficulty.equalsIgnoreCase("easy")) {
            champText.setText(pref.getString("easy name", "?????"));
            scoreText.setText(pref.getString("easy best time", "00:00:00"));
        }
        if(currentDifficulty.equalsIgnoreCase("medium")) {
            champText.setText(pref.getString("medium name", "?????"));
            scoreText.setText(pref.getString("medium best time", "00:00:00"));
        }
        if(currentDifficulty.equalsIgnoreCase("hard")) {
            champText.setText(pref.getString("hard name", "?????"));
            scoreText.setText(pref.getString("hard best time", "00:00:00"));
        }
        if(currentDifficulty.equalsIgnoreCase("impossible")) {
            champText.setText(pref.getString("impossible name", "?????"));
            scoreText.setText(pref.getString("impossible best time", "00:00:00"));
        }

    }

    //adds edittext views to each spot of the gridlayout (sudoku board).
    public void populateBoard(){

        for(int i = 0; i<9; i++){
            for(int ii = 0; ii<9; ii++){

                sudokuArray[i][ii] = new SudokuCell(this);
                sudokuArray[i][ii].setWidth(display.getWidth()/9);
                sudokuArray[i][ii].setHeight((int)(display.getHeight()*.575/9));
                sudokuBoard.addView(sudokuArray[i][ii]);

            }
        }

    }

    //gets 2d array of cells
    //not in use but may want later
    public SudokuCell[][] getBoardData(){
        return sudokuArray;
    }

    //writes numbers and settings to internal storage
    public void saveBoardData(){

        FileOutputStream fos = null;
        try {

            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(getCellData());
            outputStream.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //gets info from save file and repopulates numbers and settings
    public void loadBoardData(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            cellData = (CellData[][]) is.readObject();
            resetCellText();
            for(int i =0; i<9; i++){
                for(int ii =0; ii<9; ii++){

                    if(!cellData[i][ii].getBase()){
                        setBlankCell(i,ii);

                    }
                    sudokuArray[i][ii].setText(cellData[i][ii].getNumString());
                }
            }

            currentDifficulty = cellData[1][1].getDifficulty();
            setBestTime();
            if(time.isAlive()){
                time.reset();
            }
            else {
                time = new TimeThread(this);
                time.start();
            }
            time.setTime(cellData[1][1].getTime());
            is.close();
            fis.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //prevents backpage to splash screen.
    //couldn't do in manifest for some reason.
    @Override
    public void onBackPressed() {

    }


    //gets an int 2d array of board
    //use with ArrayChcker
    public int[][] getIntArray(){
        int[][] userArray = new int[9][9];
        for(int i =0; i<9; i++){
            for(int ii =0; ii<9; ii++){
                userArray[i][ii] = Integer.parseInt(sudokuArray[i][ii].getText().toString());

            }
        }
        return userArray;
    }

    //unused
    //was for serialization
    //kept because fuck it
    public String[][] getStringArray(){
        String[][] tempArray = new String[9][9];
        for(int i =0; i<9; i++){
            for(int ii =0; ii<9; ii++){
                tempArray[i][ii] = sudokuArray[i][ii].getText().toString();

            }
        }
        return tempArray;
    }

    //to serialize cells
    public CellData[][] getCellData(){
        CellData[][] data = new CellData[9][9];
        for(int i = 0; i<9; i++){
            for(int ii = 0; ii<9; ii++){
                data[i][ii] = new CellData(sudokuArray[i][ii].getText().toString(),
                        sudokuArray[i][ii].getCurrentTextColor() == Color.BLACK,
                        currentDifficulty,
                        time.getTime());
            }
        }
        return data;
    }

    //checks game
    //used with menu item
    public void checkGame(){
        if(time.isAlive()) {
            try {
                //triggers if user array is valid
                //ends thread and writes to sharedPreferences based on difficulty
                if (ArrayChecker.checkNumbers(getIntArray())) {
                    time.endThread();
                    if (currentDifficulty.equalsIgnoreCase("easy")) {
                        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
                        inputDialog.setView(input);
                        inputDialog.setMessage("Congratulations!! You Won AND got the best " +
                                "time. Enter your name here: ");
                        inputDialog.setTitle("Best Time!");
                        inputDialog.setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {


                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putString("easy name", input.getText().toString());
                                        editor.commit();
                                    }
                                });
                        inputDialog.create().show();
                        editor.putString("easy best time", currentTime);
                        editor.commit();
                        setBestTime();
                    } else if (currentDifficulty.equalsIgnoreCase("medium")) {
                        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
                        inputDialog.setView(input);
                        inputDialog.setMessage("Congratulations!! You Won AND got the best " +
                                "time. Enter your name here: ");
                        inputDialog.setTitle("Best Time!");
                        inputDialog.setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {


                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putString("medium name", input.getText().toString());
                                        editor.commit();
                                    }
                                });
                        inputDialog.create().show();
                        editor.putString("medium best time", currentTime);
                        editor.commit();
                        setBestTime();
                    } else if (currentDifficulty.equalsIgnoreCase("hard")) {
                        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
                        inputDialog.setView(input);
                        inputDialog.setMessage("Congratulations!! You Won AND got the best " +
                                "time. Enter your name here: ");
                        inputDialog.setTitle("Best Time!");
                        inputDialog.setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {


                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putString("hard name", input.getText().toString());
                                        editor.commit();
                                    }
                                });
                        inputDialog.create().show();
                        editor.putString("hard best time", currentTime);
                        editor.commit();
                        setBestTime();
                    } else if (currentDifficulty.equalsIgnoreCase("impossible")) {
                        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
                        inputDialog.setView(input);
                        inputDialog.setMessage("Congratulations!! You Won AND got the best " +
                                "time. Enter your name here: ");
                        inputDialog.setTitle("Best Time!");
                        inputDialog.setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {


                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putString("impossible name", input.getText().toString());
                                        editor.commit();
                                    }
                                });
                        inputDialog.create().show();
                        editor.putString("impossible best time", currentTime);
                        editor.commit();
                        setBestTime();
                    }

                    //if user array is invalid
                    //shows write and wrong numbers
                    //gives toast message to user
                } else {
                    Toast.makeText(this, "You Suck", Toast.LENGTH_LONG).show();
                    for (int i = 0; i < 9; i++) {
                        for (int ii = 0; ii < 9; ii++) {
                            if (sudokuArray[i][ii].getCurrentTextColor() != Color.BLACK) {
                                if (ArrayChecker.checkIndividualNumber(
                                        getIntArray(),
                                        Integer.parseInt(
                                                sudokuArray[i][ii].getText().toString()),
                                        i, ii)) {
                                    sudokuArray[i][ii].setTextColor(Color.GREEN);


                                } else if (!ArrayChecker.checkIndividualNumber(
                                        getIntArray(),
                                        Integer.parseInt(
                                                sudokuArray[i][ii].getText().toString()),
                                        i, ii)) {
                                    sudokuArray[i][ii].setTextColor(Color.RED);

                                }
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Board is incomplete!", Toast.LENGTH_LONG).show();
            }
        }
    }
}




class CellListener implements View.OnFocusChangeListener {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.hasFocus()){
            v.setBackgroundColor(Color.LTGRAY);
        }
        else{
            v.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}


class ButtonListener implements View.OnClickListener {

    MainActivity c;

    public ButtonListener(MainActivity c) {
        this.c = c;
    }

    @Override
    public void onClick(View v) {
        View v2 = c.getCurrentFocus();
        if (v2 instanceof SudokuCell) {
            ((SudokuCell) v2).setText(((Button) v).getText());
            ((SudokuCell) v2).setTextColor(Color.BLUE);
        }
    }
}




