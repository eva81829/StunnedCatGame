/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author asdfg
 */
public class StageTimer {
    private long startTime;
    private long targetTime;
    private long currentTime;
    private long intervalSec;
    private int targetSecond;

    private int x;
    private int y;
    private int width;
    private int height;
    private String s;

    public StageTimer(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        startTime = System.currentTimeMillis();
        targetTime = startTime + (targetSecond * 1000);
        intervalSec = 0;
    }
    public StageTimer(int x, int y, int width, int height, int targetSecond) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.targetSecond = targetSecond;
        startTime = System.currentTimeMillis();
        targetTime = startTime + (targetSecond * 1000);
        
    }

    public boolean timeUpDate() {
        currentTime = System.currentTimeMillis();
        if(currentTime >= targetTime){
            return true;
        }
        return false;
    }
    
    public String getResultTime(){
        return s;
    }
    
    public long getResult(){
        return intervalSec;
    }

    public void paint(Graphics g) {
        intervalSec = /*targetSecond - */(currentTime - startTime)/1000;
        int minuteTenDigits = (int)(intervalSec / 60 / 10);
        int minuteDigits = (int)(intervalSec / 60 % 10);
        int secondTenDigits = (int)(intervalSec % 60 / 10);
        int secondDigits = (int)(intervalSec % 60 % 10);
        s = minuteTenDigits + "" + minuteDigits + ":" + secondTenDigits + "" + secondDigits;
        g.setColor(Color.WHITE);
        g.setFont(new Font("華康少女文字W3", Font.PLAIN, 45));
        g.drawString(s, x, y);
    }
}
