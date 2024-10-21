package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GameBoardView - Displays the Sudoku board GUI with a timer and buttons.
 * Implements GameBoardListener to get updates from the model.
 */
public class GameBoardView extends JFrame implements GameBoardListener {
    private JTextField[][] grid;
    private JLabel timerLabel;
    private Timer gameTimer;
    private int secondsElapsed;
    private JButton submitButton;
    private JButton backButton;
    private MainMenuView mainMenuView;
    private GameBoardModel gameBoardModel;
    private boolean isPuzzleCompleted = false;  

    // Constructor
    public GameBoardView(ActionListener listener, GameBoardModel gameBoardModel, MainMenuView mainMenuView) {
        this.gameBoardModel = gameBoardModel;  //set the model for interaction
        this.mainMenuView = mainMenuView;
        grid = new JTextField[9][9];  // create 9x9 grid of text fields
        initializeComponents(listener);
        setupLayout();
        updateBoard(gameBoardModel.getPlayerBoard());  // display the initial board
        secondsElapsed = 0;  // initialize elapsed time
        startTimer();  // start the timer when the board is shown
    }

    // Initialize GUI components
    private void initializeComponents(ActionListener listener) {
        timerLabel = new JLabel("Time: 00:00");

        submitButton = new JButton("Submit");
        backButton = new JButton("Back to Menu");

        // Set action commands and listeners for the buttons
        submitButton.setActionCommand("Submit");
        backButton.setActionCommand("Back to Menu");

        submitButton.addActionListener(e -> handleSubmit());  // Handle submission
        backButton.addActionListener(listener);

        // Initialize the grid with text fields and restrict inputs
        Font puzzleFont = new Font("Arial", Font.BOLD, 20);  // Set font for numbers
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new JTextField(2);
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                grid[i][j].setFont(puzzleFont);
                ((AbstractDocument) grid[i][j].getDocument()).setDocumentFilter(new NumericDocumentFilter());
            }
        }

        // Initialize the game timer (ticks every second)
        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            updateTimerLabel();
        });
    }

    // Setup the JFrame layout
    private void setupLayout() {
        setTitle("Super Sudoku - Game Board");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));

        // Add text fields to the grid panel and set the borders for the 3x3 blocks
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridPanel.add(grid[i][j]);
                grid[i][j].setBorder(createGridBorder(i, j));
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        mainPanel.add(timerLabel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Create thick black borders between the 3x3 blocks
    private Border createGridBorder(int row, int col) {
        int top = (row % 3 == 0) ? 3 : 1;  // Thicker top border for 3x3 grids
        int left = (col % 3 == 0) ? 3 : 1;  // Thicker left border for 3x3 grids
        int bottom = (row == 8) ? 3 : 1;    // Thicker bottom border on last row
        int right = (col == 8) ? 3 : 1;     // Thicker right border on last column
        return BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
    }

    // Method to handle when the user submits the puzzle
    private void handleSubmit() {
        if (isPuzzleCompleted) {
            return;  // No further action if the puzzle is already complete
        }

        boolean allCorrect = true;
        try {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String input = grid[row][col].getText().trim();
                    if (!input.isEmpty()) {
                        int value = Integer.parseInt(input);
                        if (value >= 1 && value <= 9) {
                            boolean moveCorrect = gameBoardModel.makeMove(row, col, value);
                            if (!moveCorrect) {
                                allCorrect = false;
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid number! Only 1-9 allowed.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
            }

            if (gameBoardModel.isComplete() && allCorrect) {
                isPuzzleCompleted = true;
                stopTimer();
                JOptionPane.showMessageDialog(this, "Congratulations! You've completed the puzzle.");
                makeBoardNonEditable();
                goBackToMainMenu();  // After clicking OK, return to the main menu
            } else if (!allCorrect) {
                JOptionPane.showMessageDialog(this, "The puzzle is still wrong!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "The puzzle is still incomplete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to make the entire board non-editable
    private void makeBoardNonEditable() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col].setEditable(false);  // Disable editing
            }
        }
    }

    private void goBackToMainMenu() {
        this.setVisible(false);  // Hide the game board
        mainMenuView.showMenu();  // Display the main menu
    }

    // Method to update the board visually
    public void updateBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0 && gameBoardModel.isClue(i, j)) {
                    grid[i][j].setText(String.valueOf(board[i][j]));
                    grid[i][j].setEditable(false);  // Set clues to non-editable
                    grid[i][j].setFont(new Font("Arial", Font.BOLD, 20));  // Bold the clues
                } else {
                    grid[i][j].setText(board[i][j] == 0 ? "" : String.valueOf(board[i][j]));
                    grid[i][j].setEditable(true);
                    grid[i][j].setFont(new Font("Arial", Font.PLAIN, 18));  // Regular font for user input
                }
            }
        }
    }

    @Override
    public void onGameComplete() {
        stopTimer();
        JOptionPane.showMessageDialog(this, "Congratulations! You've completed the puzzle.");
        isPuzzleCompleted = true;
    }

    @Override
    public void boardChanged(int[][] board) {
        updateBoard(board);
    }

    @Override
    public void onMoveMade() {
        System.out.println("A move was made on the board.");
    }

    public void startTimer() {
        secondsElapsed = 0;  // Reset timer
        gameTimer.start();
    }

    public void stopTimer() {
        gameTimer.stop();
    }

    private void updateTimerLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    public String getElapsedTime() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void showBoard() {
        setVisible(true);
    }

    public void closeBoard() {
        setVisible(false);
    }
}
