package TR.sudokukid;

import static TR.sudokukid.SudokuTest.random;
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
        cell = new Cell(1,1);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
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
                () -> assertEquals(row, cell.getRow()),
                () -> assertEquals(col, cell.getCol()),
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
    
}
