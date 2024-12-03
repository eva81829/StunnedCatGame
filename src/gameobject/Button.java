/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import controller.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class Button extends GameObject {

    public interface ButtonListener {
        public void onClick(int x, int y);
        public void hover(int x, int y);
    }

    private ButtonListener buttonListener;
    private BufferedImage[] img;
    private int currentImg;
    private String str;

    public Button(int x, int y, int width, int height, String str) {
        super(x, y, width, height);
        img = new BufferedImage[2];
        this.str = str;
        setButton(str,img);
        currentImg = 0;
    }

//    public Button(int x, int y,int number) {
//        super(x, y, Global.BUTTON_X_OFFSET, Global.BUTTON_Y_OFFSET);
//        img = new BufferedImage[2];
//        setButton(number, img);
//        currentImg = 0;
//    }
    
    public void setButton(String str, BufferedImage[] img){
        ImageResourceController irc = ImageResourceController.getInstance();
        switch(str){
            case "Start":
                img[0] = irc.tryGetImage(PathBuilder.getImage(ImagePath.Button.BUTTON_START));
                img[1] = irc.tryGetImage(PathBuilder.getImage(ImagePath.Button.BUTTON_START_SELECTED));
                break;
            case "Resume":
                img[0] = irc.tryGetImage(PathBuilder.getImage(ImagePath.Button.BUTTON_RESUME));
                img[1] = irc.tryGetImage(PathBuilder.getImage(ImagePath.Button.BUTTON_RESUME_SELECTED));
                break;
            case "Exit":
                img[0] = irc.tryGetImage(PathBuilder.getImage(ImagePath.Button.BUTTON_EXIT));
                img[1] = irc.tryGetImage(PathBuilder.getImage(ImagePath.Button.BUTTON_EXIT_SELECTED));
                break;
        }
    }

    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public boolean isCollision(int x, int y) {
        if (x < this.x || x > this.x + this.width) {
            currentImg = 0;
            return false;
        }
        if (y < this.y || y > this.y + this.height) {
            currentImg = 0;
            return false;
        }
        currentImg = 1;
        return true;
    }

    public void click(int x, int y) {
        if (buttonListener == null) {
            return;
        }
        buttonListener.onClick(x, y);
    }

    public void hover(int x, int y) {
        if (buttonListener == null) {
            return;
        }
        buttonListener.hover(x, y);
    }

    @Override
    public void paint(Graphics g) {
//        g.drawRect(x, y, width, height);
        if(currentImg == 0){
            g.drawImage(img[currentImg], (int)x+10, (int)y + 5, (int)width-20, (int)height-10, null);
        }else if(currentImg == 1){
            g.drawImage(img[currentImg], (int)x, (int)y, (int)width, (int)height, null);
        }
    }
}

