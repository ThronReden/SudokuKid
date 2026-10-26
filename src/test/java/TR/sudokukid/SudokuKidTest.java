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
public class SudokuKidTest {
    
    SudokuKid sudokukid;
    
    public SudokuKidTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        sudokukid = new SudokuKid();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /* ##################################################################################################################################################
     * basicSudokuRules:
     */
    static Stream<Arguments> basicSudokuRulesTestArgs() {
        return Stream.of(
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1, random.nextInt(8)+1),
                Arguments.of(random.nextInt(8)+1,random.nextInt(8)+1, random.nextInt(8)+1)
        );
    }
    @ParameterizedTest
    @MethodSource("basicSudokuRulesTestArgs")
    public void basicSudokuRulesUpdatesCandidatesCorrectly(int row, int col, int val){
        sudokukid.fill(val, row, col);
        Cell filledCell = sudokukid.getSudoku().getCell(row, col);
        
        List<Executable> affectedCellsCandidatesUpdated = new ArrayList<>();
        for (Cell cell : sudokukid.getSudoku().getRowCells(row)) {
            if (!cell.equals(filledCell)) {
                affectedCellsCandidatesUpdated.add(() -> assertFalse(cell.isPlausible(val)));
            }
        }
        for (Cell cell : sudokukid.getSudoku().getColCells(col)) {
            if (!cell.equals(filledCell)) {
                affectedCellsCandidatesUpdated.add(() -> assertFalse(cell.isPlausible(val)));
            }
        }
        for (Cell cell : sudokukid.getSudoku().getSqrCells(row, col)) {
            if (!cell.equals(filledCell)) {
                affectedCellsCandidatesUpdated.add(() -> assertFalse(cell.isPlausible(val)));
            }
        }
        assertAll(affectedCellsCandidatesUpdated);
    }
    
    /* ##################################################################################################################################################
     * solveSimpleSingles:
     */
    static Stream<Arguments> solveSimpleSinglesTestArgs() {
        return Stream.of(
                Arguments.of(emptyMatrix, false),
                Arguments.of(simpleSinglesEasySolve1, true),
                Arguments.of(simpleSinglesNoSolve1, false),
                Arguments.of(simpleSinglesEasySolve2, true),
                Arguments.of(simpleSinglesEasySolve3, true),
                Arguments.of(simpleSinglesNoSolve2, false),
                Arguments.of(simpleSinglesNoSolve3, false),
                Arguments.of(fullMatrix, false)
        );
    }
    @ParameterizedTest
    @MethodSource("solveSimpleSinglesTestArgs")
    public void solveSimpleSinglesFindsSinglesIfExist(int[][] matrix, boolean expected){
        sudokukid = new SudokuKid(matrix);
        sudokukid.basicSudokuRules();
//        System.out.println("solveSimpleSingles, expected: "+expected);
//        sudokukid.getSudoku().show();
        assertEquals(expected, sudokukid.solveSimpleSingles());
//        if (expected) {
//            System.out.println("Solved:");
//            sudokukid.getSudoku().show();
//        }
    }
    
    /* ##################################################################################################################################################
     * solveSimplePairs:
     */
    static Stream<Arguments> solveSimplePairsTestArgs() {
        return Stream.of(
                Arguments.of(emptyMatrix, false),
                Arguments.of(simpleSinglesNoSolve1, true),
                Arguments.of(simplePairsEasySolve1, true),
                Arguments.of(simplePairsNoSolve1, false),
                Arguments.of(simplePairsEasySolve2, true),
                Arguments.of(fullMatrix, false)
        );
    }
    @ParameterizedTest
    @MethodSource("solveSimplePairsTestArgs")
    public void solveSimplePairsFindsPairsIfExist(int[][] matrix, boolean expected){
        sudokukid = new SudokuKid(matrix);
        sudokukid.basicSudokuRules();
//        System.out.println("solveSimplePairs, expected: "+expected);
//        sudokukid.getSudoku().show();
        assertEquals(expected, sudokukid.solveSimplePairs());
//        if (expected) {
//            System.out.println("Solved:");
//            sudokukid.solveSimpleSingles();
//            sudokukid.getSudoku().show();
//        }
    }
    
}
