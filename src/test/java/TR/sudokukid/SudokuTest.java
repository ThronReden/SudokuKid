package TR.sudokukid;

import static TR.sudokukid.matrixLibrary.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


/**
 *
 * @author TR
 * @date 25/SEP/26
 */
public class SudokuTest {

    Sudoku sudoku;

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
        sudoku = new Sudoku();
    }

    @AfterEach
    public void tearDown() {
    }

    /* ##################################################################################################################################################
     * CONSTRUCTOR:
     */
    @Test
    public void standardConstructorCreatesEmptySudoku() {
//        System.out.println("Must be empty:");
//        sudoku.show();
        assertTrue(sudoku.isEmpty());
    }

    @Test
    public void standardConstructorCreatesValidSizeSudoku() {
        //the desired size:
        final int correctSize = 9;
        //the actual values:
        int actualSizeRows = sudoku.cells.length;
        int actualSizeCols = sudoku.cells[0].length;

        assertAll("Both rows and columns are size 9:",
                () -> assertEquals(correctSize, actualSizeRows),
                () -> assertEquals(correctSize, actualSizeCols)
        );
    }

    @Test
    public void standardConstructorInitializesCellsCorrectly() {
        assertAll("Cells Initialized with correct parameters",
                IntStream.range(0, 9).boxed().flatMap(i
                        -> IntStream.range(0, 9).mapToObj(j
                                -> () -> assertAll(
                                        () -> assertNotNull(sudoku.cells[i][j]),
                                        () -> assertEquals(i + 1, sudoku.cells[i][j].getRow()),
                                        () -> assertEquals(j + 1, sudoku.cells[i][j].getCol())
                                ))));
    }

    /* ##################################################################################################################################################
     * isValid() :
     */
    static Stream<Arguments> validSudokus() {
        return Stream.of(
                Arguments.of((Object) emptyMatrix),
                Arguments.of((Object) fullMatrix),
                Arguments.of((Object) validMatrix1),
                Arguments.of((Object) validMatrix2),
                Arguments.of((Object) validMatrix3)
        );
    }
    static Stream<Arguments> invalidSudokus() {
        return Stream.of(
                Arguments.of((Object) invalidMatrix1),
                Arguments.of((Object) invalidMatrix2),
                Arguments.of((Object) invalidMatrix3),
                Arguments.of((Object) invalidMatrix4),
                Arguments.of((Object) invalidMatrix5),
                Arguments.of((Object) invalidMatrix6)
        );
    }

    @ParameterizedTest
    @MethodSource("validSudokus")
    public void isValidReturnsTrueWhenSudokuIsValid(int[][] matrix){
        assertTrue(Sudoku.isValid(matrix));
    }
    
    @ParameterizedTest
    @MethodSource("invalidSudokus")
    public void isValidReturnsFalseWhenSudokuIsNotValid(int[][] matrix){
        assertFalse(Sudoku.isValid(matrix));
    }
    
    /* ##################################################################################################################################################
     * isEmpty() :
     */
    static Stream<Arguments> isEmptySudoku() {
        return Stream.of(
                Arguments.of(new Sudoku(emptyMatrix), true),
                Arguments.of(new Sudoku(fullMatrix), false),
                Arguments.of(new Sudoku(validMatrix1), false),
                Arguments.of(new Sudoku(validMatrix2), false),
                Arguments.of(new Sudoku(validMatrix3), false),
                Arguments.of(new Sudoku(), true),
                Arguments.of(new Sudoku(matrix711), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isEmptySudoku")
    public void isEmptyReturnsWhatsExpected(
            Sudoku parametersSudoku, boolean expectedIsEmptyReturn) {
//        parametersSudoku.show();
        assertEquals(expectedIsEmptyReturn, parametersSudoku.isEmpty());
    }

    @Test
    public void isEmptyReturnsTrueWhenNoDigits() {
//        System.out.println("Must be empty:");
//        sudoku.show();
        assertTrue(sudoku.isEmpty());
    }

    @Test
    public void isEmptyReturnsFalseWhenTheresDigits() {
        sudoku.cells[0][0].setValue(7);
//        System.out.println("Must have a filled cell:");
//        sudoku.show();
        assertFalse(sudoku.isEmpty());
    }
    
    /* ##################################################################################################################################################
     * isFilled() :
     */
    static Stream<Arguments> isFilledSudoku() {
        return Stream.of(Arguments.of(new Sudoku(emptyMatrix), false),
                Arguments.of(new Sudoku(fullMatrix), true),
                Arguments.of(new Sudoku(validMatrix1), false),
                Arguments.of(new Sudoku(validMatrix2), false),
                Arguments.of(new Sudoku(matrixAlmostFull), false),
                Arguments.of(new Sudoku(), false),
                Arguments.of(new Sudoku(otherFullMatrix), true)
        );
    }
    
    @ParameterizedTest
    @MethodSource("isFilledSudoku")
    public void isFilledReturnsWhatsExpected(
            Sudoku parametersSudoku, boolean expectedIsFilledReturn) {
//        parametersSudoku.show();
        assertEquals(expectedIsFilledReturn, parametersSudoku.isFilled());
    }

    @Test
    public void isFilledReturnsFalseWhenMissingDigits() {
        sudoku.fill(7, 1, 1);
        sudoku.fill(7, 2, 5);
        sudoku.fill(2, 9, 9);
//        System.out.println("Must have some filled cells:");
        sudoku.show();
        assertFalse(sudoku.isFilled());
    }

    @Test
    public void isFilledReturnsTrueWhenSudokuFilled() {
        Sudoku filledSudoku = new Sudoku(fullMatrix);
//        System.out.println("Must have all cells filled:");
//        filledSudoku.show();
        assertTrue(filledSudoku.isFilled());
    }

}
