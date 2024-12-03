/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

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
public class EndGameObject extends GameObject{
    private BufferedImage [] imgs;
    private int act;
    private int[] ACT = {0, 1 ,2 ,1 ,0 ,3 ,4 ,3};
    private DelayCounter delayCounter;
    
    public EndGameObject(int x, int y) {
        super(x, y - 450 - 160, Global.GRID_SIZE_X * 3, Global.GRID_SIZE_X * 3);
        ImageResourceController irc = ImageResourceController.getInstance();
        imgs = new BufferedImage[5];
        imgs[0] = irc.tryGetImage(PathBuilder.getImage(ImagePath.PassObject.END_TREE1));
        imgs[1] = irc.tryGetImage(PathBuilder.getImage(ImagePath.PassObject.END_TREE2));
        imgs[2] = irc.tryGetImage(PathBuilder.getImage(ImagePath.PassObject.END_TREE3));
        imgs[3] = irc.tryGetImage(PathBuilder.getImage(ImagePath.PassObject.END_TREE4));
        imgs[4] = irc.tryGetImage(PathBuilder.getImage(ImagePath.PassObject.END_TREE5));
        act = 0;
        delayCounter = new DelayCounter(5);
        setImageRange(0, 0, 0, 0);
    }
    
//    @Override
//    public void update(){
//        if(delayCounter.update() == true){
//            act = ++act % ACT.length;
//        }
//    }
    
    @Override
    public void paint(Graphics g, int x, int y){
        if(imgs == null){
            return;
        }
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(imgs[ACT[act]], imageRange.imageX - x, imageRange.imageY - y,imageRange.getImageWidth(), imageRange.getImageHeight(),null);
    }
    
}
