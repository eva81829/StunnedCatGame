/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temposystem;

import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.MovableGameObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import utils.DebugMode;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author Eka
 */
public class SystemBeat extends MovableGameObject{
    private TempoController tempoController;    
    private BufferedImage img1;
    private BufferedImage img2;
    private int linedy;
    private int shakeCount;
    private int lineX;
    private int lineY;
    private int beatStartX; // 節拍開始的X位置
    private int beatStartY; // 節拍開始的Y位置
    private int beatEndX; // 節拍結束(被正確打擊)的Y位置
    private int beatEndY; // 節拍結束(被正確打擊)的Y位置
    private int onTempleRange; // 可容忍打擊的正負誤差範圍
    private int rightOnTempleRange; //最右邊可容忍打擊的範圍;
    private int leftOnTempleRange; //最左邊可容忍打擊的範圍;
    private int moveCycle; //週期
    private boolean onCentral;
    private boolean onCentralUpdate;
    private int shiftBeatTime; //提前多久重和時間
    
    public SystemBeat(int beatStartX, int beatStartY, int beatEndX,  int beatEndY, int[] tempo) {
        super(beatStartX, beatStartY, Global.BEAT_WIDTH, Global.BEAT_HEIGHT); // 設定節拍移動中的X位置, 節拍移動中的Y位置, 節拍圖片尺寸
        this.beatStartX = beatStartX;
        this.beatStartY = beatStartY;
        this.beatEndX = beatEndX;
        this.beatEndY = beatEndY;        
        ImageResourceController irc = ImageResourceController.getInstance();
        img1 = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tempo.TEMPO1));
        img2 = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tempo.TEMPO3));
        lineY = Global.LINE_Y;
        linedy = shakeCount = lineX = 0;
        tempoController = new TempoController(tempo);
        setSpeed(tempoController.getTempo());
        setImageRange(0,0,0,0);
        onTempleRange = (beatEndX - beatStartX) / tempoController.getParameter();
        rightOnTempleRange = beatEndX - onTempleRange;
        leftOnTempleRange = beatStartX + onTempleRange;
        moveCycle = beatEndX - beatStartX;
        onCentral = false;
        onCentralUpdate = false;
        shiftBeatTime = 75;
    }

    @Override
    public void update(){
        move();
    }
    
    public void move(){
        if(x > beatEndX - shiftBeatTime && !onCentralUpdate){ //重和的x提早
            onCentral = true;
            onCentralUpdate = true;
        } else{
            onCentral = false;
        }
        //System.out.println(onCentral);
        
        if(x > beatEndX){
            setSpeed(tempoController.getTempo());
            x = beatStartX;
            onCentralUpdate = false;
            return;
        }
        x += getSpeed();
        updateLine();
    }
    
    public boolean checkOnCentral(){ //確認節奏是否位於正中心, 每次位於正中心就改變一次true/false
        return onCentral;
    }
    
    public boolean checkOnTempo(){ //確認按健每次是否都有按在節奏範圍內
        if (x < rightOnTempleRange && x > leftOnTempleRange){
            return false;
        }
        return true;
    }
    
    
    private void updateLine(){
        if (lineY > Global.LINE_Y || lineY < Global.LINE_Y){
            lineY = Global.LINE_Y;
            return;
        } 
        linedy = (int)(Math.random() * 3);
        
        if (shakeCount == 0){
            lineY += linedy;
            shakeCount = 1;
            return;
        }
        lineY -= linedy;
        shakeCount = 0;
        return;        
    }
        
    @Override
    public void paint(Graphics g) {
        DebugMode.paintTempo(this, g);
        setPenWidthAndColor(g);
        g.drawImage(img1, x - Global.BEAT_WIDTH/2, y, null); //畫左邊的震動圖
        g.drawImage(img2, Global.FRAME_SIZE_X - Global.BEAT_WIDTH - (x - Global.BEAT_WIDTH/2), y, null);  //畫右邊的震動圖
        g.drawLine(lineX, lineY, x, lineY); //畫左邊的線
        g.drawLine(Global.FRAME_SIZE_X - lineX, lineY, Global.FRAME_SIZE_X - x, lineY); //畫右邊的線      
    }
    
    public void setPenWidthAndColor(Graphics g) {
        int width = 5;        
        ((Graphics2D)g).setStroke(new BasicStroke(width));
        Color color= new Color(1.0F, 1.0F, 1.0F, 0.5F);
        g.setColor(color);
    }
    
    public void setTempo(int [] tempo){
        tempoController.setTempo(tempo);
    }
    
    public void resetTempo(){
        tempoController.resetTempo();
    }

    public int getMoveCycle(){
        return moveCycle; 
    }    
    
    public int getOnTempleRange(){
        return onTempleRange; 
    }
    
    public int getBeatEndX(){
        return beatEndX; 
    }
    
    public int getBeatStartX(){
        return beatStartX; 
    }    
    
    public int getRightOnTempleRange(){
        return rightOnTempleRange; 
    }
    
    public int getLeftOnTempleRange(){
        return leftOnTempleRange; 
    }    
    
    public TempoController getTempoController(){
        return tempoController;
    }
        
}
