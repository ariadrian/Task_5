/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Ari Adrian Ibrahim
 */
public class Application {
    private ArrayList<String> musicList;
    private ThreadPlayer thread;
    private int nowPlaying;

    class ThreadPlayer extends Thread {

        String filepath;
        Player player;

        public ThreadPlayer(String filepath) {
            this.filepath = filepath;
        }

        public void run() {
            try {
                FileInputStream inp = new FileInputStream(filepath);
                player = new Player(inp);
                player.play();
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("File " + filepath + " not found");

            } catch (JavaLayerException e) {
                throw new IllegalStateException("ERROR while playing file");
            }
        }
    }
    
    public Application() {
        musicList = new ArrayList<>();
    }
    
    public void addMusic(String path){
        musicList.add(path);
    }
    
    public String[] getMusicList(){
        return (String[]) musicList.toArray(new String[0]);
    }
    
    public String getNowPlayed(){
        return musicList.get(nowPlaying);
    }
    
    public void removeMusic(int i){
        musicList.remove(i);
    }
    
    public void play(int i)
    {
        nowPlaying = i;
        String flpath = musicList.get(i);
        thread = new ThreadPlayer(flpath);
        thread.start();
    }
    
    public void stop(){
        if(thread != null){
            thread.stop();
        }
    }
    
    public void next(){
        if(nowPlaying< musicList.size()-1){
            stop();
            play(nowPlaying+1);
        }
    }
    public void prev(){
        if(nowPlaying< musicList.size()+1){
            stop();
            play(nowPlaying-1);
        }
    }

}
