/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import controller.DelayCounter;
import controller.ImageResourceController;
import controller.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DebugMode;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class MovingBarrier extends Barrier{
    protected BufferedImage img;
    protected int directTime;
    protected int speedX;
    protected int distanceGridX;
    protected int speedY;
    protected int distanceGridY;
    
    public MovingBarrier(int x, int y, int width, int height,int leftDiff, int RightDiff,int topDiff, int bottomDiff) {
        super(x, y, width ,height,leftDiff ,RightDiff ,topDiff ,bottomDiff );
        directTime = 0;
    }
    
    public void setSpeedX(int speedX){
        this.speedX = speedX * Global.ACTION_SPEED / 4;
    }
    
    public void setDistanceGridX(int grid){
        this.distanceGridX = grid * Global.GRID_SIZE_X;
    }
    
    public void setSpeedY(int speedY){
        this.speedY = speedY * Global.ACTION_SPEED / 4;
    }
    
    public void setDistanceGridY(int grid){
        this.distanceGridY = grid * Global.GRID_SIZE_Y;
    }
    
    @Override
    public void update(){}
    
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(img, getImageRange().imageX - x,getImageRange().imageY - y , null);
    }
}
