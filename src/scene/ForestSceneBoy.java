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
public class ForestSceneBoy extends StageScene{

    public ForestSceneBoy(SceneController sceneController) {
        super(sceneController);
    }
    @Override
    public void sceneBegin() {
        bgm = new MusicController(PathBuilder.getAudio(AudioPath.BackGroundMusic.GALAXY1));
        bgm.play();
        character = new Boy(17050, -1000, CharacterParameter.BoyParameter.COLLSION_WIDTH, CharacterParameter.BoyParameter.COLLSION_HEIGHT); //初始邊界不能超出換場景的邊界, 否則會直接換到下個場景
        pressedMusic = new PressedMusic();
        camera = new Camera(sceneBackground.getBackgroundSize(),17050 ,-1000 ,15f ,25f);
        camera.bind(character);
        currentKeyPress = new StateUnPress();
        tempoSystem = new TempoSystem(Global.TUTORIAL_TEMPO);
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
