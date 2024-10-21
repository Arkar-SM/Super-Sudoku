package pkgsuper.sudoku;

/**
 *
 * @author Arkar
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * GameBoardModel class - Manages the Sudoku board and player moves.
 * Implements IGameBoard interface.
 */

public class GameBoardModel implements IGameBoard {
    private int[][] completedBoard = new int[9][9];  // Completed board
    private int[][] playerBoard = new int[9][9];     // Player's board
    private boolean[][] isClue = new boolean[9][9];  // Track clue positions
    private List<GameBoardListener> listeners = new ArrayList<>();  // List of listeners

    // Constructor to generate the boards with the given number of clues
    public GameBoardModel(int clues) {
        generateCompleteBoard();
        generatePlayerBoard(clues);  // Call to generate the player's board
    }

    // Method to add a GameBoardListener
    public void addGameBoardListener(GameBoardListener listener) {
        listeners.add(listener);
    }

    // Notify listeners when a move is made
    private void notifyMoveMade() {
        for (GameBoardListener listener : listeners) {
            listener.onMoveMade();
        }
    }

    // Notify listeners when the game is completed
    private void notifyGameComplete() {
        for (GameBoardListener listener : listeners) {
            listener.onGameComplete();
        }
    }

    @Override
    public void generateCompleteBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                completedBoard[i][j] = (i * 3 + i / 3 + j) % 9 + 1;
            }
        }
    }

    @Override
    public void generatePlayerBoard(int clues) {
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                playerBoard[i][j] = 0;
                isClue[i][j] = false;
            }
        }
        while (clues > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (playerBoard[row][col] == 0) {
                playerBoard[row][col] = completedBoard[row][col];
                isClue[row][col] = true;
                clues--;
            }
        }
    }

   // In GameBoardModel
@Override
public boolean makeMove(int row, int col, int value) {
    // Check if the value matches the completed board
    if (completedBoard[row][col] == value) {
        // If it matches, update the player's board
        playerBoard[row][col] = value;
        return true;
    } else {
        return false;  // Return false if the move is incorrect
    }
}

    @Override
    public boolean isComplete() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (playerBoard[i][j] != completedBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[][] getPlayerBoard() {
        return playerBoard;
    }

    // isClue method
    public boolean isClue(int row, int col) {
        return isClue[row][col];  // Return whether the cell is a clue
    }
}
