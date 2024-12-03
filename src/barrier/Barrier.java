/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import gameobject.GameObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import gameobjectcharacter.Character;

/**
 *
 * @author asdfg
 */
public class Barrier extends GameObject{
    
    private Graphics2D barrierTest;

    public Barrier(int x, int y, int width, int height,int leftDiff, int RightDiff,int topDiff, int bottomDiff) {
        super(x + leftDiff, y + topDiff- 450, width - leftDiff - RightDiff, height - topDiff - bottomDiff);
        setImageRange(leftDiff, RightDiff, topDiff, bottomDiff);
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
//        DebugMode.paintObject(this, g, x, y-900);
//        setImagePosition();
    }
}
