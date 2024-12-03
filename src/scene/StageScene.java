/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import audio.Audio;
import audio.Audio.BackGroundMusic;
import audio.Audio.PressedMusic;
import background.EndingBackground;
import background.SceneBackground;
import barrier.SceneGameObject;
import camera.Camera;
import controller.CommandSolver;
import controller.DelayCounter;
import controller.PathBuilder;
import controller.SceneController;
import gameobject.PassObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import utils.DocPath;
import gameobjectcharacter.Character;
import java.awt.Graphics;
import static java.awt.image.ImageObserver.ABORT;
import popwindow.GameInformationWindow;
import popwindow.ResultWindow;
import temposystem.TempoSystem;
import title.Title;
import utils.DebugMode;
import utils.FrameTimer;
import utils.Global;
import utils.StageTimer;

/**
 *
 * @author asdfg
 */
public abstract class StageScene extends Scene {

    protected Character character;
    protected Camera camera;
    protected StateKeyPress currentKeyPress;
    protected TempoSystem tempoSystem;
    protected PressedMusic pressedMusic;
    private BackGroundMusic countMusic;
    private DelayCounter delayCounterEnd;
//    // -----------------------------------
    protected GameInformationWindow gameInformationWindow;
    protected ResultWindow resultWindow;
    protected CommandSolver.KeyCommandListener keyCommandListener;
    protected StageTimer timer;
    protected Title title;
    protected EndingBackground endingBackground;


    public StageScene(SceneController sceneController) {
        super(sceneController);
        
        sceneBackground = new SceneBackground();
        sceneGameObject = new SceneGameObject();
        readDoc();
        timer = new StageTimer(1145, 58, 500, 500);
        countMusic = new BackGroundMusic();
        delayCounterEnd = new DelayCounter(50);
    }

    public void updateCondition() {
        if (gameInformationWindow == null && resultWindow == null) {
            tempoSystem.update();
            character.update();
            sceneGameObject.update(sceneBackground.getCurrentPage(), camera.getCameraX(), camera.getCameraY(), tempoSystem.checkOnCentral());
            camera.update();
            character.isCollisionBarrier(sceneGameObject.getCurrentGameObjects(), tempoSystem, title);
            title.update();
            popResultWindow();
            checkCharacterBoundary();
            timer.timeUpDate();
        } else if (gameInformationWindow != null) {
            gameInformationWindow.update();
        } else if (resultWindow != null) {
            resultWindow.update();
        }
    }

    public void paintCondition(Graphics g) {
        if (sceneBackground == null) {
            return;
        }
        sceneBackground.paint(g, (int) (camera.getCameraX() * 0.5), (int) (camera.getCameraY() * 0.5));
        debugPaint(g);
        character.paint(g, camera.getCameraX(), camera.getCameraY());
        if (gameInformationWindow == null && resultWindow == null) {
            sceneGameObject.paint(g, camera.getCameraX(), camera.getCameraY());
            if (endingBackground == null) {
                tempoSystem.paint(g);
                timer.paint(g);
                title.paint(g);
            }
        } else if (gameInformationWindow != null) {
            gameInformationWindow.paint(g);
        } else if (resultWindow != null) {
            resultWindow.paint(g);
        }
    }

    protected void popResultWindow() {
        if (stage > 0 && stage < 5) {
            for (int i = 0; i < sceneGameObject.getCurrentGameObjects().size(); i++) {
                if (character.isCollision(sceneGameObject.getCurrentGameObjects().get(i))) {
                    if (sceneGameObject.getCurrentGameObjects().get(i) instanceof PassObject) {
                        Scene scene = getNextScene();
                        resultWindow = new ResultWindow(timer, tempoSystem, character.getDeadTimes());
                        countMusic.play(1);
                        resultWindow.setKeyCommandListener(new CommandSolver.KeyCommandListener() {
                            @Override
                            public void keyPressed(int commandCode, long trigTime) {
                                switch (commandCode) {
                                    case Global.SPACE:
                                        sceneController.changeScene(scene);
                                        break;
                                }
                            }

                            @Override
                            public void keyReleased(int commandCode, long trigTime) {
                            }
                        });
                    }
                }
            }
        }
        if (sceneBackground.getAlpha() == 0f) {
            if (delayCounterEnd.update()) {
                resultWindow = new ResultWindow(timer, tempoSystem, character.getDeadTimes());
                countMusic.play(1);
                resultWindow.setKeyCommandListener(new CommandSolver.KeyCommandListener() {
                    @Override
                    public void keyPressed(int commandCode, long trigTime) {
                        switch (commandCode) {
                            case Global.SPACE:
                                sceneController.changeScene(getNextScene());
                                break;
                        }
                    }

                    @Override
                    public void keyReleased(int commandCode, long trigTime) {
                    }
                });
            }
        }
    }
    
    protected void bgmStop(){
        bgm.stop();
    }

    protected void checkCharacterBoundary() {
        if (character.getX() < 0) {
            character.setX(0);
        } else if (character.getX() > (sceneBackground.getBackgroundSize() - 1) * 1600 * 2 + 1600 - 400) {
            character.setX((sceneBackground.getBackgroundSize() - 1) * 1600 * 2 + 1600 - 400);
        }
    }

    protected void debugPaint(Graphics g) {
        DebugMode.paintGrid(g);
        DebugMode.paintCameraXY(camera, character, g);
        DebugMode.paintCurrentPage(g, sceneBackground.getCurrentPage());
    }

    private String getDocPath() {
//        System.out.println(stage + " getDocpath");
        String path = null;
        if (this instanceof ForestSceneCat) {
            stage = 1;
            path = DocPath.Scene.DOC_FOREST_CAT;
        } else if (this instanceof ForestSceneBoy) {
            stage = 2;
            path = DocPath.Scene.DOC_FOREST_BOY;
        } else if (this instanceof GalaxySceneCat) {
            stage = 3;
            path = DocPath.Scene.DOC_GALAXY_CAT;
        } else if (this instanceof GalaxySceneBoy) {
            stage = 4;
            path = DocPath.Scene.DOC_GALAXY_BOY;
        }
        return PathBuilder.getDoc(path);
    }

    private void readDoc() { //將檔案中的資料轉成背景、障礙物的實體
        String path = getDocPath();
//        System.out.println(stage + " readDoc");
        if (path == null) {
            return;
        }
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bufferIn = new BufferedReader(fr);
            //backGroundMusic.play(bufferIn.readLine()); //讀取音樂
            sceneBackground.genBackgroundsPath(bufferIn.readLine());
            while (bufferIn.ready()) { //從TXT的第一行開始讀取到最後一行
                sceneGameObject.genGameObjects(bufferIn.readLine());
            }
            fr.close();
        } catch (FileNotFoundException e) {
            //System.out.println(path);      
            //System.out.println("例外發生:找不到該檔案");
        } catch (final IOException e) {
            //System.out.println("例外發生:檔案存取錯誤");
        }
    }

    public static void writeStageFile(int stage, String file) {
        try {
//            System.out.println(stage +" writeStage");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(String.valueOf(stage));
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void freezeKeyCommandListener() {
        this.keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
            }
        };
    }

    public void setKeyCommandListener() {
        this.keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                /* 讀按鍵音會有延遲 */
                pressedMusic.play(commandCode);
                if (!Global.DEBUGMODE || (Global.DEBUGMODE && !Global.MOVE_BY_KYBOARD)) {
                switch (commandCode) {
                    case Global.A:
                        DebugMode.setAllowMoveFalse();
                        currentKeyPress.checkEvent(character, tempoSystem, commandCode);
                        currentKeyPress = new StatePress();
                        break;

                    case Global.S:
                        DebugMode.setAllowMoveFalse();
                        currentKeyPress.checkEvent(character, tempoSystem, commandCode);
                        currentKeyPress = new StatePress();
                        break;

                    case Global.D:
                        DebugMode.setAllowMoveFalse();
                        currentKeyPress.checkEvent(character, tempoSystem, commandCode);
                        currentKeyPress = new StatePress();
                        break;
                    case Global.F:
                        DebugMode.setAllowMoveFalse();
                        currentKeyPress.checkEvent(character, tempoSystem, commandCode);
                        currentKeyPress = new StatePress();
                        break;
                    case Global.SPACE:
                        gameInformationWindow = new GameInformationWindow();
                        gameInformationWindow.setKeyCommandListener(new CommandSolver.KeyCommandListener() {
                            @Override
                            public void keyPressed(int commandCode, long trigTime) {
                                switch (commandCode) {
                                    case Global.SPACE:
                                        gameInformationWindow = null;
                                        break;
                                }
                            }

                            @Override
                            public void keyReleased(int commandCode, long trigTime) {
                            }
                        });
                        break;
                    case Global.ESC:
                        sceneController.changeScene(new StartScene(sceneController));
                        break;
                    }
                    return;
                }
                switch (commandCode) {
                    case Global.UP:
                        character.jump();
                        break;
                    case Global.DOWN:
                        character.slow();
                        break;
                    case Global.LEFT:
                        character.turnLeft();
                        break;
                    case Global.RIGHT:
                        character.turnRight();
                        break;
                    case Global.N:
                        sceneController.changeScene(getNextScene());
                        break;
                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                DebugMode.setAllowMoveTrue();
                currentKeyPress = new StateUnPress();
            }
        };
    }

    public CommandSolver.KeyCommandListener getKey() {
        CommandSolver.KeyCommandListener tmp;
        if (gameInformationWindow == null && resultWindow == null) {
            return keyCommandListener;
        } else if (gameInformationWindow != null) {
            return gameInformationWindow.getKeyCommandListener();
        } else if (resultWindow != null) {
            return resultWindow.getKeyCommandListener();
        }
        return null;
    }

    public static abstract class StateKeyPress { //若某功能只有按健放開後重新下按才有反應，則由下設定

        public abstract void checkEvent(Character character, TempoSystem tempoSystem, int command);
    }

    public static class StatePress extends StateKeyPress {

        @Override
        public void checkEvent(Character character, TempoSystem tempoSystem, int command) {
        }
    }

    public static class StateUnPress extends StateKeyPress {

        @Override

        public void checkEvent(Character character, TempoSystem tempoSystem, int command) {
            switch (tempoSystem.checkEvent(command)) {
                case TempoSystem.JUMP_EVENT:
                    character.jump();
                    break;
                case TempoSystem.SUPER_JUMP_EVENT:
                    character.superJump();
                    break;
                case TempoSystem.BACK_JUMP_EVENT:
                    character.backJump();
                    break;
                case TempoSystem.TURN_RIGHT_EVENT:
                    character.turnRight();
                    break;
                case TempoSystem.TURN_LEFT_EVENT:
                    character.turnLeft();
                    break;
                case TempoSystem.SLOW_EVENT:
                    character.slow();
                    break;
                case TempoSystem.HAPPY_EVENT:
                    break;
                case TempoSystem.STUN_EVENT:
                    character.stun();
                    break;
            }
        }
    }
    
    
    public Scene getNextScene(){
        if (this instanceof ForestSceneCat) {
            return new ForestSceneBoy(sceneController);
        } else if (this instanceof ForestSceneBoy) {
            return new GalaxySceneCat(sceneController);
        } else if (this instanceof GalaxySceneCat) {
            return new GalaxySceneBoy(sceneController);
        } else if (this instanceof GalaxySceneBoy) {
            return new StartScene(sceneController);
        }
        return null;
    }
}
