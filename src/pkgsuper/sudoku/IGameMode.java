package pkgsuper.sudoku;
/**
 * Author -Arkar
 */

/**
 * Interface defining the structure for game modes.
 */
public interface IGameMode {
    void startGame();    // Start the game based on the selected difficulty.
    int getClues();      // Return the number of clues for the selected mode.
    String getDifficultyName();  // Return the name of the selected difficulty.
}
