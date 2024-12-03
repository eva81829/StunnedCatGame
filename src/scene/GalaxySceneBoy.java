/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import audio.Audio.PressedMusic;
import background.EndingBackground;
import camera.Camera;
import controller.MusicController;
import controller.PathBuilder;
import controller.SceneController;
import gameobjectcharacter.Boy;
import java.awt.Graphics;
import temposystem.TempoSystem;
import title.Title;
import utils.AudioPath;
import utils.Global;
import utils.CharacterParameter;
import utils.DocPath;

/**
 *
 * @author asdfg
 */
public class GalaxySceneBoy extends StageScene{
    
    public GalaxySceneBoy(SceneController sceneController) {
        super(sceneController);
    }
    @Override
    public void sceneBegin() {
        bgm = new MusicController(PathBuilder.getAudio(AudioPath.BackGroundMusic.GALAXY1));
        bgm.play();
//        --------------------------------------------------------------------------------------
        camera = new Camera(sceneBackground.getBackgroundSize(),14000, 200,15f,25f); //14000 200 20200
        character = new Boy(14000, 200, CharacterParameter.BoyParameter.COLLSION_WIDTH, CharacterParameter.BoyParameter.COLLSION_HEIGHT);
        camera.bind(character);
        pressedMusic = new PressedMusic();
        currentKeyPress = new StateUnPress();
        tempoSystem = new TempoSystem(Global.NORMAL_TEMPO);
        title = new Title();
        setKeyCommandListener();
        writeStageFile(stage, PathBuilder.getDoc(DocPath.Scene.DOC_STAGE));
    }

    @Override
    public void sceneUpdate() {
        updateCondition();
        if(character.getX() < Global.GAME_RELEASE_YEAR){
            freezeKeyCommandListener();
            endingBackground = new EndingBackground();
        }
        
        if(character.getX() < 970){
            character.setX(970);
            character.stop();
            sceneBackground.fadeOut();
        }
    }

    @Override
    public void sceneEnd() {
        bgmStop();
    }

    @Override
    public void paint(Graphics g) {
        if(endingBackground != null){
            endingBackground.paint(g, camera.getCameraX(),camera.getCameraY());
        }
        paintCondition(g);
    }
}

