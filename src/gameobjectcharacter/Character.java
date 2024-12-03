/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjectcharacter;

import audio.Audio;
import audio.Audio.PressedMusic;
import barrier.Barrier;
import barrier.DeadBarrier;
import gameobject.SaveObject;
import gameobject.TempoObject;
import controller.CharacterHelper;
import controller.DelayCounter;
import controller.SceneController;
import gameobject.GameObject;
import gameobject.PassObject;
import gameobject.PhysicalGameObject;
import gameobject.TempoStopObject;
import gameobjectcharacter.Character.StateCat;
import java.util.ArrayList;
import scene.Scene;
import temposystem.TempoSystem;
import title.Title;

/**
 *
 * @author asdfg
 */
public abstract class Character extends PhysicalGameObject{
    
    public abstract static class StateCharacter{
        public abstract void stun(PressedMusic wrongPressedMusic);
    }
    public static class StateCat extends StateCharacter{
        @Override
        public void stun(PressedMusic wrongPressedMusic) {
            wrongPressedMusic.getMeow().play();
        }
    }
    public static class StateBoy extends StateCharacter{
        @Override
        public void stun(PressedMusic wrongPressedMusic) {
            wrongPressedMusic.getFall().play();
        }
    }
    
    public abstract static class StateEmotion{}
    public static class StateNormal extends StateEmotion{}
    public static class StateShock extends StateEmotion{}

    protected StateCharacter currentCharacterState;
    protected StateEmotion currentStateEmotion;
    protected CharacterHelper characterHelper;
    protected DelayCounter delayCounterEmotion;
    protected int[] saveXY;
    protected int beforeJumpHorizontalVelocity;
    public Audio.PressedMusic wrongPressedMusic;
    public Audio.PressedMusic tempoChange;
    public Audio.PressedMusic tempoRecover;
    private int deadTimes;
    protected boolean stop;
    
    public Character(int x, int y, int width, int height) {
        super(x, y, width, height);
        currentStateEmotion = new StateNormal();
        saveXY = new int[2];
        wrongPressedMusic = new Audio.PressedMusic();
        tempoChange = new Audio.PressedMusic();
        tempoRecover = new Audio.PressedMusic();
        beforeJumpHorizontalVelocity = 0;
        deadTimes = 0;
    }
    
    
    public void isCollisionBarrier(ArrayList< ? extends GameObject > gameObjects, TempoSystem tempoSystem, Title title){
        int countTouchTempo = 0;
        int countTouchTop = 0;
        int countTouchLR = 0;
        for(int i = 0 ;i < gameObjects.size(); i++){
            if(isCollision(gameObjects.get(i))){
                if(gameObjects.get(i) instanceof TempoObject){
                    if(!((TempoObject)(gameObjects.get(i))).getIsCollision()){
                        wrongPressedMusic.getTempoChange().play();
                        tempoSystem.setTempo(((TempoObject)(gameObjects.get(i))).getTempo());
                        title.setFeverTimeTrue();
                        ((TempoObject)(gameObjects.get(i))).setIsCollision(true);
                    }
                }
                if(gameObjects.get(i) instanceof TempoStopObject){
                    if(!((TempoStopObject)(gameObjects.get(i))).getIsCollision()){
                        wrongPressedMusic.getTempoRecover().play();
                        tempoSystem.resetTempo();
                        title.setFeverTimeFalse();
                        ((TempoStopObject)(gameObjects.get(i))).setIsCollision(true);
                    }
                }
                if(gameObjects.get(i) instanceof SaveObject){
                    saveXY = ((SaveObject)(gameObjects.get(i))).record(this);
                }
                if(gameObjects.get(i) instanceof Barrier){
                    if(isCollision(gameObjects.get(i))){
                        /* 判斷特殊障礙物 */
                        if(gameObjects.get(i) instanceof DeadBarrier){
                            ((DeadBarrier)(gameObjects.get(i))).backRevivePoint(this, saveXY[0], saveXY[1]);
                            horizontalVelocity = minHorizontalVelocity;
                            verticalVelocity = 0;
                            deadTimes++;
                            continue;
                        }
                        /* 計算角色跟障礙物各邊的差距，其中的最小值就是接觸的面 */
                        int dL = Math.abs(this.getRight() - gameObjects.get(i).getLeft());
                        int dR = Math.abs(this.getLeft() - gameObjects.get(i).getRight());
                        int dB = Math.abs(this.getTop() - gameObjects.get(i).getButtom());
                        int dT = Math.abs(this.getButtom() - gameObjects.get(i).getTop());
                        if(dL < dR && dL < dB && dL < dT){
                            countTouchLR++;
        //                    currentStateMotion = new StateRightStop();
                            x = gameObjects.get(i).getLeft() - width -1;
                        }
                        else if(dR < dL && dR < dB && dR < dT){
                            countTouchLR++;
        //                    currentStateMotion = new StateLeftStop();
                            x = gameObjects.get(i).getRight() + 1 ;
                        }
                        else if(dT < dB && dT < dR && dT < dL && jumpButton == false){
                        /*  */
                            currentStateWeightlessness = new StateOnFloor();
                            y = gameObjects.get(i).getTop() - height ;
                            verticalVelocity = 0;
                            /* 如果是跳躍狀態碰到物體表面，將水平速度設成，跳躍前速度 */
                            if(currentStateMotion instanceof StateJump){
                                horizontalVelocity = beforeJumpHorizontalVelocity;
                                currentStateMotion = new StateMove();
                                currentStateVelocity = new StateDeceleration();
                            }
                            countTouchTop++;
                        }
                        else if(dB < dT && dB < dR && dB < dL){
                            y = gameObjects.get(i).getButtom();
                            verticalVelocity = 0;
                            drop();
                        }
                    }
                }
            } else if(gameObjects.get(i) instanceof TempoObject){
                ((TempoObject)(gameObjects.get(i))).setIsCollision(false);
            } else if(gameObjects.get(i) instanceof TempoStopObject){
                ((TempoStopObject)(gameObjects.get(i))).setIsCollision(false);
            }
            if(countTouchTop == 0){
                currentStateWeightlessness = new StateInAir();
            }
            if(countTouchLR == 0){
        //            currentStateMotion = new StateMove();
            }
        }
    }
    
    public StateEmotion getStateEmotion(){
        return currentStateEmotion;
    }
    
    public void setStateEmotion(StateEmotion currentState){
        this.currentStateEmotion = currentState;
    }
    
    public StateCharacter getCharacterState(){
        return currentCharacterState;
    }
    
    public void setCharacterState(StateCharacter currentCharacterState){
        this.currentCharacterState = currentCharacterState;
    }
    
    public void setCharacterHelper(Character character){
        characterHelper = new CharacterHelper(character);
    }
    /* 紀錄跳躍前的水平速度、將狀態更改成跳躍、如果狀態是在地上按上才有跳躍 */
    public void jump() {
        if(horizontalVelocity >= minHorizontalVelocity){
            beforeJumpHorizontalVelocity = minHorizontalVelocity;
        }else{
            beforeJumpHorizontalVelocity = horizontalVelocity;
        }
        currentStateMotion = new StateJump();
        currentStateWeightlessness.clickUp(this);
    }
    
    public void superJump() {
        if(horizontalVelocity >= minHorizontalVelocity){
            beforeJumpHorizontalVelocity = minHorizontalVelocity;
        }else{
            beforeJumpHorizontalVelocity = horizontalVelocity;
        }
        currentStateMotion = new StateJump();
        currentStateVelocity = new StateAcceleration();
        horizontalVelocity = 16;
        currentStateWeightlessness.clickUp(this);
    }
    
    public void backJump() {
        if(horizontalVelocity >= minHorizontalVelocity){
            beforeJumpHorizontalVelocity = minHorizontalVelocity;
        }else{
            beforeJumpHorizontalVelocity = horizontalVelocity;
        }
        if(currentStateDirection instanceof StateFaceRight){
            currentStateDirection = new StateFaceLeft();
        }else if(currentStateDirection instanceof StateFaceLeft){
            currentStateDirection = new StateFaceRight();
        }
        currentStateMotion = new StateJump();
        currentStateVelocity = new StateAcceleration();
        horizontalVelocity = 16;
        currentStateWeightlessness.clickUp(this);
    }

    public void turnRight() {
        currentStateDirection = new StateFaceRight();
        currentStateMotion.clickRight(this);
        walk();
    }

    public void turnLeft() {
        currentStateDirection = new StateFaceLeft();
        currentStateMotion.clickLeft(this);
        walk();
    }
    
    
    public void slow(){
        currentStateWeightlessness.clickDown(this);
    }
    
    public void stun(){
        currentStateEmotion = new StateShock();
        currentCharacterState.stun(wrongPressedMusic);
    }
    
    public int getDeadTimes(){
        return deadTimes;
    }
    
    public void stop(){
        this.stop = true;
    }
    
}
