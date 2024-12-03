/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popwindow;

import audio.Audio.BackGroundMusic;
import controller.DelayCounter;
import controller.ImageResourceController;
import controller.PathBuilder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import temposystem.TempoSystem;
import utils.Global;
import utils.ImagePath;
import utils.StageTimer;

/**
 *
 * @author asdfg
 */
public class ResultWindow extends PopWindow{
    private BufferedImage img ;
    private BufferedImage space ;
    private BufferedImage arrow ;
    private DelayCounter delaycounter;
    private int correctTimes; // 節拍對的次數
    private int wrongTimes; // 節拍錯的次數
    private int score; // 獲得分數
    private final static String [] RANK = {"S","A","B","C","D"};
    private final static String [] BEAT_ON_TEMPO = {"B","e","a","t ","o","n ","T","e","m","p","o ",": "};
    private final static String [] WRONG_TEMPO = {"W","r","o","n","g ","T","e","m","p","o ",": "};
    private final static String [] FINISHED_TIME = {"F","i","n","i","s","h","e","d ","T","i","m","e  ",": "};
    private  String beatOnTempo;
    private  String wrongTempo;
    private  String finished;
    private  String finishedTime;
    private  String rank;
    private int correctWeight;
    private int wrongWeight = 20;
    private int deadTimes;
    private int countA;
    private int countB;
    private int countC;
    private int time;

    public ResultWindow(StageTimer timer, TempoSystem tempoSystem, int deadTimes) {
        super(0, 0, Global.FRAME_SIZE_X, Global.FRAME_SIZE_Y);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(ImagePath.Scene.BACKGROUND_RESULT));
        space = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.SPACE2));
        arrow = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.ARROW));
        delaycounter = new DelayCounter(1);
        correctTimes = tempoSystem.getCorrectTimes();
        wrongTimes = tempoSystem.getWrongTimes();
        finishedTime = timer.getResultTime();
        time = (int)timer.getResult();
        this.deadTimes = deadTimes;
        setScore();
        beatOnTempo = "";
        wrongTempo = "";
        finished = "";
        rank = "";
        countA = 0;
        countB = 0;
        countC = 0;
    }
    
    @Override
    public void update(){
        if(countA < BEAT_ON_TEMPO.length){
            if(delaycounter.update()){
                beatOnTempo += BEAT_ON_TEMPO[countA++];
            }
        }else if(countA == BEAT_ON_TEMPO.length){
            
            beatOnTempo += correctTimes;
            countA++;
        }else{
            if(countB < WRONG_TEMPO.length){
                if(delaycounter.update()){
                    wrongTempo += WRONG_TEMPO[countB++];
                }
            }else if(countB == WRONG_TEMPO.length){
                wrongTempo += wrongTimes;
                countB++;
            }else{
                if(countC < FINISHED_TIME.length){
                    if(delaycounter.update()){
                        finished += FINISHED_TIME[countC++];
                    }
                }else if(countC == FINISHED_TIME.length){
                    finished += finishedTime;
                    countC++;
                }else{
                    rank = resultRank();
                }
            }
        }
    }
    
    public void setScore(){
        if(deadTimes < 5){
            correctWeight = 8;
        }else if(deadTimes < 10){
            correctWeight = 6;
        }else if(deadTimes < 20){
            correctWeight = 4;
        }else {
            correctWeight = 2;
        }
        this.score =  correctTimes * correctWeight - wrongTimes * wrongWeight + (180 - time);
    }
    
    public String resultRank(){
        if(score > 500){
            return RANK[0];
        }else if(score > 400){
            return RANK[1];
        }else if(score > 300){
            return RANK[2];
        }else if(score > 200){
            return RANK[3];
        }else {
            return RANK[4];
        }
    }

    @Override
    public boolean isEnd() {
        return false;
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(img, x, y, width, height, null);
        g.setFont(new Font("華康唐風隸", Font.PLAIN, 70));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("RESULT", 550, 180);
        
        g.setFont(new Font("華康少女文字W3", Font.PLAIN, 45));
        g.drawString(beatOnTempo, 318, 270);
        g.drawString(wrongTempo, 364, 370);
        g.drawString(finished, 296, 470);
        g.setFont(new Font("華康唐風隸", Font.PLAIN, 200));
        g.drawString(rank, 900, 350);
        g.setFont(new Font("華康少女文字W3", Font.PLAIN, 45));
//        g.drawString("Press", 720, 570);
        g.drawImage(arrow, 970, 535, null);
        g.drawImage(space, 750, 455, null);
    }
    
}
