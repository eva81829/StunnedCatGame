/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import audio.Audio;
import barrier.SceneGameObject;
import controller.CommandSolver.*;
import controller.SceneController;
import background.SceneBackground;
import controller.MusicController;
import java.awt.*;
import javax.sound.sampled.Clip;

/**
 *
 * @author asdfg
 */
    public abstract class Scene{
        protected SceneController sceneController;
        protected SceneGameObject sceneGameObject;
        protected SceneBackground sceneBackground;
        protected Audio.BackGroundMusic backGroundMusic;
        protected static int stage; // 全域變數讓所有場景都改變同一個stage;
        protected static MusicController bgm; // 全域變數讓所有場景都改變同一個bgm，一次不可能有兩首bgm;

        
        public Scene(SceneController sceneController){
            this.sceneController = sceneController;
            
        }
        
        public abstract void sceneBegin();
        public abstract void sceneUpdate();
        public abstract void sceneEnd();
        public abstract void paint(Graphics g);  
        public abstract KeyCommandListener getKey();
        public MouseCommandListener getMouse(){
            return null;
        }
}
