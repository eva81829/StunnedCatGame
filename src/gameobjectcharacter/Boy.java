/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjectcharacter;

import java.awt.Graphics;
import controller.*;
import utils.*;

/**
 *
 * @author asdfg
 */
public class Boy extends Character{
    private int act;
    
    
    public Boy(int x, int y, int width, int height) {
        super(x, y, width, height);
        characterHelper = new CharacterHelper(this);
        act = 0;
        setJumpForce(CharacterParameter.BoyParameter.JUMP_FORCE);
        setMaxHorizontalVelocity(CharacterParameter.BoyParameter.MAX_HORIZONTAL_VELOCITY);
        setMinHorizontalVelocity(CharacterParameter.BoyParameter.MIN_HORIZONTAL_VELOCITY);
        setAcceleration(CharacterParameter.BoyParameter.ACCELERATION);
        setImageRange(CharacterParameter.BoyParameter.LEFT_DIFF,
                CharacterParameter.BoyParameter.RIGHT_DIFF,
                CharacterParameter.BoyParameter.TOP_DIFF,
                CharacterParameter.BoyParameter.BUTTOM_DIFF);
        delayCounterAct = new DelayCounter(CharacterParameter.BoyParameter.ACT_DELAY);
        delayCounterEmotion = new DelayCounter(6);
        currentStateWeightlessness = new StateInAir();
        currentStateDirection = new StateFaceLeft();
        currentCharacterState = new StateBoy();
        currentStateMotion = new StateMove();
        characterHelper = new CharacterHelper(this);
        stop = false;
        
    }
    
    public void stop(){
        this.stop = true;
        this.act = 1;
    }
    
     @Override
    public void update(){
        if(stop == false){
            if(delayCounterAct.update()){
                act = ++act % characterHelper.getActExcute().length;
            }
        }
        if(currentStateWeightlessness instanceof StateInAir){
            drop();
        }
        if(currentStateEmotion instanceof StateShock){
            if(delayCounterEmotion.update()){
                currentStateEmotion = new StateNormal();
            }
        }
        if(currentStateDirection instanceof StateFaceRight && !(currentStateMotion instanceof StateRightStop)){
            walk();
            x += horizontalVelocity / 4;
        }else if(currentStateDirection instanceof StateFaceLeft && !(currentStateMotion instanceof StateLeftStop)){
            walk();
            x -= horizontalVelocity / 4;    
        }
        setCharacterHelper(this);
    }
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        characterHelper.paint(g, imageRange.imageX - x, imageRange.imageY - y, imageRange.imageWidth, imageRange.imageHeight, act);
    }
    
}
