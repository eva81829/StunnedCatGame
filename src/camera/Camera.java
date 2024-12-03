/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import gameobject.GameObject;
import utils.Global;

/**
 *
 * @author asdfg
 */
public class Camera {
    private GameObject gameObject;

    private int cameraX;
    private int cameraY;
    private int camerawidth;
    private int cameraheight;
    private float cameraSpeedX;
    private float cameraSpeedY;
    private float focalX;
    private float focalY;
    private int backgroundPages;

    private int desX;
    private int desY;
    
    // 絕對座標 相對座標

    public Camera(int backgroundPages, int startX, int startY,float focalX, float focalY) {
        this.backgroundPages = backgroundPages;
        cameraX = startX;
        cameraY = startY;
        this.camerawidth = Global.FRAME_SIZE_X;
        this.cameraheight = Global.FRAME_SIZE_Y;
        // camera 盡量保持 貓在中心
        this.focalX = focalX;
        this.focalY = focalY;
        
        
    }

    public int getCameraX() {
        return cameraX;
    }

    public void setCameraX(int cameraX) {
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraY(int cameraY) {
        this.cameraY = cameraY;
    }
    
    

    public int getCamerawidth() {
        return camerawidth;
    }

    public void setCamerawidth(int camerawidth) {
        this.camerawidth = camerawidth;
    }

    public int getCameraheight() {
        return cameraheight;
    }

    public void setCameraheight(int cameraheight) {
        this.cameraheight = cameraheight;
    }
    
    public void bind(GameObject gameObject){
        this.gameObject = gameObject;
    }
    

    public int getDesX() {
        return desX;
    }

    public int getDesY() {
        return desY;
    }
    
    public void update(){
        desX = this.gameObject.getX() + this.gameObject.getWidth()/2 - 625;
        int dx = desX - this.cameraX; // dx = 當前鏡頭的位置和貓的圖片X座標之間的距離
        cameraSpeedX = dx / focalX; // 隨著dx越來越小，鏡頭追的速度越來越慢
        cameraX += cameraSpeedX;
//        cameraX = desX; // 鏡頭不追焦，固定在中線，cameraX 等於貓的圖片X座標
        if(cameraX < 0){
            cameraX = 0;
        }
        if(cameraX >= (backgroundPages - 1) * 1600 * 2 - 1){
            cameraX =  (backgroundPages - 1) * 1600 * 2 - 1;
        }
        desY = this.gameObject.getY() + this.gameObject.getHeight() / 2 - 400;
        int dy = this.cameraY - desY;
        cameraSpeedY = dy / focalY;
        cameraY -= cameraSpeedY;
        if(cameraY < Global.CAMERA_TOP_LIMIT){
            cameraY = Global.CAMERA_TOP_LIMIT;
        }
        if(cameraY > Global.CAMERA_BOTTOM_LIMIT){
            cameraY = Global.CAMERA_BOTTOM_LIMIT;
        }
    }


}
