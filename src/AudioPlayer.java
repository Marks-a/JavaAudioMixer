package AudioMixer.src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private Clip audioClip;
    private String filePath;
    private boolean isLooping;
    private Thread playThread;


    public AudioPlayer(String filePath) {
        this.filePath = filePath;
        loadAudio(filePath);
    }
    public String getAudipPath() {
        return filePath;
    }

    private void loadAudio(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("Something went wrong in AudioPlayer Try/Catch Block");
        }
    }

    public void play() {
        if (audioClip != null) {
            if (audioClip.getFramePosition() == audioClip.getFrameLength()) {
                audioClip.setFramePosition(0); 
            }
            audioClip.start();
        }
    }
    public void loop() {
        if (audioClip != null) {
            isLooping = true;
            playThread = new Thread(() -> {
                while (isLooping) {
                    if (!audioClip.isRunning()) {
                        audioClip.setFramePosition(0);
                        audioClip.start();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            playThread.start();
        }
    }
    public void stopLoop() {
        isLooping = false;
        if (playThread != null) {
            try {
                playThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void pause() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
        }
    }

    public void resume() {
        if (audioClip != null && !audioClip.isRunning()) {
            audioClip.start();
        }

    }
    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.setFramePosition(0);
        }
    }
    public void close() {
        if (audioClip != null) {
            audioClip.close();
        }
    }
    public boolean isPlaying() {
        // TODO Auto-generated method stub
        return audioClip != null && audioClip.isRunning();

    }
}