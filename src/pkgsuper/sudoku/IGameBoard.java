package pkgsuper.sudoku;


/**
 * Interface defining the essential methods for a Sudoku game board.
 */
public interface IGameBoard {
    void generateCompleteBoard();       // Generate the complete board.
    void generatePlayerBoard(int clues); // Generate a playable board with clues.
    
    // Update the method to accept row, col, and value directly
    boolean makeMove(int row, int col, int number);  // Process player moves.
    
    boolean isComplete();               // Check if the board is complete.
    int[][] getPlayerBoard();           // Expose the current state of the player’s board.
}
