/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temposystem;

import utils.DebugMode;
import utils.Global;

/**
 *
 * @author Eka
 */
public class PressedKeyProcessor {
//    public static final String ATTACK_KEY = "SSSS";
//    public static final String  DEFENSE_KEY = "DDDD";
    public static final String  JUMP_KEY = "SSSD";
    public static final String  SUPER_JUMP_KEY = "ASDF";
    public static final String  BACK_JUMP_KEY = "FDSA";
    public static final String  TURN_RIGHT_KEY = "FDSD";
    public static final String  TURN_LEFT_KEY = "ASDS";
    public static final String  SLOW_KEY = "DSDS";
    
//    public static final int ACTION_ATTACK = 0;
//    public static final int ACTION_DEFENSE = 1;
    public static final int ACTION_JUMP = 2;
    public static final int ACTION_TURN_RIGHT = 3;
    public static final int ACTION_TURN_LEFT = 4;
    public static final int ACTION_SLOW = 5;
    public static final int ACTION_SUPER_JUMP = 6;
    public static final int ACTION_BACK_JUMP = 7;
    public static final int ACTION_NO_MATCH = -1;
    
    private String pressedRecord;
    private int lastPressX; //上次按鍵的位置;
    private int currentPressX; //這次按鍵的位置;    
    private int accumulation; //兩次按鍵間隔距離;
   // private boolean keepRecord = true;    
    
    public PressedKeyProcessor(){
        pressedRecord = "";
        accumulation = lastPressX = currentPressX = 0;
    }
    
    public int checkAction(int command){
        //如果目前總按的按鍵等同於其中一個規定的按鍵順序, 則回傳觸發動作
//        if (pressedRecord.indexOf(ATTACK_KEY)>=0){
//            return ACTION_ATTACK;
//        }
//        if (pressedRecord.indexOf(DEFENSE_KEY)>=0){
//            return ACTION_DEFENSE;     
//        }
        if (pressedRecord.indexOf(JUMP_KEY)>=0){
            return ACTION_JUMP;
        }
        if (pressedRecord.indexOf(SUPER_JUMP_KEY)>=0){
            return ACTION_SUPER_JUMP;
        }
        if (pressedRecord.indexOf(BACK_JUMP_KEY)>=0){
            return ACTION_BACK_JUMP;
        }
        if (pressedRecord.indexOf(TURN_RIGHT_KEY)>=0){
            return ACTION_TURN_RIGHT;     
        }
        if (pressedRecord.indexOf(TURN_LEFT_KEY)>=0){
            return ACTION_TURN_LEFT;
        }
        if (pressedRecord.indexOf(SLOW_KEY)>=0){
            return ACTION_SLOW;     
        }
        return ACTION_NO_MATCH;
    }    
    
    public void recordPressedKey(int command){
        pressedRecord += switchCommand(command);
        DebugMode.recordReminder = pressedRecord;
        //System.out.println("按鍵紀錄: " + pressedRecord);
    }
   
    public void cleanPressedKey(){
        pressedRecord = "";
        if(Global.DEBUGMODE == true){
            DebugMode.recordReminder = pressedRecord;
            //DebugMode.resultReminder = "";
            //System.out.println("按鍵紀錄清除");
        }        
    }
    
//    public String switchCommand(int command){
//        switch (command){
//            case Global.UP:
//                return "上";
//            case Global.DOWN:
//                return "下";
//            case Global.LEFT:
//                return "左";
//            case Global.RIGHT:
//                return "右";                
//        }
//        return "";
//    }    
    public String switchCommand(int command){
        switch (command){
            case Global.A:
                return "A";
            case Global.S:
                return "S";
            case Global.D:
                return "D";
            case Global.F:
                return "F";                
        }
        return "";
    }    
    
    public String getPressedRecord(){
        return pressedRecord;
    }
    
    public void setCurrentPressX(int x){
        currentPressX = x;
    }

    public int getLastPressX(){
        return lastPressX;
    }    
    
    public void updateLastPressX(){
        lastPressX = currentPressX;
    }
    
    public int getAccumulation(){
        return accumulation;
    }
    
    public void updateAccumulation(int speed){
        accumulation += speed;
    }
    
    public void cleanAccumulation(){
        accumulation = 0;
    }
        
}