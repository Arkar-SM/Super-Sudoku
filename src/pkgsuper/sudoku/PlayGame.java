package pkgsuper.sudoku;

import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;
import java.io.*;

/**
 * Handles the game play
 */

/** 
 * @author Arkar
 */
public class PlayGame {
    private Scanner scanner;
    private GameBoard board;
    private Instant startTime;
    private String currentPlayerName;

    public PlayGame(Scanner scanner, String currentPlayerName) {
        this.scanner = scanner;
        this.currentPlayerName = currentPlayerName;
    }

    // Starts the game and handles mode selection.
    public void startGame() {
        GameBoard.Difficulty selectedMode = null;

        while (true) {
            // Display mode selection menu
            System.out.println("\nPlease choose your mode:");
            System.out.println("(1) Easy");
            System.out.println("(2) Normal");
            System.out.println("(3) Hard");
            System.out.println("(4) Test");
            System.out.println("Enter [x] to return to Main Menu");

            String modeInput = scanner.nextLine().trim().toLowerCase();

            switch (modeInput) {
                case "1":
                    System.out.println("Easy mode");
                    board = new GameBoard(GameBoard.Difficulty.EASY);
                    selectedMode = GameBoard.Difficulty.EASY;
                    break;
                case "2":
                    System.out.println("Normal mode");
                    board = new GameBoard(GameBoard.Difficulty.NORMAL);
                    selectedMode = GameBoard.Difficulty.NORMAL;
                    break;
                case "3":
                    System.out.println("Hard mode");
                    board = new GameBoard(GameBoard.Difficulty.HARD);
                    selectedMode = GameBoard.Difficulty.HARD;
                    break;
                case "4":
                    System.out.println("Test mode");                    // Test mode with 80 clues(this is for testing purpose)
                                                                        //We dont have to play the whole game again and again in CUI to test the game
                                                                        // I am leaving this test mode in submission , intentionally for the "Tester" 
                                                                        //so he/she dont have to play the whole game :) 
                    board = new GameBoard(GameBoard.Difficulty.TEST);
                    selectedMode = GameBoard.Difficulty.TEST;
                    break;
                case "x":
                    System.out.println("Returning to Main Menu...");
                    return;  // Return to Main Menu
                default:
                    System.out.println("Invalid input. Please select a valid option.");
                    continue;  // Redisplay the mode selection 
            }

            startTime = Instant.now(); // Start the timer when a mode is selected

            // Start the game loop after mode selection
            while (true) {
                board.displayBoard(getElapsedTime()); // Display the current state of the board with the timer

                System.out.println("\nEnter your move in the format 'a5 3' or 'x' to return to Main Menu:");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("x")) {
                    if (confirmExit()) {
                        return;  // Return to Main Menu
                    } else {
                        continue;  // Continue the game
                    }
                }

                if (board.makeMove(input)) {
                    if (board.isComplete()) {
                        board.displayBoard(getElapsedTime());
                        System.out.println("Congratulations! You have completed this Sudoku puzzle.");
                        recordGameResult(selectedMode, getElapsedTime()); // Record the game result
                        System.out.println("Press any key to return to the Main Menu.");
                        scanner.nextLine();  // Wait for user input before returning to the Main Menu
                        return;  // End the game after a successful completion
                    }
                }
            }
        }
    }

    //Calculates and returns the elapsed time 
     
    private String getElapsedTime() {
        Duration duration = Duration.between(startTime, Instant.now());
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d", minutes, seconds);
    }

    //Confirm Exit
    private boolean confirmExit() {
        System.out.println("\nAre you sure you want to exit? All your game progress will be lost! (Y/N)");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y");
    }
    
    //this method is generated by ChatGPT
    // Records the game result to the player's profile.
    private void recordGameResult(GameBoard.Difficulty difficulty, String elapsedTime) {
    String profilePath = "./resources/" + currentPlayerName + "_profile.txt";
    File file = new File(profilePath);

    if (file.exists()) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder updatedProfile = new StringBuilder();
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    updatedProfile.append(line).append("\n");
                    firstLine = false;
                } else if (line.startsWith(currentPlayerName)) {
                    String[] parts = line.split("\\s+");

                    // Update the correct game mode stats based on the difficulty
                    int easyMatches = Integer.parseInt(parts[1]);
                    String easyBestTime = parts[2];
                    int normalMatches = Integer.parseInt(parts[3]);
                    String normalBestTime = parts[4];
                    int hardMatches = Integer.parseInt(parts[5]);
                    String hardBestTime = parts[6];
                    int testMatches = Integer.parseInt(parts[7]);
                    String testBestTime = parts[8];

                    switch (difficulty) {
                        case EASY:
                            easyMatches++;
                            if (easyBestTime.equals("--") || isBetterTime(elapsedTime, easyBestTime)) {
                                easyBestTime = elapsedTime;
                            }
                            break;
                        case NORMAL:
                            normalMatches++;
                            if (normalBestTime.equals("--") || isBetterTime(elapsedTime, normalBestTime)) {
                                normalBestTime = elapsedTime;
                            }
                            break;
                        case HARD:
                            hardMatches++;
                            if (hardBestTime.equals("--") || isBetterTime(elapsedTime, hardBestTime)) {
                                hardBestTime = elapsedTime;
                            }
                            break;
                        case TEST:
                            testMatches++;
                            if (testBestTime.equals("--") || isBetterTime(elapsedTime, testBestTime)) {
                                testBestTime = elapsedTime;
                            }
                            break;
                    }

                    // Update the line with the new values
                    updatedProfile.append(String.format(
                        "%-20s %-10d %-20s %-10d %-20s %-10d %-20s %-10d %-20s\n",
                        currentPlayerName,
                        easyMatches, easyBestTime,
                        normalMatches, normalBestTime,
                        hardMatches, hardBestTime,
                        testMatches, testBestTime
                    ));
                } else {
                    updatedProfile.append(line).append("\n");
                }
            }
            reader.close();

            // Write the updated profile back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(updatedProfile.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("###Failed to update profile: ###" + e.getMessage());
        }
    }
    }
    
    //this method is generated by ChatGPT
    //Compares the new game time with the current best time. only record the best time
    private boolean isBetterTime(String newTime, String oldTime) {
        String[] newParts = newTime.split(":");
        String[] oldParts = oldTime.split(":");
        int newMinutes = Integer.parseInt(newParts[0]);
        int newSeconds = Integer.parseInt(newParts[1]);
        int oldMinutes = Integer.parseInt(oldParts[0]);
        int oldSeconds = Integer.parseInt(oldParts[1]);

        return (newMinutes < oldMinutes) || (newMinutes == oldMinutes && newSeconds < oldSeconds);
    }

    
     // Retrieves the column index based on the difficulty 
     
    private int getColumnIndex(String difficultyLabel) {
        switch (difficultyLabel) {
            case "Easy":
                return 1;
            case "Easy_Best_Time":
                return 2;
            case "Normal":
                return 3;
            case "Normal_Best_Time":
                return 4;
            case "Hard":
                return 5;
            case "Hard_Best_Time":
                return 6;
            case "Test":
                return 7;
            case "Test_Best_Time":
                return 8;
            default:
                return -1;
        }
    }
}