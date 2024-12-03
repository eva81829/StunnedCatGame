/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temposystem;

import audio.Audio;
import audio.Audio.PressedMusic;
import controller.DelayCounter;
import gameobjectcharacter.SmallCat;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import utils.DebugMode;
import utils.Global;

/**
 *
 * @author Eka
 */
public class TempoSystem {
//    public static final int ATTACK_EVENT = 0;
//    public static final int DEFENSE_EVENT = 1;
    public static final int HAPPY_EVENT = 2;       
    public static final int STUN_EVENT = 3;    
    public static final int JUMP_EVENT = 4;
    public static final int TURN_RIGHT_EVENT = 5;
    public static final int TURN_LEFT_EVENT = 6;
    public static final int SLOW_EVENT = 7;
    public static final int SUPER_JUMP_EVENT = 8;
    public static final int BACK_JUMP_EVENT = 9;
    private SystemBeat beat;
    private PressedKeyProcessor processor;
    private CorrectTempoEffect correctTempoEffect;
    private int correctTimes;
    private int wrongTimes;

    
    public TempoSystem (int[] tempo){
        beat = new SystemBeat (0, Global.BEAT_Y, (int)((Global.FRAME_SIZE_X)/2), Global.BEAT_Y, tempo);
        correctTempoEffect = new CorrectTempoEffect();
        processor = new PressedKeyProcessor();
        correctTimes = 0;
        wrongTimes = 0;
    }
    
    public TempoSystem(SystemBeat beat) {
        this.beat = beat;
    }
    
    public void update(){
        if(!DebugMode.allowMove){
            return;
        }
        beat.update();
        processor.updateAccumulation(beat.getSpeed());
        correctTempoEffect.update();
    }   
    
    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, Global.FRAME_SIZE_X , Global.GRID_SIZE_Y);
        g.fillRect(0, Global.REC_Y, Global.FRAME_SIZE_X , Global.REC_Y);
        beat.paint(g);
        correctTempoEffect.paint(g);
        DebugMode.paintOutput(g);
    }
    
    public int checkEvent(int command){ //每次有效按下按鍵時執行一次
        processor.setCurrentPressX(beat.getX());
        
        if (checkOnTempo()){ //檢查是否有在節拍上
            if (checkPressGap() == false){
                correctTempoEffect.triggerWrong();
                processor.cleanPressedKey();
                processor.cleanAccumulation();
                wrongTimes++;
                return STUN_EVENT;
            }
            correctTimes++;
//            correctTempoEffect.setPressKeyIsOnTempo(true);
            processor.updateLastPressX();
            processor.recordPressedKey(command);
            switch (processor.checkAction(command)){ //檢查按健是否有對到動作
                case PressedKeyProcessor.ACTION_NO_MATCH: //沒對到動作
                    correctTempoEffect.triggerCorrect();
                    DebugMode.resultReminder = "節拍對，沒對應動作";
                    processor.cleanAccumulation();
                    return HAPPY_EVENT;                
//                case PressedKeyProcessor.ACTION_ATTACK: //對到攻擊動作
//                    DebugMode.resultReminder = "節拍對，攻擊";
//                    processor.cleanPressedKey();
//                    processor.cleanAccumulation();
//                    return ATTACK_EVENT;
//                case PressedKeyProcessor.ACTION_DEFENSE: //對到攻擊動作
//                    DebugMode.resultReminder = "節拍對，防禦";
//                    processor.cleanPressedKey();
//                    processor.cleanAccumulation();
//                    return DEFENSE_EVENT;
                case PressedKeyProcessor.ACTION_JUMP: //對到攻擊動作
                    DebugMode.resultReminder = "jump";
                    correctTempoEffect.triggerWrong();
                    processor.cleanPressedKey();
                    processor.cleanAccumulation();
                    return JUMP_EVENT;
                case PressedKeyProcessor.ACTION_SUPER_JUMP: //對到攻擊動作
                    DebugMode.resultReminder = "super jump";
                    correctTempoEffect.triggerWrong();
                    processor.cleanPressedKey();
                    processor.cleanAccumulation();
                    return SUPER_JUMP_EVENT;
                case PressedKeyProcessor.ACTION_BACK_JUMP: //對到攻擊動作
                    DebugMode.resultReminder = "back jump";
                    correctTempoEffect.triggerWrong();
                    processor.cleanPressedKey();
                    processor.cleanAccumulation();
                    return BACK_JUMP_EVENT;
                case PressedKeyProcessor.ACTION_TURN_RIGHT: //對到攻擊動作
                    DebugMode.resultReminder = "move right";
                    correctTempoEffect.triggerWrong();
                    processor.cleanPressedKey();
                    processor.cleanAccumulation();
                    return TURN_RIGHT_EVENT;
                case PressedKeyProcessor.ACTION_TURN_LEFT: //對到攻擊動作
                    DebugMode.resultReminder = "move left";
                    correctTempoEffect.triggerWrong();
                    processor.cleanPressedKey();
                    processor.cleanAccumulation();
                    return TURN_LEFT_EVENT;
                case PressedKeyProcessor.ACTION_SLOW:
                    DebugMode.resultReminder = "stop";
                    correctTempoEffect.triggerWrong();
                    processor.cleanPressedKey();
                    processor.cleanAccumulation();
                    return SLOW_EVENT;
            }
        } else { //沒在節拍上
//            correctTempoEffect.setPressKeyIsOnTempo(false);
            DebugMode.resultReminder = "節拍錯";
            correctTempoEffect.triggerWrong();
            processor.cleanAccumulation();
            processor.cleanPressedKey();
            wrongTimes++;
            return STUN_EVENT;
        }
        return -1;
    }
    
    public int getCorrectTimes(){
        return correctTimes;
    }
    public int getWrongTimes(){
        return wrongTimes;
    }
    
    public boolean checkPressGap(){
        if(!processor.getPressedRecord().equals("")){ //如果上次按鍵結果已被清除(上次按錯/重複/不連續，新的開始)，就不必再判斷兩次按鍵間隔距離是否合理
            //檢查兩次按鍵間隔距離是否小於範圍 (代表一拍之中按了兩次)
            boolean pressDuplicate = processor.getAccumulation() < calculatePressBound();
            //檢查兩次按鍵間隔距離是否大於範圍 (代表兩拍不連續)
            boolean pressUncontinuous = processor.getAccumulation() > beat.getMoveCycle() + calculatePressBound();
            boolean pressGapTooLong = processor.getAccumulation() > beat.getMoveCycle() * 2 + calculatePressBound();
            if(pressDuplicate){
                DebugMode.resultReminder = "重複按";
                //System.out.println("重複按");        
                return false;
            }
            if(pressGapTooLong){
                DebugMode.resultReminder = "隔太久沒按";
                correctTempoEffect.triggerWrong();
                processor.cleanPressedKey();
                processor.cleanAccumulation();
                return true;
            }
            if(pressUncontinuous){
                DebugMode.resultReminder = "不連續";
                //System.out.println("不連續");           
                return false;
            }
        }
        return true;
    }
    
    public int calculatePressBound(){
       // 上次按下位置靠左還是右
       boolean lastPressPosition = processor.getLastPressX() > beat.getLeftOnTempleRange();
       // 上次按下距離邊緣限制
       int lastPressBound = lastPressPosition ? beat.getBeatEndX() - processor.getLastPressX(): beat.getLeftOnTempleRange() - processor.getLastPressX();
       // 實際累積距離限制，如果上次按下是在左邊的容忍範圍，要加上容忍範圍的距離
       return lastPressPosition ? lastPressBound + beat.getOnTempleRange(): lastPressBound;
    }
    
    public void setTempo(int [] tempo){
        beat.setTempo(tempo);
    }
    
    public void resetTempo(){
        beat.resetTempo();
    }
    
    private boolean checkOnTempo(){
        return beat.checkOnTempo();
    }
    
    public boolean checkOnCentral(){
        return beat.checkOnCentral();
    }    
    
    public SystemBeat getBeat(){
        return beat;
    }
}
