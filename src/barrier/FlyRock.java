/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import controller.ImageResourceController;
import controller.PathBuilder;
import java.awt.Graphics;
import utils.DebugMode;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class FlyRock extends MovingBarrier{

    public FlyRock(int x, int y,int speedX, int distanceGridX,int speedY, int distanceGridY) {
        super(x, y, Global.GRID_SIZE_X, Global.GRID_SIZE_Y, 0, 0, 20, 30);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Barrier.FLYROCK));
        setSpeedX(speedX);
        setDistanceGridX(distanceGridX);
        setSpeedY(speedY);
        setDistanceGridY(distanceGridY);
    }
    
    @Override
    public void update(){
        if(speedX != 0){
            if(directTime / (distanceGridX/speedX) == 0){
                x += speedX;
                directTime++;
                return;
            }else if(directTime / (distanceGridX/speedX) == 1){
                x -= speedX;
                directTime++;
                return;
            }
        }else if(speedY != 0){
            if(directTime / (distanceGridY/speedY) == 0){
                y += speedY;
                directTime++;
                return;
            }else if(directTime / (distanceGridY/speedY) == 1){
                y -= speedY;
                directTime++;
                return;
            }
        }
        directTime = 0;
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(img, getImageRange().imageX - x,getImageRange().imageY - y , null);
    }
}
