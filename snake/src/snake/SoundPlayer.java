package snake;
import javax.sound.sampled.*;

public class SoundPlayer {
    private Clip clip;

    public void play(String resourcePath, boolean loop) {
        try {
            // Load sound file from classpath (inside src/snake/)
            var url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("Sound file not found: " + resourcePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    
    }
}
 
