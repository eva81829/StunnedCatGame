/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import barrier.Barrier;
import controller.DelayCounter;
import java.util.ArrayList;
import utils.Global;
import static utils.Global.ACTION_SPEED;    
import gameobjectcharacter.Character;

/**
 *
 * @author asdfg
 */
public abstract class PhysicalGameObject extends MovableGameObject{
    public static abstract class StateWeightlessness{
        public abstract void clickUp(PhysicalGameObject physicalGameObject);
        public abstract void clickDown(PhysicalGameObject physicalGameObject);
        public abstract void clickLeft(PhysicalGameObject physicalGameObject);
        public abstract void clickRight(PhysicalGameObject physicalGameObject);
    }
    
    public static class StateOnFloor extends StateWeightlessness{
        @Override
        public void clickUp(PhysicalGameObject physicalGameObject) {
            physicalGameObject.setJumpButton(true);
            physicalGameObject.currentStateWeightlessness = new StateInAir();
            physicalGameObject.setVerticalVelocity(physicalGameObject.jumpForce);
        }

        @Override
        public void clickDown(PhysicalGameObject physicalGameObject) {
            physicalGameObject.currentStateVelocity = new StateSlow();
            physicalGameObject.setHorizontalVelocity(0);
            physicalGameObject.delayCounterAct.setDelay(4);
        }

        @Override
        public void clickLeft(PhysicalGameObject physicalGameObject) {
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
        }

        @Override
        public void clickRight(PhysicalGameObject physicalGameObject) {
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
        }
    }
    
    public static class StateInAir extends StateWeightlessness{
        @Override
        public void clickUp(PhysicalGameObject physicalGameObject) {
            
        }

        @Override
        public void clickDown(PhysicalGameObject physicalGameObject) {
            
        }

        @Override
        public void clickLeft(PhysicalGameObject physicalGameObject) {
        }

        @Override
        public void clickRight(PhysicalGameObject physicalGameObject) {
        }
    }
    
    public static abstract class StateVelocity{
    }
    
    public static class StateAcceleration extends StateVelocity{
    }
    
    public static class StateDeceleration extends StateVelocity{
    }
    
    public static class StateSlow extends StateVelocity{
        
    }
    
    public static abstract class StateDirection{
        public abstract void paint(PhysicalGameObject physicalGameObject);
    }

    public static class StateFaceRight extends StateDirection{

        @Override
        public void paint(PhysicalGameObject physicalGameObject) {
            
        }
        
    }
    
    public static class StateFaceLeft extends StateDirection{
        @Override
        public void paint(PhysicalGameObject physicalGameObject) {
            
        }
    }
    
    public static abstract class StateMotion{
        public abstract void clickRight(PhysicalGameObject physicalGameObject);
        public abstract void clickLeft(PhysicalGameObject physicalGameObject);
    }
     
    public static class StateRightStop extends StateMotion{
        @Override
        public void clickRight(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateDirection = new StateFaceRight();
        }
        @Override
        public void clickLeft(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateDirection = new StateFaceLeft();
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
            physicalGameObject.currentStateMotion = new StateMove();
        }
    }
    
    public static class StateLeftStop extends StateMotion{
        @Override
        public void clickRight(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateDirection = new StateFaceRight();
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
            physicalGameObject.currentStateMotion = new StateMove();
        }
        @Override
        public void clickLeft(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateDirection = new StateFaceLeft();
            
        }
    }
    
    public static class StateMove extends StateMotion{
        @Override
        public void clickRight(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
        }
        @Override
        public void clickLeft(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
        }
    }
    
    public static class StateJump extends StateMotion{
        @Override
        public void clickRight(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
        }
        @Override
        public void clickLeft(PhysicalGameObject physicalGameObject){
            physicalGameObject.currentStateVelocity = new StateAcceleration();
            physicalGameObject.delayCounterAct.setDelay(2);
        }
    }
    
    protected int verticalVelocity;
    protected int horizontalVelocity;
    protected int maxHorizontalVelocity;
    protected int minHorizontalVelocity;
    protected int acceleration;
    protected int jumpForce;
    protected boolean jumpButton;
    protected DelayCounter delayCounterAct;
    protected DelayCounter delayCounterSprint;
    protected StateWeightlessness currentStateWeightlessness;
    protected StateVelocity currentStateVelocity;
    protected StateDirection currentStateDirection;
    protected StateMotion currentStateMotion;
    
    public PhysicalGameObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        delayCounterSprint = new DelayCounter(20);
    }
    
    public void isCollisionBarrier(ArrayList< ? extends GameObject > barriers){
        int countTouchTop = 0;
        int countTouchLR = 0;
        for(int i = 0 ;i < barriers.size(); i++){
            if(isCollision(barriers.get(i))){
                int dL = Math.abs(this.getRight() - barriers.get(i).getLeft());
                int dR = Math.abs(this.getLeft() - barriers.get(i).getRight());
                int dB = Math.abs(this.getTop() - barriers.get(i).getButtom());
                int dT = Math.abs(this.getButtom() - barriers.get(i).getTop());
                if(dL < dR && dL < dB && dL < dT){
                    countTouchLR++;
                    currentStateMotion = new StateRightStop();
                    x = barriers.get(i).getLeft() - width -1;
                }
                else if(dR < dL && dR < dB && dR < dT){
                    countTouchLR++;
                    currentStateMotion = new StateLeftStop();
                    x = barriers.get(i).getRight() +1;
                }
                else if(dT < dB && dT < dR && dT < dL && jumpButton == false){
                    currentStateWeightlessness = new StateOnFloor();
                    y = barriers.get(i).getTop() - height;
                    verticalVelocity = 0;
                    countTouchTop++;
                }
                else if(dB < dT && dB < dR && dB < dL){
                    y = barriers.get(i).getButtom();
                    verticalVelocity = 0;
                    drop();
                }
            }
        }
        if(countTouchTop == 0){
            currentStateWeightlessness = new StateInAir();
        }
        if(countTouchLR == 0){
            currentStateMotion = new StateMove();
        }
    }
    
    public void drop(){
        verticalVelocity -= Global.Acceleration.G;
        y -= verticalVelocity / 15;
        jumpButton = false;
    }
    
    public void walk(){
        if(horizontalVelocity < maxHorizontalVelocity && currentStateVelocity instanceof StateAcceleration){
            horizontalVelocity += acceleration / 4;
        }
        else if(horizontalVelocity >= maxHorizontalVelocity){
            if(delayCounterSprint.update()){
                currentStateVelocity = new StateDeceleration();
                horizontalVelocity -= acceleration / 4;
            }
        }else if(horizontalVelocity > minHorizontalVelocity && currentStateVelocity instanceof StateDeceleration){
            horizontalVelocity -= acceleration / 4;
        }else if(horizontalVelocity == minHorizontalVelocity && currentStateVelocity instanceof StateDeceleration){
            currentStateVelocity = null;
        }
    }
    
    public int getMaxHorizontalVelocity(){
        return maxHorizontalVelocity;
    }
    
    public void setMaxHorizontalVelocity(int maxHorizontalVelocity){
        this.maxHorizontalVelocity = maxHorizontalVelocity * ACTION_SPEED;
    }
    
    public int getMinHorizontalVelocity(){
        return minHorizontalVelocity;
    }
    
    public void setMinHorizontalVelocity(int minHorizontalVelocity){
        this.minHorizontalVelocity = minHorizontalVelocity * ACTION_SPEED;
    }
    
    public int getJumpForce(){
        return jumpForce;
    }
    
    public void setJumpForce(int jumpForce){
        this.jumpForce = jumpForce * ACTION_SPEED;
    }
    
    public int getVerticalVelocity(){
        return verticalVelocity;
    }
    
    public int getHorizontalVelocity(){
        return horizontalVelocity;
    }
    
    public void setVerticalVelocity(int verticalVelocity){
        this.verticalVelocity = verticalVelocity * ACTION_SPEED;
    }
    
    public void setHorizontalVelocity(int horizontalVelocity){
        this.horizontalVelocity = horizontalVelocity * ACTION_SPEED;
    }
    
    public void setAcceleration(int acceleration){
        this.acceleration = acceleration * ACTION_SPEED;
    }
    
    public int getAcceleration(){
        return acceleration;
    }
    
    public void setStateWeightlessness(StateWeightlessness currentState){
        this.currentStateWeightlessness = currentState;
    }
    
    public void setStateVelocity(StateVelocity currentState){
        this.currentStateVelocity = currentState;
    }
       
    public void setStateFace(StateDirection currentState){
        this.currentStateDirection = currentState;
    }
    
    public void setStateMotion(StateMotion currentState){
        this.currentStateMotion = currentState;
    }
    
    public void setJumpButton(boolean jumpButton){
        this.jumpButton = jumpButton;
    }
    
    public StateWeightlessness getStateWeightlessness(){
        return currentStateWeightlessness;
    }
    
    public StateVelocity getStateVelocity(){
        return currentStateVelocity;
    }
    
    public StateDirection getStateDirection(){
        return currentStateDirection;
    }
    
    public StateMotion getStateMotion(){
        return currentStateMotion;
    }
    
}
