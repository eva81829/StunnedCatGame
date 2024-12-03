/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class BackgroundHelper{
    private ArrayList<BufferedImage> backgrounds;
    private ArrayList<String> backgroundsPath;
    private char[] pictureOrder; //全部圖片排列順序

    public BackgroundHelper(){
        backgroundsPath = new ArrayList<>();
        backgrounds = new ArrayList<>();
    }
    
    public void genBackgrounds(){
        //mageResourceController irc = ImageResourceController.getInstance();
        for(int i = 0; i < pictureOrder.length ; i++){
            //System.out.println(backgroundsPath.get(i));
            backgrounds.add(ImageResourceController.getInstance().tryGetImage(backgroundsPath.get(i)));
        }
    }
    

    public String getImgPath(char mark) {
        String path = null;
        switch (mark) {
            case 'a':
                path = ImagePath.Scene.BACKGROUND_FOREST1;
                break;
            case 'b':
                path = ImagePath.Scene.BACKGROUND_FOREST2;
                break;
            case 'c':
                path = ImagePath.Scene.BACKGROUND_FOREST3;
                break;
            case 'd':
                path = ImagePath.Scene.BACKGROUND_FOREST4;
                break;
            case 'e':
                path = ImagePath.Scene.BACKGROUND_GALAXY1;
                break;
            case 'f':
                path = ImagePath.Scene.BACKGROUND_GALAXY2;
                break;
            case 'g':
                path = ImagePath.Scene.BACKGROUND_GALAXY3;
                break;
            case 'h':
                path = ImagePath.Scene.BACKGROUND_GALAXY4;
                break;
            case 'i':
                path = ImagePath.Scene.BACKGROUND_GALAXY5;
                break;
            case 'j':
                path = ImagePath.Scene.BACKGROUND_GALAXY6;
                break;
            case 'k':
                path = ImagePath.Scene.BACKGROUND_GALAXY7;
                break;
            case 'l':
                path = ImagePath.Scene.BACKGROUND_GALAXY8;
                break;                
        }
        return PathBuilder.getImage(path);
    }
    
    public void genBackgroundsPath(String line) {
        pictureOrder = line.toCharArray();
        //System.out.println(pictureOrder[0] + pictureOrder[1] + pictureOrder[2] + pictureOrder[3]);
        
        for (int i=0; i<pictureOrder.length; i++){
            backgroundsPath.add(getImgPath(pictureOrder[i]));
            //System.out.println(backgroundsPath.get(i));
        }
        genBackgrounds();
    }
    
    public ArrayList<BufferedImage> getBackgrounds() {
        return backgrounds;
    }
    
    public char[] getPictureOrder() {
        return pictureOrder;
    }
    
}
