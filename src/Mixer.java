package AudioMixer.src;
import java.util.ArrayList;
import java.util.List;

//Mixer class contains multiple array of audio Player
public class Mixer {
    //List of all running audio players
    private List<AudioPlayer> audioPlayers;
    private boolean isPlaying;
    private Thread playThread;

    //Creates a new mixer which will contain running music
    public Mixer() {
        audioPlayers = new ArrayList<>();
        isPlaying = false;
    }

    public List<String> getMixer() {
        List<String> AudioPath = new ArrayList<>();
        for(AudioPlayer audio : audioPlayers) {
            AudioPath.add(audio.getAudipPath());
        }
        return AudioPath;
    }

    //Add track, the name speaks for itself
    public void addTrack(String filePath) {
        audioPlayers.add(new AudioPlayer(filePath));
    }

    //Maybe useless function, for playing all the sound that contains in the array
    public synchronized void playAll() {
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        playThread = new Thread(() -> {
            while (isPlaying) {
                for (AudioPlayer player : audioPlayers) {
                    if (!player.isPlaying()) {
                        player.play();
                    }
                }
                try {
                    Thread.sleep(100); // Small delay to prevent busy-waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        playThread.start();
    }

    //Same with playAll but it stops all the audio
    public synchronized void pauseAll() {
        isPlaying = false;
        for (AudioPlayer player : audioPlayers) {
            player.pause();
        }
    }

    public synchronized void stopAll() {
        isPlaying = false;
        for (AudioPlayer player : audioPlayers) {
            player.stop();
        }
        if (playThread != null) {
            try {
                playThread.join(); // Wait for the play thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void closeAll() {
        stopAll();
        for (AudioPlayer player : audioPlayers) {
            player.close();
        }
    }

    public List<String> getMixerNames() {
        List<String> fileNames = new ArrayList<>();
        for (AudioPlayer player : audioPlayers) {
            fileNames.add(player.getAudipPath());
        }
        return fileNames;
    }
    public void loopTrack(int index) {
        if (index >= 0 && index < audioPlayers.size()) {
            audioPlayers.get(index).loop();
        } else {
            System.out.println("Invalid track index.");
        }
    }
    public void stopLoopTrack(int index) {
        if (index >= 0 && index < audioPlayers.size()) {
            audioPlayers.get(index).stopLoop();
        } else {
            System.out.println("Invalid track index.");
        }
    }

}
