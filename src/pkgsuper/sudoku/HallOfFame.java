/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */

import java.io.*;
import java.util.*;

/**
 * Handles displaying and managing the Hall of Fame, where top players are listed based on their performance.
 */
public class HallOfFame {

    private List<PlayerProfile> profiles = new ArrayList<>();

    // ANSI escape codes for displaying colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";

    public HallOfFame() {
        loadProfiles();
        sortProfiles();
    }

    // Load profiles from the resources
    private void loadProfiles() {
        File directory = new File("./resources");
        File[] files = directory.listFiles((dir, name) -> name.endsWith("_profile.txt"));

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    // Skip the header line
                    reader.readLine();
                    String line = reader.readLine();
                    if (line != null) {
                        profiles.add(parseProfile(line));
                    }
                } catch (IOException e) {
                    System.err.println("Error reading profile file: " + file.getName());
                }
            }
        }
    }

    // Parse a single profile line into a PlayerProfile object
    private PlayerProfile parseProfile(String line) {
        String[] tokens = line.trim().split("\\s+");
        String name = tokens[0];
        int easyMatches = Integer.parseInt(tokens[1]);
        String easyBestTime = tokens[2];
        int normalMatches = Integer.parseInt(tokens[3]);
        String normalBestTime = tokens[4];
        int hardMatches = Integer.parseInt(tokens[5]);
        String hardBestTime = tokens[6];
        int testMatches = Integer.parseInt(tokens[7]);
        String testBestTime = tokens[8];
        return new PlayerProfile(name, easyMatches, easyBestTime, normalMatches, normalBestTime, hardMatches, hardBestTime, testMatches, testBestTime);
    }

    // Sort profiles by hard > normal > easy > test > total matches
    private void sortProfiles() {
        profiles.sort(Comparator.comparing(PlayerProfile::getHardBestTime)
            .thenComparing(PlayerProfile::getNormalBestTime)
            .thenComparing(PlayerProfile::getEasyBestTime)
            .thenComparing(PlayerProfile::getTestBestTime)
            .thenComparing(PlayerProfile::getTotalMatches));
    }

    // Display the Hall of Fame
    public void displayHallOfFame(Scanner scanner) {
        System.out.println("\n--- Hall of Fame ---");

        int rank = 1;
        for (PlayerProfile profile : profiles) {
            String color = RESET;
            if (rank == 1) {
                color = GREEN;
            } else if (rank == 2) {
                color = BLUE;
            } else if (rank == 3) {
                color = RED;
            }
            System.out.println(color + "Rank " + rank + ": " + profile + RESET);
            rank++;
        }

        System.out.println("\nEnter [x] to return to Main Menu");
        String input = scanner.nextLine().trim().toLowerCase();
        while (!input.equals("x")) {
            System.out.println("Invalid input. Please enter [x] to return to Main Menu.");
            input = scanner.nextLine().trim().toLowerCase();
        }
    }

    // Inner class to hold player profile data
    private static class PlayerProfile {
        private String name;
        private int easyMatches;
        private String easyBestTime;
        private int normalMatches;
        private String normalBestTime;
        private int hardMatches;
        private String hardBestTime;
        private int testMatches;
        private String testBestTime;

        public PlayerProfile(String name, int easyMatches, String easyBestTime, int normalMatches, String normalBestTime, int hardMatches, String hardBestTime, int testMatches, String testBestTime) {
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

        public String getHardBestTime() {
            return hardBestTime.equals("--") ? "99:99" : hardBestTime; // Placeholder for unset times
        }

        public String getNormalBestTime() {
            return normalBestTime.equals("--") ? "99:99" : normalBestTime;
        }

        public String getEasyBestTime() {
            return easyBestTime.equals("--") ? "99:99" : easyBestTime;
        }

        public String getTestBestTime() {
            return testBestTime.equals("--") ? "99:99" : testBestTime;
        }

        public int getTotalMatches() {
            return easyMatches + normalMatches + hardMatches + testMatches;
        }

        @Override
        public String toString() {
            return String.format("%-20s %-10d %-20s %-10d %-20s %-10d %-20s %-10d %-20s",
                    name, easyMatches, easyBestTime, normalMatches, normalBestTime, hardMatches, hardBestTime, testMatches, testBestTime);
        }
    }
}
