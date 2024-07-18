package AudioMixer.src;
//Curently in development
import java.util.List;
//Needs a scanner for detecting key pressing
import java.util.Scanner;

// Main class for looping trought the mixer
public class Main {
    private static Mixer mixer = new Mixer();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//Main loop of the file 
        while (true) {
            clearConsole();
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTrack();
                    break;
                case 2:
                    mixer.playAll();
                    break;
                case 3:
                    mixer.pauseAll();
                    break;
                case 4:
                    mixer.stopAll();
                    break;
                case 5:
                    listTracks();
                    break;
                case 6:
                    loopTrack();
                    break;
                case 7:
                    stopLoopTrack();
                    break;
                case 8:
                  mixer.closeAll();
                  System.out.println("Exiting...");
                  scanner.close();
                  return;

                default:
                    System.out.println("Invalid choice.");

            }
        }

    }

    private static void stopLoopTrack() {
        // TODO Auto-generated method stub
        listTracks();
        System.out.print("Enter the track number to stop looping: ");
        int trackIndex = scanner.nextInt();
        scanner.nextLine();  // consume the newline
        mixer.stopLoopTrack(trackIndex);
    }

    private static void listTracks() {
        List<String> trackNames = mixer.getMixerNames();
        if (trackNames.isEmpty()) {
            System.out.println("No tracks added.");
        } else {
            System.out.println("Current tracks:");
            for (String name : trackNames) {
                System.out.println(name);
            }
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printMenu() {
        System.out.println("Audio Mixer:");
        System.out.println("1. Add track");
        System.out.println("2. Play all tracks");
        System.out.println("3. Pause all tracks");
        System.out.println("4. Stop all tracks");
        System.out.println("5. List all tracks");
        System.out.println("6. Loop a track");
        System.out.println("7. Stop looping a track");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addTrack() {
        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();
        mixer.addTrack(filePath);
    }
    private static void loopTrack() {
        listTracks();
        System.out.print("Enter the track number to loop: ");
        int trackIndex = scanner.nextInt();
        scanner.nextLine();  // consume the newline
        mixer.loopTrack(trackIndex);
    }


}
