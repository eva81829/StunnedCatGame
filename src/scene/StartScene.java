/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controller.CommandSolver;
import controller.CommandSolver.MouseCommandListener;
import controller.DelayCounter;
import controller.ImageResourceController;
import controller.MusicController;
import controller.PathBuilder;
import controller.SceneController;
import gameobject.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.ABORT;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.StunnedCatGame;
import utils.AudioPath;
import utils.DocPath;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author asdfg
 */
public class StartScene extends Scene{
    private Button [] buttons;
    private BufferedImage img;
    private BufferedImage title;
    private int selection;  
    private DelayCounter delayCounter;
    private int x;
    private int y;
    private int verticalVelocity;
    private int maxVelocity;
    private int acceleration;
    private boolean canAccelerate;
    private Scene beginScene;
    private Scene resumeScene;
    
    public StartScene(SceneController sceneController) {
        super(sceneController);
        img = ImageResourceController.getInstance().tryGetImage(PathBuilder.getImage(ImagePath.Scene.BACKGROUND_START));
        title = ImageResourceController.getInstance().tryGetImage(PathBuilder.getImage(ImagePath.Button.TITLE));
        this.x = 0;
        this.y = 0;
        stage = readStageFile(PathBuilder.getDoc(DocPath.Scene.DOC_STAGE));
        
    }

    @Override
    public void sceneBegin() {
        bgm = new MusicController(PathBuilder.getAudio(AudioPath.BackGroundMusic.PIANO));
        bgm.play();
        delayCounter = new DelayCounter(10);
        canAccelerate = true;
        verticalVelocity = 0;
        maxVelocity = 4;
        acceleration = 1;
        resumeScene = resume(stage);
        buttons = new Button[3];
        buttons[0] = new Button(150,300,150,50,"Start");
        buttons[1] = new Button(135,390,180,50,"Resume");
        buttons[2] = new Button(160,480,130,50,"Exit");
        buttons[0].setButtonListener(new Button.ButtonListener() {
                @Override
                public void onClick(int x, int y) {
                    sceneController.changeScene(new ForestSceneCat(sceneController));
                }
                @Override
                public void hover(int x, int y) {}
            });
        buttons[1].setButtonListener(new Button.ButtonListener() {
                @Override
                public void onClick(int x, int y) {
                    if(stage > 0 && stage < 5){
                        sceneController.changeScene(resumeScene);
                    }
                }
                @Override
                public void hover(int x, int y) {}
            });
        buttons[2].setButtonListener(new Button.ButtonListener() {
                @Override
                public void onClick(int x, int y) {
                    System.exit(1);
                }
                @Override
                public void hover(int x, int y) {}
            });
        
    }

    @Override
    public void sceneUpdate() { 
    }

    @Override
    public void sceneEnd() {
        img = null;
        title = null;
        bgm.stop();
    }

    @Override
    public void paint(Graphics g) {
        if(img == null){
            return;
        }
        g.drawImage(img, x ,y , 1280, 700, null);
        g.setColor(Color.black);        
        g.fillRect(0, 0, Global.FRAME_SIZE_X , Global.GRID_SIZE_Y);
        g.fillRect(0, Global.REC_Y, Global.FRAME_SIZE_X , Global.REC_Y);    
        buttons[0].paint(g);
        buttons[1].paint(g);
        buttons[2].paint(g);
    }
    
    public MouseCommandListener getMouse(){
        return new MouseCommandListener(){
            @Override
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
                if(state != null){
//                    System.out.println(e.getButton() + " " + e.getX() + " " + e.getY() + " " + state);
                }
                for(int i = 0; i < 3; i++){
                    if(state == CommandSolver.MouseState.CLICKED){
                        if(buttons[i].isCollision(e.getX(), e.getY())){
                           buttons[i].click(e.getX(),  e.getY());
                        }
                    }else if (state == CommandSolver.MouseState.MOVED) {
                        if (buttons[i].isCollision(e.getX(), e.getY())) {
                            buttons[i].hover(e.getX(), e.getY());
                        }
                    }
                }
            }
        };
    }    

    @Override
    public CommandSolver.KeyCommandListener getKey() {
        return null;
    }
    
    public Scene resume(int stage){
        switch(stage){
            case 1:
                return new ForestSceneCat(sceneController);
            case 2:
                return new ForestSceneBoy(sceneController);
            case 3:
                return new GalaxySceneCat(sceneController);
            case 4:
                return new GalaxySceneBoy(sceneController);
        }
        return null;
    }
    
    public static int readStageFile(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i;
            i = Integer.valueOf(br.readLine());
            br.close();
            return i;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StunnedCatGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StunnedCatGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
