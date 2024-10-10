/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pkgsuper.sudoku;

/**
 *
 * @author Arkar
 */

/**
 * Interface defining the structure for game modes.
 */
public interface IGameMode {
    void startGame();    // Method to start the game mode
    int getClues();      // Method to return the number of clues for the mode
    String getDifficultyName();  // Method to return the difficulty level's name
}
