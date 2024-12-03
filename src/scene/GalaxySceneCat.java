/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import audio.Audio.PressedMusic;
import camera.Camera;
import controller.MusicController;
import controller.PathBuilder;
import controller.SceneController;
import gameobjectcharacter.SmallCat;
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
public class GalaxySceneCat extends StageScene {

    public GalaxySceneCat(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void sceneBegin() {
        bgm = new MusicController(PathBuilder.getAudio(AudioPath.BackGroundMusic.FOREST2));
        bgm.play();
//        --------------------------------------------------------------------------------------
        character = new SmallCat(50, 50, CharacterParameter.CatParameter.COLLSION_WIDTH, CharacterParameter.CatParameter.COLLSION_HEIGHT);
        pressedMusic = new PressedMusic();
        camera = new Camera(sceneBackground.getBackgroundSize(),50,0,15f,25f);
        camera.bind(character);
        currentKeyPress = new StateUnPress();
        tempoSystem = new TempoSystem(Global.NORMAL_TEMPO);
        title = new Title();
        setKeyCommandListener();
        writeStageFile(stage, PathBuilder.getDoc(DocPath.Scene.DOC_STAGE));
        
    }

    @Override
    public void sceneUpdate() {
        updateCondition();
    }

    @Override
    public void sceneEnd() {
        bgmStop();
    }

    @Override
    public void paint(Graphics g) {
        paintCondition(g);
    }
}
