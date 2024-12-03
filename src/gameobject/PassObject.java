/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import controller.ImageResourceController;
import controller.PathBuilder;
import controller.SceneController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import scene.Scene;
import utils.Global;
import gameobjectcharacter.Character;
import utils.DebugMode;
import utils.ImagePath;


/**
 *
 * @author asdfg
 */
public class PassObject extends GameObject{
    private BufferedImage img;

    public PassObject(int x, int y, String imagePath) {
        super(x + 40, y - 450, Global.GRID_SIZE_X - 80, Global.GRID_SIZE_Y * 2);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImage(imagePath));
        setImageRange(40, 40, 0, 0);
    }
    
    public PassObject(int x, int y) {
        super(x, y - 450, Global.GRID_SIZE_X, Global.GRID_SIZE_Y);
    }
    
//    public void update(SceneController sceneController, Scene scene, Character character){
//        if(this.isCollision(character)){
//            sceneController.changeScene(scene);
//        }
//    }

    @Override
    public void paint(Graphics g, int x, int y){
        if(img == null){
            return;
        }
        setImagePosition();
        DebugMode.paintObject(this, g, x, y);
        g.drawImage(img, imageRange.imageX - x, imageRange.imageY - y, null);
    }
    
}
