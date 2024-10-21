package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GameBoardView - Displays the Sudoku board GUI with a timer and buttons.
 */
public class GameBoardView extends JFrame {
    private JTextField[][] grid;
    private JLabel timerLabel;
    private JButton resetButton;
    private JButton submitButton;
    private JButton backButton;

    private Timer timer;
    private int elapsedSeconds;

    // Constructor
    public GameBoardView(ActionListener listener) {
        grid = new JTextField[9][9];  // Create 9x9 grid of text fields
        initializeComponents(listener);
        setupLayout();
        elapsedSeconds = 0;  // Initialize timer at 0 seconds
    }

    // Initialize GUI components
    private void initializeComponents(ActionListener listener) {
        timerLabel = new JLabel("Time: 00:00");

        resetButton = new JButton("Reset");
        submitButton = new JButton("Submit");
        backButton = new JButton("Back to Menu");

        // Set action commands and listeners for the buttons
        resetButton.setActionCommand("Reset");
        submitButton.setActionCommand("Submit");
        backButton.setActionCommand("Back to Menu");

        resetButton.addActionListener(listener);
        submitButton.addActionListener(listener);
        backButton.addActionListener(listener);

        // Initialize the grid with text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new JTextField(2);
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }

    // Setup the JFrame layout
    private void setupLayout() {
        setTitle("Super Sudoku - Game Board");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));

        // Add text fields to the grid panel
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridPanel.add(grid[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(resetButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        mainPanel.add(timerLabel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Get the current board values
    public int[][] getBoardValues() {
        int[][] values = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText();
                values[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return values;
    }

    // Start the timer
    public void startTimer() {
        timer = new Timer();
        elapsedSeconds = 0;  // Reset elapsed time when the timer starts
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                elapsedSeconds++;
                SwingUtilities.invokeLater(() -> updateTimerLabel());  // Update the timer label every second
            }
        }, 1000, 1000);  // Schedule task to run every 1000 milliseconds (1 second)
    }

    // Stop the timer
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    // Update the timer label with the current elapsed time
    private void updateTimerLabel() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    // Retrieve the elapsed time in MM:SS format
    public String getElapsedTime() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Display the game board
    public void showBoard() {
        setVisible(true);
        startTimer();  // Start the timer when the board is shown
    }

    // Hide the game board
    public void closeBoard() {
        setVisible(false);
        stopTimer();  // Stop the timer when the board is closed
    }
}
