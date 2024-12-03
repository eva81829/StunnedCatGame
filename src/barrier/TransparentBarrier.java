/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import gameobject.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import utils.DebugMode;
import utils.Global;

/**
 *
 * @author asdfg
 */
public class TransparentBarrier extends Barrier{
    private Graphics2D barrierTest;

    public TransparentBarrier(int x, int y) {
        super(x, y, Global.GRID_SIZE_X, Global.GRID_SIZE_Y, 0, 0, 0, 0);
        /* 後面是實際碰撞範圍左右上下 */
    }
    
    @Override
    public void update(){
        
    }

    @Override
    public void paint(Graphics g) {
//        DebugMode.paintObject(this, g);
//        setImagePosition();
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        //g.setColor(Color.black);
        //g.fillRect(getImageRange().imageX - x, getImageRange().imageY - y, getImageRange().getImageWidth(), getImageRange().getImageHeight());
    }
}
