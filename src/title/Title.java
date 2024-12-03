/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package title;

import controller.DelayCounter;
import controller.ImageResourceController;
import controller.PathBuilder;
import gameobject.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utils.DebugMode.recordReminder;
import utils.ImagePath;

/**
 *
 * @author Eka
 */
public class Title extends GameObject{
    private BufferedImage space;
    private BufferedImage esc;
    private boolean[] drawFeverTime;
    private static final boolean[] UNFEVER_TIME = {false};
    private static final boolean[] FEVER_TIME = {true, false};
    private boolean feverTime;
    private boolean startFlash;
    private int count;
    private DelayCounter flashDelayCounter;
    private DelayCounter diaplayDelayCounter;
    
    public Title() {
        /* 位置是在上面一格，但畫在下面的地方(+70) */
        super(10,-5,200,200);
        ImageResourceController irc = ImageResourceController.getInstance();
        space = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.SPACE));
        esc = irc.tryGetImage(PathBuilder.getImage(ImagePath.Tutorial.ESC));
        drawFeverTime = UNFEVER_TIME;
        startFlash = false;
        count = 0;
        flashDelayCounter = new DelayCounter(4);
        diaplayDelayCounter = new DelayCounter(50);
    }
    
    public void setFeverTimeTrue(){
        count = 0;
        startFlash = true;
        drawFeverTime = FEVER_TIME;
    }
    
    public void setFeverTimeFalse(){
        count = 0;
        drawFeverTime = UNFEVER_TIME;
    }    
    
    
    @Override
    public void update() {
        if (startFlash){
            if(diaplayDelayCounter.update()){
                setFeverTimeFalse();
                startFlash = false;
            }
        }
        if(flashDelayCounter.update()){
            feverTime = drawFeverTime[count++ % drawFeverTime.length];
        }
    }
    
    @Override
    public void paint(Graphics g) {
        if (feverTime){
            g.setColor(Color.white);
            g.setFont(new Font("華康少女文字W3", Font.PLAIN, 50));
            g.drawString("TEMPO CHANGE", 500, 65);            
        }
        
        g.setColor(Color.white);
        g.setFont(new Font("華康少女文字W3", Font.PLAIN, 30));
        g.drawImage(esc, x, y, 100, 100, null);
        g.drawString(":back", x + 70, 55);
        g.drawImage(space, x + 170, y, 100, 100,  null);
        g.drawString(":hint", x + 270, 55);
    }    
    
}
