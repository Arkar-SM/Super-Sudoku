/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This PlayerProfile class represents a player's profile
 * 
 * with different difficulty levels
 *
 * @author Taha Bazyar
 */
package pkgsuper.sudoku;

public class PlayerProfile {
    // Player's name
    private final String name;

    // Number of matches played and best time for each difficulty level
    private final int easyMatches;
    private final int easyBestTime;
    private final int normalMatches;
    private final int normalBestTime;
    private final int hardMatches;
    private final int hardBestTime;
    private final int testMatches;
    private final int testBestTime;

   
      //Constructs a PlayerProfile object with the specified details.
     
    public PlayerProfile(String name, int easyMatches, int easyBestTime, int normalMatches, int normalBestTime,
                         int hardMatches, int hardBestTime, int testMatches, int testBestTime) {
        this.name = name;
        this.easyMatches = easyMatches;
        this.easyBestTime = easyBestTime;
        this.normalMatches = normalMatches;
        this.normalBestTime = normalBestTime;
        this.hardMatches = hardMatches;
        this.hardBestTime = hardBestTime;
        this.testMatches = testMatches;
        this.testBestTime = testBestTime;
    }

    // return name
    public String getName() {
        return name;
    }

    // returns easy matches played
    public int getEasyMatches() {
        return easyMatches;
    }

    // returns easy best time
    public int getEasyBestTime() {
        return easyBestTime;
    }

    // returns normal matches play
    public int getNormalMatches() {
        return normalMatches;
    }

    // returns normal best time
    public int getNormalBestTime() {
        return normalBestTime;
    }

    // retrun hard matches play
    public int getHardMatches() {
        return hardMatches;
    }

    // returns hard best time
    public int getHardBestTime() {
        return hardBestTime;
    }

    // retrun test matches play
    public int getTestMatches() {
        return testMatches;
    }

    // returns test best time
    public int getTestBestTime() {
        return testBestTime;
    }

    //returns total numbers of matches play
    public int getTotalMatchesPlayed() {
        return easyMatches + normalMatches + hardMatches + testMatches;
    }
}



