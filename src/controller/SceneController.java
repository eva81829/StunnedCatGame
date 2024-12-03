/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import scene.Scene;
import java.awt.Graphics;

/**
 *
 * @author asdfg
 */
public class SceneController {
    
    private Scene currentScene;
    
    public void changeScene(Scene scene){
        if(currentScene != null){
            currentScene.sceneEnd();
        }
        currentScene = scene;
        currentScene.sceneBegin();
    }
    
    public void sceneUpdate(CommandSolver.CommandWrapper commands){
        if(commands != null){
            commands.actionCommand(currentScene.getKey());
            commands.actionCommand(currentScene.getMouse());
        }
        currentScene.sceneUpdate();
    }
    
    public void paint(Graphics g) {
        currentScene.paint(g);
    }
}
