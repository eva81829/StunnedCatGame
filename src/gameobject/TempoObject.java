/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import controller.DelayCounter;
import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.MovableGameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import temposystem.TempoController;
import utils.DebugMode;
import static utils.DebugMode.recordReminder;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class TempoObject extends MovableGameObject{
    private int[] tempo;
    private int shiftBeatTime;  
    private BufferedImage img;
    private static final int[] yShift = {-5, -5, 0, 0, 5, 5, 0};
    private int shiftCount;
    private DelayCounter delayCounter;
    private boolean isCollision;

    public TempoObject(int x, int y, int[] tempo, int shiftBeatTime) {
        /* 位置是在上面一格，但畫在下面的地方(+70) */
        super(x +35, y - 450 + 70, Global.GRID_SIZE_X -70, Global.GRID_SIZE_Y);
        this.tempo = tempo;
        this.shiftBeatTime = shiftBeatTime;
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Barrier.TEMPO_PLAY));
        setImageRange(0, 0, 0, 0);
        shiftCount = 0;
        delayCounter = new DelayCounter(3);
        isCollision = false;
    }
    
    public void setIsCollision(boolean collision){
        isCollision = collision;
    }
    
    public boolean getIsCollision(){
        return isCollision;
    }    
    
    public int[] getTempo(){
        return tempo;
    }
    
    public int getShiftBeatTime(){
        return shiftBeatTime;
    }    
    
    @Override
    public void update() {
        if(delayCounter.update()){
            //System.out.println(yShift[shiftCount]);
            y += yShift[shiftCount++ % yShift.length];
        }        
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(img, getImageRange().imageX - x,getImageRange().imageY - y, null);
    }
}

