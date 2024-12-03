/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popwindow;

import controller.CommandSolver;
import gameobject.GameObject;
import java.awt.Graphics;

/**
 *
 * @author asdfg
 */
public abstract class PopWindow extends GameObject{
    protected CommandSolver.KeyCommandListener keyCommandListener;
    protected CommandSolver.MouseCommandListener mouseCommandListener;

    public PopWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public void setKeyCommandListener(CommandSolver.KeyCommandListener keyCommandListener){
        this.keyCommandListener = keyCommandListener;
    }
    
    public void setMouseCommandListener(CommandSolver.MouseCommandListener mouseCommandListener){
        this.mouseCommandListener = mouseCommandListener;
    }
    
    public CommandSolver.KeyCommandListener getKeyCommandListener(){
        return keyCommandListener;
    }
    
    public CommandSolver.MouseCommandListener getMouseCommandListener(){
        return mouseCommandListener;
    }
    
    public abstract boolean isEnd();
    
    @Override
    public void update(){
    
    }
    
    @Override
    public void paint(Graphics g){
    
    }
}
