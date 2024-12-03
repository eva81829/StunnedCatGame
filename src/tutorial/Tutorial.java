/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import controller.DelayCounter;
import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class Tutorial extends GameObject{
    
    private BufferedImage img;
    private static final int[] TUTORIAL_ANIME = {0,1,0,2};
    private int num;
    private DelayCounter delayCounter;

    public Tutorial(int x, int y, String name) {
        /* y - 450  鏡頭的修正*/
        super(x, y - 450, Global.TUTORIAL_X_OFFSET, Global.TUTORIAL_Y_OFFSET);
        num = 0;
        this.name = name;
        ImageResourceController irc = ImageResourceController.getInstance();
        delayCounter = new DelayCounter(4);
        setImageRange(0, 0, 0, 0);
    }
    
    @Override
    public void update(){
        if(delayCounter.update()){
            num = ++num % 4;
        }
    }
    
    @Override
    public void paint(Graphics g, int x, int y){
        if (img == null) {
            return;
        }
        g.drawImage(img, this.x - x, this.y - y,this.x - x + Global.TUTORIAL_X_OFFSET,this.y - y + Global.TUTORIAL_Y_OFFSET, 
                0 + TUTORIAL_ANIME[num] * Global.TUTORIAL_X_OFFSET, 0,
                Global.TUTORIAL_X_OFFSET + TUTORIAL_ANIME[num] * Global.TUTORIAL_X_OFFSET, Global.TUTORIAL_Y_OFFSET,null);
    }
}
