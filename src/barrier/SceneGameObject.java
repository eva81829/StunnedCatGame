/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrier;

import controller.GameObjectContorller;
import gameobject.GameObject;
import gameobject.MovableGameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import tutorial.Tutorial2;
import utils.Global;

/**
 *
 * @author Eka
 */
public class SceneGameObject { //代表一個場景裡全部的gameObjects
    private GameObjectContorller gameObjectContorller;

    public SceneGameObject() {
        gameObjectContorller = new GameObjectContorller();
    }

    public void update(int currentPage, int x, int y, boolean onCentral) {
        gameObjectContorller.updateNonMovingGameObjects(currentPage);
        for(int i=0 ; i < gameObjectContorller.getMovingGameObjects().size();i++){
            GameObject currentGameObject = gameObjectContorller.getMovingGameObjects().get(i);
            if(currentGameObject instanceof Tutorial2){
                currentGameObject.update(onCentral);
                continue;
            }
            gameObjectContorller.getMovingGameObjects().get(i).update();
        }
    }

    public void paint(Graphics g, int x, int y) {
        for (int i = 0; i < gameObjectContorller.getAllGameObjects().size(); i++) {
            /* 如果遊戲物件的絕對左邊座標小於 當前camera的x 加上 遊戲視窗的寬 加上 一格的寬
             * 並且遊戲物件的絕對右邊座標大於 當前camera的x 減掉 一個的寬
            */
            if(!(gameObjectContorller.getAllGameObjects().get(i) instanceof MovableGameObject || gameObjectContorller.getAllGameObjects().get(i)instanceof MovingBarrier)){
                if (gameObjectContorller.getAllGameObjects().get(i).getLeft() < x + Global.FRAME_SIZE_X + Global.GRID_SIZE_X * 3 && gameObjectContorller.getAllGameObjects().get(i).getRight() > x - Global.FRAME_SIZE_X - Global.GRID_SIZE_X * 3) {
                    if (gameObjectContorller.getAllGameObjects().get(i).getButtom() < y + Global.FRAME_SIZE_Y + Global.GRID_SIZE_Y * 3 && gameObjectContorller.getAllGameObjects().get(i).getTop() > y - Global.FRAME_SIZE_Y - Global.GRID_SIZE_Y * 3) {
                        gameObjectContorller.getAllGameObjects().get(i).paint(g, x, y);
                    }
                }
            }
        }  
        for(int i = 0;i < gameObjectContorller.getMovingGameObjects().size();i++){
            if (gameObjectContorller.getMovingGameObjects().get(i).getLeft() < x + Global.FRAME_SIZE_X + Global.GRID_SIZE_X * 3 && gameObjectContorller.getMovingGameObjects().get(i).getRight() > x - Global.FRAME_SIZE_X - Global.GRID_SIZE_X * 3) {
                if (gameObjectContorller.getMovingGameObjects().get(i).getButtom() < y + Global.FRAME_SIZE_Y + Global.GRID_SIZE_Y * 3 && gameObjectContorller.getMovingGameObjects().get(i).getTop() > y - Global.FRAME_SIZE_Y - Global.GRID_SIZE_Y * 3) {
                    gameObjectContorller.getMovingGameObjects().get(i).paint(g, x, y);
                }
            }
        }
    }

    public void genGameObjects(String line) {
        //System.out.println(line);
        gameObjectContorller.genGameObjects(line);
    }

    public ArrayList<GameObject> getCurrentGameObjects() {
        
        return gameObjectContorller.getAllGameObjects();
    }
}
