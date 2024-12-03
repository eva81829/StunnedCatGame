/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import java.awt.*;

/**
 *
 * @author asdfg
 */
public abstract class GameObject {
    public class ImageRange{
        public int imageX;
        public int imageY;
        public int imageWidth;
        public int imageHeight;
        private int topDiff;
        private int leftDiff;
        
        public ImageRange(int leftDiff,int RightDiff,int topDiff,int bottomDiff){
            this.imageX = x - leftDiff;
            this.imageY = y - topDiff;
            this.imageWidth = width + leftDiff + RightDiff;
            this.imageHeight = height + topDiff + bottomDiff;
            this.leftDiff = leftDiff;
            this.topDiff = topDiff;
        }
        
        public int reactRight(){
            return imageX + imageWidth;
        }
        
        public int reactLeft(){
            return imageX;
        }
        
        public int reactTop(){
            return imageY;
        }
        
        public int reactBottom(){
            return imageY + imageHeight;
        }
        
        public int getImageX(){
            return imageX;
        }
        
        public int getImageY(){
            return imageY;
        }
        
        public int getImageWidth(){
            return imageWidth;
        }
        
        public int getImageHeight(){
            return imageHeight;
        }
        
        public void setImageX(int x){
            this.imageX = x - leftDiff;
        }
        
        public void setImageY(int y){
            this.imageY = y - topDiff;
        }
    }
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int direction;
    protected ImageRange imageRange;
    protected String name;
    
    public GameObject(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update(){}
    public void update(boolean onCentral){}
    
    public void paint(Graphics g){}
    public void paint(Graphics g, int x, int y){}
    
    public boolean isCollision(GameObject obj){
        if(getLeft() > obj.getRight()){
            return false;
        }
        if(getRight() < obj.getLeft()){
            return false;
        }
        if(getTop() > obj.getButtom()){
            return false;
        }
        if(getButtom() < obj.getTop()){
            return false;
        }
        return true;
    }
    
    public ImageRange getImageRange() {
        return imageRange;
    }
    
    public int getLeft(){
        return x;
    }
    
    public int getRight(){
        return x + width;
    }
    
    public int getTop(){
        return y;
    }
    
    public int getButtom(){
        return y + height;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public String getName(){
        return name;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setImageRange(int leftDiff, int RightDiff,int topDiff, int bottomDiff) {
        this.imageRange = new ImageRange(leftDiff, RightDiff, topDiff, bottomDiff);
    }
    
    public void setImagePosition() {
        imageRange.setImageX(x);
        imageRange.setImageY(y);
    }
}