/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import controller.*;
import gameobject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class EndingBackground extends GameObject{
    private BufferedImage background;

    public EndingBackground(){
        super(0, 0, Global.FRAME_SIZE_X, Global.FRAME_SIZE_Y);
        ImageResourceController irc = ImageResourceController.getInstance();
        background = irc.tryGetImage(PathBuilder.getImage(ImagePath.Scene.BACKGROUND_GALAXY7));
    }
    
    @Override
    public void paint(Graphics g, int cameraX, int cameraY){
        g.drawImage(background, x, y , x + width, y  +height,
        cameraX -187, cameraY + Global.CAMERA_BOTTOM_LIMIT-90, width + cameraX-187, Global.CAMERA_BOTTOM_LIMIT + height + cameraY-90, null);        
    }
}
