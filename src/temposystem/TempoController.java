/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temposystem;

import utils.Global;

/**
 *
 * @author Eka
 */
public class TempoController {
    private int[] startTempo;
    private int[] tempo;
    private int count;
    
    public TempoController(int[] tempo){
        startTempo = tempo;
        this.tempo = tempo;
        count = 0;
    }
    
    public int getTempo(){
        //System.out.println(tempo[count % tempo.length]);
        return tempo[count++ % tempo.length]; //取得index為count的節奏
    }
    
    public void setTempo(int [] tempo){
        this.tempo = tempo;
        count = 0;
    }
    
    public void resetTempo(){
        this.tempo = startTempo;
        count = 0;
    }    
    
    public int[] getArr(){
        return tempo;
    }
    
    public int getParameter(){
        return Global.ON_TEMPO_PARAMETER; 
    }
    
}