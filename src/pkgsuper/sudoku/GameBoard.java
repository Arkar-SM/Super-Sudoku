package pkgsuper.sudoku;

import java.util.Random;

/**
 * GameBoard class - Manages the Sudoku board and player moves.
 * Implements IGameBoard interface.
 */
public class GameBoard implements IGameBoard {
    private int[][] completedBoard = new int[9][9];  // Completed board
    private int[][] playerBoard = new int[9][9];     // Player's board
    private boolean[][] isClue = new boolean[9][9];  // Track clue positions

    // Constructor to generate the boards with the given number of clues
    public GameBoard(int clues) {
        generateCompleteBoard();
        generatePlayerBoard(clues);  // Call to generate the player's board
    }

    @Override
    public void generateCompleteBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                completedBoard[i][j] = (i * 3 + i / 3 + j) % 9 + 1;
            }
        }
    }

    // FIX: Changed this method to public to avoid the symbol not found error
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

    @Override
    public void displayBoard(String elapsedTime) {
        System.out.printf("%30s%n", "Time: " + elapsedTime);
        System.out.println("  | A  B  C  | D  E  F  | G  H  I");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("----------------------------------");
            }
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                if (isClue[i][j]) {
                    System.out.print(playerBoard[i][j] + "  ");
                } else if (playerBoard[i][j] == 0) {
                    System.out.print("0  ");
                } else {
                    System.out.print(playerBoard[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean makeMove(String move) {
        int col = move.charAt(0) - 'a';
        int row = move.charAt(1) - '1';
        int number = Character.getNumericValue(move.charAt(3));

        if (isClue[row][col]) {
            System.out.println("You cannot change a clue number.");
            return false;
        }

        playerBoard[row][col] = number;
        return true;
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
}
