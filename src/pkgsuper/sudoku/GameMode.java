package pkgsuper.sudoku;

/**
 * GameMode class - Manages different game difficulties.
 * Implements IGameMode interface.
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

    private Difficulty difficulty;

    // Constructor to initialize the game mode with a difficulty
    public GameMode(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // Implement the startGame method to fulfill the IGameMode interface contract
    @Override
    public void startGame() {
        System.out.println("Starting " + difficulty.getName() + " mode with " + getClues() + " clues.");
        // You can add more game initialization logic here if needed
    }

    // Return the number of clues for the game mode
    @Override
    public int getClues() {
        return difficulty.getClues();
    }

    // Return the name of the selected difficulty
    @Override
    public String getDifficultyName() {
        return difficulty.getName();
    }
}
