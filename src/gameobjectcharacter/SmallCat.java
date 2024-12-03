/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjectcharacter;

import controller.*;
import utils.*;
import java.awt.Graphics;


/**
 *
 * @author asdfg
 */
public class SmallCat extends Character{
    private int act;

    public SmallCat(int x, int y, int width, int height) {
        super(x, y, width, height);
        characterHelper = new CharacterHelper(this);
        act = 0;
        setJumpForce(CharacterParameter.CatParameter.JUMP_FORCE);
        setMaxHorizontalVelocity(CharacterParameter.CatParameter.MAX_HORIZONTAL_VELOCITY);
        setMinHorizontalVelocity(CharacterParameter.CatParameter.MIN_HORIZONTAL_VELOCITY);
        setAcceleration(CharacterParameter.CatParameter.ACCELERATION);
        setImageRange(CharacterParameter.CatParameter.LEFT_DIFF,
                CharacterParameter.CatParameter.RIGHT_DIFF,
                CharacterParameter.CatParameter.TOP_DIFF,
                CharacterParameter.CatParameter.BUTTOM_DIFF);
        delayCounterAct = new DelayCounter(CharacterParameter.CatParameter.ACT_DELAY);
        delayCounterEmotion = new DelayCounter(8);
        currentStateWeightlessness = new StateInAir();
        currentStateDirection = new StateFaceRight();
        currentCharacterState = new StateCat();
        currentStateMotion = new StateMove();
        characterHelper = new CharacterHelper(this);
    }
    @Override
    public void update(){
        if(delayCounterAct.update()){
            act = ++act % characterHelper.getActExcute().length;
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
