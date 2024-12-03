/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author asdfg
 */
public class Global {
    /* DebugMode常數制定 */
    public static final int GAME_RELEASE_YEAR = 2019;
    public static final int GAME_RELEASE_MONTH = 11;
    public static final int GAME_RELEASE_DAY = 8;
    public static final String GAME_PROGRAMMER_EKA = "Eka";
    public static final String GAME_PROGRAMMER_CYRIL = "Cyril";

    public static final boolean DEBUGMODE = false;
    public static final boolean MOVE_BY_KYBOARD = true; //是否用鍵盤上下左右操作
    public static final boolean MOVE_STOP_WHEN_PRESS = false; //是否要按下就停止移動    
    public static final boolean SHOW_OBJECT = true; //是否要畫物件框
    public static final boolean SHOW_TEMPO = false; //是否要畫節奏框
    public static final boolean SHOW_OUTPUT = true; //是否要畫打擊結果
    public static final boolean SHOW_GRID = false; //是否要畫網格
    public static final boolean SHOW_CAMERAXY = true; //是否要畫鏡頭座標 

    /* 畫面大小與格線制定 */
    public static final int FRAME_SIZE_X = 1280;
    public static final int FRAME_SIZE_Y = 720;
    public static final int CENTRAL_X = (FRAME_SIZE_X) / 2;
    public static final int GRID_NUM_X = 8;
    public static final int GRID_NUM_Y = 8;
    public static final int GRID_SIZE_X = FRAME_SIZE_X / GRID_NUM_X;
    public static final int GRID_SIZE_Y = FRAME_SIZE_Y / GRID_NUM_X;

    /* 場景常數 */
    public static final int BACKGROUND_SIZE_X = 1600;
    public static final int BACKGROUND_SIZE_Y = 900;
    public static final int BACKGROUND_GRID_NUM_X = 20;
    public static final int BACKGROUND_GRID_NUM_Y = 20;

    public static final int FLOOR = 0;

    /* 切換角色 */
    public static final int CAT = 1;
    public static final int BOY = 2;

    /* 畫面更新率 */
    public static final int UPDATE_TIME_PER_SEC = 60;
    public static final int MILLERSEC_PER_UPDATE = 1000 / UPDATE_TIME_PER_SEC;

    /* 畫面最大更新率 */
    public static final int UPDATE_LIMIT = 120;
    public static final int LIMIT_DELTA_TIME = 1000 / UPDATE_LIMIT;

    /* 設定物件基礎速度 */
    public static final int ACTION_SPEED = 240 / UPDATE_TIME_PER_SEC;

    /* 動畫延遲設定 */
    public static final int ANIME_DELAY = UPDATE_TIME_PER_SEC / 20;

    /* 動畫自動轉向頻率 */
    public static final int ANIME_AUTO_DIRECTION = UPDATE_TIME_PER_SEC / 20;

    /* 動畫自動變換角色頻率 */
    public static final int ANIME_AUTO_CHARACTER_CHANGE = UPDATE_TIME_PER_SEC / 20;

    /* 設定圖片大小 */
    public static final int CHARACTER_X_OFFSET = 140;
    public static final int CHARACTER_Y_OFFSET = 140;
    public static final int BEAT_WIDTH = 150;
    public static final int BEAT_HEIGHT = 106;
    public static final int BUTTON_X_OFFSET = 100;
    public static final int BUTTON_Y_OFFSET = 100;
    public static final int TUTORIAL_X_OFFSET = 320;
    public static final int TUTORIAL_Y_OFFSET = 180;
    public static final int LETTER_WIDTH = 40;
    public static final int LETTER_HEIGHT = 50;
    public static final int LETTER_X_OFFSET = LETTER_WIDTH * 2;
    public static final int ACTION_WIDTH = 200;
    public static final int ACTION_HEIGHT = 35;
    public static final int CORRECT_EFFCT_WIDTH = 70;
    public static final int CORRECT_EFFCT_HEIGHT = 70;

    /* 鏡頭常數設定 */
    public static final int CAMERA_BOTTOM_LIMIT = 180;
    public static final int CAMERA_TOP_LIMIT = -540;

    /* 上下左右常數制定 */
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    /* 節奏按健制定 */
    public static final int A = 4;
    public static final int S = 5;
    public static final int D = 6;
    public static final int F = 7;
    
    public static final String MOVE_RIGHT = "FDSD";
    public static final String MOVE_LEFT = "ASDS";
    public static final String SUPER_JUMP = "ASDF";
    public static final String BACK_JUMP = "FDSA";
    public static final String JUMP = "SSSD";
    public static final String STOP = "DSDS";    

    /* 其餘按鍵設定 */
    public static final int ESC = 8;
    public static final int SPACE = 9;
    public static final int N = 10;

    /* 節奏物件畫的位置 */
    public static final int LINE_Y = 680;
    public static final int BEAT_Y = LINE_Y - 60;
    public static final int REC_Y = FRAME_SIZE_Y - GRID_SIZE_Y;

    /* 節奏常數設定 */
    public static final int[] TUTORIAL_TEMPO = {3};
    public static final int[] NORMAL_TEMPO = {5};
    public static final int ON_TEMPO_PARAMETER = 2; // 可容忍打擊的正負誤差範圍係數(總移動距離的多少分之幾)
    public static final int [] TEMPO_STYLE1 = {5};
    public static final int [] TEMPO_STYLE2 = {3};
    public static final int [] TEMPO_STYLE3 = {3,6};
    public static final int [] TEMPO_STYLE4 = {2,3,4,5};
    
    public class Acceleration {
        public static final int G = 1 * ACTION_SPEED * ACTION_SPEED;
    }

}
