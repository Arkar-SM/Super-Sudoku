package pkgsuper.sudoku;

import javax.swing.*;
import java.awt.*;

/**
 * MainMenuView - Displays the main menu GUI with buttons for game options.
 */
public class MainMenuView extends JFrame {
    private JButton playGameButton;
    private JButton tutorialButton;
    private JButton hallOfFameButton;
    private JButton exitButton;
    private JButton startMenuButton; // New Start Menu button
    private MainMenuListener listener;

    // Constructor
    public MainMenuView(MainMenuListener listener) {
        this.listener = listener;
        initializeComponents();
        setupLayout();
    }

    // Initialize all GUI components
    private void initializeComponents() {
        playGameButton = new JButton("Play Game");
        tutorialButton = new JButton("View Tutorial");
        hallOfFameButton = new JButton("Hall of Fame");
        exitButton = new JButton("Exit");
        startMenuButton = new JButton("Start Menu");  // Initialize Start Menu button

        // Add action listeners for buttons
        playGameButton.addActionListener(e -> listener.onPlayGame());
        tutorialButton.addActionListener(e -> listener.onViewTutorial());
        hallOfFameButton.addActionListener(e -> listener.onHallOfFame());
        exitButton.addActionListener(e -> listener.onExit());
        startMenuButton.addActionListener(e -> listener.onStartMenu());  // Action for Start Menu button
    }

    // Setup the JFrame layout with padding and centering
    private void setupLayout() {
        setTitle("Super Sudoku - Main Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        panel.add(playGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(tutorialButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(hallOfFameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(startMenuButton);  // Add the Start Menu button
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Add space before exit button
        panel.add(exitButton);

        playGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hallOfFameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align Start Menu button
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(panel);
    }

    // Show the main menu
    public void showMenu() {
        setVisible(true);
    }

    // Hide the main menu
    public void closeMenu() {
        setVisible(false);
    }
}
