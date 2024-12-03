/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import utils.Global;

/**
 *
 * @author asdfg
 */
public abstract class MovableGameObject extends GameObject{
    protected int speed;
    
    public MovableGameObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public MovableGameObject(int x, int y, int width, int height,int speed) {
        super(x, y, width, height);
        setSpeed(speed);
    }
    
    public void setSpeed(int speed){
        this.speed = speed * Global.ACTION_SPEED;
    }
    
    public int getSpeed(){
        return speed;
    }
}
