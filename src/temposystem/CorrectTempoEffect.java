/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temposystem;

import controller.DelayCounter;
import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.MovableGameObject;
import gameobject.PhysicalGameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author Eka
 */
public class CorrectTempoEffect extends MovableGameObject{
    private int pressCorrectCount;
    private Note[] notes;
    
    public class Note extends PhysicalGameObject{
        private BufferedImage img;
        
        public Note(int x, int y, int horizontalVelocity,int accY,BufferedImage img){
            super(x, y, 50, 50);
            this.img = img;
            this.x = x;
            this.y = y;
            this.horizontalVelocity = horizontalVelocity;
            
        }
        
        @Override
        public void update(){
            if( this.y >= Global.REC_Y ){
                setY(Global.REC_Y + 10);
                setX(Global.CENTRAL_X - 37);
                setVerticalVelocity(0);
            }else{
                drop();
            }
//            drop();
            this.x += horizontalVelocity;
        }
    }

    public CorrectTempoEffect(){
        super(Global.CENTRAL_X - 37,  Global.REC_Y + 10, Global.CORRECT_EFFCT_WIDTH, Global.CORRECT_EFFCT_HEIGHT);
        pressCorrectCount = 0;
        ImageResourceController irc = ImageResourceController.getInstance();
        notes = new Note[3];
        notes[0] = new Note(x, y, 0, 0,irc.tryGetImage(PathBuilder.getImage(ImagePath.Tempo.NOTE1))); //中間的音符
        notes[1] = new Note(x, y, -5, 0,irc.tryGetImage(PathBuilder.getImage(ImagePath.Tempo.NOTE2))); //左邊的音符
        notes[2] = new Note(x, y, 5, 0,irc.tryGetImage(PathBuilder.getImage(ImagePath.Tempo.NOTE3))); //右邊的音符
    }
    
    public void triggerCorrect(){
        notes[pressCorrectCount % 3].setY(notes[pressCorrectCount % 3].getY() - 11);
        notes[pressCorrectCount++ % 3].setVerticalVelocity(90);
    }
    
    public void triggerWrong(){
        pressCorrectCount = 0;
        notes[pressCorrectCount % 3].setY(Global.REC_Y + 10);
    }
    
    @Override
    public void update(){
        for(int i=0; i<3; i++){
            notes[i].update();
//            System.out.println("第"+i+"號音符 " + notes[i].getX() +","+notes[i].getY()+","+notes[i].getVerticalVelocity());
        }
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(notes[0].img, notes[0].getX(), notes[0].getY(),width,height, null); //畫中間音符
        g.drawImage(notes[1].img, notes[1].getX(), notes[1].getY(),width,height, null); //左邊的音符
        g.drawImage(notes[2].img, notes[2].getX(), notes[2].getY(),width,height,null); //右邊的音符
    }
}
