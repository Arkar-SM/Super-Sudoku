/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;
/**
 *
 * @author Arkar
 */

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * Handles the game play.
 */
public class PlayGame {
    private Scanner scanner;
    private GameBoard board;
    private Instant startTime;
    private String currentPlayerName;
    private ProfileManager profileManager;

    // Constructor
    public PlayGame(Scanner scanner, String currentPlayerName, ProfileManager profileManager) {
        this.scanner = scanner;
        this.currentPlayerName = currentPlayerName;
        this.profileManager = profileManager;
    }

    // Starts the game and handles mode selection
    public void startGame(GameMode gameMode) {
        board = new GameBoard(gameMode.getClues()); // Generate the board based on mode clues
        startTime = Instant.now(); // Start the timer

        // Start the game loop
        while (true) {
            board.displayBoard(getElapsedTime()); // Display the current board

            System.out.println("\nEnter your move in the format 'a5 3' or 'x' to return to Main Menu:");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("x")) {
                if (Utils.confirmExit(scanner)) {
                    return;  // Exit the game
                } else {
                    continue;  // Continue the game
                }
            }

            if (board.makeMove(input)) {
                if (board.isComplete()) {
                    board.displayBoard(getElapsedTime());
                    System.out.println("Congratulations! You have completed this Sudoku puzzle.");
                    recordGameResult(gameMode.getDifficultyName(), getElapsedTime()); // Record the game result
                    System.out.println("Press any key to return to the Main Menu.");
                    scanner.nextLine();  // Wait for user input before returning to the Main Menu
                    return;  // End the game after a successful completion
                }
            }
        }
    }

    // Calculate and return the elapsed time
    private String getElapsedTime() {
        Duration duration = Duration.between(startTime, Instant.now());
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Record the game result in the player's profile
    private void recordGameResult(String difficulty, String elapsedTime) {
        profileManager.updateBestTime(currentPlayerName, difficulty, elapsedTime); // Delegates to ProfileManager
    }
}
