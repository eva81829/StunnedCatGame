/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import utils.DebugMode;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class GrassGround extends Barrier{
    private Graphics2D barrierTest;
    private BufferedImage img;

    public GrassGround(int x, int y) {
        super(x, y, Global.GRID_SIZE_X, Global.GRID_SIZE_Y, 0, 0, 80,-40);
        /* 後面是實際碰撞範圍左右上下 */
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Barrier.GRASS));
    }
    
    @Override
    public void update(){
        
    }

    @Override
    public void paint(Graphics g) {
//        DebugMode.paintObject(this, g);
//        setImagePosition();
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        DebugMode.paintObject(this, g, x, y);
        setImagePosition();
        g.drawImage(img, getImageRange().imageX - x,getImageRange().imageY - y , null);
        //g.setColor(Color.black);
        //g.fillRect(getImageRange().imageX - x, getImageRange().imageY - y, getImageRange().getImageWidth(), getImageRange().getImageHeight());
    }
}
