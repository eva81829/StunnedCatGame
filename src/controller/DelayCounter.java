/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import utils.Global;

/**
 *
 * @author asdfg
 */
public class DelayCounter {
    private int delay;
    private int count;
    private int countD;
    private int countC;
    
    public DelayCounter(int delay){
        this.delay = delay * Global.ANIME_DELAY;
        count = 0;
        countC = 0;
        countD = 0;
    }
    
    public void setDelay(int delay){
        this.delay = delay * Global.ANIME_DELAY;
    }
    
    public boolean update(){
        if(count++ < delay){
            return false;
        }
        count = 0;
        return true;
    }
    
    public boolean updateDirection(int changeTimes){
        if(countD++ < changeTimes * Global.ANIME_AUTO_DIRECTION){
            return false;
        }
        countD = 0;
        return true;
    }
    
    public boolean updateCharacter(int changeTimes){
        if(countC++ < changeTimes * Global.ANIME_AUTO_CHARACTER_CHANGE){
            return false;
        }
        countC = 0;
        return true;
    }
}
