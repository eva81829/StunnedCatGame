/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popwindow;

import controller.CommandSolver;
import controller.ImageResourceController;
import controller.PathBuilder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import tutorial.Tutorial;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class GameInformationWindow extends PopWindow {
    private ArrayList<Tutorial> tutorial;
    private BufferedImage img ;
    private BufferedImage space ;

    public GameInformationWindow() {
        super(0, 0, Global.FRAME_SIZE_X, Global.FRAME_SIZE_Y);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.GAMEINFORMATION));
        space = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.SPACE2));
    }
    
    @Override
    public boolean isEnd() {
        return false;
    }
    
    public void paint(Graphics g){
        g.drawImage(img, x, y, width, height, null);
        g.drawImage(space, 750, 455, null);
        
    }
    
}
