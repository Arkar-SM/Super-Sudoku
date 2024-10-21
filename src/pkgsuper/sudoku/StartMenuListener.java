/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pkgsuper.sudoku;

/**
 *
 * @author Arkar
 */

public interface StartMenuListener {
    void onCreateProfile();
    void onEnterPlayerName();
    void onExit();
    boolean confirmExit();
}
