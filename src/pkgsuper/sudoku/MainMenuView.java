package pkgsuper.sudoku;

/**
 * Author -TahaBazyar
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * MainMenuView - Displays the main menu GUI with buttons for game options.
 */
public class MainMenuView extends JFrame {
    private JButton playGameButton;
    private JButton tutorialButton;
    private JButton hallOfFameButton;
    private JButton exitButton;

    // Constructor
    public MainMenuView(ActionListener listener) {
        initializeComponents(listener);
        setupLayout();
    }

    // Initialize all GUI components
    private void initializeComponents(ActionListener listener) {
        playGameButton = new JButton("Play Game");
        tutorialButton = new JButton("View Tutorial");
        hallOfFameButton = new JButton("Hall of Fame");
        exitButton = new JButton("Exit");

        // Set action commands and listeners for the buttons
        playGameButton.setActionCommand("Play Game");
        tutorialButton.setActionCommand("View Tutorial");
        hallOfFameButton.setActionCommand("Hall of Fame");
        exitButton.setActionCommand("Exit");

        playGameButton.addActionListener(listener);
        tutorialButton.addActionListener(listener);
        hallOfFameButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    // Setup the JFrame layout with padding and centering
    private void setupLayout() {
        setTitle("Super Sudoku - Main Menu");
        setSize(400, 400);  // Increased size for better look
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on screen

        // Using a BoxLayout for better alignment of buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));  // Padding around the buttons

        panel.add(playGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Space between buttons
        panel.add(tutorialButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(hallOfFameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);

        // Align buttons to center
        playGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hallOfFameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(panel);
    }

    // Display the main menu
    public void showMenu() {
        setVisible(true);
    }

    // Hide the main menu
    public void closeMenu() {
        setVisible(false);
    }
}
