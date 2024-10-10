package pkgsuper.sudoku;
/**
 *
 * @author Arkar
 */

/**
 * Interface defining the essential methods for a Sudoku game board.
 */
public interface IGameBoard {
    void generateCompleteBoard();  // Method to generate the complete board
    boolean makeMove(String move); // Method to process player moves
    boolean isComplete();          // Method to check if the board is complete
    void displayBoard(String elapsedTime); // Method to display the board with time
}
