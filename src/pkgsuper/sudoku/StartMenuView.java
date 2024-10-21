package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * StartMenuView - Displays the start menu for entering or creating profiles.
 * Provides pop-ups for entering player names and confirmation on exit.
 */
public class StartMenuView extends JFrame {
    private JButton createProfileButton;
    private JButton enterNameButton;
    private JButton exitButton;

    // Constructor
    public StartMenuView(ActionListener listener) {
        initializeComponents(listener);
        setupLayout();
    }

    // Initialize GUI components
    private void initializeComponents(ActionListener listener) {
        // Initialize buttons
        createProfileButton = new JButton("Create New Player");
        enterNameButton = new JButton("Enter Player Name");
        exitButton = new JButton("Exit");

        // Set action commands and listeners for the buttons
        createProfileButton.setActionCommand("Create New Player");
        enterNameButton.setActionCommand("Enter Player Name");
        exitButton.setActionCommand("Exit");

        createProfileButton.addActionListener(listener);
        enterNameButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    // Setup the JFrame layout with only buttons (no input fields)
    private void setupLayout() {
        setTitle("Super Sudoku - Start Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on screen

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));  // Add padding

        panel.add(enterNameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Spacing between buttons
        panel.add(createProfileButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);

        // Align buttons to center
        enterNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(panel);
    }

    // Show the start menu
    public void showMenu() {
        setVisible(true);
    }

    // Hide the start menu
    public void closeMenu() {
        setVisible(false);
    }

    // Pop-up dialog for entering player name
    public String promptForPlayerName(String title) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel labelPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        labelPanel.add(new JLabel("Enter Your Name:"));
        panel.add(labelPanel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField playerNameField = new JTextField(20);
        textPanel.add(playerNameField);
        panel.add(textPanel, BorderLayout.CENTER);

        // Pop-up dialog with Enter and Back buttons
        int result = JOptionPane.showOptionDialog(this, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{"Enter", "Back"}, "Enter");

        if (result == JOptionPane.OK_OPTION) {
            return playerNameField.getText().trim();
        } else {
            return null;  // Back button pressed or dialog canceled
        }
    }

    // Pop-up confirmation for exiting
    public boolean confirmExit() {
        int result = JOptionPane.showOptionDialog(this,
                "Are you sure you want to exit?", "Exit Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, new Object[]{"Yes", "No"}, "No");

        return result == JOptionPane.YES_OPTION;
    }
}
