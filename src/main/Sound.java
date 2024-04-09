package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/Main.wav");
        soundURL[1] = getClass().getResource("/sounds/sword_slash_1.wav");
        //soundURL[2] = getClass().getResource("/sounds/sword_slash_2.wav");
        soundURL[3] = getClass().getResource("/sounds/hitting.wav");
        soundURL[4] = getClass().getResource("/sounds/being_hit.wav");
        soundURL[5] = getClass().getResource("/sounds/level_up.wav");
    }

    public void setFile(int i)
    {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play()
    {
        clip.start();
    }
    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop()
    {
        clip.stop();
    }

}
