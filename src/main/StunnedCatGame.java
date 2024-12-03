/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.CommandSolver;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import utils.Global;

/**
 *
 * @author asdfg
 */
public class StunnedCatGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        final JFrame f = new JFrame();
        f.setSize(Global.FRAME_SIZE_X + 15, Global.FRAME_SIZE_Y + 45);
        f.setTitle("Stunned Cat");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GameJPanel jp = new GameJPanel();
        f.add(jp);
        
        CommandSolver cs = new CommandSolver.Builder(Global.MILLERSEC_PER_UPDATE, new int[][]{
            {KeyEvent.VK_DOWN, Global.DOWN},
            {KeyEvent.VK_UP, Global.UP},
            {KeyEvent.VK_LEFT, Global.LEFT},
            {KeyEvent.VK_RIGHT, Global.RIGHT},
            {KeyEvent.VK_A, Global.A},
            {KeyEvent.VK_S, Global.S},
            {KeyEvent.VK_D, Global.D},
            {KeyEvent.VK_F, Global.F},
            {KeyEvent.VK_ESCAPE, Global.ESC},
            {KeyEvent.VK_SPACE, Global.SPACE},
            {KeyEvent.VK_N, Global.N}
        }).enableMouseTrack(jp).keyTypedMode().gen();
        
        f.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                cs.trig(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                cs.trig(e.getKeyCode(), false);
            }
        });
        f.setFocusable(true);
        cs.start();
        f.setVisible(true);
        
        long startTime = System.currentTimeMillis();
        long lastRepaintTime = System.currentTimeMillis();
        long passedFrames = 0;
        
        while(true){
            long currentTime = System.currentTimeMillis();
            long totalTime = currentTime - startTime;
            long targetTotalFrame = totalTime / Global.MILLERSEC_PER_UPDATE;
            while(passedFrames < targetTotalFrame){
                jp.update(cs.update());
                passedFrames++;
            }
            if(Global.LIMIT_DELTA_TIME < currentTime - lastRepaintTime){
                lastRepaintTime = currentTime;
                f.repaint();
            }
        }
    }
    
}
