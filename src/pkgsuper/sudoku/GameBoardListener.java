/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pkgsuper.sudoku;

/**
 *
 * @author Arkar
 */



public interface GameBoardListener {
    void onMoveMade();   // triggered when a move is made on the game board.
    void onGameComplete();   // triggered when the game is completed.
     void boardChanged(int[][] board);
}
