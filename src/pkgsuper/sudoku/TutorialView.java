package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * TutorialView - Displays the tutorial GUI with instructions.
 */
public class TutorialView extends JFrame {
    private JButton backButton;
    private JTextArea tutorialText;

    // Constructor
    public TutorialView(ActionListener listener) {
        initializeComponents(listener);
        setupLayout();
    }

    // Initialize GUI components
    private void initializeComponents(ActionListener listener) {
        tutorialText = new JTextArea();
        tutorialText.setEditable(false);
        tutorialText.setText("Welcome to Super Sudoku!\n\n"
                + "Game Objective:\n"
                + "Fill every empty cell in the 9x9 grid with numbers 1 to 9.\n"
                + "Each row, column, and 3x3 block must contain each number exactly once.\n\n"
                + "How to Play:\n"
                + "Input Numbers: To place a number, type a the number you want to enter.\n"
                + "After completing the game board Click Submit\n\n"
                + "Game Modes:\n"
                + "Easy Mode (45 Clues)\n"
                + "Normal Mode (35 Clues)\n"
                + "Hard Mode (25 Clues)\n\n"
                + "Achievements:\n"
                + "All modes' results are recorded in your Profile and Hall of Fame.\n"
                + "Top players are ranked by the shortest completion times!\n"
                + "Give your best to beat the top players!");

        backButton = new JButton("Back to Main Menu");
        backButton.setActionCommand("Back to Menu");
        backButton.addActionListener(listener);
    }

    // Setup the JFrame layout
    private void setupLayout() {
        setTitle("Super Sudoku - Tutorial");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tutorialText.setMargin(new Insets(10, 10, 10, 10));  // Add padding to text area

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tutorialText), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
    }

    // Show the tutorial
    public void showTutorial() {
        setVisible(true);
    }

    // Hide the tutorial
    public void closeTutorial() {
        setVisible(false);
    }
}
