/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import utils.Global;
import gameobjectcharacter.Character;

/**
 *
 * @author asdfg
 */
public class DeadBarrier extends Barrier{

    public DeadBarrier(int x, int y) {
        super(x, y, Global.GRID_SIZE_X, Global.GRID_SIZE_Y, 0, 0, 0, 0);
    }
    
    public void backRevivePoint(Character character,int saveX,int saveY){
        character.setX(saveX);
        character.setY(saveY);
    }
    
}
