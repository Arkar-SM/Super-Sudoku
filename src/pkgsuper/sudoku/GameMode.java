package pkgsuper.sudoku;
/**
 *
 * @author Arkar
 */

import java.util.Scanner;


/**
 * Class for different game modes that uses an enum to represent difficulty levels.
 */
public class GameMode implements IGameMode {
    
    // Enum to represent difficulty levels
    public enum Difficulty {
        EASY(45, "Easy"), 
        NORMAL(35, "Normal"), 
        HARD(25, "Hard"), 
        TEST(80, "Test");

        private final int clues;
        private final String name;

        Difficulty(int clues, String name) {
            this.clues = clues;
            this.name = name;
        }

        public int getClues() {
            return clues;
        }

        public String getName() {
            return name;
        }
    }

    private Scanner scanner;
    private Difficulty difficulty;

    // Constructor to initialize the game mode with the desired difficulty
    public GameMode(Scanner scanner, Difficulty difficulty) {
        this.scanner = scanner;
        this.difficulty = difficulty;
    }

    // Starts the game based on the selected difficulty
    @Override
    public void startGame() {
        System.out.println("Starting " + difficulty.getName() + " Mode...");
        PlayGame playGame = new PlayGame(scanner, StartMenu.currentPlayerName, new ProfileManager());
        playGame.startGame(this);
    }

    // Returns the number of clues based on the difficulty
    @Override
    public int getClues() {
        return difficulty.getClues();
    }

    // Returns the difficulty name
    @Override
    public String getDifficultyName() {
        return difficulty.getName();
    }
}
