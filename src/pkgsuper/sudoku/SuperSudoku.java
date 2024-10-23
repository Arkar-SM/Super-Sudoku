package pkgsuper.sudoku;

public class SuperSudoku {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        database.createTables(); // Create tables on program start

        // Add a shutdown hook to close the database connection when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing database connection...");
            database.close();
        }));

        // Initialize the SudokuController to manage the game flow
        SudokuController controller = new SudokuController();
    }
}
