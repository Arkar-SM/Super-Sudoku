
package pkgsuper.sudoku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
/*
 * Abstract class for different game modes in the Super Sudoku game.
 */

/** 
 * @author Arkar
 */
public abstract class GameMode {
    protected Scanner scanner;  // Scanner for reading player input
    protected GameBoard board;  // GameBoard for the current game

    
    //Constructor to initialize the game mode with a scanner.
    public GameMode(Scanner scanner) {
        this.scanner = scanner;
    }

    // Abstract method that starts the game
     
    public abstract void startGame();

    //returns the elapsed game time 
    
    protected String getTime(Instant startTime) {
        Duration duration = Duration.between(startTime, Instant.now());
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d", minutes, seconds);
    }

   //confirm exit
    protected boolean confirmExit() {
        System.out.println("\nAre you sure you want to exit? All your game progress will be lost! (Y/N)");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y");
    }

     //Saves the player's game time to their profile.
    
    protected void saveTime(String profilePath, String timeRecord) {
        File file = new File(profilePath);
        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(timeRecord);
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Failed to save the time to profile: " + e.getMessage());
            }
        } else {
            System.out.println("Profile file does not exist.");
        }
    }
}