package TR.sudokukid;

import static TR.sudokukid.SudokuParamValidation.*;
import static TR.sudokukid.matrixLibrary.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

/**
 *
 * @author jsanchez
 * @date 20/OCT/26
 */
public class SudokuParamValidationTest {
    
    public SudokuParamValidationTest() {
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
    public void testSomeMethod() {
    }
    
    /* ##################################################################################################################################################
     * isInValidRange, etc:
     */
    static Stream<Arguments> isInValidRangeParams() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, true),
                Arguments.of(5, true),
                Arguments.of(9, true),
                Arguments.of(-1, false),
                Arguments.of(10, false)
        );
    }
    
    @ParameterizedTest
    @MethodSource("isInValidRangeParams")
    public void isInValidRangeReturnsWhatsExpected(int num, boolean expectedResult){
        assertEquals(expectedResult, isInValidRange(num));
    }
    
    @ParameterizedTest
    @MethodSource("isInValidRangeParams")
    public void isNotInValidRangeReturnsWhatsExpected(int num, boolean expectedResult){
        assertEquals(!expectedResult, isNotInValidRange(num));
    }
    
    static Stream<Arguments> validRangeGroupParams() {
        return Stream.of(
                Arguments.of(2),
                Arguments.of(1),
                Arguments.of(5),
                Arguments.of(9),
                Arguments.of(8),
                Arguments.of(4)
        );
    }
    static Stream<Arguments> validRangeValueParams() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(2),
                Arguments.of(1),
                Arguments.of(5),
                Arguments.of(9),
                Arguments.of(8),
                Arguments.of(4)
        );
    }
    
    @ParameterizedTest
    @MethodSource("validRangeValueParams")
    public void checkValidValueDoesntThrowExceptionWhenValIsValid(int val){
        assertDoesNotThrow(() -> checkValidValue(val));
    }
    @ParameterizedTest
    @MethodSource("validRangeGroupParams")
    public void checkValidRowNumberDoesntThrowExceptionWhenRowIsValid(int row){
        assertDoesNotThrow(() -> checkValidRowNumber(row));
    }
    @ParameterizedTest
    @MethodSource("validRangeGroupParams")
    public void checkValidColNumberDoesntThrowExceptionWhenColIsValid(int col){
        assertDoesNotThrow(() -> checkValidColNumber(col));
    }
    @ParameterizedTest
    @MethodSource("validRangeGroupParams")
    public void checkValidSqrNumberDoesntThrowExceptionWhenSqrIsValid(int sqr){
        assertDoesNotThrow(() -> checkValidSqrNumber(sqr));
    }
    
    static Stream<Arguments> invalidRangeGroupParams() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(50),
                Arguments.of(10),
                Arguments.of(-8),
                Arguments.of(11)
        );
    }
    static Stream<Arguments> invalidRangeValueParams() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(50),
                Arguments.of(10),
                Arguments.of(-8),
                Arguments.of(11)
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidRangeValueParams")
    public void checkValidValueThrowsExceptionWhenValIsNotValid(int val){
        IllegalArgumentException ex;
        ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> checkValidValue(val)
        );
        assertEquals("Invalid value! Must be a number from 0 to 9.",ex.getMessage());
    }
    @ParameterizedTest
    @MethodSource("invalidRangeGroupParams")
    public void checkValidRowNumberThrowsExceptionWhenRowIsNotValid(int row){
        IllegalArgumentException ex;
        ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> checkValidRowNumber(row)
        );
        assertEquals("Invalid row! Must be a number from 1 to 9.",ex.getMessage());
    }
    @ParameterizedTest
    @MethodSource("invalidRangeGroupParams")
    public void checkValidColNumberThrowsExceptionWhenRowIsNotValid(int col){
        IllegalArgumentException ex;
        ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> checkValidColNumber(col)
        );
        assertEquals("Invalid column! Must be a number from 1 to 9.",ex.getMessage());
    }
    @ParameterizedTest
    @MethodSource("invalidRangeGroupParams")
    public void checkValidSqrNumberThrowsExceptionWhenRowIsNotValid(int sqr){
        IllegalArgumentException ex;
        ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> checkValidSqrNumber(sqr)
        );
        assertEquals("Invalid square! Must be a number from 1 to 9.",ex.getMessage());
    }
    
    /* ##################################################################################################################################################
     * isValidMatrix:
     */
    static Stream<Arguments> validSudokuMatrixs() {
        return Stream.of(
                Arguments.of((Object) emptyMatrix),
                Arguments.of((Object) fullMatrix),
                Arguments.of((Object) validMatrix1),
                Arguments.of((Object) validMatrix2),
                Arguments.of((Object) validMatrix3)
        );
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
    @MethodSource("validSudokuMatrixs")
    public void isValidMatrixReturnsTrueWhenSudokuIsValid(int[][] matrix){
        assertTrue(isValidMatrix(matrix));
    }
    
    @ParameterizedTest
    @MethodSource("invalidSudokuMatrixs")
    public void isValidMatrixReturnsFalseWhenSudokuIsNotValid(int[][] matrix){
        assertFalse(isValidMatrix(matrix));
    }
    
    @ParameterizedTest
    @MethodSource("validSudokuMatrixs")
    public void checkValidMatrixDoesntThrowExceptionWhenSudokuIsValid(int[][] matrix){
        assertDoesNotThrow(() -> checkValidMatrix(matrix));
    }
    
    @ParameterizedTest
    @MethodSource("invalidSudokuMatrixs")
    public void checkValidMatrixThrowsExceptionWhenSudokuIsNotValid(int[][] matrix){
        IllegalArgumentException ex;
        ex = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> checkValidMatrix(matrix)
        );
        assertEquals("The matrix doesn't contain a valid sudoku!",ex.getMessage());
    }
    
    
}
