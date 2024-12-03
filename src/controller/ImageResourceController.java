/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author asdfg
 */
public class ImageResourceController {
    private static class KeyPair{
        private String path;
        private BufferedImage img;
        
        public KeyPair(String path, BufferedImage img){
            this.path = path;
            this.img = img;
        }
    }
    
    private static ImageResourceController irc;
    private ArrayList<KeyPair> imgPairs;
    
    private ImageResourceController(){
        imgPairs = new ArrayList<>();
    }
    
    public static ImageResourceController getInstance(){
        if(irc == null){
            irc = new ImageResourceController();
        }
        return irc;
    }
    /* 判斷圖片路徑是否已經存在，如果存在則回傳路徑 */
    private KeyPair findKeyPair(String path){
        for(int i = 0; i < imgPairs.size(); i++){
            KeyPair pair = imgPairs.get(i);
            if(pair.path.equals(path)){
                return pair;
            }
        }
        return null;
    }
    /* 新增圖片 */
    private BufferedImage addImage(String path){
        try{
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            imgPairs.add(new KeyPair(path,img));
            return img;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
    /* 取得圖片，如果路徑已存在則回傳存在圖片，如沒有該路徑
    則新增 */
    public BufferedImage tryGetImage(String path){
        KeyPair pair = findKeyPair(path);
        if(pair == null){
            return addImage(path);
        }
        return pair.img;
    }
    
    
    
    
    
}
