/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;
import controller.ImageResourceController;
import controller.PathBuilder;
import gameobjectcharacter.Character;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DebugMode;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class SaveObject extends GameObject{
    private int[] recordXY;
    private BufferedImage img;
    public SaveObject(int x, int y) {
        super(x - Global.GRID_SIZE_X, y - 450, Global.GRID_SIZE_X * 2, Global.GRID_SIZE_X * 2 - 100);
        recordXY = new int [2];
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Barrier.SAVE_POINT));
        setImageRange(0, 0, 0, 100);
    }
    
    public int [] record(Character character){
        recordXY[0] = x + 100;
        recordXY[1] = y ;
        return recordXY;
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(img, this.imageRange.getImageX()- x, this.imageRange.getImageY() - y , null);
    }
}
