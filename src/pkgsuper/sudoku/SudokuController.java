package pkgsuper.sudoku;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SudokuController - Central controller to manage game flow, profiles, and transitions.
 */
public class SudokuController implements ActionListener {
    private MainMenuView mainMenuView;
    private StartMenuView startMenuView;
    private GameBoardView gameBoardView;
    private TutorialView tutorialView;  // Add TutorialView for the Tutorial button
    private ProfileManager profileManager;

    private String currentPlayerName;   // Store the current player's name
    private GameMode currentGameMode;   // Stores the selected game mode
    private GameBoard gameBoard;        // Stores the current game board

    public SudokuController() {
        profileManager = new ProfileManager(); // Initialize ProfileManager

        // Initialize Views
        mainMenuView = new MainMenuView(this);
        startMenuView = new StartMenuView(this);
        gameBoardView = new GameBoardView(this);
        tutorialView = new TutorialView(this);  // Initialize the TutorialView

        // Start with the Start Menu
        startMenuView.showMenu();
    }

    // Handle button clicks across all views
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Play Game":
                mainMenuView.closeMenu();
                selectGameMode();  // Transition to game mode selection
                break;

            case "View Tutorial":
                showTutorial();  // Show the tutorial view
                break;

            case "Hall of Fame":
                showHallOfFame();
                break;

            case "Create New Player":
                createProfile();
                break;

            case "Enter Player Name":
                enterPlayerName();
                break;

            case "Reset":
                resetGameBoard();
                break;

            case "Submit":
                submitBoard();
                break;

            case "Back to Menu":
                gameBoardView.closeBoard();
                mainMenuView.showMenu();
                break;

            case "Exit":
                handleExit();
                break;
        }
    }

    // Prompt user to select a game mode and start the game
    private void selectGameMode() {
        String[] options = {"Easy", "Normal", "Hard", "Test"};
        int choice = JOptionPane.showOptionDialog(
                mainMenuView, "Select a Game Mode:", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice != -1) {
            GameMode.Difficulty difficulty = GameMode.Difficulty.values()[choice];
            startGame(difficulty);  // Start game with selected difficulty
        } else {
            mainMenuView.showMenu();  // Return to the main menu if cancelled
        }
    }

    // Start the game with the selected difficulty
    private void startGame(GameMode.Difficulty difficulty) {
        currentGameMode = new GameMode(difficulty);  // scanner is not needed in GUI
        gameBoard = new GameBoard(currentGameMode.getClues());

        gameBoardView.showBoard();  // Show the game board
        gameBoardView.startTimer();  // Start the game timer
    }

    // Create a new player profile
    private void createProfile() {
        String playerName = startMenuView.promptForPlayerName("Create New Player");

        if (playerName != null && !playerName.isEmpty()) {
            if (profileManager.createProfile(playerName)) {
                currentPlayerName = playerName; // Set current player name
                JOptionPane.showMessageDialog(startMenuView,
                        "Profile created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                switchToMainMenu();
            } else {
                JOptionPane.showMessageDialog(startMenuView,
                        "Profile already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Enter an existing player profile
    private void enterPlayerName() {
        String playerName = startMenuView.promptForPlayerName("Enter Player Name");

        if (playerName != null && !playerName.isEmpty()) {
            if (profileManager.validateProfile(playerName)) {
                currentPlayerName = playerName; // Set current player name
                JOptionPane.showMessageDialog(startMenuView,
                        "Welcome back, " + playerName + "!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                switchToMainMenu();
            } else {
                JOptionPane.showMessageDialog(startMenuView,
                        "Profile not found. Please create a new profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Switch to the main menu
    private void switchToMainMenu() {
        startMenuView.closeMenu();
        mainMenuView.showMenu();
    }

    // Handle the exit confirmation
    private void handleExit() {
        if (startMenuView.confirmExit()) {
            System.exit(0);  // Exit the game if confirmed
        }
    }

    // Reset the game board
    private void resetGameBoard() {
        int clues = currentGameMode.getClues();
        gameBoard = new GameBoard(clues);  // Regenerate the board
        JOptionPane.showMessageDialog(gameBoardView,
                "Board has been reset!", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }

    // Submit the board and check if it's complete
    private void submitBoard() {
        int[][] boardValues = gameBoardView.getBoardValues();
        gameBoard = new GameBoard(currentGameMode.getClues());  // Recreate board for comparison

        if (gameBoard.isComplete()) {
            String elapsedTime = gameBoardView.getElapsedTime();  // Get the real elapsed time
            JOptionPane.showMessageDialog(gameBoardView,
                    "Congratulations! You completed the puzzle!", "Success", JOptionPane.INFORMATION_MESSAGE);
            profileManager.updateBestTime(currentPlayerName,
                    currentGameMode.getDifficultyName(), elapsedTime);
            gameBoardView.closeBoard();
            mainMenuView.showMenu();
        } else {
            JOptionPane.showMessageDialog(gameBoardView,
                    "The board is not correct. Keep trying!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Display the tutorial view
    private void showTutorial() {
        tutorialView.showTutorial();  // Open the TutorialView when the "Tutorial" button is clicked
    }

    // Display the Hall of Fame (placeholder)
    private void showHallOfFame() {
        JOptionPane.showMessageDialog(mainMenuView,
                "Hall of Fame coming soon!", "Hall of Fame", JOptionPane.INFORMATION_MESSAGE);
    }
}
