package com.georgopoulos.sudoku.main.logic;


import com.georgopoulos.sudoku.main.activities.*;
import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author themis
 */

public class TimeThread extends Thread{

    MainActivity activity;
    int hours = 0, minutes = 0, seconds;
    int time = 0;
    boolean isRunning;
    DecimalFormat timeFormat = new DecimalFormat("00");
    public TimeThread(MainActivity activity){
        this.activity = activity;
    }
    public void run(){
        isRunning = true;

        while(isRunning){
            time++;
            seconds = time%60;
            if(time%60 == 0){
                seconds = 0;
                minutes++;
            }
            if(time%3600 == 0){
                minutes = 0;
                hours++;
            }



            activity.setCurrentTime(timeFormat.format(hours)+":"+
                    timeFormat.format(minutes)+ ":"+
                    timeFormat.format(seconds));
            try{
                this.sleep(1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            activity.handler.sendEmptyMessage(0);
        }
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time =  time;
    }

    public void reset(){
        time = 0;
        activity.setCurrentTime("00:00:00");
    }

    public void endThread(){
        isRunning = false;
    }

}