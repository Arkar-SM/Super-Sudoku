package pkgsuper.sudoku;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController implements MainMenuListener, StartMenuListener, ActionListener {
    private MainMenuView mainMenuView;
    private StartMenuView startMenuView;
    private GameBoardView gameBoardView;
    private TutorialView tutorialView;
    private ProfileManager profileManager;
    private GameBoardModel gameBoardModel;

    private String currentPlayerName;
    private GameMode currentGameMode;

    public SudokuController() {
        profileManager = new ProfileManager();
        mainMenuView = new MainMenuView(this);  // Using MainMenuListener
        startMenuView = new StartMenuView(this);  // Using StartMenuListener
        tutorialView = new TutorialView(this);
        startMenuView.showMenu();  // Show the start menu at the beginning
    }

    // Handle actions coming from buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Play Game":
                mainMenuView.closeMenu();
                selectGameMode();
                break;
            case "View Tutorial":
                showTutorial();
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
            case "Back to Menu":
                gameBoardView.closeBoard();
                mainMenuView.showMenu();
                break;
            case "Exit":
                handleExit();
                break;
        }
    }

 
    // Implement methods from MainMenuListener
    @Override
    public void onPlayGame() {
        selectGameMode();
    }

    @Override
    public void onViewTutorial() {
        showTutorial();
    }

    @Override
    public void onHallOfFame() {
        showHallOfFame();
    }

    @Override
    public void onExit() {
    // Use JOptionPane to confirm if the user really wants to exit
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", 
                                                "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0);  // Exit the program if YES is selected
        }
    }


    // Implement methods from StartMenuListener
    @Override
    public void onCreateProfile() {
        createProfile();
    }

    @Override
    public void onEnterPlayerName() {
        enterPlayerName();
    }

    @Override
    public boolean confirmExit() {
        return startMenuView.confirmExit();
    }

   @Override
    public void onStartMenu() {
    mainMenuView.closeMenu();  // Hide the Main Menu
    startMenuView.showMenu();  // Show the existing Start Menu
    }

    private void selectGameMode() {
        String[] options = {"Easy", "Normal", "Hard", "Test Mode"};
        int choice = JOptionPane.showOptionDialog(mainMenuView, "Select a Game Mode:", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice != -1) {
            GameMode.Difficulty difficulty = GameMode.Difficulty.values()[choice];
            startGame(difficulty);
        } else {
            mainMenuView.showMenu();
        }
    }

    private void startGame(GameMode.Difficulty difficulty) {
        currentGameMode = new GameMode(difficulty);
        gameBoardModel = new GameBoardModel(currentGameMode.getClues());

        gameBoardModel.addGameBoardListener(new GameBoardListener() {
            @Override
            public void onMoveMade() {
                gameBoardView.updateBoard(gameBoardModel.getPlayerBoard());
            }

            @Override
            public void onGameComplete() {
                JOptionPane.showMessageDialog(gameBoardView, "Congratulations! You completed the game.");
                gameBoardView.closeBoard();
                mainMenuView.showMenu();
            }

            @Override
            public void boardChanged(int[][] board) {
                gameBoardView.updateBoard(board);
            }
        });

        gameBoardView = new GameBoardView(this, gameBoardModel, mainMenuView);
        gameBoardView.showBoard();
        gameBoardView.startTimer();
    }

    private void createProfile() {
        String playerName = startMenuView.promptForPlayerName("Create New Player");

        if (playerName != null && !playerName.isEmpty()) {
            if (profileManager.createProfile(playerName)) {
                currentPlayerName = playerName;
                JOptionPane.showMessageDialog(startMenuView, "Profile created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                switchToMainMenu();
            } else {
                JOptionPane.showMessageDialog(startMenuView, "Profile already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void enterPlayerName() {
        String playerName = startMenuView.promptForPlayerName("Enter Player Name");

        if (playerName != null && !playerName.isEmpty()) {
            if (profileManager.validateProfile(playerName)) {
                currentPlayerName = playerName;
                JOptionPane.showMessageDialog(startMenuView, "Welcome back, " + playerName + "!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                switchToMainMenu();
            } else {
                JOptionPane.showMessageDialog(startMenuView, "Profile not found. Please create a new profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void switchToMainMenu() {
        startMenuView.closeMenu();
        mainMenuView.showMenu();
    }

    private void handleExit() {
        if (startMenuView.confirmExit()) {
            System.exit(0);
        }
    }

    private void showTutorial() {
        tutorialView.showTutorial();
    }

    private void showHallOfFame() {
        JOptionPane.showMessageDialog(mainMenuView, "Hall of Fame coming soon!", "Hall of Fame", JOptionPane.INFORMATION_MESSAGE);
    }
}
