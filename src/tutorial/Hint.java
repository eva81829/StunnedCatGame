/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DebugMode;
import utils.Global;

/**
 *
 * @author asdfg
 */
public class Hint extends GameObject{
    private BufferedImage img;
    public Hint(int x, int y, String imagePath) {
        super(x , y - 450 + 25, Global.GRID_SIZE_X * 2 -80 , Global.GRID_SIZE_Y * 2 - 45);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(imagePath));
        setImageRange(0, 0, 0, 0);
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
//        DebugMode.paintObject(this, g, x, y);
        g.drawImage(img, this.imageRange.getImageX()- x, this.imageRange.getImageY() - y , null);
    }
    
}
