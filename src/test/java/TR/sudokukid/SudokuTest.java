package TR.sudokukid;

import static TR.sudokukid.matrixLibrary.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


/**
 *
 * @author TR
 * @date 20/OCT/26
 */
public class SudokuTest {

    static final Random random = new Random();
    
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

    static Stream<Arguments> validSudokuStrings() {
        return Stream.of(
                Arguments.of(vopani1),
                Arguments.of(vopani2),
                Arguments.of(vopani3),
                Arguments.of(menneske843211),
                Arguments.of(menneske4813117)
        );
    }
    
    @ParameterizedTest
    @MethodSource("validSudokuStrings")
    public void fromStringConstructorFillsTheCorrectCellWithTheCorrectValue(String string){
        sudoku = new Sudoku(string);
        List<Executable> checks = new ArrayList<>();
        for (int i = 0; i < sudoku.cells.length; i++) {
            for (int j = 0; j < sudoku.cells[i].length; j++) {
                final int row = i;
                final int col = j;
                final char ch = string.charAt(row*9+col);
                final int expected = ch == '.'? 0 
                        : Character.getNumericValue(ch);
                checks.add(() -> assertEquals(
                        expected, 
                        sudoku.cells[row][col].getValue()
                ));
            }
        }
        assertAll(checks);
    }
    
    static Stream<Arguments> invalidSudokuStrings() {
        return Stream.of(
                Arguments.of(vopani1+"0"),
                Arguments.of(vopani2+"A"),
                Arguments.of("0005980040091002688070005099857030020T00000053040056u0200070900401906000000201040"),
                Arguments.of("0160309000010004840200000000090000068301250001080700000004086900060000002070490"),
                Arguments.of("Helo, world!")
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidSudokuStrings")
    public void fromMatrixConstructorThrowsExceptionWhenMatrixIsNotValid(String string){
        IllegalArgumentException ex;
        ex = assertThrows(
                IllegalArgumentException.class,
                () -> sudoku = new Sudoku(string)
        );
        assertAll(
            () -> assertEquals("The string does not contain a sudoku statement.", ex.getMessage()),
            () -> assertTrue(sudoku.isEmpty())
        );
    }
    
    static Stream<Arguments> validSudokuMatrixs() {
        return Stream.of(
                Arguments.of((Object) emptyMatrix),
                Arguments.of((Object) fullMatrix),
                Arguments.of((Object) validMatrix1),
                Arguments.of((Object) validMatrix2),
                Arguments.of((Object) validMatrix3)
        );
    }
    
    @ParameterizedTest
    @MethodSource("validSudokuMatrixs")
    public void fromMatrixConstructorFillsTheCorrectCellWithTheCorrectValue(int[][] matrix){
        sudoku = new Sudoku(matrix);
        List<Executable> checks = new ArrayList<>();
        for (int i = 0; i < sudoku.cells.length; i++) {
            for (int j = 0; j < sudoku.cells[i].length; j++) {
                final int row = i;
                final int col = j;
                checks.add(() -> assertEquals(
                        matrix[row][col], 
                        sudoku.cells[row][col].getValue()
                ));
            }
        }
        assertAll(checks);
    }
    
    static Stream<Arguments> invalidSudokuMatrixs() {
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
    @MethodSource("invalidSudokuMatrixs")
    public void fromMatrixConstructorThrowsExceptionWhenMatrixIsNotValid(int[][] matrix){
        assertThrows(IllegalArgumentException.class,() -> sudoku = new Sudoku(matrix));
        assertTrue(sudoku.isEmpty());
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
        return Stream.of(
                Arguments.of(new Sudoku(emptyMatrix), false),
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

    /* ##################################################################################################################################################
     * isGroupFilled:
     */
    static Stream<Arguments> isGroupFilledParams() {
        return Stream.of(
                Arguments.of(new Sudoku(fullMatrix).getRowCells(1), true),
                Arguments.of(new Sudoku(fullMatrix).getSqrCells(1), true),
                Arguments.of(new Sudoku(emptyMatrix).getRowCells(3), false),
                Arguments.of(new Sudoku(validMatrix1).getColCells(5), false),
                Arguments.of(new Sudoku(matrixAlmostFull).getColCells(8), true),
                Arguments.of(new Sudoku(matrixAlmostFull).getColCells(9), false)
        );
    }
    
    @ParameterizedTest
    @MethodSource("isGroupFilledParams")
    public void isGroupFilledReturnsWhatsExpected(
            Cell[] parametersGroup, boolean expectedIsFilledReturn) {
        assertEquals(expectedIsFilledReturn, Sudoku.isGroupFilled(parametersGroup));
    }
    
    /* ##################################################################################################################################################
     * getCell:
     */
    static Stream<Arguments> randRowCol() {
        return Stream.of(
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1)
        );
    }
    @ParameterizedTest
    @MethodSource("randRowCol")
    public void getCellReturnsWhatsExpected(int row, int col) {
        assertEquals(sudoku.getCell(row, col), sudoku.cells[row-1][col-1]);
    }
    
    /* ##################################################################################################################################################
     * getRowCells, getColCells, getSqrCells,
     * getRowVals, getColVals, getSqrVals:
     */
    static Stream<Arguments> randGroupNumber() {
        return Stream.of(
                Arguments.of(random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1)
        );
    }
    @ParameterizedTest
    @MethodSource("randGroupNumber")
    public void getRowCellsValsReturnWhatsExpected(int rowNumber) {
        Cell[] rowCells = new Cell[9];
        int[] rowVals = new int[9];
        int index = 0;
        for(Cell cell : sudoku.cells[rowNumber-1]){
            rowCells[index] = cell;
            rowVals[index] = cell.getValue();
            index++;
        }
        assertAll(
            () -> assertArrayEquals(sudoku.getRowCells(rowNumber), rowCells),
            () -> assertArrayEquals(sudoku.getRowVals(rowNumber), rowVals)
        );
    }
    @ParameterizedTest
    @MethodSource("randGroupNumber")
    public void getColCellsValsReturnWhatsExpected(int colNumber) {
        Cell[] colCells = new Cell[9];
        int[] colVals = new int[9];
        for (int i = 0; i < sudoku.cells.length; i++) {
            colCells[i] = sudoku.cells[i][colNumber-1];
            colVals[i] = sudoku.cells[i][colNumber-1].getValue();
        }
        assertAll(
            () -> assertArrayEquals(sudoku.getColCells(colNumber), colCells),
            () -> assertArrayEquals(sudoku.getColVals(colNumber), colVals)
        );
    }
    @ParameterizedTest
    @MethodSource("randGroupNumber")
    public void getSqrCellsValsReturnWhatsExpected(int sqrNumber) {
        Cell[] sqrCells = new Cell[9];
        int[] sqrVals = new int[9];
        int sqrIndex = sqrNumber - 1; 
        for(int i = 0; i < 3; i++){
            sqrCells[i] = sudoku.cells[sqrIndex/3*3][sqrIndex%3*3+i];
            sqrVals[i] = sudoku.cells[sqrIndex/3*3][sqrIndex%3*3+i].getValue();
            sqrCells[i+3] = sudoku.cells[sqrIndex/3*3+1][sqrIndex%3*3+i];
            sqrVals[i+3] = sudoku.cells[sqrIndex/3*3+1][sqrIndex%3*3+i].getValue();
            sqrCells[i+6] = sudoku.cells[sqrIndex/3*3+2][sqrIndex%3*3+i];
            sqrVals[i+6] = sudoku.cells[sqrIndex/3*3+2][sqrIndex%3*3+i].getValue();
        }
        assertAll(
            () -> assertArrayEquals(sudoku.getSqrCells(sqrNumber), sqrCells),
            () ->     assertArrayEquals(sudoku.getSqrVals(sqrNumber), sqrVals)
        );
    }
    
    /* ##################################################################################################################################################
     * fill:
     */
    static Stream<Arguments> randValRowCol() {
        return Stream.of(
                Arguments.of(random.nextInt(9),random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(9),random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(9),random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(9),random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(9),random.nextInt(8)+1,random.nextInt(8)+1)
        );
    }
    
    @ParameterizedTest
    @MethodSource("randValRowCol")
    public void fillFillsTheCorrectCellWithTheCorrectValue(int val, int row, int col){
        sudoku.fill(val, row, col);
        assertEquals(val, sudoku.cells[row-1][col-1].getValue());
    }
    
    static Stream<Arguments> invalidValRowCol() {
        return Stream.of(
                Arguments.of(11, random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(-1, random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(10, random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(-20, random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(48, random.nextInt(8)+1,random.nextInt(8)+1)
        );
    }
    static Stream<Arguments> validValInvalidRowCol() {
        return Stream.of(
                Arguments.of(random.nextInt(8)+1, 0, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1, -1, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1, 10, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1, -20, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1, 48, random.nextInt(8)+1)
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidValRowCol")
    public void fillThrowsExceptionWhenValIsNotValid(int val, int row, int col){
        assertThrows(IllegalArgumentException.class, () -> sudoku.fill(val, row, col));
        assertEquals(Cell.EMPTY, sudoku.cells[row-1][col-1].getValue());
    }
    @ParameterizedTest
    @MethodSource("validValInvalidRowCol")
    public void fillThrowsExceptionWhenRowIsNotValid(int val, int row, int col){
        assertThrows(IllegalArgumentException.class, () -> sudoku.fill(val, row, col));
    }
    @ParameterizedTest
    @MethodSource("validValInvalidRowCol")
    public void fillThrowsExceptionWhenColIsNotValid(int val, int col, int row){
        assertThrows(IllegalArgumentException.class, () -> sudoku.fill(val, row, col));
    }
    
}
