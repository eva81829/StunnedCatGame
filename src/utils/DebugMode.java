/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import camera.Camera;
import gameobject.GameObject;
import java.awt.BasicStroke;
import temposystem.SystemBeat;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import gameobjectcharacter.Character;

/**
 *
 * @author asdfg
 */
public class DebugMode {
    public static boolean allowMove = true; //允許移動
    public static String recordReminder = "";
    public static String resultReminder = "";
    
    public static void paintObject(GameObject obj,Graphics g, int x, int y){
        if (Global.SHOW_OBJECT && Global.DEBUGMODE){
            if(x +1280 > obj.getLeft() && x < obj.getX() + obj.getRight()){
                g.setColor(Color.red);
                g.drawRect(obj.getX() - x, obj.getY() - y, obj.getWidth(), obj.getHeight());
                g.setColor(Color.blue);
                g.drawRect(obj.getImageRange().imageX - x, obj.getImageRange().imageY - y, obj.getImageRange().getImageWidth(), obj.getImageRange().getImageHeight());
            }
          }
    }
    
    public static void setAllowMoveTrue(){
        allowMove = true;
    }    
    
    public static void setAllowMoveFalse(){
        if (Global.DEBUGMODE && Global.MOVE_STOP_WHEN_PRESS){
            allowMove = false;
        }
    }    
    
    public static void paintTempo(SystemBeat beat,Graphics g){ //畫可容忍打擊的範圍
        if (Global.DEBUGMODE && Global.SHOW_TEMPO){
            g.setColor(Color.yellow);
            g.drawRect(beat.getRightOnTempleRange(), Global.BEAT_Y, beat.getOnTempleRange(), Global.BEAT_HEIGHT);
            g.drawRect(beat.getBeatEndX(), Global.BEAT_Y, beat.getOnTempleRange(), Global.BEAT_HEIGHT);
            g.drawRect(beat.getBeatStartX(), Global.BEAT_Y, beat.getOnTempleRange(), Global.BEAT_HEIGHT);    
            g.drawRect(Global.FRAME_SIZE_X - beat.getOnTempleRange(), Global.BEAT_Y, Global.FRAME_SIZE_X, Global.BEAT_HEIGHT); 
        }
    }
    
    public static void paintOutput(Graphics g){ //畫打擊結果
        if (Global.DEBUGMODE && Global.SHOW_OUTPUT){
            g.setColor(Color.white);
            g.setFont(new Font("細明體", Font.BOLD, 30));
            g.drawString("按鍵紀錄: " + recordReminder, 10, 30); //按鍵紀錄
            g.drawString("按鍵結果: " + resultReminder, 10, 70); // 按鍵結果
        }
    }
    
    public static void paintGrid(Graphics g) { //畫背景網格
        if (Global.DEBUGMODE && Global.SHOW_GRID) {
            g.setColor(Color.gray);
            for (int x = 0; x < Global.FRAME_SIZE_X; x += Global.GRID_SIZE_X) {
                for (int y = 0; y < Global.FRAME_SIZE_Y; y += Global.GRID_SIZE_Y) {
                    g.drawRect(x, y, Global.GRID_SIZE_X, Global.GRID_SIZE_Y);
                }
            }
        }
    }

    public static void paintCameraXY(Camera camera,Character character,Graphics g){
        if(Global.DEBUGMODE && Global.SHOW_CAMERAXY){
            Graphics2D g2 = (Graphics2D)g; 
            g.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3.0f));
            g.drawLine(character.getX() - camera.getDesX(), 0, character.getX() - camera.getDesX(), 720);
            g.drawLine(0, character.getY(), 1280, character.getY());
            //System.out.println((character.getX() - camera.getDesX())+" "+ (character.getY() - camera.getDesY()));
        }
    }
    
    public static void paintCurrentPage(Graphics g, int scene){
        if(Global.DEBUGMODE && Global.SHOW_OUTPUT){
            g.drawString("頁面: " + scene, 1150, 30); //場景
        }
    }
}
