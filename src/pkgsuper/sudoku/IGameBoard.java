package pkgsuper.sudoku;

/**
 * Interface defining the essential methods for a Sudoku game board.
 */
public interface IGameBoard {
    void generateCompleteBoard();       // Generate the complete board.
    void generatePlayerBoard(int clues); // Generate a playable board with clues.
    boolean makeMove(String move);      // Process player moves.
    boolean isComplete();               // Check if the board is complete.
    void displayBoard(String elapsedTime); // Display the board with elapsed time.
    int[][] getPlayerBoard();           // Expose the current state of the player’s board.
}
