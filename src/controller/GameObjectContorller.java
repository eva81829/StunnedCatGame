/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gameobject.TempoObject;
import gameobject.SaveObject;
import barrier.*;
import gameobject.EndGameObject;
import gameobject.GameObject;
import gameobject.MovableGameObject;
import gameobject.PassObject;
import gameobject.TempoStopObject;
import java.util.ArrayList;
import tutorial.Hint;
import tutorial.Tutorial2;
import utils.Global;
import utils.ImagePath;

/**
 *
 * @author Eka
 */
public class GameObjectContorller {

    private int x; //新Barrier插入的座標
    private int y; //新Barrier插入的座標
    private int row; //新Barrier插入的列
    private int perCurrentPage; //上一次的CurrentPage
    private ArrayList<PageGameObject> pageGameObject;
    private ArrayList<GameObject> allGameObjects; //目前兩個背景上的GameObject(不包含null的)
    private ArrayList<GameObject> movingGameObjects; //所有的GameObject(不包含null的)

    public class PageGameObject {

        //private int page; //第幾個page
        private GameObject[][] gameObjects; //一個page上全部的barriers            

        public PageGameObject() {
            gameObjects = new GameObject[Global.BACKGROUND_GRID_NUM_X][Global.BACKGROUND_GRID_NUM_Y]; //一個page允許畫20x20個barrier
        }
    }

    public GameObjectContorller() {
        x = 0;
        y = 0;
        row = 0;
        perCurrentPage = -1;
        pageGameObject = new ArrayList<>();
        allGameObjects = new ArrayList<>();
        movingGameObjects = new ArrayList<>();
    }

    public void genGameObjects(String line) { //一頁一定要畫滿20行, 否則後一頁的障礙物會補到前一頁(一頁之中橫向不可以有空格)
        String[] pageRows;
        if (!line.equals("")) {
            pageRows = line.split(" "); //依照不同的背景圖把每行的資料分割(用空格區分不同頁)
            for (int page = 0; page < pageRows.length; page++) { //再從第一個背景的lines[j]找到最後一個背景
                if (pageGameObject.size() <= page) { //確認pageGameObjects是否都已new出來,總數應等於lines.length(背景圖)的數量
                    pageGameObject.add(new PageGameObject());
                }
                for (int column = 0; column < pageRows[page].length(); column++) { //把每個pageRows[page]中提出第k個字元
                    GameObject gameObject = instanceGameObject(pageRows[page].charAt(column), x, y);
                    pageGameObject.get(page).gameObjects[row][column] = gameObject; //把lines[j]中提出第k個字元轉為gameObject實體並依序丟入GameObject[]
                    if (gameObject != null && (gameObject instanceof MovableGameObject || gameObject instanceof MovingBarrier)){
                        movingGameObjects.add(gameObject);
                    }
                    x += Global.GRID_SIZE_X;
                }
            }
        }
        row++;
        x = 0;
        y += Global.GRID_SIZE_Y;
    }

    public GameObject instanceGameObject(char mark, int x, int y) {
        switch (mark) {
            case 'A':
                return new TransparentBarrier(x, y);
            case 'B':
                return new Ground(x, y);
            case 'C':
                return new BlackBarrier(x, y);
            case 'D':
                return new Rock(x, y);
            case 'E':
                return new GrassGround(x, y);
            case 'F':
                /* x 軸橫移速度, x 軸橫移格數,y 軸橫移速度, y 軸橫移格數 */
                return new FlyRock(x, y, 3, 4, 0, 0);
            case 'G':
                /* x 軸橫移速度, x 軸橫移格數,y 軸橫移速度, y 軸橫移格數 */
                return new Cloud(x, y, 2, 3, 0, 0);
            case 'S':
                return new SaveObject(x, y);
            case 'X':
                return new DeadBarrier(x, y);
            case 'T':
                return new TempoObject(x, y, Global.TEMPO_STYLE1, 60);
            case 'U':
                return new TempoObject(x, y, Global.TEMPO_STYLE2, 60);
            case 'V':
                return new TempoObject(x, y, Global.TEMPO_STYLE3, 60);
            case 'W':
                return new TempoObject(x, y, Global.TEMPO_STYLE4, 60);
            case 'Z':
                return new EndGameObject(x,y);
            case 't':
                return new TempoStopObject(x, y);
            case 'a':
                return new Tutorial2(x, y, Global.MOVE_RIGHT);
            case 'b':
                return new Tutorial2(x, y, Global.MOVE_LEFT);
            case 'c':
                return new Tutorial2(x, y, Global.BACK_JUMP);
            case 'd':
                return new Tutorial2(x, y, Global.SUPER_JUMP);
            case 'e':
                return new Tutorial2(x, y, Global.JUMP);
            case 'f':
                return new Tutorial2(x, y, Global.STOP);
            case 'g':
                return new Hint(x, y, ImagePath.Tutorial.TEACH_BEAT);
            case 'h':
                return new Hint(x, y, ImagePath.Tutorial.TEACH_SAVE_POINT);
            case 'i':
                return new Hint(x, y, ImagePath.Tutorial.TEACH_TEMPO_CHANGE);
            case 'j':
                return new Hint(x, y, ImagePath.Tutorial.TEACH_TEMPO_RECOVER);
            case 'z':
                return new PassObject(x, y, ImagePath.PassObject.CHANGE_SCENE);
            
        }
        return null;
    }

    public void updateNonMovingGameObjects(int currentPage) {
        if (currentPage != perCurrentPage) {
            allGameObjects.clear();
            addCurrentGameObjects(currentPage);
            addNextPageGameObjects(currentPage + 1);
        }
        perCurrentPage = currentPage;
    }

    public void cleanCurrentGameObjects() {
        allGameObjects.clear();
    }

    private void addCurrentGameObjects(int i) {
        //System.out.println(pageBarriers.size());
        if (pageGameObject.size() - 1 >= i && !(pageGameObject.get(i) == null)) {
            //如果pageameObject裡包含現在要畫的page i(pageameObject的page個數-1>=i)，且pageameObject不是空的
            for (int j = 0; j < Global.BACKGROUND_GRID_NUM_X; j++) { //X軸畫幾個
                for (int k = 0; k < Global.BACKGROUND_GRID_NUM_Y; k++) { //Y軸畫幾個
                    GameObject gameObject = pageGameObject.get(i).gameObjects[j][k];
                    if (gameObject != null) {
                        allGameObjects.add(gameObject);
                    }
                }
            }
        }
    }

    private void addNextPageGameObjects(int i) {
        //System.out.println(pageBarriers.size());
        if (pageGameObject.size() - 1 >= i && !(pageGameObject.get(i) == null)) {
            //如果pageGameObject裡包含現在要畫的page i(pageGameObject的page個數-1 >= i)，且pageGameObject不是空的
            //System.out.println("w");
            for (int j = 0; j < Global.BACKGROUND_GRID_NUM_X; j++) {
                for (int k = 0; k < Global.BACKGROUND_GRID_NUM_Y / 2; k++) {
                    GameObject gameObject = pageGameObject.get(i).gameObjects[j][k];
                    if (gameObject != null) {
                        allGameObjects.add(gameObject);
                    }
                }
            }
        }
    }

    public ArrayList<GameObject> getAllGameObjects() {
        return allGameObjects;
    }
    
    public ArrayList<GameObject> getMovingGameObjects() {
        return movingGameObjects;
    }    
}
