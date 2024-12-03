/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author asdfg
 */
public class AudioResourceController {
    private static class KeyPair{
        private String path;
        private AudioClip audio;
        
        public KeyPair(String path, AudioClip audio){
            this.path = path;
            this.audio = audio;
        }
    }
    private static AudioResourceController arc;
    private ArrayList<KeyPair> audioPairs;
    
    private AudioResourceController(){
        audioPairs = new ArrayList<>();
    }
    
    public static AudioResourceController getInstance(){
        if(arc == null){
            arc = new AudioResourceController();
        }
        return arc;
    }
    
    private KeyPair findKeyPair(String path){
        for(int i = 0; i < audioPairs.size(); i++){
            KeyPair pair = audioPairs.get(i);
            if(pair.path.equals(path)){
                return pair;
            }
        }
        return null;
    }
    
    private AudioClip addAudio(String path){
        try{
            AudioClip audio = Applet.newAudioClip(getClass().getResource(path));
            audioPairs.add(new KeyPair(path, audio));
            return audio;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public AudioClip tryGetAudio(String path){
        KeyPair pair = findKeyPair(path);
        if(pair == null){
            return addAudio(path);
        }
        return pair.audio;
    }
}
//public class AudioResourceController {
//    private static class KeyPair{
//        private String path;
//        private Clip audio;
//        
//        public KeyPair(String path, Clip audio){
//            this.path = path;
//            this.audio = audio;
//        }
//    }
//    private static AudioResourceController arc;
//    private ArrayList<KeyPair> audioPairs;
//    
//    private AudioResourceController(){
//        audioPairs = new ArrayList<>();
//    }
//    
//    public static AudioResourceController getInstance(){
//        if(arc == null){
//            arc = new AudioResourceController();
//        }
//        return arc;
//    }
//    
//    private KeyPair findKeyPair(String path){
//        for(int i = 0; i < audioPairs.size(); i++){
//            KeyPair pair = audioPairs.get(i);
//            if(pair.path.equals(path)){
//                return pair;
//            }
//        }
//        return null;
//    }
//    
//    private Clip addAudio(String path){
//        try{
//            Clip audio = AudioSystem.getClip();
//            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(path));
//            audio.open(ais);
//            audioPairs.add(new KeyPair(path, audio));
//            return audio;
//        } catch (LineUnavailableException ex) {
//            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedAudioFileException ex) {
//            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    
//    public Clip tryGetAudio(String path){
//        KeyPair pair = findKeyPair(path);
//        if(pair == null){
//            return addAudio(path);
//        }
//        return pair.audio;
//    }
//}
