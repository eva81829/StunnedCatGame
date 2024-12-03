/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import controller.BackgroundHelper;
import controller.DelayCounter;
import gameobject.GameObject;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import utils.Global;

/**
 *
 * @author Eka
 */
public class SceneBackground extends GameObject{
    protected BackgroundHelper backgroundHelper;
    private int currentPage;
    private int nextPage;
    private float alpha;
    private DelayCounter delayCounterEnd;
    
    public SceneBackground() {
        super(0, 0, Global.FRAME_SIZE_X, Global.FRAME_SIZE_Y);
        backgroundHelper = new BackgroundHelper();
        alpha = 1f;
        delayCounterEnd = new DelayCounter(1);
        //ImageResourceController irc = ImageResourceController.getInstance();
//        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Scene.BACKGROUND_FOREST)); 
    }
    
//    public ArrayList<String> getBackgroundsPath(){
//        return backgroundHelper.backgroundsPath;
//    }    
    
    public int getBackgroundSize(){
        return backgroundHelper.getPictureOrder().length;
    }

    public void genBackgroundsPath(String line) {
        backgroundHelper.genBackgroundsPath(line);
    }
    
    public void fadeOut(){
        if(delayCounterEnd.update()){
            alpha -= 0.05f;
            if(alpha < 0){
                alpha = 0;
            }
        }
        
    }
    
    @Override
    public void paint(Graphics g){
        if(backgroundHelper.getBackgrounds() == null){
            return;
        }
    }
    
    @Override
    public void paint(Graphics g, int cameraX, int cameraY){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        //System.out.println(alpha);
        if(backgroundHelper.getBackgrounds() == null){
            return;
        }
        currentPage = cameraX  / Global.BACKGROUND_SIZE_X;
        nextPage = currentPage + 1;
        /* 依照camera的X 畫出需要涵蓋範圍的背景(最多2張) */
        for(int i = currentPage; i <= currentPage + 1; i++){
            g2d.drawImage(backgroundHelper.getBackgrounds().get(i), x, y , x + width, y  +height,
            cameraX - Global.BACKGROUND_SIZE_X * i, cameraY + Global.CAMERA_BOTTOM_LIMIT, width + cameraX -Global.BACKGROUND_SIZE_X * i,Global.CAMERA_BOTTOM_LIMIT + height + cameraY, null);
//            System.out.println(currentPage);
//            System.out.println("畫在背景x,y座標: "+ x +","+ y+" 畫的寬: "+(x + width)+" 畫的高: "+(y + height));
//            System.out.println("取圖的x,y座標: "+ (cameraX - Global.FRAME_SIZE_X * i) +","+ (cameraY + Global.CAMERA_BOTTOM_LIMIT));
//            System.out.println("取圖的寬: "+ (width + cameraX - Global.FRAME_SIZE_X *i));
//            System.out.println("取圖的高: "+ (Global.CAMERA_BOTTOM_LIMIT+ height + cameraY));
//            System.out.println("--------------------------------");
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }    

    public int getCurrentPage(){
        return currentPage;
    }
    
    public int getNextPage(){
        return nextPage;
    }
    
    public float getAlpha(){
        return alpha;
    }
    
}