/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import controller.DelayCounter;
import java.awt.Color;
import java.awt.Graphics;
//import static main.Main.f;
import static utils.Global.*;

/**
 *
 * @author asdfg
 */
public class FrameTimer {

    private double countSecond;
    private int targetSecond;
    private int x;
    private int y;
    private int width;
    private int height;
    private DelayCounter counter;

    public FrameTimer(int x, int y, int width, int height, int targetSecond) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.targetSecond = targetSecond;
        countSecond = 0;
        counter = new DelayCounter(Global.UPDATE_TIME_PER_SEC / 2);
    }

    public boolean timeUpDate() {
        if (counter.update()) {
            countSecond += 0.5 * Global.UPDATE_TIME_PER_SEC / 15;
        }
        if (countSecond >= targetSecond) {
            return true;
        }
        return false;
    }

    public void paint(Graphics g) {
        double intervalSec = targetSecond - countSecond;
        int minuteTenDigits = (int) (intervalSec / 60 / 10);
        int minuteDigits = (int) (intervalSec / 60 % 10);
        int secondTenDigits = (int) (intervalSec % 60 / 10);
        int secondDigits = (int) (intervalSec % 60 % 10);
        String s = minuteTenDigits + "" + minuteDigits + ":" + secondTenDigits + "" + secondDigits;
        g.setColor(Color.WHITE);
//        g.setFont(f);
        g.drawString(s, x, y);
    }
}

