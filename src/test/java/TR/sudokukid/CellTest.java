package TR.sudokukid;

import static TR.sudokukid.SudokuTest.random;
import static TR.sudokukid.matrixLibrary.*;
import java.util.ArrayList;
import java.util.List;
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
 * @author jsanchez
 */
public class CellTest {
    
    SudokuKid sudokuKid;
    Cell cell;
    
    public CellTest() {
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
        cell = null;
    }
    
    /* ##################################################################################################################################################
     * CONSTRUCTORS:
     */
    static Stream<Arguments> validRowCol() {
        return Stream.of(
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1)
        );
    }
    
    @ParameterizedTest
    @MethodSource("validRowCol")
    public void standardConstructorCreatesCellCorrectly(int row, int col){
        assertDoesNotThrow(() -> cell = new Cell(row, col));
        List<Executable> plausibleValuesAllTrue = new ArrayList<>();
        for (boolean isValCandidate : cell.getPlausVals()) {
            plausibleValuesAllTrue.add(() -> assertTrue(isValCandidate));
        }
        assertAll(
                () -> assertEquals(row, cell.row()),
                () -> assertEquals(col, cell.col()),
                () -> assertAll(plausibleValuesAllTrue)
        );
    }
    
    static Stream<Arguments> invalidRowCol() {
        return Stream.of(
                Arguments.of(0,random.nextInt(8)+1),
                Arguments.of(-1,random.nextInt(8)+1),
                Arguments.of(10,random.nextInt(8)+1),
                Arguments.of(11,random.nextInt(8)+1),
                Arguments.of(234,random.nextInt(8)+1)
        );
    }
    @ParameterizedTest
    @MethodSource("invalidRowCol")
    public void standardConstructorThrowsExceptionWhenRowIsNotValid(int row, int col){
        assertThrows(IllegalArgumentException.class, () -> cell = new Cell(row, col));
    }
    @ParameterizedTest
    @MethodSource("invalidRowCol")
    public void standardConstructorThrowsExceptionWhenColIsNotValid(int col, int row){
        assertThrows(IllegalArgumentException.class, () -> cell = new Cell(row, col));
    }
    
    @ParameterizedTest
    @MethodSource("validRowCol")
    public void cloningConstructorCreatesCellCorrectly(int row, int col){
        cell = new Cell(row, col);
        Cell clone = cell.clone();
        assertAll(
                () -> assertEquals(cell.row(), clone.row()),
                () -> assertEquals(cell.col(), clone.col()),
                () -> assertArrayEquals(cell.getPlausVals(), clone.getPlausVals())
        );
    }
    
    /* ##################################################################################################################################################
     * getSqr:
     */
    static Stream<Arguments> getSqrTestParams() {
        return Stream.of(
                Arguments.of(1,1,1),
                Arguments.of(1,4,2),
                Arguments.of(2,7,3),
                Arguments.of(7,7,9),
                Arguments.of(6,7,6),
                Arguments.of(4,3,4),
                Arguments.of(5,5,5),
                Arguments.of(5,6,5),
                Arguments.of(8,4,8),
                Arguments.of(9,1,7)
        );
    }
    
    @ParameterizedTest
    @MethodSource("getSqrTestParams")
    public void getSqrReturnsWhatsExpected(int row, int col, int expectedSqr){
        assertEquals(expectedSqr, Position.sqr(row, col));
    }
    
    /* ##################################################################################################################################################
     * countPlausVals:
     */
    static Stream<Arguments> countPlausValsTestParams() {
        return Stream.of(
                Arguments.of((Object) validMatrix2, 2, 2, 3),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, 9),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, 9),
                Arguments.of((Object) validMatrix2, 1, 9, 3),
                Arguments.of((Object) validMatrix2, 1, 1, 0),
                Arguments.of((Object) validMatrix2, 1, 2, 0),
                Arguments.of((Object) validMatrix2, 8, 8, 1)
        );
    }
    
    @ParameterizedTest
    @MethodSource("countPlausValsTestParams")
    public void countPlausValsReturnsWhatsExpected(int[][] matrix, int row, int col, int expected) {
        sudokuKid = new SudokuKid(matrix);
        sudokuKid.basicSudokuRules();
        assertEquals(expected, 
                sudokuKid.getSudoku().getCell(row, col).countPlausVals());
    }
    
    /* ##################################################################################################################################################
     * getPlausValsList:
     */
    static Stream<Arguments> getPlausValsListTestParams() {
        return Stream.of(
                Arguments.of((Object) validMatrix2, 2, 2, new int[]{2,4,7}),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, new int[]{1,2,3,4,5,6,7,8,9}),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, new int[]{1,2,3,4,5,6,7,8,9}),
                Arguments.of((Object) validMatrix2, 1, 9, new int[]{2,4,8}),
                Arguments.of((Object) validMatrix2, 1, 1, new int[0]),
                Arguments.of((Object) validMatrix2, 1, 2, new int[0]),
                Arguments.of((Object) validMatrix2, 8, 8, new int[]{3})
        );
    }
    
    @ParameterizedTest
    @MethodSource("getPlausValsListTestParams")
    public void getPlausValsListReturnsWhatsExpected(int[][] matrix, int row, int col, int[] expected) {
        sudokuKid = new SudokuKid(matrix);
        sudokuKid.basicSudokuRules();
        assertArrayEquals(expected, 
                sudokuKid.getSudoku().getCell(row, col).getPlausValsList());
    }
    
    /* ##################################################################################################################################################
     * isPlausible:
     */
    static Stream<Arguments> isPlausibleTestParams() {
        return Stream.of(
                Arguments.of((Object) validMatrix2, 2, 2, 2, true),
                Arguments.of((Object) validMatrix2, 2, 2, 1, false),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, random.nextInt(8)+1, true),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, random.nextInt(8)+1, true),
                Arguments.of((Object) validMatrix2, 1, 9, 4, true),
                Arguments.of((Object) validMatrix2, 1, 1, random.nextInt(8)+1, false),
                Arguments.of((Object) validMatrix2, 1, 1, random.nextInt(8)+1, false),
                Arguments.of((Object) validMatrix2, 1, 1, random.nextInt(8)+1, false),
                Arguments.of((Object) validMatrix2, 8, 8, 1, false),
                Arguments.of((Object) validMatrix2, 8, 8, 9, false),
                Arguments.of((Object) validMatrix2, 8, 8, 4, false),
                Arguments.of((Object) validMatrix2, 8, 8, 3, true)
        );
    }
    @ParameterizedTest
    @MethodSource("isPlausibleTestParams")
    public void isPlausibleReturnsWhatsExpected(int[][] matrix, int row, int col, int val, boolean expected) {
        sudokuKid = new SudokuKid(matrix);
        sudokuKid.basicSudokuRules();
        assertEquals(expected,
                sudokuKid.getSudoku().getCell(row, col).isPlausible(val));
    }
    
    static Stream<Arguments> isPlausibleThrowsTestParams() {
        return Stream.of(
                Arguments.of((Object) validMatrix2, 2, 2, 0),
                Arguments.of((Object) validMatrix2, 2, 1, 10),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, 0),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, 0),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, -1),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, 11),
                Arguments.of((Object) validMatrix2, 1, 9, -4),
                Arguments.of((Object) validMatrix2, 1, 9, 231)
        );
    }
    @ParameterizedTest
    @MethodSource("isPlausibleThrowsTestParams")
    public void isPlausibleThrowsExceptionWhenValIsNotValid(int[][] matrix, int row, int col, int val) {
        sudokuKid = new SudokuKid(matrix);
        sudokuKid.basicSudokuRules();
        assertThrows(IllegalArgumentException.class,
                () -> sudokuKid.getSudoku().getCell(row, col).isPlausible(val));
    }
    
    /* ##################################################################################################################################################
     * areAllPlausible:
     */
    static Stream<Arguments> areAllPlausibleTestParams() {
        return Stream.of(
                Arguments.of((Object) validMatrix2, 2, 2, true, new int[]{2,4,7}),
                Arguments.of((Object) validMatrix2, 2, 2, false, new int[]{2,4,7,1}),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, true, new int[]{random.nextInt(8)+1,random.nextInt(8)+1,random.nextInt(8)+1}),
                Arguments.of((Object) emptyMatrix, random.nextInt(8)+1, random.nextInt(8)+1, true, new int[]{random.nextInt(8)+1,random.nextInt(8)+1,random.nextInt(8)+1,random.nextInt(8)+1,random.nextInt(8)+1}),
                Arguments.of((Object) validMatrix2, 1, 9, true, new int[]{2,4,8}),
                Arguments.of((Object) validMatrix2, 1, 1, false, new int[]{2,4,8}),
                Arguments.of((Object) validMatrix2, 1, 2, false, new int[]{random.nextInt(8)+1,random.nextInt(8)+1,random.nextInt(8)+1}),
                Arguments.of((Object) validMatrix2, 8, 8, true, new int[]{3}),
                Arguments.of((Object) validMatrix2, 8, 8, false, new int[]{1}),
                Arguments.of((Object) validMatrix2, 8, 8, false, new int[]{3,2})
        );
    }
    @ParameterizedTest
    @MethodSource("areAllPlausibleTestParams")
    public void areAllPlausibleReturnsWhatsExpected(int[][] matrix, int row, int col, boolean expected, int... vals) {
        sudokuKid = new SudokuKid(matrix);
        sudokuKid.basicSudokuRules();
        assertEquals(expected,
                sudokuKid.getSudoku().getCell(row, col).isPlausible(vals));
    }
    
    static Stream<Arguments> areAllPlausibleThrowsTestParams() {
        return Stream.of(
                Arguments.of((Object) validMatrix2, 2, 2, new int[0]),
                Arguments.of((Object) validMatrix2, 2, 1, new int[]{1,2,10}),
                Arguments.of((Object) validMatrix2, 2, 1, new int[]{3,0}),
                Arguments.of((Object) validMatrix2, 6, 3, new int[]{1,-2,6})
        );
    }
    @ParameterizedTest
    @MethodSource("areAllPlausibleThrowsTestParams")
    public void areAllPlausibleThrowsExceptionWhenValsNotValid(int[][] matrix, int row, int col, int... vals) {
        sudokuKid = new SudokuKid(matrix);
        sudokuKid.basicSudokuRules();
        assertThrows(IllegalArgumentException.class,
                () -> sudokuKid.getSudoku().getCell(row, col).isPlausible(vals));
    }
    
    /* ##################################################################################################################################################
     * isOnlyCandidate:
     */
    static Stream<Arguments> isOnlyCandidateTestParams() {
        return Stream.of(
                Arguments.of(true, new int[]{1,2,9}, new int[]{1,2,9}),
                Arguments.of(false, new int[]{1}, new int[]{1,2,9}),
                Arguments.of(true, new int[]{4}, new int[]{4}),
                Arguments.of(false, new int[]{4,7}, new int[]{4}),
                Arguments.of(false, new int[]{1,2,9}, new int[]{1})
        );
    }
    @ParameterizedTest
    @MethodSource("isOnlyCandidateTestParams")
    public void isOnlyCandidateReturnsWhatsExpected(boolean expected, int[] cellCandidates, int... vals){
        cell = new Cell(random.nextInt(8)+1, random.nextInt(8)+1);
        cell.removeAllCandidatesBut(cellCandidates);
        assertEquals(expected, cell.isOnlyCandidate(vals));
    }
    
    /* ##################################################################################################################################################
     * removeAllCandidatesBut:
     */
    static Stream<Arguments> removeAllCandidatesButTestParams() {
        return Stream.of(
                Arguments.of(new int[]{1,2,9}),
                Arguments.of(new int[]{1}),
                Arguments.of(new int[]{4}),
                Arguments.of(new int[]{4,7}),
                Arguments.of(new int[]{1,2,9})
        );
    }
    @ParameterizedTest
    @MethodSource("removeAllCandidatesButTestParams")
    public void removeAllCandidatesButDoesWhatsExpected(int[] candidates){
        cell = new Cell(random.nextInt(8)+1, random.nextInt(8)+1);
        cell.removeAllCandidatesBut(candidates);
        assertArrayEquals(candidates, cell.getPlausValsList());
    }
    
}
