/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

import controller.AudioResourceController;
import controller.PathBuilder;
import java.applet.AudioClip;
import utils.AudioPath;
import utils.Global;

/**
 *
 * @author asdfg
 */
public class Audio {
    private static final AudioResourceController arc = AudioResourceController.getInstance();
    public static class PressedMusic{
        private final AudioClip pressedA;
        private final AudioClip pressedS;
        private final AudioClip pressedD;
        private final AudioClip pressedF;
        private final AudioClip meow;
        private final AudioClip fall;
        private final AudioClip tempoRecover;
        private final AudioClip tempoChange;
        
        public PressedMusic(){
            pressedA = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_1));
            pressedS = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_2));
            pressedD = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_3));
            pressedF = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_4));
            meow = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.MEOW));
            fall = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.FALL));
            tempoRecover = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.TEMPO_RECOVER));
            tempoChange = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.TEMPO_CHANGE));
        }
        
        public AudioClip getPressedA() {
            return pressedA;
        }

        public AudioClip getPressedS() {
            return pressedS;
        }

        public AudioClip getPressedD() {
            return pressedD;
        }

        public AudioClip getPressedF() {
            return pressedF;
        }
        
        public AudioClip getMeow(){
            return meow;
        }
        
        public AudioClip getFall(){
            return fall;
        }
        
        
        public AudioClip getTempoRecover() {
            return tempoRecover;
        }

        public AudioClip getTempoChange() {
            return tempoChange;
        }
        
        public void play(int commandCode){
            switch(commandCode){
                case Global.A:
                    pressedA.play();
                    break;
                case Global.S:
                    pressedS.play();
                    break;
                case Global.D:
                    pressedD.play();
                    break;
                case Global.F:
                    pressedF.play();
                    break;
            }
        }

        
    }
    public static class BackGroundMusic{
        private final AudioClip count;
        
        public BackGroundMusic(){
            /* 無法讀太大的檔案 */
            count = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.BackGroundMusic.COUNT));
        }
        public void play(int sceneNumber){
            switch(sceneNumber){
                case 1:
                    count.play();
                    break;
            }
        }
        
        public void stop(){
            count.stop();
        }
    }
}
//public class Audio {
//    private static final AudioResourceController arc = AudioResourceController.getInstance();
//    public static class PressedMusic{
//        private final Clip pressedA;
//        private final Clip pressedS;
//        private final Clip pressedD;
//        private final Clip pressedF;
//        private final Clip meow;
//        
//        public PressedMusic(){
//            pressedA = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_1));
//            pressedS = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_2));
//            pressedD = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_3));
//            pressedF = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.PIANO_4));
//            meow = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.PressedMusic.MEOW));
//        }
//        
//        public Clip getPressedA() {
//            
//            return pressedA;
//        }
//
//        public Clip getPressedS() {
//            return pressedS;
//        }
//
//        public Clip getPressedD() {
//            return pressedD;
//        }
//
//        public Clip getPressedF() {
//            return pressedF;
//        }
//        
//        public Clip getMeow(){
//            meow.setFramePosition(0);
//            return meow;
//        }
//        
//        public void play(int commandCode){
//            switch(commandCode){
//                case Global.A:
//                    pressedA.setFramePosition(0);
//                    pressedA.start();
//                    
//                    break;
//                case Global.S:
//                    pressedS.setFramePosition(0);
//                    pressedS.start();
//                    break;
//                case Global.D:
//                    pressedD.setFramePosition(0);
//                    pressedD.start();
//                    break;
//                case Global.F:
//                    pressedF.setFramePosition(0);
//                    pressedF.start();
//                    break;
//            }
//        }
//        
////        public void stop(int commandCode){
////            switch(commandCode){
////                case Global.A:
////                    pressedA.stop();
////                    break;
////                case Global.S:
////                    pressedS.stop();
////                    break;
////                case Global.D:
////                    pressedS.stop();
////                    break;
////                case Global.F:
////                    pressedF.stop();
////                    break;
////            }
////        }
//        
//        
//    }
//    public static class BackGroundMusic{
//        private final Clip piano;
//        private final Clip test;
//        
//        public BackGroundMusic(){
//            /* 無法讀太大的檔案 */
//            piano = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.BackGroundMusic.PIANO));
//            test = arc.tryGetAudio(PathBuilder.getAudio(AudioPath.BackGroundMusic.TEST));
//        }
//        public void play(int sceneNumber){
//            switch(sceneNumber){
//                case 1:
//                    piano.loop(100);
//                    piano.start();
//                    break;
//            }
//        }
//        
//        public void stop(){
//            piano.stop();
//        }
//    }
//}
