package pkgsuper.sudoku;

/**
 * Sudoku game board
 * It is for the generation of a complete Sudoku board with all clues 
 * after that it hide the completed board with 0 which will be the visible Players Game Board, 
 * and it also checks the validity of the player's moves. 
 * It also track the game difficulty and displays the ongoing timer.
 * 
 * @author Arkar
 */

import java.util.Random;

public class GameBoard {
    // 2D arrays to represent the completed board
    private int[][] completedBoard = new int[9][9];
    private int[][] playerBoard = new int[9][9];
    private boolean[][] isClue = new boolean[9][9];

    // text coloring
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m"; // color for displaying clues, so player can easily distinguish 

    // to genenrate the completed and player boards based on difficulty
    public GameBoard(Difficulty difficulty) {
        generateCompleteBoard();
        generatePlayerBoard(difficulty);
    }

    // Generates a completed Sudoku board
    private void generateCompleteBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                completedBoard[i][j] = (i * 3 + i / 3 + j) % 9 + 1;
            }
        }
    }

    // generate playable board for player by hiding the completed board with 0 , based on difficulty 
    private void generatePlayerBoard(Difficulty difficulty) {
        Random random = new Random();
        int clues = difficulty.getClues(); // number of clues to reveal 
        // initialize playerBoard and isClue
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                playerBoard[i][j] = 0; // initially hide all numbers
                isClue[i][j] = false;  // mark all positions as non-clues
            }
        }
        // reveal the clues in random positions
        while (clues > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (playerBoard[row][col] == 0) { // only place a clue if cell is empty
                playerBoard[row][col] = completedBoard[row][col];
                isClue[row][col] = true; // mark this position as a clue
                clues--;
            }
        }
    }

    // displays the player's board with the timer
    public void displayBoard(String elapsedTime) {
        // display the timer at the top right
        System.out.printf("%30s%n", "Time: " + elapsedTime);

        // display the board
        System.out.println("  | A  B  C  | D  E  F  | G  H  I");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("----------------------------------"); // horizontal lines
            }
            System.out.print((i + 1) + " | "); // row number
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| "); // vertical lines
                }
                // displays numbers in board , clues are display in blue
                if (isClue[i][j]) {
                    System.out.print(BLUE + playerBoard[i][j] + RESET + "  ");
                } else if (playerBoard[i][j] == 0) {
                    System.out.print("0  "); // empty cells are shown as 0
                } else {
                    System.out.print(playerBoard[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    //player move and input validation
    public boolean makeMove(String move) {
        // validate input format
        if (!move.matches("[a-i][0-9] [0-9]")) {
            System.out.println("##Invalid input format##");
            return false;
        }

        // parse the input to determine row, column, and the number to place
        int col = move.charAt(0) - 'a';
        int row = move.charAt(1) - '1';
        int number = Character.getNumericValue(move.charAt(3));

        // player cannot change clue
        if (isClue[row][col]) {
            System.out.println("You cannot change a clue number.");
            return false;
        }

        // update with row number
        playerBoard[row][col] = number;
        return true;
    }

    // match the player board with complete board
    public boolean isComplete() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (playerBoard[i][j] != completedBoard[i][j]) {
                    return false; // Return false if its doesnt match
                }
            }
        }
        return true; // Return true if all numbers match
    }

    // Enum to represent the different difficulty levels
    public enum Difficulty {
        EASY(45),   // Easy mode with 45 clues
        NORMAL(35), // Normal mode with 35 clues
        HARD(25),   // Hard mode with 25 clues
        TEST(80);   // Test mode with 80 clues(this is for testing purpose)
                    //We dont have to play the whole game again and again in CUI to test the game
                    // I am leaving this test mode in submission , intentionally for the "Tester" 
                    //so he/she dont have to play the whole game :)    
        private final int clues;

        // to set the number of clues based on difficulty
        Difficulty(int clues) {
            this.clues = clues;
        }

        // Method to get the number of clues based on difficulty
        public int getClues() {
            return clues;
        }
    }
}