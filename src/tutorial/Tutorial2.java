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
import gameobject.MovableGameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DebugMode;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class Tutorial2 extends MovableGameObject{
    private Letter[] letter;
    private ImageResourceController irc;
    private BufferedImage actionImg;
    private int actionX;
    private int actionY;
    public int alignLetterX = 20;
    public int alignLetterY = 20;
    public int alignActionX = Global.GRID_SIZE_Y/2;
    public int alignActionY = 20;    
    public int onCentralCount; 
        
    private class Letter{
        private BufferedImage letterBlackImg;
        private BufferedImage letterGrayImg;
        private int letterX;
        private int letterY;
        
        public Letter(int letterX, int letterY, BufferedImage letterBlackImg, BufferedImage letterGrayImg){
            this.letterX = letterX;
            this.letterY = letterY;
            this.letterBlackImg = letterBlackImg;
            this.letterGrayImg = letterGrayImg;
        }
    }

    public Tutorial2(int x, int y, String action) {
        super(x, y - 450, 160, 90); // y - 450  鏡頭的修正
        
        alignLetterX = 20 - Global.GRID_SIZE_X;
        alignLetterY = 20;
        alignActionX = -15 - Global.GRID_SIZE_X/2;
        alignActionY = Global.GRID_SIZE_Y;
        
        irc = ImageResourceController.getInstance();
        letter = new Letter[4];
        for(int i=0; i<4; i++){
            letter[i] = new Letter(super.x + alignLetterX + Global.LETTER_X_OFFSET * i, super.y + alignLetterY, getBlackLetterPath(action.charAt(i)), getGrayLetterPath(action.charAt(i)));
        }
        actionImg = getActionPath(action);
        actionX = super.x + alignActionX;
        actionY = super.y + alignActionY;
        onCentralCount = -1;
        setImageRange(Global.GRID_SIZE_X,0, 0,Global.GRID_SIZE_Y );
    }
    
    
    
    @Override
    public void update(boolean onCentral){
        if (onCentral){ //lastOnCentral != onCentral
            onCentralCount++;
            if(onCentralCount==4){
                onCentralCount = -1;
            }
        }
    }
    
    @Override
    public void paint(Graphics g, int x, int y){
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(actionImg, actionX - x, actionY - y, Global.ACTION_WIDTH, Global.ACTION_HEIGHT, null);
        
        for(int i=0; i<4; i++){
            if(onCentralCount >= i){
                g.drawImage(letter[i].letterBlackImg, letter[i].letterX - x, letter[i].letterY - y, Global.LETTER_WIDTH, Global.LETTER_HEIGHT, null);
                continue;
            }
            g.drawImage(letter[i].letterGrayImg, letter[i].letterX - x, letter[i].letterY - y, Global.LETTER_WIDTH, Global.LETTER_HEIGHT, null);
        }
    }
    
    public BufferedImage getActionPath(String action){
        switch (action) {
            case Global.MOVE_RIGHT:
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.MOVE_RIGHT));
            case Global.MOVE_LEFT:
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.MOVE_LEFT));
            case Global.BACK_JUMP:
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.BACK_JUMP));
            case Global.SUPER_JUMP:
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.SUPER_JUMP));
            case Global.JUMP:
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.JUMP));
            case Global.STOP:
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.STOP));
        }
        return null;
    }
    public BufferedImage getBlackLetterPath(char mark){
        switch (mark) {
            case 'A':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.A_BLACK));
            case 'S':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.S_BLACK));             
            case 'D':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.D_BLACK));
            case 'F':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.F_BLACK));             
        }
        return null;
    }
    public BufferedImage getGrayLetterPath(char mark){
        switch (mark) {
            case 'A':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.A_GRAY));
            case 'S':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.S_GRAY));             
            case 'D':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.D_GRAY));
            case 'F':
                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.F_GRAY));                 
        }
        return null;
    }    
}
