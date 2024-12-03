/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.CommandSolver.CommandWrapper;
import controller.SceneController;
import java.awt.*;
import javax.swing.*;
import scene.StartScene;

/**
 *
 * @author asdfg
 */
public class GameJPanel extends JPanel{
    private SceneController sceneController;
    
    public GameJPanel(){
        sceneController = new SceneController();
        sceneController.changeScene(new StartScene(sceneController));
    }
    
    public void update(CommandWrapper commands){
        sceneController.sceneUpdate(commands);
    }
    
    @Override
    public void paintComponent(Graphics g){
        sceneController.paint(g);
    }
}
