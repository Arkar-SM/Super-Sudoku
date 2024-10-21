package pkgsuper.sudoku;

/**
 * Author -Arkar
 */


import java.io.*;

/**
 * ProfileManager class - Manages player profiles, including creation, validation, and updating best times.
 */
public class ProfileManager {

    // Validate if the profile exists
    public boolean validateProfile(String playerName) {
        File profileFile = new File(getProfileFilePath(playerName));
        return profileFile.exists();  // Check if the profile file exists
    }

    // Create a new player profile
    public boolean createProfile(String playerName) {
        File profileFile = new File(getProfileFilePath(playerName));

        if (!profileFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(profileFile))) {
                // Write the header
                writer.write(formatProfileHeader());
                // Write the player's initial data with no matches and placeholder times
                writer.write(formatInitialProfile(playerName));
                System.out.println("Profile created successfully for " + playerName);
                return true;
            } catch (IOException e) {
                System.err.println("Error creating profile: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Profile for " + playerName + " already exists.");
            return false;
        }
    }

    // Update the best time for a player based on difficulty
    public void updateBestTime(String playerName, String difficulty, String elapsedTime) {
        File profileFile = new File(getProfileFilePath(playerName));

        if (profileFile.exists()) {
            try {
                // Read the profile and update the best time
                BufferedReader reader = new BufferedReader(new FileReader(profileFile));
                StringBuilder updatedProfile = new StringBuilder();
                String line;
                boolean firstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        updatedProfile.append(line).append("\n");  // Keep the header
                        firstLine = false;
                    } else if (line.startsWith(playerName)) {
                        updatedProfile.append(updatePlayerProfile(line, difficulty, elapsedTime));
                    } else {
                        updatedProfile.append(line).append("\n");
                    }
                }
                reader.close();

                // Write the updated profile back to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter(profileFile));
                writer.write(updatedProfile.toString());
                writer.close();

            } catch (IOException e) {
                System.err.println("Error updating profile: " + e.getMessage());
            }
        } else {
            System.out.println("Profile for " + playerName + " does not exist.");
        }
    }

    // Helper method to update the profile line with the best time
    private String updatePlayerProfile(String line, String difficulty, String elapsedTime) {
        String[] parts = line.split("\\s+");
        String easyBestTime = parts[2];
        String normalBestTime = parts[4];
        String hardBestTime = parts[6];
        String testBestTime = parts[8];

        switch (difficulty) {
            case "Easy":
                if (isBetterTime(elapsedTime, easyBestTime)) easyBestTime = elapsedTime;
                break;
            case "Normal":
                if (isBetterTime(elapsedTime, normalBestTime)) normalBestTime = elapsedTime;
                break;
            case "Hard":
                if (isBetterTime(elapsedTime, hardBestTime)) hardBestTime = elapsedTime;
                break;
            case "Test":
                if (isBetterTime(elapsedTime, testBestTime)) testBestTime = elapsedTime;
                break;
        }

        return String.format("%-20s %-10s %-20s %-10s %-20s %-10s %-20s %-10s %-20s\n",
                parts[0], parts[1], easyBestTime, parts[3], normalBestTime,
                parts[5], hardBestTime, parts[7], testBestTime);
    }

    // Helper method to compare times in MM:SS format
    private boolean isBetterTime(String newTime, String oldTime) {
        String[] newParts = newTime.split(":");
        String[] oldParts = oldTime.split(":");
        int newMinutes = Integer.parseInt(newParts[0]);
        int newSeconds = Integer.parseInt(newParts[1]);
        int oldMinutes = Integer.parseInt(oldParts[0]);
        int oldSeconds = Integer.parseInt(oldParts[1]);

        return (newMinutes < oldMinutes) || (newMinutes == oldMinutes && newSeconds < oldSeconds);
    }

    // Helper method to format the initial profile
    private String formatInitialProfile(String playerName) {
        return String.format("%-20s %-10d %-20s %-10d %-20s %-10d %-20s %-10d %-20s\n",
                playerName, 0, "--", 0, "--", 0, "--", 0, "--");
    }

    // Helper method to format the profile header
    private String formatProfileHeader() {
        return String.format("%-20s %-10s %-20s %-10s %-20s %-10s %-20s %-10s %-20s\n",
                "Name", "Easy", "Easy_Best_Time", "Normal", "Normal_Best_Time",
                "Hard", "Hard_Best_Time", "Test", "Test_Best_Time");
    }

    // Helper method to get the file path for a player's profile
    private String getProfileFilePath(String playerName) {
        return "./resources/" + playerName + "_profile.txt";
    }
}
