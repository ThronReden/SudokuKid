package TR.sudokukid;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jsanchez
 */
public class SudokuTest {
    
    public SudokuTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void standardConstructorCreatesEmptySudoku() {
        Sudoku sudoku = new Sudoku();
        sudoku.showSudoku();
        
        assertTrue(sudoku.isEmpty());
    }
    
    @Test
    public void standardConstructorCreatesValidSizeSudoku(){
        Sudoku sudoku = new Sudoku();
        //the desired size:
        final int correctSize = 9;
        //the actual values:
        int actualSizeRows = sudoku.cells.length;
        int actualSizeCols = sudoku.cells[0].length;
        
        assertAll("Both rows and columns are size 9:",
            () -> assertEquals(correctSize,actualSizeRows),
            () -> assertEquals(correctSize,actualSizeCols)
        );
    }
}
