package main;
import javax.sound.sampled.*;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[40];
    FloatControl floatControl;
    int soundEffect = 6;
    int musicVolume = 10;
    float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/Main.wav");
        soundURL[1] = getClass().getResource("/sounds/sword_slash_1.wav");  // sound effect
        //soundURL[2] = getClass().getResource("/sounds/sword_slash_2.wav");
        soundURL[3] = getClass().getResource("/sounds/hitting.wav"); // sound effect
        soundURL[4] = getClass().getResource("/sounds/being_hit.wav"); // sound effect
        soundURL[5] = getClass().getResource("/sounds/level_up.wav"); // sound effect
        soundURL[6] = getClass().getResource("/sounds/select_menu.wav"); // sound effect
        soundURL[7] = getClass().getResource("/sounds/collect_item.wav"); // sound effect
        soundURL[8] = getClass().getResource("/sounds/dig_1.wav"); // sound effect

        //===========================================================================================//
        soundURL[9] = getClass().getResource("/sounds/menu_open.wav"); // sound effect
        soundURL[10] = getClass().getResource("/sounds/menu_close.wav"); // sound effect
        //===========================================================================================//

        soundURL[11] = getClass().getResource("/sounds/teleport.wav"); // sound effect
        soundURL[12] = getClass().getResource("/sounds/MaxMana.wav");
        soundURL[13] = getClass().getResource("/sounds/dash.wav");

        //========================================== Door ============================================//
        soundURL[14] = getClass().getResource("/sounds/door_opened.wav"); // door open
        soundURL[15] = getClass().getResource("/sounds/door_closed.wav"); // door close
        //===========================================================================================//

        //===========================================================================================//
        soundURL[16] = getClass().getResource("/sounds/run.wav");
        //===========================================================================================//

        //===========================================================================================//
        soundURL[17] = getClass().getResource("/sounds/open_chest.wav");
        //===========================================================================================//

        //===========================================================================================//
        soundURL[18] = getClass().getResource("/sounds/block_with_shield.wav");
        soundURL[19] = getClass().getResource("/sounds/parry_sound.wav");
        //===========================================================================================//

        //===========================================================================================//
        soundURL[20] = getClass().getResource("/sounds/dialogue.wav");
        //===========================================================================================//

        //===========================================================================================//
        soundURL[21] = getClass().getResource("/sounds/Underground_Music.wav");
        soundURL[22] = getClass().getResource("/sounds/Day_Music.wav");
        //===========================================================================================//
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Accept from -80f to 6f
            // -80f is the lowest volume, 6f is the highest volume
            checkVolume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (soundEffect) {
            case 0: volume = -80f; break;
            case 1: volume = -60f; break;
            case 2: volume = -50f; break;
            case 3: volume = -40f; break;
            case 4: volume = -30f; break;
            case 5: volume = -20f; break;
            case 6: volume = -10f; break;
            case 7: volume = 0f; break;
            case 8: volume = 0.5f; break;
            case 9: volume = 1f; break;
            case 10: volume = 6f; break;
        }
        floatControl.setValue(volume);
    }
}
