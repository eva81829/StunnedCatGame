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
public class AudioResourceController2 {
    private static class KeyPair{
        private String path;
        private AudioInputStream audio;
        
        public KeyPair(String path, AudioInputStream audio){
            this.path = path;
            this.audio = audio;
        }
    }
    private static AudioResourceController2 arc;
    private ArrayList<KeyPair> audioPairs;
    
    private AudioResourceController2(){
        audioPairs = new ArrayList<>();
    }
    
    public static AudioResourceController2 getInstance(){
        if(arc == null){
            arc = new AudioResourceController2();
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
    
    private AudioInputStream addAudio(String path){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(path));
            audioPairs.add(new KeyPair(path, ais));
            return ais;
        } catch (IOException ex) {
            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public AudioInputStream tryGetAudio(String path){
        KeyPair pair = findKeyPair(path);
        if(pair == null){
            return addAudio(path);
        }
        return pair.audio;
    }
}
